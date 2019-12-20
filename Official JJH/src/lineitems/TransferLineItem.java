package lineitems;

import java.util.GregorianCalendar;

public class TransferLineItem
{
  private String fromStore, unit;
  private GregorianCalendar tranDate;
  private int itemID;
  private double qtyEa;
  /**
   * @param fromStore
   * @param unit
   * @param tranDate
   * @param itemID
   * @param qtyEa
   */
  public TransferLineItem(String fromStore, String unit, GregorianCalendar tranDate, int itemID,
      double qtyEa)
  {
    this.fromStore = fromStore;
    this.unit = unit;
    this.tranDate = tranDate;
    this.itemID = itemID;
    this.qtyEa = qtyEa;
  }
  public String getFromStore()
  {
    return fromStore;
  }
  public String getUnit()
  {
    return unit;
  }
  public GregorianCalendar getTranDate()
  {
    return tranDate;
  }
  public int getItemID()
  {
    return itemID;
  }
  public double getQtyEa()
  {
    return qtyEa;
  }
}
