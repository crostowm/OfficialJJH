package util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import app.MainApplication;
import readers.UPKMap;
import readers.WSRMap;

public class ReportFinder
{
  String directory;

  public ReportFinder(String directory)
  {
    this.directory = directory;
  }

  public void uploadWSRToDataHub()
  {
    ArrayList<File> fs = findLatestDuplicates(getAllWSRFiles(directory), 4);
    for (int ii = 0; ii < 4; ii++)
    {
      MainApplication.dataHub.addWSRMapForProjections(new WSRMap(fs.get(ii)), ii + 1);
    }
  }
  
  public void uploadUPKToDataHub()
  {
    ArrayList<File> fs = findLatestDuplicates(getAllUPKFiles(directory), 1);
    MainApplication.dataHub.setCurrentUPKMap(new UPKMap(fs.get(fs.size()-1)));
  }
  

  /**
   * @param allFiles
   * @param i
   * @return Highest index = newest
   */
  private ArrayList<File> findLatestDuplicates(File[] allFiles, int i)
  {
    ArrayList<File> latestFiles = new ArrayList<File>();
    for (File f : allFiles)
    {
      int highestIndex = -1;
      if (latestFiles.size() == 0)
        latestFiles.add(f);
      else
      {
        for (int ii = 0; ii < latestFiles.size(); ii++)
        {
          if (getDupVal(f) > getDupVal(latestFiles.get(ii)))
          {
            if (ii > highestIndex)
              highestIndex = ii;
          }
        }
        if (latestFiles.size() < 4 && highestIndex == -1)
        {
          latestFiles.add(0, f);
        }
        else if (highestIndex >= 0)
        {
          latestFiles.add(highestIndex, f);
          if (latestFiles.size() > 4)
          {
            latestFiles.remove(0);
          }
        }
      }
    }
    return latestFiles;
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

  private File[] getAllUPKFiles(String directory2)
  {
    File f = new File(directory);
    return f.listFiles(new FilenameFilter()
    {
      public boolean accept(File dir, String name)
      {
        return name.startsWith("UPK Expected Usage Report") && name.endsWith("csv");
      }
    });
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
