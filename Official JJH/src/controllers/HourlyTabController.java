package controllers;

import app.MainApplication;
import gui.GuiUtilFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import lineitems.HourlySalesItem;

public class HourlyTabController
{
  @FXML
  private VBox itemBox;
  
  @FXML
  private GridPane gp;
  
  public void initialize()
  {
    gp.add(GuiUtilFactory.getHourlyTitleHBox(), 0, 0);
    for(HourlySalesItem hsi: MainApplication.dataHub.getPast4HourlySalesMaps().get(3).getHourlySalesItems())
    {
      itemBox.getChildren().addAll(GuiUtilFactory.createHourlyItemBox(hsi), new Separator());
    }
  }
}
