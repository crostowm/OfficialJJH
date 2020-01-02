package controllers;

import app.MainApplication;
import gui.GuiUtilFactory;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import lineitems.InventoryItem;

public class InventoryController
{
  @FXML
  private VBox itemVBox, detailBox;
  
  @FXML
  private GridPane gp;
  
  public void initialize()
  {
    gp.add(GuiUtilFactory.createInventoryTitleBox(), 0, 1, 2, 1);
    for(InventoryItem ii: MainApplication.dataHub.getInventoryItems())
    {
      itemVBox.getChildren().add(GuiUtilFactory.createInventoryBox(ii));
    }
  }
}
