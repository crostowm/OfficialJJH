package gui;

import java.util.HashMap;

import javafx.scene.layout.HBox;

public abstract class UPKHBox extends HBox
{

  private int category;
  private String n;
  private HashMap<Integer, Double> data;

  public UPKHBox(int category, String n, HashMap<Integer, Double> data)
  {
    this.category = category;
    this.n = n;
    this.data = data;
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
