package util;

import java.util.ArrayList;

import observers.DataObserver;
import readers.WSRMap;

public class DataHub
{
  private static ArrayList<DataObserver> observers = new ArrayList<DataObserver>();
  private static ArrayList<CateringOrder> cateringOrders = new ArrayList<CateringOrder>();
  private static WSRMap[] last4WeeksWSR = new WSRMap[4];
  
  public static void addCateringOrder(CateringOrder cateringOrder)
  {
    cateringOrders.add(cateringOrder);
    for(DataObserver dato: observers)
    {
      dato.cateringOrderAdded(cateringOrder);
    }
  }
  
  public static void removeCateringOrder(CateringOrder cateringOrder)
  {
    cateringOrders.remove(cateringOrder);
    for(DataObserver dato: observers)
    {
      dato.cateringOrderRemoved(cateringOrder);
    }
  }
  
  public static ArrayList<CateringOrder> getCateringOrders()
  {
    return cateringOrders;
  }
  
  public static void addObserver(DataObserver observer)
  {
    observers.add(observer);
  }
  
  /**
   * @param wsr
   * @param week 1-4
   */
  public static void addWSRMapForProjections(WSRMap wsr, int week)
  {
    last4WeeksWSR[week-1] = wsr;
    if(last4WeeksWSR[0] != null && last4WeeksWSR[1] != null && last4WeeksWSR[2] != null && last4WeeksWSR[3] != null)
      for(DataObserver dato: observers)
      {
        dato.projectionDataReady();
      }
  }
  
  /**
   * @param week 1-4
   * @return
   */
  public static WSRMap getProjectionWSR(int week)
  {
    return last4WeeksWSR[week-1];
  }
}
