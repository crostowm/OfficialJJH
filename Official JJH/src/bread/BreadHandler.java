package bread;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import app.MainApplication;
import observers.DataObserver;
import util.CateringOrder;
import util.DataHub;
import util.JimmyCalendarUtil;

public class BreadHandler implements DataObserver
{
  private ArrayList<BreadRequest> breadRequests = new ArrayList<BreadRequest>();
  private ArrayList<BreadTray6> oven = new ArrayList<BreadTray6>();
  private ArrayList<BreadTray6> proofer = new ArrayList<BreadTray6>();
  private DataHub data;
  
  public BreadHandler()
  {
    this.data = MainApplication.dataHub;
    analyzeBread();
  }
  
  @Override
  public void cateringOrderAdded(CateringOrder co)
  {
  }

  @Override
  public void cateringOrderRemoved(CateringOrder co)
  {
  }

  @Override
  public void toolBoxDataUpdated()
  {
    analyzeBread();
  }

  private void analyzeBread()
  {
    GregorianCalendar currentTime = new GregorianCalendar();
    breadRequests.clear();
    oven.clear();
    proofer.clear();
    
    int amShiftNumber = JimmyCalendarUtil.getShiftNumber(currentTime, (int)data.getSetting(DataHub.STORESC_TIME));
    if(amShiftNumber % 2 == 0)
      amShiftNumber--;
    //Baked at 11
    GregorianCalendar gc11 = new GregorianCalendar();
    //TODO 11 + Setting->BakeTime? for hour
    gc11.set(Calendar.HOUR, 11);
    gc11.set(Calendar.MINUTE, 0);
    gc11.set(Calendar.AM_PM, Calendar.AM);
    int numTrays6At11 = (int)(Math.ceil((data.getPercentageDataForIndex(amShiftNumber - 1)/data.getSetting(DataHub.BTV)) * 2));
    breadRequests.add(new BreadRequest(numTrays6At11 * 6, gc11));
    
    //Baked at sc
    
  }
}
