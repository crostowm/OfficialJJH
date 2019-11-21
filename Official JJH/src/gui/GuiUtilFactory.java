package gui;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import readers.UPKMap;

public class GuiUtilFactory
{

  public static HBox createUsageAnalysisHBoxItem(String n, HashMap<Integer, Double> item)
  {
    HBox box = new HBox(10);
    Label name = new Label(n);
    name.setPrefWidth(200);
    Label acUsage = new Label(item.get(UPKMap.ACTUAL_USAGE) + "");
    acUsage.setPrefWidth(100);
    acUsage.setAlignment(Pos.CENTER);
    Label thUsage = new Label(item.get(UPKMap.THEORETICAL_USAGE) + "");
    thUsage.setPrefWidth(100);
    thUsage.setAlignment(Pos.CENTER);
    Label usageVar = new Label(item.get(UPKMap.USAGE_VARIANCE) + "");
    usageVar.setPrefWidth(100);
    usageVar.setAlignment(Pos.CENTER);
    Label usageVar$ = new Label(item.get(UPKMap.USAGE_VARIANCE$) + "");
    usageVar$.setPrefWidth(100);
    usageVar$.setAlignment(Pos.CENTER);
    Label acUPK = new Label(item.get(UPKMap.ACTUAL_UPK) + "");
    acUPK.setPrefWidth(100);
    acUPK.setAlignment(Pos.CENTER);
    Label avgUPK = new Label(item.get(UPKMap.AVERAGE_UPK) + "");
    avgUPK.setPrefWidth(100);
    avgUPK.setAlignment(Pos.CENTER);
    Label upkVar = new Label(item.get(UPKMap.UPK_VARIANCE) + "");
    upkVar.setPrefWidth(100);
    upkVar.setAlignment(Pos.CENTER);
    box.getChildren().addAll(name, acUsage, thUsage, usageVar, usageVar$, acUPK, avgUPK, upkVar);
    return box;
  }

  public static HBox createUsageAnalysisHBoxTitle()
  {
    HBox box = new HBox(10);
    Label name = new Label("Name");
    name.setPrefWidth(200);
    Label acUsage = new Label("Actual Usage");
    acUsage.setPrefWidth(100);
    acUsage.setAlignment(Pos.CENTER);
    Label thUsage = new Label("Theoretical Usage");
    thUsage.setPrefWidth(100);
    thUsage.setAlignment(Pos.CENTER);
    Label usageVar = new Label("Usage Variance");
    usageVar.setPrefWidth(100);
    usageVar.setAlignment(Pos.CENTER);
    Label usageVar$ = new Label("Usage Variance $");
    usageVar$.setPrefWidth(100);
    usageVar$.setAlignment(Pos.CENTER);
    Label acUPK = new Label("Actual UPK");
    acUPK.setPrefWidth(100);
    acUPK.setAlignment(Pos.CENTER);
    Label avgUPK = new Label("Average UPK");
    avgUPK.setPrefWidth(100);
    avgUPK.setAlignment(Pos.CENTER);
    Label upkVar = new Label("UPK Variance");
    upkVar.setPrefWidth(100);
    upkVar.setAlignment(Pos.CENTER);
    box.getChildren().addAll(name, acUsage, thUsage, usageVar, usageVar$, acUPK, avgUPK, upkVar);
    return box;
  }

  public static HBox createTruckOrderHBoxTitle()
  {
    HBox box = new HBox(10);
    Label name = new Label("Name");
    name.setPrefWidth(200);
    Label unit = new Label("Unit");
    unit.setPrefWidth(70);
    unit.setAlignment(Pos.CENTER);
    Label acUsage = new Label("Actual Usage");
    acUsage.setPrefWidth(100);
    acUsage.setAlignment(Pos.CENTER);
    Label avgUPK = new Label("Average UPK");
    avgUPK.setPrefWidth(100);
    avgUPK.setAlignment(Pos.CENTER);
    Label onHand = new Label("On Hand");
    onHand.setPrefWidth(100);
    onHand.setAlignment(Pos.CENTER);
    Label toOrder = new Label("To Order");
    toOrder.setPrefWidth(100);
    toOrder.setAlignment(Pos.CENTER);
    box.getChildren().addAll(name, unit, acUsage, avgUPK, onHand, toOrder);
    return box;
  }

  public static LineChart<Number, Number> createUsageAnalysisLineChart(UsageAnalysisHBox uah,
      int currentWeekNumber)
  {
    final NumberAxis xAxis = new NumberAxis(currentWeekNumber - 7, currentWeekNumber, 1);
    final NumberAxis yAxis = new NumberAxis();
    yAxis.setForceZeroInRange(false);
    xAxis.setLabel("Week Number");
    // creating the chart
    final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);

    lineChart.setTitle("Usage Evaluation of " + uah.getName() + " Over The Past 6 Weeks");

    return lineChart;
  }

  public static XYChart.Series<Number, Number> getUPKSeriesFor(UsageAnalysisHBox uah,
      ArrayList<UPKMap> arrayList, int weekNumber, String seriesName, int upkMapCategory)
  {
    XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
    series.setName(seriesName);

    double usage1 = arrayList.get(0).getData(uah.getCategory(), uah.getName(), upkMapCategory);
    double sales1 = arrayList.get(0).getAdjustedSales();
    XYChart.Data<Number, Number> w1 = new XYChart.Data<Number, Number>(weekNumber - 6, usage1);
    w1.setNode(new DataPointNode(usage1, sales1));
    series.getData().add(w1);

    double usage2 = arrayList.get(1).getData(uah.getCategory(), uah.getName(), upkMapCategory);
    double sales2 = arrayList.get(1).getAdjustedSales();
    XYChart.Data<Number, Number> w2 = new XYChart.Data<Number, Number>(weekNumber - 5, usage2);
    w2.setNode(new DataPointNode(usage2, sales2));
    series.getData().add(w2);

    double usage3 = arrayList.get(2).getData(uah.getCategory(), uah.getName(), upkMapCategory);
    double sales3 = arrayList.get(2).getAdjustedSales();
    XYChart.Data<Number, Number> w3 = new XYChart.Data<Number, Number>(weekNumber - 4, usage3);
    w3.setNode(new DataPointNode(usage3, sales3));
    series.getData().add(w3);

    double usage4 = arrayList.get(3).getData(uah.getCategory(), uah.getName(), upkMapCategory);
    double sales4 = arrayList.get(3).getAdjustedSales();
    XYChart.Data<Number, Number> w4 = new XYChart.Data<Number, Number>(weekNumber - 3, usage4);
    w4.setNode(new DataPointNode(usage4, sales4));
    series.getData().add(w4);

    double usage5 = arrayList.get(4).getData(uah.getCategory(), uah.getName(), upkMapCategory);
    double sales5 = arrayList.get(4).getAdjustedSales();
    XYChart.Data<Number, Number> w5 = new XYChart.Data<Number, Number>(weekNumber - 2, usage5);
    w5.setNode(new DataPointNode(usage5, sales5));
    series.getData().add(w5);

    double usage6 = uah.getData().get(upkMapCategory);
    double sales6 = uah.getAdjustedSales();
    XYChart.Data<Number, Number> w6 = new XYChart.Data<Number, Number>(weekNumber - 1, usage6);
    w6.setNode(new DataPointNode(sales6));
    series.getData().add(w6);

    return series;
  }

  /**
   * @param <T>
   * @param seriesNames
   * @param xs
   *          Should all have the same xs
   * @param ys
   * @return
   */
  public static LineChart<Number, Number> createNewPopulatedBusinessAnalysisNumberChart(String xAxisTitle,
      String yAxisTitle, String chartTitle, ArrayList<String> seriesNames, ArrayList<Number> xs,
      ArrayList<ArrayList<Number>> ys)
  {
    // TODO finish this
    if (xs.size() > 1)
    {
      int diff = (Integer) xs.get(1) - (Integer) xs.get(0);
      final NumberAxis xAxis = new NumberAxis((Integer) xs.get(0), (Integer) xs.get(xs.size() - 1),
          diff);
      final NumberAxis yAxis = new NumberAxis();
      yAxis.setForceZeroInRange(false);
      xAxis.setLabel(xAxisTitle);
      yAxis.setLabel(yAxisTitle);
      // creating the chart
      final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);
      lineChart.setTitle(chartTitle);
      for(int ii = 0; ii < ys.size(); ii++)
      {
        lineChart.getData().add(getSeriesFor(seriesNames.get(ii), xs, ys.get(ii)));
      }
      return lineChart;
    }
    else
    {
      System.out.println("Line Chart Failed");
      return null;
    }
  }

  public static XYChart.Series<Number, Number> getSeriesFor(String seriesName, ArrayList<Number> x,
      ArrayList<Number> y)
  {
    XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
    series.setName(seriesName);
    for (int ii = 0; ii < x.size() && ii < y.size(); ii++)
    {
      XYChart.Data<Number, Number> data = new XYChart.Data<Number, Number>(x.get(ii), y.get(ii));
      series.getData().add(data);
    }
    return series;
  }
}
