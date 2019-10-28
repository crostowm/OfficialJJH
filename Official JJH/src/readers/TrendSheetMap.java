package readers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * @author crost Key1: One of the static ints below Key2: Week Number Object: Value
 */
public class TrendSheetMap extends HashMap<Integer, HashMap<Integer, Double>>
{
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  public static int LY_ROYALTY = 1, CY_ROYALTY = 2, COMPS = 3, CY_LABORP = 4, CY_LABOR$ = 5,
      LY_LABORP = 6, COGS$ = 7, COGSP = 8, BREADP = 9, FOODP = 10, SIDESP = 11, PAPERP = 12,
      PRODUCEP = 13, BEVERAGEP = 14, CATERINGP = 15;
  private Scanner scanner;

  public TrendSheetMap(File file)
  {
    for (int ii = 1; ii < 16; ii++)
    {
      put(ii, new HashMap<Integer, Double>());
    }
    try
    {
      scanner = new Scanner(file);

      //TODO Fix Year for reusability
      scanner.useDelimiter(
          "Period 1,2019 Current Year Royalty Sales,2018  Last Year Royalty Sales,Variance %,2019 Labor $,2019 Labor %,2018  Labor %,2019 Cash Over / Under,COGS $,COGS%,Bread %,Food %,Sides %,Paper %,Produce %,Beverage %,Catering %,Week");
      // scanner.next();
      int week = 0;
      while (scanner.hasNext())
      {
        week++;
        StringTokenizer st = new StringTokenizer(scanner.next(), ",");
        while (st.hasMoreTokens())
        {
          String s = st.nextToken();
          if (s.length() > 3 && s.substring(0, 4).equals("Week"))
          {
            insertValueIntoMap(week, CY_ROYALTY, st);
            insertValueIntoMap(week, LY_ROYALTY, st);
            insertValueIntoMap(week, COMPS, st);
            insertValueIntoMap(week, CY_LABOR$, st);
            insertValueIntoMap(week, CY_LABORP, st);
            insertValueIntoMap(week, LY_LABORP, st);
            st.nextToken();
            insertValueIntoMap(week, COGS$, st);
            insertValueIntoMap(week, COGSP, st);
            insertValueIntoMap(week, BREADP, st);
            insertValueIntoMap(week, FOODP, st);
            insertValueIntoMap(week, SIDESP, st);
            insertValueIntoMap(week, PAPERP, st);
            insertValueIntoMap(week, PRODUCEP, st);
            insertValueIntoMap(week, BEVERAGEP, st);
            insertValueIntoMap(week, CATERINGP, st);
          }
        }
      }
    }
    catch (FileNotFoundException fnf)
    {
      fnf.printStackTrace();
    }
  }

  private void insertValueIntoMap(int week, int category, StringTokenizer st)
  {
    String s = st.nextToken();
    s = removeQuotes(s, st);
    System.out.println(s);
    get(category).put(week, Double.parseDouble(s));
  }

  private String removeQuotes(String s, StringTokenizer st)
  {
    if (s.length() > 0)
    {
      if (s.charAt(0) == '"')
      {
        s += st.nextToken();
        s = s.substring(1, s.indexOf("\"", 1));
      }
      if (s.charAt(0) == '$')
      {
        s = s.substring(1);
      }
      if (s.charAt(s.length() - 1) == '%')
      {
        s = s.substring(0, s.length() - 1);
      }
    }
    System.out.println(s);
    return s;
  }
}

