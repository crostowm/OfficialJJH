package week_data;

import java.io.Serializable;
import java.util.HashMap;

import app.AppDirector;
import observers.IndexDataObserver;

public class SlicingWeek implements IndexDataObserver, Serializable
{

  private static final long serialVersionUID = -6184436995058929313L;
  private HashMap<String, WeekData> pars = new HashMap<String, WeekData>();

  public SlicingWeek()
  {
    pars.put("Cheese", new WeekData());
    pars.put("Ham", new WeekData());
    pars.put("Turkey", new WeekData());
    pars.put("Beef", new WeekData());
    pars.put("Salami", new WeekData());
    pars.put("Capicola", new WeekData());
  }
  
  @Override
  public void indexDataUpdated(int index)
  {
    for (String name : pars.keySet())
    {
      pars.get(name)
          .setDataForIndex(index,
              (AppDirector.dataHub.getLastCompletedWeekUPKWeek().getUPKItem(name).getAverageUPK()
                  * (AppDirector.dataHub.getProjectionWeek().getDataForIndex(index) / 1000))
                  / 3.307);
    }
  }

  public void updateAllPars()
  {
    for (String name : pars.keySet())
    {
      for (int ii = 0; ii < 14; ii++)
      {
        pars.get(name)
            .setDataForIndex(ii,
                (AppDirector.dataHub.getLastCompletedWeekUPKWeek().getUPKItem(name).getAverageUPK()
                    * (AppDirector.dataHub.getProjectionWeek().getDataForIndex(ii) / 1000))
                    / 3.307);
      }
    }
  }

  public double getSlicingParsForShift(int shift, String name)
  {
    return pars.get(name).getDataForShift(shift);
  }
}
