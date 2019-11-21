package controllers;

import app.MainApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import personnel.Manager;

public class LoginController
{
  @FXML
  private TextField userField;

  @FXML
  private PasswordField passField;

  @FXML
  private Button loginButton;

  @FXML
  private Label messageLabel;
  
  private MainApplication mainApplication;

  @FXML
  void loginButtonPressed() {
    boolean loginFlag = false;
    for(Manager m: MainApplication.dataHub.getManagers())
    {
      if(m.getUsername().equals(userField.getText()) && m.getPassword().equals(passField.getText()))
        loginFlag = true;
    }
    if(loginFlag)
    mainApplication.runAMPhoneAudit(userField.getText(), passField.getText());
    else
    messageLabel.setText("Unable to login, invalid username and password");
  }

  public void setMainApp(MainApplication mainApplication)
  {
    // TODO Auto-generated method stub
    this.mainApplication = mainApplication;
  }
}
