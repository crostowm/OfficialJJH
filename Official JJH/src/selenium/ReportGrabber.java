package selenium;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class ReportGrabber
{
  private ArrayList<String> xPathForReports = new ArrayList<String>();
  private String store1528 = "//*[@id=\"ctl00_ph_MultiStoreSelector_multiSelector_TreeView\"]/ul/li/ul/li/ul/li/ul/li/ul/li[4]/div/label/input";
  private String store2048 = "//*[@id=\"ctl00_ph_MultiStoreSelector_multiSelector_TreeView\"]/ul/li/ul/li/ul/li/ul/li/ul/li[5]/div/label/input";

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
    WebDriver driver = new ChromeDriver();
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

      // Max 23 reports so < 24 for all
      for (int ii = 1; ii < 2; ii++)
      {
        driver.findElement(By.xpath("//*[@id=\"ctl00_ph_ListBoxReports\"]/option[" + ii + "]"))
            .click();
        // *[@id="ctl00_ph_ListBoxReports"]/option[2]
        switch (ii)
        {
          case 1:
          case 2:
          case 9:
          case 10:
          case 12:
          case 19:
          case 20:
          case 23:
            // Check send to download center
            driver.findElement(By.xpath("//*[@id=\"ctl00_ph_CheckBoxSendToDownloadCentre\"]"))
                .click();
            driver.findElement(By.xpath(store2048)).click();
            break;
          default:
            break;
        }
        WebElement typeDropDown = driver
            .findElement(By.xpath("//*[@id=\"Skinnedctl00_ph_DropDownListReportFormat\"]"));
        typeDropDown.click();
        typeDropDown.sendKeys("c" + Keys.ENTER);

        WebElement generate = driver
            .findElement(By.xpath("//*[@id=\"ctl00_ph_ButtonGenerate_input\"]"));
        generate.click();
      }

      // Go to download Center
      driver.findElement(By.xpath("//*[@id=\"ctl00_Uctrl_MMS_MenuGroups1_pb\"]/ul/li[5]/a"))
          .click();
      driver
          .findElement(
              By.xpath("//*[@id=\"ctl00_Uctrl_MMS_MenuGroups1_pb_i4_i0_ctl00\"]/ul/li[4]/a"))
          .click();
      ;
      driver.findElement(By.xpath("//*[@id=\"ctl00_ph_GridCachedReport_ctl00_ctl04_LbDownload\"]"))
          .click();
      // driver.findElement(By.xpath("//*[@id=\"ctl00_ph_GridCachedReport_ctl00_ctl04_LbDownload\"]")).click();
    }
    finally
    {
      // TODO Auto-generated catch block
      // driver.quit();
    }
  }
}
