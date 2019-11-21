package readers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import error_handling.ErrorHandler;

public class WSRMap extends HashMap<String, HashMap<Integer, Double>>
{
  public static final int ADJUSTED_SALES = 0, SAMPLING = 1, WASTE = 2, ROYALTY_SALES = 3,
      PLATTERS_MINI_JIMMYS = 4, BOX_LUNCH = 5;
  private static final long serialVersionUID = 1L;
  private int index;
  private ArrayList<String> managerDeposits = new ArrayList<String>();

  public WSRMap(File file)
  {
    try
    {
      Scanner scanner = new Scanner(file);
      while (scanner.hasNext())
      {
        String[] tokens = scanner.nextLine().split(",");
        if (tokens[0].equals("Sales Item"))
        {
          // Item name
          String name = tokens[45];
          if (name.equals("Shift Manager - Name"))
          {
            for(int ii = 48; ii < 62; ii++)
            {
              managerDeposits.add(tokens[ii]);
            }
          }
          else
          {
            put(name, new HashMap<Integer, Double>());
            // 46 Summary
            index = 46;
            String test = removeQuotes(tokens);
            get(name).put(-1, Double.parseDouble(test));
            index++;
            // Next Each
            get(name).put(0, Double.parseDouble(removeQuotes(tokens)));
            index++;
            // Next Shifts
            for (int ii = 1; ii < 15; ii++)
            {
              get(name).put(ii, Double.parseDouble(removeQuotes(tokens)));
              index++;
            }
          }
        }
      }
      scanner.close();
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
      ErrorHandler.addError(e);
    }
  }

  /**
   * Checks for quotes and removes
   * 
   * @param s
   * @param tokens
   * @return
   */
  private String removeQuotes(String[] tokens)
  {
    String s = tokens[index];
    if (s.length() > 0)
    {
      if (s.charAt(0) == '"')
      {
        index++;
        s += tokens[index];
        s = s.substring(1, s.indexOf("\"", 1));
      }
    }
    return s;
  }

  public double getDataForShift(String name, int shift)
  {
    return get(name).get(shift);
  }

  public double getEachForItem(String name)
  {
    return get(name).get(0);
  }

  public double getSummaryForItem(String name)
  {
    return get(name).get(-1);
  }
  
  public ArrayList<String> getManagerDepositSignatures()
  {
    return managerDeposits;
  }
}
