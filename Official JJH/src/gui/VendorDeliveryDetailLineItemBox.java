package gui;

import java.text.SimpleDateFormat;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import lineitems.VendorDeliveryDetailLineItem;

public class VendorDeliveryDetailLineItemBox extends HBox
{
  public VendorDeliveryDetailLineItemBox(VendorDeliveryDetailLineItem item)
  {
    super(10);
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    Label deliveryDate = new Label(sdf.format(item.getDeliveryDate().getTime()));
    deliveryDate.setMinWidth(100);
    deliveryDate.setPrefWidth(100);
    deliveryDate.setMaxWidth(100);
    Label desc = new Label(item.getDesc());
    desc.setMinWidth(70);
    desc.setPrefWidth(70);
    desc.setMaxWidth(70);
    Label unit = new Label(item.getUnit());
    unit.setMinWidth(50);
    unit.setPrefWidth(50);
    unit.setMaxWidth(50);
    unit.setAlignment(Pos.CENTER);
    Label qtyCase = new Label(String.format("%.2f", item.getQtyCase()));
    qtyCase.setMinWidth(70);
    qtyCase.setPrefWidth(70);
    qtyCase.setMaxWidth(70);
    Label qtyEa = new Label(String.format("%.2f", item.getQtyEa()));
    qtyEa.setMinWidth(70);
    qtyEa.setPrefWidth(70);
    qtyEa.setMaxWidth(70);
    Label invoiceNum = new Label(item.getInvoiceNum());
    invoiceNum.setMinWidth(100);
    invoiceNum.setPrefWidth(100);
    invoiceNum.setMaxWidth(100);
    Label vendorNum = new Label(item.getVendorItemNum());
    vendorNum.setMinWidth(100);
    vendorNum.setPrefWidth(100);
    vendorNum.setMaxWidth(100);
    getChildren().addAll(deliveryDate, desc, unit, qtyCase, qtyEa, invoiceNum, vendorNum);
  }
}
