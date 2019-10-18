package gui;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.geometry.Pos;
import javafx.scene.Node;
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

  public static LineChart<Number, Number> createUsageAnalysisLineChart(UsageAnalysisHBox uah, int currentWeekNumber)
  {
    //TODO show adj sales
    // defining the axes
    final NumberAxis xAxis = new NumberAxis(currentWeekNumber - 6, currentWeekNumber - 1, 1);
    final NumberAxis yAxis = new NumberAxis();
    yAxis.setForceZeroInRange(false);
    xAxis.setLabel("Week Number");
    // creating the chart
    final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);

    lineChart.setTitle("Usage Evaluation of " + uah.getName() + " Over The Past 6 Weeks");
    
    return lineChart;
  }

  public static XYChart.Series<Number, Number> getSeriesFor(UsageAnalysisHBox uah,
      ArrayList<UPKMap> arrayList, int currentWeekNumber, String seriesName, int upkMapCategory)
  {
    XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
    series.setName(seriesName);
    series.getData().add(new XYChart.Data<Number, Number>(currentWeekNumber - 6,
        arrayList.get(0).getData(uah.getCategory(), uah.getName(), upkMapCategory)));
    series.getData().add(new XYChart.Data<Number, Number>(currentWeekNumber - 5,
        arrayList.get(1).getData(uah.getCategory(), uah.getName(), upkMapCategory)));
    series.getData().add(new XYChart.Data<Number, Number>(currentWeekNumber - 4,
        arrayList.get(2).getData(uah.getCategory(), uah.getName(), upkMapCategory)));
    series.getData().add(new XYChart.Data<Number, Number>(currentWeekNumber - 3,
        arrayList.get(3).getData(uah.getCategory(), uah.getName(), upkMapCategory)));
    series.getData().add(new XYChart.Data<Number, Number>(currentWeekNumber - 2,
        arrayList.get(4).getData(uah.getCategory(), uah.getName(), upkMapCategory)));
    series.getData().add(new XYChart.Data<Number, Number>(currentWeekNumber - 1,
        uah.getData().get(upkMapCategory)));
    
    return series;
  }
}
