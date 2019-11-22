package controllers;

import app.MainApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import readers.TrendSheetMap;
import util.JimmyCalendarUtil;

public class PeriodFoldController
{
  @FXML
  private PieChart periodChart;

  public void initialize()
  {

  }

  public void updateAll()
  {
    TrendSheetMap current = MainApplication.dataHub.getCurrentYearTrendSheet();
    double other = 100 - (current.getDataForCategoryForCurrentPeriod(TrendSheetMap.BREADP)
        + current.getDataForCategoryForCurrentPeriod(TrendSheetMap.FOODP)
        + current.getDataForCategoryForCurrentPeriod(TrendSheetMap.SIDESP)
        + current.getDataForCategoryForCurrentPeriod(TrendSheetMap.PAPERP)
        + current.getDataForCategoryForCurrentPeriod(TrendSheetMap.PRODUCEP)
        + current.getDataForCategoryForCurrentPeriod(TrendSheetMap.BEVERAGEP)
        + current.getDataForCategoryForCurrentPeriod(TrendSheetMap.CY_LABORP));
    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
        new PieChart.Data(TrendSheetMap.BREADP,
            current.getDataForCategoryForCurrentPeriod(TrendSheetMap.BREADP)),
        new PieChart.Data(TrendSheetMap.FOODP,
            current.getDataForCategoryForCurrentPeriod(TrendSheetMap.FOODP)),
        new PieChart.Data(TrendSheetMap.SIDESP,
            current.getDataForCategoryForCurrentPeriod(TrendSheetMap.SIDESP)),
        new PieChart.Data(TrendSheetMap.PAPERP,
            current.getDataForCategoryForCurrentPeriod(TrendSheetMap.PAPERP)),
        new PieChart.Data(TrendSheetMap.PRODUCEP,
            current.getDataForCategoryForCurrentPeriod(TrendSheetMap.PRODUCEP)),
        new PieChart.Data(TrendSheetMap.BEVERAGEP,
            current.getDataForCategoryForCurrentPeriod(TrendSheetMap.BEVERAGEP)),
        new PieChart.Data(TrendSheetMap.CY_LABORP,
            current.getDataForCategoryForCurrentPeriod(TrendSheetMap.CY_LABORP)),
        new PieChart.Data("Other", other));

    periodChart.setData(pieChartData);
  }
}
