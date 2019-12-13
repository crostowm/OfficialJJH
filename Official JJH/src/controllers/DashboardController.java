package controllers;

import app.MainApplication;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import util.JimmyCalendarUtil;

public class DashboardController
{
  @FXML
  private TextField amProj;

  @FXML
  private TextField pmProj;

  @FXML
  private TextField sliceC;

  @FXML
  private TextField sliceH;

  @FXML
  private TextField sliceT;

  @FXML
  private TextField sliceB;

  @FXML
  private TextField sliceV;

  public void updateAllFields()
  {
    amProj.setText(String.format("%.2f", MainApplication.dataHub
        .getProjectionDataForIndex(JimmyCalendarUtil.getTodaysAMShift() - 1)));
    pmProj.setText(String.format("%.2f", MainApplication.dataHub
        .getProjectionDataForIndex(JimmyCalendarUtil.getTodaysPMShift() - 1)));

    sliceC.setText(String.format("%.2f", MainApplication.dataHub.getSlicingPars("Cheese", "gec",
        JimmyCalendarUtil.getCurrentShift())));
    sliceH.setText(String.format("%.2f",
        MainApplication.dataHub.getSlicingPars("Ham", "gec", JimmyCalendarUtil.getCurrentShift())));
    sliceT.setText(String.format("%.2f", MainApplication.dataHub.getSlicingPars("Turkey", "gec",
        JimmyCalendarUtil.getCurrentShift())));
    sliceB.setText(String.format("%.2f", MainApplication.dataHub.getSlicingPars("Beef", "gec",
        JimmyCalendarUtil.getCurrentShift())));
    sliceV.setText(String.format("%.2f",
        (MainApplication.dataHub.getSlicingPars("Salami", "gec",
            JimmyCalendarUtil.getCurrentShift())
            + MainApplication.dataHub.getSlicingPars("Capicola", "gec",
                JimmyCalendarUtil.getCurrentShift()))));

  }
}
