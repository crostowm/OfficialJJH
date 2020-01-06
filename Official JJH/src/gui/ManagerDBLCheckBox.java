package gui;

import javafx.scene.control.CheckBox;
import util.ManagerDBL;

public class ManagerDBLCheckBox extends CheckBox
{
  private ManagerDBL dbl;

  public ManagerDBLCheckBox(ManagerDBL dbl)
  {
    this.dbl = dbl;
    setText(dbl.getDesc());
    setWrapText(true);
    setStyle("-fx-text-fill: white;");
  }
  
  public ManagerDBL getDBL()
  {
    return dbl;
  }
}
