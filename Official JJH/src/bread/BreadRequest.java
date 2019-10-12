package bread;

import java.util.GregorianCalendar;

public class BreadRequest
{
  private int numSticks;
  private GregorianCalendar timeDue;
  /**
   * @param numSticks
   * @param timeDue
   */
  public BreadRequest(int numSticks, GregorianCalendar timeDue)
  {
    this.numSticks = numSticks;
    this.timeDue = timeDue;
  }
  public int getNumSticks()
  {
    return numSticks;
  }
  public void setNumSticks(int numSticks)
  {
    this.numSticks = numSticks;
  }
  public GregorianCalendar getTimeDue()
  {
    return timeDue;
  }
  public void setTimeDue(GregorianCalendar timeDue)
  {
    this.timeDue = timeDue;
  }
}
