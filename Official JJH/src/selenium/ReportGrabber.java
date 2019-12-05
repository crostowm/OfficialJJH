package selenium;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import error_handling.ErrorHandler;
import util.JimmyCalendarUtil;

public class ReportGrabber
{
  private String storeNumber;
  private String storeXPath;
  private WebDriver driver;
  private int numReports = 0;

  public ReportGrabber(int storeNumber)
  {
    this.storeNumber = storeNumber + "";
    switch (storeNumber)
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
      case 2048:
        storeXPath = "//*[@id=\"ctl00_ph_MultiStoreSelector_multiSelector_TreeView\"]/ul/li/ul/li/ul/li/ul/li/ul/li[5]/div/label/input";
        break;
      case 2581:
        storeXPath = "//*[@id=\"ctl00_ph_MultiStoreSelector_multiSelector_TreeView\"]/ul/li/ul/li/ul/li/ul/li/ul/li[6]/div/label/input";
        break;
    }
  }

  public void runTester()
  {
    driver = new ChromeDriver();
    try
    {
      // Open Chrome to reports
      driver.get("https://jimmyjohns.macromatix.net/MMS_System_Reports.aspx?MenuCustomItemID=254");
      driver.manage().window().maximize();

      // Login$UserName
      WebElement loginBox = driver.findElement(By.id("Login_UserName"));
      loginBox.sendKeys("crostowm");

      // Login$Password
      WebElement passBox = driver.findElement(By.id("Login_Password"));
      passBox.sendKeys("Zulu9495" + Keys.ENTER);

      downloadLastAMPhoneAuditReport();
      downloadLast6UPK();
      downloadLast4WSR();
      downloadLast4HourlySales();
      goToDownloadCenterAndDownloadAll();
    }
    finally
    {
      // driver.quit();
    }
  }

  private void downloadLast4HourlySales()
  {
    driver.findElement(By.xpath("//*[@id=\"ctl00_ph_ListBoxReports\"]/option[12]")).click();
    selectStoreCheckBox();
    selectDayOfTheWeek();
    for (int ii = 28; ii >= 7; ii -= 7)
    {
      selectDateXDaysBeforeCurrent(ii);
      changeToCSVAndDownload();
      numReports++;
    }
  }

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

  private void goToDownloadCenterAndDownloadAll()
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

  private void downloadLast6UPK()
  {
    driver.findElement(By.xpath("//*[@id=\"ctl00_ph_ListBoxReports\"]/option[20]")).click();
    selectStoreCheckBox();
    selectLastXWeeksFromWeekDropdownAndDownload(6);
  }

  private void selectLastXWeeksFromWeekDropdownAndDownload(int numWeeks)
  {
    int currentWeekIndex = JimmyCalendarUtil.getWeekNumber(new GregorianCalendar()) - 1;

    for (int ii = numWeeks; ii > 0; ii--)
    {
      Select select = new Select(
          driver.findElement(By.xpath("//*[@id=\"ctl00_ph_DropDownListPeriod\"]")));
      select.selectByIndex(currentWeekIndex - ii);
      changeToCSVAndDownload();
      numReports++;
    }
  }

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

  private void downloadLastAMPhoneAuditReport()
  {
    driver.findElement(By.xpath("//*[@id=\"ctl00_ph_ListBoxReports\"]/option[2]")).click();
    selectStoreCheckBox();
    changeToCSVAndDownload();
    numReports++;
  }

  private void downloadLast4WSR()
  {
    driver.findElement(By.xpath("//*[@id=\"ctl00_ph_ListBoxReports\"]/option[22]")).click();
    selectStoreNumberFromDropdown();
    selectLastXWeeksFromWeekDropdownAndDownload(4);
  }

  private void sendToDownloadCenter()
  {
    if (!driver.findElement(By.xpath("//*[@id=\"ctl00_ph_CheckBoxSendToDownloadCentre\"]"))
        .isSelected())
      driver.findElement(By.xpath("//*[@id=\"ctl00_ph_CheckBoxSendToDownloadCentre\"]")).click();
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
}
