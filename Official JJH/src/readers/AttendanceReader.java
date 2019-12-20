package readers;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Scanner;

import error_handling.ErrorHandler;
import lineitems.AttendanceShift;
import util.ParseUtil;

public class AttendanceReader
{
  private ArrayList<AttendanceShift> shifts = new ArrayList<AttendanceShift>();

  public AttendanceReader(File file)
  {
    try
    {
      Scanner scanner = new Scanner(file);
      while (scanner.hasNext())
      {
        ArrayList<String> tokens = ParseUtil.getQuoteConsolidatedList(scanner.nextLine().split(","));
        if (tokens.get(0).equals("Employee #"))
        {
          boolean authorized = tokens.get(10).equals("Authorized Time");
          boolean empNumSet = !tokens.get(11).equals("Not Set");
          int empNum = empNumSet ? Integer.parseInt(tokens.get(11)) : -1;
          String lastName = tokens.get(12).substring(1);
          String firstName = tokens.get(13).substring(1, tokens.get(13).length() - 2);
          SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
          GregorianCalendar start = new GregorianCalendar();
          start.setTime(sdf.parse(tokens.get(14)));
          GregorianCalendar end = new GregorianCalendar();
          end.setTime(sdf.parse(tokens.get(15)));
          double payHours = Double.parseDouble(tokens.get(16));
          boolean adjusted = tokens.get(17).length() == 1;
          String position = tokens.get(18);
          double cumulativeHours = Double.parseDouble(tokens.get(19));
          double payRate = parseMoney(tokens.get(20));
          double estimatedWages = parseMoney(tokens.get(21));
          String adjustmentReason = tokens.get(22);
          shifts.add(new AttendanceShift(authorized, empNum, firstName, lastName, position, adjustmentReason, payHours,
              payRate, cumulativeHours, estimatedWages, start, end, adjusted));
        }
      }
      scanner.close();
    }
    catch (Exception e)
    {
      ErrorHandler.addError(e);
    }
  }

  private double parseMoney(String string)
  {
    return Double.parseDouble(string.substring(1));
  }

  public ArrayList<AttendanceShift> getShifts()
  {
    return shifts;
  }
}
