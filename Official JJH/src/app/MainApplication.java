package app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Timer;

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
  public static boolean fullRun = true;
  public static DataHub dataHub;
  public static ErrorHandler errorHandler = new ErrorHandler();

  public static void main(String[] args)
  {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception
  {
    /*Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        ErrorHandler.writeErrors();
          System.out.println("System was shutdown");
      }
  });*/
    readInDataHub();
    ReportFinder rf = new ReportFinder(BASE_DOWNLOAD_LOCATION);
    rf.uploadWSRToDataHub();
    rf.uploadUPKToDataHub();
    // ReportGrabber rg = new ReportGrabber();
    // AMPhoneAuditMap ampam = new AMPhoneAuditMap(BASE_DOWNLOAD_LOCATION + "\\Area Manager Phone
    // Audit Report.csv");
    // HourlySalesMap hsm = new HourlySalesMap(BASE_DOWNLOAD_LOCATION + "\\Hourly Sales
    // Report.csv");
    // UPKMap upk = new UPKMap(BASE_DOWNLOAD_LOCATION + "\\UPK Expected Usage Report (5).csv");
    /*
     * if (fullRun) { dataHub.addWSRMapForProjections(new WSRMap(new
     * File("src/resources/WeeklySalesRS08-crostowm.csv")), 1); dataHub.addWSRMapForProjections(new
     * WSRMap(new File("src/resources/WeeklySalesRS08-crostowm (1).csv")), 2);
     * dataHub.addWSRMapForProjections(new WSRMap(new
     * File("src/resources/WeeklySalesRS08-crostowm (2).csv")), 3);
     * dataHub.addWSRMapForProjections(new WSRMap(new File(BASE_DOWNLOAD_LOCATION +
     * "\\WeeklySalesRS08-crostowm.csv")), 4); } else { dataHub.addWSRMapForProjections(new
     * WSRMap(new File("src/resources/WeeklySalesRS08-crostowm.csv")), 1);
     * dataHub.addWSRMapForProjections(new WSRMap(new
     * File("src/resources/WeeklySalesRS08-crostowm (1).csv")), 2);
     * dataHub.addWSRMapForProjections(new WSRMap(new
     * File("src/resources/WeeklySalesRS08-crostowm (2).csv")), 3);
     * dataHub.addWSRMapForProjections(new WSRMap(new
     * File("src/resources/WeeklySalesRS08-crostowm (3).csv")), 4); }
     */
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
          timerSec.cancel();
          timerMin.cancel();
          
          //Save Catering Orders
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
          
        //Save Data Hub
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

  private void readInDataHub()
  {
    try
    {
      FileInputStream in = new FileInputStream(new File("DataHub.dat"));
      ObjectInputStream deserializer = new ObjectInputStream(in);

      dataHub = (DataHub) deserializer.readObject();
      deserializer.close();
    }
    catch (Exception e)
    {
      dataHub = new DataHub();
    }
  }
}
