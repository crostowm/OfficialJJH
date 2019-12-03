package app;

import java.io.IOException;

import error_handling.ErrorHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class AMBreadMathStage extends Stage
{
  public AMBreadMathStage()
  {
    FXMLLoader loader;
    Pane root;
    try
    {
      loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/AMBreadMath.fxml"));
      root = loader.load();
      getIcons().add(new Image("resources/jjhr.png"));
      Scene scene = new Scene(root);
      setScene(scene);
    }
    catch(IOException io)
    {
      io.printStackTrace();
      ErrorHandler.addError(io);
    }
  }
}
