package util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import observers.DataObserver;
import readers.WSRMap;

public class DataHub implements Serializable
{
  public static final int AMBUFFER = 1, PMBUFFER = 2, BTV = 3, B9TV = 4, WLV = 5, BAKEDAT11 = 6,
      BAKEDATSC = 7, LETTUCEBV = 8, TOMATOBV = 9, ONIONBV = 10, CUCUMBERBV = 11, PICKLEBV = 12,
      STORESC_TIME = 13;
  private static final long serialVersionUID = 2092175547020407363L;
  private transient ArrayList<DataObserver> observers = new ArrayList<DataObserver>();
  private ArrayList<CateringOrder> cateringOrders = new ArrayList<CateringOrder>();
  private ArrayList<Double> average = new ArrayList<Double>();
  private ArrayList<Double> averagePlusBuffer = new ArrayList<Double>();
  private ArrayList<Double> catering = new ArrayList<Double>();
  private ArrayList<Integer> sampling = new ArrayList<Integer>();
  private ArrayList<Double> projections = new ArrayList<Double>();
  private ArrayList<Double> thawed = new ArrayList<Double>();
  private ArrayList<Double> percentage = new ArrayList<Double>();
  private ArrayList<Double> wheat = new ArrayList<Double>();
  private WSRMap[] last4WeeksWSR = new WSRMap[4];
  private HashMap<Integer, Double> settings = new HashMap<Integer, Double>();

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
      for (DataObserver dato : observers)
      {
        dato.projectionDataReady();
      }
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
    averagePlusBuffer.set(shift - 1,
        value * (shift % 2 == 0 ? settings.get(PMBUFFER) : settings.get(AMBUFFER)));
    updateProjForShift(shift);
    for (DataObserver dato : observers)
    {
      dato.averageUpdatedForShift(shift, value);
    }
  }

  /**
   * @param shift
   */
  private void updateProjForShift(int shift)
  {
    int index = shift - 1;
    double proj = averagePlusBuffer.get(index) + catering.get(index) + sampling.get(index);
    System.out.println(proj);
    projections.set(index,
        proj);
    if (shift % 2 == 1)
    {
      double pmProj = averagePlusBuffer.get(index + 1) + catering.get(index + 1) + sampling.get(index + 1);
      thawed.set(index, proj);
      percentage.set(index, proj * settings.get(BAKEDAT11));
      //wheat
      wheat.set(index, proj + pmProj);
      //produce
    }
    else
    {
      double amProj = averagePlusBuffer.get(index - 1) + catering.get(index - 1) + sampling.get(index - 1);
      thawed.set(index, proj - (proj * settings.get(BAKEDATSC)) - ((1 - settings.get(BAKEDAT11)) * amProj));
      percentage.set(index, proj * settings.get(BAKEDATSC));
    }
  }

  public double getThawedDataForShift(int shift)
  {
    return thawed.get(shift-1);
  }
  
  public double getThawedDataForIndex(int index)
  {
    return thawed.get(index);
  }
  public void addCateringOrder(CateringOrder cateringOrder)
  {
    cateringOrders.add(cateringOrder);
    for (DataObserver dato : observers)
    {
      dato.cateringOrderAdded(cateringOrder);
    }
  }

  public void removeCateringOrder(CateringOrder cateringOrder)
  {
    cateringOrders.remove(cateringOrder);
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
}
