package app;

import java.io.IOException;

import error_handling.ErrorHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Login Stage
 * @author crost
 *
 */
public class LoginApp extends Application
{

  @Override
  public void start(Stage stage) throws Exception
  {
    FXMLLoader loader;
    Pane root;
    try
    {
      loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/JJHLogin.fxml"));
      root = loader.load();
      stage.setTitle("JimmyHub -We Kick Ass, What Do You Do?");
      stage.getIcons().add(new Image("resources/jjhr.png"));
      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage.setMaximized(true);
      stage.show();
    }
    catch(IOException io)
    {
      ErrorHandler.addError(io);
    }
  }

}
