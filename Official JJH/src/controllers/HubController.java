package controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import readers.WSRMap;

public class HubController
{
  private ArrayList<TextField> averageFields, average20Fields, cateringFields, samplingFields,
      projFields, thawedTrayField, percentageFields, wheatFields;
  
  private double currentShiftProjection = 0, currentDayProjection = 0, amBuffer, pmBuffer, btv, b9tv, wlv, bakedAt11, bakedAtSC, lettuceBV, tomatoBV, onionBV, cucumberBV, pickleBV;

  //ToolBox
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
  private Label produceLabel, clockLabel;
  //Settings
  @FXML
  private TextField amBufferField, pmBufferField, btvField, b9tvField, wlvField, bakedAt11Field, bakedAtSCField, lettuceBVField, tomatoBVField, onionBVField, cucumberBVField, pickleBVField;

  public void initialize()
  {
    //Read in Settings
    try
    {
      amBuffer = Double.parseDouble(amBufferField.getText());
      pmBuffer = Double.parseDouble(pmBufferField.getText());
      btv = Double.parseDouble(btvField.getText());
      b9tv = Double.parseDouble(b9tvField.getText());
      wlv = Double.parseDouble(wlvField.getText());
      bakedAt11 = Double.parseDouble(bakedAt11Field.getText());
      bakedAtSC = Double.parseDouble(bakedAtSCField.getText());
      lettuceBV = Double.parseDouble(lettuceBVField.getText());
      tomatoBV = Double.parseDouble(tomatoBVField.getText());
      onionBV = Double.parseDouble(onionBVField.getText());
      cucumberBV = Double.parseDouble(cucumberBVField.getText());
      pickleBV = Double.parseDouble(pickleBVField.getText());
    }
    catch(NumberFormatException nfe)
    {
      System.out.println("NFE, Could not parse Settings:\n" + nfe.getMessage());
    }
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
    fillAverageTextFields();

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

    thawedTrayField = new ArrayList<TextField>();
    thawedTrayField.add(tt1);
    thawedTrayField.add(tt2);
    thawedTrayField.add(tt3);
    thawedTrayField.add(tt4);
    thawedTrayField.add(tt5);
    thawedTrayField.add(tt6);
    thawedTrayField.add(tt7);
    thawedTrayField.add(tt8);
    thawedTrayField.add(tt9);
    thawedTrayField.add(tt10);
    thawedTrayField.add(tt11);
    thawedTrayField.add(tt12);
    thawedTrayField.add(tt13);
    thawedTrayField.add(tt14);

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
    updateAllFields();
  }

  /**
   * Adds up avg*1.2 + catering + sampling for each shift and updates proj fields am/pmbuffer Amount
   * baked at shift change 8am total pm - bakedsc - 25%am
   */
  private void updateAllFields()
  {
    double amProj = 0;
    //Update Settings
    try
    {
      amBuffer = Double.parseDouble(amBufferField.getText());
      pmBuffer = Double.parseDouble(pmBufferField.getText());
      btv = Double.parseDouble(btvField.getText());
      b9tv = Double.parseDouble(b9tvField.getText());
      wlv = Double.parseDouble(wlvField.getText());
      bakedAt11 = Double.parseDouble(bakedAt11Field.getText());
      bakedAtSC = Double.parseDouble(bakedAtSCField.getText());
      lettuceBV = Double.parseDouble(lettuceBVField.getText());
      tomatoBV = Double.parseDouble(tomatoBVField.getText());
      onionBV = Double.parseDouble(onionBVField.getText());
      cucumberBV = Double.parseDouble(cucumberBVField.getText());
      pickleBV = Double.parseDouble(pickleBVField.getText());
    }
    catch(NumberFormatException nfe)
    {
      System.out.println("NFE, Could not parse Settings:\n" + nfe.getMessage());
    }
    
    //Update Toolbox
    for (int ii = 0; ii < 14; ii++)
    {
      try
      {
        // Fill avg, catering, and sample vals
        double averagePlusBuffer = 0, catering = 0, sampling = 0;
        if (!average20Fields.get(ii).getText().equals(""))
        {
          averagePlusBuffer = Double.parseDouble(average20Fields.get(ii).getTooltip().getText());
        }
        if (!cateringFields.get(ii).getText().equals(""))
        {
          catering = Double.parseDouble(cateringFields.get(ii).getText());
        }
        if (!samplingFields.get(ii).getText().equals(""))
        {
          sampling = Double.parseDouble(samplingFields.get(ii).getText());
        }
        double currentProjection = averagePlusBuffer + catering + sampling;

        projFields.get(ii).setText(String.format("%.2f", currentProjection));
        // AM Shifts (Index 0)
        if (ii % 2 == 0)
        {
          amProj = currentProjection;
          // Thawed at Open
          thawedTrayField.get(ii)
              .setText(String.format("%.0f", Math.ceil(currentProjection / btv)));
          thawedTrayField.get(ii)
              .setTooltip(new Tooltip(String.format("%.2f", currentProjection / btv)));
          // Baked 75% @ 11
          percentageFields.get(ii)
              .setText(String.format("%.0f", Math.ceil((currentProjection * bakedAt11) / btv)));
          percentageFields.get(ii).setTooltip(new Tooltip(String.format("%.2f/%.2f",
              currentProjection * bakedAt11, (currentProjection * bakedAt11) / btv)));
        }
        // PM Shifts
        else
        {
          // Laid out at 8am
          thawedTrayField.get(ii)
              .setText(String.format("%.0f",
                  Math.ceil((currentProjection - (currentProjection * bakedAtSC) - (.25 * amProj)))
                      / btv));
          thawedTrayField.get(ii)
              .setTooltip(new Tooltip(String.format("%.2f/%.2f",
                  (currentProjection - (currentProjection * bakedAtSC) - (.25 * amProj)),
                  ((currentProjection - (currentProjection * bakedAtSC) - (.25 * amProj))) / btv)));

          // PM percentage fields, Baked at SC
          percentageFields.get(ii)
              .setText(String.format("%.0f", Math.ceil((currentProjection * bakedAtSC) / b9tv)));
          percentageFields.get(ii).setTooltip(new Tooltip(String.format("%.2f/%.2f",
              currentProjection * bakedAtSC, (currentProjection * bakedAtSC) / b9tv)));

          // AM Wheat Field, All for day
          wheatFields.get(ii - 1)
              .setText(String.format("%.0f", Math.ceil((amProj + currentProjection) / wlv)));
          wheatFields.get(ii - 1)
              .setTooltip(new Tooltip(String.format("%.2f", (amProj + currentProjection) / wlv)));

          // PM Wheat Field, Needed for PM
          wheatFields.get(ii).setText(String.format("%.0f", Math.ceil(currentProjection / wlv)));
          wheatFields.get(ii)
              .setTooltip(new Tooltip(String.format("%.2f", currentProjection / wlv)));

          amProj = 0;
        }
        //TODO
        //Fill produce fields
        //lettuceBVField.setText(String.format("%.0f", Math.ceil(a)))
      }
      catch (NumberFormatException nfe)
      {
        System.out.println("NFE, Could not parse a text field on Projections tab:\n" + nfe.getMessage());
      }
    }
  }

  /**
   * Pulls info from wsr to fill avgs
   */
  private void fillAverageTextFields()
  {
    // TODO pull data for amBuffer/pmBuffer
    WSRMap w1 = new WSRMap("src/resources/WeeklySalesRS08-crostowm.csv");
    WSRMap w2 = new WSRMap("src/resources/WeeklySalesRS08-crostowm (1).csv");
    WSRMap w3 = new WSRMap("src/resources/WeeklySalesRS08-crostowm (2).csv");
    WSRMap w4 = new WSRMap("src/resources/WeeklySalesRS08-crostowm (3).csv");
    for (int ii = 0; ii < 14; ii++)
    {
      double avg = (w1.getDataForShift(WSRMap.ROYALTY_SALES, ii + 1)
          + w2.getDataForShift(WSRMap.ROYALTY_SALES, ii + 1)
          + w3.getDataForShift(WSRMap.ROYALTY_SALES, ii + 1)
          + w4.getDataForShift(WSRMap.ROYALTY_SALES, ii + 1)) / 4;
      averageFields.get(ii).setText(String.format("%.0f", avg));
      averageFields.get(ii).setTooltip(new Tooltip(String.format("%.2f", avg)));
      if (ii % 2 == 0)
      {
        average20Fields.get(ii).setText(String.format("%.0f", avg * 1.2));
        average20Fields.get(ii).setTooltip(new Tooltip(String.format("%.2f", avg * amBuffer)));
      }
      else
      {
        average20Fields.get(ii).setText(String.format("%.0f", avg * 1.2));
        average20Fields.get(ii).setTooltip(new Tooltip(String.format("%.2f", avg * pmBuffer)));
      }
    }
  }
  
  public void timeUpdateSecond()
  {
    //TODO
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss MMM dd yyyy");
    GregorianCalendar cal = new GregorianCalendar();
    int currentShift = getCurrentShiftNum(cal);
    currentShiftProjection = Double.parseDouble(projFields.get(currentShift-1).getText());
    currentDayProjection = currentShiftProjection + Double.parseDouble(projFields.get(currentShift%2==0?currentShift-2:currentShift).getText());
    Platform.runLater(new Runnable() {

      @Override
      public void run()
      {
        clockLabel.setText(sdf.format(cal.getTime()));
      }
    });
    if(cal.get(Calendar.HOUR_OF_DAY) >= 1)
    {
      produceLabel.setText("Produce required for PM");
    }
  }
  
  public void timeUpdateMinute()
  {
    //TODO
  }

  private int getCurrentShiftNum(GregorianCalendar cal)
  {
    cal.get(Calendar.DAY_OF_WEEK);
    return 0;
  }

  //@formatter:off
  //ToolBox
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

  @FXML public void s1Changed(){updateAllFields();}

  @FXML public void s2Changed(){updateAllFields();}

  @FXML public void s3Changed(){updateAllFields();}

  @FXML public void s4Changed(){updateAllFields();}

  @FXML public void s5Changed(){updateAllFields();}

  @FXML public void s6Changed(){updateAllFields();}

  @FXML public void s7Changed(){updateAllFields();}

  @FXML public void s8Changed(){updateAllFields();}

  @FXML public void s9Changed(){updateAllFields();}

  @FXML public void s10Changed(){updateAllFields();}

  @FXML public void s11Changed(){updateAllFields();}

  @FXML public void s12Changed(){updateAllFields();}

  @FXML public void s13Changed(){updateAllFields();}

  @FXML public void s14Changed(){updateAllFields();}
  
  //Settings
  @FXML void amBufferFieldChanged() {updateAllFields();}

  @FXML void pmBufferFieldChanged() {updateAllFields();}

  @FXML void btvFieldChanged() {updateAllFields();}
  
  @FXML void b9tvFieldChanged() {updateAllFields();}
  
  @FXML void wlvFieldChanged() {updateAllFields();}

  @FXML void bakedAt11FieldChanged() {updateAllFields();}

  @FXML void bakedAtSCFieldChanged() {updateAllFields();}

  @FXML void lettuceBVChanged() {updateAllFields();}
  
  @FXML void tomatoBVChanged() {updateAllFields();}
  
  @FXML void onionBVChanged() {updateAllFields();}
  
  @FXML void cucumberBVChanged() {updateAllFields();}
  
  @FXML void pickleBVChanged() {updateAllFields();}
  //@formatter:on
}