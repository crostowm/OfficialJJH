package readers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import error_handling.ErrorHandler;
import lineitems.UPKItem;
import lineitems.UPKWeek;
import util.ParseUtil;

/**
 * @author crost Map<int-Category1-7, <String-item/cogs, <int-datatype8-18, double-data>>>
 *         Map<int-Category1-7, <Integer-cogtype, Double-cog> Map<String-item, String-unit>
 */
public class UPKReader
{
  private HashMap<String, String> units = new HashMap<String, String>();
  private UPKWeek week = new UPKWeek();
  public UPKReader(File file)
  {
    try
    {
      Scanner scan = new Scanner(file);
      while (scan.hasNext())
      {
        String line = scan.nextLine();
        ArrayList<String> tokens = ParseUtil.getQuoteConsolidatedList(line.split(","));
        if (tokens.get(0).startsWith("Store :"))
        {
          // 1 adj sales
          week.setAdjustedSales(Double.parseDouble(tokens.get(1).substring(17)));
          // 14 category
          String category = tokens.get(14).split(" ")[0];
          week.setCOGS(category, UPKWeek.ACTUAL_COGS,
              Double.parseDouble(tokens.get(15).split(" ")[0]));
          week.setCOGS(category, UPKWeek.THEORETICAL_COGS,
              Double.parseDouble(tokens.get(16).split(" ")[0]));
          week.setCOGS(category, UPKWeek.COGS_VARIANCE,
              Double.parseDouble(tokens.get(17).split(" ")[0]));

          // 18 Item name
          String name = tokens.get(18);
          String unit = tokens.get(19);
          units.put(name, unit);
          double actualUsage = Double.parseDouble(tokens.get(20));
          double theoreticalUsage = Double.parseDouble(tokens.get(21));
          double usageVariance = Double.parseDouble(tokens.get(22));
          double usageVariance$ = Double.parseDouble(tokens.get(23));
          double actualUPK = Double.parseDouble(tokens.get(24));
          double averageUPK = Double.parseDouble(tokens.get(25));
          double upkVariance = Double.parseDouble(tokens.get(26));
          UPKItem item = new UPKItem(name, category, unit, actualUsage, theoreticalUsage,
              usageVariance, usageVariance$, actualUPK, averageUPK, upkVariance);
          System.out.println("Adding item: " + item.getName());
          week.addItem(item);
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
  
  public UPKWeek getWeek()
  {
    return week;
  }
}
