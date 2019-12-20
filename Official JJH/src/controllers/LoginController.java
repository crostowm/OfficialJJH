package controllers;

import app.MainApplication;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
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

  public void initialize()
  {
    passField.setOnKeyPressed(new EventHandler<KeyEvent>()
    {
      @Override
      public void handle(KeyEvent arg0)
      {
        loginButtonPressed();
      }
    });
    System.out.println("LC");
  }

  @FXML
  void loginButtonPressed()
  {
    if (userField.getText().equals("") && passField.getText().equals(""))
    {
      mainApplication.runAMPhoneAudit(new Manager("Guest", "Guest", "123", ""));
    }
    else
    {
      Manager mgr = MainApplication.dataHub.getManager(userField.getText(), passField.getText());
      if (mgr != null)
        mainApplication.runAMPhoneAudit(mgr);
      else
        messageLabel.setText("Unable to login, invalid username and password");
    }
  }

  public void setMainApp(MainApplication mainApplication)
  {
    this.mainApplication = mainApplication;
  }
}
