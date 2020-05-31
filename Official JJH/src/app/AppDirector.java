package app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Timer;

import controllers.AreaManagerReportController;
import controllers.ConfigMenuController;
import controllers.HubController;
import controllers.LoginController;
import error_handling.ErrorHandler;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lineitems.InventoryItem;
import personnel.Manager;
import readers.InventoryItemNameReader;
import selenium.ReportGrabber;
import time_updates.TimeUpdateMinute;
import time_updates.TimeUpdateSecond;
import util.Config;
import util.DataHub;
import util.ReportFinder;

public class AppDirector extends Application
{
  public static Config config;
  public static DataHub dataHub;
  public static final String cateringOrderFileName = "Catering_Orders.dat";
  public static final String dataHubFileName = "Data_Hub.dat";
  public static final String configFileName = "Config.dat";
  public static String reportsUsed = "";
  public static String jjrgb = "206, 31, 47";
  public static ErrorHandler errorHandler = new ErrorHandler();
  public static ArrayList<Manager> activeManagers = new ArrayList<Manager>();
  private Timer timerMin, timerSec;
  private Stage stage;

  public static void main(String[] args)
  {
    launch(args);
  }
  
  @Override
  public void start(Stage stage) throws Exception
  {
    this.stage = stage;
    if (readConfig())
    {

    }
    else
    {
    }
    FXMLLoader loader;
    Pane root;
    try
    {
      loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/ConfigMenu.fxml"));
      root = loader.load();
      ConfigMenuController cmc = (ConfigMenuController) loader.getController();
      cmc.setMain(this);
      stage.setTitle("JimmyHub -We Kick Ass, What Do You Do?");
      stage.getIcons().add(new Image("resources/jjhr.png"));
      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage.setOnCloseRequest(new EventHandler<WindowEvent>()
      {

        @Override
        public void handle(WindowEvent arg0)
        {
          System.out.println("Close Window");
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

  public void configComplete()
  {
    setShutdownHook();
    readInDataHub();
    dataHub.setupObservers();
    InventoryItemNameReader iir = new InventoryItemNameReader(new File("InventoryItems.txt"));
    dataHub.setInventoryItemNames(iir.getItems());
    for(String s: dataHub.getSavedUPKs())
    {
      System.out.println(s);
    }
    if (config.shouldDownloadReports())
    {
      ReportGrabber rg = null;
      try
      {
        rg = new ReportGrabber();
        rg.startAndLogin();
        rg.downloadMissingUPKs(dataHub.getMissingOfLast6UPKYearWeekPairs());
        if (new GregorianCalendar().get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY
            || AppDirector.config.shouldDownloadWeekendingReports())
        {
          rg.downloadItemUsageAnalysis();
          // rg.downloadFullWeekAttendance();
        }
        else
        {
          rg.downloadAttendanceReport();
        }
        // rg.downloadLast4Catering();
        rg.downloadLastAMPhoneAuditReport();
        rg.downloadMissingWSR(dataHub.getMissingOfLast41WSRYearWeekPairs());
        rg.downloadHourlySalesReports(dataHub.getMissingLast4HourlySales());
      }
      finally
      {
        if (rg != null)
          rg.close();
      }
    }
    ReportFinder rf = new ReportFinder(AppDirector.config.getDownloadFolderPath());
    rf.uploadSpecialItems();
    rf.uploadMissingUPKsToDataHub(dataHub.getMissingOfLast6UPKYearWeekPairs());
    for (InventoryItem ii : dataHub.getInventoryItems())
    {
      dataHub.getPast6UPKMaps().get(5).getUPKItem(ii.getName());
    }
    rf.uploadWSRToDataHub(dataHub.getMissingOfLast41WSRYearWeekPairs().size());
    rf.uploadAreaManagerPhoneAuditToDataHub();
    rf.uploadLastXHourlyDays(dataHub.getMissingLast4HourlySales());
    rf.uploadCateringTransactionsToDataHub();
    rf.uploadAttendanceReportToDataHub();
    rf.uploadWeeklySummaryToDataHub();
    System.out.println(reportsUsed);
  
    for(String s: dataHub.getSavedUPKs())
    {
      System.out.println(s);
    }
    if (config.shouldAmFullStart())
      runAMPhoneAudit(new Manager("Guest", "", "", ""));
    else
      runDash();
  }

  public void runLoginStage()
  {
    FXMLLoader loader;
    Pane root;
    try
    {
      loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/JJHLogin.fxml"));
      root = loader.load();
      LoginController lc = (LoginController) loader.getController();
      lc.setMainApp(this);
      stage.setTitle("JimmyHub -We Kick Ass, What Do You Do?");
      stage.getIcons().add(new Image("resources/jjhr.png"));
      Scene scene = new Scene(root);
      stage.setScene(scene);
    }
    catch (IOException io)
    {
      io.printStackTrace();
      ErrorHandler.addError(io);
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
    FXMLLoader loader;
    Pane root;
    try
    {
      loader = new FXMLLoader(
          getClass().getClassLoader().getResource("fxml/Area Manager Report.fxml"));
      root = loader.load();
      AreaManagerReportController amrc = (AreaManagerReportController) loader.getController();
      amrc.setMain(this);
      Scene scene = new Scene(root);
      stage.setScene(scene);
    }
    catch (IOException e)
    {
      ErrorHandler.addError(e);
      e.printStackTrace();
    }
  }

  /**
   * Runs the Dash Starts Timers
   */
  public void runDash()
  {
    FXMLLoader loader;
    Pane root;
    try
    {
      loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/JJH.fxml"));
      root = loader.load();
      HubController hc = (HubController)loader.getController();
      dataHub.getCateringWeek().addObserver(hc.getCateringCalculatorController());
      hc.updateAllFields();
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

  private boolean readConfig()
  {
    try
    {
      FileInputStream in = new FileInputStream(new File(configFileName));
      ObjectInputStream deserializer = new ObjectInputStream(in);

      config = (Config) deserializer.readObject();
      deserializer.close();
      return true;
    }
    catch (Exception e)
    {
      System.out.println("Did not read in config\n" + e.getMessage());
      ErrorHandler.addError(e);
      return false;
    }
  }

  /**
   * Reads in DataHub If no save file, will create a default datahub
   */
  private void readInDataHub()
  {
    try
    {
      FileInputStream in = new FileInputStream(new File(config.getStoreNumber() + dataHubFileName));
      ObjectInputStream deserializer = new ObjectInputStream(in);

      dataHub = (DataHub) deserializer.readObject();
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
   * Runs when program halts
   */
  private void setShutdownHook()
  {
    Runtime.getRuntime().addShutdownHook(new Thread()
    {
      @Override
      public void run()
      {
        try
        {
          saveDataHub();
          saveConfig();
          timerSec.cancel();
          timerMin.cancel();
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

  protected void saveConfig() throws Exception
  {
    FileOutputStream out = new FileOutputStream(configFileName);
    ObjectOutputStream serializer = new ObjectOutputStream(out);

    serializer.writeObject(config);

    serializer.flush();
    out.close();
  }

  protected void saveDataHub() throws Exception
  {
    FileOutputStream out = new FileOutputStream(config.getStoreNumber() + dataHubFileName);
    ObjectOutputStream serializer = new ObjectOutputStream(out);

    serializer.writeObject(dataHub);

    serializer.flush();
    out.close();
  }

}
