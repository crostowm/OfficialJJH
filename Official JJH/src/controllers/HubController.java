package controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import app.AMBreadMathStage;
import app.CateringStage;
import app.AppDirector;
import app.WeeklySupplyStage;
import gui.CompletableTaskBox;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import observers.IndexDataObserver;
import observers.TimeObserver;
import personnel.Manager;
import util.CompletableTask;
import util.JimmyCalendarUtil;
import util.Setting;

/**
 * 
 * @author crost
 *
 */
public class HubController
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
  private VBox managerDBLBox, weeklyTaskBox;

  @FXML
  private TextArea helpArea;

  private Button amBreadMathButton, managerSignInButton;

  public void initialize()
  {
    System.out.println("HC");
    // Setup Labor
    // Tabs that require time updates
    timeObservers.add(projectionTabController);
    if(AppDirector.activeManagers.size() == 0)
      AppDirector.activeManagers.add(new Manager("Guest", "", "", ""));
    shiftManagerLabel.setText(AppDirector.activeManagers.get(0) + "");

    currentShift = JimmyCalendarUtil.getShiftNumber(currentTimeAndDate);
    System.out.println("HC");
    // Fill Weekly Tasks
    switch (new GregorianCalendar().get(Calendar.DAY_OF_WEEK))
    {
      case Calendar.SUNDAY:
        break;
      case Calendar.MONDAY:
        weeklyTaskBox.getChildren()
            .add(new CompletableTaskBox<CompletableTask>(new CompletableTask("Place Truck Order")));
        weeklyTaskBox.getChildren().add(
            new CompletableTaskBox<CompletableTask>(new CompletableTask("Print Weekly Paperwork")));
        break;
      case Calendar.TUESDAY:
        weeklyTaskBox.getChildren().add(new CompletableTaskBox<CompletableTask>(
            new CompletableTask("Make sure all orders are entered in Macromatix.")));
        weeklyTaskBox.getChildren().add(new CompletableTaskBox<CompletableTask>(
            new CompletableTask("Organize weekly envelopes in chronological order.")));
        weeklyTaskBox.getChildren().add(new CompletableTaskBox<CompletableTask>(new CompletableTask(
            "Label manilla envelope with weekending date and place all order receipts inside.")));
        weeklyTaskBox.getChildren().add(new CompletableTaskBox<CompletableTask>(new CompletableTask(
            "Check Time and Attendence from last Wednesday through yesterday.")));
        weeklyTaskBox.getChildren().add(new CompletableTaskBox<CompletableTask>(
            new CompletableTask("Pre-weigh and count inventory.")));
        weeklyTaskBox.getChildren().add(new CompletableTaskBox<CompletableTask>(
            new CompletableTask("Organize and prepare for truck.")));
        break;
      case Calendar.WEDNESDAY:
        weeklyTaskBox.getChildren()
            .add(new CompletableTaskBox<CompletableTask>(new CompletableTask("Check Inventory")));
        weeklyTaskBox.getChildren()
            .add(new CompletableTaskBox<CompletableTask>(new CompletableTask("Upload WSR")));
        weeklyTaskBox.getChildren().add(new CompletableTaskBox<CompletableTask>(
            new CompletableTask("Call or text your area manager that weekending is complete.")));
        break;
      case Calendar.THURSDAY:
        break;
      case Calendar.FRIDAY:
        break;
      case Calendar.SATURDAY:
        break;
    }
    // Fill mgr dbls
    populateMgrDBLs();

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

    managerSignInButton = new Button("Sign In Manager");
    managerSignInButton.setOnAction(new EventHandler<ActionEvent>()
    {
      @Override
      public void handle(ActionEvent arg0)
      {
        //TODO
      }
    });
    bottomHBox.getChildren().add(1, managerSignInButton);
    projectionTabSelected();
    System.out.println("HC-");
  }

  public void updateAllFields()
  {
    projectionTabController.updateAllFields();
    produceOrderGuideTabController.updateProjections();
    cateringCalculatorTabController.updateAllFields();
    settingsTabController.updateAllFields();

    todayProjAMField.setText(String.format("%.2f", AppDirector.dataHub
        .getProjectionWeek().getDataForIndex(currentShift % 2 == 0 ? currentShift - 2 : currentShift - 1)));
    todayProjPMField.setText(String.format("%.2f", AppDirector.dataHub
        .getProjectionWeek().getDataForIndex(currentShift % 2 == 0 ? currentShift - 1 : currentShift)));

    /*lastYearProjAMField
        .setText(String.format("%.2f", AppDirector.dataHub.getWSRWeek(JimmyCalendarUtil.getCurrentYear() - 1, JimmyCalendarUtil.getCurrentWeek()).getLineItem(
            "= Royalty Sales").getDataForShift(currentShift % 2 == 0 ? currentShift - 1 : currentShift)));
    lastYearProjPMField
    .setText(String.format("%.2f", AppDirector.dataHub.getWSRWeek(JimmyCalendarUtil.getCurrentYear() - 1, JimmyCalendarUtil.getCurrentWeek()).getLineItem(
        "= Royalty Sales").getDataForShift(currentShift % 2 == 0 ? currentShift: currentShift + 1)));
    System.out.println("Updated all");*/
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
        double sc = AppDirector.dataHub.getSettings().getSetting(Setting.STORESC_TIME);
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
            (AppDirector.dataHub.getAverageHourlySales("TotalInshop",
                JimmyCalendarUtil.normalizeHour(currentHour), false)
                / AppDirector.dataHub.getAverageHourlySales("Total",
                    JimmyCalendarUtil.normalizeHour(currentHour), false))
                * 100));
        currentPaneSecondInPercLabel.setText(String.format("%.0f",
            (AppDirector.dataHub.getAverageHourlySales("TotalInshop",
                JimmyCalendarUtil.normalizeHour(currentHour) + 1, false)
                / AppDirector.dataHub.getAverageHourlySales("Total",
                    JimmyCalendarUtil.normalizeHour(currentHour) + 1, false))
                * 100));
        currentPaneThirdInPercLabel.setText(String.format("%.0f",
            (AppDirector.dataHub.getAverageHourlySales("TotalInshop",
                JimmyCalendarUtil.normalizeHour(currentHour) + 2, false)
                / AppDirector.dataHub.getAverageHourlySales("Total",
                    JimmyCalendarUtil.normalizeHour(currentHour) + 2, false))
                * 100));
        currentPaneFourthInPercLabel.setText(String.format("%.0f",
            (AppDirector.dataHub.getAverageHourlySales("TotalInshop",
                JimmyCalendarUtil.normalizeHour(currentHour) + 3, false)
                / AppDirector.dataHub.getAverageHourlySales("Total",
                    JimmyCalendarUtil.normalizeHour(currentHour) + 3, false))
                * 100));
        // TODO handle beyond midnight
        currentPaneFirstDelPercLabel.setText(String.format("%.0f",
            (AppDirector.dataHub.getAverageHourlySales("TotalDelivery",
                JimmyCalendarUtil.normalizeHour(currentHour), false)
                / AppDirector.dataHub.getAverageHourlySales("Total",
                    JimmyCalendarUtil.normalizeHour(currentHour), false))
                * 100));
        currentPaneSecondDelPercLabel.setText(String.format("%.0f",
            (AppDirector.dataHub.getAverageHourlySales("TotalDelivery",
                JimmyCalendarUtil.normalizeHour(currentHour) + 1, false)
                / AppDirector.dataHub.getAverageHourlySales("Total",
                    JimmyCalendarUtil.normalizeHour(currentHour) + 1, false))
                * 100));
        currentPaneThirdDelPercLabel.setText(String.format("%.0f",
            (AppDirector.dataHub.getAverageHourlySales("TotalDelivery",
                JimmyCalendarUtil.normalizeHour(currentHour) + 2, false)
                / AppDirector.dataHub.getAverageHourlySales("Total",
                    JimmyCalendarUtil.normalizeHour(currentHour) + 2, false))
                * 100));
        currentPaneFourthDelPercLabel.setText(String.format("%.0f",
            (AppDirector.dataHub.getAverageHourlySales("TotalDelivery",
                JimmyCalendarUtil.normalizeHour(currentHour) + 3, false)
                / AppDirector.dataHub.getAverageHourlySales("Total",
                    JimmyCalendarUtil.normalizeHour(currentHour) + 3, false))
                * 100));

        currentPaneFirstHourField.setText(String.format("%.2f", AppDirector.dataHub
            .getAverageHourlySales("Total", JimmyCalendarUtil.normalizeHour(currentHour), false)));
        currentPaneSecondHourField
            .setText(String.format("%.2f", AppDirector.dataHub.getAverageHourlySales("Total",
                JimmyCalendarUtil.normalizeHour(currentHour) + 1, false)));
        currentPaneThirdHourField
            .setText(String.format("%.2f", AppDirector.dataHub.getAverageHourlySales("Total",
                JimmyCalendarUtil.normalizeHour(currentHour) + 2, false)));
        currentPaneFourthHourField
            .setText(String.format("%.2f", AppDirector.dataHub.getAverageHourlySales("Total",
                JimmyCalendarUtil.normalizeHour(currentHour) + 3, false)));

        currentPaneBaked9Field.setText(String.format("%.1f",
            (AppDirector.dataHub.getAverageHourlySales("Total",
                JimmyCalendarUtil.normalizeHour(currentHour), false)
                + AppDirector.dataHub.getAverageHourlySales("Total",
                    JimmyCalendarUtil.normalizeHour(currentHour) + 1, false))
                / AppDirector.dataHub.getSettings().getSetting(Setting.B9TV)));
        currentPaneInProcess12Field.setText(String.format("%.1f",
            (AppDirector.dataHub.getAverageHourlySales("Total",
                JimmyCalendarUtil.normalizeHour(currentHour) + 2, false)
                + AppDirector.dataHub.getAverageHourlySales("Total",
                    JimmyCalendarUtil.normalizeHour(currentHour) + 3, false))
                / AppDirector.dataHub.getSettings().getSetting(Setting.BTV)));
      }
    });
  }

  @FXML
  public void addCateringButtonPressed()
  {
    ca = new CateringStage();
    ca.show();
  }

  @FXML
  public void createWeeklySupplyButtonPressed()
  {
    WeeklySupplyStage wss = new WeeklySupplyStage();
    wss.show();
  }

  private void populateMgrDBLs()
  {
    int numComplete = AppDirector.dataHub.getCompleteOrIncompleteManagerDBLs(true).size();
    while (numComplete + managerDBLBox.getChildren().size() < new GregorianCalendar()
        .get(Calendar.DAY_OF_MONTH))
    {
      if (numComplete + managerDBLBox.getChildren().size() < AppDirector.dataHub
          .getManagerDBLs().size())
      {
        CompletableTaskBox<CompletableTask> mdc = new CompletableTaskBox<CompletableTask>(
            AppDirector.dataHub.getManagerDBLs()
                .get(numComplete + managerDBLBox.getChildren().size()));
        // TODO make this work inside of completable check box
        mdc.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
          @Override
          public void handle(MouseEvent ae)
          {
            if (mdc.isSelected())
            {
              if (AppDirector.activeManagers.size() == 1)
              {
                mdc.getTask().complete(AppDirector.activeManagers.get(0).getName(),
                    new GregorianCalendar());
                managerDBLBox.getChildren().remove(mdc);
                populateMgrDBLs();
              }
              else
              {
                ContextMenu cm = new ContextMenu();
                for (Manager m : AppDirector.activeManagers)
                {
                  RadioMenuItem managerItem = new RadioMenuItem(m.getName());
                  managerItem.setOnAction(new EventHandler<ActionEvent>()
                  {
                    @Override
                    public void handle(ActionEvent arg0)
                    {
                      mdc.getTask().complete(managerItem.getText(), new GregorianCalendar());
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
      else
      {
        break;
      }
    }
  }

  @FXML
  public void projectionTabSelected()
  {
    if (helpArea != null)
      helpArea.setText(
          "Projections\n\tAll data is based off of the previous 4 weeks of WSR data."
          + "\n\nAverage:\n\tAverage of Royalty Sales"
          + "\n\nCatering:\n\tTo add catering, click the \"Add a Catering Order\" button at the bottom."
          + "\n\nSampling:\n\tEnter Sampling directly into the fields."
          + "\n\nProjections:\n\t=Average + Buffer + Catering + Sampling."
          + "\n\nOpen Thawed:\n\tAM Proj/BTV12"
          + "\n\n8am:\n\t(PM Proj) - (.25*AM Proj) - (Baked at Shift Change)"
          + "\n\nBaked 75%@11(12):\n\t(.75*AM Proj)/BTV12"
          + "\n\n@SC(9):\n\t(Baked at SC * PM Proj)/BTV9");
  }

  @FXML
  public void hourlyTabSelected()
  {
    helpArea.setText("Hourly");
  }

  @FXML
  public void produceOrderGuideTabSelected()
  {
    helpArea.setText("Produce");
  }

  @FXML
  public void truckOrderGuideTabSelected()
  {
    helpArea.setText("Produce");
  }

  @FXML
  public void inventoryParsTabSelected()
  {
    helpArea.setText("Produce");
  }

  @FXML
  public void cateringManagerTabSelected()
  {
    helpArea.setText("Produce");
  }

  @FXML
  public void usageAnalysisTabSelected()
  {
    helpArea.setText("Produce");
  }

  @FXML
  public void businessAnalysisTabSelected()
  {
    helpArea.setText("Produce");
  }

  @FXML
  public void settingsTabSelected()
  {
    helpArea.setText("Produce");
  }

  public CateringCalculatorTabController getCateringCalculatorController()
  {
    return cateringCalculatorTabController;
  }
}
