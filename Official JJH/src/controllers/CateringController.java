package controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import util.CateringOrder;
import util.DataHub;

public class CateringController
{
  @FXML
  private ChoiceBox<Integer> hourChoice, minuteChoice, dayChoice, monthChoice, yearChoice;

  @FXML
  private ChoiceBox<String> ampmChoice;
  
  @FXML
  private TextField dollarField;

  @FXML
  private Button addButton;

  public void initialize()
  {
    ArrayList<Integer> years = new ArrayList<Integer>();
    for(int ii = new GregorianCalendar().get(Calendar.YEAR); ii < new GregorianCalendar().get(Calendar.YEAR) + 10; ii++)
    {
      years.add(ii);
    }
    yearChoice.setItems(FXCollections.observableArrayList(years));
    yearChoice.setValue(new GregorianCalendar().get(Calendar.YEAR));
    
    ArrayList<Integer> months = new ArrayList<Integer>();
    for(int ii = 1; ii < 13; ii++)
    {
      months.add(ii);
    }
    monthChoice.setItems(FXCollections.observableArrayList(months));
    monthChoice.setValue(new GregorianCalendar().get(Calendar.MONTH));
    
    ArrayList<Integer> days = new ArrayList<Integer>();
    for(int ii = 1; ii < 32; ii++)
    {
      days.add(ii);
    }
    dayChoice.setItems(FXCollections.observableArrayList(days));
    dayChoice.setValue(new GregorianCalendar().get(Calendar.DAY_OF_MONTH));
    
    ArrayList<Integer> hours = new ArrayList<Integer>();
    for(int ii = 1; ii < 13; ii++)
    {
      hours.add(ii);
    }
    hourChoice.setItems(FXCollections.observableArrayList(hours));
    hourChoice.setValue(12);
    
    ArrayList<Integer> minutes = new ArrayList<Integer>();
    for(int ii = 0; ii < 60; ii+=5)
    {
      minutes.add(ii);
    }
    minuteChoice.setItems(FXCollections.observableArrayList(minutes));
    minuteChoice.setValue(0);
    
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
      cal.set(Calendar.MONTH, monthChoice.getValue());
      cal.set(Calendar.DAY_OF_MONTH, dayChoice.getValue());
      cal.set(Calendar.HOUR, hourChoice.getValue() + (ampmChoice.getValue().equals("PM")?12:0));
      cal.set(Calendar.MINUTE, minuteChoice.getValue());
      
      DataHub.addCateringOrder(new CateringOrder(Double.parseDouble(dollarField.getText()), cal));
    }
    catch (NumberFormatException e)
    {
      // TODO: handle exception
    }
  }
}
