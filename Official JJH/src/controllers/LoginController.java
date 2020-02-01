package controllers;

import app.AppDirector;
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

  private AppDirector mainApplication;

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
    Manager mgr = null;
    if (userField.getText().equals("") && passField.getText().equals(""))
    {
      mgr = new Manager("Guest", "", "", "");
    }
    else
    {
      mgr = AppDirector.dataHub.getManager(userField.getText(), passField.getText());
    }
    if (mgr != null)
    {
      AppDirector.activeManagers.add(mgr);
      if (mainApplication != null)
        mainApplication.runAMPhoneAudit(mgr);
    }
    else
      messageLabel.setText("Unable to login, invalid username and password");
  }

  public void setMainApp(AppDirector mainApplication)
  {
    this.mainApplication = mainApplication;
  }
}
