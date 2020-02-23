package util;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class TimeFile extends ComparableFile<TimeFile>
{
  private GregorianCalendar downloadTime;

  public TimeFile(File file) throws ParseException
  {
    super(file);
    downloadTime = new GregorianCalendar();
    if (getName().endsWith(".csv"))
    {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HHmmss");
      System.out.println(getName());
      downloadTime
          .setTime(sdf.parse(getName().substring(getName().length() - 25, getName().length() - 8)));
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
