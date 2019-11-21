package controllers;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import app.MainApplication;
import error_handling.ErrorHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import observers.TimeObserver;
import util.CateringOrder;
import util.DataHub;
import util.JimmyCalendarUtil;
import util.MathUtil;

public class ProjectionTabController implements TimeObserver
{
  // ToolBox
  @FXML
  private TextField a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14;

  @FXML
  private TextField a21, a22, a23, a24, a25, a26, a27, a28, a29, a210, a211, a212, a213, a214;

  @FXML
  private TextField c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, c14;

  @FXML
  private TextField s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14;

  @FXML
  private TextField p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14;

  @FXML
  private TextField tt1, tt2, tt3, tt4, tt5, tt6, tt7, tt8, tt9, tt10, tt11, tt12, tt13, tt14;

  @FXML
  private TextField perc1, perc2, perc3, perc4, perc5, perc6, perc7, perc8, perc9, perc10, perc11,
      perc12, perc13, perc14;

  @FXML
  private TextField w1, w2, w3, w4, w5, w6, w7, w8, w9, w10, w11, w12, w13, w14;

  @FXML
  private TextField lettuceField, tomatoField, onionField, cucumberField, pickleField;

  @FXML
  private Label produceLabel;

  @FXML
  private Button addCateringButton;

  // Slicing Pars
  @FXML
  private TextField cheeseMSCField, hamMSCField, turkeyMSCField, beefMSCField, vitoMSCField,
      cheeseGECField, hamGECField, turkeyGECField, beefGECField, vitoGECField, cheeseMSNField,
      hamMSNField, turkeyMSNField, beefMSNField, vitoMSNField, cheeseGENField, hamGENField,
      turkeyGENField, beefGENField, vitoGENField;

  // Styles
  String basicTextFieldStyle = "-fx-background-color: white;-fx-border-color: black;"
      + "-fx-border-radius: 10 10 10 10;-fx-background-radius: 10 10 10 10;";

  private ArrayList<TextField> averageFields, average20Fields, cateringFields, samplingFields,
      projFields, thawedTrayFields, percentageFields, wheatFields;

  private int currentShift;

  public void initialize()
  {
    averageFields = new ArrayList<TextField>();
    averageFields.add(a1);
    averageFields.add(a2);
    averageFields.add(a3);
    averageFields.add(a4);
    averageFields.add(a5);
    averageFields.add(a6);
    averageFields.add(a7);
    averageFields.add(a8);
    averageFields.add(a9);
    averageFields.add(a10);
    averageFields.add(a11);
    averageFields.add(a12);
    averageFields.add(a13);
    averageFields.add(a14);

    average20Fields = new ArrayList<TextField>();
    average20Fields.add(a21);
    average20Fields.add(a22);
    average20Fields.add(a23);
    average20Fields.add(a24);
    average20Fields.add(a25);
    average20Fields.add(a26);
    average20Fields.add(a27);
    average20Fields.add(a28);
    average20Fields.add(a29);
    average20Fields.add(a210);
    average20Fields.add(a211);
    average20Fields.add(a212);
    average20Fields.add(a213);
    average20Fields.add(a214);

    cateringFields = new ArrayList<TextField>();
    cateringFields.add(c1);
    cateringFields.add(c2);
    cateringFields.add(c3);
    cateringFields.add(c4);
    cateringFields.add(c5);
    cateringFields.add(c6);
    cateringFields.add(c7);
    cateringFields.add(c8);
    cateringFields.add(c9);
    cateringFields.add(c10);
    cateringFields.add(c11);
    cateringFields.add(c12);
    cateringFields.add(c13);
    cateringFields.add(c14);

    samplingFields = new ArrayList<TextField>();
    samplingFields.add(s1);
    samplingFields.add(s2);
    samplingFields.add(s3);
    samplingFields.add(s4);
    samplingFields.add(s5);
    samplingFields.add(s6);
    samplingFields.add(s7);
    samplingFields.add(s8);
    samplingFields.add(s9);
    samplingFields.add(s10);
    samplingFields.add(s11);
    samplingFields.add(s12);
    samplingFields.add(s13);
    samplingFields.add(s14);

    projFields = new ArrayList<TextField>();
    projFields.add(p1);
    projFields.add(p2);
    projFields.add(p3);
    projFields.add(p4);
    projFields.add(p5);
    projFields.add(p6);
    projFields.add(p7);
    projFields.add(p8);
    projFields.add(p9);
    projFields.add(p10);
    projFields.add(p11);
    projFields.add(p12);
    projFields.add(p13);
    projFields.add(p14);

    thawedTrayFields = new ArrayList<TextField>();
    thawedTrayFields.add(tt1);
    thawedTrayFields.add(tt2);
    thawedTrayFields.add(tt3);
    thawedTrayFields.add(tt4);
    thawedTrayFields.add(tt5);
    thawedTrayFields.add(tt6);
    thawedTrayFields.add(tt7);
    thawedTrayFields.add(tt8);
    thawedTrayFields.add(tt9);
    thawedTrayFields.add(tt10);
    thawedTrayFields.add(tt11);
    thawedTrayFields.add(tt12);
    thawedTrayFields.add(tt13);
    thawedTrayFields.add(tt14);

    percentageFields = new ArrayList<TextField>();
    percentageFields.add(perc1);
    percentageFields.add(perc2);
    percentageFields.add(perc3);
    percentageFields.add(perc4);
    percentageFields.add(perc5);
    percentageFields.add(perc6);
    percentageFields.add(perc7);
    percentageFields.add(perc8);
    percentageFields.add(perc9);
    percentageFields.add(perc10);
    percentageFields.add(perc11);
    percentageFields.add(perc12);
    percentageFields.add(perc13);
    percentageFields.add(perc14);

    wheatFields = new ArrayList<TextField>();
    wheatFields.add(w1);
    wheatFields.add(w2);
    wheatFields.add(w3);
    wheatFields.add(w4);
    wheatFields.add(w5);
    wheatFields.add(w6);
    wheatFields.add(w7);
    wheatFields.add(w8);
    wheatFields.add(w9);
    wheatFields.add(w10);
    wheatFields.add(w11);
    wheatFields.add(w12);
    wheatFields.add(w13);
    wheatFields.add(w14);

    setStyles();

  }

  public void updateAllFields()
  {
    // Update Toolbox
    for (int ii = 0; ii < 14; ii++)
    {
      try
      {
        averageFields.get(ii)
            .setText(String.format("%.0f", MainApplication.dataHub.getAverageDataForIndex(ii)));
        averageFields.get(ii).setTooltip(
            new Tooltip(String.format("%.2f", MainApplication.dataHub.getAverageDataForIndex(ii))));
        average20Fields.get(ii)
            .setText(String.format("%.0f", MainApplication.dataHub.getAveragePlusBufferData(ii)));
        average20Fields.get(ii).setTooltip(new Tooltip(
            String.format("%.2f", MainApplication.dataHub.getAveragePlusBufferData(ii))));
        projFields.get(ii)
            .setText(String.format("%.2f", MainApplication.dataHub.getProjectionDataForIndex(ii)));

        double btv = MainApplication.dataHub.getSetting(DataHub.BTV);
        double b9tv = MainApplication.dataHub.getSetting(DataHub.B9TV);
        double wlv = MainApplication.dataHub.getSetting(DataHub.WLV);
        // AM Shifts (Index 0)
        if (ii % 2 == 0)
        {
          // Thawed at Open
          thawedTrayFields.get(ii).setText(String.format("%.1f",
              MathUtil.ceilHalf(MainApplication.dataHub.getThawedDataForIndex(ii) / btv)));
          thawedTrayFields.get(ii)
              .setTooltip(new Tooltip(
                  String.format("%.2f/%.2f", MainApplication.dataHub.getThawedDataForIndex(ii),
                      MainApplication.dataHub.getThawedDataForIndex(ii) / btv)));
          // Baked 75% @ 11
          percentageFields.get(ii).setText(String.format("%.1f",
              MathUtil.ceilHalf(MainApplication.dataHub.getPercentageDataForIndex(ii) / btv)));
          percentageFields.get(ii)
              .setTooltip(new Tooltip(
                  String.format("%.2f/%.2f", MainApplication.dataHub.getPercentageDataForIndex(ii),
                      MainApplication.dataHub.getPercentageDataForIndex(ii) / btv)));

          // AM Wheat Field, All for day
          wheatFields.get(ii).setText(String.format("%.0f",
              Math.ceil(MainApplication.dataHub.getWheatDataForIndex(ii) / wlv)));
          wheatFields.get(ii).setTooltip(new Tooltip(
              String.format("%.2f", MainApplication.dataHub.getWheatDataForIndex(ii) / wlv)));
        }
        // PM Shifts
        else
        {
          // Laid out at 8am
          thawedTrayFields.get(ii).setText(String.format("%.1f",
              MathUtil.ceilHalf(MainApplication.dataHub.getThawedDataForIndex(ii) / btv)));
          thawedTrayFields.get(ii)
              .setTooltip(new Tooltip(
                  String.format("%.2f/%.2f", MainApplication.dataHub.getThawedDataForIndex(ii),
                      MainApplication.dataHub.getThawedDataForIndex(ii) / btv)));

          // PM percentage fields, Baked at SC
          percentageFields.get(ii).setText(String.format("%.1f",
              MathUtil.ceilHalf(MainApplication.dataHub.getPercentageDataForIndex(ii) / b9tv)));
          percentageFields.get(ii)
              .setTooltip(new Tooltip(
                  String.format("%.2f/%.2f", MainApplication.dataHub.getPercentageDataForIndex(ii),
                      MainApplication.dataHub.getPercentageDataForIndex(ii) / b9tv)));

          // PM Wheat Field, Needed for PM
          wheatFields.get(ii).setText(String.format("%.0f",
              Math.ceil(MainApplication.dataHub.getWheatDataForIndex(ii) / wlv)));
          wheatFields.get(ii).setTooltip(new Tooltip(
              String.format("%.2f", MainApplication.dataHub.getWheatDataForIndex(ii) / wlv)));

        }

        // Catering Fields
        cateringFields.get(ii).setText("");
        for (CateringOrder co : MainApplication.dataHub.getCateringOrders())
        {
          if (JimmyCalendarUtil.getShiftNumber(co.getTime(), (int)MainApplication.dataHub.getSetting(DataHub.STORESC_TIME)) == ii + 1)
          {
            double currentCat = 0;
            if (cateringFields.get(ii).getText().length() > 0)
              currentCat = Double.parseDouble(cateringFields.get(ii).getText());
            System.out.println(currentCat + " " + co.getDollarValue());
            cateringFields.get(ii).setText(currentCat + co.getDollarValue() + "");
          }
        }
        timeUpdateMinute();

        // Fill produce fields
        double produceProj;
        if (currentShift % 2 == 1)
          produceProj = MainApplication.dataHub.getProjectionDataForIndex(currentShift - 1)
              + MainApplication.dataHub.getProjectionDataForIndex(currentShift);
        else
          produceProj = MainApplication.dataHub.getProjectionDataForIndex(currentShift - 1);
        lettuceField.setText(String.format("%.1f", MathUtil
            .ceilHalf(produceProj / MainApplication.dataHub.getSetting(DataHub.LETTUCEBV))));
        lettuceField.setTooltip(new Tooltip(String.format("%.2f",
            produceProj / MainApplication.dataHub.getSetting(DataHub.LETTUCEBV))));
        tomatoField.setText(String.format("%.1f",
            MathUtil.ceilHalf(produceProj / MainApplication.dataHub.getSetting(DataHub.TOMATOBV))));
        tomatoField.setTooltip(new Tooltip(String.format("%.2f",
            produceProj / MainApplication.dataHub.getSetting(DataHub.TOMATOBV))));
        onionField.setText(String.format("%.1f",
            MathUtil.ceilHalf(produceProj / MainApplication.dataHub.getSetting(DataHub.ONIONBV))));
        onionField.setTooltip(new Tooltip(String.format("%.2f",
            produceProj / MainApplication.dataHub.getSetting(DataHub.ONIONBV))));
        cucumberField.setText(String.format("%.1f", MathUtil
            .ceilHalf(produceProj / MainApplication.dataHub.getSetting(DataHub.CUCUMBERBV))));
        cucumberField.setTooltip(new Tooltip(String.format("%.2f",
            produceProj / MainApplication.dataHub.getSetting(DataHub.CUCUMBERBV))));
        pickleField.setText(String.format("%.1f",
            MathUtil.ceilHalf(produceProj / MainApplication.dataHub.getSetting(DataHub.PICKLEBV))));
        pickleField.setTooltip(new Tooltip(String.format("%.2f",
            produceProj / MainApplication.dataHub.getSetting(DataHub.PICKLEBV))));

        // Slicing Pars
        cheeseMSCField.setText(
            MathUtil.ceilHalf(MainApplication.dataHub.getSlicingPars("Cheese", "msc", currentShift))
                + "");
        cheeseGECField.setText(
            MathUtil.ceilHalf(MainApplication.dataHub.getSlicingPars("Cheese", "gec", currentShift))
                + "");
        cheeseMSNField.setText(
            MathUtil.ceilHalf(MainApplication.dataHub.getSlicingPars("Cheese", "msn", currentShift))
                + "");
        cheeseGENField.setText(
            MathUtil.ceilHalf(MainApplication.dataHub.getSlicingPars("Cheese", "gen", currentShift))
                + "");
        hamMSCField.setText(
            MathUtil.ceilHalf(MainApplication.dataHub.getSlicingPars("Ham", "msc", currentShift))
                + "");
        hamGECField.setText(
            MathUtil.ceilHalf(MainApplication.dataHub.getSlicingPars("Ham", "gec", currentShift))
                + "");
        hamMSNField.setText(
            MathUtil.ceilHalf(MainApplication.dataHub.getSlicingPars("Ham", "msn", currentShift))
                + "");
        hamGENField.setText(
            MathUtil.ceilHalf(MainApplication.dataHub.getSlicingPars("Ham", "gen", currentShift))
                + "");
        turkeyMSCField.setText(
            MathUtil.ceilHalf(MainApplication.dataHub.getSlicingPars("Turkey", "msc", currentShift))
                + "");
        turkeyGECField.setText(
            MathUtil.ceilHalf(MainApplication.dataHub.getSlicingPars("Turkey", "gec", currentShift))
                + "");
        turkeyMSNField.setText(
            MathUtil.ceilHalf(MainApplication.dataHub.getSlicingPars("Turkey", "msn", currentShift))
                + "");
        turkeyGENField.setText(
            MathUtil.ceilHalf(MainApplication.dataHub.getSlicingPars("Turkey", "gen", currentShift))
                + "");
        beefMSCField.setText(
            MathUtil.ceilHalf(MainApplication.dataHub.getSlicingPars("Beef", "msc", currentShift))
                + "");
        beefGECField.setText(
            MathUtil.ceilHalf(MainApplication.dataHub.getSlicingPars("Beef", "gec", currentShift))
                + "");
        beefMSNField.setText(
            MathUtil.ceilHalf(MainApplication.dataHub.getSlicingPars("Beef", "msn", currentShift))
                + "");
        beefGENField.setText(
            MathUtil.ceilHalf(MainApplication.dataHub.getSlicingPars("Beef", "gen", currentShift))
                + "");
        vitoMSCField.setText(
            (MathUtil.ceilHalf(MainApplication.dataHub.getSlicingPars("Salami", "msc", currentShift)
                + MainApplication.dataHub.getSlicingPars("Capicola", "msc", currentShift))) + "");
        vitoGECField.setText(
            (MathUtil.ceilHalf(MainApplication.dataHub.getSlicingPars("Salami", "gec", currentShift)
                + MainApplication.dataHub.getSlicingPars("Capicola", "gec", currentShift))) + "");
        vitoMSNField.setText(
            (MathUtil.ceilHalf(MainApplication.dataHub.getSlicingPars("Salami", "msn", currentShift)
                + MainApplication.dataHub.getSlicingPars("Capicola", "msn", currentShift))) + "");
        vitoGENField.setText(
            (MathUtil.ceilHalf(MainApplication.dataHub.getSlicingPars("Salami", "gen", currentShift)
                + MainApplication.dataHub.getSlicingPars("Capicola", "gen", currentShift))) + "");
      }
      catch (NumberFormatException nfe)
      {
        System.out
            .println("NFE, Could not parse a text field on Projections tab:\n" + nfe.getMessage());
        ErrorHandler.addError(nfe);
      }
    }
  }

  public void timeUpdateMinute()
  {
    currentShift = JimmyCalendarUtil.getShiftNumber(new GregorianCalendar(), (int)MainApplication.dataHub.getSetting(DataHub.STORESC_TIME));
    colorCurrentShiftFields();
  }

  @Override
  public void shiftChangeTo(int ampm)
  {
    if (ampm % 2 == 1)
    {
      produceLabel.setText("Produce to Prep");
    }
    else
    {
      produceLabel.setText("Produce required for PM");
    }
  }
  
  private void setStyles()
  {
    // TODO Auto-generated method stub
    for (int ii = 0; ii < 14; ii++)
    {
    }
    cheeseMSCField.setStyle(basicTextFieldStyle);
    cheeseGECField.setStyle(basicTextFieldStyle);
    cheeseMSNField.setStyle(basicTextFieldStyle);
    cheeseGENField.setStyle(basicTextFieldStyle);
    hamMSCField.setStyle(basicTextFieldStyle);
    hamGECField.setStyle(basicTextFieldStyle);
    hamMSNField.setStyle(basicTextFieldStyle);
    hamGENField.setStyle(basicTextFieldStyle);
    turkeyMSCField.setStyle(basicTextFieldStyle);
    turkeyGECField.setStyle(basicTextFieldStyle);
    turkeyMSNField.setStyle(basicTextFieldStyle);
    turkeyGENField.setStyle(basicTextFieldStyle);
    beefMSCField.setStyle(basicTextFieldStyle);
    beefGECField.setStyle(basicTextFieldStyle);
    beefMSNField.setStyle(basicTextFieldStyle);
    beefGENField.setStyle(basicTextFieldStyle);
    vitoMSCField.setStyle(basicTextFieldStyle);
    vitoGECField.setStyle(basicTextFieldStyle);
    vitoMSNField.setStyle(basicTextFieldStyle);
    vitoGENField.setStyle(basicTextFieldStyle);

    lettuceField.setStyle(basicTextFieldStyle);
    tomatoField.setStyle(basicTextFieldStyle);
    onionField.setStyle(basicTextFieldStyle);
    cucumberField.setStyle(basicTextFieldStyle);
    pickleField.setStyle(basicTextFieldStyle);
  }

  private void colorCurrentShiftFields()
  {
    for (int ii = 0; ii < 14; ii++)
    {
      String style = basicTextFieldStyle;
      if (ii == currentShift - 1)
      {
        style = "-fx-border-color: black;-fx-background-color: lime;-fx-border-radius: 10 10 0 0;-fx-background-radius: 10 10 0 0;";
      }
      averageFields.get(ii).setStyle(style);
      average20Fields.get(ii).setStyle(style);
      cateringFields.get(ii).setStyle(style);
      samplingFields.get(ii).setStyle(style);
      projFields.get(ii).setStyle(style);
      thawedTrayFields.get(ii).setStyle(style);
      percentageFields.get(ii).setStyle(style);
      wheatFields.get(ii).setStyle(style);
    }
  }

  // ToolBox
  @FXML
  public void s1Changed()
  {
    try
    {
      if (!s1.getText().equals(""))
        MainApplication.dataHub.setSamplingForShift(1, Integer.parseInt(s1.getText()));
      else
        MainApplication.dataHub.setSamplingForShift(1, 0);
    }
    catch (NumberFormatException nfe)
    {
      ErrorHandler.addError(nfe);
    }
  }

  @FXML
  public void s2Changed()
  {
    try
    {
      if (!s2.getText().equals(""))
        MainApplication.dataHub.setSamplingForShift(2, Integer.parseInt(s2.getText()));
      else
        MainApplication.dataHub.setSamplingForShift(2, 0);
    }
    catch (NumberFormatException nfe)
    {
      ErrorHandler.addError(nfe);
    }
  }

  @FXML
  public void s3Changed()
  {
    try
    {
      if (!s3.getText().equals(""))
        MainApplication.dataHub.setSamplingForShift(3, Integer.parseInt(s3.getText()));
      else
        MainApplication.dataHub.setSamplingForShift(3, 0);
    }
    catch (NumberFormatException nfe)
    {
      ErrorHandler.addError(nfe);
    }
  }

  @FXML
  public void s4Changed()
  {
    try
    {
      if (!s4.getText().equals(""))
        MainApplication.dataHub.setSamplingForShift(4, Integer.parseInt(s4.getText()));
      else
        MainApplication.dataHub.setSamplingForShift(4, 0);
    }
    catch (NumberFormatException nfe)
    {
      ErrorHandler.addError(nfe);
    }
  }

  @FXML
  public void s5Changed()
  {
    try
    {
      if (!s5.getText().equals(""))
        MainApplication.dataHub.setSamplingForShift(5, Integer.parseInt(s5.getText()));
      else
        MainApplication.dataHub.setSamplingForShift(5, 0);
    }
    catch (NumberFormatException nfe)
    {
      ErrorHandler.addError(nfe);
    }
  }

  @FXML
  public void s6Changed()
  {
    try
    {
      if (!s6.getText().equals(""))
        MainApplication.dataHub.setSamplingForShift(6, Integer.parseInt(s6.getText()));
      else
        MainApplication.dataHub.setSamplingForShift(1, 0);
    }
    catch (NumberFormatException nfe)
    {
      ErrorHandler.addError(nfe);
    }
  }

  @FXML
  public void s7Changed()
  {
    try
    {
      if (!s7.getText().equals(""))
        MainApplication.dataHub.setSamplingForShift(7, Integer.parseInt(s7.getText()));
      else
        MainApplication.dataHub.setSamplingForShift(1, 0);
    }
    catch (NumberFormatException nfe)
    {
      ErrorHandler.addError(nfe);
    }
  }

  @FXML
  public void s8Changed()
  {
    try
    {
      if (!s8.getText().equals(""))
        MainApplication.dataHub.setSamplingForShift(8, Integer.parseInt(s8.getText()));
      else
        MainApplication.dataHub.setSamplingForShift(1, 0);
    }
    catch (NumberFormatException nfe)
    {
      ErrorHandler.addError(nfe);
    }
  }

  @FXML
  public void s9Changed()
  {
    try
    {
      if (!s9.getText().equals(""))
        MainApplication.dataHub.setSamplingForShift(9, Integer.parseInt(s9.getText()));
      else
        MainApplication.dataHub.setSamplingForShift(9, 0);
    }
    catch (NumberFormatException nfe)
    {
      ErrorHandler.addError(nfe);
    }
  }

  @FXML
  public void s10Changed()
  {
    try
    {
      if (!s10.getText().equals(""))
        MainApplication.dataHub.setSamplingForShift(10, Integer.parseInt(s10.getText()));
      else
        MainApplication.dataHub.setSamplingForShift(10, 0);
    }
    catch (NumberFormatException nfe)
    {
      ErrorHandler.addError(nfe);
    }
  }

  @FXML
  public void s11Changed()
  {
    try
    {
      if (!s11.getText().equals(""))
        MainApplication.dataHub.setSamplingForShift(11, Integer.parseInt(s11.getText()));
      else
        MainApplication.dataHub.setSamplingForShift(11, 0);
    }
    catch (NumberFormatException nfe)
    {
      ErrorHandler.addError(nfe);
    }
  }

  @FXML
  public void s12Changed()
  {
    try
    {
      if (!s12.getText().equals(""))
        MainApplication.dataHub.setSamplingForShift(12, Integer.parseInt(s12.getText()));
      else
        MainApplication.dataHub.setSamplingForShift(12, 0);
    }
    catch (NumberFormatException nfe)
    {
      ErrorHandler.addError(nfe);
    }
  }

  @FXML
  public void s13Changed()
  {
    try
    {
      if (!s13.getText().equals(""))
        MainApplication.dataHub.setSamplingForShift(13, Integer.parseInt(s13.getText()));
      else
        MainApplication.dataHub.setSamplingForShift(13, 0);
    }
    catch (NumberFormatException nfe)
    {
      ErrorHandler.addError(nfe);
    }
  }

  @FXML
  public void s14Changed()
  {
    try
    {
      if (!s14.getText().equals(""))
        MainApplication.dataHub.setSamplingForShift(14, Integer.parseInt(s14.getText()));
      else
        MainApplication.dataHub.setSamplingForShift(14, 0);
    }
    catch (NumberFormatException nfe)
    {
      ErrorHandler.addError(nfe);
    }
  }

 //@formatter:off
 @FXML public void c1Changed(){updateAllFields();}
 
 @FXML public void c2Changed(){updateAllFields();}
 
 @FXML public void c3Changed(){updateAllFields();}
 
 @FXML public void c4Changed(){updateAllFields();}
 
 @FXML public void c5Changed(){updateAllFields();}
 
 @FXML public void c6Changed(){updateAllFields();}
 
 @FXML public void c7Changed(){updateAllFields();}
 
 @FXML public void c8Changed(){updateAllFields();}
 
 @FXML public void c9Changed(){updateAllFields();}
 
 @FXML public void c10Changed(){updateAllFields();}
 
 @FXML public void c11Changed(){updateAllFields();}
 
 @FXML public void c12Changed(){updateAllFields();}
 
 @FXML public void c13Changed(){updateAllFields();}
 
 @FXML public void c14Changed(){updateAllFields();}
 //@formatter:on
}
