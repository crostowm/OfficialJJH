package observers;

import java.util.ArrayList;

import lineitems.TheoryUsageLineItem;
import lineitems.TransferLineItem;
import lineitems.VendorDeliveryDetailLineItem;

public interface InventoryBoxObserver
{
  public void theoryUsageClicked(String desc, ArrayList<TheoryUsageLineItem> items);
  public void vendorClicked(String desc, ArrayList<VendorDeliveryDetailLineItem> items);
  public void transClicked(String desc, ArrayList<TransferLineItem> transIn, ArrayList<TransferLineItem> transOut);
}
