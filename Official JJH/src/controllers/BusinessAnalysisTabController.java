package controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;

import app.MainApplication;
import gui.GuiUtilFactory;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import util.JimmyCalendarUtil;

public class BusinessAnalysisTabController
{

  @FXML
  private ScrollPane contentScrollPane;

  @FXML
  private FlowPane categoryBox;

  @FXML
  private ChoiceBox<String> reportChoice;

  @FXML
  private ToggleGroup rangeGroup;

  @FXML
  private RadioButton byShiftRadio;

  @FXML
  private RadioButton byDayRadio;

  @FXML
  private RadioButton byWeekRadio;

  private ArrayList<RadioButton> categoryRadioButtons = new ArrayList<RadioButton>();

  public void initialize()
  {
    reportChoice.setItems(FXCollections.observableArrayList("Weekly Sales Report", "Trend Sheet"));
    reportChoice.setOnAction(new EventHandler<ActionEvent>()
    {
      @Override
      public void handle(ActionEvent ae)
      {
        handleNewReportSelection(reportChoice.getValue());
      }
    });
    System.out.println("BATC");
  }

  protected void handleNewReportSelection(String value)
  {
    // Reset For New Report
    categoryRadioButtons.clear();
    categoryBox.getChildren().clear();
    byShiftRadio.setSelected(false);
    byDayRadio.setSelected(false);
    byWeekRadio.setSelected(false);
    switch (value)
    {
      // WSR
      case "Weekly Sales Report":
        contentScrollPane.setContent(null);
        ArrayList<String> itemNames = new ArrayList<String>(
            MainApplication.dataHub.getProjectionWSR(4).getMap().keySet());
        Collections.sort(itemNames);
        EventHandler<ActionEvent> wsrEvent = new EventHandler<ActionEvent>()
        {
          @Override
          public void handle(ActionEvent arg0)
          {
            contentScrollPane.setContent(null);
            categoryRadioButtons.clear();
            categoryBox.getChildren().clear();
            for (String item : itemNames)
            {
              RadioButton rb = new RadioButton(item);
              rb.setPrefWidth(150);
              rb.setMinWidth(Region.USE_PREF_SIZE);
              rb.setOnAction(new EventHandler<ActionEvent>()
              {
                @Override
                public void handle(ActionEvent arg0)
                {
                  generateChart();
                }
              });
              categoryRadioButtons.add(rb);
              categoryBox.getChildren().add(rb);
            }
          }
        };
        byShiftRadio.setText("By Shift");
        byShiftRadio.setVisible(true);
        byShiftRadio.setOnAction(wsrEvent);
        byDayRadio.setText("By Day");
        byDayRadio.setVisible(true);
        byDayRadio.setOnAction(wsrEvent);
        byWeekRadio.setText("By Week");
        byWeekRadio.setVisible(true);
        byWeekRadio.setOnAction(wsrEvent);
        break;
      // Trend Sheet
      case "Trend Sheet":
        contentScrollPane.setContent(null);
        byShiftRadio.setText("By Week");
        byShiftRadio.setVisible(true);
        byShiftRadio.setOnAction(new EventHandler<ActionEvent>()
        {
          @Override
          public void handle(ActionEvent arg0)
          {
            contentScrollPane.setContent(null);
            categoryRadioButtons.clear();
            categoryBox.getChildren().clear();
            // Main Difference For Period/Week
            ArrayList<String> itemNamesTS = new ArrayList<String>(
                MainApplication.dataHub.getCurrentYearTrendSheet().getWeeklyItems());
            Collections.sort(itemNamesTS);
            for (String item : itemNamesTS)
            {
              RadioButton rb = new RadioButton(item);
              rb.setMinWidth(Region.USE_PREF_SIZE);
              rb.setOnAction(new EventHandler<ActionEvent>()
              {
                @Override
                public void handle(ActionEvent arg0)
                {
                  generateChart();
                }
              });
              categoryRadioButtons.add(rb);
              categoryBox.getChildren().add(rb);
            }
          }
        });
        byDayRadio.setText("By Period");
        byDayRadio.setVisible(true);
        byDayRadio.setOnAction(new EventHandler<ActionEvent>()
        {
          @Override
          public void handle(ActionEvent arg0)
          {
            contentScrollPane.setContent(null);
            categoryRadioButtons.clear();
            categoryBox.getChildren().clear();
            // Main Difference For Period/Week
            ArrayList<String> itemNamesTS = new ArrayList<String>(
                MainApplication.dataHub.getCurrentYearTrendSheet().getPeriodItems());
            Collections.sort(itemNamesTS);
            for (String item : itemNamesTS)
            {
              RadioButton rb = new RadioButton(item);
              rb.setMinWidth(Region.USE_PREF_SIZE);
              rb.setOnAction(new EventHandler<ActionEvent>()
              {
                @Override
                public void handle(ActionEvent arg0)
                {
                  generateChart();
                }
              });
              categoryRadioButtons.add(rb);
              categoryBox.getChildren().add(rb);
            }
          }
        });
        byWeekRadio.setVisible(false);
        break;
    }
  }

  protected void generateChart()
  {
    LineChart<Number, Number> numberChart = null;
    ArrayList<Number> xs = new ArrayList<Number>();
    ArrayList<String> names = new ArrayList<String>();
    ArrayList<ArrayList<Number>> allYs = new ArrayList<ArrayList<Number>>();
    switch (((RadioButton) rangeGroup.getSelectedToggle()).getText())
    {
      case "By Shift":
        for (int ii = 1; ii < 15; ii++)
        {
          xs.add(ii);
        }
        break;
      case "By Day":
        for (int ii = 1; ii < 15; ii += 2)
        {
          xs.add(ii);
        }
        break;
      case "By Week":
        // Choose if wsr do last 4 weeks
        if (reportChoice.getValue().equals("Weekly Sales Report"))
        {
          for (int ii = JimmyCalendarUtil.getWeekNumber(new GregorianCalendar())
              - 4; ii < JimmyCalendarUtil.getWeekNumber(new GregorianCalendar()); ii++)
          {
            xs.add(ii);
          }
        }
        else
        {
          for (int ii = 1; ii < JimmyCalendarUtil.getWeekNumber(new GregorianCalendar()); ii++)
          {
            xs.add(ii);
          }
        }
        break;
      case "By Period":
        for (int ii = 1; ii < JimmyCalendarUtil
            .getPeriodNumber(JimmyCalendarUtil.getWeekNumber(new GregorianCalendar())); ii++)
        {
          xs.add(ii);
        }
        break;
    }
    switch (reportChoice.getValue())
    {
      case "Weekly Sales Report":
        for (RadioButton r : categoryRadioButtons)
        {
          if (r.isSelected())
          {
            ArrayList<Number> ys = new ArrayList<Number>();
            names.add(r.getText());
            for (Number x : xs)
            {
              // By Day
              if (((RadioButton) rangeGroup.getSelectedToggle()).getText().equals("By Day"))
              {
                ys.add(MainApplication.dataHub.getProjectionWSR(4).getDataForShift(r.getText(),
                    (int) x)
                    + MainApplication.dataHub.getProjectionWSR(4).getDataForShift(r.getText(),
                        ((int) x) + 1));
              }
              //By Shift
              else if (((RadioButton) rangeGroup.getSelectedToggle()).getText().equals("By Shift"))
                ys.add(MainApplication.dataHub.getProjectionWSR(4).getDataForShift(r.getText(),
                    (int) x));
            }
            // By Week
            if (((RadioButton) rangeGroup.getSelectedToggle()).getText().equals("By Week"))
            {
              for (int ii = 1; ii < 5; ii++)
              {
                ys.add(MainApplication.dataHub.getProjectionWSR(ii).getSummaryForItem(r.getText()));
              }
            }
            allYs.add(ys);
          }
        }
        numberChart = GuiUtilFactory.createNewPopulatedBusinessAnalysisNumberChart(
            (((RadioButton) rangeGroup.getSelectedToggle()).getText().split(" "))[1], "$$",
            "Weekly Sales Analysis " + ((RadioButton) rangeGroup.getSelectedToggle()).getText(),
            names, xs, allYs);
        break;
      case "Trend Sheet":
        for (RadioButton r : categoryRadioButtons)
        {
          if (r.isSelected())
          {
            ArrayList<Number> ys = new ArrayList<Number>();
            names.add(r.getText());
            for (Number x : xs)
            {
              if (((RadioButton) rangeGroup.getSelectedToggle()).getText().equals("By Week"))
                ys.add(MainApplication.dataHub.getCurrentYearTrendSheet()
                    .getDataForCategoryForWeek(r.getText(), (int) x));
              else if (((RadioButton) rangeGroup.getSelectedToggle()).getText().equals("By Period"))
                ys.add(MainApplication.dataHub.getCurrentYearTrendSheet()
                    .getDataForCategoryForPeriod(r.getText(), (int) x));
            }
            allYs.add(ys);
          }
        }
        numberChart = GuiUtilFactory.createNewPopulatedBusinessAnalysisNumberChart(
            (((RadioButton) rangeGroup.getSelectedToggle()).getText().split(" "))[1], "",
            "Trend Sheet Analysis " + ((RadioButton) rangeGroup.getSelectedToggle()).getText(),
            names, xs, allYs);
        break;
    }
    contentScrollPane.setContent(numberChart);
  }
}
