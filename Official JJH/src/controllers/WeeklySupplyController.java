package controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import app.MainApplication;
import app.WeeklySupplyStage;
import gui.WeeklySupplyItemBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import util.Email;

public class WeeklySupplyController
{
  @FXML
  private FlowPane supplyGrid;

  @FXML
  private Button submitButton, addItemButton, removeItemsButton;

  @FXML
  private TextArea commentArea;

  @FXML
  private TextField itemField;

  private ArrayList<WeeklySupplyItemBox> itemBoxList = new ArrayList<WeeklySupplyItemBox>();

  private WeeklySupplyStage weeklySupplyStage;

  public void initialize()
  {
    for (String item : MainApplication.dataHub.getWeeklySupplyItems())
    {
      addItemToList(item);
    }
    itemField.setOnKeyPressed(new EventHandler<KeyEvent>()
    {
      @Override
      public void handle(KeyEvent ke)
      {
        if (ke.getCode().equals(KeyCode.ENTER))
          handleAddItem();
      }
    });
    supplyGrid.setHgap(10);
  }

  protected void handleAddItem()
  {
    addItemToList(itemField.getText());
    MainApplication.dataHub.addWeeklySupplyItem(itemField.getText());
    itemField.setText("");
  }

  private void addItemToList(String item)
  {
    WeeklySupplyItemBox wsib = new WeeklySupplyItemBox(item);
    itemBoxList.add(wsib);
    supplyGrid.getChildren().add(wsib);
  }

  @FXML
  void submitButtonPressed(ActionEvent event)
  {
    SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy hh:mm:ss");
    String email = "--This is an automated JimmyHub email: \nWeekly Supply Order from Store #"
        + MainApplication.storeNumber + " " + sdf.format(new GregorianCalendar().getTime())
        + "--\n";
    for (WeeklySupplyItemBox wsib : itemBoxList)
    {
      if (wsib.isNeeded())
      {
        email += wsib.getName() + ": " + wsib.getCount() + "\n";
      }
    }
    email += commentArea.getText();
    if (MainApplication.sendWeeklySupplyEmail)
    {
      if (new Email("essentialsandwich@gmail.com",
          "Weekly Supply Order from Store #" + MainApplication.storeNumber, email).send())
        weeklySupplyStage.close();
    }
    else
    {
      System.out.println(email);
      weeklySupplyStage.close();
    }
  }

  @FXML
  void addItemButtonPressed()
  {
    handleAddItem();
  }
  
  @FXML
  void removeItemsButtonPressed()
  {
    ArrayList<WeeklySupplyItemBox> itemsToRemove = new ArrayList<WeeklySupplyItemBox>();
    for(WeeklySupplyItemBox wsib: itemBoxList)
    {
      if(wsib.isNeeded())
        itemsToRemove.add(wsib);
    }
    for(WeeklySupplyItemBox wsib: itemsToRemove)
    {
      MainApplication.dataHub.removeWeeklySupplyItem(wsib.getName());
      itemBoxList.remove(wsib);
      supplyGrid.getChildren().remove(wsib);
    }
  }

  public void setStage(WeeklySupplyStage weeklySupplyStage)
  {
    this.weeklySupplyStage = weeklySupplyStage;
  }
}
