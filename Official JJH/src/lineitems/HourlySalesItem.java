package lineitems;

public class HourlySalesItem
{
  private double takeoutValue, pickupValue, deliveryValue, eatInValue, onlinePickupValue, onlineDeliveryValue, total$, totalPercent, totalCount;
  private int takeoutCount, pickupCount, deliveryCount, eatInCount, onlinePickupCount, onlineDeliveryCount;
  /**
   * @param takeoutValue
   * @param pickupValue
   * @param deliveryValue
   * @param eatInValue
   * @param onlinePickupValue
   * @param onlineDeliveryValue
   * @param total$
   * @param totalPercent
   * @param totalCount
   * @param takeoutCount
   * @param pickupCount
   * @param deliveryCount
   * @param eatInCount
   * @param onlinePickupCount
   * @param onlineDeliveryCount
   */
  public HourlySalesItem(double takeoutValue, double pickupValue, double deliveryValue,
      double eatInValue, double onlinePickupValue, double onlineDeliveryValue, double total$,
      double totalPercent, double totalCount, int takeoutCount, int pickupCount, int deliveryCount,
      int eatInCount, int onlinePickupCount, int onlineDeliveryCount)
  {
    this.takeoutValue = takeoutValue;
    this.pickupValue = pickupValue;
    this.deliveryValue = deliveryValue;
    this.eatInValue = eatInValue;
    this.onlinePickupValue = onlinePickupValue;
    this.onlineDeliveryValue = onlineDeliveryValue;
    this.total$ = total$;
    this.totalPercent = totalPercent;
    this.totalCount = totalCount;
    this.takeoutCount = takeoutCount;
    this.pickupCount = pickupCount;
    this.deliveryCount = deliveryCount;
    this.eatInCount = eatInCount;
    this.onlinePickupCount = onlinePickupCount;
    this.onlineDeliveryCount = onlineDeliveryCount;
  }
  public double getTakeoutValue()
  {
    return takeoutValue;
  }
  public double getPickupValue()
  {
    return pickupValue;
  }
  public double getDeliveryValue()
  {
    return deliveryValue;
  }
  public double getEatInValue()
  {
    return eatInValue;
  }
  public double getOnlinePickupValue()
  {
    return onlinePickupValue;
  }
  public double getOnlineDeliveryValue()
  {
    return onlineDeliveryValue;
  }
  public double getTotal$()
  {
    return total$;
  }
  public double getTotalPercent()
  {
    return totalPercent;
  }
  public double getTotalCount()
  {
    return totalCount;
  }
  public int getTakeoutCount()
  {
    return takeoutCount;
  }
  public int getPickupCount()
  {
    return pickupCount;
  }
  public int getDeliveryCount()
  {
    return deliveryCount;
  }
  public int getEatInCount()
  {
    return eatInCount;
  }
  public int getOnlinePickupCount()
  {
    return onlinePickupCount;
  }
  public int getOnlineDeliveryCount()
  {
    return onlineDeliveryCount;
  }
  
  
}
