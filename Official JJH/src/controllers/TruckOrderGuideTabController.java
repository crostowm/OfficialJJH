package controllers;

import java.util.ArrayList;

import app.MainApplication;
import gui.GuiUtilFactory;
import gui.TruckOrderHBox;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class TruckOrderGuideTabController
{
  @FXML
  private ChoiceBox<String> orderGuideCategoryChoice = new ChoiceBox<String>(),
      orderingOnChoice = new ChoiceBox<String>(), forDeliveryOnChoice = new ChoiceBox<String>();

  @FXML
  private VBox truckOrderGuideVBox;

  private TruckOrderHBox currentlySelectedTOH = null;

  public void initialize()
  {
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
          int numDiff;
          if (orderingOnChoice.getItems()
              .indexOf(orderingOnChoice.getValue()) == forDeliveryOnChoice.getItems()
                  .indexOf(forDeliveryOnChoice.getValue()))
            numDiff = 0;
          else if (orderingOnChoice.getItems()
              .indexOf(orderingOnChoice.getValue()) > forDeliveryOnChoice.getItems()
                  .indexOf(forDeliveryOnChoice.getValue()))
            numDiff = (7 - orderingOnChoice.getItems().indexOf(orderingOnChoice.getValue()))
                + forDeliveryOnChoice.getItems().indexOf(forDeliveryOnChoice.getValue());
          else
            numDiff = forDeliveryOnChoice.getItems().indexOf(forDeliveryOnChoice.getValue())
                - orderingOnChoice.getItems().indexOf(orderingOnChoice.getValue());
          System.out.println(numDiff + " " + currentlySelectedTOH);
        }
      }
    });
    forDeliveryOnChoice.setItems(FXCollections.observableArrayList(daysOfTheWeek));

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
          truckOrderGuideVBox.getChildren().clear();
          truckOrderGuideVBox.getChildren().add(GuiUtilFactory.createUsageAnalysisHBoxTitle());
          int category = -1;
          switch (orderGuideCategoryChoice.getValue())
          {
            case "Bread":
              category = 1;
              break;
            case "Food":
              category = 2;
              break;
            case "Sides":
              category = 3;
              break;
            case "Paper":
              category = 4;
              break;
            case "Produce":
              category = 5;
              break;
            case "Beverage":
              category = 6;
              break;
            case "Catering":
              category = 7;
              break;
          }
          // Iterate through upk items
          for (String name : MainApplication.dataHub.getCurrentUPKMap().get(category).keySet())
          {
            if (!name.equals("COGs"))
            {
              TruckOrderHBox toh = new TruckOrderHBox(category,
                  MainApplication.dataHub.getCurrentUPKMap().getAdjustedSales(), name,
                  MainApplication.dataHub.getCurrentUPKMap().get(category).get(name));
              toh.setOnMouseClicked(new EventHandler<MouseEvent>()
              {
                @Override
                public void handle(MouseEvent arg0)
                {
                  currentlySelectedTOH = toh;
                  handleNewTruckOrderCategorySelection();
                }
              });
              truckOrderGuideVBox.getChildren().add(toh);
            }
          }
        }
      }
    });
  }

  private void handleNewTruckOrderCategorySelection()
  {

  }

}
