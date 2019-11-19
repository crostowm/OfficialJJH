package controllers;

import app.MainApplication;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import util.CateringOrder;

public class CateringCalculatorTabController
{
  @FXML
  private ChoiceBox<CateringOrder> cateringChoiceBox;

  @FXML
  private Button deleteCateringButton;

  @FXML
  private Spinner<Integer> blSpinner, mj24Spinner, mj12Spinner;

  @FXML
  private TextField blBagField, mj24NapField, mj24BoxField, mj24MenuField, mj12NapField,
      mj12BoxField, mj12MenuField;

  @FXML
  private TextArea cateringOrderDetailsArea;

  public void initialize()
  {
    SpinnerValueFactory<Integer> blValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(
        0, 500, 0);
    blSpinner.setValueFactory(blValueFactory);
    blSpinner.valueProperty().addListener(new ChangeListener<Integer>()
    {
      @Override
      public void changed(ObservableValue<? extends Integer> arg0, Integer old, Integer newVal)
      {
        cateringChoiceBox.getValue().setNumBL(newVal);
        blBagField.setText((Math.ceil(newVal.doubleValue() / 10)) + "");
      }
    });

    SpinnerValueFactory<Integer> mj24ValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(
        0, 500, 0);
    mj24Spinner.setValueFactory(mj24ValueFactory);
    mj24Spinner.valueProperty().addListener(new ChangeListener<Integer>()
    {
      @Override
      public void changed(ObservableValue<? extends Integer> arg0, Integer old, Integer newVal)
      {
        cateringChoiceBox.getValue().setNum24P(newVal);
        mj24NapField.setText((newVal * 24) + "");
        mj24BoxField.setText(newVal + "");
        mj24MenuField.setText((newVal * 2) + "");
      }
    });

    SpinnerValueFactory<Integer> mj12ValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(
        0, 500, 0);
    mj12Spinner.setValueFactory(mj12ValueFactory);
    mj12Spinner.valueProperty().addListener(new ChangeListener<Integer>()
    {
      @Override
      public void changed(ObservableValue<? extends Integer> arg0, Integer old, Integer newVal)
      {
        cateringChoiceBox.getValue().setNum12P(newVal);
        mj12NapField.setText((newVal * 12) + "");
        mj12BoxField.setText(newVal + "");
        mj12MenuField.setText(newVal + "");
      }
    });

    cateringChoiceBox.setOnAction(new EventHandler<ActionEvent>()
    {

      @Override
      public void handle(ActionEvent arg0)
      {
        if (cateringChoiceBox.getValue() != null)
        {
          cateringOrderDetailsArea.setText(cateringChoiceBox.getValue().getDetails());
          blBagField.setText("");
          blValueFactory.setValue(cateringChoiceBox.getValue().getNumBL());

          mj24NapField.setText("");
          mj24BoxField.setText("");
          mj24MenuField.setText("");
          mj24ValueFactory.setValue(cateringChoiceBox.getValue().getNum24P());

          mj12NapField.setText("");
          mj12BoxField.setText("");
          mj12MenuField.setText("");
          mj12ValueFactory.setValue(cateringChoiceBox.getValue().getNum12P());
        }

      }
    });
  }

  public void updateAllFields()
  {
    cateringChoiceBox
        .setItems(FXCollections.observableArrayList(MainApplication.dataHub.getCateringOrders()));
  }
  
  @FXML
  void deleteCateringButtonPressed()
  {
    if (cateringChoiceBox.getValue() != null)
      MainApplication.dataHub.removeCateringOrder(cateringChoiceBox.getValue());
  }

}
