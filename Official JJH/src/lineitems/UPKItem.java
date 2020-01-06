package lineitems;

public class UPKItem implements Comparable<UPKItem>
{
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
      case "Sugar":
      case "Onions":
        caseValue = 50;
      case "Capicola":
        caseValue = 40.2;
      case "Ham":
        caseValue = 40;
      case "Salami":
        caseValue = 39;
      case "Cheese":
        caseValue = 36;
      case "Wheat Sub -  9 Grain Roll":
      case "French Bread 32 ct":
        caseValue = 32;
      case "Beef":
        caseValue = 30;
      case "Tomatoes":
        caseValue = 25;
      case "Cucumbers":
      case "Lettuce":
        caseValue = 24;
      case "Turkey":
        caseValue = 18.8;
      case "Kickin Ranch Dressing Mix":
        caseValue = 18;
      case "Avocado":
        caseValue = 12;
      case "Oil":
      case "Pan Spray":
      case "Tuna Chunk - pouch":
      case "MUSTARD WHL GRAIN":
        caseValue = 6;
      case "Peppers Cherry":
      case "Mayonnaise":
      case "Vinegar":
      case "Butter (salted)":
        caseValue = 4;
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
