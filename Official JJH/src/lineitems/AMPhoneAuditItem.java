package lineitems;

public class AMPhoneAuditItem
{
  double salesAM, salesPM, depositAM, depositPM, cashOverUnderAM, cashOverUnderPM, laborAM, laborPM,
      laborWeek, compPerc, compDollars;

  /**
   * @param salesAM
   * @param salesPM
   * @param depositAM
   * @param depositPM
   * @param cashOverUnderAM
   * @param cashOverUnderPM
   * @param laborAM
   * @param laborPM
   * @param laborWeek
   * @param compPerc
   * @param compDollars
   */
  public AMPhoneAuditItem(double salesAM, double salesPM, double depositAM, double depositPM,
      double cashOverUnderAM, double cashOverUnderPM, double laborAM, double laborPM,
      double laborWeek, double compPerc, double compDollars)
  {
    this.salesAM = salesAM;
    this.salesPM = salesPM;
    this.depositAM = depositAM;
    this.depositPM = depositPM;
    this.cashOverUnderAM = cashOverUnderAM;
    this.cashOverUnderPM = cashOverUnderPM;
    this.laborAM = laborAM;
    this.laborPM = laborPM;
    this.laborWeek = laborWeek;
    this.compPerc = compPerc;
    this.compDollars = compDollars;
  }

  public double getSalesAM()
  {
    return salesAM;
  }

  public double getSalesPM()
  {
    return salesPM;
  }

  public double getDepositAM()
  {
    return depositAM;
  }

  public double getDepositPM()
  {
    return depositPM;
  }

  public double getCashOverUnderAM()
  {
    return cashOverUnderAM;
  }

  public double getCashOverUnderPM()
  {
    return cashOverUnderPM;
  }

  public double getLaborAM()
  {
    return laborAM;
  }

  public double getLaborPM()
  {
    return laborPM;
  }

  public double getLaborWeek()
  {
    return laborWeek;
  }

  public double getCompPerc()
  {
    return compPerc;
  }

  public double getCompDollars()
  {
    return compDollars;
  }
}
