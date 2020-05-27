package controllers;

import app.AddManagerStage;
import app.AppDirector;
import error_handling.ErrorHandler;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import personnel.Manager;
import selenium.ReportGrabber;
import util.Setting;

public class SettingsTabController
{
  @FXML
  private TextField amBufferField, pmBufferField, btvField, b9tvField, wlvField, wrtvField,
      fsvField, bakedAt11Field, bakedAtSCField, lettuceBVField, tomatoBVField, onionBVField,
      cucumberBVField, pickleBVField, spvField, inshopStartPayField;

  @FXML
  private Button addManagerButton, removeManagerButton, redownloadButton;

  @FXML
  private ChoiceBox<Manager> managerChoice;

  @FXML
  private ChoiceBox<String> downloadChoice;

  @FXML
  private TextArea reportsUsedArea;

  public void initialize()
  {
    System.out.println("STC");
    downloadChoice.setItems(FXCollections.observableArrayList("Attendance (Yesterday)",
        "Attendance (Last Week)", "Area Manager Phone Audit", "Hourly Sales (Last 4 Weeks)",
        "Item Usage Analysis", "Trend Sheet", "UPK (Last 6 Weeks)", "Weekly Sales (Last 4 Weeks)",
        "Weekly Sales (Last Year)"));
    // TODO should be set based on saved datahub data
    managerChoice
        .setItems(FXCollections.observableArrayList(AppDirector.dataHub.getManagers()));
    try
    {
      AppDirector.dataHub.getSettings().setSetting(Setting.AMBUFFER,
          Double.parseDouble(amBufferField.getText()));
      AppDirector.dataHub.getSettings().setSetting(Setting.PMBUFFER,
          Double.parseDouble(pmBufferField.getText()));
      AppDirector.dataHub.getSettings().setSetting(Setting.BTV, Double.parseDouble(btvField.getText()));
      AppDirector.dataHub.getSettings().setSetting(Setting.B9TV, Double.parseDouble(b9tvField.getText()));
      AppDirector.dataHub.getSettings().setSetting(Setting.WLV, Double.parseDouble(wlvField.getText()));
      AppDirector.dataHub.getSettings().setSetting(Setting.BAKEDAT11,
          Double.parseDouble(bakedAt11Field.getText()));
      AppDirector.dataHub.getSettings().setSetting(Setting.BAKEDATSC,
          Double.parseDouble(bakedAtSCField.getText()));
      AppDirector.dataHub.getSettings().setSetting(Setting.LETTUCEBV,
          Double.parseDouble(lettuceBVField.getText()));
      AppDirector.dataHub.getSettings().setSetting(Setting.TOMATOBV,
          Double.parseDouble(tomatoBVField.getText()));
      AppDirector.dataHub.getSettings().setSetting(Setting.ONIONBV,
          Double.parseDouble(onionBVField.getText()));
      AppDirector.dataHub.getSettings().setSetting(Setting.CUCUMBERBV,
          Double.parseDouble(cucumberBVField.getText()));
      AppDirector.dataHub.getSettings().setSetting(Setting.PICKLEBV,
          Double.parseDouble(pickleBVField.getText()));
    }
    catch (NumberFormatException nfe)
    {
      System.out.println("NFE, Could not parse Settings:\n" + nfe.getMessage());
    }
    System.out.println("STC-");
  }

  public void updateAllFields()
  {
    managerChoice
        .setItems(FXCollections.observableArrayList(AppDirector.dataHub.getManagers()));
    reportsUsedArea.setText(AppDirector.reportsUsed);
  }

  @FXML
  void addManagerButtonPressed()
  {
    AddManagerStage ams = new AddManagerStage();
    ams.show();
  }

  @FXML
  void removeManagerButtonPressed()
  {
    AppDirector.dataHub.removeManager(managerChoice.getValue());
  }

  @FXML
  void redownloadButtonClicked()
  {
    ReportGrabber rg = new ReportGrabber();
    rg.startAndLogin();
    switch (downloadChoice.getValue())
    {
      case "Attendance (Yesterday)":
        rg.downloadAttendanceReport();
        break;
      case "Attendance (Last Week)":
        break;
      case "Area Manager Phone Audit":
        rg.downloadLastAMPhoneAuditReport();
        break;
      case "Hourly Sales (Last 4 Weeks)":
        rg.downloadHourlySalesReports(AppDirector.dataHub.getMissingLast4HourlySales());;
        break;
      case "Item Usage Analysis":
        rg.downloadItemUsageAnalysis();
        break;
      case "Trend Sheet":
        rg.downloadTrendSheets();
        break;
      case "UPK (Last 6 Weeks)":
        rg.downloadMissingUPKs(AppDirector.dataHub.getMissingOfLast6UPKYearWeekPairs());
        break;
      case "Weekly Sales (Last 4 Weeks)":
        rg.downloadMissingWSR(AppDirector.dataHub.getMissingOfLast41WSRYearWeekPairs());
        break;
      case "Weekly Sales (Last Year)":
        rg.downloadLastYearWSR();
        break;
    }
    rg.close();
  }

  // Settings
  @FXML
  void amBufferFieldChanged()
  {
    try
    {
      AppDirector.dataHub.getSettings().setSetting(Setting.AMBUFFER,
          Double.parseDouble(amBufferField.getText()));
    }
    catch (NumberFormatException nfe)
    {
      System.out.println("NFE, Could not parse Settings:\n" + nfe.getMessage());
      ErrorHandler.addError(nfe);
    }
  }

  @FXML
  void pmBufferFieldChanged()
  {
    try
    {
      AppDirector.dataHub.getSettings().setSetting(Setting.PMBUFFER,
          Double.parseDouble(pmBufferField.getText()));
    }
    catch (NumberFormatException nfe)
    {
      System.out.println("NFE, Could not parse Settings:\n" + nfe.getMessage());
      ErrorHandler.addError(nfe);
    }
  }

  @FXML
  void btvFieldChanged()
  {
    try
    {
      AppDirector.dataHub.getSettings().setSetting(Setting.BTV, Double.parseDouble(btvField.getText()));
    }
    catch (NumberFormatException nfe)
    {
      System.out.println("NFE, Could not parse Settings:\n" + nfe.getMessage());
      ErrorHandler.addError(nfe);
    }
  }

  @FXML
  void b9tvFieldChanged()
  {
    try
    {
      AppDirector.dataHub.getSettings().setSetting(Setting.B9TV, Double.parseDouble(b9tvField.getText()));
    }
    catch (NumberFormatException nfe)
    {
      System.out.println("NFE, Could not parse Settings:\n" + nfe.getMessage());
      ErrorHandler.addError(nfe);
    }
  }

  @FXML
  void wlvFieldChanged()
  {
    try
    {
      AppDirector.dataHub.getSettings().setSetting(Setting.WLV, Double.parseDouble(wlvField.getText()));
    }
    catch (NumberFormatException nfe)
    {
      System.out.println("NFE, Could not parse Settings:\n" + nfe.getMessage());
      ErrorHandler.addError(nfe);
    }
  }

  @FXML
  void bakedAt11FieldChanged()
  {
    try
    {
      AppDirector.dataHub.getSettings().setSetting(Setting.BAKEDAT11,
          Double.parseDouble(bakedAt11Field.getText()));
    }
    catch (NumberFormatException nfe)
    {
      System.out.println("NFE, Could not parse Settings:\n" + nfe.getMessage());
      ErrorHandler.addError(nfe);
    }
  }

  @FXML
  void bakedAtSCFieldChanged()
  {
    try
    {
      AppDirector.dataHub.getSettings().setSetting(Setting.BAKEDATSC,
          Double.parseDouble(bakedAtSCField.getText()));
    }
    catch (NumberFormatException nfe)
    {
      System.out.println("NFE, Could not parse Settings:\n" + nfe.getMessage());
      ErrorHandler.addError(nfe);
    }
  }

  @FXML
  void lettuceBVChanged()
  {
    try
    {
      AppDirector.dataHub.getSettings().setSetting(Setting.LETTUCEBV,
          Double.parseDouble(lettuceBVField.getText()));
    }
    catch (NumberFormatException nfe)
    {
      System.out.println("NFE, Could not parse Settings:\n" + nfe.getMessage());
      ErrorHandler.addError(nfe);
    }
  }

  @FXML
  void tomatoBVChanged()
  {
    try
    {
      AppDirector.dataHub.getSettings().setSetting(Setting.TOMATOBV,
          Double.parseDouble(tomatoBVField.getText()));
    }
    catch (NumberFormatException nfe)
    {
      System.out.println("NFE, Could not parse Settings:\n" + nfe.getMessage());
      ErrorHandler.addError(nfe);
    }
  }

  @FXML
  void onionBVChanged()
  {
    try
    {
      AppDirector.dataHub.getSettings().setSetting(Setting.ONIONBV,
          Double.parseDouble(onionBVField.getText()));
    }
    catch (NumberFormatException nfe)
    {
      System.out.println("NFE, Could not parse Settings:\n" + nfe.getMessage());
      ErrorHandler.addError(nfe);
    }
  }

  @FXML
  void cucumberBVChanged()
  {
    try
    {
      AppDirector.dataHub.getSettings().setSetting(Setting.CUCUMBERBV,
          Double.parseDouble(cucumberBVField.getText()));
    }
    catch (NumberFormatException nfe)
    {
      System.out.println("NFE, Could not parse Settings:\n" + nfe.getMessage());
      ErrorHandler.addError(nfe);
    }
  }

  @FXML
  void pickleBVChanged()
  {
    try
    {
      AppDirector.dataHub.getSettings().setSetting(Setting.PICKLEBV,
          Double.parseDouble(pickleBVField.getText()));
    }
    catch (NumberFormatException nfe)
    {
      System.out.println("NFE, Could not parse Settings:\n" + nfe.getMessage());
      ErrorHandler.addError(nfe);
    }
  }

}
