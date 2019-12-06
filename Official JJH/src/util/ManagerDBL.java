package util;

import java.io.Serializable;

public class ManagerDBL extends Completable implements Serializable
{
  private static final long serialVersionUID = 521481736377501295L;
  private String desc;
  
  public ManagerDBL(String desc)
  {
    this.desc = desc;
  }

  public String getDesc()
  {
    return desc;
  }
}
