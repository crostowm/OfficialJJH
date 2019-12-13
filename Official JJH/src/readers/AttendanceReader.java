package readers;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Scanner;

import error_handling.ErrorHandler;
import util.AttendanceShift;

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
        String[] tokens = scanner.nextLine().split(",");
        if (tokens[0].equals("Employee #"))
        {
          boolean authorized = tokens[10].equals("Authorized Time");
          boolean empNumSet = !tokens[11].equals("Not Set");
          int empNum = empNumSet ? Integer.parseInt(tokens[11]) : -1;
          String lastName = tokens[12].substring(1);
          String firstName = tokens[13].substring(1, tokens[13].length() - 2);
          SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
          GregorianCalendar start = new GregorianCalendar();
          start.setTime(sdf.parse(tokens[14]));
          GregorianCalendar end = new GregorianCalendar();
          end.setTime(sdf.parse(tokens[15]));
          double payHours = Double.parseDouble(tokens[16]);
          boolean adjusted = tokens[17].length() == 1;
          String position = tokens[18];
          double cumulativeHours = Double.parseDouble(tokens[19]);
          double payRate = parseMoney(tokens[20]);
          double estimatedWages = parseMoney(tokens[21]);
          String adjustmentReason = tokens[22];
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
