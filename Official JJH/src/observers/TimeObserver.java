package observers;

public interface TimeObserver
{
  public void timeUpdateMinute();
  public void shiftChangeTo(int currentShift);
}
