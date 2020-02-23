package readers;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

import app.AppDirector;
import error_handling.ErrorHandler;
import lineitems.WeeklySalesLineItem;
import lineitems.WeeklySalesWeek;
import util.JimmyCalendarUtil;
import util.ParseUtil;

public class WSRReader
{
  public WSRReader(File file)
  {
    ArrayList<WeeklySalesLineItem> lineItems = new ArrayList<WeeklySalesLineItem>();
    ArrayList<String> cashDeposits = new ArrayList<String>();
    int weekNumber = 0;
    int year = 0;
    try
    {
      Scanner scanner = new Scanner(file);
      while (scanner.hasNext())
      {
        ArrayList<String> tokens = ParseUtil.getQuoteConsolidatedList(scanner.nextLine().split(","));
        
        if (tokens.get(0).equals("Sales Item"))
        {
          SimpleDateFormat sdf = new SimpleDateFormat("mm/DD/yyyy");
          GregorianCalendar gc = new GregorianCalendar();
          gc.setTime(sdf.parse(tokens.get(17)));
          year = gc.get(Calendar.YEAR);
          weekNumber = JimmyCalendarUtil.getWeekNumber(gc);
          // Item name
          String name = tokens.get(45);
          if (name.equals("Shift Manager - Name"))
          {
            for(int ii = 48; ii < 62; ii++)
            {
              if(ii >= tokens.size())
                cashDeposits.add("");
              else
                cashDeposits.add(tokens.get(ii));
            }
          }
          else
          {
            // 46 Summary
            double summary = Double.parseDouble(tokens.get(46));
            // Next Each
            double each = Double.parseDouble(tokens.get(47));
            // Next Shifts
            ArrayList<Double> shiftData = new ArrayList<Double>();
            for (int ii = 48; ii < 62; ii++)
            {
              shiftData.add(Double.parseDouble(tokens.get(ii)));
            }
            lineItems.add(new WeeklySalesLineItem(name, summary, each, shiftData));
          }
        }
      }
      AppDirector.dataHub.addWSRWeek(new WeeklySalesWeek(year, weekNumber, lineItems, cashDeposits));
      scanner.close();
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
      ErrorHandler.addError(e);
    }
    catch (ParseException e)
    {
      e.printStackTrace();
      ErrorHandler.addError(e);
    }
  }
}
