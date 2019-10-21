package controllers;

import app.MainApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import readers.AMPhoneAuditMap;
import util.CateringOrder;

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

  public void initialize()
  {
    AMPhoneAuditMap map = MainApplication.dataHub.getAMPhoneAudit();
    salesLabelAM.setText(map.getData(AMPhoneAuditMap.SALES, AMPhoneAuditMap.AM) + "");
    salesLabelPM.setText(map.getData(AMPhoneAuditMap.SALES, AMPhoneAuditMap.PM) + "");
    overUnderLabelAM.setText(map.getData(AMPhoneAuditMap.OVER_UNDER, AMPhoneAuditMap.AM) + "");
    overUnderLabelPM.setText(map.getData(AMPhoneAuditMap.OVER_UNDER, AMPhoneAuditMap.PM) + "");
    laborLabelAM.setText(map.getData(AMPhoneAuditMap.LABOR, AMPhoneAuditMap.AM) + "");
    laborLabelPM.setText(map.getData(AMPhoneAuditMap.LABOR, AMPhoneAuditMap.PM) + "");
  }

  public void setMain(MainApplication mainApplication)
  {
    // TODO Auto-generated method stub
    this.mainApplication = mainApplication;
  }

  @FXML
  private void sendReportButtonPressed()
  {
    System.out.println(toEmail());
    mainApplication.runApplication();
  }

  public String toEmail()
  {
    String email = String.format(
        "Sales\nAM: %s\nPM: %s\nOver/Under\nAM: %s\nPM: %s\nLabor\nAM: %s\nPM: %s\n",
        salesLabelAM.getText(), salesLabelPM.getText(), overUnderLabelAM.getText(),
        overUnderLabelPM.getText(), laborLabelAM.getText(), laborLabelPM.getText());
    email += "Staff: " + (staffCheck.isSelected()?"OK\n":"Need Help\n");
    email += "Equipment: " + (equipmentCheck.isSelected()?"OK\n":"Need Fixin\n");
    email += "Punchlist: " + (punchlistCheck.isSelected()?"OK\n":"Incomplete\n");
    email += explanationArea.getText();
    return email;
  }
}
