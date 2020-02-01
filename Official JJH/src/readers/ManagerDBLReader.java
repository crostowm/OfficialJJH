package readers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import error_handling.ErrorHandler;
import util.CompletableTask;

public class ManagerDBLReader
{
  private ArrayList<String> mgrDBLs = new ArrayList<String>();
  public ManagerDBLReader(String filePath)
  {
    File file = new File(filePath);
    try
    {
      Scanner scan = new Scanner(file);
      while(scan.hasNext())
      {
        mgrDBLs.add(scan.nextLine());
      }
      scan.close();
    }
    catch (FileNotFoundException e)
    {
      ErrorHandler.addError(e);
      e.printStackTrace();
    }
    
  }
  public ArrayList<CompletableTask> getDBLs()
  {
    ArrayList<CompletableTask> dbls = new ArrayList<CompletableTask>();
    for(String s: mgrDBLs)
    {
      dbls.add(new CompletableTask(s));
    }
    return dbls;
  }
}
