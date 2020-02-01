package gui;

import java.util.ArrayList;

import app.AppDirector;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import lineitems.InventoryItem;
import lineitems.UPKItem;
import observers.InventoryBoxObserver;

public class InventoryBox extends HBox
{
  private ArrayList<InventoryBoxObserver> observers = new ArrayList<InventoryBoxObserver>();

  public InventoryBox(InventoryItem ii)
  {
    super(10);
    Label name = new Label(ii.getParsedName());
    name.setMinWidth(195);
    name.setPrefWidth(195);
    name.setMaxWidth(195);
    Separator s1 = new Separator(Orientation.VERTICAL);
    Label begInventory = new Label(String.format("%.2f", ii.getBegInv()));
    begInventory.setMinWidth(65);
    begInventory.setPrefWidth(65);
    begInventory.setMaxWidth(65);
    begInventory.setAlignment(Pos.CENTER);
    Separator s2 = new Separator(Orientation.VERTICAL);
    Label totPurch = new Label(String.format("%.2f", ii.getTotPurch()));
    totPurch.setMinWidth(65);
    totPurch.setPrefWidth(65);
    totPurch.setMaxWidth(65);
    totPurch.setAlignment(Pos.CENTER);
    totPurch.setOnMouseClicked(new EventHandler<MouseEvent>()
    {
      @Override
      public void handle(MouseEvent arg0)
      {
        for (InventoryBoxObserver ibo : observers)
        {
          ibo.vendorClicked("Vendor Purchases For " + ii.getName(), ii.getVendorItems());
        }
      }
    });
    totPurch.setOnMouseEntered(new EventHandler<MouseEvent>()
    {
      @Override
      public void handle(MouseEvent arg0)
      {
        totPurch.setStyle("-fx-background-color: rgba(0, 0, 0, .3);");
      }
    });
    totPurch.setOnMouseExited(new EventHandler<MouseEvent>()
    {
      @Override
      public void handle(MouseEvent arg0)
      {
        totPurch.setStyle("-fx-background-color: none;");
      }
    });
    Separator s3 = new Separator(Orientation.VERTICAL);
    Label totTrans = new Label(String.format("%.2f", ii.getTotTrans()));
    totTrans.setMinWidth(65);
    totTrans.setPrefWidth(65);
    totTrans.setMaxWidth(65);
    totTrans.setAlignment(Pos.CENTER);
    totTrans.setOnMouseClicked(new EventHandler<MouseEvent>()
    {
      @Override
      public void handle(MouseEvent arg0)
      {
        for (InventoryBoxObserver ibo : observers)
        {
          ibo.transClicked("All Transfers For " + ii.getName(), ii.getTransfersIn(), ii.getTransfersOut());
        }
      }
    });
    totTrans.setOnMouseEntered(new EventHandler<MouseEvent>()
    {
      @Override
      public void handle(MouseEvent arg0)
      {
        totTrans.setStyle("-fx-background-color: rgba(0, 0, 0, .3);");
      }
    });
    totTrans.setOnMouseExited(new EventHandler<MouseEvent>()
    {
      @Override
      public void handle(MouseEvent arg0)
      {
        totTrans.setStyle("-fx-background-color: none;");
      }
    });
    Separator s4 = new Separator(Orientation.VERTICAL);
    Label endInv = new Label(String.format("%.2f", ii.getEndInv()));
    endInv.setMinWidth(65);
    endInv.setPrefWidth(65);
    endInv.setMaxWidth(65);
    endInv.setAlignment(Pos.CENTER);
    Separator s5 = new Separator(Orientation.VERTICAL);
    Label theorUsage = new Label(String.format("%.2f", ii.getTheoreticalUsage()));
    theorUsage.setMinWidth(65);
    theorUsage.setPrefWidth(65);
    theorUsage.setMaxWidth(65);
    theorUsage.setAlignment(Pos.CENTER);
    theorUsage.setOnMouseClicked(new EventHandler<MouseEvent>()
    {
      @Override
      public void handle(MouseEvent arg0)
      {
        for (InventoryBoxObserver ibo : observers)
        {
          ibo.theoryUsageClicked("Theoretical Usages For " + ii.getName(), ii.getTheoryUsages());
        }
      }
    });
    theorUsage.setOnMouseEntered(new EventHandler<MouseEvent>()
    {
      @Override
      public void handle(MouseEvent arg0)
      {
        theorUsage.setStyle("-fx-background-color: rgba(0, 0, 0, .3);");
      }
    });
    theorUsage.setOnMouseExited(new EventHandler<MouseEvent>()
    {
      @Override
      public void handle(MouseEvent arg0)
      {
        theorUsage.setStyle("-fx-background-color: none;");
      }
    });
    Separator s6 = new Separator(Orientation.VERTICAL);
    Label actUsage = new Label(String.format("%.2f", ii.getActUsage()));
    actUsage.setMinWidth(65);
    actUsage.setPrefWidth(65);
    actUsage.setMaxWidth(65);
    actUsage.setAlignment(Pos.CENTER);
    Separator s7 = new Separator(Orientation.VERTICAL);
    UPKItem upkItem = AppDirector.dataHub.getPast6UPKMaps().get(5).getUPKItem(ii.getName());
    Label avgUPK = new Label(
        upkItem == null ? "null" : String.format("%.2f", upkItem.getAverageUPK()));
    avgUPK.setMinWidth(65);
    avgUPK.setPrefWidth(65);
    avgUPK.setMaxWidth(65);
    avgUPK.setAlignment(Pos.CENTER);
    Separator s8 = new Separator(Orientation.VERTICAL);
    Label actUPK = new Label(
        upkItem == null ? "null" : String.format("%.2f", upkItem.getActualUPK()));
    actUPK.setMinWidth(65);
    actUPK.setPrefWidth(65);
    actUPK.setMaxWidth(65);
    actUPK.setAlignment(Pos.CENTER);
    Separator s9 = new Separator(Orientation.VERTICAL);
    Label upkVar = new Label(
        upkItem == null ? "null" : String.format("%.2f", upkItem.getUPKVariance()));
    upkVar.setMinWidth(65);
    upkVar.setPrefWidth(65);
    upkVar.setMaxWidth(65);
    upkVar.setAlignment(Pos.CENTER);
    Separator s10 = new Separator(Orientation.VERTICAL);

    // Style
    if (upkItem != null)
    {
      if (upkItem.getUPKVariance() > 2 || upkItem.getUPKVariance() < -2)
      {
        setStyle("-fx-background-color: rgba(255, 0, 0, .2);");
      }
      else if (upkItem.getUPKVariance() > 1 || upkItem.getUPKVariance() < -1)
      {
        setStyle("-fx-background-color: rgba(255, 128, 0, .2);");
      }
      else
      {
        setStyle("-fx-background-color: rgba(0, 255, 0, .2);");
      }
    }
    double usageVariance = (ii.getActUsage() - ii.getTheoreticalUsage()) / ii.getTheoreticalUsage();
    if (usageVariance > .1 || usageVariance < -.1)
    {
      actUsage.setStyle("-fx-background-color: rgba(255, 0, 0, .8);-fx-border-radius: 10 10 10 10;-fx-background-radius: 10 10 10 10;");

    }
    else if (usageVariance > .05 || usageVariance < -.05)
    {
      actUsage.setStyle("-fx-background-color: rgba(255, 128, 0, .8);-fx-border-radius: 10 10 10 10;-fx-background-radius: 10 10 10 10;");
    }
    else
    {
      actUsage.setStyle("-fx-background-color: rgba(0, 255, 0, .4);-fx-border-radius: 10 10 10 10;-fx-background-radius: 10 10 10 10;");
    }

    getChildren().addAll(name, s1, begInventory, s2, totPurch, s3, totTrans, s4, endInv, s5,
        theorUsage, s6, actUsage, s7, avgUPK, s8, actUPK, s9, upkVar, s10);
  }

  public void addObserver(InventoryBoxObserver iio)
  {
    observers.add(iio);
  }
}
