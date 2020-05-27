package week_data;

import app.AppDirector;
import observers.IndexDataObserver;
import util.Setting;

public class ThawedWeek extends DualValWeekData implements IndexDataObserver
{

  private static final long serialVersionUID = -7409612794800353590L;

  public ThawedWeek(int setting)
  {
    super(setting);
  }

  @Override
  public void indexDataUpdated(int index)
  {
    setDataForIndex(index,
        AppDirector.dataHub.getProjectionWeek().getDataForIndex(index) * (index % 2 == 0 ? 1
            : AppDirector.dataHub.getSettings().getSetting(Setting.LAYOUT_8AM)));
  }

}
