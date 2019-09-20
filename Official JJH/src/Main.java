import java.io.IOException;

import controllers.ProjController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import readers.WSRMap;
import util.Email;

public class Main extends Application
{
  public static void main(String[] args)
  {
    launch(args);
    WSRMap wsrMap = new WSRMap("resources/WeeklySalesRS08-crostowm.csv");
    System.out.println(wsrMap.getDataForShift(WSRMap.ADJUSTED_SALES, 5));

    Email email = new Email("essentialsandwich@gmail.com", "Automated JimmyHub Email",
        "This is an automated email, text me if you get this. Beep. Boop.");
  }

  @Override
  public void start(Stage stage) throws Exception
  {
    FXMLLoader loader;
    Pane root;
    try
    {
      loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/JJH.fxml"));
      root = loader.load();
      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage.show();
    }
    catch (IOException e)
    {
      System.out.println("Could not load root for " + "resources/MainMenuFXML.fxml");
      e.printStackTrace();
    }
  }

}
