package controllers;

import java.util.ArrayList;

import app.AppDirector;
import gui.GuiUtilFactory;
import gui.TruckOrderHBox;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lineitems.UPKItem;
import util.JimmyCalendarUtil;

public class TruckOrderGuideTabController
{
  @FXML
  private ChoiceBox<String> orderGuideCategoryChoice = new ChoiceBox<String>(),
      orderingOnChoice = new ChoiceBox<String>(), forDeliveryOnChoice = new ChoiceBox<String>();

  @FXML
  private VBox truckOrderGuideVBox;

  @FXML
  private GridPane grid;

  @FXML
  private TextField projField;

  private double weekProj = AppDirector.dataHub.getWeekProj();

  public void initialize()
  {
    projField.setText(String.format("%.2f", weekProj));

    ArrayList<String> daysOfTheWeek = new ArrayList<String>();
    daysOfTheWeek.add("Sunday");
    daysOfTheWeek.add("Monday");
    daysOfTheWeek.add("Tuesday");
    daysOfTheWeek.add("Wednesday");
    daysOfTheWeek.add("Thursday");
    daysOfTheWeek.add("Friday");
    daysOfTheWeek.add("Saturday");
    orderingOnChoice.setItems(FXCollections.observableArrayList(daysOfTheWeek));
    orderingOnChoice.setOnAction(new EventHandler<ActionEvent>()
    {
      @Override
      public void handle(ActionEvent arg0)
      {
        if (orderingOnChoice.getValue() != null && forDeliveryOnChoice.getValue() != null)
        {
          figureNewProjections();
        }
      }
    });
    orderingOnChoice.setValue("Monday");

    forDeliveryOnChoice.setItems(FXCollections.observableArrayList(daysOfTheWeek));
    forDeliveryOnChoice.setOnAction(new EventHandler<ActionEvent>()
    {
      @Override
      public void handle(ActionEvent arg0)
      {
        if (orderingOnChoice.getValue() != null && forDeliveryOnChoice.getValue() != null)
        {
          figureNewProjections();
        }
      }
    });
    forDeliveryOnChoice.setValue("Wednesday");

    ArrayList<String> categories = new ArrayList<String>();
    categories.add("Bread");
    categories.add("Food");
    categories.add("Sides");
    categories.add("Paper");
    categories.add("Produce");
    categories.add("Beverage");
    categories.add("Catering");
    orderGuideCategoryChoice.setItems(FXCollections.observableArrayList(categories));

    orderGuideCategoryChoice.setValue("Select a Category");
    orderGuideCategoryChoice.setOnAction(new EventHandler<ActionEvent>()
    {

      @Override
      public void handle(ActionEvent arg0)
      {
        if (orderGuideCategoryChoice.getValue() != null)
        {
          refreshItems();
        }
      }
    });
    HBox thb = GuiUtilFactory.createTruckOrderHBoxTitle();
    thb.setPadding(new Insets(15, 0, 0, 0));
    grid.add(thb, 0, 1, 2, 1);
    System.out.println("TOGTC");
  }

  protected void refreshItems()
  {
    truckOrderGuideVBox.getChildren().clear();
    for (UPKItem item : AppDirector.dataHub.getLastCompletedWeekUPKWeek().getItems())
    {
      if (item.getCategory().equals(orderGuideCategoryChoice.getValue()))
      {
        TruckOrderHBox toh = new TruckOrderHBox(item,
            AppDirector.dataHub.getLastCompletedWeekUPKWeek().getAdjustedSales(), weekProj);
        truckOrderGuideVBox.getChildren().add(toh);
      }
    }
  }

  protected void figureNewProjections()
  {
    double week = AppDirector.dataHub.getWeekProj();
    double extra = AppDirector.dataHub.getProjectionsForShifts(
        JimmyCalendarUtil.getAMShiftFor(orderingOnChoice.getValue()),
        JimmyCalendarUtil.getAMShiftFor(forDeliveryOnChoice.getValue()));
    weekProj = week + extra;
    projField.setText(String.format("%.2f", weekProj));
    for (Node n : truckOrderGuideVBox.getChildren())
    {
      ((TruckOrderHBox) n).refreshProjections(weekProj);
    }
  }

}
