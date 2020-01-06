package gui;

import java.text.SimpleDateFormat;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import lineitems.TransferLineItem;

public class TransferBox extends HBox
{
  public TransferBox(TransferLineItem ti, boolean in)
  {
    super(10);
    Label inOut = new Label(in?"In":"Out");
    inOut.setMinWidth(40);
    inOut.setPrefWidth(40);
    inOut.setMaxWidth(40);
    inOut.setAlignment(Pos.CENTER);
    Label store = new Label(ti.getFromStore());
    store.setMinWidth(150);
    store.setPrefWidth(150);
    store.setMaxWidth(150);
    store.setAlignment(Pos.CENTER);
    Label unit = new Label(ti.getUnit());
    unit.setMinWidth(40);
    unit.setPrefWidth(40);
    unit.setMaxWidth(40);
    unit.setAlignment(Pos.CENTER);
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    Label date = new Label(sdf.format(ti.getTranDate()));
    date.setMinWidth(70);
    date.setPrefWidth(70);
    date.setMaxWidth(70);
    date.setAlignment(Pos.CENTER);
    Label itemId = new Label(String.format("%d", ti.getItemID()));
    itemId.setMinWidth(100);
    itemId.setPrefWidth(100);
    itemId.setMaxWidth(100);
    itemId.setAlignment(Pos.CENTER);
    Label qty = new Label(String.format("%.2d", ti.getQtyEa()));
    qty.setMinWidth(65);
    qty.setPrefWidth(65);
    qty.setMaxWidth(65);
    qty.setAlignment(Pos.CENTER);
    getChildren().addAll(inOut, store, unit, date, itemId, qty);

  }
}
