package bread;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class BreadRequest implements Comparable<BreadRequest>
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
  @Override
  public int compareTo(BreadRequest other)
  {
    return timeDue.compareTo(other.timeDue);
  }
  
  public String toString()
  {
    SimpleDateFormat tf = new SimpleDateFormat("YYYY:MMM:dd hh:mm:ss");
    return numSticks + "@" + tf.format(timeDue.getTime());
  }
}
