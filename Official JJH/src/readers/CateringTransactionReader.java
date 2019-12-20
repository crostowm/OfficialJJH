package readers;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Scanner;

import error_handling.ErrorHandler;
import lineitems.CateringTransaction;
import util.ParseUtil;

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
        ArrayList<String> tokens = ParseUtil.getQuoteConsolidatedList(scanner.nextLine().split(","));
        if (tokens.get(0).equals("System Trans'n ID") && !tokens.get(1).equals("0"))
        {
          int systemID = Integer.parseInt(tokens.get(1));
          int posID = Integer.parseInt(tokens.get(3));
          SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
          GregorianCalendar time = new GregorianCalendar();
          time.setTime(sdf.parse(tokens.get(5)));
          int clerkID = Integer.parseInt(tokens.get(7).substring(1));
          String clerkName = tokens.get(8).substring(0, tokens.get(8).length() - 1);
          double grossAmount = Double.parseDouble(tokens.get(11).substring(1));
          double taxAmount = Double.parseDouble(tokens.get(14).substring(1));
          double royalty = Double.parseDouble(tokens.get(17).substring(1));
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
