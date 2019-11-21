package error_handling;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ErrorHandler
{
  private static ArrayList<Exception> errors = new ArrayList<Exception>();
  
  public static void addError(Exception error)
  {
    errors.add(error);
  }
  
  public ArrayList<Exception> getErrors()
  {
     return errors;
  }
  
  public static void writeErrors()
  {
    try
    {
      PrintWriter pw = new PrintWriter(new File("error.txt"));
      for(Exception e: errors)
      {
        e.printStackTrace(pw);
        pw.write("\n");
      }
      pw.flush();
      pw.close();
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
      addError(e);
    }
  }
}
