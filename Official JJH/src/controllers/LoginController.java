package controllers;

import app.MainApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController
{
  @FXML
  private TextField userField;

  @FXML
  private PasswordField passField;

  @FXML
  private Button loginButton;

  private MainApplication mainApplication;

  @FXML
  void loginButtonPressed() {
    mainApplication.runAMPhoneAudit(userField.getText(), passField.getText());
    
  }

  public void setMainApp(MainApplication mainApplication)
  {
    // TODO Auto-generated method stub
    this.mainApplication = mainApplication;
  }
}
