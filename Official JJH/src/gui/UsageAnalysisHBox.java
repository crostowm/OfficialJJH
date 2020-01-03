package gui;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import lineitems.UPKItem;

public class UsageAnalysisHBox extends UPKHBox
{

  public UsageAnalysisHBox(UPKItem item, double adjSales)
  {
    super(adjSales, item);
    setSpacing(10);
    Label name = new Label(item.getName());
    name.setPrefWidth(200);
    Label acUsage = new Label(String.format("%.2f", item.getActualUsage()));
    acUsage.setPrefWidth(100);
    acUsage.setAlignment(Pos.CENTER);
    Label thUsage = new Label(String.format("%.2f", item.getTheoreticalUsage()));
    thUsage.setPrefWidth(100);
    thUsage.setAlignment(Pos.CENTER);
    Label usageVar = new Label(String.format("%.2f", item.getUsageVariance()));
    usageVar.setPrefWidth(100);
    usageVar.setAlignment(Pos.CENTER);
    Label usageVar$ = new Label(String.format("%.2f", item.getUsageVariance$()));
    usageVar$.setPrefWidth(100);
    usageVar$.setAlignment(Pos.CENTER);
    Label acUPK = new Label(String.format("%.2f", item.getActualUPK()));
    acUPK.setPrefWidth(100);
    acUPK.setAlignment(Pos.CENTER);
    Label avgUPK = new Label(String.format("%.2f", item.getAverageUPK()));
    avgUPK.setPrefWidth(100);
    avgUPK.setAlignment(Pos.CENTER);
    Label upkVar = new Label(String.format("%.2f", item.getUPKVariance()));
    upkVar.setPrefWidth(100);
    upkVar.setAlignment(Pos.CENTER);
    getChildren().addAll(name, acUsage, thUsage, usageVar, usageVar$, acUPK, avgUPK, upkVar);
    
    setOnMouseEntered(new EventHandler<MouseEvent>()
    {

      @Override
      public void handle(MouseEvent arg0)
      {
        if(item.getUPKVariance() > 2 || item.getUPKVariance() < -2)
        {
          setStyle("-fx-background-color: rgba(255, 0, 0, .4);");
        }
        else if(item.getUPKVariance() > 1 || item.getUPKVariance() < -1)
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
