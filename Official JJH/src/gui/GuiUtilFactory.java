package gui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import app.AppDirector;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import lineitems.HourlySalesItem;
import lineitems.UPKItem;
import lineitems.UPKWeek;

public class GuiUtilFactory
{

  public static HBox createUsageAnalysisHBoxItem(String n, UPKItem item)
  {
    HBox box = new HBox(10);
    Label name = new Label(n);
    name.setPrefWidth(200);
    Label acUsage = new Label(String.format("%.2f", item.getActualUsage()));
    acUsage.setPrefWidth(100);
    acUsage.setAlignment(Pos.CENTER);
    Label thUsage = new Label(String.format("%.2f", item.getTheoreticalUsage()));
    thUsage.setPrefWidth(100);
    thUsage.setAlignment(Pos.CENTER);
    Label usageVar = new Label(String.format("%.2f", item.getUsageVariance()));
    usageVar.setPrefWidth(100);
    usageVar.setAlignment(Pos.CENTER);
    Label usageVar$ = new Label(String.format("%.2f", item.getUsageVariance$()));
    usageVar$.setPrefWidth(100);
    usageVar$.setAlignment(Pos.CENTER);
    Label acUPK = new Label(String.format("%.2f", item.getActualUPK()));
    acUPK.setPrefWidth(100);
    acUPK.setAlignment(Pos.CENTER);
    Label avgUPK = new Label(String.format("%.2f", item.getAverageUPK()));
    avgUPK.setPrefWidth(100);
    avgUPK.setAlignment(Pos.CENTER);
    Label upkVar = new Label(String.format("%.2f", item.getUPKVariance()));
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

    lineChart.setTitle("Usage Evaluation of " + uah.getItem().getName() + " Over The Past 6 Weeks");

    return lineChart;
  }

  public static XYChart.Series<Number, Number> getUPKSeriesFor(String itemName, int weekNumber,
      String upkMapCategory)
  {
    XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
    series.setName(upkMapCategory);

    int index = 0;
    for (int ii = 6; ii > 0; ii--)
    {
      UPKWeek week = AppDirector.dataHub.getPast6UPKMaps().get(index);
      UPKItem item = week.getUPKItem(itemName);
      double usage = -1;
      switch (upkMapCategory)
      {
        case "Actual Usage":
          usage = item.getActualUsage();
          break;
        case "Theoretical Usage":
          usage = item.getTheoreticalUsage();
          break;
        case "Actual UPK":
          usage = item.getActualUPK();
          break;
        case "Average UPK":
          usage = item.getAverageUPK();
          break;
      }
      index++;
      double sales = week.getAdjustedSales();
      XYChart.Data<Number, Number> w = new XYChart.Data<Number, Number>(weekNumber - ii, usage);
      w.setNode(new DataPointNode(usage, sales));
      series.getData().add(w);
    }
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
    //TODO handle a size 1 to show week 1 data
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
        System.out.println("Chart: x-" + xs.get(ii) + " y-" + ys.get(ii));
        lineChart.getData().add(getSeriesFor(seriesNames.get(ii), xs, ys.get(ii)));
      }
      lineChart.setPadding(new Insets(10));
      return lineChart;
    }
    else
    {
      System.out.println("Line Chart Failed, GuiUtilFactory");
      return null;
    }
  }

  public static XYChart.Series<Number, Number> getSeriesFor(String seriesName, ArrayList<Number> x,
      ArrayList<Number> y)
  {
    for(Number n: y)
    {
      System.out.println(n);
    }
    XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
    series.setName(seriesName);
    for (int ii = 0; ii < x.size() && ii < y.size(); ii++)
    {
      XYChart.Data<Number, Number> data = new XYChart.Data<Number, Number>(x.get(ii), y.get(ii));
      System.out.println(ii + " " + data + " " + x.get(ii) + " " + y.get(ii));
      data.setNode(new DataPointNode((double) y.get(ii)));
      series.getData().add(data);
    }
    return series;
  }

  public static HBox createInventoryTitleBox()
  {
    Label name = new Label("Item Name");
    name.setMinWidth(200);
    name.setPrefWidth(200);
    name.setMaxWidth(200);
    name.setAlignment(Pos.CENTER);
    Label begInventory = new Label("Start Inventory");
    begInventory.setMinWidth(90);
    begInventory.setPrefWidth(90);
    begInventory.setMaxWidth(90);
    begInventory.setAlignment(Pos.CENTER);
    Label totPurch = new Label("Total Purch");
    totPurch.setMinWidth(75);
    totPurch.setPrefWidth(75);
    totPurch.setMaxWidth(75);
    totPurch.setAlignment(Pos.CENTER);
    Label totTrans = new Label("Total Trans");
    totTrans.setMinWidth(75);
    totTrans.setPrefWidth(75);
    totTrans.setMaxWidth(75);
    totTrans.setAlignment(Pos.CENTER);
    Label endInv = new Label("End Inventory");
    endInv.setMinWidth(90);
    endInv.setPrefWidth(90);
    endInv.setMaxWidth(90);
    endInv.setAlignment(Pos.CENTER);
    Label theorUsage = new Label("Theory Usage");
    theorUsage.setMinWidth(80);
    theorUsage.setPrefWidth(80);
    theorUsage.setMaxWidth(80);
    theorUsage.setAlignment(Pos.CENTER);
    Label actUsage = new Label("Actual Usage");
    actUsage.setMinWidth(75);
    actUsage.setPrefWidth(75);
    actUsage.setMaxWidth(75);
    actUsage.setAlignment(Pos.CENTER);
    Label avgUPK = new Label("Average UPK");
    avgUPK.setMinWidth(90);
    avgUPK.setPrefWidth(90);
    avgUPK.setMaxWidth(90);
    avgUPK.setAlignment(Pos.CENTER);
    Label actUPK = new Label("Actual UPK");
    actUPK.setMinWidth(75);
    actUPK.setPrefWidth(75);
    actUPK.setMaxWidth(75);
    actUPK.setAlignment(Pos.CENTER);
    Label upkVar = new Label("UPK Variance (%)");
    upkVar.setMinWidth(95);
    upkVar.setPrefWidth(95);
    upkVar.setMaxWidth(95);
    upkVar.setAlignment(Pos.CENTER);
    HBox box = new HBox(10);
    box.getChildren().addAll(name, begInventory, totPurch, totTrans, endInv, theorUsage, actUsage, avgUPK, actUPK, upkVar);
    return box;
  }

  public static void populateTheoryUsageTitleBox(HBox box)
  {
    Label menuNum = new Label("Menu Item Num");
    menuNum.setMinWidth(100);
    menuNum.setPrefWidth(100);
    menuNum.setMaxWidth(100);
    menuNum.setAlignment(Pos.CENTER);
    Label desc = new Label("Description");
    desc.setMinWidth(200);
    desc.setPrefWidth(200);
    desc.setMaxWidth(200);
    desc.setAlignment(Pos.CENTER);
    Label unit = new Label("Unit");
    unit.setMinWidth(50);
    unit.setPrefWidth(50);
    unit.setMaxWidth(50);
    unit.setAlignment(Pos.CENTER);
    Label qtySold = new Label("Qty Sold"); 
    qtySold.setMinWidth(55);
    qtySold.setPrefWidth(55);
    qtySold.setMaxWidth(55);
    qtySold.setAlignment(Pos.CENTER);
    Label totalQty = new Label("Total Qty"); 
    totalQty.setMinWidth(55);
    totalQty.setPrefWidth(55);
    totalQty.setMaxWidth(55);
    totalQty.setAlignment(Pos.CENTER);
    Label portion = new Label("Portion");
    portion.setMinWidth(55);
    portion.setPrefWidth(55);
    portion.setMaxWidth(55);
    portion.setAlignment(Pos.CENTER);
    box.getChildren().addAll(menuNum, desc, unit, qtySold, totalQty, portion);
  }

  public static void populateVendorDeliveryDetailTitleBox(HBox box)
  {
    Label deliveryDate = new Label("Delivery Date");
    deliveryDate.setMinWidth(100);
    deliveryDate.setPrefWidth(100);
    deliveryDate.setMaxWidth(100);
    Label desc = new Label("Description");
    desc.setMinWidth(70);
    desc.setPrefWidth(70);
    desc.setMaxWidth(70);
    Label unit = new Label("Unit");
    unit.setMinWidth(50);
    unit.setPrefWidth(50);
    unit.setMaxWidth(50);
    unit.setAlignment(Pos.CENTER);
    Label qtyCase = new Label("Qty (cs)");
    qtyCase.setMinWidth(70);
    qtyCase.setPrefWidth(70);
    qtyCase.setMaxWidth(70);
    Label qtyEa = new Label("Qty (ea)");
    qtyEa.setMinWidth(70);
    qtyEa.setPrefWidth(70);
    qtyEa.setMaxWidth(70);
    Label invoiceNum = new Label("Invoice Num");
    invoiceNum.setMinWidth(100);
    invoiceNum.setPrefWidth(100);
    invoiceNum.setMaxWidth(100);
    Label vendorNum = new Label("Vendor Num");
    vendorNum.setMinWidth(100);
    vendorNum.setPrefWidth(100);
    vendorNum.setMaxWidth(100);
    box.getChildren().addAll(deliveryDate, desc, unit, qtyCase, qtyEa, invoiceNum, vendorNum);

  }

  public static void populateTransferTitleBox(HBox box)
  {
    Label inOut = new Label("In/Out");
    inOut.setMinWidth(40);
    inOut.setPrefWidth(40);
    inOut.setMaxWidth(40);
    inOut.setAlignment(Pos.CENTER);
    Label store = new Label("Store");
    store.setMinWidth(250);
    store.setPrefWidth(250);
    store.setMaxWidth(250);
    store.setAlignment(Pos.CENTER);
    Label unit = new Label("Unit");
    unit.setMinWidth(40);
    unit.setPrefWidth(40);
    unit.setMaxWidth(40);
    unit.setAlignment(Pos.CENTER);
    Label date = new Label("Date");
    date.setMinWidth(70);
    date.setPrefWidth(70);
    date.setMaxWidth(70);
    date.setAlignment(Pos.CENTER);
    Label itemId = new Label("Item ID");
    itemId.setMinWidth(100);
    itemId.setPrefWidth(100);
    itemId.setMaxWidth(100);
    itemId.setAlignment(Pos.CENTER);
    Label qty = new Label("Quantity");
    qty.setMinWidth(65);
    qty.setPrefWidth(65);
    qty.setMaxWidth(65);
    qty.setAlignment(Pos.CENTER);
    box.getChildren().addAll(inOut, store, unit, date, itemId, qty);
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
    Label onlinePickupValue = new Label("Web Pickup");
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
    if (hsi.getTotalInshopValuePercentage() > 0)
      totalInshopValue.setStyle("-fx-background-color: rgba(255, 165, 0, "
          + (hsi.getTotalInshopValuePercentage() / 100) + ");");
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
    if (hsi.getTotalDeliveryValuePercentage() > 0)
      totalDeliveryValue.setStyle("-fx-background-color: rgba(255, 165, 0, "
          + (hsi.getTotalDeliveryValuePercentage() / 100) + ");");
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
    if (hsi.getTotalPercent() > 0)
      totalValue.setStyle("-fx-background-color: rgba(" + AppDirector.jjrgb + ", "
          + ((hsi.getTotalPercent() / 100) * 3) + ");");
    Label totalValuePercentage = new Label(String.format("%.2f%%", hsi.getTotalPercent()));
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

  public static HBox createHourlyItemBox(ArrayList<HourlySalesItem> hsis)
  {
    HBox box = new HBox(4);
    box.setPadding(new Insets(5, 5, 5, 15));
    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa");
    Label time = new Label(sdf.format(hsis.get(0).getHour().getTime()));
    time.setMinWidth(55);
    time.setPrefWidth(55);
    time.setMaxWidth(55);
    Separator s1 = new Separator(Orientation.VERTICAL);
    s1.setPadding(new Insets(0, 0, 0, 15));
    int eic = 0;
    for(HourlySalesItem hsi: hsis)
    {
      eic += hsi.getEatInCount();
    }
    eic = eic/hsis.size();
    Label eatInCount = new Label(String.format("%d", eic));
    eatInCount.setMinWidth(20);
    eatInCount.setPrefWidth(20);
    eatInCount.setMaxWidth(20);
    double eiv = 0;
    for(HourlySalesItem hsi: hsis)
    {
      eiv += hsi.getEatInValue();
    }
    eiv = eiv/hsis.size();
    Label eatInValue = new Label(String.format("$%.2f", eiv));
    eatInValue.setMinWidth(50);
    eatInValue.setPrefWidth(50);
    eatInValue.setMaxWidth(50);
    eatInValue.setAlignment(Pos.CENTER_RIGHT);
    Separator s2 = new Separator(Orientation.VERTICAL);
    int tc = 0;
    for(HourlySalesItem hsi: hsis)
    {
      tc += hsi.getTakeoutCount();
    }
    tc = tc/hsis.size();
    Label takeoutCount = new Label(String.format("%d", tc));
    takeoutCount.setMinWidth(20);
    takeoutCount.setPrefWidth(20);
    takeoutCount.setMaxWidth(20);
    double tv = 0;
    for(HourlySalesItem hsi: hsis)
    {
      tv += hsi.getTakeoutValue();
    }
    tv = tv/hsis.size();
    Label takeoutValue = new Label(String.format("$%.2f", tv));
    takeoutValue.setMinWidth(50);
    takeoutValue.setPrefWidth(50);
    takeoutValue.setMaxWidth(50);
    takeoutValue.setAlignment(Pos.CENTER_RIGHT);
    Separator s3 = new Separator(Orientation.VERTICAL);
    int pc = 0;
    for(HourlySalesItem hsi: hsis)
    {
      pc += hsi.getPickupCount();
    }
    pc = pc/hsis.size();
    Label pickupCount = new Label(String.format("%d", pc));
    pickupCount.setMinWidth(20);
    pickupCount.setPrefWidth(20);
    pickupCount.setMaxWidth(20);
    double pv = 0;
    for(HourlySalesItem hsi: hsis)
    {
      pv += hsi.getPickupValue();
    }
    pv = pv/hsis.size();
    Label pickupValue = new Label(String.format("$%.2f", pv));
    pickupValue.setMinWidth(50);
    pickupValue.setPrefWidth(50);
    pickupValue.setMaxWidth(50);
    pickupValue.setAlignment(Pos.CENTER_RIGHT);
    Separator s4 = new Separator(Orientation.VERTICAL);
    int opc = 0;
    for(HourlySalesItem hsi: hsis)
    {
      opc += hsi.getOnlinePickupCount();
    }
    opc = opc/hsis.size();
    Label onlinePickupCount = new Label(String.format("%d", opc));
    onlinePickupCount.setMinWidth(20);
    onlinePickupCount.setPrefWidth(20);
    onlinePickupCount.setMaxWidth(20);
    double opv = 0;
    for(HourlySalesItem hsi: hsis)
    {
      opv += hsi.getOnlinePickupValue();
    }
    opv = opv/hsis.size();
    Label onlinePickupValue = new Label(String.format("$%.2f", opv));
    onlinePickupValue.setMinWidth(50);
    onlinePickupValue.setPrefWidth(50);
    onlinePickupValue.setMaxWidth(50);
    onlinePickupValue.setAlignment(Pos.CENTER_RIGHT);
    Separator s5 = new Separator(Orientation.VERTICAL);
    int dc = 0;
    for(HourlySalesItem hsi: hsis)
    {
      dc += hsi.getDeliveryCount();
    }
    dc = dc/hsis.size();
    Label deliveryCount = new Label(String.format("%d", dc));
    deliveryCount.setMinWidth(20);
    deliveryCount.setPrefWidth(20);
    deliveryCount.setMaxWidth(20);
    double dv = 0;
    for(HourlySalesItem hsi: hsis)
    {
      dv += hsi.getDeliveryValue();
    }
    dv = dv/hsis.size();
    Label deliveryValue = new Label(String.format("$%.2f", dv));
    deliveryValue.setMinWidth(50);
    deliveryValue.setPrefWidth(50);
    deliveryValue.setMaxWidth(50);
    deliveryValue.setAlignment(Pos.CENTER_RIGHT);
    Separator s6 = new Separator(Orientation.VERTICAL);
    int odc = 0;
    for(HourlySalesItem hsi: hsis)
    {
      odc += hsi.getOnlineDeliveryCount();
    }
    odc = odc/hsis.size();
    Label onlineDeliveryCount = new Label(String.format("%d", odc));
    onlineDeliveryCount.setMinWidth(20);
    onlineDeliveryCount.setPrefWidth(20);
    onlineDeliveryCount.setMaxWidth(20);
    double odv = 0;
    for(HourlySalesItem hsi: hsis)
    {
      odv += hsi.getOnlineDeliveryValue();
    }
    odv = odv/hsis.size();
    Label onlineDeliveryValue = new Label(String.format("$%.2f", odv));
    onlineDeliveryValue.setMinWidth(50);
    onlineDeliveryValue.setPrefWidth(50);
    onlineDeliveryValue.setMaxWidth(50);
    onlineDeliveryValue.setAlignment(Pos.CENTER_RIGHT);
    Separator s7 = new Separator(Orientation.VERTICAL);
    int tic = 0;
    for(HourlySalesItem hsi: hsis)
    {
      tic += hsi.getTotalInshopCount();
    }
    tic = tic/hsis.size();
    Label totalInshopCount = new Label(String.format("%d", tic));
    totalInshopCount.setMinWidth(20);
    totalInshopCount.setPrefWidth(20);
    totalInshopCount.setMaxWidth(20);
    double tiv = 0;
    for(HourlySalesItem hsi: hsis)
    {
      tiv += hsi.getTotalInshopValue();
    }
    tiv = tiv/hsis.size();
    Label totalInshopValue = new Label(String.format("$%.2f", tiv));
    totalInshopValue.setMinWidth(50);
    totalInshopValue.setPrefWidth(50);
    totalInshopValue.setMaxWidth(50);
    totalInshopValue.setAlignment(Pos.CENTER_RIGHT);
    double tivp = 0;
    for(HourlySalesItem hsi: hsis)
    {
      tivp += hsi.getTotalInshopValue();
    }
    tivp = tivp/hsis.size();
    if (tivp > 0)
      totalInshopValue.setStyle("-fx-background-color: rgba(255, 165, 0, "
          + (tivp / 100) + ");");
    Label totalInshopValuePercentage = new Label(
        String.format("%.2f%%", tivp));
    totalInshopValuePercentage.setMinWidth(50);
    totalInshopValuePercentage.setPrefWidth(50);
    totalInshopValuePercentage.setMaxWidth(50);
    totalInshopValuePercentage.setAlignment(Pos.CENTER_RIGHT);
    Separator s8 = new Separator(Orientation.VERTICAL);
    int tdc = 0;
    for(HourlySalesItem hsi: hsis)
    {
      tdc += hsi.getTotalDeliveryCount();
    }
    tdc = tdc/hsis.size();
    Label totalDeliveryCount = new Label(String.format("%d", tdc));
    totalDeliveryCount.setMinWidth(20);
    totalDeliveryCount.setPrefWidth(20);
    totalDeliveryCount.setMaxWidth(20);
    double tdv = 0;
    for(HourlySalesItem hsi: hsis)
    {
      tdv += hsi.getTotalDeliveryValue();
    }
    tdv = tdv/hsis.size();
    Label totalDeliveryValue = new Label(String.format("$%.2f", tdv));
    totalDeliveryValue.setMinWidth(50);
    totalDeliveryValue.setPrefWidth(50);
    totalDeliveryValue.setMaxWidth(50);
    totalDeliveryValue.setAlignment(Pos.CENTER_RIGHT);
    double tdvp = 0;
    for(HourlySalesItem hsi: hsis)
    {
      tdvp += hsi.getTotalDeliveryValuePercentage();
    }
    tdvp = tdvp/hsis.size();
    if (tdvp > 0)
      totalDeliveryValue.setStyle("-fx-background-color: rgba(255, 165, 0, "
          + (tdvp / 100) + ");");
    Label totalDeliveryValuePercentage = new Label(
        String.format("%.2f%%", tdvp));
    totalDeliveryValuePercentage.setMinWidth(50);
    totalDeliveryValuePercentage.setPrefWidth(50);
    totalDeliveryValuePercentage.setMaxWidth(50);
    totalDeliveryValuePercentage.setAlignment(Pos.CENTER_RIGHT);
    Separator s9 = new Separator(Orientation.VERTICAL);
    int totc = 0;
    for(HourlySalesItem hsi: hsis)
    {
      totc += hsi.getTotalCount();
    }
    totc = totc/hsis.size();
    Label totalCount = new Label(String.format("%d", totc));
    totalCount.setMinWidth(20);
    totalCount.setPrefWidth(20);
    totalCount.setMaxWidth(20);
    double tdol = 0;
    for(HourlySalesItem hsi: hsis)
    {
      tdol += hsi.getTotal$();
    }
    tdol = tdol/hsis.size();
    Label totalValue = new Label(String.format("$%.2f", tdol));
    totalValue.setMinWidth(50);
    totalValue.setPrefWidth(50);
    totalValue.setMaxWidth(50);
    totalValue.setAlignment(Pos.CENTER_RIGHT);
    double totp = 0;
    for(HourlySalesItem hsi: hsis)
    {
      totp += hsi.getTotalPercent();
    }
    totp = totp/hsis.size();
    if (totp > 0)
      totalValue.setStyle("-fx-background-color: rgba(" + AppDirector.jjrgb + ", "
          + Math.min(1, ((totp / 100) * 3)) + ");");
    
    Label totalValuePercentage = new Label(String.format("%.2f%%", totp));
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
}
