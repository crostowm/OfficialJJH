package lineitems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import app.AppDirector;

public class UPKWeek
{
  private ArrayList<UPKItem> upkItems = new ArrayList<UPKItem>();
  private HashMap<String, HashMap<Integer, Double>> cogsMap = new HashMap<String, HashMap<Integer, Double>>();
  public static final int ACTUAL_COGS = 1, THEORETICAL_COGS = 2, COGS_VARIANCE = 3;
  private double adjustedSales = -1;

  public void addItem(UPKItem item)
  {
    upkItems.add(item);
    Collections.sort(upkItems);
  }

  public void setCOGS(String category, int dataType, double data)
  {
    if (cogsMap.get(category) == null)
      cogsMap.put(category, new HashMap<Integer, Double>());
    cogsMap.get(category).put(dataType, data);
  }

  public double getCOGS(String category, int dataType)
  {
    return cogsMap.get(category).get(dataType);
  }

  public double getAdjustedSales()
  {
    return adjustedSales;
  }

  public UPKItem getUPKItem(String name)
  {
    String cat = "";
    UPKItem cons = null;
    String unit = "";
    switch (name)
    {
      case "Chip JJ Reg":
      case "Chip JJ BBQ":
      case "Chip JJ Jalapeno":
      case "Chip JJ S&V":
      case "Chip JJ Skinny":
        cat = "Sides";
        cons = getUPKItem("Chips");
        unit = "ea";
        break;
      case "Cookie JJ Triple Choc Chip":
      case "Cookie Oatmeal Raisin":
        cat = "Sides";
        cons = getUPKItem("Cookies");
        unit = "ea";
        break;
      case "Onions (Yellow Jumbo)":
        return getUPKItem("Onions");
      case "Syrup Cherry Coke":
      case "Syrup Coke":
      case "Syrup Diet Coke":
      case "Syrup Dr. Pepper":
      case "Syrup Lemonade":
      case "Syrup Powerade":
      case "Syrup Root Beer":
      case "Syrup Sprite":
        cat = "Beverage";
        cons = getUPKItem("Syrup Drinks");
        unit = "gal";
        break;
      default:
        for (UPKItem item : upkItems)
        {
          if (item.getName().equals(name))
            return item;
        }
        break;
    }
    InventoryItem ii = AppDirector.dataHub.getInventoryItem(name);
    double perc;
    double actUPK;
    double avgUPK;
    if (ii != null && cons != null)
    {
      perc = ii.getActUsage() / cons.getActualUsage();
      actUPK = perc * cons.getActualUPK();
      avgUPK = perc * cons.getAverageUPK();
    }
    else
    {
      actUPK = -1;
      avgUPK = -1;
      perc = -1;
    }
    // TODO variance$
    return new UPKItem(name, cat, unit, ii.getActUsage(), ii.getTheoreticalUsage(),
        ii.getActUsage() - ii.getTheoreticalUsage(), -1, actUPK, avgUPK, actUPK - avgUPK);
  }

  public ArrayList<UPKItem> getItems()
  {
    return upkItems;
  }

  public ArrayList<UPKItem> getItemsOfCategory(String category)
  {
    ArrayList<UPKItem> items = new ArrayList<UPKItem>();
    for (UPKItem item : upkItems)
    {
      if (item.getCategory().equals(category))
        items.add(item);
    }
    return items;
  }

  public void setAdjustedSales(double adjustedSales)
  {
    this.adjustedSales = adjustedSales;
  }
}
