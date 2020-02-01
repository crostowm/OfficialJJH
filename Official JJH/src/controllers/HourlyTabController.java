package controllers;

import java.time.LocalDate;
import java.util.ArrayList;

import app.AppDirector;
import gui.GuiUtilFactory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import lineitems.HourlySalesDay;
import lineitems.HourlySalesItem;
import selenium.ReportGrabber;
import util.ReportFinder;

public class HourlyTabController
{
  @FXML
  private DatePicker dp;

  @FXML
  private CheckBox averageCheck;

  @FXML
  private Button downloadButton, addToDownloadQueueButton;

  @FXML
  private VBox itemBox, avgDateBox, downloadQueueBox;

  @FXML
  private GridPane gp;

  private ArrayList<HourlySalesDay> averages = new ArrayList<HourlySalesDay>();
  private ArrayList<String> downloadQueue = new ArrayList<String>();

  public void initialize()
  {
    System.out.println("HTC");
    gp.add(GuiUtilFactory.getHourlyTitleHBox(), 0, 1);
    dp.setOnAction(new EventHandler<ActionEvent>()
    {
      @Override
      public void handle(ActionEvent arg0)
      {
        itemBox.getChildren().clear();
        LocalDate ld = dp.getValue();
        if (ld != null)
        {
          HourlySalesDay hsd = AppDirector.dataHub.getHourlySalesDay(ld.getYear(),
              ld.getMonthValue(), ld.getDayOfMonth());
          if (hsd != null)
          {
            addToDownloadQueueButton.setDisable(true);
            downloadButton.setDisable(true);
            if (averageCheck.isSelected())
            {
              addDateToAverage(ld);
            }
            else
            {
              itemBox.getChildren().clear();
              for (HourlySalesItem hsi : hsd)
              {
                itemBox.getChildren().addAll(GuiUtilFactory.createHourlyItemBox(hsi),
                    new Separator());
              }
            }
          }
          else
          {
            addToDownloadQueueButton.setDisable(false);
            downloadButton.setDisable(false);
          }
        }
      }
    });
    System.out.println("HTC-");
  }

  @FXML
  public void downloadButtonPressed(ActionEvent event)
  {
    ReportGrabber rg = new ReportGrabber();
    rg.startAndLogin();
    LocalDate ld = dp.getValue();
    rg.downloadHourlySalesReports(downloadQueue);
    rg.goToDownloadCenterAndDownloadAll();
    rg.close();

    ReportFinder rf = new ReportFinder(AppDirector.config.getDownloadFolderPath());
    rf.uploadLastXHourlyDays(downloadQueue);

    downloadQueue.clear();
    downloadQueueBox.getChildren().clear();
    if (ld != null)
    {
      HourlySalesDay hsd = AppDirector.dataHub.getHourlySalesDay(ld.getYear(),
          ld.getMonthValue(), ld.getDayOfMonth());
      if (hsd != null)
      {
        downloadButton.setDisable(true);
        itemBox.getChildren().clear();
        for (HourlySalesItem hsi : hsd)
        {
          itemBox.getChildren().addAll(GuiUtilFactory.createHourlyItemBox(hsi),
              new Separator());
        }
      }
      else
      {
        downloadButton.setDisable(false);
      }
    }
  }

  @FXML
  public void addToDownloadQueueButtonPressed(ActionEvent event)
  {
    LocalDate ld = dp.getValue();
    String date = String.format("%02d-%02d-%04d", ld.getMonthValue(), ld.getDayOfMonth(),
        ld.getYear());
    addDateToQueue(date);
  }

  private void addDateToQueue(String date)
  {
    downloadQueue.add(date);
    Label l = new Label(date);
    l.setOnMouseClicked(new EventHandler<MouseEvent>()
    {
      @Override
      public void handle(MouseEvent arg0)
      {
        downloadQueue.remove(date);
        downloadQueueBox.getChildren().remove(l);
      }
    });
    downloadQueueBox.getChildren().add(l);
  }

  private void addDateToAverage(LocalDate ld)
  {
    HourlySalesDay hsd = AppDirector.dataHub.getHourlySalesDay(ld.getYear(), ld.getMonthValue(),
        ld.getDayOfMonth());
    String date = String.format("%02d-%02d-%04d", ld.getMonthValue(), ld.getDayOfMonth(),
        ld.getYear());
    averages.add(hsd);
    Label l = new Label(date);
    l.setOnMouseClicked(new EventHandler<MouseEvent>()
    {
      @Override
      public void handle(MouseEvent arg0)
      {
        averages.remove(hsd);
        avgDateBox.getChildren().remove(l);
      }
    });
    avgDateBox.getChildren().add(l);

    itemBox.getChildren().clear();
    ArrayList<ArrayList<HourlySalesItem>> map = new ArrayList<ArrayList<HourlySalesItem>>();
    for(int ii = 0; ii < 24; ii++)
    {
      map.add(new ArrayList<HourlySalesItem>());
    }
    for (HourlySalesDay hsds : averages)
    {
      for (int ii = 0; ii < hsds.size(); ii++)
      {
        if (map.get(ii) == null)
        {
          map.add(ii, new ArrayList<HourlySalesItem>());
        }
        map.get(ii).add(hsds.get(ii));
      }
    }
    for (ArrayList<HourlySalesItem> hsis : map)
    {
      itemBox.getChildren().addAll(GuiUtilFactory.createHourlyItemBox(hsis), new Separator());
    }
  }
  
  @FXML
  public void averageCheckClicked()
  {
    if(!averageCheck.isSelected())
    {
      averages.clear();
      avgDateBox.getChildren().clear();
    }
  }
}
