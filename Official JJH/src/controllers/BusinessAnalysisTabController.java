package controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;

import app.AppDirector;
import gui.GuiUtilFactory;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import selenium.ReportGrabber;
import util.JimmyCalendarUtil;
import util.ReportFinder;

public class BusinessAnalysisTabController
{

  @FXML
  private Label periodLabel;

  @FXML
  private ScrollPane contentScrollPane;

  @FXML
  private FlowPane categoryBox;

  @FXML
  private ChoiceBox<String> reportChoice, rangeBox;

  @FXML
  private ChoiceBox<Integer> periodBox;

  @FXML
  private Button downloadButton;

  public void initialize()
  {
    System.out.println("BATC");
    reportChoice.setItems(FXCollections.observableArrayList("Weekly Sales Report", "Trend Sheet"));
    reportChoice.setOnAction(new EventHandler<ActionEvent>()
    {
      @Override
      public void handle(ActionEvent ae)
      {
        handleNewReportSelection();
      }
    });
    rangeBox.setOnAction(new EventHandler<ActionEvent>()
    {
      @Override
      public void handle(ActionEvent arg0)
      {
        handleNewRangeSelection();
      }
    });
    reportChoice.setValue("Weekly Sales Report");
    handleNewReportSelection();
    System.out.println("BATC-");
  }

  protected void handleNewReportSelection()
  {
    // Reset For New Report
    categoryBox.getChildren().clear();
    contentScrollPane.setContent(null);
    switch (reportChoice.getValue())
    {
      // WSR
      case "Weekly Sales Report":
        rangeBox.setItems(FXCollections.observableArrayList("By Shift", "By Day", "By Week"));
        periodBox.setItems(FXCollections.observableArrayList(
            JimmyCalendarUtil.normalizeWeekIndex(JimmyCalendarUtil.getCurrentWeek() - 4),
            JimmyCalendarUtil.normalizeWeekIndex(JimmyCalendarUtil.getCurrentWeek() - 3),
            JimmyCalendarUtil.normalizeWeekIndex(JimmyCalendarUtil.getCurrentWeek() - 2),
            JimmyCalendarUtil.normalizeWeekIndex(JimmyCalendarUtil.getCurrentWeek() - 1)));
        periodBox.setOnAction(new EventHandler<ActionEvent>()
        {
          @Override
          public void handle(ActionEvent arg0)
          {
            generateChart();
          }
        });
        break;
      // Trend Sheet
      case "Trend Sheet":
        rangeBox.setItems(FXCollections.observableArrayList("By Week", "By Period"));

        GregorianCalendar gc = new GregorianCalendar();
        periodBox.getItems().clear();
        for (int ii = 0; ii < 10; ii++)
        {
          periodBox.getItems().add(gc.get(Calendar.YEAR) - ii);
        }
        periodBox.setOnAction(new EventHandler<ActionEvent>()
        {
          @Override
          public void handle(ActionEvent arg0)
          {
            if (AppDirector.dataHub.getTrendSheetForYear(periodBox.getValue()) == null)
              downloadButton.setVisible(true);
            else
            generateChart();
          }
        });
        break;
    }
  }

  protected void handleNewRangeSelection()
  {
    contentScrollPane.setContent(null);
    categoryBox.getChildren().clear();

    if ((reportChoice.getValue().equals("Weekly Sales Report")
        && rangeBox.getValue().equals("By Week"))
        || (reportChoice.getValue().equals("Trend Sheet") && rangeBox.getValue().equals("By Year")))
      periodBox.setVisible(false);
    else
      periodBox.setVisible(true);

    ArrayList<String> itemNamesTS;
    switch (reportChoice.getValue())
    {
      case "Weekly Sales Report":
        itemNamesTS = new ArrayList<String>(
            AppDirector.dataHub.getProjectionWSRIndex(0).getMap().keySet());
        break;
      case "Trend Sheet":
        switch (rangeBox.getValue())
        {
          case "By Week":
            itemNamesTS = new ArrayList<String>(
                AppDirector.dataHub.getCurrentYearTrendSheet().getWeeklyItems());
            break;
          case "By Period":
            itemNamesTS = new ArrayList<String>(
                AppDirector.dataHub.getCurrentYearTrendSheet().getPeriodItems());
            break;
          default:
            itemNamesTS = new ArrayList<String>();
            break;
        }
        break;
      default:
        itemNamesTS = new ArrayList<String>();
        break;
    }

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
      categoryBox.getChildren().add(rb);
    }
  }

  protected void generateChart()
  {
    if (reportSelected() && rangeBox.getValue() != null && periodSelected() && categorySelected()
        || (reportChoice.getValue().equals("Weekly Sales Report")
            && rangeBox.getValue().equals("By Week"))
        || (reportChoice.getValue().equals("Trend Sheet") && rangeBox.getValue().equals("By Year")))
    {
      LineChart<Number, Number> numberChart = null;
      ArrayList<Number> xs = new ArrayList<Number>();
      ArrayList<String> names = new ArrayList<String>();
      ArrayList<ArrayList<Number>> allYs = new ArrayList<ArrayList<Number>>();
      switch (rangeBox.getValue())
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
            for (int ii = JimmyCalendarUtil.getCurrentWeekNumber() - 4; ii < JimmyCalendarUtil
                .getCurrentWeekNumber(); ii++)
            {
              xs.add(ii);
            }
          }
          else
          {
            if (periodBox.getValue() == new GregorianCalendar().get(Calendar.YEAR))
            {
              for (int ii = 1; ii < JimmyCalendarUtil.getWeekNumber(new GregorianCalendar()); ii++)
              {
                xs.add(ii);
              }
            }
            else
            {
              for (int ii = 1; ii < 53; ii++)
              {
                xs.add(ii);
              }
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
          int week = periodBox.getItems().indexOf(periodBox.getValue()) + 1;
          for (Node n : categoryBox.getChildren())
          {
            RadioButton r = (RadioButton) n;
            if (r.isSelected())
            {
              ArrayList<Number> ys = new ArrayList<Number>();
              names.add(r.getText());
              for (Number x : xs)
              {
                switch (rangeBox.getValue())
                {
                  case "By Day":
                    ys.add(AppDirector.dataHub.getProjectionWSRWeek(week)
                        .getDataForShift(r.getText(), (int) x)
                        + AppDirector.dataHub.getProjectionWSRWeek(week)
                            .getDataForShift(r.getText(), ((int) x) + 1));
                    break;
                  case "By Shift":
                    ys.add(AppDirector.dataHub.getProjectionWSRWeek(week)
                        .getDataForShift(r.getText(), (int) x));
                    break;
                  case "By Week":
                    for (int ii = 1; ii < 5; ii++)
                    {
                      ys.add(AppDirector.dataHub.getProjectionWSRWeek(ii)
                          .getSummaryForItem(r.getText()));
                      System.out.println("Summary: " + AppDirector.dataHub
                          .getProjectionWSRWeek(ii).getSummaryForItem(r.getText()));
                    }
                    break;
                }
              }
              allYs.add(ys);
            }
          }
          numberChart = GuiUtilFactory.createNewPopulatedBusinessAnalysisNumberChart(
              rangeBox.getValue().split(" ")[1], "", "Weekly Sales Analysis", names, xs, allYs);
          break;
        case "Trend Sheet":
          for (Node n : categoryBox.getChildren())
          {
            RadioButton r = (RadioButton) n;
            if (r.isSelected())
            {
              ArrayList<Number> ys = new ArrayList<Number>();
              names.add(r.getText());
              for (Number x : xs)
              {
                switch (rangeBox.getValue())
                {
                  case "By Week":
                    ys.add(AppDirector.dataHub.getTrendSheetForYear(periodBox.getValue())
                        .getDataForCategoryForWeek(r.getText(), (int) x));
                    break;
                  case "By Period":
                    ys.add(AppDirector.dataHub.getTrendSheetForYear(periodBox.getValue())
                        .getDataForCategoryForPeriod(r.getText(), (int) x));
                    break;
                }
              }
              allYs.add(ys);
            }
          }
          numberChart = GuiUtilFactory.createNewPopulatedBusinessAnalysisNumberChart(
              rangeBox.getValue().split(" ")[1], "", "Trend Sheet Analysis", names, xs, allYs);
          break;
      }
      numberChart.setStyle("-fx-text-fill: black;");
      contentScrollPane.setContent(numberChart);
    }
    else
    {

    }
  }

  private boolean categorySelected()
  {
    if (categoryBox.getChildren().size() > 0)
    {
      for (Node n : categoryBox.getChildren())
      {
        if (((RadioButton) n).isSelected())
          return true;
      }
    }
    return false;
  }

  private boolean periodSelected()
  {
    return periodBox.getValue() != null;
  }

  private boolean reportSelected()
  {
    return reportChoice.getValue() != null;
  }

  public void newSelectionMade(String type)
  {
    switch (type)
    {
      case "Period":
        generateChart();
        break;
      case "Range":
        break;
      case "Type":
        break;
    }
  }

  @FXML
  public void downloadButtonPressed()
  {
    switch (downloadButton.getText())
    {
      case "Download":
        ReportGrabber rg = new ReportGrabber();
        rg.startAndLogin();
        rg.downloadTrendSheet(periodBox.getValue());
        rg.goToDownloadCenterAndDownloadAll();
        periodBox.setDisable(true);
        rangeBox.setDisable(true);
        reportChoice.setDisable(true);
        downloadButton.setText("Download Complete");
        break;
      case "Download Complete":
        ReportFinder rf = new ReportFinder(AppDirector.config.getDownloadFolderPath());
        rf.uploadLastTrendSheet(periodBox.getValue());
        periodBox.setDisable(false);
        rangeBox.setDisable(false);
        reportChoice.setDisable(false);
        downloadButton.setText("Download");
        downloadButton.setVisible(false);
        break;
    }
  }
}
