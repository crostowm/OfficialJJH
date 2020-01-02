package gui;

import java.util.HashMap;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import readers.UPKMap;

public class UsageAnalysisHBox extends UPKHBox
{

  public UsageAnalysisHBox(int category, double adjSales, String n, HashMap<Integer, Double> data)
  {
    super(category, adjSales, n, data);
    setSpacing(10);
    Label name = new Label(n);
    name.setPrefWidth(200);
    Label acUsage = new Label(data.get(UPKMap.ACTUAL_USAGE) + "");
    acUsage.setPrefWidth(100);
    acUsage.setAlignment(Pos.CENTER);
    Label thUsage = new Label(data.get(UPKMap.THEORETICAL_USAGE) + "");
    thUsage.setPrefWidth(100);
    thUsage.setAlignment(Pos.CENTER);
    Label usageVar = new Label(data.get(UPKMap.USAGE_VARIANCE) + "");
    usageVar.setPrefWidth(100);
    usageVar.setAlignment(Pos.CENTER);
    Label usageVar$ = new Label(data.get(UPKMap.USAGE_VARIANCE$) + "");
    usageVar$.setPrefWidth(100);
    usageVar$.setAlignment(Pos.CENTER);
    Label acUPK = new Label(data.get(UPKMap.ACTUAL_UPK) + "");
    acUPK.setPrefWidth(100);
    acUPK.setAlignment(Pos.CENTER);
    Label avgUPK = new Label(data.get(UPKMap.AVERAGE_UPK) + "");
    avgUPK.setPrefWidth(100);
    avgUPK.setAlignment(Pos.CENTER);
    Label upkVar = new Label(data.get(UPKMap.UPK_VARIANCE) + "");
    upkVar.setPrefWidth(100);
    upkVar.setAlignment(Pos.CENTER);
    getChildren().addAll(name, acUsage, thUsage, usageVar, usageVar$, acUPK, avgUPK, upkVar);
    
    setOnMouseEntered(new EventHandler<MouseEvent>()
    {

      @Override
      public void handle(MouseEvent arg0)
      {
        if(data.get(UPKMap.UPK_VARIANCE) > 2 || data.get(UPKMap.UPK_VARIANCE) < -2)
        {
          setStyle("-fx-background-color: rgba(255, 0, 0, .4);");
        }
        else if(data.get(UPKMap.UPK_VARIANCE) > 1 || data.get(UPKMap.UPK_VARIANCE) < -1)
        {
          setStyle("-fx-background-color: rgba(120, 120, 0, .4);");
        }
        else
        {
          setStyle("-fx-background-color: rgba(0, 255, 0, .4);");
        }
      }
    });
    
    setOnMouseExited(new EventHandler<MouseEvent>()
    {

      @Override
      public void handle(MouseEvent arg0)
      {
        setStyle("-fx-background-color: none;");
      }
    });
  }

}
