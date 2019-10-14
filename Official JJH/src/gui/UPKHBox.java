package gui;

import java.util.HashMap;

import javafx.scene.layout.HBox;

public abstract class UPKHBox extends HBox
{

  private String n;
  private HashMap<Integer, Double> data;

  public UPKHBox(String n, HashMap<Integer, Double> data)
  {
    this.n = n;
    this.data = data;
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
