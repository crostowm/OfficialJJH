import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application
{
  public static void main(String[] args)
  {
    launch(args);
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
      stage.getIcons().add(new Image("resources/jjhr.png"));
      stage.setTitle("JimmyHub -We Kick Ass, What Do You Do?");
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
