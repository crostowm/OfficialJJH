package readers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

public class WSRMap extends HashMap<Integer, HashMap<Integer, Double>>
{
  public static final int ADJUSTED_SALES = 0, SAMPLING = 1, WASTE = 2, ROYALTY_SALES = 3,
      PLATTERS_MINI_JIMMYS = 4, BOX_LUNCH = 5;
  private static final long serialVersionUID = 1L;
  private Scanner scanner;

  public WSRMap(String fileName)
  {
    try
    {
      scanner = new Scanner(new File(fileName));
      scanner.useDelimiter(
          "Sales Item,Summary,#EA,Wed-1,Wed-2,Thur-3,Thur-4,Fri-5,Fri-6,Sat-7,Sat-8,Sun-9,Sun-10,Mon-11,Mon-12,Tues-13,Tues-14");
      while (scanner.hasNext())
      {
        StringTokenizer st = new StringTokenizer(scanner.next(), ",");
        while (st.hasMoreTokens())
        {
          String s = st.nextToken();
          if (s.length() > 0)
          {
            if (s.charAt(0) == '"')
              s += st.nextToken();
            switch (s)
            {
              case "= Adjusted Sales":
                loadNumbersForCategory(ADJUSTED_SALES, st);
                System.out.println("Loaded Adjusted Sales");
                break;
              case "- Sampling":
                loadNumbersForCategory(SAMPLING, st);
                System.out.println("Loaded Sampling");
                break;
              case "- Waste":
                loadNumbersForCategory(WASTE, st);
                System.out.println("Loaded Waste");
                break;
              case "= Royalty Sales":
                loadNumbersForCategory(ROYALTY_SALES, st);
                System.out.println("Loaded Royalty Sales");
                break;
              case "Platters / Mini Jimmys":
                loadNumbersForCategory(PLATTERS_MINI_JIMMYS, st);
                System.out.println("Loaded Platters/Mini Jimmys");
                break;
              case "Box Lunch":
                loadNumbersForCategory(BOX_LUNCH, st);
                System.out.println("Loaded Box Lunch");
                break;
              default:
                break;
            }
          }
        }
      }
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * @param cat Static int for type of data
   * @param st String Tokenizer
   */
  private void loadNumbersForCategory(int cat, StringTokenizer st)
  {
    put(cat, new HashMap<Integer, Double>());
    String s = st.nextToken();
    s = removeQuotes(s, st);
    get(cat).put(0, Double.parseDouble(s));
    st.nextToken();
    for (int ii = 0; ii < 14; ii++)
    {
      s = st.nextToken();
      s = removeQuotes(s, st);
      get(cat).put(ii + 1, Double.parseDouble(s));
    }
  }

  /**
   * Checks for quotes and removes
   * @param s
   * @param st
   * @return
   */
  private String removeQuotes(String s, StringTokenizer st)
  {
    if (s.length() > 0)
    {
      if (s.charAt(0) == '"')
      {
        s += st.nextToken();
        s = s.substring(1, s.indexOf("\"", 1));
      }
    }
    System.out.println(s);
    return s;
  }

  public double getDataForShift(int category, int shift)
  {
    return get(category).get(shift);
  }
}
