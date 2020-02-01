package controllers;

import app.AppDirector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import util.Config;

public class ConfigMenuController
{

  @FXML
  private TextField storeNumberField;

  @FXML
  private Button startButton;

  @FXML
  private TextField amEmailField;

  @FXML
  private CheckBox downloadReportsCheck;

  @FXML
  private CheckBox sendAMEmailCheck;

  @FXML
  private TextField macroUserField;

  @FXML
  private TextField macroPassField;

  @FXML
  private CheckBox fullStartCheck;
  
  @FXML
  private CheckBox downloadWeekendingReportsCheck;

  private AppDirector appDirector;

  @FXML
  public void initialize()
  {
    if(AppDirector.config != null)
    {
      storeNumberField.setText(AppDirector.config.getStoreNumber() + "");
      amEmailField.setText(AppDirector.config.getAreaManagerEmail());
      macroUserField.setText(AppDirector.config.getMacroUser());
      macroPassField.setText(AppDirector.config.getMacroPass());
      downloadReportsCheck.setSelected(AppDirector.config.shouldDownloadReports());
      sendAMEmailCheck.setSelected(AppDirector.config.shouldSendAMEmail());
      fullStartCheck.setSelected(AppDirector.config.shouldAmFullStart());
    }
  }
  
  @FXML
  void startButtonPressed(ActionEvent event)
  {
    Config config = new Config(Integer.parseInt(storeNumberField.getText()), amEmailField.getText(),
        macroUserField.getText(), macroPassField.getText(), downloadReportsCheck.isSelected(),
        fullStartCheck.isSelected(), sendAMEmailCheck.isSelected(), downloadWeekendingReportsCheck.isSelected());
    AppDirector.config = config;
    appDirector.configComplete();
  }

  public void setMain(AppDirector appDirector)
  {
    this.appDirector = appDirector;
  }

}
