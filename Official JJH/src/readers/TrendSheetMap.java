package readers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import error_handling.ErrorHandler;
import javafx.collections.FXCollections;

/**
 * @author crost Key1: One of the static Strings below Key2: Week Number Object: Value
 */
public class TrendSheetMap implements Serializable
{
  private static final long serialVersionUID = -9008251803784269667L;
  public static String LY_ROYALTY = "Last Year Royalty", CY_ROYALTY = "Current Year Royalty",
      COMPS = "Comps", CY_LABORP = "Current Year Labor %", CY_LABOR$ = "Current Year Labor $",
      LY_LABORP = "Last Year Labor %", CASH_OVER_UNDER = "Cash Over/Under", COGS$ = "COGs $",
      COGSP = "COGS %", BREADP = "Bread %", FOODP = "Food %", SIDESP = "Sides %",
      PAPERP = "Paper %", PRODUCEP = "Produce %", BEVERAGEP = "Beverage %",
      CATERINGP = "Catering %";
  private HashMap<String, HashMap<Integer, Double>> weeklyMap = new HashMap<String, HashMap<Integer, Double>>();
  private HashMap<String, HashMap<Integer, Double>> periodMap = new HashMap<String, HashMap<Integer, Double>>();
  private String[] tokens;
  private int index;
  private int week = 0;
  private int period = 1;

  public TrendSheetMap(File file)
  {
    try
    {
      Scanner scanner = new Scanner(file);
      while (scanner.hasNext())
      {
        tokens = scanner.nextLine().split(",");
        if (tokens[0].startsWith("\"Store"))
        {
          week++;
          index = 21;
          insertValueIntoWeeklyMap(CY_ROYALTY);
          insertValueIntoWeeklyMap(LY_ROYALTY);
          insertValueIntoWeeklyMap(COMPS);
          insertValueIntoWeeklyMap(CY_LABOR$);
          insertValueIntoWeeklyMap(CY_LABORP);
          insertValueIntoWeeklyMap(LY_LABORP);
          insertValueIntoWeeklyMap(CASH_OVER_UNDER);
          insertValueIntoWeeklyMap(COGS$);
          insertValueIntoWeeklyMap(COGSP);
          insertValueIntoWeeklyMap(BREADP);
          insertValueIntoWeeklyMap(FOODP);
          insertValueIntoWeeklyMap(SIDESP);
          insertValueIntoWeeklyMap(PAPERP);
          insertValueIntoWeeklyMap(PRODUCEP);
          insertValueIntoWeeklyMap(BEVERAGEP);
          insertValueIntoWeeklyMap(CATERINGP);
          index++;
          period = Integer.parseInt((tokens[3].split(" "))[1]);
          insertValueIntoPeriodMap(CY_ROYALTY);
          insertValueIntoPeriodMap(LY_ROYALTY);
          insertValueIntoPeriodMap(COMPS);
          insertValueIntoPeriodMap(CY_LABOR$);
          insertValueIntoPeriodMap(COGS$);
          insertValueIntoPeriodMap(COGSP);
          insertValueIntoPeriodMap(BREADP);
          insertValueIntoPeriodMap(FOODP);
          insertValueIntoPeriodMap(SIDESP);
          insertValueIntoPeriodMap(PAPERP);
          insertValueIntoPeriodMap(PRODUCEP);
          insertValueIntoPeriodMap(BEVERAGEP);
          insertValueIntoPeriodMap(CATERINGP);
        }
      }
      scanner.close();
    }
    catch (FileNotFoundException fnf)
    {
      fnf.printStackTrace();
      ErrorHandler.addError(fnf);
    }
  }

  private void insertValueIntoWeeklyMap(String category)
  {
    String s = tokens[index];
    s = removeQuotes(s);
    if (weeklyMap.get(category) == null)
      weeklyMap.put(category, new HashMap<Integer, Double>());
    weeklyMap.get(category).put(week, Double.parseDouble(s));
    index++;
  }

  private void insertValueIntoPeriodMap(String category)
  {
    String s = tokens[index];
    s = removeQuotes(s);
    if (periodMap.get(category) == null)
      periodMap.put(category, new HashMap<Integer, Double>());
    periodMap.get(category).put(period, Double.parseDouble(s));
    index++;
  }

  private String removeQuotes(String s)
  {
    if (s.length() > 0)
    {
      if (s.charAt(0) == '"')
      {
        index++;
        s += tokens[index];
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
    return s;
  }

  public ArrayList<String> getWeeklyItems()
  {
    // TODO split into for week and for period
    return new ArrayList<String>(FXCollections.observableArrayList(LY_ROYALTY, CY_ROYALTY, COMPS,
        CY_LABORP, CY_LABOR$, LY_LABORP, CASH_OVER_UNDER, COGS$, COGSP, BREADP, FOODP, SIDESP,
        PAPERP, PRODUCEP, BEVERAGEP, CATERINGP));
  }

  public ArrayList<String> getPeriodItems()
  {
    // TODO split into for week and for period
    return new ArrayList<String>(FXCollections.observableArrayList(LY_ROYALTY, CY_ROYALTY, COMPS,
        CY_LABOR$, COGS$, COGSP, BREADP, FOODP, SIDESP, PAPERP, PRODUCEP, BEVERAGEP, CATERINGP));
  }

  public Double getDataForCategoryForWeek(String cat, int week)
  {
    return weeklyMap.get(cat).get(week);
  }

  public double getDataForCategoryForPeriod(String cat, int period)
  {
    System.out.println(cat + " " + period);
    return periodMap.get(cat).get(period);
  }
}
