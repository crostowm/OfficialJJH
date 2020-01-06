package controllers;

import java.util.ArrayList;

import app.MainApplication;
import gui.GuiUtilFactory;
import gui.InventoryBox;
import gui.TheoryUsageBox;
import gui.TransferBox;
import gui.VendorDeliveryDetailLineItemBox;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lineitems.InventoryItem;
import lineitems.TheoryUsageLineItem;
import lineitems.TransferLineItem;
import lineitems.UPKItem;
import lineitems.VendorDeliveryDetailLineItem;
import observers.InventoryBoxObserver;

public class InventoryController implements InventoryBoxObserver
{
  @FXML
  private Label detailBoxTitle;
  
  @FXML
  private VBox itemVBox, detailBox;

  @FXML
  private GridPane gp;

  @FXML
  private HBox titleBox;

  @FXML
  private ChoiceBox<String> categoryChoice;

  public void initialize()
  {
    // Can also sort by category by going through upk map
    gp.add(GuiUtilFactory.createInventoryTitleBox(), 0, 1, 2, 1);
    ArrayList<String> categories = new ArrayList<String>();
    categories.add("Bread");
    categories.add("Food");
    categories.add("Sides");
    categories.add("Paper");
    categories.add("Produce");
    categories.add("Beverage");
    categories.add("Catering");
    categoryChoice.setItems(FXCollections.observableArrayList(categories));
    categoryChoice.setValue("Select a Category");
    categoryChoice.setOnAction(new EventHandler<ActionEvent>()
    {
      @Override
      public void handle(ActionEvent arg0)
      {
        itemVBox.getChildren().clear();
        for (InventoryItem ii : MainApplication.dataHub.getInventoryItems())
        {
          UPKItem item = MainApplication.dataHub.getPast6UPKMaps().get(5).getUPKItem(ii.getName());
          if (item != null)
          {
            if (item.getCategory().equals(categoryChoice.getValue()))
            {
              InventoryBox ib = new InventoryBox(ii);
              ib.addObserver(InventoryController.this);
              itemVBox.getChildren().add(ib);
            }
          }
        }
      }
    });
  }

  @Override
  public void theoryUsageClicked(String desc, ArrayList<TheoryUsageLineItem> items)
  {
    detailBox.getChildren().clear();
    titleBox.getChildren().clear();
    detailBoxTitle.setText(desc);
    GuiUtilFactory.populateTheoryUsageTitleBox(titleBox);
    for (TheoryUsageLineItem ti : items)
    {
      detailBox.getChildren().add(new TheoryUsageBox(ti));
    }
  }

  @Override
  public void vendorClicked(String desc, ArrayList<VendorDeliveryDetailLineItem> items)
  {
    detailBox.getChildren().clear();
    titleBox.getChildren().clear();
    detailBoxTitle.setText(desc);
    GuiUtilFactory.populateVendorDeliveryDetailTitleBox(titleBox);
    for (VendorDeliveryDetailLineItem ti : items)
    {
      detailBox.getChildren().add(new VendorDeliveryDetailLineItemBox(ti));
    }
  }

  @Override
  public void transClicked(String desc, ArrayList<TransferLineItem> transIn,
      ArrayList<TransferLineItem> transOut)
  {
    detailBox.getChildren().clear();
    titleBox.getChildren().clear();
    detailBoxTitle.setText(desc);
    GuiUtilFactory.populateTransferTitleBox(titleBox);
    for (TransferLineItem ti : transIn)
    {
      detailBox.getChildren().add(new TransferBox(ti, true));
    }
    for (TransferLineItem ti : transOut)
    {
      detailBox.getChildren().add(new TransferBox(ti, false));
    }
  }
}
