package readers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

import error_handling.ErrorHandler;

/**
 * @author crost Map<int-Category1-7, <String-item/cogs, <int-datatype8-18, double-data>>>
 *         Map<int-Category1-7, <Integer-cogtype, Double-cog> Map<String-item, String-unit>
 */
public class UPKMap
{
  private HashMap<Integer, HashMap<String, HashMap<Integer, Double>>> upkMap = new HashMap<Integer, HashMap<String, HashMap<Integer, Double>>>();
  private HashMap<Integer, HashMap<Integer, Double>> cogsMap = new HashMap<Integer, HashMap<Integer, Double>>();
  private HashMap<String, String> units = new HashMap<String, String>();

  public static final int BREAD = 1, FOOD = 2, SIDES = 3, PAPER = 4, PRODUCE = 5, BEVERAGE = 6,
      CATERING = 7, UNIT = 8, ACTUAL_USAGE = 9, THEORETICAL_USAGE = 10, USAGE_VARIANCE = 11,
      USAGE_VARIANCE$ = 12, ACTUAL_UPK = 13, AVERAGE_UPK = 14, UPK_VARIANCE = 15, ACTUAL_COGS = 16,
      THEORETICAL_COGS = 17, COGS_VARIANCE = 18, CASE_VALUE = 19;
  private double adjustedSales = -1;
  private int count = 22;

  public UPKMap(File file)
  {
    for (int ii = 1; ii < 8; ii++)
    {
      upkMap.put(ii, new HashMap<String, HashMap<Integer, Double>>());
      cogsMap.put(ii, new HashMap<Integer, Double>());
    }
    try
    {
      Scanner scan = new Scanner(file);
      while (scan.hasNext())
      {
        String line = scan.nextLine();
        String[] tokens = line.split(",");
        if (tokens[0].startsWith("\"Store :"))
        {
          // 3 adj sales
          adjustedSales = Double.parseDouble(tokens[3].substring(17));

          // 16 category
          int category = -1;
          switch (tokens[16].split(" ")[0])
          {
            case "Bread":
              category = 1;
              break;
            case "Food":
              category = 2;
              break;
            case "Sides":
              category = 3;
              break;
            case "Paper":
              category = 4;
              break;
            case "Produce":
              category = 5;
              break;
            case "Beverage":
              category = 6;
              break;
            case "Catering":
              category = 7;
              break;
          }
          // 17 actual cogs
          cogsMap.get(category).put(ACTUAL_COGS, Double.parseDouble(tokens[17].split(" ")[0]));
          // 18 theoretical cogs
          cogsMap.get(category).put(THEORETICAL_COGS, Double.parseDouble(tokens[18].split(" ")[0]));
          // 19 cogs variance
          cogsMap.get(category).put(COGS_VARIANCE, Double.parseDouble(tokens[19].split(" ")[0]));

          // 20 Item name
          String item = tokens[20];
          upkMap.get(category).put(item, new HashMap<Integer, Double>());

          // 21 unit
          units.put(item, tokens[21]);

          // Starts at count
          count = 22;
          // 22 actual usage
          upkMap.get(category).get(item).put(ACTUAL_USAGE, removeQuotes(tokens));
          upkMap.get(category).get(item).put(THEORETICAL_USAGE, removeQuotes(tokens));
          upkMap.get(category).get(item).put(USAGE_VARIANCE, removeQuotes(tokens));
          upkMap.get(category).get(item).put(USAGE_VARIANCE$, removeQuotes(tokens));
          upkMap.get(category).get(item).put(ACTUAL_UPK, removeQuotes(tokens));
          upkMap.get(category).get(item).put(AVERAGE_UPK, removeQuotes(tokens));
          upkMap.get(category).get(item).put(UPK_VARIANCE, removeQuotes(tokens));
          
          upkMap.get(category).get(item).put(CASE_VALUE, getCaseValue(item));
        }
      }
      scan.close();
    }
    catch (FileNotFoundException fnf)
    {
      System.out.println("Could not find file for upkmap");
      ErrorHandler.addError(fnf);
    }
    catch (NumberFormatException nfe)
    {
      System.out.println("error parsing in upkmap");
      ErrorHandler.addError(nfe);
    }
  }

  public double getCaseValue(String name)
  {
    switch(name)
    {
      case "BACON - SLI APPLWD SMKD CKD":
        return 600;
      case "Sugar":
      case "Onions":
        return 50;
      case "Capicola":
        return 40.2;
      case "Ham":
        return 40;
      case "Salami":
        return 39;
      case "Cheese":
        return 36;
      case "Wheat Sub -  9 Grain Roll":
      case "French Bread 32 ct":
        return 32;
      case "Beef":
        return 30;
      case "Tomatoes":
        return 25;
      case "Cucumbers":
      case "Lettuce":
        return 24;
      case "Turkey":
        return 18.8;
      case "Kickin Ranch Dressing Mix":
        return 18;
      case "Avocado":
        return 12;
      case "Oil":
      case "Pan Spray":
      case "Tuna Chunk - pouch":
      case "MUSTARD WHL GRAIN":
        return 6;
      case "Peppers Cherry":
      case "Mayonnaise":
      case "Vinegar":
      case "Butter (salted)":
        return 4;
      default:
        return 1;
    }
  }

  /**
   * removes quotes and increments count
   * 
   * @param tokens
   * @return
   */
  private double removeQuotes(String[] tokens)
  {
    if (tokens[count].startsWith("\""))
    {
      String tok1 = tokens[count];
      count++;
      String tok2 = tokens[count];
      count++;
      return Double.parseDouble(tok1.substring(1).concat(tok2.substring(0, tok2.length() - 1)));
    }
    else
    {
      String tok = tokens[count];
      count++;
      return Double.parseDouble(tok);
    }
  }

  public double getAdjustedSales()
  {
    return adjustedSales;
  }

  public double getData(int category, String name, int dataType)
  {
    return upkMap.get(category).get(name).get(dataType);
  }

  public double getCogsForCategory(int category, int cogType)
  {
    return cogsMap.get(category).get(cogType);
  }

  public String getUnitsForItem(String item)
  {
    return units.get(item);
  }

  public HashMap<Integer, HashMap<String, HashMap<Integer, Double>>> getUPKMap()
  {
    return upkMap;
  }

  public double getData(String parsedName, int dataType)
  {
    for(int cat: upkMap.keySet())
    {
      for(String name: upkMap.get(cat).keySet())
      {
        if(name.equals(parsedName))
          return upkMap.get(cat).get(name).get(dataType);
      }
    }
    return 0.0;
  }
}
