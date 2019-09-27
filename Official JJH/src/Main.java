import java.io.IOException;
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

public class Main extends Application
{
  public static final String BASE_DOWNLOAD_LOCATION = "C:\\Users\\crost\\JJHLocalRepo\\Official JJH\\src\\resources";
  public static final int storeSC = 3;
  public static void main(String[] args)
  {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception
  {
    
    //HourlySalesMap hsm = new HourlySalesMap(BASE_DOWNLOAD_LOCATION + "\\Hourly Sales Report.csv");
    //UPKMap upk = new UPKMap(BASE_DOWNLOAD_LOCATION + "\\UPK Expected Usage Report (5).csv");
    FXMLLoader loader;
    Pane root;
    try
    {
      loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/JJH.fxml"));
      root = loader.load();
      
      Timer timerSec = new Timer();
      timerSec.scheduleAtFixedRate(new TimeUpdateSecond((HubController)loader.getController()), 0, 1000L);
      
      Timer timerMin = new Timer();
      timerMin.scheduleAtFixedRate(new TimeUpdateMinute((HubController)loader.getController()), 0, 60000L);
      stage.getIcons().add(new Image("resources/jjhr.png"));
      stage.setTitle("JimmyHub -We Kick Ass, What Do You Do?");
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
