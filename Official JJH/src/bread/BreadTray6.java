package bread;

import java.util.GregorianCalendar;

/**
 * Start Time to begin proofing
 * @author crost
 *
 */
public class BreadTray6
{
  /**
   * The time it needs to begin proofing process
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
