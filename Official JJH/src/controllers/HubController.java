package controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import app.CateringStage;
import app.MainApplication;
import app.WeeklySupplyStage;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import observers.DataObserver;
import observers.TimeObserver;
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
  
  @FXML
  private TruckOrderGuideTabController truckOrderGuideTabController;
  
  @FXML
  private CateringCalculatorTabController cateringCalculatorTabController;
  
  @FXML
  private SettingsTabController settingsTabController;
  
  @FXML
  private BusinessAnalysisTabController businessAnalysisTabController;
  
  @FXML
  private PeriodFoldController periodFoldController;
  //Outer
  @FXML
  private Label shiftManagerLabel, shiftLabel, clockLabel, dateLabel;
  
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
  
  //Today
  @FXML
  private TextField todayProjAMField, todayProjPMField, lastYearProjAMField, lastYearProjPMField;

  public void initialize()
  {
    MainApplication.dataHub.addObserver(this);
    
    //Tabs that require time updates
    timeObservers.add(projectionTabController);
    shiftManagerLabel.setText(MainApplication.amManager + "");
    updateAllFields();
  }

  private void updateAllFields()
  {
    projectionTabController.updateAllFields();
    produceOrderGuideTabController.updateAllFields();
    cateringCalculatorTabController.updateAllFields();
    settingsTabController.updateAllFields();
    periodFoldController.updateAll();
    
    todayProjAMField.setText("" + MainApplication.dataHub.getProjectionDataForIndex(currentShift%2==0?currentShift-2:currentShift-1));
    todayProjPMField.setText("" + MainApplication.dataHub.getProjectionDataForIndex(currentShift%2==0?currentShift-1:currentShift));

    System.out.println("Updating all");
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
    cateringCalculatorTabController.updateAllFields();
    updateAllFields();
  }

  // TODO if you highlight an entire sampling field and backspace it will not update

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
