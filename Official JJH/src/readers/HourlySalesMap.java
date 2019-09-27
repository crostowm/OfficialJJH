package readers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author crost HashMap<Int-Order Category, HashMap<Int-Hour 24hr<HashMap<Int-Data Type Category,
 *         Double>>>
 *
 */
public class HourlySalesMap extends HashMap<Integer, HashMap<Integer,HashMap<Integer, Double>>>
{
  private static final long serialVersionUID = 8953713896638507995L;
  public static final int TAKE_OUT = 1, PICKUP = 2, DELIVERY = 3, EAT_IN = 4, ONLINE_PICKUP = 5,
      ONLINE_DELIVERY = 6, VALUE = 7, COUNT = 8, TOTAL$ = 9, TOTAL_COUNT = 10, TOTAL_PERCENT = 11;

  public HourlySalesMap(String fileName)
  {
    for(int ii = 1; ii < 7; ii++)
    {
      put(ii, new HashMap<Integer, HashMap<Integer, Double>>());
      for(int i = 0; i < 24; i++)
      {
        get(ii).put(i, new HashMap<Integer, Double>());
      }
    }
    Scanner scanner;
    try
    {
      scanner = new Scanner(new File(fileName));
      while (scanner.hasNext())
      {
        int currentCat;
        String[] tokens = scanner.nextLine().split(",");
        switch (tokens[0])
        {
          case "Take Out":
            currentCat = 1;
            break;
          case "Pickup":
            currentCat = 2;
            break;
          case "Delivery":
            currentCat = 3;
            break;
          case "Eat In":
            currentCat = 4;
            break;
          case "Online Pickup":
            currentCat = 5;
            break;
          case "Online Delivery":
            currentCat = 6;
            break;
          default:
            continue;
        }
        // Start at first time interval
        for (int ii = 7; ii < tokens.length; ii += 6)
        {
          //Read Time TODO if time pm + 12
          int time = 0;
          try
          {
            if(tokens[ii].substring(tokens[ii].length()-2).equals("PM") && !tokens[ii].substring(0, 2).equals("12"))
              time = 12;
            if (!tokens[ii].equals("Total"))
              time += Integer.parseInt((tokens[ii].split(":"))[0]);
            System.out.println(time);
          }
          catch (NumberFormatException e)
          {
            break;
          }
          //Read Categories
          for(int i = 1; i < 6; i++)
          {
            String tok = tokens[ii+i];
            
            //Remove Quotes
            if(tok.charAt(0) == '"')
            {
              tok = tok.substring(1) + tokens[++ii + i].substring(0, tokens[ii + i].length()-1);
            }
            
            //Remove $
            if(i == 1 || i == 3)
              tok = tok.substring(1);
            
            //Total does not include %
            else if(i == 4 && time == 0)
              break;
            
            //Remove %
            else if(i == 5)
            {
              tok = tok.substring(0, tok.length()-1);
            }
            System.out.println("Test: " + currentCat + ", " + time + ", " + tok);
            get(currentCat).get(time).put(6+i, Double.parseDouble(tok));
          }
        }
      }
      scanner.close();
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }
  }
  
  public double getData(int orderType, int dataType, int time)
  {
    return get(orderType).get(time).get(dataType);
  }
}
