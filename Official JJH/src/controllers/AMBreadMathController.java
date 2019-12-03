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
          (int) MainApplication.dataHub.getSetting(DataHub.STORESC_TIME) - 2)
          + MainApplication.dataHub.getAverageHourlySales("Total",
              (int) MainApplication.dataHub.getSetting(DataHub.STORESC_TIME) - 1);
      tilSC.setText("" + projTilSC);
      
      double projB = (haveB + haveP) - projTilSC;
      projBaked.setText("" + projB);

      double reqB = MainApplication.dataHub
          .getPercentageDataForIndex(JimmyCalendarUtil.getCurrentPMShift() - 1);
      double proj = MainApplication.dataHub
          .getProjectionDataForIndex(JimmyCalendarUtil.getCurrentPMShift() - 1);
      pm.setText("" + proj);
      double reqT = proj - reqB;
      reqBaked.setText(reqB + "");
      reqThawed.setText(reqT + "");
      reqSales.setText(proj + "");

      double deltBaked = reqB - projB;
      toBake.setText(deltBaked > 0 ? deltBaked + "" : "0");

      double deltThawed = reqT - haveT;
      toThaw.setText(deltThawed > 0 ? deltThawed + "" : "0");
    }
    catch (Exception e)
    {
      e.printStackTrace();
      ErrorHandler.addError(e);
    }
  }

}
