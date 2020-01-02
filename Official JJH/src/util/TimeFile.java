package util;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import error_handling.ErrorHandler;

public class TimeFile extends ComparableFile<TimeFile>
{
  private GregorianCalendar downloadTime;

  public TimeFile(File file)
  {
    super(file);
    downloadTime = new GregorianCalendar();
    if (getName().endsWith(".csv"))
    {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HHmmss");
      try
      {
        System.out.println(getName());
        downloadTime.setTime(
            sdf.parse(getName().substring(getName().length() - 25, getName().length() - 8)));
      }
      catch (ParseException e)
      {
        e.printStackTrace();
        ErrorHandler.addError(e);
      }
    }
  }

  @Override
  public int compareTo(TimeFile tf)
  {
    return downloadTime.compareTo(tf.getDownloadTime());
  }

  public GregorianCalendar getDownloadTime()
  {
    return downloadTime;
  }
}
