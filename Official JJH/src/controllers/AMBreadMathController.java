package controllers;

import app.MainApplication;
import error_handling.ErrorHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import util.DataHub;
import util.JimmyCalendarUtil;

public class AMBreadMathController
{

  @FXML
  private TextField haveF, toBakeF, toMakeF, bakedF, reqF, haveWL, reqWL, toBakeWL, toBakeWS,
      haveWS, reqWS, haveBaked, haveSales, haveThawed, haveProofBake, toBake, toThaw, reqThawed,
      reqBaked, reqSales, projBaked;

  @FXML
  private Label val9, val12, tilSC, pm;

  public void initialize()
  {
    updateAll();
  }

  @FXML
  void updateAll()
  {
    val9.setText("Tray Of 9: $" + MainApplication.dataHub.getSetting(DataHub.B9TV));
    val12.setText("Tray Of 12: $" + MainApplication.dataHub.getSetting(DataHub.BTV));
    try
    {
      double haveB = haveBaked.getText().equals("") ? 0 : Double.parseDouble(haveBaked.getText());
      double haveP = haveProofBake.getText().equals("") ? 0
          : Double.parseDouble(haveProofBake.getText());
      double haveT = haveThawed.getText().equals("") ? 0 : Double.parseDouble(haveThawed.getText());
      haveSales.setText((haveB + haveP + haveT) + "");

      double projTilSC = MainApplication.dataHub.getAverageHourlySales("Total",
          (int) MainApplication.dataHub.getSetting(DataHub.STORESC_TIME) - 2, false)
          + MainApplication.dataHub.getAverageHourlySales("Total",
              (int) MainApplication.dataHub.getSetting(DataHub.STORESC_TIME) - 1, false);
      tilSC.setText(JimmyCalendarUtil
          .convertTo12Hour((int) MainApplication.dataHub.getSetting(DataHub.STORESC_TIME) - 2)
          + "-"
          + JimmyCalendarUtil
              .convertTo12Hour((int) MainApplication.dataHub.getSetting(DataHub.STORESC_TIME))
          + ": " + Math.ceil(projTilSC));

      double projB = (haveB + haveP) - projTilSC;
      projBaked.setText(String.format("%.0f", projB));

      double reqB = MainApplication.dataHub
          .getPercentageDataForIndex(JimmyCalendarUtil.getTodaysPMShift() - 1);
      double proj = MainApplication.dataHub
          .getProjectionDataForIndex(JimmyCalendarUtil.getTodaysPMShift() - 1);
      pm.setText("PM: " + Math.ceil(proj));
      double reqT = proj - reqB;
      reqBaked.setText(String.format("%.0f", reqB));
      reqThawed.setText(String.format("%.0f", reqT));
      reqSales.setText(String.format("%.0f", proj));

      double deltBaked = reqB - projB;
      toBake.setText(deltBaked > 0 ? String.format("%.0f",deltBaked) : "0");

      double deltThawed = reqT - haveT;
      toThaw.setText(deltThawed > 0 ? String.format("%.0f",deltThawed) : "0");
    }
    catch (Exception e)
    {
      e.printStackTrace();
      ErrorHandler.addError(e);
    }
  }

}
