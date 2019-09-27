package time_updates;

import java.util.TimerTask;

import controllers.HubController;

public class TimeUpdate extends TimerTask
{
  private HubController controller;
  
  public TimeUpdate(HubController controller)
  {
    this.controller = controller;
  }
  
  @Override
  public void run()
  {
    System.out.println("Run");
    controller.timeUpdate();
  }

}
