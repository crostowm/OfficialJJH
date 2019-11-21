package controllers;

import app.AddManagerStage;
import app.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import personnel.Manager;

public class AddManagerController
{

  @FXML
  private Button addManagerButton;

  @FXML
  private TextField usernameField;

  @FXML
  private TextField nameField;

  @FXML
  private TextField passwordField;

  private AddManagerStage addManagerStage;

  @FXML
  void addManagerButtonPressed(ActionEvent event)
  {
    MainApplication.dataHub.addManager(
        new Manager(nameField.getText(), usernameField.getText(), passwordField.getText(), ""));
    addManagerStage.close();
  }

  public void setStage(AddManagerStage addManagerStage)
  {
    // TODO Auto-generated method stub
    this.addManagerStage = addManagerStage;
  }

}
