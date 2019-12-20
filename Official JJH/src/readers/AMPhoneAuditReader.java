package readers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import error_handling.ErrorHandler;
import lineitems.AMPhoneAuditItem;
import util.ParseUtil;

public class AMPhoneAuditReader
{
  private AMPhoneAuditItem item;
  
  public AMPhoneAuditReader(File file)
  {
    try
    {
      Scanner scan = new Scanner(file);
      while(scan.hasNext())
      {
        String line = scan.nextLine();
        if(line.startsWith("\""))
        {
          System.out.println(line);
          ArrayList<String> tokens = ParseUtil.getQuoteConsolidatedList(line.split(","));
          
          double salesAM = Double.parseDouble(tokens.get(2));
          double salesPM = Double.parseDouble(tokens.get(3));
          double depositAM = Double.parseDouble(ParseUtil.parse$(tokens.get(5)));
          double depositPM = Double.parseDouble(ParseUtil.parse$(tokens.get(6)));
          double cashOverUnderAM = Double.parseDouble(ParseUtil.parseParen(ParseUtil.parse$(tokens.get(8))));
          double cashOverUnderPM = Double.parseDouble(ParseUtil.parseParen(ParseUtil.parse$(tokens.get(9))));
          double laborAM = Double.parseDouble(ParseUtil.parsePerc(tokens.get(11)));
          double laborPM = Double.parseDouble(ParseUtil.parsePerc(tokens.get(12)));
          double laborWeek = Double.parseDouble(ParseUtil.parsePerc(tokens.get(14)));
          String[] last = tokens.get(15).split(" / ");
          double compPerc = Double.parseDouble(ParseUtil.parsePerc(last[0]));
          double compDollars = Double.parseDouble(ParseUtil.parse$(last[1]));
          
          item = new AMPhoneAuditItem(salesAM, salesPM, depositAM, depositPM, cashOverUnderAM, cashOverUnderPM, laborAM, laborPM, laborWeek, compPerc, compDollars);
        }
      }
      scan.close();
    }
    catch (FileNotFoundException e)
    {
      System.out.println("FNF AMPhoneAudit");
      ErrorHandler.addError(e);
    }
    catch (NumberFormatException nfe)
    {
      System.out.println("Error parsing AMPhoneAudit");
      nfe.printStackTrace();
      ErrorHandler.addError(nfe);
    }
  }

  public AMPhoneAuditItem getItem()
  {
    return item;
  }
}
