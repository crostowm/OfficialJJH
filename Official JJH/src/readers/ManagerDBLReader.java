package readers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import util.ManagerDBL;

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
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
  }
  public ArrayList<ManagerDBL> getDBLs()
  {
    ArrayList<ManagerDBL> dbls = new ArrayList<ManagerDBL>();
    for(String s: mgrDBLs)
    {
      dbls.add(new ManagerDBL(s));
    }
    return dbls;
  }
}
