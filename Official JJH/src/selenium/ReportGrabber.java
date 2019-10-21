package selenium;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import util.JimmyCalendarUtil;

public class ReportGrabber
{
  private String storeNumber;
  private String storeXPath;
  private WebDriver driver;

  public ReportGrabber(int storeNumber)
  {
    this.storeNumber = storeNumber + "";
    switch(storeNumber)
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

      // Login$UserName
      WebElement loginBox = driver.findElement(By.id("Login_UserName"));
      loginBox.sendKeys("crostowm");

      // Login$Password
      WebElement passBox = driver.findElement(By.id("Login_Password"));
      passBox.sendKeys("Zulu9495" + Keys.ENTER);
      
      driver.findElement(By.xpath("//*[@id=\"ctl00_ph_ListBoxReports\"]/option[22]")).click();
      //clickSendToDownloadCenter();
      downloadLast4WSR();
    }
    finally
    {
      driver.quit();
    }
  }

  private void selectStoreNumberFromDropdown()
  {
    WebElement storeComboBox = driver.findElement(By.xpath("//*[@id=\"ctl00_ph_DropDownListStore_Input\"]"));
    storeComboBox.click();
    for(int ii = 0; ii < storeNumber.length(); ii++)
    {
      storeComboBox.sendKeys(storeNumber.charAt(ii) + "");
    }
    storeComboBox.sendKeys(Keys.ENTER);
  }

  private void downloadLast4WSR()
  {
    selectStoreNumberFromDropdown();
    int currentWeekIndex = JimmyCalendarUtil.getWeekNumber(new GregorianCalendar()) - 1;
    for(int ii = 4; ii > 0; ii--)
    {
      Select select = new Select(driver.findElement(By.xpath("//*[@id=\"ctl00_ph_DropDownListPeriod\"]")));
      select.selectByIndex(currentWeekIndex-ii);
      changeToCSVAndDownload();
    }
  }

  public void runChromeReportGrabber()
  {
    driver = new ChromeDriver();
    // System.setProperty("webdriver.chrome.driver", "C:/Users/crost/Webdriver/bin");
    try
    {
      // Open Chrome to reports
      driver.get("https://jimmyjohns.macromatix.net/MMS_System_Reports.aspx?MenuCustomItemID=254");

      // Login$UserName
      WebElement loginBox = driver.findElement(By.id("Login_UserName"));
      loginBox.sendKeys("crostowm");

      // Login$Password
      WebElement passBox = driver.findElement(By.id("Login_Password"));
      passBox.sendKeys("Zulu9495" + Keys.ENTER);

      // WSR
      driver.findElement(By.xpath("//*[@id=\"ctl00_ph_ListBoxReports\"]/option[23]")).click();
      clickSendToDownloadCenter();
      chooseStore();
      driver.findElement(By.xpath("//*[@id=\"Skinnedctl00_ph_DropDownListPeriod\"]")).click();
      int currentWeekNum = JimmyCalendarUtil.getWeekNumber(new GregorianCalendar());
      scrollNumberOfItemsAndSelect("//*[@id=\"Skinnedctl00_ph_DropDownListPeriod\"]", currentWeekNum - 3);
      changeToCSVAndDownload();
      driver.findElement(By.xpath("//*[@id=\"Skinnedctl00_ph_DropDownListPeriod\"]")).sendKeys(Keys.ARROW_DOWN);
      changeToCSVAndDownload();
      driver.findElement(By.xpath("//*[@id=\"Skinnedctl00_ph_DropDownListPeriod\"]")).sendKeys(Keys.ARROW_DOWN);
      changeToCSVAndDownload();
      driver.findElement(By.xpath("//*[@id=\"Skinnedctl00_ph_DropDownListPeriod\"]")).sendKeys(Keys.ARROW_DOWN);
      driver.findElement(By.xpath("//*[@id=\"Skinnedctl00_ph_DropDownListFiscalYear\"]")).click();
      driver.findElement(By.xpath("//*[@id=\"Skinnedctl00_ph_DropDownListFiscalYear\"]"))
          .sendKeys("" + (new GregorianCalendar().get(Calendar.YEAR) - 1) + Keys.ENTER);
      changeToCSVAndDownload();

      // Go to download Center
      driver.findElement(By.xpath("//*[@id=\"ctl00_Uctrl_MMS_MenuGroups1_pb\"]/ul/li[5]/a"))
          .click();
      driver
          .findElement(
              By.xpath("//*[@id=\"ctl00_Uctrl_MMS_MenuGroups1_pb_i4_i0_ctl00\"]/ul/li[4]/a"))
          .click();
      ;
      //driver.findElement(By.xpath("//*[@id=\"ctl00_ph_GridCachedReport_ctl00_ctl04_LbDownload\"]")).click();
    }
    finally
    {
      // TODO Auto-generated catch block
      // driver.quit();
    }
  }

  private void scrollNumberOfItemsAndSelect(String string, int itemNum)
  {
    for (int ii = 0; ii < itemNum - 1; ii++)
    {
      driver.findElement(By.xpath(string)).sendKeys(Keys.ARROW_DOWN);
    }
    driver.findElement(By.xpath(string)).sendKeys(Keys.ENTER);
  }

  private void chooseStore()
  {
    driver.findElement(By.xpath(storeXPath)).click();
  }

  private void clickSendToDownloadCenter()
  {
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
