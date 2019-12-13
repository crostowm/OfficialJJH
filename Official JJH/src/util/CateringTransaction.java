package util;

import java.util.GregorianCalendar;

public class CateringTransaction
{
  private int systemID, posID, clerkID;
  private String clerkName;
  private GregorianCalendar time;
  private double grossAmount, taxAmount, royalty;
  
  /**
   * @param systemID
   * @param posID
   * @param clerkID
   * @param clerkName
   * @param time
   * @param grossAmount
   * @param taxAmount
   * @param royalty
   */
  public CateringTransaction(int systemID, int posID, int clerkID, String clerkName,
      GregorianCalendar time, double grossAmount, double taxAmount, double royalty)
  {
    this.systemID = systemID;
    this.posID = posID;
    this.clerkID = clerkID;
    this.clerkName = clerkName;
    this.time = time;
    this.grossAmount = grossAmount;
    this.taxAmount = taxAmount;
    this.royalty = royalty;
  }
  
  
  public int getSystemID()
  {
    return systemID;
  }
  public int getPosID()
  {
    return posID;
  }
  public int getClerkID()
  {
    return clerkID;
  }
  public String getClerkName()
  {
    return clerkName;
  }
  public GregorianCalendar getTime()
  {
    return time;
  }
  public double getGrossAmount()
  {
    return grossAmount;
  }
  public double getTaxAmount()
  {
    return taxAmount;
  }
  public double getRoyalty()
  {
    return royalty;
  }
  
  
}
