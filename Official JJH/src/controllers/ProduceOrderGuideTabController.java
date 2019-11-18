package controllers;

import app.MainApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import util.JimmyCalendarUtil;

public class ProduceOrderGuideTabController
{
  @FXML
  private Label produceOrderGuideLettuceMin, produceOrderGuideTomatoMin, produceOrderGuideOnionMin,
      produceOrderGuideCeleryMin, produceOrderGuideSproutMin;

  public void updateAllFields()
  {
    int nextAMShift = JimmyCalendarUtil.getNextAMShift();
    produceOrderGuideLettuceMin
        .setText(Math.ceil(MainApplication.dataHub.getProduceRequiredForShifts(nextAMShift,
            JimmyCalendarUtil.convertToShiftNumber(nextAMShift + 3), "Lettuce", 24)) + "");
    produceOrderGuideTomatoMin
        .setText(Math.ceil(MainApplication.dataHub.getProduceRequiredForShifts(nextAMShift,
            JimmyCalendarUtil.convertToShiftNumber(nextAMShift + 3), "Tomatoes", 25)) + "");
    produceOrderGuideOnionMin
        .setText(Math.ceil(MainApplication.dataHub.getProduceRequiredForShifts(nextAMShift,
            JimmyCalendarUtil.convertToShiftNumber(nextAMShift + 3), "Onions", 50)) + "");
    produceOrderGuideCeleryMin
        .setText(Math.ceil(MainApplication.dataHub.getProduceRequiredForShifts(nextAMShift,
            JimmyCalendarUtil.convertToShiftNumber(nextAMShift + 3), "Celery", 1)) + "");
    // produceOrderGuideSproutMin.setText(MainApplication.dataHub.getProduceRequiredForShifts(nextAMShift,
    // nextAMShift + 3, "Sprout", 1) + "");
  }
}
