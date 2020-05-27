package week_data;

import app.AppDirector;
import observers.IndexDataObserver;

public class WheatWeek extends DualValWeekData implements IndexDataObserver
{

  private static final long serialVersionUID = 211626220801308614L;

  public WheatWeek(int setting)
  {
    super(setting);
  }

  @Override
  public void indexDataUpdated(int index)
  {
    setDataForIndex(index,
        AppDirector.dataHub.getProjectionWeek().getDataForIndex(index)
            + (index % 2 == 0 ? AppDirector.dataHub.getProjectionWeek().getDataForIndex(index + 1)
                : 0));
  }

}
