package time_updates;

import java.util.TimerTask;

import controllers.HubController;

public class TimeUpdateMinute extends TimerTask
{
  private HubController controller;
  
  public TimeUpdateMinute(HubController controller)
  {
    this.controller = controller;
  }
  
  @Override
  public void run()
  {
    controller.timeUpdateMinute();
  }

}
