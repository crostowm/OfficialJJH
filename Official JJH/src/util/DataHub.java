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
import lineitems.WeeklySalesWeek;
import lineitems.WeeklySummaryItem;
import personnel.Manager;
import readers.AMPhoneAuditReader;
import readers.ManagerDBLReader;
import readers.TrendSheetReader;
import readers.WSRReader;
import week_data.AveragePlusBufferWeek;
import week_data.DualValWeekData;
import week_data.PercentageWeek;
import week_data.ProjectionWeek;
import week_data.SlicingWeek;
import week_data.ThawedWeek;
import week_data.WeekData;
import week_data.WheatWeek;

public class DataHub implements Serializable
{
  private static final long serialVersionUID = 2092175547020407363L;
  /**
   * index 5 is last completed week index 0 is earliest week
   */
  // private transient ArrayList<UPKWeek> past6UPKWeeks;
  private transient ArrayList<ArrayList<CateringTransaction>> past4DaysCatering;
  private transient AMPhoneAuditItem amPhoneAuditItem;
  private transient ArrayList<AttendanceShift> yesterdaysAttendanceShifts;
  private transient WSRReader lastYearWSR;
  private transient ArrayList<InventoryItem> inventoryItems;
  private transient WeeklySummaryItem weeklySummaryItem;
  private ArrayList<String> inventoryItemNames;
  private ArrayList<Manager> managers = new ArrayList<Manager>();
  private ArrayList<CateringOrder> cateringOrders = new ArrayList<CateringOrder>();
  private WeekData average;
  private AveragePlusBufferWeek averagePlusBufferWeek;
  private WeekData cateringWeek;
  private WeekData sampleWeek = new WeekData();
  private ProjectionWeek projectionWeek;
  private ThawedWeek thawed;
  private PercentageWeek percentage;
  private WheatWeek wheat;
  private Setting settings;
  private SlicingWeek slicingPars;
  private ArrayList<String> weeklySupplyItems;
  private ArrayList<CompletableTask> managerDBLs;
  // Year-Month-Day
  private HashMap<Integer, HashMap<Integer, HashMap<Integer, HourlySalesDay>>> hourlySalesCalendar;
  // Year
  private HashMap<Integer, TrendSheetReader> trendSheetCalendar;
  // Year-Week
  private HashMap<Integer, HashMap<Integer, WeeklySalesWeek>> weeklySalesCalendar;
  // Year-Week
  private HashMap<Integer, HashMap<Integer, UPKWeek>> upkCalendar;

  public DataHub()
  {
    average = new WeekData();
    averagePlusBufferWeek = new AveragePlusBufferWeek();
    sampleWeek = new WeekData();
    cateringWeek = new WeekData();
    projectionWeek = new ProjectionWeek();
    thawed = new ThawedWeek(Setting.BTV);
    percentage = new PercentageWeek(Setting.BTV);
    wheat = new WheatWeek(Setting.WLV);
    settings = new Setting();
    slicingPars = new SlicingWeek();

    setupWeeklySupplyItems();
    setupManagerDBLs();
    hourlySalesCalendar = new HashMap<Integer, HashMap<Integer, HashMap<Integer, HourlySalesDay>>>();
    trendSheetCalendar = new HashMap<Integer, TrendSheetReader>();
    weeklySalesCalendar = new HashMap<Integer, HashMap<Integer, WeeklySalesWeek>>();
    upkCalendar = new HashMap<Integer, HashMap<Integer, UPKWeek>>();
  }

  public void newSlice()
  {
    slicingPars = new SlicingWeek();
  }
  
  public void addCateringOrder(CateringOrder cateringOrder)
  {
    cateringOrders.add(cateringOrder);
    if (JimmyCalendarUtil.isInCurrentWeek(cateringOrder.getTime()))
    {
      cateringWeek.setDataForShift(JimmyCalendarUtil.getShiftNumber(cateringOrder.getTime()),
          cateringWeek.getDataForShift(JimmyCalendarUtil.getShiftNumber(cateringOrder.getTime()))
              + cateringOrder.getDollarValue());
    }
    cateringWeek.notify(0);
  }

  public void removeCateringOrder(CateringOrder cateringOrder)
  {
    cateringOrders.remove(cateringOrder);
    if (JimmyCalendarUtil.isInCurrentWeek(cateringOrder.getTime()))
    {
      cateringWeek.setDataForShift(JimmyCalendarUtil.getShiftNumber(cateringOrder.getTime()),
          cateringWeek.getDataForShift(JimmyCalendarUtil.getShiftNumber(cateringOrder.getTime()))
              - cateringOrder.getDollarValue());
    }
    cateringWeek.notify(0);
  }

  public ArrayList<CateringOrder> getCateringOrders()
  {
    return cateringOrders;
  }

  public UPKWeek getLastCompletedWeekUPKWeek()
  {
    int[][] lastWeek = JimmyCalendarUtil.getLastXWeeksInYearWeekPairs(1);
    return upkCalendar.get(lastWeek[0][0]).get(lastWeek[0][1]);
  }

  public ArrayList<UPKWeek> getPast6UPKMaps()
  {
    ArrayList<UPKWeek> weeks = new ArrayList<UPKWeek>();
    int[][] last6Weeks = JimmyCalendarUtil.getLastXWeeksInYearWeekPairs(6);
    for (int ii = 0; ii < last6Weeks.length; ii++)
    {
      weeks.add(upkCalendar.get(last6Weeks[ii][0]).get(last6Weeks[ii][1]));
    }
    return weeks;
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
      totalProj += projectionWeek.getDataForIndex(ii);
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
    double proj = getProjectionsForShifts(startShift, endShift);
    double upk = getLastCompletedWeekUPKWeek().getUPKItem(produceName).getAverageUPK();
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
      days.add(getHourlySalesDay(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH) + 1,
          gc.get(Calendar.DAY_OF_MONTH)));
    }
    return days;
  }

  private double getCateringForHour(int hour)
  {
    if(past4DaysCatering == null)
      return 0;
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
      ManagerDBLReader mdr = new ManagerDBLReader("mgrdbls.txt");
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
  }

  public void removeManager(Manager manager)
  {
    managers.remove(manager);
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

  public void setLastYearWSR(WSRReader lastYearWSR)
  {
    this.lastYearWSR = lastYearWSR;
  }

  public WSRReader getLastYearWSR()
  {
    return lastYearWSR;
  }

  public double getWeekProj()
  {
    double proj = 0;
    for (int ii = 0; ii < 14; ii++)
    {
      proj += projectionWeek.getDataForIndex(ii);
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
    for (int ii = 0; ii < 4; ii++)
    {
      gc.add(Calendar.DAY_OF_YEAR, -7);
      HourlySalesDay hsd = getHourlySalesDay(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH) + 1,
          gc.get(Calendar.DAY_OF_MONTH));
      if (hsd == null)
      {
        String date = String.format("%02d-%02d-%04d", gc.get(Calendar.MONTH) + 1,
            gc.get(Calendar.DAY_OF_MONTH), gc.get(Calendar.YEAR));
        missingDates.add(date);
      }
    }
    for (String s : missingDates)
    {
      System.out.println("Missing hourly " + s);
    }
    return missingDates;
  }

  public void addWSRWeek(WeeklySalesWeek weeklySalesWeek)
  {
    if (weeklySalesCalendar.get(weeklySalesWeek.getYear()) == null)
      weeklySalesCalendar.put(weeklySalesWeek.getYear(), new HashMap<Integer, WeeklySalesWeek>());
    System.out.println("Adding WSR for year " + weeklySalesWeek.getYear() + " week "
        + weeklySalesWeek.getWeekNumber());
    weeklySalesCalendar.get(weeklySalesWeek.getYear()).put(weeklySalesWeek.getWeekNumber(),
        weeklySalesWeek);
    System.out.println("UAA");
    updateAllAverages();
  }

  private void updateAllAverages()
  {
    int[][] pairs = JimmyCalendarUtil.getLastXWeeksInYearWeekPairs(4);
    boolean all4WSRUploaded = true;
    for (int yw = 0; yw < 4; yw++)
    {
      if (weeklySalesCalendar.get(pairs[yw][0]) == null
          || weeklySalesCalendar.get(pairs[yw][0]).get(pairs[yw][1]) == null)
      {
        all4WSRUploaded = false;
        System.out.println("Missing the week " + pairs[3][1] + " to average for projections");
      }
    }

    if (all4WSRUploaded)
    {
      ArrayList<Double> tempAvg = new ArrayList<Double>();
      for (int ii = 0; ii < 14; ii++)
      {
        tempAvg.add(0.0);
      }
      for (int yw = 0; yw < 4; yw++)
      {
        System.out.println("Week " + (yw + 1));
        for (int ii = 0; ii < 14; ii++)
        {
          double avgForShift = weeklySalesCalendar.get(pairs[yw][0]).get(pairs[yw][1])
              .getLineItem("= Royalty Sales").getDataForIndex(ii);
          System.out.println("Shift " + (ii + 1) + ": " + tempAvg.get(ii));
          tempAvg.set(ii, tempAvg.get(ii) + avgForShift);
        }
      }
      for (int ii = 0; ii < 14; ii++)
      {
        average.setDataForIndex(ii, tempAvg.get(ii) / 4);
      }
    }
  }

  public HashMap<Integer, HashMap<Integer, WeeklySalesWeek>> getWeeklySalesCalendar()
  {
    return weeklySalesCalendar;
  }

  public HashMap<Integer, HashMap<Integer, UPKWeek>> getUPKCalendar()
  {
    return upkCalendar;
  }

  public WeeklySalesWeek getWSRWeek(int year, int week)
  {
    return weeklySalesCalendar.get(year).get(week);
  }

  public ArrayList<String> getSavedWeeklySales()
  {
    ArrayList<String> savedWeeks = new ArrayList<String>();
    int year = JimmyCalendarUtil.getCurrentYear() - 1;
    int week = 1;
    while (year <= JimmyCalendarUtil.getCurrentYear())
    {
      if (getWeeklySalesCalendar().get(year) == null)
      {
        week = 1;
        year++;
        continue;
      }
      if (getWeeklySalesCalendar().get(year).get(week) != null)
        savedWeeks.add(year + " : " + week);
      if (week == 53)
      {
        week = 1;
        year++;
      }
      else
        week++;
    }
    return savedWeeks;
  }

  public ArrayList<String> getSavedUPKs()
  {
    ArrayList<String> savedWeeks = new ArrayList<String>();
    int year = JimmyCalendarUtil.getCurrentYear() - 1;
    int week = 1;
    while (year <= JimmyCalendarUtil.getCurrentYear())
    {
      if (getUPKCalendar().get(year) == null)
      {
        week = 1;
        year++;
        continue;
      }
      if (getUPKCalendar().get(year).get(week) != null)
        savedWeeks.add(year + " : " + week);
      if (week == 53)
      {
        week = 1;
        year++;
      }
      else
        week++;
    }
    return savedWeeks;
  }

  public ArrayList<int[]> getMissingOfLast6UPKYearWeekPairs()
  {
    ArrayList<int[]> missingWeeks = new ArrayList<int[]>();
    for (int ii = 0; ii < 6; ii++)
    {
      if (getUPKCalendar().get(JimmyCalendarUtil.getLastXWeeksInYearWeekPairs(6)[ii][0]) == null
          || getUPKCalendar().get(JimmyCalendarUtil.getLastXWeeksInYearWeekPairs(6)[ii][0])
              .get(JimmyCalendarUtil.getLastXWeeksInYearWeekPairs(6)[ii][1]) == null)
        missingWeeks.add(JimmyCalendarUtil.getLastXWeeksInYearWeekPairs(6)[ii]);
    }
    return missingWeeks;
  }

  public ArrayList<int[]> getMissingOfLast41WSRYearWeekPairs()
  {
    ArrayList<int[]> missingWeeks = new ArrayList<int[]>();
    if (getWeeklySalesCalendar().get(JimmyCalendarUtil.getCurrentYear() - 1) == null
        || getWeeklySalesCalendar().get(JimmyCalendarUtil.getCurrentYear() - 1)
            .get(JimmyCalendarUtil.getCurrentWeek()) == null)
      missingWeeks.add(
          new int[] {JimmyCalendarUtil.getCurrentYear() - 1, JimmyCalendarUtil.getCurrentWeek()});
    for (int ii = 0; ii < 4; ii++)
    {
      if (getWeeklySalesCalendar()
          .get(JimmyCalendarUtil.getLastXWeeksInYearWeekPairs(4)[ii][0]) == null
          || getWeeklySalesCalendar().get(JimmyCalendarUtil.getLastXWeeksInYearWeekPairs(4)[ii][0])
              .get(JimmyCalendarUtil.getLastXWeeksInYearWeekPairs(4)[ii][1]) == null)
        missingWeeks.add(JimmyCalendarUtil.getLastXWeeksInYearWeekPairs(4)[ii]);
    }
    return missingWeeks;
  }

  public void setUPKWeek(int year, int week, UPKWeek wk)
  {
    if (upkCalendar.get(year) == null)
      upkCalendar.put(year, new HashMap<Integer, UPKWeek>());
    upkCalendar.get(year).put(week, wk);
  }

  public AveragePlusBufferWeek getAveragePlusBufferWeek()
  {
    return averagePlusBufferWeek;
  }

  public WeekData getAverageWeek()
  {
    return average;
  }

  public ProjectionWeek getProjectionWeek()
  {
    return projectionWeek;
  }

  public WeekData getSampleWeek()
  {
    return sampleWeek;
  }
  
  public WeekData getCateringWeek()
  {
    return cateringWeek;
  }

  public Setting getSettings()
  {
    return settings;
  }

  public DualValWeekData getThawedWeek()
  {
    return thawed;
  }

  public DualValWeekData getPercentageWeek()
  {
    return percentage;
  }
  
  public DualValWeekData getWheatWeek()
  {
    return wheat;
  }
  
  public SlicingWeek getSlicingPars()
  {
    return slicingPars;
  }

  public void setupObservers()
  {
    average.addObserver(averagePlusBufferWeek);
    averagePlusBufferWeek.addObserver(projectionWeek);
    sampleWeek.addObserver(projectionWeek);
    cateringWeek.addObserver(projectionWeek);
    projectionWeek.addObserver(slicingPars);
    projectionWeek.addObserver(percentage);
    projectionWeek.addObserver(thawed);
    projectionWeek.addObserver(wheat);
  }

}
