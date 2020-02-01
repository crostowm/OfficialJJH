package util;

import java.io.Serializable;

public class Config implements Serializable
{
  private static final long serialVersionUID = 4797238702620604541L;
  public int storeNumber;
  public String areaManagerEmail, downloadFolderPath, resourceFolderPath, macroUser, macroPass;
  public boolean downloadReports, amFullStart, sendAMEmail, downloadWeekendingReports;
  /**
   * @param storeNumber
   * @param areaManagerEmail
   * @param downloadFolderPath
   * @param macroUser
   * @param macroPass
   * @param downloadReports
   * @param amFullStart
   * @param downloadWeekendingReports 
   */
  public Config(int storeNumber, String areaManagerEmail,
      String macroUser, String macroPass, boolean downloadReports, boolean amFullStart, boolean sendAMEmail, boolean downloadWeekendingReports)
  {
    this.storeNumber = storeNumber;
    this.areaManagerEmail = areaManagerEmail;
    this.macroUser = macroUser;
    this.macroPass = macroPass;
    this.downloadReports = downloadReports;
    this.amFullStart = amFullStart;
    this.sendAMEmail = sendAMEmail;
    this.downloadWeekendingReports = downloadWeekendingReports;
    
    String home = System.getProperty("user.home");
    downloadFolderPath = home + "\\Downloads"; 
  }
  public int getStoreNumber()
  {
    return storeNumber;
  }
  public String getAreaManagerEmail()
  {
    return areaManagerEmail;
  }
  public String getDownloadFolderPath()
  {
    return downloadFolderPath;
  }
  public String getMacroUser()
  {
    return macroUser;
  }
  public String getMacroPass()
  {
    return macroPass;
  }
  public boolean shouldDownloadReports()
  {
    return downloadReports;
  }
  public boolean shouldAmFullStart()
  {
    return amFullStart;
  }
  public boolean shouldSendAMEmail()
  {
    return sendAMEmail;
  }
  public boolean shouldDownloadWeekendingReports()
  {
    return downloadWeekendingReports;
  }
}
