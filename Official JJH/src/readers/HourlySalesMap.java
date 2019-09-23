package readers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class HourlySalesMap extends HashMap<Integer, HashMap<Integer, Double>>
{
  private static final long serialVersionUID = 8953713896638507995L;

  public HourlySalesMap(String fileName)
  {
    Scanner scanner;
    try
    {
      scanner = new Scanner(new File(fileName));
      while (scanner.hasNext())
      {
        System.out.println("New Line: " + scanner.nextLine());
      }
      scanner.close();
    }
    catch (FileNotFoundException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
