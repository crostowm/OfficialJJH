package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;

public class ProgressStageController
{
  @FXML
  private ProgressBar pb;
  
  public void increment(double increment)
  {
    pb.setProgress(pb.getProgress() + increment);
  }

  public void setProgress(double d)
  {
    pb.setProgress(d);
  }
}
