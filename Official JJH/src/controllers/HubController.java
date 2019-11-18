package controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import app.CateringStage;
import app.MainApplication;
import app.WeeklySupplyStage;
import gui.GuiUtilFactory;
import gui.TruckOrderHBox;
import gui.UsageAnalysisHBox;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import observers.DataObserver;
import observers.TimeObserver;
import readers.UPKMap;
import util.CateringOrder;
import util.DataHub;
import util.JimmyCalendarUtil;

/**
 * 
 * @author crost
 *
 */
public class HubController implements DataObserver
{
  private int currentShift = 1;

  private GregorianCalendar currentTimeAndDate = new GregorianCalendar();

  private CateringStage ca;
  
  private ArrayList<TimeObserver> timeObservers = new ArrayList<TimeObserver>();

  @FXML
  private ProjectionTabController projectionTabController;
  
  @FXML
  private ProduceOrderGuideTabController produceOrderGuideTabController;
  
  //Outer
  @FXML
  private Label shiftLabel, clockLabel, dateLabel;
  
  // Truck Order Guide
  @FXML
  private ChoiceBox<String> orderGuideCategoryChoice = new ChoiceBox<String>(),
      orderingOnChoice = new ChoiceBox<String>(), forDeliveryOnChoice = new ChoiceBox<String>();

  @FXML
  private VBox truckOrderGuideVBox;

  private TruckOrderHBox currentlySelectedTOH = null;

  // Catering
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

  // Usage Analysis
  @FXML
  private ChoiceBox<String> usageAnalysisCategoryChoice;

  @FXML
  private VBox usageAnalysisVBox;

  @FXML
  private RadioButton usageAnalysisActualUsageRadio, usageAnalysisTheoreticalUsageRadio,
      usageAnalysisActualUPKRadio, usageAnalysisAverageUPKRadio;

  @FXML
  private ScrollPane usageAnalysisGraphPane;

  private UsageAnalysisHBox currentlySelectedUAH = null;
  private ArrayList<RadioButton> usageAnalysisCategoryGroup;

  // Settings
  @FXML
  private TextField amBufferField, pmBufferField, btvField, b9tvField, wlvField, bakedAt11Field,
      bakedAtSCField, lettuceBVField, tomatoBVField, onionBVField, cucumberBVField, pickleBVField;

  // TitledPanes
  // Current
  @FXML
  private Label currentPaneFirstHourLabel, currentPaneSecondHourLabel, currentPaneThirdHourLabel,
      currentPaneFourthHourLabel, currentPaneFirstInPercLabel, currentPaneSecondInPercLabel,
      currentPaneThirdInPercLabel, currentPaneFourthInPercLabel, currentPaneFirstDelPercLabel,
      currentPaneSecondDelPercLabel, currentPaneThirdDelPercLabel, currentPaneFourthDelPercLabel;

  @FXML
  private TextField currentPaneFirstHourField, currentPaneSecondHourField,
      currentPaneThirdHourField, currentPaneFourthHourField, currentPaneBaked9Field,
      currentPaneInProcess12Field;

  public void initialize()
  {
    MainApplication.dataHub.addObserver(this);
    
    //Tabs that require time updates
    timeObservers.add(projectionTabController);
    
    updateAllFields();

    // Order Guide
    ArrayList<String> daysOfTheWeek = new ArrayList<String>();
    daysOfTheWeek.add("Sunday");
    daysOfTheWeek.add("Monday");
    daysOfTheWeek.add("Tuesday");
    daysOfTheWeek.add("Wednesday");
    daysOfTheWeek.add("Thursday");
    daysOfTheWeek.add("Friday");
    daysOfTheWeek.add("Saturday");
    orderingOnChoice.setItems(FXCollections.observableArrayList(daysOfTheWeek));
    orderingOnChoice.setOnAction(new EventHandler<ActionEvent>()
    {
      @Override
      public void handle(ActionEvent arg0)
      {
        if(orderingOnChoice.getValue() != null && forDeliveryOnChoice.getValue() != null)
        {
          int numDiff;
          if(orderingOnChoice.getItems().indexOf(orderingOnChoice.getValue()) == forDeliveryOnChoice.getItems().indexOf(forDeliveryOnChoice.getValue()))
            numDiff = 0;
          else if(orderingOnChoice.getItems().indexOf(orderingOnChoice.getValue()) > forDeliveryOnChoice.getItems().indexOf(forDeliveryOnChoice.getValue()))
            numDiff = (7 - orderingOnChoice.getItems().indexOf(orderingOnChoice.getValue())) + forDeliveryOnChoice.getItems().indexOf(forDeliveryOnChoice.getValue());
          else
            numDiff = forDeliveryOnChoice.getItems().indexOf(forDeliveryOnChoice.getValue())- orderingOnChoice.getItems().indexOf(orderingOnChoice.getValue());
          System.out.println(numDiff + " " + currentlySelectedTOH);
        }
      }
    });
    forDeliveryOnChoice.setItems(FXCollections.observableArrayList(daysOfTheWeek));

    ArrayList<String> categories = new ArrayList<String>();
    categories.add("Bread");
    categories.add("Food");
    categories.add("Sides");
    categories.add("Paper");
    categories.add("Produce");
    categories.add("Beverage");
    categories.add("Catering");
    orderGuideCategoryChoice.setItems(FXCollections.observableArrayList(categories));

    orderGuideCategoryChoice.setValue("Select a Category");
    orderGuideCategoryChoice.setOnAction(new EventHandler<ActionEvent>()
    {

      @Override
      public void handle(ActionEvent arg0)
      {
        if (orderGuideCategoryChoice.getValue() != null)
        {
          truckOrderGuideVBox.getChildren().clear();
          truckOrderGuideVBox.getChildren().add(GuiUtilFactory.createUsageAnalysisHBoxTitle());
          int category = -1;
          switch (orderGuideCategoryChoice.getValue())
          {
            case "Bread":
              category = 1;
              break;
            case "Food":
              category = 2;
              break;
            case "Sides":
              category = 3;
              break;
            case "Paper":
              category = 4;
              break;
            case "Produce":
              category = 5;
              break;
            case "Beverage":
              category = 6;
              break;
            case "Catering":
              category = 7;
              break;
          }
          // Iterate through upk items
          for (String name : MainApplication.dataHub.getCurrentUPKMap().get(category).keySet())
          {
            if (!name.equals("COGs"))
            {
              TruckOrderHBox toh = new TruckOrderHBox(category,
                  MainApplication.dataHub.getCurrentUPKMap().getAdjustedSales(), name,
                  MainApplication.dataHub.getCurrentUPKMap().get(category).get(name));
              toh.setOnMouseClicked(new EventHandler<MouseEvent>()
              {
                @Override
                public void handle(MouseEvent arg0)
                {
                  currentlySelectedTOH = toh;
                  handleNewTruckOrderCategorySelection();
                }
              });
              truckOrderGuideVBox.getChildren().add(toh);
            }
          }
        }
      }
    });

    // Catering
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

    // Usage Analysis
    usageAnalysisCategoryChoice.setItems(FXCollections.observableArrayList(categories));
    usageAnalysisCategoryChoice.setValue("Select a Category");
    usageAnalysisCategoryChoice.setOnAction(new EventHandler<ActionEvent>()
    {

      @Override
      public void handle(ActionEvent arg0)
      {
        if (usageAnalysisCategoryChoice.getValue() != null)
        {
          usageAnalysisVBox.getChildren().clear();
          usageAnalysisVBox.getChildren().add(GuiUtilFactory.createUsageAnalysisHBoxTitle());
          int category = -1;
          switch (usageAnalysisCategoryChoice.getValue())
          {
            case "Bread":
              category = 1;
              break;
            case "Food":
              category = 2;
              break;
            case "Sides":
              category = 3;
              break;
            case "Paper":
              category = 4;
              break;
            case "Produce":
              category = 5;
              break;
            case "Beverage":
              category = 6;
              break;
            case "Catering":
              category = 7;
              break;
          }
          // Iterate through upk items
          for (String name : MainApplication.dataHub.getCurrentUPKMap().get(category).keySet())
          {
            if (!name.equals("COGs"))
            {
              UsageAnalysisHBox uah = new UsageAnalysisHBox(category,
                  MainApplication.dataHub.getCurrentUPKMap().getAdjustedSales(), name,
                  MainApplication.dataHub.getCurrentUPKMap().get(category).get(name));
              uah.setOnMouseClicked(new EventHandler<MouseEvent>()
              {
                @Override
                public void handle(MouseEvent arg0)
                {
                  currentlySelectedUAH = uah;
                  handleNewUsageAnalysisCategorySelection();
                }
              });
              usageAnalysisVBox.getChildren().add(uah);
            }
          }
        }
      }
    });
    usageAnalysisCategoryGroup = new ArrayList<RadioButton>();
    usageAnalysisCategoryGroup.add(usageAnalysisActualUsageRadio);
    usageAnalysisCategoryGroup.add(usageAnalysisTheoreticalUsageRadio);
    usageAnalysisCategoryGroup.add(usageAnalysisActualUPKRadio);
    usageAnalysisCategoryGroup.add(usageAnalysisAverageUPKRadio);
  }

  

  /**
   * Adds up avg*1.2 + catering + sampling for each shift and updates proj fields am/pmbuffer Amount
   * baked at shift change 8am total pm - bakedsc - 25%am
   */
  private void updateAllFields()
  {
    projectionTabController.updateAllFields();
    produceOrderGuideTabController.updateAllFields();
    
    System.out.println("Updating all");
    // Update Settings
    try
    {
      MainApplication.dataHub.changeSetting(DataHub.AMBUFFER, Double.parseDouble(amBufferField.getText()));
      MainApplication.dataHub.changeSetting(DataHub.PMBUFFER, Double.parseDouble(pmBufferField.getText()));
      MainApplication.dataHub.changeSetting(DataHub.BTV, Double.parseDouble(btvField.getText()));
      MainApplication.dataHub.changeSetting(DataHub.B9TV, Double.parseDouble(b9tvField.getText()));
      MainApplication.dataHub.changeSetting(DataHub.WLV, Double.parseDouble(wlvField.getText()));
      MainApplication.dataHub.changeSetting(DataHub.BAKEDAT11, Double.parseDouble(bakedAt11Field.getText()));
      MainApplication.dataHub.changeSetting(DataHub.BAKEDATSC, Double.parseDouble(bakedAtSCField.getText()));
      MainApplication.dataHub.changeSetting(DataHub.LETTUCEBV, Double.parseDouble(lettuceBVField.getText()));
      MainApplication.dataHub.changeSetting(DataHub.TOMATOBV, Double.parseDouble(tomatoBVField.getText()));
      MainApplication.dataHub.changeSetting(DataHub.ONIONBV, Double.parseDouble(onionBVField.getText()));
      MainApplication.dataHub.changeSetting(DataHub.CUCUMBERBV, Double.parseDouble(cucumberBVField.getText()));
      MainApplication.dataHub.changeSetting(DataHub.PICKLEBV, Double.parseDouble(pickleBVField.getText()));
    }
    catch (NumberFormatException nfe)
    {
      System.out.println("NFE, Could not parse Settings:\n" + nfe.getMessage());
    }

    cateringChoiceBox.setItems(FXCollections.observableArrayList(MainApplication.dataHub.getCateringOrders()));
  }

  /**
   * Handles clock
   */
  public void timeUpdateSecond()
  {
    SimpleDateFormat tf = new SimpleDateFormat("hh:mm:ss");
    SimpleDateFormat df = new SimpleDateFormat("MMM dd yyyy");
    currentTimeAndDate = new GregorianCalendar();
    Platform.runLater(new Runnable()
    {

      @Override
      public void run()
      {
        shiftLabel.setText("Shift: " + currentShift);
        clockLabel.setText(tf.format(currentTimeAndDate.getTime()));
        dateLabel.setText(df.format(currentTimeAndDate.getTime()));
      }
    });
  }

  /**
   * Update shiftNum Color fields Update current proj vals
   */
  public void timeUpdateMinute()
  {
    Platform.runLater(new Runnable()
    {
      @Override
      public void run()
      {
        int preUpShift = currentShift;
        currentShift = JimmyCalendarUtil.getShiftNumber(currentTimeAndDate, (int)MainApplication.dataHub.getSetting(DataHub.STORESC_TIME));
        if(preUpShift != currentShift)
        {
          for(TimeObserver to: timeObservers)
          {
            to.shiftChangeTo(currentShift);
          }
        }
        projectionTabController.timeUpdateMinute();
        // update current proj vals
        if (currentTimeAndDate.get(Calendar.HOUR_OF_DAY) >= 4 && currentTimeAndDate.get(Calendar.HOUR_OF_DAY) < 10)
        {
          
        }
        else if (currentTimeAndDate.get(Calendar.HOUR_OF_DAY) >= 10
            && currentTimeAndDate.get(Calendar.HOUR_OF_DAY) < 13)
        {
          
        }
        else if (currentTimeAndDate.get(Calendar.HOUR_OF_DAY) >= 13
            && currentTimeAndDate.get(Calendar.HOUR_OF_DAY) < 15)
        {

        }
        else if (currentTimeAndDate.get(Calendar.HOUR_OF_DAY) >= 15)
        {
          
        }

        // Current Titled Pane
        int currentHour = currentTimeAndDate.get(Calendar.HOUR_OF_DAY);

        currentPaneFirstHourLabel.setText(JimmyCalendarUtil.convertTo12Hour(currentHour) + "-"
            + JimmyCalendarUtil.convertTo12Hour(currentHour + 1));
        currentPaneSecondHourLabel.setText(JimmyCalendarUtil.convertTo12Hour(currentHour + 1) + "-"
            + JimmyCalendarUtil.convertTo12Hour(currentHour + 2));
        currentPaneThirdHourLabel.setText(JimmyCalendarUtil.convertTo12Hour(currentHour + 2) + "-"
            + JimmyCalendarUtil.convertTo12Hour(currentHour + 3));
        currentPaneFourthHourLabel.setText(JimmyCalendarUtil.convertTo12Hour(currentHour + 3) + "-"
            + JimmyCalendarUtil.convertTo12Hour(currentHour + 4));

        currentPaneFirstInPercLabel
            .setText(String.format("%.0f", (MainApplication.dataHub.getAverageHourlySales("Inshop", currentHour)
                / MainApplication.dataHub.getAverageHourlySales("Total", currentHour)) * 100));
        currentPaneSecondInPercLabel
            .setText(String.format("%.0f", (MainApplication.dataHub.getAverageHourlySales("Inshop", currentHour + 1)
                / MainApplication.dataHub.getAverageHourlySales("Total", currentHour + 1)) * 100));
        currentPaneThirdInPercLabel
            .setText(String.format("%.0f", (MainApplication.dataHub.getAverageHourlySales("Inshop", currentHour + 2)
                / MainApplication.dataHub.getAverageHourlySales("Total", currentHour + 2)) * 100));
        currentPaneFourthInPercLabel
            .setText(String.format("%.0f", (MainApplication.dataHub.getAverageHourlySales("Inshop", currentHour + 3)
                / MainApplication.dataHub.getAverageHourlySales("Total", currentHour + 3)) * 100));

        currentPaneFirstDelPercLabel
            .setText(String.format("%.0f", (MainApplication.dataHub.getAverageHourlySales("Delivery", currentHour)
                / MainApplication.dataHub.getAverageHourlySales("Total", currentHour)) * 100));
        currentPaneSecondDelPercLabel
            .setText(String.format("%.0f", (MainApplication.dataHub.getAverageHourlySales("Delivery", currentHour + 1)
                / MainApplication.dataHub.getAverageHourlySales("Total", currentHour + 1)) * 100));
        currentPaneThirdDelPercLabel
            .setText(String.format("%.0f", (MainApplication.dataHub.getAverageHourlySales("Delivery", currentHour + 2)
                / MainApplication.dataHub.getAverageHourlySales("Total", currentHour + 2)) * 100));
        currentPaneFourthDelPercLabel
            .setText(String.format("%.0f", (MainApplication.dataHub.getAverageHourlySales("Delivery", currentHour + 3)
                / MainApplication.dataHub.getAverageHourlySales("Total", currentHour + 3)) * 100));

        currentPaneFirstHourField
            .setText(String.format("%.2f", MainApplication.dataHub.getAverageHourlySales("Total", currentHour)));
        currentPaneSecondHourField
            .setText(String.format("%.2f", MainApplication.dataHub.getAverageHourlySales("Total", currentHour + 1)));
        currentPaneThirdHourField
            .setText(String.format("%.2f", MainApplication.dataHub.getAverageHourlySales("Total", currentHour + 2)));
        currentPaneFourthHourField
            .setText(String.format("%.2f", MainApplication.dataHub.getAverageHourlySales("Total", currentHour + 3)));

        currentPaneBaked9Field.setText(String.format("%.1f",
            (MainApplication.dataHub.getAverageHourlySales("Total", currentHour)
                + MainApplication.dataHub.getAverageHourlySales("Total", currentHour + 1))
                / MainApplication.dataHub.getSetting(DataHub.B9TV)));
        currentPaneInProcess12Field.setText(String.format("%.1f",
            (MainApplication.dataHub.getAverageHourlySales("Total", currentHour + 2)
                + MainApplication.dataHub.getAverageHourlySales("Total", currentHour + 3))
                / MainApplication.dataHub.getSetting(DataHub.BTV)));
      }
    });
  }

  
  @FXML
  public void addCateringButtonPressed()
  {
    ca = new CateringStage();
    ca.show();
  }

  @Override
  public void cateringOrderAdded(CateringOrder co)
  {
    if (ca != null)
      ca.close();
    updateAllFields();
  }

  @Override
  public void cateringOrderRemoved(CateringOrder co)
  {
    cateringChoiceBox.setItems(FXCollections.observableArrayList(MainApplication.dataHub.getCateringOrders()));
  }

  // TODO if you highlight an entire sampling field and backspace it will not update

  

  // Settings
  // TODO needs to update setting in datahub
  @FXML
  void amBufferFieldChanged()
  {
    try
    {
      MainApplication.dataHub.changeSetting(DataHub.AMBUFFER, Double.parseDouble(amBufferField.getText()));
    }
    catch (NumberFormatException nfe)
    {
      System.out.println("NFE, Could not parse Settings:\n" + nfe.getMessage());
    }
  }

  @FXML
  void pmBufferFieldChanged()
  {
    try
    {
      MainApplication.dataHub.changeSetting(DataHub.PMBUFFER, Double.parseDouble(pmBufferField.getText()));
    }
    catch (NumberFormatException nfe)
    {
      System.out.println("NFE, Could not parse Settings:\n" + nfe.getMessage());
    }
  }

  @FXML
  void btvFieldChanged()
  {
    try
    {
      MainApplication.dataHub.changeSetting(DataHub.BTV, Double.parseDouble(btvField.getText()));
    }
    catch (NumberFormatException nfe)
    {
      System.out.println("NFE, Could not parse Settings:\n" + nfe.getMessage());
    }
  }

  @FXML
  void b9tvFieldChanged()
  {
    try
    {
      MainApplication.dataHub.changeSetting(DataHub.B9TV, Double.parseDouble(b9tvField.getText()));
    }
    catch (NumberFormatException nfe)
    {
      System.out.println("NFE, Could not parse Settings:\n" + nfe.getMessage());
    }
  }

  @FXML
  void wlvFieldChanged()
  {
    try
    {
      MainApplication.dataHub.changeSetting(DataHub.WLV, Double.parseDouble(wlvField.getText()));
    }
    catch (NumberFormatException nfe)
    {
      System.out.println("NFE, Could not parse Settings:\n" + nfe.getMessage());
    }
  }

  @FXML
  void bakedAt11FieldChanged()
  {
    try
    {
      MainApplication.dataHub.changeSetting(DataHub.BAKEDAT11, Double.parseDouble(bakedAt11Field.getText()));
    }
    catch (NumberFormatException nfe)
    {
      System.out.println("NFE, Could not parse Settings:\n" + nfe.getMessage());
    }
  }

  @FXML
  void bakedAtSCFieldChanged()
  {
    try
    {
      MainApplication.dataHub.changeSetting(DataHub.BAKEDATSC, Double.parseDouble(bakedAtSCField.getText()));
    }
    catch (NumberFormatException nfe)
    {
      System.out.println("NFE, Could not parse Settings:\n" + nfe.getMessage());
    }
  }

  @FXML
  void lettuceBVChanged()
  {
    try
    {
      MainApplication.dataHub.changeSetting(DataHub.LETTUCEBV, Double.parseDouble(lettuceBVField.getText()));
    }
    catch (NumberFormatException nfe)
    {
      System.out.println("NFE, Could not parse Settings:\n" + nfe.getMessage());
    }
  }

  @FXML
  void tomatoBVChanged()
  {
    try
    {
      MainApplication.dataHub.changeSetting(DataHub.TOMATOBV, Double.parseDouble(tomatoBVField.getText()));
    }
    catch (NumberFormatException nfe)
    {
      System.out.println("NFE, Could not parse Settings:\n" + nfe.getMessage());
    }
  }

  @FXML
  void onionBVChanged()
  {
    try
    {
      MainApplication.dataHub.changeSetting(DataHub.ONIONBV, Double.parseDouble(onionBVField.getText()));
    }
    catch (NumberFormatException nfe)
    {
      System.out.println("NFE, Could not parse Settings:\n" + nfe.getMessage());
    }
  }

  @FXML
  void cucumberBVChanged()
  {
    try
    {
      MainApplication.dataHub.changeSetting(DataHub.CUCUMBERBV, Double.parseDouble(cucumberBVField.getText()));
    }
    catch (NumberFormatException nfe)
    {
      System.out.println("NFE, Could not parse Settings:\n" + nfe.getMessage());
    }
  }

  @FXML
  void pickleBVChanged()
  {
    try
    {
      MainApplication.dataHub.changeSetting(DataHub.PICKLEBV, Double.parseDouble(pickleBVField.getText()));
    }
    catch (NumberFormatException nfe)
    {
      System.out.println("NFE, Could not parse Settings:\n" + nfe.getMessage());
    }
  }

  private void handleNewTruckOrderCategorySelection()
  {

  }

  private void handleNewUsageAnalysisCategorySelection()
  {
    if (currentlySelectedUAH != null)
    {
      int weekNumber = JimmyCalendarUtil.getWeekNumber(currentTimeAndDate);
      LineChart<Number, Number> chart = GuiUtilFactory
          .createUsageAnalysisLineChart(currentlySelectedUAH, weekNumber);
      for (RadioButton rb : usageAnalysisCategoryGroup)
      {
        if (rb.isSelected())
        {
          switch (rb.getText())
          {
            case "Actual Usage":
              chart.getData().add(GuiUtilFactory.getSeriesFor(currentlySelectedUAH,
                  MainApplication.dataHub.getPast5UPKMaps(), weekNumber, rb.getText(), UPKMap.ACTUAL_USAGE));
              break;
            case "Theoretical Usage":
              chart.getData().add(GuiUtilFactory.getSeriesFor(currentlySelectedUAH,
                  MainApplication.dataHub.getPast5UPKMaps(), weekNumber, rb.getText(), UPKMap.THEORETICAL_USAGE));
              break;
            case "Actual UPK":
              chart.getData().add(GuiUtilFactory.getSeriesFor(currentlySelectedUAH,
                  MainApplication.dataHub.getPast5UPKMaps(), weekNumber, rb.getText(), UPKMap.ACTUAL_UPK));
              break;
            case "Average UPK":
              chart.getData().add(GuiUtilFactory.getSeriesFor(currentlySelectedUAH,
                  MainApplication.dataHub.getPast5UPKMaps(), weekNumber, rb.getText(), UPKMap.AVERAGE_UPK));
              break;
          }
        }
      }
      usageAnalysisGraphPane.setContent(chart);
    }
  }

  @FXML
  void usageAnalysisActualUsageRadioClicked()
  {
    handleNewUsageAnalysisCategorySelection();
  }

  @FXML
  void usageAnalysisTheoreticalUsageRadioClicked()
  {
    handleNewUsageAnalysisCategorySelection();
  }

  @FXML
  void usageAnalysisActualUPKRadioClicked()
  {
    handleNewUsageAnalysisCategorySelection();
  }

  @FXML
  void usageAnalysisAverageUPKRadioClicked()
  {
    handleNewUsageAnalysisCategorySelection();
  }

  @FXML
  void deleteCateringButtonPressed()
  {
    if (cateringChoiceBox.getValue() != null)
      MainApplication.dataHub.removeCateringOrder(cateringChoiceBox.getValue());
  }

  @Override
  public void toolBoxDataUpdated()
  {
    updateAllFields();
  }
  
  @FXML
  public void createWeeklySupplyButtonPressed()
  {
    WeeklySupplyStage wss = new WeeklySupplyStage();
    wss.show();
  }
}
