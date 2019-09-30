package readers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class AMPhoneAuditMap extends HashMap<Integer, HashMap<Integer, Double>>
{
  private static final long serialVersionUID = 5127268723476633575L;
  public static final int SALES = 1, DEPOSITS = 2, OVER_UNDER = 3, LABOR = 4, AM = 5, PM = 6;
  
  private int index = 4;
  
  public AMPhoneAuditMap(String fileName)
  {
    for(int ii = 1; ii < 5; ii++)
    {
      put(ii, new HashMap<Integer, Double>());
    }
    try
    {
      Scanner scan = new Scanner(new File(fileName));
      while(scan.hasNext())
      {
        String line = scan.nextLine();
        if(line.startsWith("\""))
        {
          System.out.println(line);
          String[] tokens = line.split(",");
          
          String temp = tokens[index];
          get(SALES).put(AM, Double.parseDouble(parse$(parseQuotes(temp, tokens))));
          index++;
          
          temp = tokens[index];
          get(SALES).put(PM, Double.parseDouble(parse$(parseQuotes(temp, tokens))));
          index++;
          index++;
          
          temp = tokens[index];
          get(DEPOSITS).put(AM, Double.parseDouble(parse$(parseQuotes(temp, tokens))));
          index++;
          
          temp = tokens[index];
          get(DEPOSITS).put(PM, Double.parseDouble(parse$(parseQuotes(temp, tokens))));
          index++;
          index++;
          
          temp = tokens[index];
          get(OVER_UNDER).put(AM, Double.parseDouble(parse$(parseParen(temp))));
          index++;
          
          temp = tokens[index];
          get(OVER_UNDER).put(PM, Double.parseDouble(parse$(parseParen(temp))));
          index++;
          index++;
          
          temp = tokens[index];
          get(LABOR).put(AM, Double.parseDouble(parsePerc(temp)));
          index++;
          
          temp = tokens[index];
          get(LABOR).put(PM, Double.parseDouble(parsePerc(temp)));
          index++;
          index++;
        }
      }
      scan.close();
    }
    catch (FileNotFoundException e)
    {
      System.out.println("FNF AMPhoneAudit");
    }
    catch (NumberFormatException nfe)
    {
      System.out.println("Error parsing AMPhoneAudit");
      nfe.printStackTrace();
    }
  }

  private String parseQuotes(String temp, String[] tokens)
  {
    if(temp.startsWith("\""))
    {
      index++;
      temp = temp.substring(1) + tokens[index].substring(0, tokens[index].length()-1);
    }
    return temp;
  }
  
  private String parse$(String temp)
  {
    if(temp.startsWith("-$"))
      return "-" + temp.substring(2);
    return temp.substring(1);
  }
  
  private String parseParen(String temp)
  {
    if(temp.startsWith("("))
    {
      temp = "-" + temp.substring(1, temp.length()-1);
    }
    return temp;
  }
  
  private String parsePerc(String temp)
  {
    if(temp.endsWith("%"))
    {
      temp = temp.substring(0, temp.length()-1);
    }
    return temp;
  }
  
  public double getData(int cat, int ampm)
  {
    return get(cat).get(ampm);
  }
}
