package app;

import java.io.IOException;

import controllers.WeeklySupplyController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Weekly Supply Stage
 * @author crost
 *
 */
public class WeeklySupplyStage extends Stage
{
  public WeeklySupplyStage()
  {
    FXMLLoader loader;
    Pane root;
    try
    {
      loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/Weekly Supply Orderv2.fxml"));
      root = loader.load();
      ((WeeklySupplyController)loader.getController()).setStage(this);
      getIcons().add(new Image("resources/jjhr.png"));
      setTitle("JimmyHub -We Kick Ass, What Do You Do?");
      Scene scene = new Scene(root);
      setScene(scene);
      setMaximized(true);
    }
    catch(IOException io)
    {
      io.printStackTrace();
    }
  }
}
