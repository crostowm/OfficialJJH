package controllers;

import app.AddManagerStage;
import app.MainApplication;
import error_handling.ErrorHandler;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import personnel.Manager;
import util.DataHub;

public class SettingsTabController
{
  @FXML
  private TextField amBufferField, pmBufferField, btvField, b9tvField, wlvField, bakedAt11Field,
      bakedAtSCField, lettuceBVField, tomatoBVField, onionBVField, cucumberBVField, pickleBVField;

  @FXML
  private Button addManagerButton, removeManagerButton;
  
  @FXML
  private ChoiceBox<Manager> managerChoice;
  
  public void initialize()
  {
    managerChoice.setItems(FXCollections.observableArrayList(MainApplication.dataHub.getManagers()));
  }
  
  public void updateAllFields()
  {
    managerChoice.setItems(FXCollections.observableArrayList(MainApplication.dataHub.getManagers()));
    try
    {
      MainApplication.dataHub.changeSetting(DataHub.AMBUFFER,
          Double.parseDouble(amBufferField.getText()));
      MainApplication.dataHub.changeSetting(DataHub.PMBUFFER,
          Double.parseDouble(pmBufferField.getText()));
      MainApplication.dataHub.changeSetting(DataHub.BTV, Double.parseDouble(btvField.getText()));
      MainApplication.dataHub.changeSetting(DataHub.B9TV, Double.parseDouble(b9tvField.getText()));
      MainApplication.dataHub.changeSetting(DataHub.WLV, Double.parseDouble(wlvField.getText()));
      MainApplication.dataHub.changeSetting(DataHub.BAKEDAT11,
          Double.parseDouble(bakedAt11Field.getText()));
      MainApplication.dataHub.changeSetting(DataHub.BAKEDATSC,
          Double.parseDouble(bakedAtSCField.getText()));
      MainApplication.dataHub.changeSetting(DataHub.LETTUCEBV,
          Double.parseDouble(lettuceBVField.getText()));
      MainApplication.dataHub.changeSetting(DataHub.TOMATOBV,
          Double.parseDouble(tomatoBVField.getText()));
      MainApplication.dataHub.changeSetting(DataHub.ONIONBV,
          Double.parseDouble(onionBVField.getText()));
      MainApplication.dataHub.changeSetting(DataHub.CUCUMBERBV,
          Double.parseDouble(cucumberBVField.getText()));
      MainApplication.dataHub.changeSetting(DataHub.PICKLEBV,
          Double.parseDouble(pickleBVField.getText()));
    }
    catch (NumberFormatException nfe)
    {
      System.out.println("NFE, Could not parse Settings:\n" + nfe.getMessage());
    }
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
    MainApplication.dataHub.removeManager(managerChoice.getValue());
  }
  // Settings
  // TODO needs to update setting in datahub
  @FXML
  void amBufferFieldChanged()
  {
    try
    {
      MainApplication.dataHub.changeSetting(DataHub.AMBUFFER,
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
      MainApplication.dataHub.changeSetting(DataHub.PMBUFFER,
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
      MainApplication.dataHub.changeSetting(DataHub.BTV, Double.parseDouble(btvField.getText()));
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
      MainApplication.dataHub.changeSetting(DataHub.B9TV, Double.parseDouble(b9tvField.getText()));
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
      MainApplication.dataHub.changeSetting(DataHub.WLV, Double.parseDouble(wlvField.getText()));
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
      MainApplication.dataHub.changeSetting(DataHub.BAKEDAT11,
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
      MainApplication.dataHub.changeSetting(DataHub.BAKEDATSC,
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
      MainApplication.dataHub.changeSetting(DataHub.LETTUCEBV,
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
      MainApplication.dataHub.changeSetting(DataHub.TOMATOBV,
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
      MainApplication.dataHub.changeSetting(DataHub.ONIONBV,
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
      MainApplication.dataHub.changeSetting(DataHub.CUCUMBERBV,
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
      MainApplication.dataHub.changeSetting(DataHub.PICKLEBV,
          Double.parseDouble(pickleBVField.getText()));
    }
    catch (NumberFormatException nfe)
    {
      System.out.println("NFE, Could not parse Settings:\n" + nfe.getMessage());
      ErrorHandler.addError(nfe);
    }
  }

}
