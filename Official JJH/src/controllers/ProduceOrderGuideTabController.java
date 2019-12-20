package controllers;

import java.util.ArrayList;

import app.MainApplication;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import observers.DataObserver;
import readers.UPKMap;
import util.CateringOrder;
import util.DataHub;
import util.JimmyCalendarUtil;

public class ProduceOrderGuideTabController implements DataObserver
{
  @FXML
  private Label produceOrderGuideSproutMin;

  @FXML
  private Label produceOrderGuideCeleryMin;

  @FXML
  private Label produceOrderGuideOnionMin;

  @FXML
  private Label produceOrderGuideTomatoMin;

  @FXML
  private Label produceOrderGuideLettuceMin;

  @FXML
  private TextField projField;

  @FXML
  private TextField uLettuce;

  @FXML
  private TextField toSprouts;

  @FXML
  private TextField ohSprouts;

  @FXML
  private TextField uSprouts;

  @FXML
  private TextField toCelery;

  @FXML
  private TextField ohCelery;

  @FXML
  private TextField toOnions;

  @FXML
  private TextField ohOnions;

  @FXML
  private TextField uCelery;

  @FXML
  private TextField toTomatoes;

  @FXML
  private TextField ohTomatoes;

  @FXML
  private TextField toLettuce;

  @FXML
  private TextField ohLettuce;

  @FXML
  private TextField uOnions;

  @FXML
  private TextField uTomatoes;

  @FXML
  private TextField toCucumbers;

  @FXML
  private TextField ohCucumbers;

  @FXML
  private TextField uCucumbers;

  @FXML
  private TextField usCucumbers;

  @FXML
  private TextField usSprouts;

  @FXML
  private TextField usCelery;

  @FXML
  private TextField usOnions;

  @FXML
  private TextField usTomatoes;

  @FXML
  private TextField usLettuce;

  @FXML
  private ChoiceBox<String> orderingOnChoice;

  @FXML
  private ChoiceBox<String> orderNextOn;

  private int firstShiftProduceWillBeUsed = 5, lastShiftProduceWillBeNeededFor = 8;

  public void initialize()
  {
    ArrayList<String> daysOfTheWeek = new ArrayList<String>();
    daysOfTheWeek.add("Sunday");
    daysOfTheWeek.add("Monday");
    daysOfTheWeek.add("Tuesday");
    daysOfTheWeek.add("Wednesday");
    daysOfTheWeek.add("Thursday");
    daysOfTheWeek.add("Friday");
    daysOfTheWeek.add("Saturday");
    orderingOnChoice.setItems(FXCollections.observableArrayList(daysOfTheWeek));
    orderingOnChoice.setValue("Wednesday");
    orderingOnChoice.setOnAction(new EventHandler<ActionEvent>()
    {
      @Override
      public void handle(ActionEvent arg0)
      {
        firstShiftProduceWillBeUsed = JimmyCalendarUtil
            .convertToShiftNumber(JimmyCalendarUtil.getAMShiftFor(orderingOnChoice.getValue()) + 4);
        if (orderingOnChoice.getValue() != null && orderNextOn.getValue() != null)
          updateAllFields();
      }
    });
    orderNextOn.setItems(FXCollections.observableArrayList(daysOfTheWeek));
    orderNextOn.setValue("Friday");
    orderNextOn.setOnAction(new EventHandler<ActionEvent>()
    {
      @Override
      public void handle(ActionEvent arg0)
      {
        lastShiftProduceWillBeNeededFor = JimmyCalendarUtil
            .convertToShiftNumber(JimmyCalendarUtil.getAMShiftFor(orderNextOn.getValue()) + 3);
        if (orderingOnChoice.getValue() != null && orderNextOn.getValue() != null)
          updateAllFields();
      }
    });

    ohLettuce.setOnKeyTyped(new EventHandler<KeyEvent>()
    {
      @Override
      public void handle(KeyEvent arg0)
      {
        updateAllFields();
      }
    });

    ohTomatoes.setOnKeyTyped(new EventHandler<KeyEvent>()
    {
      @Override
      public void handle(KeyEvent arg0)
      {
        updateAllFields();
      }
    });

    ohOnions.setOnKeyTyped(new EventHandler<KeyEvent>()
    {
      @Override
      public void handle(KeyEvent arg0)
      {
        updateAllFields();
      }
    });

    ohCelery.setOnKeyTyped(new EventHandler<KeyEvent>()
    {
      @Override
      public void handle(KeyEvent arg0)
      {
        updateAllFields();
      }
    });

    ohSprouts.setOnKeyTyped(new EventHandler<KeyEvent>()
    {
      @Override
      public void handle(KeyEvent arg0)
      {
        updateAllFields();
      }
    });

    ohCucumbers.setOnKeyTyped(new EventHandler<KeyEvent>()
    {
      @Override
      public void handle(KeyEvent arg0)
      {
        updateAllFields();
      }
    });
    System.out.println("POGTC");
  }

  public void updateAllFields()
  {
    // Minimums
    int nextAMShift = JimmyCalendarUtil.getNextAMShift();
    produceOrderGuideLettuceMin
        .setText(MainApplication.dataHub.getProduceRequiredForShifts(nextAMShift,
            JimmyCalendarUtil.convertToShiftNumber(nextAMShift + 3), "Lettuce", 24) + "");
    produceOrderGuideTomatoMin
        .setText(MainApplication.dataHub.getProduceRequiredForShifts(nextAMShift,
            JimmyCalendarUtil.convertToShiftNumber(nextAMShift + 3), "Tomatoes", 25) + "");
    produceOrderGuideOnionMin
        .setText(MainApplication.dataHub.getProduceRequiredForShifts(nextAMShift,
            JimmyCalendarUtil.convertToShiftNumber(nextAMShift + 3), "Onions", 50) + "");
    produceOrderGuideCeleryMin
        .setText(MainApplication.dataHub.getProduceRequiredForShifts(nextAMShift,
            JimmyCalendarUtil.convertToShiftNumber(nextAMShift + 3), "Celery", 1) + "");
    produceOrderGuideSproutMin
        .setText(MainApplication.dataHub.getProduceRequiredForShifts(nextAMShift,
            JimmyCalendarUtil.convertToShiftNumber(nextAMShift + 3), "Sprouts", 1) + "");

    // UPKs
    double lupk = MainApplication.dataHub.getCurrentUPKMap().getData(UPKMap.PRODUCE, "Lettuce",
        UPKMap.AVERAGE_UPK);
    uLettuce.setText(String.format("%.2f", lupk));

    double tupk = MainApplication.dataHub.getCurrentUPKMap().getData(UPKMap.PRODUCE, "Tomatoes",
        UPKMap.AVERAGE_UPK);
    uTomatoes.setText(String.format("%.2f", tupk));

    double oupk = MainApplication.dataHub.getCurrentUPKMap().getData(UPKMap.PRODUCE, "Onions",
        UPKMap.AVERAGE_UPK);
    uOnions.setText(String.format("%.2f", oupk));

    double ceupk = MainApplication.dataHub.getCurrentUPKMap().getData(UPKMap.PRODUCE, "Celery",
        UPKMap.AVERAGE_UPK);
    uCelery.setText(String.format("%.2f", ceupk));

    double supk = MainApplication.dataHub.getSetting(DataHub.SPROUT_UPK);
    uSprouts.setText(String.format("%.2f", supk));

    double cuupk = MainApplication.dataHub.getCurrentUPKMap().getData(UPKMap.PRODUCE, "Cucumbers",
        UPKMap.AVERAGE_UPK);
    uCucumbers.setText(String.format("%.2f", cuupk));
    // Proj
    double projections = MainApplication.dataHub
        .getProjectionsForShifts(firstShiftProduceWillBeUsed, lastShiftProduceWillBeNeededFor);
    projField.setText(projections + "");

    // Usage
    double usl = (projections / 1000) * lupk;
    usLettuce.setText(String.format("%.2f", usl));

    double ust = (projections / 1000) * tupk;
    usTomatoes.setText(String.format("%.2f", ust));

    double uso = (projections / 1000) * oupk;
    usOnions.setText(String.format("%.2f", uso));

    double usce = (projections / 1000) * ceupk;
    usCelery.setText(String.format("%.2f", usce));

    double uss = (projections / 1000) * supk;
    usSprouts.setText(String.format("%.2f", uss));

    double uscu = (projections / 1000) * cuupk;
    usCucumbers.setText(String.format("%.2f", uscu));

    if (!ohLettuce.getText().equals(""))
    {
      toLettuce.setTooltip(
          new Tooltip(String.format("%.2f", (usl - Double.parseDouble(ohLettuce.getText())) / 24)));
      toLettuce.setText(String.format("%.0f",
          Math.max(Math.ceil((usl - Double.parseDouble(ohLettuce.getText())) / 24), 0.0)));
    }

    if (!ohTomatoes.getText().equals(""))
    {
      toTomatoes.setTooltip(new Tooltip(
          String.format("%.2f", (ust - Double.parseDouble(ohTomatoes.getText())) / 25)));
      toTomatoes.setText(String.format("%.0f",
          Math.max(Math.ceil((ust - Double.parseDouble(ohTomatoes.getText())) / 25), 0.0)));
    }

    if (!ohOnions.getText().equals(""))
    {
      toOnions.setTooltip(
          new Tooltip(String.format("%.2f", (uso - Double.parseDouble(ohOnions.getText())) / 50)));
      toOnions.setText(String.format("%.0f",
          Math.max(Math.ceil((uso - Double.parseDouble(ohOnions.getText())) / 50), 0.0)));
    }

    if (!ohCelery.getText().equals(""))
    {
      toCelery.setTooltip(
          new Tooltip(String.format("%.2f", usce - Double.parseDouble(ohCelery.getText()))));
      toCelery.setText(String.format("%.0f",
          Math.max(Math.ceil(usce - Double.parseDouble(ohCelery.getText())), 0.0)));
    }

    if (!ohSprouts.getText().equals(""))
    {
      toSprouts.setTooltip(
          new Tooltip(String.format("%.2f", uss - Double.parseDouble(ohSprouts.getText()))));
      toSprouts.setText(String.format("%.0f",
          Math.max(Math.ceil(uss - Double.parseDouble(ohSprouts.getText())), 0.0)));
    }

    if (!ohCucumbers.getText().equals(""))
    {
      toCucumbers.setTooltip(
          new Tooltip(String.format("%.2f", uscu - Double.parseDouble(ohCucumbers.getText()))));
      toCucumbers.setText(String.format("%.0f",
          Math.max(Math.ceil(uscu - Double.parseDouble(ohCucumbers.getText())), 0.0)));
    }
  }

  @Override
  public void cateringOrderAdded(CateringOrder co)
  {
  }

  @Override
  public void cateringOrderRemoved(CateringOrder co)
  {
  }

  @Override
  public void toolBoxDataUpdated()
  {
    updateAllFields();
  }

}
