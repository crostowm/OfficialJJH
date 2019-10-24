package bread;

import java.util.GregorianCalendar;

public class BreadTray6
{
  /**
   * The time it needs to begin stretching process
   */
  private GregorianCalendar startTime;
  
  public BreadTray6(GregorianCalendar startTime)
  {
    this.startTime = startTime;
  }
  
  public GregorianCalendar getStartTime()
  {
    return startTime;
  }
}
