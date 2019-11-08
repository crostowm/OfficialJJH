package controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import app.MainApplication;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import readers.AMPhoneAuditMap;
import util.CateringOrder;
import util.Email;
import util.JimmyCalendarUtil;

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
  private TextArea explanationArea;

  @FXML
  private Button sendReportButton;

  private MainApplication mainApplication;
  private ArrayList<CateringOrder> todaysOrders = new ArrayList<CateringOrder>();

  public void initialize()
  {
    AMPhoneAuditMap map = MainApplication.dataHub.getAMPhoneAudit();
    salesLabelAM.setText(map.getData(AMPhoneAuditMap.SALES, AMPhoneAuditMap.AM) + "");
    salesLabelPM.setText(map.getData(AMPhoneAuditMap.SALES, AMPhoneAuditMap.PM) + "");
    overUnderLabelAM.setText(map.getData(AMPhoneAuditMap.OVER_UNDER, AMPhoneAuditMap.AM) + "");
    overUnderLabelPM.setText(map.getData(AMPhoneAuditMap.OVER_UNDER, AMPhoneAuditMap.PM) + "");
    laborLabelAM.setText(map.getData(AMPhoneAuditMap.LABOR, AMPhoneAuditMap.AM) + "");
    laborLabelPM.setText(map.getData(AMPhoneAuditMap.LABOR, AMPhoneAuditMap.PM) + "");

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
  }

  public void setMain(MainApplication mainApplication)
  {
    // TODO Auto-generated method stub
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
  
  /*private String fixedLengthString(int length, String str)
  {
    return String.format("%0$" + length + "s", str);
  }
  
  String email = String.format("This is an automated JimmyHub email from store %d\n\n", MainApplication.storeNumber);
  email += fixedLengthString(20, "Sales AM/PM") + fixedLengthString(8, salesLabelAM.getText()) + " | " + fixedLengthString(8, salesLabelPM.getText()) + "\n\n";
  email += fixedLengthString(20, "Over/Under AM/PM") + fixedLengthString(8, overUnderLabelAM.getText()) + " | " + fixedLengthString(8, overUnderLabelPM.getText()) + "\n\n";
  email += fixedLengthString(20, "Labor AM/PM") + fixedLengthString(8, laborLabelAM.getText()) + " | " + fixedLengthString(8, laborLabelPM.getText()) + "\n\n";
  email += "Staff: " + (staffCheck.isSelected() ? "OK\n" : "Need Help\n");
  email += "Equipment: " + (equipmentCheck.isSelected() ? "OK\n" : "Need Fixin\n");
  email += "Punchlist: " + (punchlistCheck.isSelected() ? "OK\n" : "Incomplete\n");*/
}
