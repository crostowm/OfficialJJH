package gui;

import java.text.DecimalFormat;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class DataPointNode extends StackPane
{
  private Label label;
  
  public DataPointNode(double data)
  {
    label = new Label(data + "");
    setupHover();
  }
  
  public DataPointNode(double data, double dataLarge)
  {
    label = new Label(data + ": " + new DecimalFormat("$#,###.00").format(dataLarge));
    setupHover();
  }
  
  private void setupHover()
  {
    label.getStyleClass().addAll("default-color0", "chart-line-symbol", "chart-series-line");
    label.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
    label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
    
    setOnMouseEntered(new EventHandler<MouseEvent>() {
      @Override public void handle(MouseEvent mouseEvent) {
        getChildren().setAll(label);
        setCursor(Cursor.NONE);
        toFront();
      }
    });
    setOnMouseExited(new EventHandler<MouseEvent>() {
      @Override public void handle(MouseEvent mouseEvent) {
        getChildren().clear();
        setCursor(Cursor.DEFAULT);
      }
    });
  }
  
}
