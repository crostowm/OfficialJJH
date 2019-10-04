package selenium;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import util.JimmyCalendarUtil;

public class ReportGrabber
{
  private ArrayList<String> xPathForReports = new ArrayList<String>();
  //private String store1528 = "//*[@id=\"ctl00_ph_MultiStoreSelector_multiSelector_TreeView\"]/ul/li/ul/li/ul/li/ul/li/ul/li[4]/div/label/input";
  private String store2048 = "//*[@id=\"ctl00_ph_MultiStoreSelector_multiSelector_TreeView\"]/ul/li/ul/li/ul/li/ul/li/ul/li[5]/div/label/input";
  private WebDriver driver;

  public ReportGrabber()
  {
    populateReports();

    runChromeReportGrabber();
  }

  private void populateReports()
  {
    xPathForReports.add(
        "//*[@id=\"ctl00_ph_MultiStoreSelector_multiSelector_TreeView\"]/ul/li/ul/li/ul/li/ul/li/ul/li[1]/div/label/span");
    // *[@id="ctl00_ph_ListBoxReports"]/option[23]
  }

  private void runChromeReportGrabber()
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
    driver.findElement(By.xpath(store2048)).click();
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
