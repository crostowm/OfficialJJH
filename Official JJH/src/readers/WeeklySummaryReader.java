package readers;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import error_handling.ErrorHandler;
import lineitems.WeeklySummaryItem;
import util.ParseUtil;

public class WeeklySummaryReader
{
  private WeeklySummaryItem wsi;
  public WeeklySummaryReader(File file)
  {
    try
    {
      Scanner scan = new Scanner(file);
      while (scan.hasNext())
      {
        String line = scan.nextLine();
        ArrayList<String> tokens = ParseUtil.getQuoteConsolidatedList(line.split(","));
        if (tokens.get(0).startsWith("Entity"))
        {
          double mgrLaborPerc = Double.parseDouble(ParseUtil.parsePerc(tokens.get(16)));
          double mgrLabor$ = Double.parseDouble(ParseUtil.parse$(tokens.get(17)));
          double inshopLaborPer = Double.parseDouble(ParseUtil.parsePerc(tokens.get(19)));
          double inshopLabor$ = Double.parseDouble(ParseUtil.parse$(tokens.get(20)));
          double driverLaborPerc = Double.parseDouble(ParseUtil.parsePerc(tokens.get(22)));
          double driverLabor$ = Double.parseDouble(ParseUtil.parse$(tokens.get(23)));
          double taxLaborPerc = Double.parseDouble(ParseUtil.parsePerc(tokens.get(25)));
          double taxLabor$ = Double.parseDouble(ParseUtil.parse$(tokens.get(26)));
          double dmrLaborPerc = Double.parseDouble(ParseUtil.parsePerc(tokens.get(28)));
          double dmrLabor$ = Double.parseDouble(ParseUtil.parse$(tokens.get(29)));

          wsi = new WeeklySummaryItem(mgrLaborPerc, mgrLabor$, inshopLaborPer,
              inshopLabor$, driverLaborPerc, driverLabor$, taxLaborPerc, taxLabor$, dmrLaborPerc,
              dmrLabor$);
        }
      }
      scan.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
      ErrorHandler.addError(e);
    }
  }

  public WeeklySummaryItem getItem()
  {
    return wsi;
  }
}
