package util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;

import app.AppDirector;
import lineitems.CateringTransaction;
import lineitems.InventoryItem;
import lineitems.UPKWeek;
import readers.AMPhoneAuditReader;
import readers.AttendanceReader;
import readers.CateringTransactionReader;
import readers.HourlySalesReader;
import readers.ItemUsageAnalysisReader;
import readers.TrendSheetReader;
import readers.UPKReader;
import readers.WSRMap;
import readers.WeeklySummaryReader;

public class ReportFinder
{
  String directory;

  public ReportFinder(String directory)
  {
    this.directory = directory;
  }

  public void uploadAttendanceReportToDataHub()
  {
    ArrayList<DupFile> fs = findLatestDuplicates(
        getAllCSVFilesThatStartWith("AttendanceWagesNoBreaks_JJ"), 1);
    AppDirector.reportsUsed += String.format("Attendance Report\n\t%s\n",
        fs.get(0).getFile().getName());
    AppDirector.dataHub
        .uploadAttendanceShifts(new AttendanceReader(fs.get(0).getFile()).getShifts());
  }

  public void uploadAreaManagerPhoneAuditToDataHub()
  {
    ArrayList<DupFile> fs = findLatestDuplicates(
        getAllCSVFilesThatStartWith("Area Manager Phone Audit Report"), 1);
    AppDirector.reportsUsed += String.format("Area Manager Phone Audit Report\n\t%s\n",
        fs.get(0).getFile().getName());
    AppDirector.dataHub
        .uploadAreaManagerPhoneAudit(new AMPhoneAuditReader(fs.get(0).getFile()));
  }

  /**
   * 0-3 last four weeks 4 last year week containing today's date
   */
  public void uploadWSRToDataHub()
  {
    ArrayList<DupFile> fs = findLatestDuplicates(getAllCSVFilesThatStartWith("WeeklySales"), 5);
    AppDirector.reportsUsed += "Weekly Sales Report\n";
    AppDirector.reportsUsed += "\tThis Year\n";
    for (int ii = 0; ii < 4; ii++)
    {
      AppDirector.reportsUsed += "\t\t" + fs.get(ii).getFile().getName() + "\n";
      AppDirector.dataHub.addWSRMapForProjections(new WSRMap(fs.get(ii).getFile()), ii + 1);
    }
    AppDirector.reportsUsed += "\tLast Year\n\t\t" + fs.get(4).getFile().getName() + "\n";
    AppDirector.dataHub.setLastYearWSR(new WSRMap(fs.get(4).getFile()));
  }

  public void uploadUPKToDataHub()
  {
    ArrayList<DupFile> fs = findLatestDuplicates(
        getAllCSVFilesThatStartWith("UPK Expected Usage Report"), 6);
    ArrayList<UPKWeek> past6UPKWeeks = new ArrayList<UPKWeek>();
    AppDirector.reportsUsed += "UPK Expected Usage Report\n";
    for (int ii = 0; ii < fs.size(); ii++)
    {
      AppDirector.reportsUsed += "\t" + fs.get(ii).getFile().getName() + "\n";
      UPKReader ur = new UPKReader(fs.get(ii).getFile());
      UPKWeek wk = ur.getWeek();
      past6UPKWeeks.add(wk);
    }
    AppDirector.dataHub.setPast6UPKWeeks(past6UPKWeeks);
  }

  public void uploadCateringTransactionsToDataHub()
  {
    ArrayList<DupFile> fs = findLatestDuplicates(
        getAllCSVFilesThatStartWith("Catering Transaction Summary"), 4);
    ArrayList<ArrayList<CateringTransaction>> last4DaysCatering = new ArrayList<ArrayList<CateringTransaction>>();
    AppDirector.reportsUsed += "Catering Transaction Summary\n";
    for (int ii = 0; ii < fs.size(); ii++)
    {
      AppDirector.reportsUsed += "\t" + fs.get(ii).getFile().getName() + "\n";
      last4DaysCatering.add(new CateringTransactionReader(fs.get(ii).getFile()).getTrans());
    }
    AppDirector.dataHub.setPast4DaysCatering(last4DaysCatering);
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
          latestFiles.subList(latestFiles.size() - (numFiles), latestFiles.size()));
    else
      return latestFiles;
  }

  private ArrayList<TimeFile> findLatestItemUsageDuplicates(File[] allFiles, int numFiles)
  {
    ArrayList<TimeFile> latestFiles = new ArrayList<TimeFile>();

    for (File f : allFiles)
    {
      try
      {
        latestFiles.add(new TimeFile(f));
      }
      catch (Exception pe)
      {
        System.out.println("Thrown");
        break;
      }
    }
    Collections.sort(latestFiles);
    if (latestFiles.size() >= numFiles)
      return new ArrayList<TimeFile>(
          latestFiles.subList(latestFiles.size() - (numFiles), latestFiles.size()));
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

  public void uploadSpecialItems()
  {
    boolean needDups = false;
    ArrayList<TimeFile> tfs = findLatestItemUsageDuplicates(
        getAllCSVFilesThatStartWith("MxItemUsageAnalysisReport"),
        AppDirector.dataHub.getInventoryItemNames().size());
    System.out.println(tfs.size() + "-----------------");
    ArrayList<DupFile> fs = null;
    if (tfs.size() < AppDirector.dataHub.getInventoryItemNames().size())
    {
      needDups = true;
      fs = findLatestDuplicates(getAllCSVFilesThatStartWith("MxItemUsageAnalysisReport"),
          AppDirector.dataHub.getInventoryItemNames().size() - tfs.size());
    }
    ArrayList<InventoryItem> items = new ArrayList<InventoryItem>();
    AppDirector.reportsUsed += "Item Usage Reports\n";
    for (TimeFile tf : tfs)
    {
      AppDirector.reportsUsed += "\t" + tf.getFile().getName() + "\n";
      items.add(new ItemUsageAnalysisReader(tf.getFile()).getItemLine());
    }
    if (needDups && fs != null)
    {
      for (DupFile df : fs)
      {
        AppDirector.reportsUsed += "\t" + df.getFile().getName() + "\n";
        items.add(new ItemUsageAnalysisReader(df.getFile()).getItemLine());
      }
    }
    Collections.sort(items);
    AppDirector.dataHub.setInventoryItems(items);
  }

  public void uploadWeeklySummaryToDataHub()
  {
    ArrayList<DupFile> fs = findLatestDuplicates(getAllCSVFilesThatStartWith("WeeklySummaryReport"),
        1);
    AppDirector.dataHub
        .setCurrentWeekSummary(new WeeklySummaryReader(fs.get(0).getFile()).getItem());
  }

  public void uploadLastHourlyDay(int year, int month, int day)
  {
    ArrayList<DupFile> fs = findLatestDuplicates(getAllCSVFilesThatStartWith("Hourly Sales Report"),
        1);
    AppDirector.dataHub.addHourlySalesDay(
        new HourlySalesReader(fs.get(0).getFile()).getHourlySalesDay(), year, month, day);
  }

  public void uploadLastXHourlyDays(ArrayList<String> downloadQueue)
  {
    ArrayList<DupFile> fs = findLatestDuplicates(getAllCSVFilesThatStartWith("Hourly Sales Report"),
        downloadQueue.size());
    for (int ii = 0; ii < fs.size(); ii++)
    {
      System.out.println(downloadQueue.get(ii));
      int year = Integer.parseInt(downloadQueue.get(ii)
          .substring(downloadQueue.get(ii).length() - 4, downloadQueue.get(ii).length()));
      int month = Integer.parseInt(downloadQueue.get(ii).substring(0, 2));
      int day = Integer.parseInt(downloadQueue.get(ii).substring(3, 5));
      AppDirector.dataHub.addHourlySalesDay(
          new HourlySalesReader(fs.get(ii).getFile()).getHourlySalesDay(), year, month, day);
    }
  }

  public void uploadLastTrendSheet(Integer year)
  {
    ArrayList<DupFile> fs = findLatestDuplicates(getAllCSVFilesThatStartWith("Trend Sheet"), 1);
    AppDirector.dataHub.setTrendSheet(year, new TrendSheetReader(fs.get(0).getFile()));
  }

}
