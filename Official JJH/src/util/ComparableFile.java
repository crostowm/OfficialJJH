package util;

import java.io.File;

public abstract class ComparableFile<T> implements Comparable<T>
{
  protected File file;
  
  public ComparableFile (File file)
  {
    this.file = file;
  }
  
  public String getName()
  {
    return file.getName();
  }

  public File getFile()
  {
    return file;
  }
}
