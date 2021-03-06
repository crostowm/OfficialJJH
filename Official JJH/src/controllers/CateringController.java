package controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import app.AppDirector;
import app.CateringStage;
import error_handling.ErrorHandler;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.CateringOrder;

public class CateringController
{
  @FXML
  private ChoiceBox<Integer> hourChoice, minuteChoice, dayChoice, monthChoice, yearChoice;

  @FXML
  private ChoiceBox<String> ampmChoice;
  
  @FXML
  private TextField dollarField, numSticksField;
  
  @FXML
  private TextArea infoArea;

  @FXML
  private Button addButton;

  private Stage stage;

  public void initialize()
  {
    //Year
    ArrayList<Integer> years = new ArrayList<Integer>();
    for(int ii = new GregorianCalendar().get(Calendar.YEAR); ii < new GregorianCalendar().get(Calendar.YEAR) + 10; ii++)
    {
      years.add(ii);
    }
    yearChoice.setItems(FXCollections.observableArrayList(years));
    yearChoice.setValue(new GregorianCalendar().get(Calendar.YEAR));
    
    //Month
    ArrayList<Integer> months = new ArrayList<Integer>();
    for(int ii = 1; ii < 13; ii++)
    {
      months.add(ii);
    }
    monthChoice.setItems(FXCollections.observableArrayList(months));
    monthChoice.setValue(new GregorianCalendar().get(Calendar.MONTH) + 1);
    
    //Day
    ArrayList<Integer> days = new ArrayList<Integer>();
    for(int ii = 1; ii < 32; ii++)
    {
      days.add(ii);
    }
    dayChoice.setItems(FXCollections.observableArrayList(days));
    dayChoice.setValue(new GregorianCalendar().get(Calendar.DAY_OF_MONTH));
    
    //Hour
    ArrayList<Integer> hours = new ArrayList<Integer>();
    for(int ii = 1; ii < 13; ii++)
    {
      hours.add(ii);
    }
    hourChoice.setItems(FXCollections.observableArrayList(hours));
    hourChoice.setValue(10);
    
    //Minute
    ArrayList<Integer> minutes = new ArrayList<Integer>();
    for(int ii = 0; ii < 60; ii+=5)
    {
      minutes.add(ii);
    }
    minuteChoice.setItems(FXCollections.observableArrayList(minutes));
    minuteChoice.setValue(0);
    
    //AM/PM
    ArrayList<String> ampm = new ArrayList<String>();
    ampm.add("AM");
    ampm.add("PM");
    ampmChoice.setItems(FXCollections.observableArrayList(ampm));
    ampmChoice.setValue("AM");
  }
  
  @FXML
  void addButtonPressed(ActionEvent event) {
    try
    {
      GregorianCalendar cal = new GregorianCalendar();
      cal.set(Calendar.YEAR, yearChoice.getValue());
      cal.set(Calendar.MONTH, monthChoice.getValue() - 1);
      cal.set(Calendar.DAY_OF_MONTH, dayChoice.getValue());
      cal.set(Calendar.HOUR, hourChoice.getValue());
      cal.set(Calendar.AM_PM, ampmChoice.getValue().equals("AM")?Calendar.AM:Calendar.PM);
      cal.set(Calendar.MINUTE, minuteChoice.getValue());
      
      int numSticks = -1;
      if(numSticksField.getText().length() > 0)
        numSticks = Integer.parseInt(numSticksField.getText());
      
      AppDirector.dataHub.addCateringOrder(new CateringOrder(Double.parseDouble(dollarField.getText()), cal, numSticks, infoArea.getText()));
      if(stage != null)
        stage.close();
    }
    catch (NumberFormatException e)
    {
      System.out.println("Unable to parse Catering order");
      ErrorHandler.addError(e);
    }
  }

  public void setStage(Stage stage)
  {
    // TODO Auto-generated method stub
    this.stage = stage;
  }
}
