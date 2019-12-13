package lineitems;

public class ItemUsageLineItem
{
  private double begInv, totPurch, totTrans, endInv, actUsage;

  /**
   * @param begInv
   * @param totPurch
   * @param totTrans
   * @param endInv
   * @param actUsage
   */
  public ItemUsageLineItem(double begInv, double totPurch, double totTrans, double endInv,
      double actUsage)
  {
    this.begInv = begInv;
    this.totPurch = totPurch;
    this.totTrans = totTrans;
    this.endInv = endInv;
    this.actUsage = actUsage;
  }

  public double getBegInv()
  {
    return begInv;
  }

  public double getTotPurch()
  {
    return totPurch;
  }

  public double getTotTrans()
  {
    return totTrans;
  }

  public double getEndInv()
  {
    return endInv;
  }

  public double getActUsage()
  {
    return actUsage;
  }
}
