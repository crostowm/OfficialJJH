package observers;

import util.CateringOrder;

public interface DataObserver
{
  public void cateringOrderAdded(CateringOrder co);
  public void cateringOrderRemoved(CateringOrder co);
  public void toolBoxDataUpdated();
  public void upkSet();
}
