package readers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author crost HashMap<Int-Order Category, HashMap<Int-Hour 24hr<HashMap<Int-Data Type Category,
 *         Double>>> 0 for total category -1 for total hour
 */
public class HourlySalesMap extends HashMap<Integer, HashMap<Integer, HashMap<Integer, Double>>>
{
  private static final long serialVersionUID = 8953713896638507995L;
  public static final int TOTAL = 0, TAKE_OUT = 1, PICKUP = 2, DELIVERY = 3, EAT_IN = 4,
      ONLINE_PICKUP = 5, ONLINE_DELIVERY = 6, VALUE = 7, COUNT = 8, TOTAL$ = 9, TOTAL_COUNT = 10,
      TOTAL_PERCENT = 11;

  public HourlySalesMap(File file)
  {
    for (int ii = 0; ii < 7; ii++)
    {
      put(ii, new HashMap<Integer, HashMap<Integer, Double>>());
      for (int i = -1; i < 24; i++)
      {
        get(ii).put(i, new HashMap<Integer, Double>());
        for(int j = 7; j < 12; j++)
        {
          get(ii).get(i).put(j, 0.0);
        }
      }
    }
    Scanner scanner;
    try
    {
      scanner = new Scanner(file);
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
          // Read Time TODO if time pm + 12
          int time = 0;
          try
          {
            if (tokens[ii].substring(tokens[ii].length() - 2).equals("PM")
                && !tokens[ii].substring(0, 2).equals("12"))
              time = 12;
            if (!tokens[ii].equals("Total"))
            {
              time += Integer.parseInt((tokens[ii].split(":"))[0]);
              // Read Categories
              for (int i = 1; i < 6; i++)
              {
                String tok = tokens[ii + i];
                // Remove Quotes
                if (tok.charAt(0) == '"')
                {
                  tok = tok.substring(1)
                      + tokens[++ii + i].substring(0, tokens[ii + i].length() - 1);
                }

                // Remove $
                if (i == 1 || i == 3)
                  tok = tok.substring(1);

                // Total does not include %
                else if (i == 4 && time == 0)
                  break;

                // Remove %
                else if (i == 5)
                {
                  tok = tok.substring(0, tok.length() - 1);
                }
                get(currentCat).get(time).put(6 + i, Double.parseDouble(tok));
              }
            }
            else
            {
              // Handle Total
              for (int i = 1; i < 5; i++)
              {
                String tok = tokens[ii + i];
                // Remove Quotes
                if (tok.charAt(0) == '"')
                {
                  tok = tok.substring(1)
                      + tokens[++ii + i].substring(0, tokens[ii + i].length() - 1);
                }
                // Remove $
                if (i == 1 || i == 3)
                  tok = tok.substring(1);

                switch (i)
                {
                  case 1:
                    get(currentCat).get(-1).put(TOTAL$, Double.parseDouble(tok));
                    break;
                  case 2:
                    get(currentCat).get(-1).put(TOTAL_COUNT, Double.parseDouble(tok));
                    break;
                  case 3:
                    get(TOTAL).get(-1).put(TOTAL$, Double.parseDouble(tok));
                    break;
                  case 4:
                    get(TOTAL).get(-1).put(TOTAL_COUNT, Double.parseDouble(tok));
                    break;
                }
              }
            }
          }
          catch (NumberFormatException e)
          {
            break;
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
    try
    {
      return get(orderType).get(time).get(dataType);
    }
    catch (Exception e)
    {
      System.out.println("Error getting data in hourly sales map\n");
      e.printStackTrace();
      return 0;
    }
  }

  public double getTotal$ForHour(int hour)
  {
    return get(1).get(hour).get(TOTAL$);
  }
  public double getTotal$ForCategory(int orderType)
  {
    return get(orderType).get(-1).get(TOTAL$);
  }

  public double getTotalCountForCategory(int orderType)
  {
    return get(orderType).get(-1).get(TOTAL_COUNT);
  }

  public double getTotal$()
  {
    return get(TOTAL).get(-1).get(TOTAL$);
  }

  public double getTotalCount()
  {
    return get(TOTAL).get(-1).get(TOTAL_COUNT);
  }
  
  public double getDelivery$ForHour(int hour)
  {
    return get(DELIVERY).get(hour).get(VALUE) + get(ONLINE_DELIVERY).get(hour).get(VALUE);
  }
  
  public double getTakeoutPickupEatin$ForHour(int hour)
  {
    return get(TAKE_OUT).get(hour).get(VALUE) + get(PICKUP).get(hour).get(VALUE) + get(EAT_IN).get(hour).get(VALUE) + get(ONLINE_PICKUP).get(hour).get(VALUE);
  }
}
