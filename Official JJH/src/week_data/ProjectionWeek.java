package week_data;


import app.AppDirector;
import observers.IndexDataObserver;

public class ProjectionWeek extends WeekData implements IndexDataObserver
{
  private static final long serialVersionUID = 1725980563055024501L;
  @Override
  public void indexDataUpdated(int index)
  {
    setDataForIndex(index,
        AppDirector.dataHub.getSampleWeek().getDataForIndex(index)
            + AppDirector.dataHub.getCateringWeek().getDataForIndex(index)
            + AppDirector.dataHub.getAveragePlusBufferWeek().getDataForIndex(index));
  }

}
