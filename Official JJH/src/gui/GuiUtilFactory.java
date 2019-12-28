package gui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import app.MainApplication;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;
import lineitems.HourlySalesItem;
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
    name.setMinWidth(200);
    name.setMaxWidth(200);
    Label unit = new Label("Unit");
    unit.setMinWidth(100);
    unit.setMaxWidth(100);
    unit.setAlignment(Pos.CENTER);
    Label acUsage = new Label("Actual Usage");
    acUsage.setMinWidth(90);
    acUsage.setMaxWidth(90);
    acUsage.setAlignment(Pos.CENTER);
    Label avgUPK = new Label("Average UPK");
    avgUPK.setMinWidth(90);
    avgUPK.setMaxWidth(90);
    avgUPK.setAlignment(Pos.CENTER);
    Label projUseCs = new Label("Proj Usage (cs)");
    projUseCs.setMinWidth(90);
    projUseCs.setMaxWidth(90);
    projUseCs.setAlignment(Pos.CENTER);
    Label projUseEa = new Label("Proj Usage (ea)");
    projUseEa.setMinWidth(90);
    projUseEa.setMaxWidth(90);
    projUseEa.setAlignment(Pos.CENTER);
    Label onHandCs = new Label("On Hand (cs)");
    onHandCs.setMinWidth(90);
    onHandCs.setMaxWidth(90);
    onHandCs.setAlignment(Pos.CENTER);
    Label onHandEa = new Label("On Hand (ea)");
    onHandEa.setMinWidth(90);
    onHandEa.setMaxWidth(90);
    onHandEa.setAlignment(Pos.CENTER);
    Label toOrder = new Label("To Order");
    toOrder.setMinWidth(90);
    toOrder.setMaxWidth(90);
    toOrder.setAlignment(Pos.CENTER);
    box.getChildren().addAll(name, unit, acUsage, avgUPK, projUseCs, projUseEa, onHandCs, onHandEa,
        toOrder);
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
  public static LineChart<Number, Number> createNewPopulatedBusinessAnalysisNumberChart(
      String xAxisTitle, String yAxisTitle, String chartTitle, ArrayList<String> seriesNames,
      ArrayList<Number> xs, ArrayList<ArrayList<Number>> ys)
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
      for (int ii = 0; ii < ys.size(); ii++)
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
      data.setNode(new DataPointNode((double) y.get(ii)));
      series.getData().add(data);
    }
    return series;
  }

  public static HBox createHourlyItemBox(HourlySalesItem hsi)
  {
    HBox box = new HBox(4);
    box.setPadding(new Insets(5, 5, 5, 15));
    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa");
    Label time = new Label(sdf.format(hsi.getHour().getTime()));
    time.setMinWidth(55);
    time.setPrefWidth(55);
    time.setMaxWidth(55);
    Separator s1 = new Separator(Orientation.VERTICAL);
    s1.setPadding(new Insets(0, 0, 0, 15));
    Label eatInCount = new Label(String.format("%d", hsi.getEatInCount()));
    eatInCount.setMinWidth(20);
    eatInCount.setPrefWidth(20);
    eatInCount.setMaxWidth(20);
    Label eatInValue = new Label(String.format("$%.2f", hsi.getEatInValue()));
    eatInValue.setMinWidth(50);
    eatInValue.setPrefWidth(50);
    eatInValue.setMaxWidth(50);
    eatInValue.setAlignment(Pos.CENTER_RIGHT);
    Separator s2 = new Separator(Orientation.VERTICAL);
    Label takeoutCount = new Label(String.format("%d", hsi.getTakeoutCount()));
    takeoutCount.setMinWidth(20);
    takeoutCount.setPrefWidth(20);
    takeoutCount.setMaxWidth(20);
    Label takeoutValue = new Label(String.format("$%.2f", hsi.getTakeoutValue()));
    takeoutValue.setMinWidth(50);
    takeoutValue.setPrefWidth(50);
    takeoutValue.setMaxWidth(50);
    takeoutValue.setAlignment(Pos.CENTER_RIGHT);
    Separator s3 = new Separator(Orientation.VERTICAL);
    Label pickupCount = new Label(String.format("%d", hsi.getPickupCount()));
    pickupCount.setMinWidth(20);
    pickupCount.setPrefWidth(20);
    pickupCount.setMaxWidth(20);
    Label pickupValue = new Label(String.format("$%.2f", hsi.getPickupValue()));
    pickupValue.setMinWidth(50);
    pickupValue.setPrefWidth(50);
    pickupValue.setMaxWidth(50);
    pickupValue.setAlignment(Pos.CENTER_RIGHT);
    Separator s4 = new Separator(Orientation.VERTICAL);
    Label onlinePickupCount = new Label(String.format("%d", hsi.getOnlinePickupCount()));
    onlinePickupCount.setMinWidth(20);
    onlinePickupCount.setPrefWidth(20);
    onlinePickupCount.setMaxWidth(20);
    Label onlinePickupValue = new Label(String.format("$%.2f", hsi.getOnlinePickupValue()));
    onlinePickupValue.setMinWidth(50);
    onlinePickupValue.setPrefWidth(50);
    onlinePickupValue.setMaxWidth(50);
    onlinePickupValue.setAlignment(Pos.CENTER_RIGHT);
    Separator s5 = new Separator(Orientation.VERTICAL);
    Label deliveryCount = new Label(String.format("%d", hsi.getDeliveryCount()));
    deliveryCount.setMinWidth(20);
    deliveryCount.setPrefWidth(20);
    deliveryCount.setMaxWidth(20);
    Label deliveryValue = new Label(String.format("$%.2f", hsi.getDeliveryValue()));
    deliveryValue.setMinWidth(50);
    deliveryValue.setPrefWidth(50);
    deliveryValue.setMaxWidth(50);
    deliveryValue.setAlignment(Pos.CENTER_RIGHT);
    Separator s6 = new Separator(Orientation.VERTICAL);
    Label onlineDeliveryCount = new Label(String.format("%d", hsi.getOnlineDeliveryCount()));
    onlineDeliveryCount.setMinWidth(20);
    onlineDeliveryCount.setPrefWidth(20);
    onlineDeliveryCount.setMaxWidth(20);
    Label onlineDeliveryValue = new Label(String.format("$%.2f", hsi.getOnlineDeliveryValue()));
    onlineDeliveryValue.setMinWidth(50);
    onlineDeliveryValue.setPrefWidth(50);
    onlineDeliveryValue.setMaxWidth(50);
    onlineDeliveryValue.setAlignment(Pos.CENTER_RIGHT);
    Separator s7 = new Separator(Orientation.VERTICAL);
    Label totalInshopCount = new Label(String.format("%d", hsi.getTotalInshopCount()));
    totalInshopCount.setMinWidth(20);
    totalInshopCount.setPrefWidth(20);
    totalInshopCount.setMaxWidth(20);
    Label totalInshopValue = new Label(String.format("$%.2f", hsi.getTotalInshopValue()));
    totalInshopValue.setMinWidth(50);
    totalInshopValue.setPrefWidth(50);
    totalInshopValue.setMaxWidth(50);
    totalInshopValue.setAlignment(Pos.CENTER_RIGHT);
    Label totalInshopValuePercentage = new Label(
        String.format("%.2f%%", hsi.getTotalInshopValuePercentage()));
    totalInshopValuePercentage.setMinWidth(50);
    totalInshopValuePercentage.setPrefWidth(50);
    totalInshopValuePercentage.setMaxWidth(50);
    totalInshopValuePercentage.setAlignment(Pos.CENTER_RIGHT);
    Separator s8 = new Separator(Orientation.VERTICAL);
    Label totalDeliveryCount = new Label(String.format("%d", hsi.getTotalDeliveryCount()));
    totalDeliveryCount.setMinWidth(20);
    totalDeliveryCount.setPrefWidth(20);
    totalDeliveryCount.setMaxWidth(20);
    Label totalDeliveryValue = new Label(String.format("$%.2f", hsi.getTotalDeliveryValue()));
    totalDeliveryValue.setMinWidth(50);
    totalDeliveryValue.setPrefWidth(50);
    totalDeliveryValue.setMaxWidth(50);
    totalDeliveryValue.setAlignment(Pos.CENTER_RIGHT);
    Label totalDeliveryValuePercentage = new Label(
        String.format("%.2f%%", hsi.getTotalDeliveryValuePercentage()));
    totalDeliveryValuePercentage.setMinWidth(50);
    totalDeliveryValuePercentage.setPrefWidth(50);
    totalDeliveryValuePercentage.setMaxWidth(50);
    totalDeliveryValuePercentage.setAlignment(Pos.CENTER_RIGHT);
    Separator s9 = new Separator(Orientation.VERTICAL);
    Label totalCount = new Label(String.format("%d", hsi.getTotalCount()));
    totalCount.setMinWidth(20);
    totalCount.setPrefWidth(20);
    totalCount.setMaxWidth(20);
    Label totalValue = new Label(String.format("$%.2f", hsi.getTotal$()));
    totalValue.setMinWidth(50);
    totalValue.setPrefWidth(50);
    totalValue.setMaxWidth(50);
    totalValue.setAlignment(Pos.CENTER_RIGHT);
    Label totalValuePercentage = new Label(
        String.format("%.2f%%", hsi.getTotalPercent()));
    totalValuePercentage.setMinWidth(50);
    totalValuePercentage.setPrefWidth(50);
    totalValuePercentage.setMaxWidth(50);
    totalValuePercentage.setAlignment(Pos.CENTER_RIGHT);
    Separator s10 = new Separator(Orientation.VERTICAL);
    box.getChildren().addAll(time, s1, eatInCount, eatInValue, s2, takeoutCount, takeoutValue, s3,
        pickupCount, pickupValue, s4, onlinePickupCount, onlinePickupValue, s5, deliveryCount,
        deliveryValue, s6, onlineDeliveryCount, onlineDeliveryValue, s7, totalInshopCount,
        totalInshopValue, totalInshopValuePercentage, s8, totalDeliveryCount, totalDeliveryValue,
        totalDeliveryValuePercentage, s9, totalCount, totalValue, totalValuePercentage, s10);
    return box;
  }

  public static HBox getHourlyTitleHBox()
  {
    HBox box = new HBox(4);
    box.setPadding(new Insets(5, 5, 5, 15));
    Label time = new Label("Time");
    time.setMinWidth(55);
    time.setPrefWidth(55);
    time.setMaxWidth(55);
    time.setAlignment(Pos.CENTER);
    Separator s1 = new Separator(Orientation.VERTICAL);
    s1.setPadding(new Insets(0, 0, 0, 15));
    Label eatInCount = new Label("Eat In");
    eatInCount.setMinWidth(76);
    eatInCount.setPrefWidth(76);
    eatInCount.setMaxWidth(76);
    eatInCount.setAlignment(Pos.CENTER);
    Separator s2 = new Separator(Orientation.VERTICAL);
    Label takeoutCount = new Label("Take Out");
    takeoutCount.setMinWidth(74);
    takeoutCount.setPrefWidth(74);
    takeoutCount.setMaxWidth(74);
    takeoutCount.setAlignment(Pos.CENTER);
    Separator s3 = new Separator(Orientation.VERTICAL);
    Label pickupValue = new Label("Pickup");
    pickupValue.setMinWidth(74);
    pickupValue.setPrefWidth(74);
    pickupValue.setMaxWidth(74);
    pickupValue.setAlignment(Pos.CENTER);
    Separator s4 = new Separator(Orientation.VERTICAL);
    Label onlinePickupValue = new Label("Online Pickup");
    onlinePickupValue.setMinWidth(74);
    onlinePickupValue.setPrefWidth(74);
    onlinePickupValue.setMaxWidth(74);
    onlinePickupValue.setAlignment(Pos.CENTER);
    Separator s5 = new Separator(Orientation.VERTICAL);
    Label deliveryValue = new Label("Delivery");
    deliveryValue.setMinWidth(74);
    deliveryValue.setPrefWidth(74);
    deliveryValue.setMaxWidth(74);
    deliveryValue.setAlignment(Pos.CENTER);
    Separator s6 = new Separator(Orientation.VERTICAL);
    Label onlineDeliveryValue = new Label("Online Del");
    onlineDeliveryValue.setMinWidth(74);
    onlineDeliveryValue.setPrefWidth(74);
    onlineDeliveryValue.setMaxWidth(74);
    onlineDeliveryValue.setAlignment(Pos.CENTER);
    Separator s7 = new Separator(Orientation.VERTICAL);
    Label totalInshopValue = new Label("Total Inshop");
    totalInshopValue.setMinWidth(128);
    totalInshopValue.setPrefWidth(128);
    totalInshopValue.setMaxWidth(128);
    totalInshopValue.setAlignment(Pos.CENTER);
    Separator s8 = new Separator(Orientation.VERTICAL);
    Label totalDeliveryValue = new Label("Total Delivery");
    totalDeliveryValue.setMinWidth(128);
    totalDeliveryValue.setPrefWidth(128);
    totalDeliveryValue.setMaxWidth(128);
    totalDeliveryValue.setAlignment(Pos.CENTER);
    Separator s9 = new Separator(Orientation.VERTICAL);
    Label total = new Label("Total");
    total.setMinWidth(128);
    total.setPrefWidth(128);
    total.setMaxWidth(128);
    total.setAlignment(Pos.CENTER);
    Separator s10 = new Separator(Orientation.VERTICAL);
    box.getChildren().addAll(time, s1, eatInCount, s2, takeoutCount, s3, pickupValue, s4,
        onlinePickupValue, s5, deliveryValue, s6, onlineDeliveryValue, s7, totalInshopValue, s8,
        totalDeliveryValue, s9, total, s10);
    return box;
  }
}
