package util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import app.MainApplication;
import readers.AMPhoneAuditMap;
import readers.HourlySalesMap;
import readers.UPKMap;
import readers.WSRMap;

public class ReportFinder
{
  String directory;

  public ReportFinder(String directory)
  {
    this.directory = directory;
  }

  public void uploadAreaManagerPhoneAuditToDataHub()
  {
    ArrayList<File> fs = findLatestDuplicates(getAllCSVFilesThatStartWith("Area Manager Phone Audit Report"), 1);
    MainApplication.dataHub.uploadAreaManagerPhoneAudit(new AMPhoneAuditMap(fs.get(0)));
  }
  
  public void uploadWSRToDataHub()
  {
    ArrayList<File> fs = findLatestDuplicates(getAllCSVFilesThatStartWith("WeeklySalesRS08"), 4);
    for (int ii = 0; ii < 4; ii++)
    {
      MainApplication.dataHub.addWSRMapForProjections(new WSRMap(fs.get(ii)), ii + 1);
    }
  }
  
  public void uploadUPKToDataHub()
  {
    ArrayList<File> fs = findLatestDuplicates(getAllCSVFilesThatStartWith("UPK Expected Usage Report"), 6);
    MainApplication.dataHub.setCurrentUPKMap(new UPKMap(fs.get(fs.size()-1)));
    ArrayList<UPKMap> past5UPKMaps = new ArrayList<UPKMap>();
    for(int ii = 0; ii < fs.size()-1; ii++)
    {
      past5UPKMaps.add(new UPKMap(fs.get(ii)));
    }
    MainApplication.dataHub.setPast5UPKMaps(past5UPKMaps);
  }
  
  public void uploadHourlySalesToDataHub()
  {
    ArrayList<File> fs = findLatestDuplicates(getAllCSVFilesThatStartWith("Hourly Sales Report"), 4);
    ArrayList<HourlySalesMap> past4HourlySales = new ArrayList<HourlySalesMap>();
    for(int ii = 0; ii < fs.size(); ii++)
    {
      past4HourlySales.add(new HourlySalesMap(fs.get(ii)));
    }
    MainApplication.dataHub.setPast4HourlySalesMaps(past4HourlySales);
  }

  /**
   * @param allFiles
   * @param numFiles
   * @return Highest index = newest
   */
  private ArrayList<File> findLatestDuplicates(File[] allFiles, int numFiles)
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
        if (latestFiles.size() < numFiles && highestIndex == -1)
        {
          latestFiles.add(0, f);
        }
        else if (highestIndex >= 0)
        {
          latestFiles.add(highestIndex, f);
          if (latestFiles.size() > numFiles)
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

  private File[] getAllCSVFilesThatStartWith(String sw)
  {
    File f = new File(directory);
    File[] files = f.listFiles(new FilenameFilter()
    {
      public boolean accept(File dir, String name)
      {
        return name.startsWith(sw) && name.endsWith("csv");
      }
    });
    return files;
  }

}
