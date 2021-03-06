package util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import app.AppDirector;

public class JimmyCalendarUtil
{
  public static int getShiftNumber(GregorianCalendar calendar)
  {
    int dow = calendar.get(Calendar.DAY_OF_WEEK);
    if (dow > 3)
      dow = dow - 3;
    else
      dow = dow + 4;
    if (calendar.get(Calendar.HOUR_OF_DAY) >= AppDirector.dataHub.getSettings().getSetting(Setting.STORESC_TIME))
      return dow * 2;
    else
      return dow * 2 - 1;
  }

  public static boolean isInCurrentWeek(GregorianCalendar date)
  {
    return getWeekNumber(date) == getWeekNumber(new GregorianCalendar());
  }

  private static int getDayOfStartOfFirstWeek(int year)
  {
    GregorianCalendar temp = new GregorianCalendar();
    temp.set(Calendar.YEAR, year);
    for (int ii = 1; ii < 8; ii++)
    {
      temp.set(Calendar.DAY_OF_YEAR, ii);
      if (temp.get(Calendar.DAY_OF_WEEK) == 4)
        return ii;
    }
    return -1;
  }

  public static int getWeekNumber(GregorianCalendar cal)
  {
    int week = 1;
    int firstWedOfYear = getDayOfStartOfFirstWeek(cal.get(GregorianCalendar.YEAR));
    for (int ii = firstWedOfYear; ii < 366; ii += 7)
    {
      if (getDayOfYear(cal) >= ii
          && getDayOfYear(cal) < ii + 7)
        return week;
      week++;
    }
    return -1;
  }

  private static int getDayOfYear(GregorianCalendar cal)
  {
    GregorianCalendar index = new GregorianCalendar(cal.get(GregorianCalendar.YEAR), 0, 0);
    int day = 1;
    while (cal.get(GregorianCalendar.MONTH) != index.get(GregorianCalendar.MONTH)
        || cal.get(GregorianCalendar.DAY_OF_MONTH) != index.get(GregorianCalendar.DAY_OF_MONTH))
    {
      day++;
      index.add(GregorianCalendar.DAY_OF_YEAR, 1);
      if (day > 366)
        return -1;
    }
    return day;
  }

  public static int convertTo24Hr(int value, String ampm)
  {
    if (value != 12)
    {
      return value + (ampm.equals("PM") ? 12 : 0);
    }
    else
    {
      return ampm.equals("PM") ? 12 : 24;
    }
  }

  public static int getNextAMShift(int currentShift)
  {
    int nextS = currentShift + (currentShift % 2 == 0 ? 1 : 2);
    return nextS >= 14 ? nextS - 14 : nextS;
  }

  public static int convertToShiftNumber(int i)
  {
    return i > 14 ? i - 14 : i;
  }

  public static String convertTo12Hour(int currentHour)
  {
    if (currentHour == 0)
      return "12am";
    else if (currentHour == 12)
      return "12pm";
    else if (currentHour > 23)
      return convertTo12Hour(currentHour - 24);
    else if (currentHour > 12)
      return (currentHour - 12) + "pm";
    return currentHour + "am";
  }

  public static boolean isToday(GregorianCalendar time)
  {
    return time.get(Calendar.DAY_OF_YEAR) == (new GregorianCalendar()).get(Calendar.DAY_OF_YEAR);
  }

  public static int getNextAMShift()
  {
    return getNextAMShift(getShiftNumber(new GregorianCalendar()));
  }

  public static int getPeriodNumber(int weekNumber)
  {
    int pNumber = (weekNumber / 4);
    if (weekNumber % 4 != 0)
      pNumber++;
    return pNumber;
  }

  public static int getPeriodNumber()
  {
    return getPeriodNumber(getWeekNumber(new GregorianCalendar()));
  }

  public static ArrayList<Integer> getWeekNumbersCompletedInPeriod()
  {
    ArrayList<Integer> weeks = new ArrayList<Integer>();
    int week = getCurrentWeekNumber() - 1;
    int period = getPeriodNumber(week);
    while (getPeriodNumber(week) == period)
    {
      weeks.add(week);
      week--;
    }
    return weeks;
  }

  public static int getCurrentWeekNumber()
  {
    return getWeekNumber(new GregorianCalendar());
  }

  public static int getAMShiftFor(String value)
  {
    switch (value)
    {
      case "Sunday":
        return 9;
      case "Monday":
        return 11;
      case "Tuesday":
        return 13;
      case "Wednesday":
        return 1;
      case "Thursday":
        return 3;
      case "Friday":
        return 5;
      case "Saturday":
        return 7;
    }
    System.out.println("Did not recognize shift day in jimmycalendarutil");
    return 1;
  }

  public static int getCurrentShift()
  {
    GregorianCalendar calendar = new GregorianCalendar();
    return getShiftNumber(calendar);
  }

  public static int getTodaysAMShift()
  {
    return getCurrentShift() % 2 == 0 ? getCurrentShift() - 1 : getCurrentShift();
  }

  public static int getTodaysPMShift()
  {
    return getCurrentShift() % 2 == 0 ? getCurrentShift() : getCurrentShift() + 1;
  }

  public static int normalizeWeekIndex(int week)
  {
    if (week <= 0)
    {
      return 51 + week;
    }
    else
      return week;
  }

  public static int getCurrentWeek()
  {
    return getWeekNumber(new GregorianCalendar());
  }

  public static int normalizeHour(int currentHour)
  {
    return currentHour > 24 ? currentHour - 24 : currentHour;
  }

  public static int getCurrentYear()
  {
    GregorianCalendar gc = new GregorianCalendar();
    return gc.get(Calendar.YEAR);
  }

  public static int getNumWeeksInYear(int year)
  {
    GregorianCalendar gc = new GregorianCalendar();
    gc.set(Calendar.YEAR, year);
    gc.set(Calendar.DAY_OF_YEAR, 365);
    return getWeekNumber(gc);
  }

  public static int[][] getLastXWeeksInYearWeekPairs(int x)
  {
    int[][] pairs = new int[x][2];
    int currentWeek = JimmyCalendarUtil.getCurrentWeek();
    int currentYear = JimmyCalendarUtil.getCurrentYear();
    for (int ii = 0; ii < x; ii++)
    {
      int ww = currentWeek - x + ii;
      int year;
      int week;
      if (ww < 1)
      {
        year = currentYear - 1;
        week = JimmyCalendarUtil.getNumWeeksInYear(year) - ww;
      }
      else
      {
        year = currentYear;
        week = ww;
      }
      pairs[ii][0] = year;
      pairs[ii][1] = week;
    }
    return pairs;
  }
}
