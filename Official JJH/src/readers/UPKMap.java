package readers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author crost
 *  Map<int-Category1-7, <String-item/cogs, <int-datatype8-18, double-data>>>
 */
public class UPKMap extends HashMap<Integer, HashMap<String, HashMap<Integer, Double>>>
{
  private static final long serialVersionUID = 7042509984354219587L;
  
  public static final int BREAD = 1, FOOD = 2, SIDES = 3, PAPER = 4, PRODUCE = 5, BEVERAGE = 6,
      CATERING = 7, UNIT = 8, ACTUAL_USAGE = 9, THEORETICAL_USAGE = 10, USAGE_VARIANCE = 11,
      USAGE_VARIANCE$ = 12, ACTUAL_UPK = 13, AVERAGE_UPK = 14, UPK_VARIANCE = 15, ACTUAL_COGS = 16,
      THEORETICAL_COGS = 17, COGS_VARIANCE = 18;
  public static final String cogs = "COGs";
  private double adjustedSales = -1;
  private int count = 22;

  public UPKMap(File file)
  {
    for(int ii = 1; ii < 8; ii++)
    {
      put(ii, new HashMap<String, HashMap<Integer, Double>>());
    }
    try
    {
      Scanner scan = new Scanner(file);
      while(scan.hasNext())
      {
        String line = scan.nextLine();
        String[] tokens = line.split(",");
        if(tokens[0].startsWith("\"Store :"))
        {
          //3 adj sales
          adjustedSales = Double.parseDouble(tokens[3].substring(17));
          
          //16 category
          int category = -1;
          switch(tokens[16].split(" ")[0])
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
          //Create cogs map
          get(category).put(cogs, new HashMap<Integer, Double>());
          //17 actual cogs
          get(category).get(cogs).put(ACTUAL_COGS, Double.parseDouble(tokens[17].split(" ")[0]));
          //18 theoretical cogs
          get(category).get(cogs).put(THEORETICAL_COGS, Double.parseDouble(tokens[17].split(" ")[0]));
          //19 cogs variance
          get(category).get(cogs).put(COGS_VARIANCE, Double.parseDouble(tokens[17].split(" ")[0]));
          
          //20 Item name
          String item = tokens[20];
          get(category).put(item, new HashMap<Integer, Double>());
          
          //21 unit
          //Starts at count
          count = 22;
          //22 actual usage
          get(category).get(item).put(ACTUAL_USAGE, removeQuotes(tokens));
          get(category).get(item).put(THEORETICAL_USAGE, removeQuotes(tokens));
          get(category).get(item).put(USAGE_VARIANCE, removeQuotes(tokens));
          get(category).get(item).put(USAGE_VARIANCE$, removeQuotes(tokens));
          get(category).get(item).put(ACTUAL_UPK, removeQuotes(tokens));
          get(category).get(item).put(AVERAGE_UPK, removeQuotes(tokens));
          get(category).get(item).put(UPK_VARIANCE, removeQuotes(tokens));
        }
      }
      scan.close();
    }
    catch (FileNotFoundException fnf)
    {
      System.out.println("Could not find file for upkmap");
    }
    catch(NumberFormatException nfe)
    {
      System.out.println("error parsing in upkmap");
    }
  }
  
  /**
   * removes quotes and increments count
   * @param tokens
   * @return
   */
  private double removeQuotes(String[] tokens)
  {
    if(tokens[count].startsWith("\""))
    {
      String tok1 = tokens[count];
      count++;
      String tok2 = tokens[count];
      count++;
      return Double.parseDouble(tok1.substring(1).concat(tok2.substring(0, tok2.length()-1)));
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
}
