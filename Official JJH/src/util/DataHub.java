package util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;

import observers.DataObserver;
import readers.UPKMap;
import readers.WSRMap;

public class DataHub implements Serializable
{
  public static final int AMBUFFER = 1, PMBUFFER = 2, BTV = 3, B9TV = 4, WLV = 5, BAKEDAT11 = 6,
      BAKEDATSC = 7, LETTUCEBV = 8, TOMATOBV = 9, ONIONBV = 10, CUCUMBERBV = 11, PICKLEBV = 12,
      STORESC_TIME = 13;
  private static final long serialVersionUID = 2092175547020407363L;
  private transient ArrayList<DataObserver> observers = new ArrayList<DataObserver>();
  private transient WSRMap[] last4WeeksWSR = new WSRMap[4];
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
  private UPKMap currentUPKMap;
  private ArrayList<UPKMap> past5UPKMaps;
  private ArrayList<HashMap<String, HashMap<String, Double>>> slicingPars = new ArrayList<HashMap<String, HashMap<String, Double>>>();

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

  }

  /**
   * @param wsr
   * @param week
   *          1-4
   */
  public void addWSRMapForProjections(WSRMap wsr, int week)
  {
    last4WeeksWSR[week - 1] = wsr;
    if (last4WeeksWSR[0] != null && last4WeeksWSR[1] != null && last4WeeksWSR[2] != null
        && last4WeeksWSR[3] != null)
      ;
    // All proj ready
  }

  /**
   * @param week
   *          1-4
   * @return
   */
  public WSRMap getProjectionWSR(int week)
  {
    return last4WeeksWSR[week - 1];
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
    for(int index = 0; index < 14; index++)
    {
    int nextShiftIndex = index + 1 >= 14 ? index - 13 : index + 1;
    int nextNextShiftIndex = index + 2 >= 14 ? index - 12 : index + 2;
    int nextNextNextShiftIndex = index + 3 >= 14 ? index - 11 : index + 3;
    slicingPars.get(index).get(name).put("msc",
        ((currentUPKMap.getData(UPKMap.FOOD, name, UPKMap.AVERAGE_UPK) * projections.get(index))
            / 1000) / 3.307);
    slicingPars.get(index).get(name).put("gec",
        ((currentUPKMap.getData(UPKMap.FOOD, name, UPKMap.AVERAGE_UPK)
            * (projections.get(nextShiftIndex) + projections.get(nextNextShiftIndex))) / 1000)
            / 3.307);
    slicingPars.get(index).get(name).put("msn",
        ((currentUPKMap.getData(UPKMap.FOOD, name, UPKMap.AVERAGE_UPK)
            * projections.get(nextShiftIndex)) / 1000) / 3.307);
    slicingPars.get(index).get(name).put("gen",
        ((currentUPKMap.getData(UPKMap.FOOD, name, UPKMap.AVERAGE_UPK)
            * (projections.get(nextNextShiftIndex) + projections.get(nextNextNextShiftIndex)))
            / 1000) / 3.307);
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
    if(JimmyCalendarUtil.isInCurrentWeek(new GregorianCalendar(), cateringOrder.getTime()))
    {
      catering.add(JimmyCalendarUtil.getShiftNumber(cateringOrder.getTime(), settings.get(DataHub.STORESC_TIME).intValue()) - 1, cateringOrder.getDollarValue());
      updateProjForShift(JimmyCalendarUtil.getShiftNumber(cateringOrder.getTime(), settings.get(DataHub.STORESC_TIME).intValue()));
    }
    for (DataObserver dato : observers)
    {
      dato.cateringOrderAdded(cateringOrder);
    }
  }

  public void removeCateringOrder(CateringOrder cateringOrder)
  {
    cateringOrders.remove(cateringOrder);
    //TODO Check for a better way to do this
    Double eq = Double.valueOf(0);
    for(Double d: catering)
    {
      if(d.equals(cateringOrder.getDollarValue()))
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

  public void setCurrentUPKMap(UPKMap upkMap)
  {
    this.currentUPKMap = upkMap;
    for(DataObserver dato: observers)
    {
      dato.upkSet();
    }
  }

  public UPKMap getCurrentUPKMap()
  {
    return currentUPKMap;
  }
  
  public double getSlicingPars(String food, String dataType, int shift)
  {
    int index = shift - 1;
    return slicingPars.get(index).get(food).get(dataType);
  }

  
  public void setPast5UPKMaps(ArrayList<UPKMap> past5UPKMaps)
  {
    this.past5UPKMaps = past5UPKMaps;
  }
  
  public ArrayList<UPKMap> getPast5UPKMaps()
  {
    return past5UPKMaps;
  }
}
