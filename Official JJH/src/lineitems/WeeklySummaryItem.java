package lineitems;

public class WeeklySummaryItem
{
  private double mgrLaborPerc, mgrLabor$, inshopLaborPer,
  inshopLabor$, driverLaborPerc, driverLabor$, taxLaborPerc, taxLabor$, dmrLaborPerc,
  dmrLabor$;

  /**
   * @param mgrLaborPerc
   * @param mgrLabor$
   * @param inshopLaborPer
   * @param inshopLabor$
   * @param driverLaborPerc
   * @param driverLabor$
   * @param taxLaborPerc
   * @param taxLabor$
   * @param dmrLaborPerc
   * @param dmrLabor$
   */
  public WeeklySummaryItem(double mgrLaborPerc, double mgrLabor$, double inshopLaborPer,
      double inshopLabor$, double driverLaborPerc, double driverLabor$, double taxLaborPerc,
      double taxLabor$, double dmrLaborPerc, double dmrLabor$)
  {
    this.mgrLaborPerc = mgrLaborPerc;
    this.mgrLabor$ = mgrLabor$;
    this.inshopLaborPer = inshopLaborPer;
    this.inshopLabor$ = inshopLabor$;
    this.driverLaborPerc = driverLaborPerc;
    this.driverLabor$ = driverLabor$;
    this.taxLaborPerc = taxLaborPerc;
    this.taxLabor$ = taxLabor$;
    this.dmrLaborPerc = dmrLaborPerc;
    this.dmrLabor$ = dmrLabor$;
  }

  public double getMgrLaborPerc()
  {
    return mgrLaborPerc;
  }

  public double getMgrLabor$()
  {
    return mgrLabor$;
  }

  public double getInshopLaborPerc()
  {
    return inshopLaborPer;
  }

  public double getInshopLabor$()
  {
    return inshopLabor$;
  }

  public double getDriverLaborPerc()
  {
    return driverLaborPerc;
  }

  public double getDriverLabor$()
  {
    return driverLabor$;
  }

  public double getTaxLaborPerc()
  {
    return taxLaborPerc;
  }

  public double getTaxLabor$()
  {
    return taxLabor$;
  }

  public double getDmrLaborPerc()
  {
    return dmrLaborPerc;
  }

  public double getDmrLabor$()
  {
    return dmrLabor$;
  }
  
  
}
