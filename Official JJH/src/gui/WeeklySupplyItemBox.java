package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class WeeklySupplyItemBox extends HBox
{
  private String name;
  private CheckBox checkBox;
  private Spinner<Integer> count;

  public WeeklySupplyItemBox(String name)
  {
    super();
    this.name = name;
    setMaxWidth(240);
    setMinWidth(240);
    checkBox = new CheckBox(name);
    checkBox.setMinWidth(150);
    count = new Spinner<Integer>();
    count.setPrefWidth(50);
    count.setVisible(checkBox.isSelected());
    SpinnerValueFactory<Integer> blValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(
        1, 500, 1);
    count.setValueFactory(blValueFactory);
    checkBox.setOnAction(new EventHandler<ActionEvent>()
    {
      @Override
      public void handle(ActionEvent arg0)
      {
        count.setVisible(checkBox.isSelected());
      }
    });
    Region region = new Region();
    HBox.setHgrow(region, Priority.ALWAYS);
    getChildren().addAll(checkBox, region, count);
  }

  public boolean isNeeded()
  {
    return checkBox.isSelected();
  }

  public String getName()
  {
    return name;
  }

  public Integer getCount()
  {
    return count.getValue();
  }
}
