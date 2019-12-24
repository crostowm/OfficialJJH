package controllers;

import app.MainApplication;
import error_handling.ErrorHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import readers.TrendSheetReader;

public class PeriodFoldController
{
  @FXML
  private PieChart periodChart;

  public void initialize()
  {
    System.out.println("PFC");
  }

  public void updateAll()
  {
    TrendSheetReader current = MainApplication.dataHub.getCurrentYearTrendSheet();
    if (current != null)
    {
      try
      {
        double other = 100 - (current.getDataForCategoryForCurrentPeriod(TrendSheetReader.BREADP)
            + current.getDataForCategoryForCurrentPeriod(TrendSheetReader.FOODP)
            + current.getDataForCategoryForCurrentPeriod(TrendSheetReader.SIDESP)
            + current.getDataForCategoryForCurrentPeriod(TrendSheetReader.PAPERP)
            + current.getDataForCategoryForCurrentPeriod(TrendSheetReader.PRODUCEP)
            + current.getDataForCategoryForCurrentPeriod(TrendSheetReader.BEVERAGEP)
            + current.getDataForCategoryForCurrentPeriod(TrendSheetReader.CY_LABORP));
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
            new PieChart.Data(TrendSheetReader.BREADP,
                current.getDataForCategoryForCurrentPeriod(TrendSheetReader.BREADP)),
            new PieChart.Data(TrendSheetReader.FOODP,
                current.getDataForCategoryForCurrentPeriod(TrendSheetReader.FOODP)),
            new PieChart.Data(TrendSheetReader.SIDESP,
                current.getDataForCategoryForCurrentPeriod(TrendSheetReader.SIDESP)),
            new PieChart.Data(TrendSheetReader.PAPERP,
                current.getDataForCategoryForCurrentPeriod(TrendSheetReader.PAPERP)),
            new PieChart.Data(TrendSheetReader.PRODUCEP,
                current.getDataForCategoryForCurrentPeriod(TrendSheetReader.PRODUCEP)),
            new PieChart.Data(TrendSheetReader.BEVERAGEP,
                current.getDataForCategoryForCurrentPeriod(TrendSheetReader.BEVERAGEP)),
            new PieChart.Data(TrendSheetReader.CY_LABORP,
                current.getDataForCategoryForCurrentPeriod(TrendSheetReader.CY_LABORP)),
            new PieChart.Data("Other", other));

        periodChart.setData(pieChartData);
      }
      catch (Exception e)
      {
        e.printStackTrace();
        ErrorHandler.addError(e);
      }
    }
  }
}
