package controllers;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import app.AppDirector;
import gui.GuiUtilFactory;
import gui.UsageAnalysisHBox;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import lineitems.UPKItem;
import util.JimmyCalendarUtil;

public class UsageAnalysisTabController
{
  @FXML
  private ChoiceBox<String> usageAnalysisCategoryChoice;

  @FXML
  private VBox usageAnalysisVBox;

  @FXML
  private RadioButton usageAnalysisActualUsageRadio, usageAnalysisTheoreticalUsageRadio,
      usageAnalysisActualUPKRadio, usageAnalysisAverageUPKRadio;

  @FXML
  private ScrollPane usageAnalysisGraphPane;

  @FXML
  private GridPane gp;

  private UsageAnalysisHBox currentlySelectedUAH = null;
  private ArrayList<RadioButton> usageAnalysisCategoryGroup;

  public void initialize()
  {
    // Usage Analysis
    ArrayList<String> categories = new ArrayList<String>();
    categories.add("Bread");
    categories.add("Food");
    categories.add("Sides");
    categories.add("Paper");
    categories.add("Produce");
    categories.add("Beverage");
    categories.add("Catering");
    usageAnalysisCategoryChoice.setItems(FXCollections.observableArrayList(categories));
    usageAnalysisCategoryChoice.setValue("Select a Category");
    usageAnalysisCategoryChoice.setOnAction(new EventHandler<ActionEvent>()
    {

      @Override
      public void handle(ActionEvent arg0)
      {
        if (usageAnalysisCategoryChoice.getValue() != null)
        {
          usageAnalysisVBox.getChildren().clear();
          gp.add(GuiUtilFactory.createUsageAnalysisHBoxTitle(), 0, 1, 2, 1);
          // Iterate through upk items
          for (UPKItem item : AppDirector.dataHub.getLastCompletedWeekUPKWeek()
              .getItemsOfCategory(usageAnalysisCategoryChoice.getValue()))
          {
            UsageAnalysisHBox uah = new UsageAnalysisHBox(item,
                AppDirector.dataHub.getLastCompletedWeekUPKWeek().getAdjustedSales());
            uah.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
              @Override
              public void handle(MouseEvent arg0)
              {
                currentlySelectedUAH = uah;
                handleNewUsageAnalysisCategorySelection();
              }
            });
            usageAnalysisVBox.getChildren().add(uah);
          }
        }
      }
    });
    usageAnalysisCategoryGroup = new ArrayList<RadioButton>();
    usageAnalysisCategoryGroup.add(usageAnalysisActualUsageRadio);
    usageAnalysisCategoryGroup.add(usageAnalysisTheoreticalUsageRadio);
    usageAnalysisCategoryGroup.add(usageAnalysisActualUPKRadio);
    usageAnalysisCategoryGroup.add(usageAnalysisAverageUPKRadio);
    System.out.println("UATC");
  }

  private void handleNewUsageAnalysisCategorySelection()
  {
    if (currentlySelectedUAH != null)
    {
      int weekNumber = JimmyCalendarUtil.getWeekNumber(new GregorianCalendar());
      LineChart<Number, Number> chart = GuiUtilFactory
          .createUsageAnalysisLineChart(currentlySelectedUAH, weekNumber);
      for (RadioButton rb : usageAnalysisCategoryGroup)
      {
        if (rb.isSelected())
        {
          chart.getData().add(GuiUtilFactory
              .getUPKSeriesFor(currentlySelectedUAH.getItem().getName(), weekNumber, rb.getText()));
        }
      }
      usageAnalysisGraphPane.setContent(chart);
    }
  }

  @FXML
  void usageAnalysisActualUsageRadioClicked()
  {
    handleNewUsageAnalysisCategorySelection();
  }

  @FXML
  void usageAnalysisTheoreticalUsageRadioClicked()
  {
    handleNewUsageAnalysisCategorySelection();
  }

  @FXML
  void usageAnalysisActualUPKRadioClicked()
  {
    handleNewUsageAnalysisCategorySelection();
  }

  @FXML
  void usageAnalysisAverageUPKRadioClicked()
  {
    handleNewUsageAnalysisCategorySelection();
  }

}
