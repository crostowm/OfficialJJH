package gui;

import java.io.IOException;

import controllers.ProgressStageController;
import error_handling.ErrorHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoadingStage extends Stage
{
  private ProgressStageController psc;
  public LoadingStage()
  {
    FXMLLoader loader;
    Pane root;
    try
    {
      loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/ProgressStage.fxml"));
      root = loader.load();
      psc = (ProgressStageController)loader.getController();
      psc.setProgress(.1);
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
  
  public void incrementPB(double increment)
  {
    psc.increment(increment);
  }
}
