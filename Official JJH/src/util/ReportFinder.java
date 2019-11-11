package util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;

import app.MainApplication;
import readers.AMPhoneAuditMap;
import readers.HourlySalesMap;
import readers.TrendSheetMap;
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
    ArrayList<DupFile> fs = findLatestDuplicates(
        getAllCSVFilesThatStartWith("Area Manager Phone Audit Report"), 1);
    MainApplication.dataHub.uploadAreaManagerPhoneAudit(new AMPhoneAuditMap(fs.get(0).getFile()));
  }

  public void uploadWSRToDataHub()
  {
    ArrayList<DupFile> fs = findLatestDuplicates(getAllCSVFilesThatStartWith("WeeklySalesRS08"), 4);
    for (int ii = 0; ii < 4; ii++)
    {
      MainApplication.dataHub.addWSRMapForProjections(new WSRMap(fs.get(ii).getFile()), ii + 1);
    }
  }

  public void uploadUPKToDataHub()
  {
    ArrayList<DupFile> fs = findLatestDuplicates(
        getAllCSVFilesThatStartWith("UPK Expected Usage Report"), 6);
    MainApplication.dataHub.setCurrentUPKMap(new UPKMap(fs.get(fs.size() - 1).getFile()));
    ArrayList<UPKMap> past5UPKMaps = new ArrayList<UPKMap>();
    for (int ii = 0; ii < fs.size() - 1; ii++)
    {
      past5UPKMaps.add(new UPKMap(fs.get(ii).getFile()));
    }
    MainApplication.dataHub.setPast5UPKMaps(past5UPKMaps);
  }

  public void uploadHourlySalesToDataHub()
  {
    ArrayList<DupFile> fs = findLatestDuplicates(getAllCSVFilesThatStartWith("Hourly Sales Report"),
        4);
    ArrayList<HourlySalesMap> past4HourlySales = new ArrayList<HourlySalesMap>();
    for (int ii = 0; ii < fs.size(); ii++)
    {
      past4HourlySales.add(new HourlySalesMap(fs.get(ii).getFile()));
    }
    MainApplication.dataHub.setPast4HourlySalesMaps(past4HourlySales);
  }

  /**
   * First download is previous year, second is current
   */
  public void uploadTrendSheetsToDataHub()
  {
    // TODO Auto-generated method stub
    ArrayList<DupFile> fs = findLatestDuplicates(getAllCSVFilesThatStartWith("Trend Sheet"), 2);
    if (fs.size() == 2)
    {
      MainApplication.dataHub.setLastYearTrendSheet(new TrendSheetMap(fs.get(0).getFile()));
      MainApplication.dataHub.setCurrentYearTrendSheet(new TrendSheetMap(fs.get(0).getFile()));
    }
  }

  /**
   * @param allFiles
   * @param numFiles
   * @return Highest index = newest
   */
  private ArrayList<DupFile> findLatestDuplicates(File[] allFiles, int numFiles)
  {
    ArrayList<DupFile> latestFiles = new ArrayList<DupFile>();

    for (File f : allFiles)
    {
      latestFiles.add(new DupFile(f));
    }
    Collections.sort(latestFiles);
    if (latestFiles.size() >= numFiles)
      return new ArrayList<DupFile>(
          latestFiles.subList(latestFiles.size() - (numFiles + 1), latestFiles.size() - 1));
    else
      return latestFiles;
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
