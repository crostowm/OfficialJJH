package error_handling;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ErrorHandler
{
  private static ArrayList<String> errors = new ArrayList<String>();
  
  public static void addError(String error)
  {
    errors.add(error);
  }
  
  public ArrayList<String> getErrors()
  {
     return errors;
  }
  
  public static void writeErrors()
  {
    try
    {
      PrintWriter pw = new PrintWriter(new File("error.txt"));
      for(String s: errors)
      {
        pw.write(s + "\n");
      }
      pw.flush();
      pw.close();
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }
  }
}
