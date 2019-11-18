package bread;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;

import app.MainApplication;
import observers.DataObserver;
import util.CateringOrder;
import util.DataHub;

/**
 * Maintains Bread Requests and creates a Tray Log with tray process status 
 * @author crost
 *
 */
public class BreadHandler implements DataObserver
{
  private ArrayList<BreadRequest> breadRequests = new ArrayList<BreadRequest>();
  private ArrayList<BreadTray6> trayLog = new ArrayList<BreadTray6>();
  private DataHub data;

  public BreadHandler()
  {
    this.data = MainApplication.dataHub;
  }

  @Override
  public void cateringOrderAdded(CateringOrder co)
  {
    breadRequests.add(new BreadRequest(co.getNumBreadSticks(), co.getTime()));
    analyzeBread();
  }

  @Override
  public void cateringOrderRemoved(CateringOrder co)
  {
    breadRequests.remove(new BreadRequest(co.getNumBreadSticks(), co.getTime()));
    analyzeBread();
  }

  @Override
  public void toolBoxDataUpdated()
  {
    analyzeBread();
  }

  /**
   * Clears Tray Log and recreates by going backwards through bread requests
   */
  public void analyzeBread()
  {
    if (breadRequests.size() > 0)
    {
      trayLog.clear();
      Collections.sort(breadRequests);

      int processTime = ((int) data.getSetting(DataHub.PROOF_TIME))
          + ((int) data.getSetting(DataHub.BAKE_TIME)) + ((int) data.getSetting(DataHub.COOL_TIME));
      int numDecks = (int) data.getSetting(DataHub.NUMDECKS);
      GregorianCalendar timerTick = (GregorianCalendar) breadRequests.get(breadRequests.size() - 1)
          .getTimeDue().clone();
      timerTick.add(Calendar.MINUTE, -processTime);
      // Go backwards through requests
      for (int ii = breadRequests.size() - 1; ii >= 0; ii--)
      {
        BreadRequest br = breadRequests.get(ii);
        if ((br.getTimeDue().get(Calendar.MINUTE)) - processTime < timerTick.get(Calendar.MINUTE))
        {
          timerTick = br.getTimeDue();
          timerTick.add(Calendar.MINUTE, -processTime);
        }
        int numTraysRequired = (int) Math.ceil(br.getNumSticks() / 6);
        // Create cycles in this loop
        while (numTraysRequired > 0)
        {
          int qty;
          if (numTraysRequired > numDecks)
            qty = numDecks;
          else
            qty = numTraysRequired;
          for (int i = 0; i < qty; i++)
          {
            trayLog.add(new BreadTray6((GregorianCalendar) timerTick.clone()));
            numTraysRequired--;
          }
          timerTick.add(Calendar.MINUTE, -processTime);
        }
      }
    }
  }

  /**
   * Adds Bread Request and reanalyzes
   * @param breadRequest
   */
  public void sendRequest(BreadRequest breadRequest)
  {
    breadRequests.add(breadRequest);
    analyzeBread();
  }

  public ArrayList<BreadTray6> getTrayLog()
  {
    return trayLog;
  }

  public String toString()
  {
    int count = 0;
    GregorianCalendar gc = null;
    String ret = "";
    for (BreadTray6 bt : trayLog)
    {
      if (gc == null)
      {
        gc = bt.getStartTime();
        count = 1;
      }
      else
      {
        if (bt.getStartTime().equals(gc))
        {
          count++;
        }
        else
        {
          SimpleDateFormat tf = new SimpleDateFormat("YYYY:MMM:dd hh:mm:ss");
          ret += count + " tray(s): " + tf.format(gc.getTime()) + "\n";
          gc = bt.getStartTime();
          count = 1;
        }
      }
    }
    if (gc != null)
    {
      SimpleDateFormat tf = new SimpleDateFormat("YYYY:MMM:dd hh:mm:ss");
      ret += count + " tray(s): " + tf.format(gc.getTime()) + "\n";
    }

    return ret;
  }
}
