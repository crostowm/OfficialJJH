package util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import app.MainApplication;
import readers.WSRMap;

public class ReportFinder
{
  public ReportFinder(String directory, boolean grabWSR)
  {
    if (grabWSR)
    {
      ArrayList<File> fs = findLatestDuplicates(getAllWSRFiles(directory), 4);
      for (int ii = 0; ii < 4; ii++)
      {
        MainApplication.dataHub.addWSRMapForProjections(new WSRMap(fs.get(ii)), ii + 1);
      }
    }
  }

  private ArrayList<File> findLatestDuplicates(File[] allWSRFiles, int i)
  {
    ArrayList<File> wsrFiles = new ArrayList<File>();
    for (File f : allWSRFiles)
    {
      System.out.println("Run");
      int highestIndex = -1;
      if (wsrFiles.size() == 0)
        wsrFiles.add(f);
      else
      {
        for (int ii = 0; ii < wsrFiles.size(); ii++)
        {
          System.out.println("Next");
          if (getDupVal(f) > getDupVal(wsrFiles.get(ii)))
          {
            if (ii > highestIndex)
              highestIndex = ii;
          }
        }
        if (wsrFiles.size() < 4 && highestIndex == -1)
        {
          wsrFiles.add(0, f);
        }
        else if (highestIndex >= 0)
        {
          wsrFiles.add(highestIndex, f);
          if (wsrFiles.size() > 4)
          {
            wsrFiles.remove(0);
          }
        }
      }
    }
    return wsrFiles;
  }

  private int getDupVal(File f)
  {
    // TODO Auto-generated method stub
    String name = f.getName();
    if (name.endsWith(").csv"))
    {
      return Integer.parseInt(name.substring(name.lastIndexOf('(') + 1, name.lastIndexOf(')')));
    }
    return 0;
  }

  public File[] getAllWSRFiles(String directory)
  {
    File f = new File(directory);
    return f.listFiles(new FilenameFilter()
    {
      public boolean accept(File dir, String name)
      {
        return name.startsWith("WeeklySalesRS08") && name.endsWith("csv");
      }
    });
  }
}
