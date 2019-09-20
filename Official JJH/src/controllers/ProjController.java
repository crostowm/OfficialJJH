package controllers;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import readers.WSRMap;

public class ProjController
{
  private ArrayList<TextField> averageFields, average20Fields;
  
  @FXML
  private TextField a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14;
  
  @FXML
  private TextField a21, a22, a23, a24, a25, a26, a27, a28, a29, a210, a211, a212, a213, a214;

  public void initialize()
  {
    averageFields = new ArrayList<TextField>();
    average20Fields = new ArrayList<TextField>();
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
    fillProjectionTextFields();
  }

  private void fillProjectionTextFields()
  {
    WSRMap w1 = new WSRMap("src/resources/WeeklySalesRS08-crostowm.csv");
    WSRMap w2 = new WSRMap("src/resources/WeeklySalesRS08-crostowm (1).csv");
    WSRMap w3 = new WSRMap("src/resources/WeeklySalesRS08-crostowm (2).csv");
    WSRMap w4 = new WSRMap("src/resources/WeeklySalesRS08-crostowm (3).csv");
    for(int ii = 0; ii < averageFields.size(); ii++)
    {
      double avg = (w1.getDataForShift(WSRMap.ROYALTY_SALES, ii+1) + w2.getDataForShift(WSRMap.ROYALTY_SALES, ii+1) + w3.getDataForShift(WSRMap.ROYALTY_SALES, ii+1) + w4.getDataForShift(WSRMap.ROYALTY_SALES, ii+1)) / 4;
      averageFields.get(ii).setText(avg + "");
      average20Fields.get(ii).setText((avg * 1.2) + "");
    }
  }
}
