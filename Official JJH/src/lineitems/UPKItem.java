package lineitems;

import java.io.Serializable;

public class UPKItem implements Comparable<UPKItem>, Serializable
{
  private static final long serialVersionUID = 4426366137785075881L;
  private String name, category, unit;
  private double actualUsage, theoreticalUsage, usageVariance, usageVariance$, actualUPK,
      averageUPK, upkVariance, caseValue;

  /**
   * @param name
   * @param category
   * @param unit
   * @param actualUsage
   * @param theoreticalUsage
   * @param usageVariance
   * @param usageVariance$
   * @param actualUPK
   * @param averageUPK
   * @param upkVariance
   * @param actualCOGS
   * @param theoreticalCOGS
   * @param cogsVariance
   * @param caseValue
   */
  public UPKItem(String name, String category, String unit, double actualUsage,
      double theoreticalUsage, double usageVariance, double usageVariance$, double actualUPK,
      double averageUPK, double upkVariance)
  {
    this.name = name;
    this.category = category;
    this.unit = unit;
    this.actualUsage = actualUsage;
    this.theoreticalUsage = theoreticalUsage;
    this.usageVariance = usageVariance;
    this.usageVariance$ = usageVariance$;
    this.actualUPK = actualUPK;
    this.averageUPK = averageUPK;
    this.upkVariance = upkVariance;
    switch (name)
    {
      case "BACON - SLI APPLWD SMKD CKD":
        caseValue = 600;
        break;
      case "Sugar":
      case "Onions":
        caseValue = 50;
        break;
      case "Capicola":
        caseValue = 40.2;
        break;
      case "Ham":
        caseValue = 40;
        break;
      case "Salami":
        caseValue = 39;
        break;
      case "Cheese":
        caseValue = 36;
        break;
      case "Wheat Sub -  9 Grain Roll":
      case "French Bread 32 ct":
        caseValue = 32;
        break;
      case "Beef":
        caseValue = 30;
        break;
      case "Tomatoes":
        caseValue = 25;
        break;
      case "Cucumbers":
      case "Lettuce":
        caseValue = 24;
        break;
      case "Turkey":
        caseValue = 18.8;
        break;
      case "Kickin Ranch Dressing Mix":
        caseValue = 18;
        break;
      case "Wheat":
        caseValue = 16;
        break;
      case "Avocado":
        caseValue = 12;
        break;
      case "Oil":
      case "Pan Spray":
      case "Tuna Chunk - pouch":
      case "MUSTARD WHL GRAIN":
        caseValue = 6;
        break;
      case "Peppers Cherry":
      case "Mayonnaise":
      case "Vinegar":
      case "Butter (salted)":
        caseValue = 4;
        break;
      default:
        caseValue = 1;
    }
  }

  public String getName()
  {
    return name;
  }

  public String getCategory()
  {
    return category;
  }

  public String getUnit()
  {
    return unit;
  }

  public double getActualUsage()
  {
    return actualUsage;
  }

  public double getTheoreticalUsage()
  {
    return theoreticalUsage;
  }

  public double getUsageVariance()
  {
    return usageVariance;
  }

  public double getUsageVariance$()
  {
    return usageVariance$;
  }

  public double getActualUPK()
  {
    return actualUPK;
  }

  public double getAverageUPK()
  {
    return averageUPK;
  }

  public double getUPKVariance()
  {
    return upkVariance;
  }

  public double getCaseValue()
  {
    return caseValue;
  }

  @Override
  public int compareTo(UPKItem item)
  {
    return name.compareTo(item.getName());
  }
}
