package gui;

import javafx.scene.layout.HBox;
import lineitems.UPKItem;

public abstract class UPKHBox extends HBox
{

  private double adjustedSales;
  protected UPKItem item;

  public UPKHBox(double adjustedSales, UPKItem item)
  {
    this.adjustedSales = adjustedSales;
    this.item = item;
  }
  
  public double getAdjustedSales()
  {
    return adjustedSales;
  }
  
  public UPKItem getItem()
  {
    return item;
  }
}
