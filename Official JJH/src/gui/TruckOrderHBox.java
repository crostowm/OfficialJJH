package gui;

import java.util.HashMap;

import error_handling.ErrorHandler;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import readers.UPKMap;

public class TruckOrderHBox extends UPKHBox
{
  private Label name, unit, acUsage, avgUPK, projUseCs, projUseEa;
  private TextField onHandCs, onHandEa, toOrder;
  private HashMap<Integer, Double> data;
  private double projections;

  public TruckOrderHBox(int category, double adjustedSales, String n, HashMap<Integer, Double> data,
      String uni, double projSales)
  {
    super(category, adjustedSales, n, data);
    this.data = data;
    this.projections = projSales;
    System.out.println(n);
    setSpacing(10);
    name = new Label(n);
    name.setMinWidth(200);
    name.setMaxWidth(200);
    unit = new Label(uni);

    unit.setMinWidth(100);
    unit.setMaxWidth(100);
    unit.setAlignment(Pos.CENTER);

    acUsage = new Label(data.get(UPKMap.ACTUAL_USAGE) + "");
    acUsage.setMinWidth(90);
    acUsage.setMaxWidth(90);
    acUsage.setAlignment(Pos.CENTER);

    avgUPK = new Label(data.get(UPKMap.AVERAGE_UPK) + "");
    avgUPK.setMinWidth(90);
    avgUPK.setMaxWidth(90);
    avgUPK.setAlignment(Pos.CENTER);

    // Needs to be divided by case
    projUseCs = new Label(String.format("%.2f",
        (data.get(UPKMap.AVERAGE_UPK) * (projections / 1000)) / data.get(UPKMap.CASE_VALUE)));
    projUseCs.setMinWidth(90);
    projUseCs.setMaxWidth(90);
    projUseCs.setAlignment(Pos.CENTER);

    projUseEa = new Label(
        String.format("%.2f", data.get(UPKMap.AVERAGE_UPK) * (projections / 1000)));
    projUseEa.setMinWidth(90);
    projUseEa.setMaxWidth(90);
    projUseEa.setAlignment(Pos.CENTER);

    onHandCs = new TextField();
    onHandCs.setMinWidth(60);
    onHandCs.setMaxWidth(60);
    HBox.setMargin(onHandCs, new Insets(0, 15, 0, 15));
    onHandCs.setAlignment(Pos.CENTER);
    onHandCs.setOnKeyTyped(new EventHandler<KeyEvent>()
    {
      @Override
      public void handle(KeyEvent arg0)
      {
        try
        {
          if (!onHandCs.getText().equals(""))
          {
            onHandEa.setText(String.format("%.2f",
                Double.parseDouble(onHandCs.getText()) * data.get(UPKMap.CASE_VALUE)));
            toOrder.setTooltip(new Tooltip(String.format("%.2f",
                (Math.max(0,
                    ((data.get(UPKMap.AVERAGE_UPK) * (projections / 1000))
                        / data.get(UPKMap.CASE_VALUE))
                        - Double.parseDouble(onHandCs.getText()))))));
            toOrder.setText(String.format("%.2f",
                (Math.ceil(Math.max(0,
                    ((data.get(UPKMap.AVERAGE_UPK) * (projections / 1000))
                        / data.get(UPKMap.CASE_VALUE))
                        - Double.parseDouble(onHandCs.getText()))))));
          }
          else
          {
            onHandEa.setText("0");
            toOrder.setTooltip(new Tooltip(String.format("%.2f",
                (Math.max(0, (data.get(UPKMap.AVERAGE_UPK) * (projections / 1000))
                    / data.get(UPKMap.CASE_VALUE))))));
            toOrder.setText(String.format("%.2f",
                (Math.ceil(Math.max(0, (data.get(UPKMap.AVERAGE_UPK) * (projections / 1000))
                    / data.get(UPKMap.CASE_VALUE))))));
          }
        }
        catch (NumberFormatException nfe)
        {
          nfe.printStackTrace();
          ErrorHandler.addError(nfe);
        }
      }
    });

    onHandEa = new TextField();
    onHandEa.setMinWidth(60);
    onHandEa.setMaxWidth(60);
    HBox.setMargin(onHandEa, new Insets(0, 15, 0, 15));
    onHandEa.setAlignment(Pos.CENTER);
    onHandEa.setOnKeyTyped(new EventHandler<KeyEvent>()
    {
      @Override
      public void handle(KeyEvent arg0)
      {
        try
        {
          if (!onHandEa.getText().equals(""))
          {
            onHandCs.setText(String.format("%.2f",
                Double.parseDouble(onHandEa.getText()) / data.get(UPKMap.CASE_VALUE)));
            toOrder.setTooltip(new Tooltip(String.format("%.2f",
                (Math.max(0,
                    ((data.get(UPKMap.AVERAGE_UPK) * (projections / 1000))
                        / data.get(UPKMap.CASE_VALUE))
                        - Double.parseDouble(onHandCs.getText()))))));
            toOrder.setText(String.format("%.2f",
                (Math.ceil(Math.max(0,
                    ((data.get(UPKMap.AVERAGE_UPK) * (projections / 1000))
                        / data.get(UPKMap.CASE_VALUE))
                        - Double.parseDouble(onHandCs.getText()))))));
          }
          else
          {
            onHandCs.setText("0");
            toOrder.setTooltip(new Tooltip(String.format("%.2f",
                (Math.max(0, (data.get(UPKMap.AVERAGE_UPK) * (projections / 1000))
                    / data.get(UPKMap.CASE_VALUE))))));
            toOrder.setText(String.format("%.2f",
                (Math.ceil(Math.max(0, (data.get(UPKMap.AVERAGE_UPK) * (projections / 1000))
                    / data.get(UPKMap.CASE_VALUE))))));
          }
        }
        catch (NumberFormatException nfe)
        {
          nfe.printStackTrace();
          ErrorHandler.addError(nfe);
        }
      }
    });

    toOrder = new TextField();
    toOrder.setMinWidth(60);
    toOrder.setMaxWidth(60);
    HBox.setMargin(toOrder, new Insets(0, 15, 0, 15));
    toOrder.setAlignment(Pos.CENTER);
    toOrder.setEditable(false);

    getChildren().addAll(name, unit, acUsage, avgUPK, projUseCs, projUseEa, onHandCs, onHandEa,
        toOrder);
  }

  public void refreshProjections(double proj)
  {
    projections = proj;
    if (!onHandCs.getText().equals(""))
      toOrder.setText(String.format("%.2f",
          (Math.max(0,
              ((data.get(UPKMap.AVERAGE_UPK) * (projections / 1000)) / data.get(UPKMap.CASE_VALUE))
                  - Double.parseDouble(onHandCs.getText())))));
  }
}
