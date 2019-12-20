package lineitems;

import java.util.GregorianCalendar;

import app.MainApplication;
import util.DataHub;

public class AttendanceShift
{
  private String firstName, lastName, position, adjustmentReason;
  private double payHours, payRate, cumulativeHours, estimatedWages;
  private GregorianCalendar start, end;
  private boolean adjusted;
  private boolean authorized;
  private int empNum;
  
  
  /**
   * @param empNum 
   * @param authorized 
   * @param firstName
   * @param lastName
   * @param position
   * @param adjustmentReason
   * @param payHours
   * @param payRate
   * @param cumulativeHours
   * @param estimatedWages
   * @param start
   * @param end
   * @param adjusted
   */
  public AttendanceShift(boolean authorized, int empNum, String firstName, String lastName, String position,
      String adjustmentReason, double payHours, double payRate, double cumulativeHours,
      double estimatedWages, GregorianCalendar start, GregorianCalendar end, boolean adjusted)
  {
    this.authorized = authorized;
    this.empNum = empNum;
    this.firstName = firstName;
    this.lastName = lastName;
    this.position = position;
    this.adjustmentReason = adjustmentReason;
    this.payHours = payHours;
    this.payRate = payRate;
    this.cumulativeHours = cumulativeHours;
    this.estimatedWages = estimatedWages;
    this.start = start;
    this.end = end;
    this.adjusted = adjusted;
  }
  
  public String getFirstName()
  {
    return firstName;
  }
  
  public String getLastName()
  {
    return lastName;
  }
  
  public String getPosition()
  {
    return position;
  }
  
  public String getAdjustmentReason()
  {
    return adjustmentReason;
  }
  
  public double getPayHours()
  {
    return payHours;
  }
  
  public double getPayRate()
  {
    return payRate;
  }
  
  public double getCumulativeHours()
  {
    return cumulativeHours;
  }
  
  public double getEstimatedWages()
  {
    return estimatedWages;
  }
  
  public GregorianCalendar getStart()
  {
    return start;
  }
  
  public GregorianCalendar getEnd()
  {
    return end;
  }
  
  public boolean isAdjusted()
  {
    return adjusted;
  }
  
  public boolean isAuthorized()
  {
    return authorized;
  }
  
  public int getEmpNum()
  {
    return empNum;
  }
  
  public boolean isValid()
  {
    boolean flag = authorized;
    flag = flag && payHours > 0 && payHours < 12;
    switch(position)
    {
      case "In Shop":
        flag = flag && payRate >= MainApplication.dataHub.getSetting(DataHub.INSHOP_MIN_PAY);
        break;
      case "Driver - In Shop":
        flag = flag && payRate == 7.25;
        break;
      case "Driver":
        flag = flag && payRate == 2.13;
        break;
    }
    return flag;
  }
  
}
