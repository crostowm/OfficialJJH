package gui;

import java.text.SimpleDateFormat;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import lineitems.AttendanceShift;

public class AttendanceShiftBox extends HBox
{

  public AttendanceShiftBox(AttendanceShift as)
  {
    setSpacing(5);
    Label name = new Label(as.getFirstName() + " " + as.getLastName());
    name.setMinWidth(120);
    name.setMaxWidth(120);
    Label position = new Label(as.getPosition());
    position.setMinWidth(95);
    position.setMaxWidth(95);
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
    Label start = new Label(sdf.format(as.getStart().getTime()));
    start.setMinWidth(130);
    start.setMaxWidth(130);
    Label end = new Label(sdf.format(as.getEnd().getTime()));
    end.setMinWidth(130);
    end.setMaxWidth(130);
    end.setPadding(new Insets(0, 15, 0, 0));
    Label payHours = new Label(as.getPayHours() + "");
    payHours.setMinWidth(55);
    payHours.setMaxWidth(55);
    Label payRate = new Label("$" + as.getPayRate());
    payRate.setMinWidth(55);
    payRate.setMaxWidth(55);
    Label estimatedWages = new Label("$" + as.getEstimatedWages());
    estimatedWages.setMinWidth(55);
    estimatedWages.setMaxWidth(55);
    Label cumulativeHours = new Label(as.getCumulativeHours() + "");
    cumulativeHours.setMinWidth(55);
    cumulativeHours.setMaxWidth(55);
    Label adjustmentReason = new Label(as.getAdjustmentReason());
    adjustmentReason.setMinWidth(Region.USE_PREF_SIZE);
    
    if(as.isValid())
      setStyle("-fx-background-color: rgba(0, 255, 0, .4);");
    else
      setStyle("-fx-background-color: rgba(255, 0, 0, .4);");
    getChildren().addAll(name, position, start, end, payHours, payRate, estimatedWages, cumulativeHours);
    if(as.isAdjusted())
      getChildren().add(adjustmentReason);
  }
  
}
