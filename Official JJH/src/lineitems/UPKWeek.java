package lineitems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

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
    if(cogsMap.get(category) == null)
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
    for(UPKItem item: upkItems)
    {
      if(item.getName().equals(name))
        return item;
    }
    return null;
  }

  public ArrayList<UPKItem> getItems()
  {
    return upkItems;
  }

  public ArrayList<UPKItem> getItemsOfCategory(String category)
  {
    ArrayList<UPKItem> items = new ArrayList<UPKItem>();
    for(UPKItem item: upkItems)
    {
      if(item.getCategory().equals(category))
        items.add(item);
    }
    return items;
  }

  public void setAdjustedSales(double adjustedSales)
  {
    this.adjustedSales = adjustedSales;
  }
}
