package readers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import error_handling.ErrorHandler;

public class InventoryItemNameReader
{
  ArrayList<String> items = new ArrayList<String>();
  public InventoryItemNameReader(File file)
  {
    try
    {
      Scanner scanner = new Scanner(file);
      while(scanner.hasNext())
      {
        items.add(scanner.nextLine());
      }
      scanner.close();
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
      ErrorHandler.addError(e);
    }
  }
  
  public ArrayList<String> getItems()
  {
    return items;
  }
}
