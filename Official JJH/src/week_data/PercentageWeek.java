package week_data;

import app.AppDirector;
import observers.IndexDataObserver;
import util.Setting;

public class PercentageWeek extends DualValWeekData implements IndexDataObserver
{

  private static final long serialVersionUID = -8410388420625645426L;

  public PercentageWeek(int setting)
  {
    super(setting);
  }

  @Override
  protected void setFaceForIndex(int index, double data)
  {
    faceData.set(index, data / AppDirector.dataHub.getSettings()
        .getSetting(index % 2 == 0 ? Setting.BTV : Setting.B9TV));
  }

  @Override
  public void indexDataUpdated(int index)
  {
    setDataForIndex(index,
        AppDirector.dataHub.getProjectionWeek().getDataForIndex(index) * index % 2 == 0
            ? AppDirector.dataHub.getSettings().getSetting(Setting.BAKEDAT11)
            : AppDirector.dataHub.getSettings().getSetting(Setting.BAKEDATSC));
  }

}
