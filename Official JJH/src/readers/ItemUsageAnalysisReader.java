package readers;

import java.io.File;
import java.util.Scanner;

import error_handling.ErrorHandler;
import lineitems.ItemUsageLineItem;

public class ItemUsageAnalysisReader
{

  private ItemUsageLineItem itemLine;

  public ItemUsageAnalysisReader(File file)
  {
    try
    {
      Scanner scanner = new Scanner(file);
      while (scanner.hasNext())
      {
        String[] tokens = scanner.nextLine().split(",");
        if (tokens.length > 1)
        {
          if (tokens[1].equals("Beginning Inventory"))
          {
            try
            {
              double begInv = Double.parseDouble(tokens[2].split(" ")[0]);
              double totPurch = Double.parseDouble(tokens[4].split(" ")[0]);
              double totTrans = Double.parseDouble(tokens[6].split(" ")[0]);
              double endInv = Double.parseDouble(tokens[8].split(" ")[0]);
              double actUsage = Double.parseDouble(tokens[10].split(" ")[0]);
              this.itemLine = new ItemUsageLineItem(begInv, totPurch, totTrans, endInv, actUsage);
            }
            catch (NumberFormatException nfe)
            {
              nfe.printStackTrace();
              ErrorHandler.addError(nfe);
            }
          }
        }
      }
      scanner.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
      ErrorHandler.addError(e);
    }
  }

  public ItemUsageLineItem getItemLine()
  {
    return itemLine;
  }
}
