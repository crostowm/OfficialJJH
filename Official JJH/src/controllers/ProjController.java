package controllers;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import readers.WSRMap;

public class ProjController
{
  @FXML
  private TextField a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14;
  
  @FXML
  private TextField a21, a22, a23, a24, a25, a26, a27, a28, a29, a210, a211, a212, a213, a214;

  public void initialize()
  {
    System.out.println(new File(".").getAbsolutePath());
    WSRMap w1 = new WSRMap("src/resources/WeeklySalesRS08-crostowm.csv");
    WSRMap w2 = new WSRMap("src/resources/WeeklySalesRS08-crostowm (1).csv");
    WSRMap w3 = new WSRMap("src/resources/WeeklySalesRS08-crostowm (2).csv");
    WSRMap w4 = new WSRMap("src/resources/WeeklySalesRS08-crostowm (3).csv");
    double avg1 = (w1.getDataForShift(WSRMap.ROYALTY_SALES, 1) + w2.getDataForShift(WSRMap.ROYALTY_SALES, 1) + w3.getDataForShift(WSRMap.ROYALTY_SALES, 1) + w4.getDataForShift(WSRMap.ROYALTY_SALES, 1)) / 4;
    a1.setText(avg1 + "");
    a21.setText((avg1 * 1.2) + "");
  }
}
