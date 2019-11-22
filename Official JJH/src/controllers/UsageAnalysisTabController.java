package controllers;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import app.MainApplication;
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
import javafx.scene.layout.VBox;
import readers.UPKMap;
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
          usageAnalysisVBox.getChildren().add(GuiUtilFactory.createUsageAnalysisHBoxTitle());
          int category = -1;
          switch (usageAnalysisCategoryChoice.getValue())
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
          for (String name : MainApplication.dataHub.getCurrentUPKMap().getUPKMap().get(category).keySet())
          {
            if (!name.equals("COGs"))
            {
              UsageAnalysisHBox uah = new UsageAnalysisHBox(category,
                  MainApplication.dataHub.getCurrentUPKMap().getAdjustedSales(), name,
                  MainApplication.dataHub.getCurrentUPKMap().getUPKMap().get(category).get(name));
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
      }
    });
    usageAnalysisCategoryGroup = new ArrayList<RadioButton>();
    usageAnalysisCategoryGroup.add(usageAnalysisActualUsageRadio);
    usageAnalysisCategoryGroup.add(usageAnalysisTheoreticalUsageRadio);
    usageAnalysisCategoryGroup.add(usageAnalysisActualUPKRadio);
    usageAnalysisCategoryGroup.add(usageAnalysisAverageUPKRadio);
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
          switch (rb.getText())
          {
            case "Actual Usage":
              chart.getData()
                  .add(GuiUtilFactory.getUPKSeriesFor(currentlySelectedUAH,
                      MainApplication.dataHub.getPast5UPKMaps(), weekNumber, rb.getText(),
                      UPKMap.ACTUAL_USAGE));
              break;
            case "Theoretical Usage":
              chart.getData()
                  .add(GuiUtilFactory.getUPKSeriesFor(currentlySelectedUAH,
                      MainApplication.dataHub.getPast5UPKMaps(), weekNumber, rb.getText(),
                      UPKMap.THEORETICAL_USAGE));
              break;
            case "Actual UPK":
              chart.getData()
                  .add(GuiUtilFactory.getUPKSeriesFor(currentlySelectedUAH,
                      MainApplication.dataHub.getPast5UPKMaps(), weekNumber, rb.getText(),
                      UPKMap.ACTUAL_UPK));
              break;
            case "Average UPK":
              chart.getData()
                  .add(GuiUtilFactory.getUPKSeriesFor(currentlySelectedUAH,
                      MainApplication.dataHub.getPast5UPKMaps(), weekNumber, rb.getText(),
                      UPKMap.AVERAGE_UPK));
              break;
          }
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
