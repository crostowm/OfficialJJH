package app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Timer;

import controllers.HubController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import time_updates.TimeUpdateMinute;
import time_updates.TimeUpdateSecond;
import util.CateringOrder;
import util.DataHub;
import util.ReportFinder;

public class MainApplication extends Application
{
  public static final String cateringOrderFileName = "Catering_Orders.dat";
  public static final String FAKE_DOWNLOAD_LOCATION = "C:\\Users\\crost\\JJHLocalRepo\\Official JJH\\src\\resources";
  public static final String BASE_DOWNLOAD_LOCATION = "C:\\Users\\crost\\Downloads";
  public static final int storeSC = 3;
  public static boolean fullRun = true;

  public static void main(String[] args)
  {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception
  {
    ReportFinder rf = new ReportFinder(BASE_DOWNLOAD_LOCATION, true);
    // AMPhoneAuditMap ampam = new AMPhoneAuditMap(BASE_DOWNLOAD_LOCATION + "\\Area Manager Phone
    // Audit Report.csv");
    // HourlySalesMap hsm = new HourlySalesMap(BASE_DOWNLOAD_LOCATION + "\\Hourly Sales
    // Report.csv");
    // UPKMap upk = new UPKMap(BASE_DOWNLOAD_LOCATION + "\\UPK Expected Usage Report (5).csv");
    /*if (fullRun)
    {
      ReportGrabber rg = new ReportGrabber();
      DataHub.addWSRMapForProjections(new WSRMap(new File("src/resources/WeeklySalesRS08-crostowm.csv")), 1);
      DataHub.addWSRMapForProjections(new WSRMap(new File("src/resources/WeeklySalesRS08-crostowm (1).csv")),
          2);
      DataHub.addWSRMapForProjections(new WSRMap(new File("src/resources/WeeklySalesRS08-crostowm (2).csv")),
          3);
      DataHub.addWSRMapForProjections(new WSRMap(new File(BASE_DOWNLOAD_LOCATION + "\\WeeklySalesRS08-crostowm.csv")), 4);
    }
    else
    {
      DataHub.addWSRMapForProjections(new WSRMap(new File("src/resources/WeeklySalesRS08-crostowm.csv")), 1);
      DataHub.addWSRMapForProjections(new WSRMap(new File("src/resources/WeeklySalesRS08-crostowm (1).csv")),
          2);
      DataHub.addWSRMapForProjections(new WSRMap(new File("src/resources/WeeklySalesRS08-crostowm (2).csv")),
          3);
      DataHub.addWSRMapForProjections(new WSRMap(new File("src/resources/WeeklySalesRS08-crostowm (3).csv")),
          4);
    }*/
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
          try
          {
            FileOutputStream out = new FileOutputStream(cateringOrderFileName);
            ObjectOutputStream serializer = new ObjectOutputStream(out);

            for (CateringOrder co : DataHub.getCateringOrders())
            {
              serializer.writeObject(co);
            }

            serializer.flush();
            out.close();
          }
          catch (Exception e)
          {
            System.out.println("Failed to save catering orders");
          }
        }
      });
    }
    catch (IOException e)
    {
      System.out.println("Could not load root for " + "resources/MainMenuFXML.fxml");
      e.printStackTrace();
    }
  }
}
