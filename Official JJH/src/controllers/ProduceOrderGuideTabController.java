package controllers;

import java.util.ArrayList;

import app.AppDirector;
import gui.ProduceOrderBox;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import util.JimmyCalendarUtil;

public class ProduceOrderGuideTabController
{
  @FXML
  private TextField letMinField, tomMinField, onMinField, celMinField;
  
  // 1 is for case
  @FXML
  private TextField projField;

  @FXML
  private ChoiceBox<String> orderingOnChoice;

  @FXML
  private ChoiceBox<String> orderNextOn;

  private int firstShiftProduceWillBeUsed = 5, lastShiftProduceWillBeNeededFor = 8;

  private double projections = 0;

  private ProduceOrderBox letBox, tomBox, onBox, celBox, cucBox;

  @FXML
  private VBox itemBox;

  public void initialize()
  {
    System.out.println("POGTC");
    ArrayList<String> daysOfTheWeek = new ArrayList<String>();
    daysOfTheWeek.add("Sunday");
    daysOfTheWeek.add("Monday");
    daysOfTheWeek.add("Tuesday");
    daysOfTheWeek.add("Wednesday");
    daysOfTheWeek.add("Thursday");
    daysOfTheWeek.add("Friday");
    daysOfTheWeek.add("Saturday");
    orderingOnChoice.setItems(FXCollections.observableArrayList(daysOfTheWeek));
    orderingOnChoice.setValue("Wednesday");
    orderingOnChoice.setOnAction(new EventHandler<ActionEvent>()
    {
      @Override
      public void handle(ActionEvent arg0)
      {
        firstShiftProduceWillBeUsed = JimmyCalendarUtil
            .convertToShiftNumber(JimmyCalendarUtil.getAMShiftFor(orderingOnChoice.getValue()) + 4);
        if (orderingOnChoice.getValue() != null && orderNextOn.getValue() != null)
          updateProjections();
      }
    });
    orderNextOn.setItems(FXCollections.observableArrayList(daysOfTheWeek));
    orderNextOn.setValue("Friday");
    orderNextOn.setOnAction(new EventHandler<ActionEvent>()
    {
      @Override
      public void handle(ActionEvent arg0)
      {
        lastShiftProduceWillBeNeededFor = JimmyCalendarUtil
            .convertToShiftNumber(JimmyCalendarUtil.getAMShiftFor(orderNextOn.getValue()) + 3);
        if (orderingOnChoice.getValue() != null && orderNextOn.getValue() != null)
          updateProjections();
      }
    });
    // Minimums
    int nextAMShift = JimmyCalendarUtil.getNextAMShift();
    letMinField
        .setText(AppDirector.dataHub.getProduceRequiredForShifts(nextAMShift,
            JimmyCalendarUtil.convertToShiftNumber(nextAMShift + 3), "Lettuce", 24) + "");
    tomMinField
        .setText(AppDirector.dataHub.getProduceRequiredForShifts(nextAMShift,
            JimmyCalendarUtil.convertToShiftNumber(nextAMShift + 3), "Tomatoes", 25) + "");
    onMinField
        .setText(AppDirector.dataHub.getProduceRequiredForShifts(nextAMShift,
            JimmyCalendarUtil.convertToShiftNumber(nextAMShift + 3), "Onions", 50) + "");
    celMinField
        .setText(AppDirector.dataHub.getProduceRequiredForShifts(nextAMShift,
            JimmyCalendarUtil.convertToShiftNumber(nextAMShift + 3), "Celery", 1) + "");

    // Proj
    projections = AppDirector.dataHub.getProjectionsForShifts(firstShiftProduceWillBeUsed,
        lastShiftProduceWillBeNeededFor);
    projField.setText(String.format("%.2f", projections));
    
    letBox = new ProduceOrderBox("Lettuce", 24, projections, "ea", "Case(s) of Iceburg Lettuce");
    tomBox = new ProduceOrderBox("Tomatoes", 25, projections, "lbs",
        "25lb Box(es) of Roma Tomatoes");
    onBox = new ProduceOrderBox("Onions", 50, projections, "lbs", "50lb Bag(s) of Onions");
    celBox = new ProduceOrderBox("Celery", 1, projections, "ea", "Stalk(s) of Celery");
    cucBox = new ProduceOrderBox("Cucumbers", 1, projections, "lbs", "Lbs of Cucumbers");
    
    itemBox.getChildren().addAll(letBox, tomBox, onBox, celBox, cucBox);
    System.out.println("POGTC-");
  }

  protected void updateProjections()
  {
    // Minimums
    int nextAMShift = JimmyCalendarUtil.getNextAMShift();
    letMinField
        .setText(AppDirector.dataHub.getProduceRequiredForShifts(nextAMShift,
            JimmyCalendarUtil.convertToShiftNumber(nextAMShift + 3), "Lettuce", 24) + "");
    tomMinField
        .setText(AppDirector.dataHub.getProduceRequiredForShifts(nextAMShift,
            JimmyCalendarUtil.convertToShiftNumber(nextAMShift + 3), "Tomatoes", 25) + "");
    onMinField
        .setText(AppDirector.dataHub.getProduceRequiredForShifts(nextAMShift,
            JimmyCalendarUtil.convertToShiftNumber(nextAMShift + 3), "Onions", 50) + "");
    celMinField
        .setText(AppDirector.dataHub.getProduceRequiredForShifts(nextAMShift,
            JimmyCalendarUtil.convertToShiftNumber(nextAMShift + 3), "Celery", 1) + "");

    // Proj
    projections = AppDirector.dataHub.getProjectionsForShifts(firstShiftProduceWillBeUsed,
        lastShiftProduceWillBeNeededFor);
    projField.setText(String.format("%.2f", projections));

    //UpdateProj
    letBox.updateProjections(projections);
    tomBox.updateProjections(projections);
    onBox.updateProjections(projections);
    celBox.updateProjections(projections);
    cucBox.updateProjections(projections);
  }

}
