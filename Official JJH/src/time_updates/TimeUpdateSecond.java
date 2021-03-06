package time_updates;

import java.util.TimerTask;

import controllers.HubController;

public class TimeUpdateSecond extends TimerTask
{
  private HubController controller;
  
  public TimeUpdateSecond(HubController controller)
  {
    this.controller = controller;
  }
  
  @Override
  public void run()
  {
    controller.timeUpdateSecond();
  }

}
