package lineitems;

import java.util.ArrayList;

public class InventoryItem implements Comparable<InventoryItem>
{
  private String name;
  private double begInv, totPurch, totTrans, endInv, theoreticalUsage, actUsage;
  private ArrayList<TheoryUsageLineItem> theoryUsages;
  private ArrayList<VendorDeliveryDetailLineItem> vendorItems;
  private ArrayList<TransferLineItem> transIn, transOut;

  /**
   * @param begInv
   * @param totPurch
   * @param totTrans
   * @param endInv
   * @param actUsage
   * @param transOut
   * @param transIn
   * @param vendorItems
   * @param theoreticalUsage
   */
  public InventoryItem(String name, double begInv, double totPurch, double totTrans, double endInv,
      double actUsage, ArrayList<TheoryUsageLineItem> theoryUsages, double theoreticalUsage,
      ArrayList<VendorDeliveryDetailLineItem> vendorItems, ArrayList<TransferLineItem> transIn,
      ArrayList<TransferLineItem> transOut)
  {
    this.name = name;
    this.begInv = begInv;
    this.totPurch = totPurch;
    this.totTrans = totTrans;
    this.endInv = endInv;
    this.actUsage = actUsage;
    this.theoryUsages = theoryUsages;
    this.theoreticalUsage = theoreticalUsage;
    this.vendorItems = vendorItems;
    this.transIn = transIn;
    this.transOut = transOut;
  }

  public String getName()
  {
    return name;
  }

  public String getParsedName()
  {
    String noSpaces = "";
    for(char c: name.toCharArray())
    {
      if(c != ' ')
      {
        noSpaces += c;
      }
    }
    return noSpaces.split("\\[")[0];
  }
  
  public double getBegInv()
  {
    return begInv;
  }

  public double getTotPurch()
  {
    return totPurch;
  }

  public double getTotTrans()
  {
    return totTrans;
  }

  public double getEndInv()
  {
    return endInv;
  }

  public double getActUsage()
  {
    return actUsage;
  }

  public ArrayList<TheoryUsageLineItem> getTheoryUsages()
  {
    return theoryUsages;
  }

  public double getTheoreticalUsage()
  {
    return theoreticalUsage;
  }

  public ArrayList<VendorDeliveryDetailLineItem> getVendorItems()
  {
    return vendorItems;
  }

  public ArrayList<TransferLineItem> getTransfersIn()
  {
    return transIn;
  }

  public ArrayList<TransferLineItem> getTransfersOut()
  {
    return transOut;
  }

  @Override
  public int compareTo(InventoryItem ii)
  {
    return getName().compareTo(ii.getName());
  }
}
