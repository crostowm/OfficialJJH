package bread;

import java.util.ArrayList;

import observers.DataObserver;
import util.CateringOrder;

public class BreadHandler implements DataObserver
{
  private ArrayList<BreadRequest> breadRequests = new ArrayList<BreadRequest>();
  
  
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
  }

}
