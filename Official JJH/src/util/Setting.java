package util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import observers.SettingObserver;

public class Setting implements Serializable
{

  public static final int AMBUFFER = 1, PMBUFFER = 2, BTV = 3, B9TV = 4, WLV = 5, BAKEDAT11 = 6,
      BAKEDATSC = 7, LETTUCEBV = 8, TOMATOBV = 9, ONIONBV = 10, CUCUMBERBV = 11, PICKLEBV = 12,
      STORESC_TIME = 13, NUMDECKS = 14, PROOF_TIME = 15, BAKE_TIME = 16, COOL_TIME = 17,
      INSHOP_MIN_PAY = 18, LAYOUT_8AM = 19;
  int numSettings = 19;
  private static final long serialVersionUID = 4508923320321857363L;
  private HashMap<Integer, Double> settings;
  private transient HashMap<Integer, ArrayList<SettingObserver>> observers = new HashMap<Integer, ArrayList<SettingObserver>>();

  public Setting()
  {
    for (int ii = 1; ii <= numSettings; ii++)
    {
      observers.put(ii, new ArrayList<SettingObserver>());
    }
    settings = new HashMap<Integer, Double>();
    settings.put(AMBUFFER, 1.2);
    settings.put(PMBUFFER, 1.2);
    settings.put(BTV, 200.0);
    settings.put(B9TV, 190.0);
    settings.put(WLV, 800.0);
    settings.put(BAKEDAT11, .75);
    settings.put(BAKEDATSC, .5);
    settings.put(LETTUCEBV, 500.0);
    settings.put(TOMATOBV, 900.0);
    settings.put(ONIONBV, 1200.0);
    settings.put(CUCUMBERBV, 2200.0);
    settings.put(PICKLEBV, 1200.0);
    settings.put(STORESC_TIME, 15.0);
    settings.put(NUMDECKS, 4.0);
    settings.put(PROOF_TIME, 50.0);
    settings.put(BAKE_TIME, 20.0);
    settings.put(COOL_TIME, 25.0);
    settings.put(INSHOP_MIN_PAY, 8.0);
    settings.put(LAYOUT_8AM, 0.5);
  }

  public void setSetting(int setting, double val)
  {
    settings.put(setting, val);
    notifyObservers(setting);
  }

  public void notifyObservers(int setting)
  {
    if (observers == null)
    {
      observers = new HashMap<Integer, ArrayList<SettingObserver>>();
      for (int ii = 1; ii <= numSettings; ii++)
      {
        observers.put(ii, new ArrayList<SettingObserver>());
      }
    }
    for (SettingObserver so : observers.get(setting))
    {
      so.settingUpdated();
    }
  }

  public double getSetting(int setting)
  {
    return settings.get(setting);
  }

  public void addObserver(int setting, SettingObserver obs)
  {
    observers.get(setting).add(obs);
  }
}
