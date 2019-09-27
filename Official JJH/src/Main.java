import java.io.IOException;
import java.util.Date;
import java.util.Timer;

import controllers.HubController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import time_updates.TimeUpdate;

public class Main extends Application
{
  public static final String BASE_DOWNLOAD_LOCATION = "C:\\Users\\crost\\JJHLocalRepo\\Official JJH\\src\\resources";
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
      
      Timer timer = new Timer();
      timer.scheduleAtFixedRate(new TimeUpdate((HubController)loader.getController()), 0, 1000L);
      
      stage.getIcons().add(new Image("resources/jjhr.png"));
      stage.setTitle("JimmyHub -We Kick Ass, What Do You Do?");
      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage.setMaximized(true);
      stage.show();
    }
    catch (IOException e)
    {
      System.out.println("Could not load root for " + "resources/MainMenuFXML.fxml");
      e.printStackTrace();
    }
  }

}
