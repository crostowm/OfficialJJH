package app;

import java.io.IOException;

import controllers.LoginController;
import error_handling.ErrorHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoginStage extends Stage
{
  FXMLLoader loader;
  Pane root;
  public LoginStage()
  {
    try
    {
      loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/JJHLogin.fxml"));
      root = loader.load();
      LoginController lc = (LoginController) loader.getController();
      lc.setStage(this);
      setTitle("JimmyHub -We Kick Ass, What Do You Do?");
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
  
  public void setMainAppForAMLogin(MainApplication ma)
  {
    LoginController lc = (LoginController) loader.getController();
    lc.setMainApp(ma);
  }
}
