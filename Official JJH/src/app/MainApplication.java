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
import personnel.Manager;
import selenium.ReportGrabber;
import time_updates.TimeUpdateMinute;
import time_updates.TimeUpdateSecond;
import util.DataHub;
import util.ReportFinder;

/**
 * JimmyHub
 * @author Max Croston
 *
 */
public class MainApplication extends Application
{
  public static final String cateringOrderFileName = "Catering_Orders.dat";
  public static final String dataHubFileName = "Data_Hub.dat";
  public static final String FAKE_DOWNLOAD_LOCATION = "C:\\Users\\crost\\JJHLocalRepo\\Official JJH\\src\\resources";
  public static final String BASE_DOWNLOAD_LOCATION = "C:\\Users\\crost\\Downloads";
  public static boolean fullRun = false;
  public static boolean downloadReports = false;
  public static boolean sendAMEmail = false;
  public static boolean sendWeeklySupplyEmail = false;
  public static String AMEmail = "jakec.esg@gmail.com";
  public static int storeNumber = 2048;
  public static DataHub dataHub;
  public static ErrorHandler errorHandler = new ErrorHandler();
  public static Manager amManager, pmManager;
  private static ArrayList<Manager> currentManagers = new ArrayList<Manager>();
  public static String reportsUsed = "";
  private Stage stage;
  private Stage amrStage;
  private Stage loginStage;

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
    if(downloadReports)
    {
    ReportGrabber rg = new ReportGrabber(storeNumber);
    rg.runTester();
    }
    
    ReportFinder rf = new ReportFinder(BASE_DOWNLOAD_LOCATION);
    rf.uploadUPKToDataHub();
    rf.uploadWSRToDataHub();
    rf.uploadAreaManagerPhoneAuditToDataHub();
    rf.uploadHourlySalesToDataHub();
    rf.uploadTrendSheetsToDataHub();
    System.out.println(reportsUsed);
    if (fullRun)
      runLogin();
    else
      runApplication();
  }

  /**
   * Runs login followed by AMReport followed by Dash
   */
  private void runLogin()
  {
    loginStage = new Stage();
    FXMLLoader loader;
    Pane root;
    try
    {
      loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/JJHLogin.fxml"));
      root = loader.load();
      LoginController lc = (LoginController) loader.getController();
      lc.setMainApp(this);
      loginStage.setTitle("JimmyHub -We Kick Ass, What Do You Do?");
      loginStage.getIcons().add(new Image("resources/jjhr.png"));
      Scene scene = new Scene(root);
      loginStage.setScene(scene);
      loginStage.show();
    }
    catch (IOException e)
    {
      ErrorHandler.addError(e);
      e.printStackTrace();
    }
  }

  /**
   * Runs the Dash
   * Starts Timers
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

      Timer timerSec = new Timer();
      timerSec.scheduleAtFixedRate(new TimeUpdateSecond((HubController) loader.getController()), 0,
          1000L);

      Timer timerMin = new Timer();
      timerMin.scheduleAtFixedRate(new TimeUpdateMinute((HubController) loader.getController()), 0,
          60000L);
      stage.setTitle("JimmyHub -We Kick Ass, What Do You Do?");
      stage.getIcons().add(new Image("resources/jjhr.png"));
      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage.setMaximized(true);
      stage.show();
      stage.setOnCloseRequest(new EventHandler<WindowEvent>()
      {

        @Override
        public void handle(WindowEvent arg0)
        {
          System.out.println("Close Window");
          timerSec.cancel();
          timerMin.cancel();

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
        }
      });
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
        ErrorHandler.writeErrors();
        System.out.println("System was shutdown");
      }
    });
  }

  /**
   * Reads in DataHub
   * If no save file, will create a default datahub
   */
  private void readInDataHub()
  {
    try
    {
      FileInputStream in = new FileInputStream(new File("Data_Hub.dat"));
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
   * @param user Username field
   * @param pass Password field
   */
  public void runAMPhoneAudit(Manager mgr)
  {
    loginStage.close();
    amManager = mgr;
    currentManagers.add(amManager);
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

  public static ArrayList<Manager> getManagers()
  {
    return currentManagers;
  }
}
