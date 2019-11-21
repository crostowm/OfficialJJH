package app;

import java.io.IOException;

import controllers.AddManagerController;
import error_handling.ErrorHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class AddManagerStage extends Stage
{

  public AddManagerStage()
  {
    FXMLLoader loader;
    Pane root;
    try
    {
      loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/Add Manager.fxml"));
      root = loader.load();
      ((AddManagerController)loader.getController()).setStage(this);
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
