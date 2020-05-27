package selenium;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import app.AppDirector;
import error_handling.ErrorHandler;
import util.JimmyCalendarUtil;

public class ReportGrabber
{
  private String storeNumber;
  private String storeXPath;
  private WebDriver driver;
  private int numReports = 0;
  private Select reportSelector;

  public ReportGrabber()
  {
    this.storeNumber = AppDirector.config.getStoreNumber() + "";
    switch (AppDirector.config.getStoreNumber())
    {
      case 1131:
        storeXPath = "//*[@id=\"ctl00_ph_MultiStoreSelector_multiSelector_TreeView\"]/ul/li/ul/li/ul/li/ul/li/ul/li[1]/div/label/input";
        break;
      case 1300:
        storeXPath = "//*[@id=\"ctl00_ph_MultiStoreSelector_multiSelector_TreeView\"]/ul/li/ul/li/ul/li/ul/li/ul/li[2]/div/label/input";
        break;
      case 1427:
        storeXPath = "//*[@id=\"ctl00_ph_MultiStoreSelector_multiSelector_TreeView\"]/ul/li/ul/li/ul/li/ul/li/ul/li[3]/div/label/input";
        break;
      case 1528:
        storeXPath = "//*[@id=\"ctl00_ph_MultiStoreSelector_multiSelector_TreeView\"]/ul/li/ul/li/ul/li/ul/li/ul/li[4]/div/label/input";
        break;
      case 1740:
        storeXPath = "//*[@id=\"ctl00_ph_MultiStoreSelector_multiSelector_TreeView\"]/ul/li/ul/li/ul/li/ul/li/ul/li[5]/div/label/input";
        break;
      case 1741:
        storeXPath = "//*[@id=\"ctl00_ph_MultiStoreSelector_multiSelector_TreeView\"]/ul/li/ul/li/ul/li/ul/li/ul/li[6]/div/label/input";
        break;
      case 2048:
        storeXPath = "//*[@id=\"ctl00_ph_MultiStoreSelector_multiSelector_TreeView\"]/ul/li/ul/li/ul/li/ul/li/ul/li[7]/div/label/input";
        break;
      case 2581:
        storeXPath = "//*[@id=\"ctl00_ph_MultiStoreSelector_multiSelector_TreeView\"]/ul/li/ul/li/ul/li/ul/li/ul/li[8]/div/label/input";
        break;
    }
  }

  public void startAndLogin()
  {
    driver = new ChromeDriver();
    try
    {
      // Open Chrome to reports
      driver.get("https://jimmyjohns.macromatix.net/MMS_System_Reports.aspx?MenuCustomItemID=254");
      driver.manage().window().maximize();

      // Login$UserName
      WebElement loginBox = driver.findElement(By.id("Login_UserName"));
      loginBox.sendKeys(AppDirector.config.getMacroUser());

      // Login$Password
      WebElement passBox = driver.findElement(By.id("Login_Password"));
      passBox.sendKeys(AppDirector.config.getMacroPass() + Keys.ENTER);

      reportSelector = new Select(
          driver.findElement(By.xpath("//*[@id=\"ctl00_ph_ListBoxReports\"]")));
    }
    catch (Exception e)
    {
      ErrorHandler.addError(e);
      e.printStackTrace();
    }
  }

  public void downloadItemUsageAnalysis()
  {
    reportSelector = new Select(
        driver.findElement(By.xpath("//*[@id=\"ctl00_ph_ListBoxReports\"]")));
    reportSelector.selectByVisibleText("Item Usage Analysis Report");
    selectDatesOfLastCompletedWeek();
    WebElement typeDropDown = driver
        .findElement(By.xpath("//*[@id=\"Skinnedctl00_ph_DropDownListReportFormat\"]"));
    typeDropDown.click();
    typeDropDown.sendKeys("c" + Keys.ENTER);
    for (String s : AppDirector.dataHub.getInventoryItemNames())
    {
      try
      {
        Select select = new Select(
            driver.findElement(By.xpath("//*[@id=\"ctl00_ph_DropDownListItems\"]")));
        select.selectByVisibleText(s);
        WebElement generate = driver
            .findElement(By.xpath("//*[@id=\"ctl00_ph_ButtonGenerate_input\"]"));
        generate.click();
      }
      catch (Exception e)
      {
        System.out.println(s + " Not Found");
        ErrorHandler.addError(e);
        continue;
      }
    }
  }

  private void selectDatesOfLastCompletedWeek()
  {
    GregorianCalendar startDate = new GregorianCalendar();
    GregorianCalendar endDate = new GregorianCalendar();
    startDate.add(Calendar.DAY_OF_YEAR, -1);
    endDate.add(Calendar.DAY_OF_YEAR, -1);
    while (endDate.get(Calendar.DAY_OF_WEEK) != Calendar.TUESDAY)
    {
      startDate.add(Calendar.DAY_OF_YEAR, -1);
      endDate.add(Calendar.DAY_OF_YEAR, -1);
    }
    startDate.add(Calendar.DAY_OF_YEAR, -6);
    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
    driver
        .findElement(
            By.xpath("//*[@id=\"ctl00_ph_DateRangePicker_DatePickerStart_dateInput_wrapper\"]"))
        .click();
    driver.findElement(By.xpath("//*[@id=\"ctl00_ph_DateRangePicker_DatePickerStart_dateInput\"]"))
        .sendKeys(Keys.BACK_SPACE + sdf.format(startDate.getTime()) + Keys.ENTER);
    driver
        .findElement(
            By.xpath("//*[@id=\"ctl00_ph_DateRangePicker_DatePickerEnd_dateInput_wrapper\"]"))
        .click();
    driver.findElement(By.xpath("//*[@id=\"ctl00_ph_DateRangePicker_DatePickerEnd_dateInput\"]"))
        .sendKeys(Keys.BACK_SPACE + sdf.format(endDate.getTime()) + Keys.ENTER);
  }

  public void downloadTrendSheets()
  {
    reportSelector = new Select(
        driver.findElement(By.xpath("//*[@id=\"ctl00_ph_ListBoxReports\"]")));
    reportSelector.selectByVisibleText("Trend Sheet");
    selectFiscalYear(new GregorianCalendar().get(Calendar.YEAR) - 1);
    selectStoreCheckBox();
    changeToCSVAndDownload();
    selectFiscalYear(new GregorianCalendar().get(Calendar.YEAR));
    selectStoreCheckBox();
    changeToCSVAndDownload();
  }

  private void selectFiscalYear(int year)
  {
    Select yearSelect = new Select(
        driver.findElement(By.xpath("//*[@id=\"ctl00_ph_DropDownListFiscalYear\"]")));
    yearSelect.selectByValue("" + year);
  }

  public void downloadAttendanceReport()
  {
    reportSelector = new Select(
        driver.findElement(By.xpath("//*[@id=\"ctl00_ph_ListBoxReports\"]")));
    reportSelector.selectByVisibleText("Attendance Report");
    selectStoreNumberFromDropdown();
    // selectDateXDaysBeforeCurrent(1);
    selectYesterdayFromDropdown();
    changeToCSVAndDownload();
  }

  private void selectYesterdayFromDropdown()
  {
    driver
        .findElement(
            By.xpath("//*[@id=\"ctl00_ph_DateRangePicker_DropDownListAutoDateSet_Input\"]"))
        .sendKeys("Yesterday" + Keys.ENTER);
  }

  @SuppressWarnings("unused")
  private void selectDayOfTheWeek()
  {
    GregorianCalendar gc = new GregorianCalendar();
    int day = gc.get(Calendar.DAY_OF_WEEK);
    int index = 0;
    switch (day)
    {
      case 1:
        index = 7;
        break;
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
        index = day - 1;
        break;
    }
    Select select = new Select(driver.findElement(By.xpath("//*[@id=\"ctl00_ph_ddlDayOfWeek\"]")));
    select.selectByIndex(index);
  }

  @SuppressWarnings("unused")
  private void selectDateXDaysBeforeCurrent(int numDays)
  {
    GregorianCalendar gc = new GregorianCalendar();
    gc.add(Calendar.DAY_OF_YEAR, -numDays);
    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
    driver
        .findElement(
            By.xpath("//*[@id=\"ctl00_ph_DateRangePicker_DatePickerStart_dateInput_wrapper\"]"))
        .click();
    driver.findElement(By.xpath("//*[@id=\"ctl00_ph_DateRangePicker_DatePickerStart_dateInput\"]"))
        .sendKeys(Keys.BACK_SPACE + sdf.format(gc.getTime()) + Keys.ENTER);
    driver
        .findElement(
            By.xpath("//*[@id=\"ctl00_ph_DateRangePicker_DatePickerEnd_dateInput_wrapper\"]"))
        .click();
    driver.findElement(By.xpath("//*[@id=\"ctl00_ph_DateRangePicker_DatePickerEnd_dateInput\"]"))
        .sendKeys(Keys.BACK_SPACE + sdf.format(gc.getTime()) + Keys.ENTER);
  }

  public void goToDownloadCenterAndDownloadAll()
  {
    driver.findElement(By.xpath("//*[@id=\"ctl00_Uctrl_MMS_MenuGroups1_pb\"]/ul/li[5]/a")).click();
    driver
        .findElement(
            By.xpath("//*[@id=\"ctl00_Uctrl_MMS_MenuGroups1_pb_i4_i0_ctl00\"]/ul/li[4]/a/span"))
        .click();
    System.out.println("Downloading " + numReports + " from the download center");
    try
    {
      for (int ii = 0; ii < numReports; ii++)
      {
        driver
            .findElement(By.xpath("//*[@id=\"ctl00_ph_GridCachedReport_ctl00_ctl04_LbDownload\"]"))
            .click();
      }
    }
    catch (Exception e)
    {
      ErrorHandler.addError(e);
    }
  }

  public void downloadMissingUPKs(ArrayList<int[]> missingWeeks)
  {
    reportSelector = new Select(
        driver.findElement(By.xpath("//*[@id=\"ctl00_ph_ListBoxReports\"]")));
    reportSelector.selectByVisibleText("UPK Expected Usage Report");
    selectStoreCheckBox();
    for (int[] i : missingWeeks)
    {
      selectFiscalYear(i[0]);
      Select select = new Select(
          driver.findElement(By.xpath("//*[@id=\"ctl00_ph_DropDownListPeriod\"]")));
      select.selectByIndex(i[1] - 1);
      changeToCSVAndDownload();
      numReports++;
    }
  }

  /*
   * public void downloadLast6UPK() {
   * driver.findElement(By.xpath("//*[@id=\"ctl00_ph_ListBoxReports\"]/option[20]")).click();
   * selectStoreCheckBox(); selectLastXWeeksFromWeekDropdownAndDownload(6); }
   * 
   * private void selectLastXWeeksFromWeekDropdownAndDownload(int numWeeks) { int currentWeekIndex =
   * JimmyCalendarUtil.getWeekNumber(new GregorianCalendar()) - 1; for (int ii = numWeeks; ii > 0;
   * ii--) { if (currentWeekIndex - ii < 0) { selectFiscalYear(new
   * GregorianCalendar().get(Calendar.YEAR) - 1); Select select = new Select(
   * driver.findElement(By.xpath("//*[@id=\"ctl00_ph_DropDownListPeriod\"]")));
   * select.selectByIndex(select.getOptions().size() + currentWeekIndex - ii); } else {
   * selectFiscalYear(new GregorianCalendar().get(Calendar.YEAR)); Select select = new Select(
   * driver.findElement(By.xpath("//*[@id=\"ctl00_ph_DropDownListPeriod\"]")));
   * select.selectByIndex(currentWeekIndex - ii); } changeToCSVAndDownload(); numReports++; } }
   */
  private void selectStoreNumberFromDropdown()
  {
    WebElement storeComboBox = driver
        .findElement(By.xpath("//*[@id=\"ctl00_ph_DropDownListStore_Input\"]"));
    storeComboBox.click();
    for (int ii = 0; ii < storeNumber.length(); ii++)
    {
      storeComboBox.sendKeys(storeNumber.charAt(ii) + "");
    }
    storeComboBox.sendKeys(Keys.ENTER);
  }

  private void selectStoreCheckBox()
  {
    if (!driver.findElement(By.xpath(storeXPath)).isSelected())
      driver.findElement(By.xpath(storeXPath)).click();
  }

  public void downloadLastAMPhoneAuditReport()
  {
    reportSelector = new Select(
        driver.findElement(By.xpath("//*[@id=\"ctl00_ph_ListBoxReports\"]")));
    reportSelector.selectByVisibleText("Area Manager Phone Audit Report");
    selectStoreCheckBox();
    changeToCSVAndDownload();
    numReports++;
  }

  /*
   * public void downloadLast4WSR() {
   * driver.findElement(By.xpath("//*[@id=\"ctl00_ph_ListBoxReports\"]/option[22]")).click();
   * selectStoreNumberFromDropdown(); selectLastXWeeksFromWeekDropdownAndDownload(4); }
   */

  /**
   * @param missingWeeks[0]
   *          = Year, [1] = Week
   */
  public void downloadMissingWSR(ArrayList<int[]> missingWeeks)
  {
    reportSelector = new Select(
        driver.findElement(By.xpath("//*[@id=\"ctl00_ph_ListBoxReports\"]")));
    reportSelector.selectByVisibleText("Weekly Sales Report");
    selectStoreNumberFromDropdown();
    for (int[] ii : missingWeeks)
    {
      // System.out.println("Year: " + ii[0] + " Week: " + ii[1]);
      selectWeekYearPairAndDownload(ii);
    }
  }

  private void selectWeekYearPairAndDownload(int[] ii)
  {
    selectFiscalYear(ii[0]);
    int currentWeekIndex = ii[1] - 1;
    Select select = new Select(
        driver.findElement(By.xpath("//*[@id=\"ctl00_ph_DropDownListPeriod\"]")));
    select.selectByIndex(currentWeekIndex);
    changeToCSVAndDownload();
    numReports++;
  }

  public void downloadLastYearWSR()
  {
    reportSelector = new Select(
        driver.findElement(By.xpath("//*[@id=\"ctl00_ph_ListBoxReports\"]")));
    reportSelector.selectByVisibleText("Weekly Sales Report");
    selectStoreNumberFromDropdown();
    selectFiscalYear(new GregorianCalendar().get(Calendar.YEAR) - 1);

    int currentWeekIndex = JimmyCalendarUtil.getWeekNumber(new GregorianCalendar()) - 1;
    Select weekSelect = new Select(
        driver.findElement(By.xpath("//*[@id=\"ctl00_ph_DropDownListPeriod\"]")));
    weekSelect.selectByIndex(currentWeekIndex);
    changeToCSVAndDownload();
    numReports++;
  }

  private void changeToCSVAndDownload()
  {
    WebElement typeDropDown = driver
        .findElement(By.xpath("//*[@id=\"Skinnedctl00_ph_DropDownListReportFormat\"]"));
    typeDropDown.click();
    typeDropDown.sendKeys("c" + Keys.ENTER);

    WebElement generate = driver
        .findElement(By.xpath("//*[@id=\"ctl00_ph_ButtonGenerate_input\"]"));
    generate.click();
  }

  public void close()
  {
    driver.quit();
  }

  public void downloadHourlySalesReport(String date)
  {
    reportSelector = new Select(
        driver.findElement(By.xpath("//*[@id=\"ctl00_ph_ListBoxReports\"]")));
    reportSelector.selectByVisibleText("Hourly Sales Report");
    selectStoreCheckBox();
    selectDate(date);
    changeToCSVAndDownload();
    numReports++;
  }

  private void selectDate(String date)
  {
    driver
        .findElement(
            By.xpath("//*[@id=\"ctl00_ph_DateRangePicker_DatePickerStart_dateInput_wrapper\"]"))
        .click();
    driver.findElement(By.xpath("//*[@id=\"ctl00_ph_DateRangePicker_DatePickerStart_dateInput\"]"))
        .sendKeys(Keys.BACK_SPACE + date + Keys.ENTER);
    driver
        .findElement(
            By.xpath("//*[@id=\"ctl00_ph_DateRangePicker_DatePickerEnd_dateInput_wrapper\"]"))
        .click();
    driver.findElement(By.xpath("//*[@id=\"ctl00_ph_DateRangePicker_DatePickerEnd_dateInput\"]"))
        .sendKeys(Keys.BACK_SPACE + date + Keys.ENTER);
  }

  public void downloadHourlySalesReports(ArrayList<String> downloadQueue)
  {
    reportSelector = new Select(
        driver.findElement(By.xpath("//*[@id=\"ctl00_ph_ListBoxReports\"]")));
    reportSelector.selectByVisibleText("Hourly Sales Report");
    selectStoreCheckBox();
    for (String date : downloadQueue)
    {
      selectDate(date);
      changeToCSVAndDownload();
      numReports++;
    }
    goToDownloadCenterAndDownloadAll();
  }

  public void downloadTrendSheet(Integer year)
  {
    reportSelector = new Select(
        driver.findElement(By.xpath("//*[@id=\"ctl00_ph_ListBoxReports\"]")));
    reportSelector.selectByVisibleText("Trend Sheet");
    selectFiscalYear(year);
    selectStoreCheckBox();
    changeToCSVAndDownload();
  }
}
