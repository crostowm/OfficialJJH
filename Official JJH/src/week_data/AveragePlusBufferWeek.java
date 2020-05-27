package week_data;

import app.AppDirector;
import observers.IndexDataObserver;
import util.Setting;

public class AveragePlusBufferWeek extends WeekData implements IndexDataObserver
{
  private static final long serialVersionUID = -8967062187050612035L;

  @Override
  public void indexDataUpdated(int index)
  {
    boolean isAm = index % 2 == 0;
    setDataForIndex(index, AppDirector.dataHub.getAverageWeek().getDataForIndex(index)
        * AppDirector.dataHub.getSettings().getSetting(isAm ? Setting.AMBUFFER : Setting.PMBUFFER));
  }

}
