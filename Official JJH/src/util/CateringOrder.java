package util;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class CateringOrder implements Serializable
{
  private static final long serialVersionUID = 6549563340518963033L;

  private double dollarValue;
  
  private GregorianCalendar time;
  
  public CateringOrder(double dollarValue, GregorianCalendar time)
  {
    this.dollarValue = dollarValue;
    this.time = time;
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
    SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy hh:mm");
    return new String(sdf.format(time.getTime()) + "  $" + dollarValue);
  }
}
