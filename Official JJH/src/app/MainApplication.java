package app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.GregorianCalendar;
import java.util.Timer;

import bread.BreadHandler;
import bread.BreadRequest;
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
import readers.WSRMap;
import time_updates.TimeUpdateMinute;
import time_updates.TimeUpdateSecond;
import util.CateringOrder;
import util.DataHub;
import util.ReportFinder;

public class MainApplication extends Application
{
  public static final String cateringOrderFileName = "Catering_Orders.dat";
  public static final String dataHubFileName = "Data_Hub.dat";
  public static final String FAKE_DOWNLOAD_LOCATION = "C:\\Users\\crost\\JJHLocalRepo\\Official JJH\\src\\resources";
  public static final String BASE_DOWNLOAD_LOCATION = "C:\\Users\\crost\\Downloads";
  public static final int storeSC = 3;
  public static boolean fullRun = false;
  public static boolean sendAMEmail = false;
  public static boolean sendWeeklySupplyEmail = false;
  public static String AMEmail = "jakec.esg@gmail.com";
  public static int storeNumber = 2048;
  public static DataHub dataHub;
  public static ErrorHandler errorHandler = new ErrorHandler();
  private Stage stage;
  private Stage amrStage;
  private Stage loginStage;
  
  //Need to be checked for null if not set
  private static String username;
  private static String pass;

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
    //ReportGrabber rg = new ReportGrabber(2048);
    //rg.runTester();
    ReportFinder rf = new ReportFinder(BASE_DOWNLOAD_LOCATION);
    rf.uploadWSRToDataHub();
    rf.uploadUPKToDataHub();
    rf.uploadAreaManagerPhoneAuditToDataHub();
    rf.uploadHourlySalesToDataHub();
    rf.uploadTrendSheetsToDataHub();
    
    //Testing
    BreadHandler bh = new BreadHandler();
    GregorianCalendar gc1 = new GregorianCalendar(2019, 8, 7, 16, 22, 1);
    GregorianCalendar gc2 = new GregorianCalendar(2019, 8, 7, 8, 22, 1);
    GregorianCalendar gc3 = new GregorianCalendar(2019, 8, 7, 21, 22, 1);
    GregorianCalendar gc4 = new GregorianCalendar(2019, 7, 7, 16, 22, 1);

    bh.sendRequest(new BreadRequest(14, gc1));
    bh.sendRequest(new BreadRequest(24, gc2));
    bh.sendRequest(new BreadRequest(35, gc3));
    bh.sendRequest(new BreadRequest(10, gc4));

    bh.analyzeBread();
    System.out.println(bh.toString());
    //Testing--
    
    for (int ii = 1; ii < 15; ii++)
    {
      System.out.println(dataHub.getProjectionWSR(1));
      System.out.println(dataHub.getProjectionWSR(1).getDataForShift(WSRMap.ROYALTY_SALES, ii) + " "
          + dataHub.getProjectionWSR(2).getDataForShift(WSRMap.ROYALTY_SALES, ii) + " "
          + dataHub.getProjectionWSR(3).getDataForShift(WSRMap.ROYALTY_SALES, ii) + " "
          + dataHub.getProjectionWSR(4).getDataForShift(WSRMap.ROYALTY_SALES, ii));
      double avg = (dataHub.getProjectionWSR(1).getDataForShift(WSRMap.ROYALTY_SALES, ii)
          + dataHub.getProjectionWSR(2).getDataForShift(WSRMap.ROYALTY_SALES, ii)
          + dataHub.getProjectionWSR(3).getDataForShift(WSRMap.ROYALTY_SALES, ii)
          + dataHub.getProjectionWSR(4).getDataForShift(WSRMap.ROYALTY_SALES, ii)) / 4;
      dataHub.setAverageForShift(ii, avg);
    }
    if(fullRun)
      runLogin();
    else
    runApplication();
  }

  private void runLogin()
  {
    loginStage = new Stage();
    FXMLLoader loader;
    Pane root;
    try
    {
      loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/JJHLogin.fxml"));
      root = loader.load();
      LoginController lc = (LoginController)loader.getController();
      lc.setMainApp(this);
      loginStage.setTitle("JimmyHub -We Kick Ass, What Do You Do?");
      loginStage.getIcons().add(new Image("resources/jjhr.png"));
      Scene scene = new Scene(root);
      loginStage.setScene(scene);
      loginStage.show();
    }
    catch (IOException e)
    {
      ErrorHandler.addError("Could not load root for " + "fxml/JJHLogin.fxml");
      e.printStackTrace();
    }
  }

  public void runApplication()
  {
    if(amrStage != null)
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
          System.out.println("Close");
          timerSec.cancel();
          timerMin.cancel();

          // Save Catering Orders
          try
          {
            FileOutputStream out = new FileOutputStream(cateringOrderFileName);
            ObjectOutputStream serializer = new ObjectOutputStream(out);

            for (CateringOrder co : dataHub.getCateringOrders())
            {
              serializer.writeObject(co);
            }

            serializer.flush();
            out.close();
          }
          catch (Exception e)
          {
            ErrorHandler.addError("Failed to save catering orders");
          }

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
            ErrorHandler.addError("Failed to save data hub\n" + e.getMessage() + e.toString());
          }
        }
      });
    }
    catch (IOException e)
    {
      ErrorHandler.addError("Could not load root for " + "resources/MainMenuFXML.fxml");
      e.printStackTrace();
    }
  }

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
    }
  }

  public void runAMPhoneAudit(String user, String pass)
  {
    loginStage.close();
    MainApplication.username = user;
    MainApplication.pass = pass;
    amrStage = new Stage();
    FXMLLoader loader;
    Pane root;
    try
    {
      loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/Area Manager Report.fxml"));
      root = loader.load();
      AreaManagerReportController amrc = (AreaManagerReportController)loader.getController();
      amrc.setMain(this);
      amrStage.setTitle("JimmyHub -We Kick Ass, What Do You Do?");
      amrStage.getIcons().add(new Image("resources/jjhr.png"));
      Scene scene = new Scene(root);
      amrStage.setScene(scene);
      amrStage.show();
    }
    catch (IOException e)
    {
      ErrorHandler.addError("Could not load root for " + "fxml/Area Manager Report.fxml");
      e.printStackTrace();
    }
  }
  
  public static String getUser()
  {
    return username;
  }
  
  public static String getPass()
  {
    return pass;
  }

}
