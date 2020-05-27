package week_data;

import java.io.Serializable;
import java.util.ArrayList;

import observers.IndexDataObserver;

public class WeekData implements Serializable
{
  private static final long serialVersionUID = 51740226794390632L;
  protected ArrayList<Double> data;
  private transient ArrayList<IndexDataObserver> observers = new ArrayList<IndexDataObserver>();
  
  public WeekData()
  {
    data = new ArrayList<Double>();
    for(int ii = 0; ii < 14; ii++)
    {
      data.add(0.0);
    }
  }
  
  public void setDataForShift(int shift, double datum)
  {
    setDataForIndex(shift-1, datum);
  }

  public void setDataForIndex(int index, double datum)
  {
    data.set(index, datum);
    notify(index);
  }
  
  public double getDataForShift(int shift)
  {
    return getDataForIndex(shift-1);
  }
  
  public double getDataForIndex(int index)
  {
    return data.get(index);
  }

  protected void notify(int index)
  {
    if(observers == null)
      observers = new ArrayList<IndexDataObserver>();
    for(IndexDataObserver ido: observers)
    {
      ido.indexDataUpdated(index);
    }
  }

  public void addObserver(IndexDataObserver obs)
  {
    if(observers == null)
      observers = new ArrayList<IndexDataObserver>();
    observers.add(obs);
  }
}
