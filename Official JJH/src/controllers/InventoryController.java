package controllers;

import java.util.ArrayList;

import app.AppDirector;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lineitems.InventoryItem;
import lineitems.TheoryUsageLineItem;
import lineitems.TransferLineItem;
import lineitems.UPKItem;
import lineitems.UPKWeek;
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
  
  @FXML
  private TextField actualCOGS, theoreticalCOGS, cogsVariance;

  public void initialize()
  {
    System.out.println("IC");
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
        UPKWeek week = AppDirector.dataHub.getPast6UPKMaps().get(5);
        actualCOGS.setText(String.format("%.2f", week.getCOGS(categoryChoice.getValue(), UPKWeek.ACTUAL_COGS)));
        theoreticalCOGS.setText(String.format("%.2f", week.getCOGS(categoryChoice.getValue(), UPKWeek.THEORETICAL_COGS)));
        cogsVariance.setText(String.format("%.2f", week.getCOGS(categoryChoice.getValue(), UPKWeek.COGS_VARIANCE)));
        for (InventoryItem ii : AppDirector.dataHub.getInventoryItems())
        {
          UPKItem item = week.getUPKItem(ii.getName());
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
    System.out.println("IC-");
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
