package lineitems;

import java.io.Serializable;
import java.util.ArrayList;

public class WeeklySalesLineItem implements Serializable
{
  private static final long serialVersionUID = -9180604864866791615L;
  private String name;
  private double summary, each;
  private ArrayList<Double> shiftData;
  /**
   * @param name
   * @param summary
   * @param each
   * @param shiftData
   */
  public WeeklySalesLineItem(String name, double summary, double each, ArrayList<Double> shiftData)
  {
    this.name = name;
    this.summary = summary;
    this.each = each;
    this.shiftData = shiftData;
  }
  public String getName()
  {
    return name;
  }
  public double getSummary()
  {
    return summary;
  }
  public double getEach()
  {
    return each;
  }
  public ArrayList<Double> getShiftData()
  {
    return shiftData;
  }
  
  public double getDataForShift(int shift)
  {
    return shiftData.get(shift - 1);
  }
  
  public double getDataForIndex(int index)
  {
    return shiftData.get(index);
  }
}
