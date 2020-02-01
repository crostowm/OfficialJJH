package util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import javafx.collections.FXCollections;
import lineitems.AMPhoneAuditItem;
import lineitems.AttendanceShift;
import lineitems.CateringTransaction;
import lineitems.HourlySalesDay;
import lineitems.InventoryItem;
import lineitems.UPKWeek;
import lineitems.WeeklySummaryItem;
import observers.DataObserver;
import personnel.Manager;
import readers.AMPhoneAuditReader;
import readers.ManagerDBLReader;
import readers.TrendSheetReader;
import readers.WSRMap;

public class DataHub implements Serializable
{
  public static final int AMBUFFER = 1, PMBUFFER = 2, BTV = 3, B9TV = 4, WLV = 5, BAKEDAT11 = 6,
      BAKEDATSC = 7, LETTUCEBV = 8, TOMATOBV = 9, ONIONBV = 10, CUCUMBERBV = 11, PICKLEBV = 12,
      STORESC_TIME = 13, NUMDECKS = 14, PROOF_TIME = 15, BAKE_TIME = 16, COOL_TIME = 17,
      SPROUT_UPK = 18, INSHOP_MIN_PAY = 19;
  private static final long serialVersionUID = 2092175547020407363L;
  private transient ArrayList<DataObserver> observers = new ArrayList<DataObserver>();
  private transient WSRMap[] last4WeeksWSR = new WSRMap[4];
  /**
   * index 5 is last completed week index 0 is earliest week
   */
  private transient ArrayList<UPKWeek> past6UPKWeeks;
  private transient ArrayList<ArrayList<CateringTransaction>> past4DaysCatering;
  private transient AMPhoneAuditItem amPhoneAuditItem;
  private transient ArrayList<AttendanceShift> yesterdaysAttendanceShifts;
  private transient WSRMap lastYearWSR;
  private transient ArrayList<InventoryItem> inventoryItems;
  private transient WeeklySummaryItem weeklySummaryItem;
  private ArrayList<String> inventoryItemNames;
  private ArrayList<Manager> managers = new ArrayList<Manager>();
  private ArrayList<CateringOrder> cateringOrders = new ArrayList<CateringOrder>();
  private ArrayList<Double> average = new ArrayList<Double>();
  private ArrayList<Double> averagePlusBuffer = new ArrayList<Double>();
  private ArrayList<Double> catering = new ArrayList<Double>();
  private ArrayList<Integer> sampling = new ArrayList<Integer>();
  private ArrayList<Double> projections = new ArrayList<Double>();
  private ArrayList<Double> thawed = new ArrayList<Double>();
  private ArrayList<Double> percentage = new ArrayList<Double>();
  private ArrayList<Double> wheat = new ArrayList<Double>();
  private HashMap<Integer, Double> settings = new HashMap<Integer, Double>();
  // Index 0 = Shift 1 <String-Protein <String-msc/gec, Double packs>>
  private ArrayList<HashMap<String, HashMap<String, Double>>> slicingPars = new ArrayList<HashMap<String, HashMap<String, Double>>>();
  private ArrayList<String> weeklySupplyItems;
  private ArrayList<CompletableTask> managerDBLs;
  // Year-Month-Day
  private HashMap<Integer, HashMap<Integer, HashMap<Integer, HourlySalesDay>>> hourlySalesCalendar;
  private HashMap<Integer, TrendSheetReader> trendSheetCalendar;

  public DataHub()
  {
    for (int ii = 0; ii < 14; ii++)
    {
      average.add(0.0);
      averagePlusBuffer.add(0.0);
      catering.add(0.0);
      sampling.add(0);
      projections.add(0.0);
      thawed.add(0.0);
      percentage.add(0.0);
      wheat.add(0.0);
      slicingPars.add(new HashMap<String, HashMap<String, Double>>());
      slicingPars.get(ii).put("Cheese", new HashMap<String, Double>());
      slicingPars.get(ii).put("Ham", new HashMap<String, Double>());
      slicingPars.get(ii).put("Turkey", new HashMap<String, Double>());
      slicingPars.get(ii).put("Beef", new HashMap<String, Double>());
      slicingPars.get(ii).put("Salami", new HashMap<String, Double>());
      slicingPars.get(ii).put("Capicola", new HashMap<String, Double>());
    }
    setupWeeklySupplyItems();
    setupManagerDBLs();
    settings.put(AMBUFFER, 1.2);
    settings.put(PMBUFFER, 1.2);
    settings.put(BTV, 200.0);
    settings.put(B9TV, 190.0);
    settings.put(WLV, 800.0);
    settings.put(BAKEDAT11, .75);
    settings.put(BAKEDATSC, .5);
    settings.put(LETTUCEBV, 500.0);
    settings.put(TOMATOBV, 900.0);
    settings.put(ONIONBV, 1200.0);
    settings.put(CUCUMBERBV, 2200.0);
    settings.put(PICKLEBV, 1200.0);
    settings.put(STORESC_TIME, 15.0);
    settings.put(NUMDECKS, 4.0);
    settings.put(PROOF_TIME, 50.0);
    settings.put(BAKE_TIME, 20.0);
    settings.put(COOL_TIME, 25.0);
    settings.put(SPROUT_UPK, 1.0);
    settings.put(INSHOP_MIN_PAY, 8.0);
    hourlySalesCalendar = new HashMap<Integer, HashMap<Integer, HashMap<Integer, HourlySalesDay>>>();
    trendSheetCalendar = new HashMap<Integer, TrendSheetReader>();
  }

  /**
   * @param wsr
   * @param week
   *          1-4
   */
  public void addWSRMapForProjections(WSRMap wsr, int week)
  {
    if (last4WeeksWSR == null)
      last4WeeksWSR = new WSRMap[4];
    last4WeeksWSR[week - 1] = wsr;
    if (last4WeeksWSR[0] != null && last4WeeksWSR[1] != null && last4WeeksWSR[2] != null
        && last4WeeksWSR[3] != null)
    {
      // All proj ready
      for (int ii = 1; ii < 15; ii++)
      {
        System.out.println(
            "Shift " + ii + ":  " + getProjectionWSRWeek(1).getDataForShift("= Royalty Sales", ii)
                + " " + getProjectionWSRWeek(2).getDataForShift("= Royalty Sales", ii) + " "
                + getProjectionWSRWeek(3).getDataForShift("= Royalty Sales", ii) + " "
                + getProjectionWSRWeek(4).getDataForShift("= Royalty Sales", ii));
        double avg = (getProjectionWSRWeek(1).getDataForShift("= Royalty Sales", ii)
            + getProjectionWSRWeek(2).getDataForShift("= Royalty Sales", ii)
            + getProjectionWSRWeek(3).getDataForShift("= Royalty Sales", ii)
            + getProjectionWSRWeek(4).getDataForShift("= Royalty Sales", ii)) / 4;
        setAverageForShift(ii, avg);
      }
    }
  }

  /**
   * @param week
   *          1-4
   * @return
   */
  public WSRMap getProjectionWSRWeek(int week)
  {
    return last4WeeksWSR[week - 1];
  }

  /**
   * @param index
   *          0-3
   * @return
   */
  public WSRMap getProjectionWSRIndex(int index)
  {
    return last4WeeksWSR[index];
  }

  /**
   * Should update average + buffer, proj
   * 
   * @param shift
   * @param value
   */
  public void setAverageForShift(int shift, double value)
  {
    average.set(shift - 1, value);
    updateAveragePlusBufferForShift(shift);
  }

  public void updateAveragePlusBufferForShift(int shift)
  {
    averagePlusBuffer.set(shift - 1, average.get(shift - 1)
        * (shift % 2 == 0 ? settings.get(PMBUFFER) : settings.get(AMBUFFER)));
    updateProjForShift(shift);
  }

  public void updateAllAveragePlusBuffer()
  {
    for (int ii = 0; ii < 14; ii++)
    {
      averagePlusBuffer.set(ii,
          average.get(ii) * (ii % 2 == 0 ? settings.get(AMBUFFER) : settings.get(PMBUFFER)));
    }
  }

  /**
   * @param shift
   */
  private void updateProjForShift(int shift)
  {
    int index = shift - 1;
    double proj = averagePlusBuffer.get(index) + catering.get(index) + sampling.get(index);
    projections.set(index, proj);
    if (shift % 2 == 1)
    {
      double pmProj = averagePlusBuffer.get(index + 1) + catering.get(index + 1)
          + sampling.get(index + 1);
      thawed.set(index, proj);
      percentage.set(index, proj * settings.get(BAKEDAT11));
      wheat.set(index, proj + pmProj);
    }
    else
    {
      double amProj = averagePlusBuffer.get(index - 1) + catering.get(index - 1)
          + sampling.get(index - 1);
      thawed.set(index, Math.max(0,
          proj - (proj * settings.get(BAKEDATSC)) - ((1 - settings.get(BAKEDAT11)) * amProj)));
      percentage.set(index, proj * settings.get(BAKEDATSC));
      wheat.set(index, proj);
    }
    // Slicing Pars
    updateAllSlicingPars("Cheese");
    updateAllSlicingPars("Ham");
    updateAllSlicingPars("Turkey");
    updateAllSlicingPars("Beef");
    updateAllSlicingPars("Salami");
    updateAllSlicingPars("Capicola");
    for (DataObserver dato : observers)
    {
      dato.toolBoxDataUpdated();
    }
  }

  private void updateAllSlicingPars(String name)
  {
    for (int index = 0; index < 14; index++)
    {
      int nextShiftIndex = JimmyCalendarUtil.convertToShiftNumber(index + 2) - 1;
      int nextNextShiftIndex = JimmyCalendarUtil.convertToShiftNumber(index + 3) - 1;
      slicingPars.get(index).get(name).put("msc",
          ((getLastCompletedWeekUPKWeek().getUPKItem(name).getAverageUPK() * projections.get(index))
              / 1000) / 3.307);
      slicingPars.get(index).get(name).put("gec",
          ((getLastCompletedWeekUPKWeek().getUPKItem(name).getAverageUPK()
              * (projections.get(nextShiftIndex) + projections.get(nextNextShiftIndex))) / 1000)
              / 3.307);
    }
  }

  public double getThawedDataForShift(int shift)
  {
    return thawed.get(shift - 1);
  }

  public double getThawedDataForIndex(int index)
  {
    return thawed.get(index);
  }

  public void addCateringOrder(CateringOrder cateringOrder)
  {
    cateringOrders.add(cateringOrder);
    if (JimmyCalendarUtil.isInCurrentWeek(new GregorianCalendar(), cateringOrder.getTime()))
    {
      catering.add(JimmyCalendarUtil.getShiftNumber(cateringOrder.getTime()) - 1,
          cateringOrder.getDollarValue());
      updateProjForShift(JimmyCalendarUtil.getShiftNumber(cateringOrder.getTime()));
    }
    for (DataObserver dato : observers)
    {
      dato.cateringOrderAdded(cateringOrder);
    }
  }

  public void removeCateringOrder(CateringOrder cateringOrder)
  {
    cateringOrders.remove(cateringOrder);
    Double eq = Double.valueOf(0);
    for (Double d : catering)
    {
      if (d.equals(cateringOrder.getDollarValue()))
        eq = d;
    }
    catering.remove(eq);
    for (DataObserver dato : observers)
    {
      dato.cateringOrderRemoved(cateringOrder);
    }
  }

  public ArrayList<CateringOrder> getCateringOrders()
  {
    return cateringOrders;
  }

  public void changeSetting(int setting, double val)
  {
    settings.put(setting, val);
    for (DataObserver dato : observers)
    {
      dato.toolBoxDataUpdated();
    }
  }

  public void addObserver(DataObserver observer)
  {
    observers.add(observer);
  }

  public double getProjectionDataForIndex(int ii)
  {
    return projections.get(ii);
  }

  public double getSetting(int setting)
  {
    return settings.get(setting);
  }

  public double getPercentageDataForIndex(int ii)
  {
    return percentage.get(ii);
  }

  public double getWheatDataForIndex(int ii)
  {
    return wheat.get(ii);
  }

  public double getAverageDataForIndex(int ii)
  {
    return average.get(ii);
  }

  public double getAveragePlusBufferData(int ii)
  {
    return averagePlusBuffer.get(ii);
  }

  public void setSamplingForShift(int shift, int val)
  {
    sampling.set(shift - 1, val);
    updateProjForShift(shift);
  }

  public UPKWeek getLastCompletedWeekUPKWeek()
  {
    return past6UPKWeeks.get(past6UPKWeeks.size() - 1);
  }

  public double getSlicingPars(String food, String dataType, int shift)
  {
    int index = shift - 1;
    return slicingPars.get(index).get(food).get(dataType);
  }

  public void setPast6UPKWeeks(ArrayList<UPKWeek> past6UPKWeeks)
  {
    this.past6UPKWeeks = past6UPKWeeks;
  }

  public ArrayList<UPKWeek> getPast6UPKMaps()
  {
    return past6UPKWeeks;
  }

  public double getProjectionsForShifts(int startShift, int endShift)
  {
    int startIndex = startShift - 1;
    int totalProj = 0;
    int ii = startIndex;
    while (ii != endShift)
    {
      if (ii > 13)
        ii = ii - 14;
      System.out.println(ii + " " + endShift);
      totalProj += projections.get(ii);
      ii++;
    }
    return totalProj;
  }

  /**
   * @param startShift
   *          am shift of day after order
   * @param endShift
   *          pm shift of day after order next
   * @param produceName
   * @param unit
   * @return
   */
  public double getProduceRequiredForShifts(int startShift, int endShift, String produceName,
      int unit)
  {
    System.out.println("OOP");
    double proj = getProjectionsForShifts(startShift, endShift);
    double upk;
    if (produceName.equals("Sprouts"))
      upk = getSetting(DataHub.SPROUT_UPK);
    else
      upk = getLastCompletedWeekUPKWeek().getUPKItem(produceName).getAverageUPK();
    double req = ((proj / 1000) * upk) / unit;
    return MathUtil.ceilHalf(req);
  }

  public void uploadAreaManagerPhoneAudit(AMPhoneAuditReader amPhoneAuditMap)
  {
    this.amPhoneAuditItem = amPhoneAuditMap.getItem();
  }

  public AMPhoneAuditItem getAMPhoneAudit()
  {
    return amPhoneAuditItem;
  }

  /**
   * @param type
   *          Inshop, Delivery, or Total
   * @param hour
   * @return
   */
  public double getAverageHourlySales(String type, int hour, boolean includeCatering)
  {
    double total = 0;
    for (HourlySalesDay hsd : getPast4HourlySales())
    {
      switch (type)
      {
        case "Total":
          total += hsd.getTotalInshopForHour(hour) + hsd.getTotalDeliveryForHour(hour);
          break;
        case "TotalInshop":
          total += hsd.getTotalInshopForHour(hour);
          break;
        case "TotalDelivery":
          total += hsd.getTotalDeliveryForHour(hour);
          break;
        case "EatIn":
          total += hsd.getEatInForHour(hour);
          break;
        case "Takeout":
          total += hsd.getTakeoutForHour(hour);
          break;
        case "PhonePickup":
          total += hsd.getPhonePickupForHour(hour);
          break;
        case "OnlinePickup":
          total += hsd.getOnlinePickupForHour(hour);
          break;
        case "PhoneDelivery":
          total += hsd.getPhoneDeliveryForHour(hour);
          break;
        case "OnlineDelivery":
          total += hsd.getOnlineDeliveryForHour(hour);
          break;
      }
    }
    // TODO total -= only the catering in that category
    if (!includeCatering)
      total -= getCateringForHour(hour);
    return total / getPast4HourlySales().size();
  }

  public ArrayList<HourlySalesDay> getPast4HourlySales()
  {
    ArrayList<HourlySalesDay> days = new ArrayList<HourlySalesDay>();
    GregorianCalendar gc = new GregorianCalendar();
    for (int ii = 0; ii < 4; ii++)
    {
      gc.add(Calendar.DAY_OF_YEAR, -7);
      days.add(getHourlySalesDay(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH),
          gc.get(Calendar.DAY_OF_MONTH)));
    }
    return days;
  }

  private double getCateringForHour(int hour)
  {
    double cat = 0;
    for (ArrayList<CateringTransaction> ac : past4DaysCatering)
    {
      for (CateringTransaction tran : ac)
      {
        if (tran.getTime().get(Calendar.HOUR_OF_DAY) == hour)
          cat += tran.getGrossAmount();
      }
    }
    return cat;
  }

  public void initializeTransientValues()
  {
    last4WeeksWSR = new WSRMap[4];
    observers = new ArrayList<DataObserver>();
  }

  public ArrayList<String> getWeeklySupplyItems()
  {
    return weeklySupplyItems;
  }

  public void addWeeklySupplyItem(String item)
  {
    weeklySupplyItems.add(item);
  }

  public void removeWeeklySupplyItem(String item)
  {
    weeklySupplyItems.remove(item);
  }

  private void setupWeeklySupplyItems()
  {
    if (weeklySupplyItems == null)
      weeklySupplyItems = new ArrayList<String>(FXCollections.observableArrayList("Clorox Bleach",
          "Windex Refill", "Windex Multi-Surface Vinegar", "Simple Green", "Scotch Brite pads",
          "Stainless Steel Polish", "Magic Erasers", "Dawn", "Hand Soap", "Toilet Bowl Cleaner",
          "Toilet Paper", "Morton Salt", "Snack Baggies", "Greased Lightning", "Mop Heads",
          "Printer Paper", "Goo Gone", "Bar Keeper's Friend", "Gloves", "Hydrogen Peroxide",
          "Band-aids", "Gauze Pads", "Burn Cream", "Neosporin", "Dasani", "Letter Envelopes",
          "Manilla Envelopes", "Staples (Standard)", "Staples (Bostich)", "Grease Pencil",
          "Sharpies", "Pens", "Blue Tape", "Sandwich Stickers", "Lightbulbs", "Broom", "Knives"));
  }

  private void setupManagerDBLs()
  {
    if (managerDBLs == null)
    {
      ManagerDBLReader mdr = new ManagerDBLReader(getClass().getResource("mgrdbls.txt").getPath());
      managerDBLs = mdr.getDBLs();
    }
  }

  public ArrayList<CompletableTask> getManagerDBLs()
  {
    return managerDBLs;
  }

  public ArrayList<CompletableTask> getCompleteOrIncompleteManagerDBLs(boolean completed)
  {
    ArrayList<CompletableTask> dbls = new ArrayList<CompletableTask>();
    for (CompletableTask mdbl : managerDBLs)
    {
      if (mdbl.isCompleted() == completed)
      {
        dbls.add(mdbl);
      }
    }
    return dbls;
  }

  public ArrayList<Manager> getManagers()
  {
    return managers;
  }

  public void addManager(Manager manager)
  {
    managers.add(manager);
    for (DataObserver dato : observers)
    {
      dato.toolBoxDataUpdated();
    }
  }

  public void removeManager(Manager manager)
  {
    managers.remove(manager);
    for (DataObserver dato : observers)
    {
      dato.toolBoxDataUpdated();
    }
  }

  public Manager getManager(String username, String password)
  {
    for (Manager m : getManagers())
    {
      if (m.getUsername().equals(username) && m.getPassword().equals(password))
        return m;
    }
    return null;
  }

  public void uploadAttendanceShifts(ArrayList<AttendanceShift> arrayList)
  {
    yesterdaysAttendanceShifts = arrayList;
  }

  public ArrayList<AttendanceShift> getAttendaceShiftsFromYesterday()
  {
    return yesterdaysAttendanceShifts;
  }

  public void setPast4DaysCatering(ArrayList<ArrayList<CateringTransaction>> last4DaysCatering)
  {
    this.past4DaysCatering = last4DaysCatering;
  }

  public void setLastYearWSR(WSRMap lastYearWSR)
  {
    this.lastYearWSR = lastYearWSR;
  }

  public WSRMap getLastYearWSR()
  {
    return lastYearWSR;
  }

  public double getWeekProj()
  {
    double proj = 0;
    for (int ii = 0; ii < 14; ii++)
    {
      proj += getProjectionDataForIndex(ii);
    }
    return proj;
  }

  public void setInventoryItemNames(ArrayList<String> items)
  {
    this.inventoryItemNames = items;
  }

  public ArrayList<String> getInventoryItemNames()
  {
    return inventoryItemNames;
  }

  public void setInventoryItems(ArrayList<InventoryItem> items)
  {
    this.inventoryItems = items;
  }

  public ArrayList<InventoryItem> getInventoryItems()
  {
    return inventoryItems;
  }

  public InventoryItem getInventoryItem(String name)
  {
    for (InventoryItem ii : inventoryItems)
    {
      if (name.equals(ii.getName()))
        return ii;
    }
    return null;
  }

  public void setCurrentWeekSummary(WeeklySummaryItem item)
  {
    this.weeklySummaryItem = item;
  }

  public WeeklySummaryItem getCurrentWeeklySummary()
  {
    return weeklySummaryItem;
  }

  public HourlySalesDay getHourlySalesDay(int year, int month, int day)
  {
    try
    {
      return hourlySalesCalendar.get(year).get(month).get(day);
    }
    catch (NullPointerException npe)
    {
      return null;
    }
  }

  public void addHourlySalesDay(HourlySalesDay hsd, int year, int month, int day)
  {
    if (hourlySalesCalendar.get(year) == null)
    {
      hourlySalesCalendar.put(year, new HashMap<Integer, HashMap<Integer, HourlySalesDay>>());
    }
    if (hourlySalesCalendar.get(year).get(month) == null)
    {
      hourlySalesCalendar.get(year).put(month, new HashMap<Integer, HourlySalesDay>());
    }
    hourlySalesCalendar.get(year).get(month).put(day, hsd);
  }

  public TrendSheetReader getTrendSheetForYear(Integer value)
  {
    return trendSheetCalendar.get(value);
  }

  public TrendSheetReader getCurrentYearTrendSheet()
  {
    GregorianCalendar gc = new GregorianCalendar();
    return getTrendSheetForYear(gc.get(Calendar.YEAR));
  }

  public void setCurrentYearTrendSheet(TrendSheetReader trendSheetReader)
  {
    GregorianCalendar gc = new GregorianCalendar();
    trendSheetCalendar.put(gc.get(Calendar.YEAR), trendSheetReader);
  }

  public void setTrendSheet(Integer value, TrendSheetReader trendSheetReader)
  {
    trendSheetCalendar.put(value, trendSheetReader);
  }

  public ArrayList<String> getMissingLast4HourlySales()
  {
    ArrayList<String> missingDates = new ArrayList<String>();
    GregorianCalendar gc = new GregorianCalendar();
    for(int ii = 0 ; ii < 4; ii++)
    {
      gc.add(Calendar.DAY_OF_YEAR, -7);
      HourlySalesDay hsd = getHourlySalesDay(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc.get(Calendar.DAY_OF_MONTH));
      if(hsd == null)
      {
        String date = String.format("%02d-%02d-%04d", gc.get(Calendar.MONTH), gc.get(Calendar.DAY_OF_MONTH), gc.get(Calendar.YEAR));
        missingDates.add(date);
      }
    }
    return missingDates;
  }
}
