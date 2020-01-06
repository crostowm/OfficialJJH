package util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;

import app.MainApplication;
import lineitems.CateringTransaction;
import lineitems.InventoryItem;
import lineitems.UPKWeek;
import readers.AMPhoneAuditReader;
import readers.AttendanceReader;
import readers.CateringTransactionReader;
import readers.HourlySalesMap;
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
    MainApplication.reportsUsed += String.format("Attendance Report\n\t%s\n",
        fs.get(0).getFile().getName());
    MainApplication.dataHub
        .uploadAttendanceShifts(new AttendanceReader(fs.get(0).getFile()).getShifts());
  }

  public void uploadAreaManagerPhoneAuditToDataHub()
  {
    ArrayList<DupFile> fs = findLatestDuplicates(
        getAllCSVFilesThatStartWith("Area Manager Phone Audit Report"), 1);
    MainApplication.reportsUsed += String.format("Area Manager Phone Audit Report\n\t%s\n",
        fs.get(0).getFile().getName());
    MainApplication.dataHub
        .uploadAreaManagerPhoneAudit(new AMPhoneAuditReader(fs.get(0).getFile()));
  }

  /**
   * 0-3 last four weeks 4 last year week containing today's date
   */
  public void uploadWSRToDataHub()
  {
    ArrayList<DupFile> fs = findLatestDuplicates(getAllCSVFilesThatStartWith("WeeklySalesRS08"), 5);
    MainApplication.reportsUsed += "Weekly Sales Report\n";
    MainApplication.reportsUsed += "\tThis Year\n";
    for (int ii = 0; ii < 4; ii++)
    {
      MainApplication.reportsUsed += "\t\t" + fs.get(ii).getFile().getName() + "\n";
      MainApplication.dataHub.addWSRMapForProjections(new WSRMap(fs.get(ii).getFile()), ii + 1);
    }
    MainApplication.reportsUsed += "\tLast Year\n\t\t" + fs.get(4).getFile().getName() + "\n";
    MainApplication.dataHub.setLastYearWSR(new WSRMap(fs.get(4).getFile()));
  }

  public void uploadUPKToDataHub()
  {
    ArrayList<DupFile> fs = findLatestDuplicates(
        getAllCSVFilesThatStartWith("UPK Expected Usage Report"), 6);
    ArrayList<UPKWeek> past6UPKWeeks = new ArrayList<UPKWeek>();
    MainApplication.reportsUsed += "UPK Expected Usage Report\n";
    for (int ii = 0; ii < fs.size(); ii++)
    {
      MainApplication.reportsUsed += "\t" + fs.get(ii).getFile().getName() + "\n";
      UPKReader ur = new UPKReader(fs.get(ii).getFile());
      UPKWeek wk = ur.getWeek();
      past6UPKWeeks.add(wk);
    }
    MainApplication.dataHub.setPast6UPKWeeks(past6UPKWeeks);
  }

  public void uploadHourlySalesToDataHub()
  {
    ArrayList<DupFile> fs = findLatestDuplicates(getAllCSVFilesThatStartWith("Hourly Sales Report"),
        4);
    ArrayList<HourlySalesMap> past4HourlySales = new ArrayList<HourlySalesMap>();
    MainApplication.reportsUsed += "Hourly Sales Report\n";
    for (int ii = 0; ii < fs.size(); ii++)
    {
      MainApplication.reportsUsed += "\t" + fs.get(ii).getFile().getName() + "\n";
      past4HourlySales.add(new HourlySalesMap(fs.get(ii).getFile()));
    }
    MainApplication.dataHub.setPast4HourlySalesMaps(past4HourlySales);
  }

  public void uploadCateringTransactionsToDataHub()
  {
    ArrayList<DupFile> fs = findLatestDuplicates(
        getAllCSVFilesThatStartWith("Catering Transaction Summary"), 4);
    ArrayList<ArrayList<CateringTransaction>> last4DaysCatering = new ArrayList<ArrayList<CateringTransaction>>();
    MainApplication.reportsUsed += "Catering Transaction Summary\n";
    for (int ii = 0; ii < fs.size(); ii++)
    {
      MainApplication.reportsUsed += "\t" + fs.get(ii).getFile().getName() + "\n";
      last4DaysCatering.add(new CateringTransactionReader(fs.get(ii).getFile()).getTrans());
    }
    MainApplication.dataHub.setPast4DaysCatering(last4DaysCatering);
  }

  /**
   * First download is previous year, second is current
   */
  public void uploadTrendSheetsToDataHub()
  {
    ArrayList<DupFile> fs = findLatestDuplicates(getAllCSVFilesThatStartWith("Trend Sheet"), 2);
    MainApplication.reportsUsed += "Trend Sheet\n";
    if (fs.size() == 2)
    {
      MainApplication.reportsUsed += "\t" + fs.get(0).getFile().getName() + "\n";
      MainApplication.reportsUsed += "\t" + fs.get(1).getFile().getName() + "\n";
      MainApplication.dataHub.setLastYearTrendSheet(new TrendSheetReader(fs.get(0).getFile()));
      MainApplication.dataHub.setCurrentYearTrendSheet(new TrendSheetReader(fs.get(1).getFile()));
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
          latestFiles.subList(latestFiles.size() - (numFiles), latestFiles.size()));
    else
      return latestFiles;
  }

  private ArrayList<TimeFile> findLatestItemUsageDuplicates(File[] allFiles, int numFiles)
  {
    ArrayList<TimeFile> latestFiles = new ArrayList<TimeFile>();

    for (File f : allFiles)
    {
      latestFiles.add(new TimeFile(f));
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
    ArrayList<TimeFile> fs = findLatestItemUsageDuplicates(
        getAllCSVFilesThatStartWith("MxItemUsageAnalysisReport"),
        MainApplication.dataHub.getInventoryItemNames().size());
    ArrayList<InventoryItem> items = new ArrayList<InventoryItem>();
    MainApplication.reportsUsed += "Item Usage Reports\n";
    for (TimeFile df : fs)
    {
      MainApplication.reportsUsed += "\t" + df.getFile().getName() + "\n";
      items.add(new ItemUsageAnalysisReader(df.getFile()).getItemLine());
    }
    Collections.sort(items);
    MainApplication.dataHub.setInventoryItems(items);
  }

  public void uploadWeeklySummaryToDataHub()
  {
    ArrayList<DupFile> fs = findLatestDuplicates(getAllCSVFilesThatStartWith("WeeklySummaryReport"), 1);
    MainApplication.dataHub.setCurrentWeekSummary(new WeeklySummaryReader(fs.get(0).getFile()).getItem());
  }

}
