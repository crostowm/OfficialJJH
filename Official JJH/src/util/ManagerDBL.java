package util;

import java.io.Serializable;

import app.MainApplication;
import personnel.Manager;

public class ManagerDBL implements Serializable
{
  private static final long serialVersionUID = 521481736377501295L;
  private Manager completedBy;
  private String desc;
  
  public ManagerDBL(String desc)
  {
    this.desc = desc;
  }

  public Manager getCompletedBy()
  {
    return completedBy;
  }

  public void complete(Manager completedBy)
  {
    this.completedBy = completedBy;
    MainApplication.dataHub.addCompletedManagerDBL(this);
  }

  public String getDesc()
  {
    return desc;
  }
}
