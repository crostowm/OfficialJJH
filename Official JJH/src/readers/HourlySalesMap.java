package readers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import error_handling.ErrorHandler;
import lineitems.HourlySalesItem;
import util.ParseUtil;

/**
 * @author crost HashMap<Int-Order Category, HashMap<Int-Hour 24hr<HashMap<Int-Data Type Category,
 *         Double>>> 0 for total category -1 for total hour
 */
public class HourlySalesMap
{
  private ArrayList<HourlySalesItem> items = new ArrayList<HourlySalesItem>();

  public HourlySalesMap(File file)
  {
    Scanner scanner;
    ArrayList<Double> takeoutValue = new ArrayList<Double>();
    ArrayList<Double> pickupValue = new ArrayList<Double>();
    ArrayList<Double> deliveryValue = new ArrayList<Double>();
    ArrayList<Double> eatInValue = new ArrayList<Double>();
    ArrayList<Double> onlinePickupValue = new ArrayList<Double>();
    ArrayList<Double> onlineDeliveryValue = new ArrayList<Double>();
    ArrayList<Integer> takeoutCount = new ArrayList<Integer>();
    ArrayList<Integer> pickupCount = new ArrayList<Integer>();
    ArrayList<Integer> deliveryCount = new ArrayList<Integer>();
    ArrayList<Integer> eatInCount = new ArrayList<Integer>();
    ArrayList<Integer> onlinePickupCount = new ArrayList<Integer>();
    ArrayList<Integer> onlineDeliveryCount = new ArrayList<Integer>();
    ArrayList<Double> total$ = new ArrayList<Double>();
    ArrayList<Integer> totalCount = new ArrayList<Integer>();
    ArrayList<Double> totalPercent = new ArrayList<Double>();

    try
    {
      scanner = new Scanner(file);
      System.out.println("Reading " + file.getName());
      while (scanner.hasNext())
      {
        ArrayList<String> tokens = ParseUtil.getQuoteConsolidatedList(scanner.nextLine().split(","));
        if (tokens.size() > 1)
        {
          if (tokens.get(1).equals("Time"))
          {
            System.out.println("Reading a line");
            // Start at first time interval
            for (int ii = 7; ii < 151; ii += 6)
            {
              // Read Time TODO if time pm + 12
              try
              {
                // Value
                double val = Double.parseDouble(tokens.get(ii + 1).substring(1));
                int count = (int)Double.parseDouble(tokens.get(ii + 2));
                switch (tokens.get(0))
                {
                  case "Take Out":
                    takeoutValue.add(val);
                    takeoutCount.add(count);
                    break;
                  case "Pickup":
                    pickupValue.add(val);
                    pickupCount.add(count);
                    break;
                  case "Delivery":
                    deliveryValue.add(val);
                    deliveryCount.add(count);
                    break;
                  case "Eat In":
                    eatInValue.add(val);
                    eatInCount.add(count);
                    break;
                  case "Online Pickup":
                    onlinePickupValue.add(val);
                    onlinePickupCount.add(count);
                    break;
                  case "Online Delivery":
                    onlineDeliveryValue.add(val);
                    onlineDeliveryCount.add(count);
                    total$.add(Double.parseDouble(tokens.get(ii + 3).substring(1)));
                    totalCount.add((int)Double.parseDouble(tokens.get(ii + 4)));
                    totalPercent.add(Double
                        .parseDouble(tokens.get(ii + 5).substring(0, tokens.get(ii + 5).length() - 1)));
                    break;
                }
              }
              catch (NumberFormatException e)
              {
                e.printStackTrace();
                ErrorHandler.addError(e);
                break;
              }
            }
          }
        }
      }
      scanner.close();
    }
    catch (

    FileNotFoundException e)
    {
      e.printStackTrace();
      ErrorHandler.addError(e);
    }
    //If none for the day
    if(takeoutCount.size() == 0)
    {
      for(int ii = 0; ii < 24; ii++)
      {
        takeoutCount.add(0);
        takeoutValue.add(0.0);
      }
    }
    if(pickupCount.size() == 0)
    {
      for(int ii = 0; ii < 24; ii++)
      {
        pickupCount.add(0);
        pickupValue.add(0.0);
      }
    }
    if(deliveryCount.size() == 0)
    {
      for(int ii = 0; ii < 24; ii++)
      {
        deliveryCount.add(0);
        deliveryValue.add(0.0);
      }
    }
    if(eatInCount.size() == 0)
    {
      for(int ii = 0; ii < 24; ii++)
      {
        eatInCount.add(0);
        eatInValue.add(0.0);
      }
    }
    if(onlineDeliveryCount.size() == 0)
    {
      for(int ii = 0; ii < 24; ii++)
      {
        onlineDeliveryCount.add(0);
        onlineDeliveryValue.add(0.0);
      }
    }
    if(onlinePickupCount.size() == 0)
    {
      for(int ii = 0; ii < 24; ii++)
      {
        onlinePickupCount.add(0);
        onlinePickupValue.add(0.0);
      }
    }
    for (int ii = 0; ii < 24; ii++)
    {
      items.add(new HourlySalesItem(takeoutValue.get(ii), pickupValue.get(ii),
          deliveryValue.get(ii), eatInValue.get(ii), onlinePickupValue.get(ii),
          onlineDeliveryValue.get(ii), total$.get(ii), totalPercent.get(ii), totalCount.get(ii),
          takeoutCount.get(ii), pickupCount.get(ii), deliveryCount.get(ii), eatInCount.get(ii),
          onlinePickupCount.get(ii), onlineDeliveryCount.get(ii)));
    }
  }

  public double getTakeoutPickupEatin$ForHour(int hour)
  {
    HourlySalesItem item = items.get(hour);
    return item.getTakeoutValue() + item.getPickupValue() + item.getEatInValue()
        + item.getOnlinePickupValue();
  }

  public double getDelivery$ForHour(int hour)
  {
    HourlySalesItem item = items.get(hour);
    return item.getDeliveryValue() + item.getOnlineDeliveryValue();
  }
}
