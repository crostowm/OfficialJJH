package controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import app.MainApplication;
import gui.AttendanceShiftBox;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import lineitems.AMPhoneAuditItem;
import lineitems.AttendanceShift;
import util.CateringOrder;
import util.Email;
import util.JimmyCalendarUtil;
import util.ManagerDBL;

/**
 * Must set MainApp
 * 
 * @author crost
 *
 */
public class AreaManagerReportController
{
  @FXML
  private Label salesLabelAM, salesLabelPM, overUnderLabelAM, overUnderLabelPM, laborLabelAM,
      laborLabelPM, cateringTimeLabel, cateringDollarLabel, weekLaborLabel, weekCompLabel;

  @FXML
  private CheckBox staffCheck, equipmentCheck, punchlistCheck;

  @FXML
  private ChoiceBox<CateringOrder> cateringChoice;

  @FXML
  private VBox attendanceVBox, mgrDBLBox;

  @FXML
  private TextArea explanationArea;

  @FXML
  private Button sendReportButton;

  private MainApplication mainApplication;
  private ArrayList<CateringOrder> todaysOrders = new ArrayList<CateringOrder>();

  public void initialize()
  {
    // Set All Labels with data
    AMPhoneAuditItem item = MainApplication.dataHub.getAMPhoneAudit();
    salesLabelAM.setText(String.format("%.2f", item.getSalesAM()));
    salesLabelPM.setText(String.format("%.2f", item.getSalesPM()));
    overUnderLabelAM.setText(String.format("%.2f", item.getCashOverUnderAM()));
    overUnderLabelPM.setText(String.format("%.2f", item.getCashOverUnderPM()));
    laborLabelAM.setText(String.format("%.2f", item.getLaborAM()));
    laborLabelPM.setText(String.format("%.2f", item.getLaborPM()));
    weekLaborLabel.setText(String.format("Week Labor: %.2f%%", item.getLaborWeek()));
    weekCompLabel.setText(
        String.format("Week Comps: %.2f%% / $%.2f", item.getCompPerc(), item.getCompDollars()));

    // MDBL Box
    for (ManagerDBL mdbl : getMgrDblsCompletedYesterday())
    {
      Label label = new Label(String.format("%s: %s", mdbl.getCompletedName(), mdbl.getDesc()));
      label.setWrapText(true);
      mgrDBLBox.getChildren().add(label);
    }
    // Add catering orders
    for (CateringOrder co : MainApplication.dataHub.getCateringOrders())
    {
      if (JimmyCalendarUtil.isToday(co.getTime()))
      {
        todaysOrders.add(co);
      }
    }
    cateringChoice.setItems(FXCollections.observableArrayList(todaysOrders));
    cateringChoice.setOnAction(new EventHandler<ActionEvent>()
    {

      @Override
      public void handle(ActionEvent ae)
      {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy hh:mm:ss");
        cateringTimeLabel
            .setText("Time: " + sdf.format(cateringChoice.getValue().getTime().getTime()));
        cateringDollarLabel.setText("Dollar Value: " + cateringChoice.getValue().getDollarValue());
      }
    });

    for (AttendanceShift as : MainApplication.dataHub.getAttendaceShiftsFromYesterday())
    {
      attendanceVBox.getChildren().add(new AttendanceShiftBox(as));
    }
    System.out.println("AMRC");
  }

  private ArrayList<ManagerDBL> getMgrDblsCompletedYesterday()
  {
    ArrayList<ManagerDBL> mdbls = new ArrayList<ManagerDBL>();
    for (ManagerDBL mdbl : MainApplication.dataHub.getCompleteOrIncompleteManagerDBLs(true))
    {
      GregorianCalendar yesterday = new GregorianCalendar();
      yesterday.add(Calendar.DAY_OF_YEAR, -1);
      if (mdbl.getCompleteTime().get(Calendar.DAY_OF_YEAR) == yesterday.get(Calendar.DAY_OF_YEAR))
      {
        mdbls.add(mdbl);
      }
    }
    return mdbls;
  }

  public void setMain(MainApplication mainApplication)
  {
    this.mainApplication = mainApplication;
  }

  @FXML
  private void sendReportButtonPressed()
  {
    if (MainApplication.sendAMEmail)
    {
      sendReportButton.setDisable(true);
      SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy hh:mm:ss");
      Email email = new Email(MainApplication.AMEmail, "Area Manager Report Store "
          + MainApplication.storeNumber + " " + sdf.format(new GregorianCalendar().getTime()),
          toEmail());
      email.send();
    }
    else
      System.out.println(toEmail());
    mainApplication.runApplication();
  }

  public String toEmail()
  {
    String email = String.format("This is an automated JimmyHub email from store %d\n\n"
        + "Sales AM/PM\n%s | %s\n\nOver/Under AM/PM\n%s | %s\n\nLabor AM/PM\n%s | %s\n\n%s\n%s\n",
        MainApplication.storeNumber, salesLabelAM.getText(), salesLabelPM.getText(),
        overUnderLabelAM.getText(), overUnderLabelPM.getText(), laborLabelAM.getText(),
        laborLabelPM.getText(), weekLaborLabel.getText(), weekCompLabel.getText());
    email += "Staff: " + (staffCheck.isSelected() ? "OK\n" : "Need Help\n");
    email += "Equipment: " + (equipmentCheck.isSelected() ? "OK\n" : "Need Fixin\n");
    email += "Punchlist: " + (punchlistCheck.isSelected() ? "OK\n" : "Incomplete\n");
    // Catering
    if (todaysOrders.size() == 0)
      email += "No Catering\n";
    else
    {
      email += "Catering:\n";
      for (CateringOrder co : todaysOrders)
      {
        email += co.toString() + "\n";
      }
    }
    // Mgr DBLs
    if (getMgrDblsCompletedYesterday().size() == 0)
    {
      email += "No Manager DBLs Completed Yesterday.\n";
    }
    else
    {
      for (ManagerDBL mdbl : getMgrDblsCompletedYesterday())
      {
        email += mdbl.getCompletedName() + " -> " + mdbl.getDesc() + "\n";
      }
    }
    email += "Number Completed In Month: " + MainApplication.dataHub.getCompleteOrIncompleteManagerDBLs(true).size() + "\n";
    email += explanationArea.getText() + "\n";
    return email;
  }
}
