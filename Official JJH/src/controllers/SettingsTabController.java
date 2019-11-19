package controllers;

import app.MainApplication;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import util.DataHub;

public class SettingsTabController
{
  @FXML
  private TextField amBufferField, pmBufferField, btvField, b9tvField, wlvField, bakedAt11Field,
      bakedAtSCField, lettuceBVField, tomatoBVField, onionBVField, cucumberBVField, pickleBVField;

  public void updateAllFields()
  {
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
    }
  }

}
