package lineitems;

import java.io.Serializable;
import java.util.ArrayList;

public class HourlySalesDay extends ArrayList<HourlySalesItem> implements Serializable
{
  private static final long serialVersionUID = 4743977471918286320L;

  public double getTotalInshopForHour(int hour)
  {
    HourlySalesItem item = get(hour);
    return item.getTakeoutValue() + item.getPickupValue() + item.getEatInValue()
        + item.getOnlinePickupValue();
  }

  public double getTotalDeliveryForHour(int hour)
  {
    HourlySalesItem item = get(hour);
    return item.getDeliveryValue() + item.getOnlineDeliveryValue();
  }

  public double getOnlineDeliveryForHour(int hour)
  {
    HourlySalesItem item = get(hour);
    return item.getOnlineDeliveryValue();
  }

  public double getPhoneDeliveryForHour(int hour)
  {
    HourlySalesItem item = get(hour);
    return item.getDeliveryValue();
  }

  public double getTakeoutForHour(int hour)
  {
    HourlySalesItem item = get(hour);
    return item.getTakeoutValue();
  }

  public double getPhonePickupForHour(int hour)
  {
    HourlySalesItem item = get(hour);
    return item.getPickupValue();
  }

  public double getOnlinePickupForHour(int hour)
  {
    HourlySalesItem item = get(hour);
    return item.getOnlinePickupValue();
  }

  public double getEatInForHour(int hour)
  {
    HourlySalesItem item = get(hour);
    return item.getEatInValue();
  }
}
