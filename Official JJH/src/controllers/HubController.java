package controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import app.AMBreadMathStage;
import app.CateringStage;
import app.LoginStage;
import app.MainApplication;
import app.WeeklySupplyStage;
import gui.ManagerDBLCheckBox;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import observers.DataObserver;
import observers.TimeObserver;
import personnel.Manager;
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

  @FXML
  private DashboardController dashboardTabController;

  // Outer
  @FXML
  private Label shiftManagerLabel, shiftLabel, clockLabel, dateLabel;

  @FXML
  private Button createWeeklySupplyButton, addCateringButton;

  @FXML
  private HBox bottomHBox;

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

  @FXML
  private VBox currentPaneVBox;

  // Today
  @FXML
  private TextField todayProjAMField, todayProjPMField, lastYearProjAMField, lastYearProjPMField;

  @FXML
  private VBox managerDBLBox;

  private Button amBreadMathButton, managerSignInButton;

  public void initialize()
  {
    MainApplication.dataHub.addObserver(this);

    // Tabs that require time updates
    timeObservers.add(projectionTabController);
    shiftManagerLabel.setText(MainApplication.activeManagers.get(0) + "");

    currentShift = JimmyCalendarUtil.getShiftNumber(currentTimeAndDate);
    // Fill mgr dbls
    populateMgrDBLs();
    updateAllFields();

    amBreadMathButton = new Button("AM Bread Math");
    amBreadMathButton.setOnAction(new EventHandler<ActionEvent>()
    {
      @Override
      public void handle(ActionEvent arg0)
      {
        AMBreadMathStage ambms = new AMBreadMathStage();
        ambms.show();
      }
    });
    bottomHBox.getChildren().add(3, amBreadMathButton);
    System.out.println("HC");

    managerSignInButton = new Button("Sign In Manager");
    managerSignInButton.setOnAction(new EventHandler<ActionEvent>()
    {
      @Override
      public void handle(ActionEvent arg0)
      {
        // TODO Auto-generated method stub
        LoginStage ls = new LoginStage();
        ls.show();
      }
    });
    bottomHBox.getChildren().add(1, managerSignInButton);
  }

  private void updateAllFields()
  {
    projectionTabController.updateAllFields();
    produceOrderGuideTabController.updateAllFields();
    cateringCalculatorTabController.updateAllFields();
    settingsTabController.updateAllFields();
    periodFoldController.updateAll();
    dashboardTabController.updateAllFields();

    todayProjAMField.setText(String.format("%.2f", MainApplication.dataHub
        .getProjectionDataForIndex(currentShift % 2 == 0 ? currentShift - 2 : currentShift - 1)));
    todayProjPMField.setText(String.format("%.2f", MainApplication.dataHub
        .getProjectionDataForIndex(currentShift % 2 == 0 ? currentShift - 1 : currentShift)));

    lastYearProjAMField
        .setText(String.format("%.2f", MainApplication.dataHub.getLastYearWSR().getDataForShift(
            "= Royalty Sales", currentShift % 2 == 0 ? currentShift - 1 : currentShift)));
    lastYearProjPMField
        .setText(String.format("%.2f", MainApplication.dataHub.getLastYearWSR().getDataForShift(
            "= Royalty Sales", currentShift % 2 == 0 ? currentShift : currentShift + 1)));
    System.out.println("Updated all");
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
        currentShift = JimmyCalendarUtil.getShiftNumber(currentTimeAndDate);
        if (preUpShift != currentShift)
        {
          for (TimeObserver to : timeObservers)
          {
            to.shiftChangeTo(currentShift);
          }
        }
        projectionTabController.timeUpdateMinute();

        // update current proj vals
        double sc = MainApplication.dataHub.getSetting(DataHub.STORESC_TIME);
        if (currentTimeAndDate.get(Calendar.HOUR_OF_DAY) >= 4
            && currentTimeAndDate.get(Calendar.HOUR_OF_DAY) < 10)
        {
          
        }
        else if (currentTimeAndDate.get(Calendar.HOUR_OF_DAY) >= 10
            && currentTimeAndDate.get(Calendar.HOUR_OF_DAY) < sc - 2)
        {
          
        }
        else if (currentTimeAndDate.get(Calendar.HOUR_OF_DAY) >= sc - 2
            && currentTimeAndDate.get(Calendar.HOUR_OF_DAY) < sc - 1)
        {
          if (amBreadMathButton != null)
          {

          }
        }
        else if (currentTimeAndDate.get(Calendar.HOUR_OF_DAY) >= sc - 1)
        {
        }
        else if (currentTimeAndDate.get(Calendar.HOUR_OF_DAY) >= sc + 1)
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

        currentPaneFirstInPercLabel.setText(String.format("%.0f",
            (MainApplication.dataHub.getAverageHourlySales("TotalInshop", currentHour, false)
                / MainApplication.dataHub.getAverageHourlySales("Total", currentHour, false))
                * 100));
        currentPaneSecondInPercLabel.setText(String.format("%.0f",
            (MainApplication.dataHub.getAverageHourlySales("TotalInshop", currentHour + 1, false)
                / MainApplication.dataHub.getAverageHourlySales("Total", currentHour + 1, false))
                * 100));
        currentPaneThirdInPercLabel.setText(String.format("%.0f",
            (MainApplication.dataHub.getAverageHourlySales("TotalInshop", currentHour + 2, false)
                / MainApplication.dataHub.getAverageHourlySales("Total", currentHour + 2, false))
                * 100));
        currentPaneFourthInPercLabel.setText(String.format("%.0f",
            (MainApplication.dataHub.getAverageHourlySales("TotalInshop", currentHour + 3, false)
                / MainApplication.dataHub.getAverageHourlySales("Total", currentHour + 3, false))
                * 100));
        // TODO handle beyond midnight
        currentPaneFirstDelPercLabel.setText(String.format("%.0f",
            (MainApplication.dataHub.getAverageHourlySales("TotalDelivery", currentHour, false)
                / MainApplication.dataHub.getAverageHourlySales("Total", currentHour, false))
                * 100));
        currentPaneSecondDelPercLabel.setText(String.format("%.0f",
            (MainApplication.dataHub.getAverageHourlySales("TotalDelivery", currentHour + 1, false)
                / MainApplication.dataHub.getAverageHourlySales("Total", currentHour + 1, false))
                * 100));
        currentPaneThirdDelPercLabel.setText(String.format("%.0f",
            (MainApplication.dataHub.getAverageHourlySales("TotalDelivery", currentHour + 2, false)
                / MainApplication.dataHub.getAverageHourlySales("Total", currentHour + 2, false))
                * 100));
        currentPaneFourthDelPercLabel.setText(String.format("%.0f",
            (MainApplication.dataHub.getAverageHourlySales("TotalDelivery", currentHour + 3, false)
                / MainApplication.dataHub.getAverageHourlySales("Total", currentHour + 3, false))
                * 100));

        currentPaneFirstHourField.setText(String.format("%.2f",
            MainApplication.dataHub.getAverageHourlySales("Total", currentHour, false)));
        currentPaneSecondHourField.setText(String.format("%.2f",
            MainApplication.dataHub.getAverageHourlySales("Total", currentHour + 1, false)));
        currentPaneThirdHourField.setText(String.format("%.2f",
            MainApplication.dataHub.getAverageHourlySales("Total", currentHour + 2, false)));
        currentPaneFourthHourField.setText(String.format("%.2f",
            MainApplication.dataHub.getAverageHourlySales("Total", currentHour + 3, false)));

        currentPaneBaked9Field.setText(String.format("%.1f",
            (MainApplication.dataHub.getAverageHourlySales("Total", currentHour, false)
                + MainApplication.dataHub.getAverageHourlySales("Total", currentHour + 1, false))
                / MainApplication.dataHub.getSetting(DataHub.B9TV)));
        currentPaneInProcess12Field.setText(String.format("%.1f",
            (MainApplication.dataHub.getAverageHourlySales("Total", currentHour + 2, false)
                + MainApplication.dataHub.getAverageHourlySales("Total", currentHour + 3, false))
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

  private void populateMgrDBLs()
  {
    int numComplete = MainApplication.dataHub.getCompleteOrIncompleteManagerDBLs(true).size();
    while (numComplete + managerDBLBox.getChildren().size() < new GregorianCalendar()
        .get(Calendar.DAY_OF_MONTH))
    {
      ManagerDBLCheckBox mdc = new ManagerDBLCheckBox(MainApplication.dataHub.getManagerDBLs()
          .get(numComplete + managerDBLBox.getChildren().size()));
      mdc.setOnMouseClicked(new EventHandler<MouseEvent>()
      {
        @Override
        public void handle(MouseEvent ae)
        {
          if (mdc.isSelected())
          {
            if (MainApplication.activeManagers.size() == 1)
            {
              mdc.getDBL().complete(MainApplication.activeManagers.get(0).getName(),
                  new GregorianCalendar());
              managerDBLBox.getChildren().remove(mdc);
              populateMgrDBLs();
            }
            else
            {
              ContextMenu cm = new ContextMenu();
              for (Manager m : MainApplication.activeManagers)
              {
                RadioMenuItem managerItem = new RadioMenuItem(m.getName());
                managerItem.setOnAction(new EventHandler<ActionEvent>()
                {
                  @Override
                  public void handle(ActionEvent arg0)
                  {
                    mdc.getDBL().complete(managerItem.getText(), new GregorianCalendar());
                    managerDBLBox.getChildren().remove(mdc);
                    populateMgrDBLs();
                  }
                });
                cm.getItems().add(managerItem);
              }
              cm.show(mdc, ae.getScreenX(), ae.getScreenY());
            }
          }
        }
      });
      managerDBLBox.getChildren().add(mdc);
    }
  }
}
