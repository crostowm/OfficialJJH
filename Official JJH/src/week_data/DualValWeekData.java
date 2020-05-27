package week_data;

import java.util.ArrayList;

import app.AppDirector;

/**
 * @author crost
 *
 */
public class DualValWeekData extends WeekData
{

  private static final long serialVersionUID = -2427540973845121673L;
  protected ArrayList<Double> faceData;
  private int setting;
  
  public DualValWeekData(int setting)
  {
    super();
    this.setting = setting;
    faceData = new ArrayList<Double>();
    for(int ii = 0; ii < 14; ii++)
    {
      faceData.add(0.0);
    }
  }

  protected void setFaceForIndex(int index, double data)
  {
    faceData.set(index, data/AppDirector.dataHub.getSettings().getSetting(setting));
  }

  @Override
  public void setDataForIndex(int index, double datum)
  {
    data.set(index, datum);
    setFaceForIndex(index, datum);
    notify(index);
  }
  
  public double getFaceForShift(int shift)
  {
    return getFaceForIndex(shift-1);
  }
  
  public double getFaceForIndex(int index)
  {
    return faceData.get(index);
  }

}
