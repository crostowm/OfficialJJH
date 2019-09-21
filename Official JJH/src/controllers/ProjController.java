package controllers;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import readers.WSRMap;

public class ProjController
{
  private ArrayList<TextField> averageFields, average20Fields, cateringFields, samplingFields,
      projFields, totalTrayField, percentageFields;

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

    totalTrayField = new ArrayList<TextField>();
    totalTrayField.add(tt1);
    totalTrayField.add(tt2);
    totalTrayField.add(tt3);
    totalTrayField.add(tt4);
    totalTrayField.add(tt5);
    totalTrayField.add(tt6);
    totalTrayField.add(tt7);
    totalTrayField.add(tt8);
    totalTrayField.add(tt9);
    totalTrayField.add(tt10);
    totalTrayField.add(tt11);
    totalTrayField.add(tt12);
    totalTrayField.add(tt13);
    totalTrayField.add(tt14);
    
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
    updateProjFields();
  }

  /**
   * Adds up avg*1.2 + catering + sampling for each shift and updates proj fields
   */
  private void updateProjFields()
  {
    int btv = 200;
    int b9tv = 150;
    int wbtv = 900;
    for (int ii = 0; ii < 14; ii++)
    {
      try
      {
        double a = 0, c = 0, s = 0;
        if (!average20Fields.get(ii).getText().equals(""))
        {
          a = Double.parseDouble(average20Fields.get(ii).getText());
        }
        if (!cateringFields.get(ii).getText().equals(""))
        {
          c = Double.parseDouble(cateringFields.get(ii).getText());
        }
        if (!samplingFields.get(ii).getText().equals(""))
        {
          s = Double.parseDouble(samplingFields.get(ii).getText());
        }
        double proj = a + c + s;
        projFields.get(ii).setText(String.format("%.2f", proj));
        if (ii % 2 == 0)
        {
          totalTrayField.get(ii).setText(String.format("%.0f", Math.ceil(proj / btv)));
          totalTrayField.get(ii).setTooltip(new Tooltip(String.format("%.2f", proj / btv)));
          percentageFields.get(ii).setText(String.format("%.0f", (proj*.75)/btv));
          percentageFields.get(ii).setTooltip(new Tooltip(String.format("%.2f/%.2f", proj*.75, (proj*.75)/btv)));
        }
        else
        {
          percentageFields.get(ii).setText(String.format("%.0f", (proj*.5)/b9tv));
          percentageFields.get(ii).setTooltip(new Tooltip(String.format("%.2f/%.2f", proj*.5, (proj*.5)/b9tv)));
        }
      }
      catch (NumberFormatException nfe)
      {
        System.out.println("NFE");
      }
    }
  }

  /**
   * Pulls info from wsr to fill avgs
   */
  private void fillAverageTextFields()
  {
    WSRMap w1 = new WSRMap("src/resources/WeeklySalesRS08-crostowm.csv");
    WSRMap w2 = new WSRMap("src/resources/WeeklySalesRS08-crostowm (1).csv");
    WSRMap w3 = new WSRMap("src/resources/WeeklySalesRS08-crostowm (2).csv");
    WSRMap w4 = new WSRMap("src/resources/WeeklySalesRS08-crostowm (3).csv");
    for (int ii = 0; ii < averageFields.size(); ii++)
    {
      double avg = (w1.getDataForShift(WSRMap.ROYALTY_SALES, ii + 1)
          + w2.getDataForShift(WSRMap.ROYALTY_SALES, ii + 1)
          + w3.getDataForShift(WSRMap.ROYALTY_SALES, ii + 1)
          + w4.getDataForShift(WSRMap.ROYALTY_SALES, ii + 1)) / 4;
      averageFields.get(ii).setText(String.format("%.0f", avg));
      averageFields.get(ii).setTooltip(new Tooltip(String.format("%.2f", avg)));
      average20Fields.get(ii).setText(String.format("%.0f", avg * 1.2));
      average20Fields.get(ii).setTooltip(new Tooltip(String.format("%.2f", avg * 1.2)));
    }
  }

  //@formatter:off
  @FXML public void c1Changed(){updateProjFields();}

  @FXML public void c2Changed(){updateProjFields();}

  @FXML public void c3Changed(){updateProjFields();}

  @FXML public void c4Changed(){updateProjFields();}

  @FXML public void c5Changed(){updateProjFields();}

  @FXML public void c6Changed(){updateProjFields();}

  @FXML public void c7Changed(){updateProjFields();}

  @FXML public void c8Changed(){updateProjFields();}

  @FXML public void c9Changed(){updateProjFields();}

  @FXML public void c10Changed(){updateProjFields();}

  @FXML public void c11Changed(){updateProjFields();}

  @FXML public void c12Changed(){updateProjFields();}

  @FXML public void c13Changed(){updateProjFields();}

  @FXML public void c14Changed(){updateProjFields();}

  @FXML public void s1Changed(){updateProjFields();}

  @FXML public void s2Changed(){updateProjFields();}

  @FXML public void s3Changed(){updateProjFields();}

  @FXML public void s4Changed(){updateProjFields();}

  @FXML public void s5Changed(){updateProjFields();}

  @FXML public void s6Changed(){updateProjFields();}

  @FXML public void s7Changed(){updateProjFields();}

  @FXML public void s8Changed(){updateProjFields();}

  @FXML public void s9Changed(){updateProjFields();}

  @FXML public void s10Changed(){updateProjFields();}

  @FXML public void s11Changed(){updateProjFields();}

  @FXML public void s12Changed(){updateProjFields();}

  @FXML public void s13Changed(){updateProjFields();}

  @FXML public void s14Changed(){updateProjFields();}
  //@formatter:on
}
