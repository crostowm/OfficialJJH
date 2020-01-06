package gui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import lineitems.TheoryUsageLineItem;

public class TheoryUsageBox extends HBox
{

  public TheoryUsageBox(TheoryUsageLineItem ti)
  {
    super(10);
    Label menuNum = new Label(String.format("%d", ti.getMenuItemNum()));
    menuNum.setMinWidth(100);
    menuNum.setPrefWidth(100);
    menuNum.setMaxWidth(100);
    menuNum.setAlignment(Pos.CENTER);
    Label desc = new Label(ti.getDesc());
    desc.setMinWidth(200);
    desc.setPrefWidth(200);
    desc.setMaxWidth(200);
    desc.setAlignment(Pos.CENTER);
    Label unit = new Label(ti.getUnit());
    unit.setMinWidth(50);
    unit.setPrefWidth(50);
    unit.setMaxWidth(50);
    unit.setAlignment(Pos.CENTER);
    Label qtySold = new Label(String.format("%.2f", ti.getQtySold())); 
    qtySold.setMinWidth(55);
    qtySold.setPrefWidth(55);
    qtySold.setMaxWidth(55);
    qtySold.setAlignment(Pos.CENTER);
    Label totalQty = new Label(String.format("%.2f", ti.getTotalQty())); 
    totalQty.setMinWidth(55);
    totalQty.setPrefWidth(55);
    totalQty.setMaxWidth(55);
    totalQty.setAlignment(Pos.CENTER);
    Label portion = new Label(String.format("%.2f", ti.getPortion()));
    portion.setMinWidth(55);
    portion.setPrefWidth(55);
    portion.setMaxWidth(55);
    portion.setAlignment(Pos.CENTER);
    getChildren().addAll(menuNum, desc, unit, qtySold, totalQty, portion);
  }

}
