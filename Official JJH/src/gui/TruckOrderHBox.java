package gui;

import java.util.HashMap;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import readers.UPKMap;

public class TruckOrderHBox extends UPKHBox
{

  public TruckOrderHBox(int category, double adjustedSales, String n, HashMap<Integer, Double> data)
  {
    super(category, adjustedSales, n, data);
    setSpacing(10);
    Label name = new Label(n);
    name.setPrefWidth(200);
    
    Label acUsage = new Label(data.get(UPKMap.ACTUAL_USAGE) + "");
    acUsage.setPrefWidth(100);
    acUsage.setAlignment(Pos.CENTER);
    
    Label avgUPK = new Label(data.get(UPKMap.AVERAGE_UPK) + "");
    avgUPK.setPrefWidth(100);
    avgUPK.setAlignment(Pos.CENTER);
    
    TextField onHand = new TextField();
    onHand.setPrefWidth(60);
    HBox.setMargin(onHand, new Insets(0, 20, 0, 20));
    onHand.setAlignment(Pos.CENTER);
    
    getChildren().addAll(name, acUsage, avgUPK, onHand);
  }

}
