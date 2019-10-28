package util;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class CateringOrder implements Serializable
{
  private static final long serialVersionUID = 6549563340518963033L;

  private double dollarValue;
  
  private GregorianCalendar time;
  
  private int numBreadSticks = -1, numBL = 0, num12P = 0, num24P = 0;
  
  private String details;
  
  /**
   * @param dollarValue
   * @param time
   * @param numBreadSticks -1 if not using
   */
  public CateringOrder(double dollarValue, GregorianCalendar time, int numBreadSticks, String details)
  {
    this.dollarValue = dollarValue;
    this.time = time;
    this.numBreadSticks = numBreadSticks;
    this.details = details;
  }

  /**
   * @return the dollarValue
   */
  public double getDollarValue()
  {
    return dollarValue;
  }

  /**
   * @param dollarValue the dollarValue to set
   */
  public void setDollarValue(double dollarValue)
  {
    this.dollarValue = dollarValue;
  }

  /**
   * @return the time
   */
  public GregorianCalendar getTime()
  {
    return time;
  }

  /**
   * @param time the time to set
   */
  public void setTime(GregorianCalendar time)
  {
    this.time = time;
  }
  
  public String toString()
  {
    SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy hh:mma");
    return new String(sdf.format(time.getTime()) + "  $" + dollarValue + " " + numBreadSticks + " Sticks");
  }

  public String fullString()
  {
    return toString() + "\n" + details;
  }
  /**
   * @return the numBreadSticks
   */
  public int getNumBreadSticks()
  {
    return numBreadSticks;
  }

  /**
   * @param numBreadSticks the numBreadSticks to set
   */
  public void setNumBreadSticks(int numBreadSticks)
  {
    this.numBreadSticks = numBreadSticks;
  }
  
  public String getDetails()
  {
    return details;
  }

  /**
   * @return the numBL
   */
  public int getNumBL()
  {
    return numBL;
  }

  /**
   * @param numBL the numBL to set
   */
  public void setNumBL(int numBL)
  {
    this.numBL = numBL;
  }

  /**
   * @return the num12P
   */
  public int getNum12P()
  {
    return num12P;
  }

  /**
   * @param num12p the num12P to set
   */
  public void setNum12P(int num12p)
  {
    num12P = num12p;
  }

  /**
   * @return the num24P
   */
  public int getNum24P()
  {
    return num24P;
  }

  /**
   * @param num24p the num24P to set
   */
  public void setNum24P(int num24p)
  {
    num24P = num24p;
  }
}
