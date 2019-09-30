package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import util.CateringOrder;

public class AreaManagerReportController
{
  @FXML
  private Label salesLabelAM, salesLabelPM, overUnderLabelAM, overUnderLabelPM, laborLabelAM,
      laborLabelPM, cateringTimeLabel, cateringDollarLabel;

  @FXML
  private CheckBox staffCheck, equipmentCheck, punchlistCheck;
  
  @FXML
  private ChoiceBox<CateringOrder> cateringChoice;
  
  @FXML
  private TextArea explanationArea;
  
  @FXML
  private Button sendReportButton;
  
  public void initialize()
  {
    
  }
}
