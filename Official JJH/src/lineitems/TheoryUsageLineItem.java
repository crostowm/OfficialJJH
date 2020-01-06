package lineitems;

public class TheoryUsageLineItem
{
  private int menuItemNum;
  private String desc, unit;
  private double qtySold, totalQty, portion;
  /**
   * @param menuItemNum
   * @param desc
   * @param unit
   * @param qtySold
   * @param totalQty
   * @param portion
   */
  public TheoryUsageLineItem(int menuItemNum, String desc, String unit, double qtySold,
      double totalQty, double portion)
  {
    this.menuItemNum = menuItemNum;
    this.desc = desc.trim();
    this.unit = unit;
    this.qtySold = qtySold;
    this.totalQty = totalQty;
    this.portion = portion;
  }
  public int getMenuItemNum()
  {
    return menuItemNum;
  }
  public String getDesc()
  {
    return desc;
  }
  public String getUnit()
  {
    return unit;
  }
  public double getQtySold()
  {
    return qtySold;
  }
  public double getTotalQty()
  {
    return totalQty;
  }
  public double getPortion()
  {
    return portion;
  }
}
