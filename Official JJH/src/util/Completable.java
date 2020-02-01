package util;

import java.io.Serializable;
import java.util.GregorianCalendar;

public abstract class Completable implements Serializable
{
  private static final long serialVersionUID = 8135491990759783587L;
  private boolean completed;
  private String completedName;
  private GregorianCalendar completeTime;
  private String desc;
  
  public Completable()
  {
    completed = false;
  }
  
  public Completable(boolean completed, String completeName, GregorianCalendar completeTime, String description)
  {
    this.completed = completed;
    this.completedName = completeName;
    this.completeTime = completeTime;
    this.desc = description;
  }
  
  public String getDesc()
  {
    return desc;
  }
  
  public void complete(String completedName, GregorianCalendar completeTime)
  {
    completed = true;
    this.completedName = completedName;
    this.completeTime = completeTime;
  }

  public boolean isCompleted()
  {
    return completed;
  }

  public String getCompletedName()
  {
    return completedName;
  }

  public GregorianCalendar getCompleteTime()
  {
    return completeTime;
  }
  
  
}
