package readers;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Scanner;

import error_handling.ErrorHandler;
import lineitems.InventoryItem;
import lineitems.TheoryUsageLineItem;
import lineitems.TransferLineItem;
import lineitems.VendorDeliveryDetailLineItem;
import util.ParseUtil;

public class ItemUsageAnalysisReader
{

  private InventoryItem itemLine;
  private String name;
  private double begInv, totPurch, totTrans, endInv, actUsage, theoreticalUsage;
  private ArrayList<TheoryUsageLineItem> theoryUsages = new ArrayList<TheoryUsageLineItem>();
  private ArrayList<VendorDeliveryDetailLineItem> vendorItems = new ArrayList<VendorDeliveryDetailLineItem>();
  private ArrayList<TransferLineItem> transIn = new ArrayList<TransferLineItem>();
  private ArrayList<TransferLineItem> transOut = new ArrayList<TransferLineItem>();

  public ItemUsageAnalysisReader(File file)
  {
    try
    {
      Scanner scanner = new Scanner(file);
      while (scanner.hasNext())
      {
        String line = scanner.nextLine();
        ArrayList<String> tokens = ParseUtil.getQuoteConsolidatedList(line.split(","));
        if (tokens.size() > 1)
        {
          try
          {
            if (tokens.get(1).equals("Beginning Inventory"))
            {
              name = tokens.get(0).trim();
              begInv = Double.parseDouble(tokens.get(2).split(" ")[0]);
              totPurch = Double.parseDouble(tokens.get(4).split(" ")[0]);
              totTrans = Double.parseDouble(tokens.get(6).split(" ")[0]);
              endInv = Double.parseDouble(tokens.get(8).split(" ")[0]);
              actUsage = Double.parseDouble(tokens.get(10).split(" ")[0]);

            }
            else if (tokens.get(0).equals("Theory Usage"))
            {
              if (!tokens.get(7).equals(""))
              {
                int menuItem = Integer.parseInt(tokens.get(7));
                String desc = tokens.get(8);
                double qtySold = Double.parseDouble(tokens.get(9));
                double portion = Double.parseDouble(tokens.get(10));
                double totalQty = Double.parseDouble(tokens.get(11));
                String unit = tokens.get(12);
                theoryUsages
                    .add(new TheoryUsageLineItem(menuItem, desc, unit, qtySold, totalQty, portion));
                theoreticalUsage = Double.parseDouble(tokens.get(14).split(" ")[0]);
              }
            }
            else if (tokens.get(0).equals("Vendor Delivery Detail"))
            {
              if (!tokens.get(9).equals(""))
              {
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                GregorianCalendar gc = new GregorianCalendar();
                gc.setTime(sdf.parse(tokens.get(8)));
                String invoiceNum = tokens.get(9);
                String vendorNum = tokens.get(10);
                double qtyCase = Double.parseDouble(tokens.get(11));
                String desc = tokens.get(12);
                double qtyEa = Double.parseDouble(tokens.get(13));
                String unit = tokens.get(14);
                vendorItems.add(new VendorDeliveryDetailLineItem(gc, invoiceNum, vendorNum, qtyCase,
                    qtyEa, desc, unit));
              }
            }
            else if (tokens.get(0).equals("Transfer In"))
            {
              if (!tokens.get(6).equals(""))
              {
                String fromStore = tokens.get(6);
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                GregorianCalendar tranDate = new GregorianCalendar();
                tranDate.setTime(sdf.parse(tokens.get(7)));
                int itemID = Integer.parseInt(tokens.get(8));
                double qtyEa = Double.parseDouble(tokens.get(9));
                String unit = tokens.get(10);
                transIn.add(new TransferLineItem(fromStore, unit, tranDate, itemID, qtyEa));
              }
            }
            else if (tokens.get(0).equals("Transfer Out"))
            {
              if (!tokens.get(6).equals(""))
              {
                String fromStore = tokens.get(6);
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                GregorianCalendar tranDate = new GregorianCalendar();
                tranDate.setTime(sdf.parse(tokens.get(7)));
                int itemID = Integer.parseInt(tokens.get(8));
                double qtyEa = Double.parseDouble(tokens.get(9));
                String unit = tokens.get(10);
                transOut.add(new TransferLineItem(fromStore, unit, tranDate, itemID, qtyEa));
              }
            }
            else if (tokens.get(0).equals("Waste"))
            {

            }
          }
          catch (NumberFormatException nfe)
          {
            nfe.printStackTrace();
            ErrorHandler.addError(nfe);
            continue;
          }
        }
      }
      this.itemLine = new InventoryItem(name, begInv, totPurch, totTrans, endInv, actUsage,
          theoryUsages, theoreticalUsage, vendorItems, transIn, transOut);
      scanner.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
      ErrorHandler.addError(e);
    }
  }

  public InventoryItem getItemLine()
  {
    return itemLine;
  }
}
