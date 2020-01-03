package app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Timer;

import controllers.AreaManagerReportController;
import controllers.HubController;
import error_handling.ErrorHandler;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import personnel.Manager;
import readers.InventoryItemNameReader;
import selenium.ReportGrabber;
import time_updates.TimeUpdateMinute;
import time_updates.TimeUpdateSecond;
import util.DataHub;
import util.ReportFinder;

/**
 * JimmyHub
 * 
 * @author Max Croston
 *
 */
public class MainApplication extends Application
{
  //noahyambao
  //noahyambao1234
  public static boolean fullRun = true;
  public static boolean downloadReports = false;
  public static boolean sendAMEmail = false;
  public static boolean sendWeeklySupplyEmail = false;
  public static final String cateringOrderFileName = "Catering_Orders.dat";
  public static final String dataHubFileName = "Data_Hub.dat";
  public static final String FAKE_DOWNLOAD_LOCATION = "C:\\Users\\crost\\JJHLocalRepo\\Official JJH\\src\\resources";
  public static final String BASE_DOWNLOAD_LOCATION = "C:\\Users\\crost\\Downloads";
  public static String AMEmail = "essentialsandwich@gmail.com";
  public static int storeNumber = 2048;
  public static DataHub dataHub;
  public static ErrorHandler errorHandler = new ErrorHandler();
  public static ArrayList<Manager> activeManagers = new ArrayList<Manager>();
  public static String reportsUsed = "";
  public static String jjrgb = "206, 31, 47";
  private Stage stage;
  private Stage amrStage;
  private LoginStage loginStage;
  private Timer timerMin, timerSec;

  public static void main(String[] args)
  {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception
  {
    this.stage = stage;
    setShutdownHook();
    readInDataHub();
    InventoryItemNameReader iir = new InventoryItemNameReader(new File("InventoryItems.txt"));
    dataHub.setInventoryItemNames(iir.getItems());
    if (downloadReports)
    {
      ReportGrabber rg = null;
      try
      {
        rg = new ReportGrabber(storeNumber);
        rg.startAndLogin();
        //if (new GregorianCalendar().get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY)rg.downloadTrendSheets();
        rg.downloadItemUsageAnalysis();
        //rg.downloadLastAMPhoneAuditReport();
        //rg.downloadAttendanceReport();
        //rg.downloadLast6UPK();
        //rg.downloadLast4WSR();
        //rg.downloadLastYearWSR();
        //rg.downloadLast4HourlySales();
        rg.goToDownloadCenterAndDownloadAll();
      }
      finally
      {
        if (rg != null)
          rg.close();
      }
    }

    ReportFinder rf = new ReportFinder(BASE_DOWNLOAD_LOCATION);
    rf.uploadUPKToDataHub();
    rf.uploadWSRToDataHub();
    rf.uploadAreaManagerPhoneAuditToDataHub();
    rf.uploadHourlySalesToDataHub();
    rf.uploadCateringTransactionsToDataHub();
    rf.uploadTrendSheetsToDataHub();
    rf.uploadAttendanceReportToDataHub();
    rf.uploadSpecialItems();
    System.out.println(reportsUsed);
    if (fullRun)
    {
      loginStage = new LoginStage();
      loginStage.setMainAppForAMLogin(this);
      loginStage.show();
    }
    else
    {
      activeManagers.add(new Manager("Max", "", "", ""));
      runApplication();
    }
  }

  /**
   * Runs the Dash Starts Timers
   */
  public void runApplication()
  {
    if (amrStage != null)
      amrStage.close();
    FXMLLoader loader;
    Pane root;
    try
    {
      loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/JJH.fxml"));
      root = loader.load();

      timerSec = new Timer();
      timerSec.scheduleAtFixedRate(new TimeUpdateSecond((HubController) loader.getController()), 0,
          1000L);

      timerMin = new Timer();
      timerMin.scheduleAtFixedRate(new TimeUpdateMinute((HubController) loader.getController()), 0,
          60000L);
      stage.setTitle("JimmyHub -We Kick Ass, What Do You Do?");
      stage.getIcons().add(new Image("resources/jjhr.png"));
      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage.setMaximized(true);
      stage.setOnCloseRequest(new EventHandler<WindowEvent>()
      {

        @Override
        public void handle(WindowEvent arg0)
        {
          System.out.println("Close Window");
          timerSec.cancel();
          timerMin.cancel();
        }
      });
      stage.show();
    }
    catch (IOException e)
    {
      ErrorHandler.addError(e);
      e.printStackTrace();
    }
  }

  /**
   * Runs when program halts
   */
  private void setShutdownHook()
  {
    Runtime.getRuntime().addShutdownHook(new Thread()
    {
      @Override
      public void run()
      {
        // Save Data Hub
        try
        {
          FileOutputStream out = new FileOutputStream(dataHubFileName);
          ObjectOutputStream serializer = new ObjectOutputStream(out);

          serializer.writeObject(dataHub);

          serializer.flush();
          out.close();
        }
        catch (Exception e)
        {
          ErrorHandler.addError(e);
        }
        ErrorHandler.writeErrors();
        System.out.println("System was shutdown");
      }
    });
  }

  /**
   * Reads in DataHub If no save file, will create a default datahub
   */
  private void readInDataHub()
  {
    try
    {
      FileInputStream in = new FileInputStream(new File(dataHubFileName));
      ObjectInputStream deserializer = new ObjectInputStream(in);

      dataHub = (DataHub) deserializer.readObject();
      dataHub.initializeTransientValues();
      deserializer.close();
    }
    catch (Exception e)
    {
      System.out.println("Did not read in data hub\n" + e.getMessage());
      dataHub = new DataHub();
      ErrorHandler.addError(e);
    }
  }

  /**
   * @param user
   *          Username field
   * @param pass
   *          Password field
   */
  public void runAMPhoneAudit(Manager mgr)
  {
    amrStage = new Stage();
    FXMLLoader loader;
    Pane root;
    try
    {
      loader = new FXMLLoader(
          getClass().getClassLoader().getResource("fxml/Area Manager Report.fxml"));
      root = loader.load();
      AreaManagerReportController amrc = (AreaManagerReportController) loader.getController();
      amrc.setMain(this);
      amrStage.setTitle("JimmyHub -We Kick Ass, What Do You Do?");
      amrStage.getIcons().add(new Image("resources/jjhr.png"));
      Scene scene = new Scene(root);
      amrStage.setScene(scene);
      amrStage.show();
    }
    catch (IOException e)
    {
      ErrorHandler.addError(e);
      e.printStackTrace();
    }
  }
}
