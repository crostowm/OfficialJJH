package lineitems;

import java.io.Serializable;
import java.util.ArrayList;

public class WeeklySalesWeek implements Serializable
{
  private static final long serialVersionUID = -8335610958818578479L;
  private int year, weekNumber;
  private ArrayList<WeeklySalesLineItem> items;
  private ArrayList<String> cashDeposits;
  /**
   * @param weekNumber
   * @param items
   * @param cashDeposits
   */
  public WeeklySalesWeek(int year, int weekNumber, ArrayList<WeeklySalesLineItem> items,
      ArrayList<String> cashDeposits)
  {
    this.year = year;
    this.weekNumber = weekNumber;
    this.items = items;
    this.cashDeposits = cashDeposits;
  }
  public static long getSerialversionuid()
  {
    return serialVersionUID;
  }
  public int getWeekNumber()
  {
    return weekNumber;
  }
  public ArrayList<WeeklySalesLineItem> getItems()
  {
    return items;
  }
  public ArrayList<String> getCashDeposits()
  {
    return cashDeposits;
  }
  public int getYear()
  {
    return year;
  }
  
  public WeeklySalesLineItem getLineItem(String dataType)
  {
    for(WeeklySalesLineItem wsli: items)
    {
      if(wsli.getName().equals(dataType))
        return wsli;
    }
    return null;
  }
}
