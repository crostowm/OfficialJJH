package controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

/**
 * Must set MainApp
 * @author crost
 *
 */
public class AreaManagerReportController
{
  @FXML
  private Label salesLabelAM, salesLabelPM, overUnderLabelAM, overUnderLabelPM, laborLabelAM,
      laborLabelPM, cateringTimeLabel, cateringDollarLabel;

  @FXML
  private CheckBox staffCheck, equipmentCheck, punchlistCheck;

  @FXML
  private ChoiceBox<CateringOrder> cateringChoice;

  @FXML
  private VBox attendanceVBox;
  
  @FXML
  private TextArea explanationArea;

  @FXML
  private Button sendReportButton;

  private MainApplication mainApplication;
  private ArrayList<CateringOrder> todaysOrders = new ArrayList<CateringOrder>();

  public void initialize()
  {
    //Set All Labels with data
    AMPhoneAuditItem item = MainApplication.dataHub.getAMPhoneAudit();
    salesLabelAM.setText(String.format("%.2f", item.getSalesAM()));
    salesLabelPM.setText(String.format("%.2f", item.getSalesPM()));
    overUnderLabelAM.setText(String.format("%.2f", item.getCashOverUnderAM()));
    overUnderLabelPM.setText(String.format("%.2f", item.getCashOverUnderPM()));
    laborLabelAM.setText(String.format("%.2f", item.getLaborAM()));
    laborLabelPM.setText(String.format("%.2f", item.getLaborPM()));

    //Add catering orders
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
    
    for(AttendanceShift as: MainApplication.dataHub.getAttendaceShiftsFromYesterday())
    {
      attendanceVBox.getChildren().add(new AttendanceShiftBox(as));
    }
    System.out.println("AMRC");
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
          + MainApplication.storeNumber + " " + sdf.format(new GregorianCalendar().getTime()), toEmail());
      email.send();
    }
    else
      System.out.println(toEmail());
    mainApplication.runApplication();
  }

  public String toEmail()
  {
    String email = String.format(
        "This is an automated JimmyHub email from store %d\n\n"
            + "Sales AM/PM\n%s | %s\n\nOver/Under AM/PM\n%s | %s\n\nLabor AM/PM\n%s | %s\n\n",
        MainApplication.storeNumber, salesLabelAM.getText(), salesLabelPM.getText(),
        overUnderLabelAM.getText(), overUnderLabelPM.getText(), laborLabelAM.getText(),
        laborLabelPM.getText());
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
    email += explanationArea.getText() + "\n";
    return email;
  }
}
