package gui;

import java.util.HashMap;

import javafx.scene.layout.HBox;

public abstract class UPKHBox extends HBox
{

  private int category;
  private double adjustedSales;
  private String n;
  private HashMap<Integer, Double> data;

  public UPKHBox(int category, double adjustedSales, String n, HashMap<Integer, Double> data)
  {
    this.category = category;
    this.adjustedSales = adjustedSales;
    this.n = n;
    this.data = data;
  }
  
  public double getAdjustedSales()
  {
    return adjustedSales;
  }
  
  public int getCategory()
  {
    return category;
  }
  
  public String getName()
  {
    return n;
  }
  
  public HashMap<Integer, Double> getData()
  {
    return data;
  }
}
