package gui;

import app.MainApplication;
import error_handling.ErrorHandler;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import readers.UPKMap;
import util.DataHub;

public class ProduceOrderBox extends HBox
{
  private String name;
  private double projections;
  private String unit;
  private String desc;
  private double upk, expectedUsage;
  private TextField upkField, usageField, onHandEaField, onHandCsField, toField;
  private Label nameLabel, descLabel;
  private int caseAmount;

  public ProduceOrderBox(String name, int caseAmount, double projections, String unit, String desc)
  {
    this.name = name;
    this.caseAmount = caseAmount;
    this.projections = projections;
    this.unit = unit;
    this.desc = desc;
    
    setSpacing(10);
    nameLabel = new Label(name + " (" + unit + ")");
    nameLabel.setMinWidth(100);
    nameLabel.setPrefWidth(100);
    nameLabel.setMaxWidth(100);
    
    upkField = new TextField();
    upkField.setMinWidth(45);
    upkField.setPrefWidth(45);
    upkField.setMaxWidth(45);
    upkField.setEditable(false);
    setMargin(upkField, new Insets(0, 0, 0, 3));
    
    usageField = new TextField();
    usageField.setMinWidth(45);
    usageField.setPrefWidth(45);
    usageField.setMaxWidth(45);
    usageField.setEditable(false);
    setMargin(usageField, new Insets(0, 0, 0, 40));
    
    figureUPKAndUsage();
    
    onHandEaField = new TextField();
    onHandEaField.setOnKeyTyped(new EventHandler<KeyEvent>()
    {
      @Override
      public void handle(KeyEvent arg0)
      {
        double onHand = 0;
        if (!onHandEaField.getText().equals(""))
        {
          onHand = Double.parseDouble(onHandEaField.getText());
        }
        onHandCsField.setText(String.format("%.2f", onHand / caseAmount));
        figureToOrder();
      }
    });
    onHandEaField.setMinWidth(45);
    onHandEaField.setPrefWidth(45);
    onHandEaField.setMaxWidth(45);
    setMargin(onHandEaField, new Insets(0, 0, 0, 50));

    onHandCsField = new TextField();
    onHandCsField.setOnKeyTyped(new EventHandler<KeyEvent>()
    {
      @Override
      public void handle(KeyEvent arg0)
      {
        double onHand = 0;
        if (!onHandCsField.getText().equals(""))
        {
          onHand = Double.parseDouble(onHandCsField.getText());
        }
        onHandEaField.setText(String.format("%.2f", onHand * caseAmount));
        figureToOrder();
      }
    });
    onHandCsField.setMinWidth(45);
    onHandCsField.setPrefWidth(45);
    onHandCsField.setMaxWidth(45);
    setMargin(onHandCsField, new Insets(0, 0, 0, 33));

    toField = new TextField();
    toField.setMinWidth(45);
    toField.setPrefWidth(45);
    toField.setMaxWidth(45);
    toField.setEditable(false);
    setMargin(toField, new Insets(0, 0, 0, 35));
    
    descLabel = new Label(desc);
    setMargin(descLabel, new Insets(0, 0, 0, 20));
    getChildren().addAll(nameLabel, upkField, usageField, onHandEaField, onHandCsField, toField, descLabel);
  }

  private void figureUPKAndUsage()
  {
    upk = name.equals("Sprouts") ? MainApplication.dataHub.getSetting(DataHub.SPROUT_UPK)
        : MainApplication.dataHub.getCurrentUPKMap().getData(UPKMap.PRODUCE, name,
            UPKMap.AVERAGE_UPK);
    expectedUsage = (projections / 1000) * upk;
    
    upkField.setText(String.format("%.2f", upk));
    usageField.setText(String.format("%.2f", expectedUsage));
  }

  public void updateProjections(double proj)
  {
    this.projections = proj;
    figureUPKAndUsage();
    figureToOrder();
  }
  protected void figureToOrder()
  {
    try
    {
      toField.setTooltip(new Tooltip(String.format("%.2f", Math.max(0, (expectedUsage - (onHandEaField.getText().equals("") ? 0
          : Double.parseDouble(onHandEaField.getText())))/caseAmount))));
      toField.setText(String.format("%.0f", Math.ceil(Math.max(0, (expectedUsage - (onHandEaField.getText().equals("") ? 0
          : Double.parseDouble(onHandEaField.getText())))/caseAmount))));
    }
    catch (NumberFormatException nfe)
    {
      nfe.printStackTrace();
      ErrorHandler.addError(nfe);
    }
  }

  public String getName()
  {
    return name;
  }

  public double getProjections()
  {
    return projections;
  }

  public String getUnit()
  {
    return unit;
  }

  public String getDesc()
  {
    return desc;
  }

  public double getUpk()
  {
    return upk;
  }

  public double getExpectedUsage()
  {
    return expectedUsage;
  }
}
