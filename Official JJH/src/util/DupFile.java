package util;

import java.io.File;

public class DupFile implements Comparable<DupFile>
{
  private int dupVal = 0;
  private File file;

  public DupFile(File file)
  {
    this.file = file;
    if (getName().endsWith(").csv"))
    {
      dupVal = Integer.parseInt(getName().substring(getName().lastIndexOf('(') + 1, getName().lastIndexOf(')')));
    }
  }

  public int getDupVal()
  {
    return dupVal;
  }

  public int compareTo(DupFile f)
  {
    if(dupVal == f.getDupVal())
      return 0;
    else if(dupVal < f.getDupVal())
      return -1;
    return 1;
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
