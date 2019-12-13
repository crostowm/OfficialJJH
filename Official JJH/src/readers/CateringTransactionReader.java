package readers;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Scanner;

import error_handling.ErrorHandler;
import util.CateringTransaction;

public class CateringTransactionReader
{
  private ArrayList<CateringTransaction> trans = new ArrayList<CateringTransaction>();

  public CateringTransactionReader(File file)
  {
    try
    {
      Scanner scanner = new Scanner(file);
      while (scanner.hasNext())
      {
        String[] tokens = scanner.nextLine().split(",");
        if (tokens[0].equals("System Trans'n ID") && !tokens[1].equals("0"))
        {
          int systemID = Integer.parseInt(tokens[1]);
          int posID = Integer.parseInt(tokens[3]);
          SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
          GregorianCalendar time = new GregorianCalendar();
          time.setTime(sdf.parse(tokens[5]));
          int clerkID = Integer.parseInt(tokens[7].substring(1));
          String clerkName = tokens[8].substring(0, tokens[8].length() - 1);
          double grossAmount = Double.parseDouble(tokens[11].substring(1));
          double taxAmount = Double.parseDouble(tokens[14].substring(1));
          double royalty = Double.parseDouble(tokens[17].substring(1));
          trans.add(new CateringTransaction(systemID, posID, clerkID, clerkName, time, grossAmount,
              taxAmount, royalty));
        }
      }
      scanner.close();
    }
    catch (Exception e)
    {
      ErrorHandler.addError(e);
    }

  }

  public ArrayList<CateringTransaction> getTrans()
  {
    return trans;
  }
}
