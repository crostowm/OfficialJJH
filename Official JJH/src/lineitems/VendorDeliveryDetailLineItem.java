package lineitems;

import java.util.GregorianCalendar;

public class VendorDeliveryDetailLineItem
{
  private GregorianCalendar deliveryDate;
  private double qtyCase, qtyEa;
  private String desc, unit, invoiceNum, vendorNum;
  /**
   * @param deliveryDate
   * @param invoiceNum2
   * @param vendorNum
   * @param qtyCase
   * @param qtyEa
   * @param desc
   * @param unit
   */
  public VendorDeliveryDetailLineItem(GregorianCalendar deliveryDate, String invoiceNum,
      String vendorNum, double qtyCase, double qtyEa, String desc, String unit)
  {
    this.deliveryDate = deliveryDate;
    this.invoiceNum = invoiceNum;
    this.vendorNum = vendorNum;
    this.qtyCase = qtyCase;
    this.qtyEa = qtyEa;
    this.desc = desc;
    this.unit = unit;
  }
  public GregorianCalendar getDeliveryDate()
  {
    return deliveryDate;
  }
  public String getInvoiceNum()
  {
    return invoiceNum;
  }
  public String getVendorItemNum()
  {
    return vendorNum;
  }
  public double getQtyCase()
  {
    return qtyCase;
  }
  public double getQtyEa()
  {
    return qtyEa;
  }
  public String getDesc()
  {
    return desc;
  }
  public String getUnit()
  {
    return unit;
  }
}
