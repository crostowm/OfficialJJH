package gui;

import error_handling.ErrorHandler;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import lineitems.UPKItem;

public class TruckOrderHBox extends UPKHBox
{
  private Label name, unit, acUsage, avgUPK, projUseCs, projUseEa;
  private TextField onHandCs, onHandEa, toOrder;
  private double projections;

  public TruckOrderHBox(UPKItem item, double adjustedSales, double projSales)
  {
    super(adjustedSales, item);
    this.projections = projSales;
    setSpacing(10);
    name = new Label(item.getName());
    name.setMinWidth(200);
    name.setMaxWidth(200);
    unit = new Label(item.getUnit());

    unit.setMinWidth(100);
    unit.setMaxWidth(100);
    unit.setAlignment(Pos.CENTER);

    acUsage = new Label(String.format("%.2f", item.getActualUsage()));
    acUsage.setMinWidth(90);
    acUsage.setMaxWidth(90);
    acUsage.setAlignment(Pos.CENTER);

    avgUPK = new Label(String.format("%.2f", item.getAverageUPK()));
    avgUPK.setMinWidth(90);
    avgUPK.setMaxWidth(90);
    avgUPK.setAlignment(Pos.CENTER);

    // Needs to be divided by case
    projUseCs = new Label(String.format("%.2f",
        (item.getAverageUPK() * (projections / 1000)) / item.getCaseValue()));
    projUseCs.setMinWidth(90);
    projUseCs.setMaxWidth(90);
    projUseCs.setAlignment(Pos.CENTER);

    projUseEa = new Label(
        String.format("%.2f", item.getAverageUPK() * (projections / 1000)));
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
                Double.parseDouble(onHandCs.getText()) * item.getCaseValue()));
            toOrder.setTooltip(new Tooltip(String.format("%.2f",
                (Math.max(0,
                    ((item.getAverageUPK() * (projections / 1000))
                        / item.getCaseValue())
                        - Double.parseDouble(onHandCs.getText()))))));
            toOrder.setText(String.format("%.2f",
                (Math.ceil(Math.max(0,
                    ((item.getAverageUPK() * (projections / 1000))
                        / item.getCaseValue())
                        - Double.parseDouble(onHandCs.getText()))))));
          }
          else
          {
            onHandEa.setText("0");
            toOrder.setTooltip(new Tooltip(String.format("%.2f",
                (Math.max(0, (item.getAverageUPK() * (projections / 1000))
                    / item.getCaseValue())))));
            toOrder.setText(String.format("%.2f",
                (Math.ceil(Math.max(0, (item.getAverageUPK() * (projections / 1000))
                    / item.getCaseValue())))));
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
                Double.parseDouble(onHandEa.getText()) / item.getCaseValue()));
            toOrder.setTooltip(new Tooltip(String.format("%.2f",
                (Math.max(0,
                    ((item.getAverageUPK() * (projections / 1000))
                        / item.getCaseValue())
                        - Double.parseDouble(onHandCs.getText()))))));
            toOrder.setText(String.format("%.2f",
                (Math.ceil(Math.max(0,
                    ((item.getAverageUPK() * (projections / 1000))
                        / item.getCaseValue())
                        - Double.parseDouble(onHandCs.getText()))))));
          }
          else
          {
            onHandCs.setText("0");
            toOrder.setTooltip(new Tooltip(String.format("%.2f",
                (Math.max(0, (item.getAverageUPK() * (projections / 1000))
                    / item.getCaseValue())))));
            toOrder.setText(String.format("%.2f",
                (Math.ceil(Math.max(0, (item.getAverageUPK() * (projections / 1000))
                    / item.getCaseValue())))));
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
    projUseCs.setText(String.format("%.2f",
        (item.getAverageUPK() * (projections / 1000)) / item.getCaseValue()));
    projUseEa
        .setText(String.format("%.2f", item.getAverageUPK() * (projections / 1000)));
    if (!onHandCs.getText().equals(""))
    {
      toOrder
          .setTooltip(new Tooltip(String.format("%.2f",
              (Math.max(0,
                  ((item.getAverageUPK() * (projections / 1000))
                      / item.getCaseValue())
                      - Double.parseDouble(onHandCs.getText()))))));
      toOrder
          .setText(String.format("%.2f",
              (Math.max(0,
                  ((item.getAverageUPK() * (projections / 1000))
                      / item.getCaseValue())
                      - Double.parseDouble(onHandCs.getText())))));
    }
  }
}
