package util;

import java.util.ArrayList;

public class ParseUtil
{
  public static ArrayList<String> getQuoteConsolidatedList(String[] tokens)
  {
    ArrayList<String> consolidated = new ArrayList<String>();
    for (int ii = 0; ii < tokens.length; ii++)
    {
      String full = tokens[ii];
      if (!full.equals(""))
      {
        if (full.charAt(0) == '"')
        {
          if (tokens[ii].charAt(tokens[ii].length() - 1) != '\"')
          {
            full = full.substring(1);
            ii++;
            while (tokens.length > ii && tokens[ii].charAt(tokens[ii].length() - 1) != '\"')
            {
              full += tokens[ii];
              ii++;
            }
            full += tokens[ii].substring(0, tokens[ii].length() - 1);
          }
          else
          {
            full = tokens[ii].substring(0, tokens[ii].length() - 1);
          }
        }
      }
      consolidated.add(full);
    }
    return consolidated;
  }

  public static String parse$(String string)
  {
    if(string.indexOf("$") == -1)
      return string;
    if (string.charAt(0) == '$')
      return string.substring(1);
    else
      return string.substring(0, string.indexOf("$"))
          + string.substring(string.indexOf("$") + 1, string.length());
  }

  /**
   * @param string
   * @return anything in between paren without including ()
   */
  public static String parseParen(String string)
  {
    if(string.indexOf("(") == -1)
      return string;
    return string.substring(string.indexOf("(") + 1, string.indexOf(")"));
  }

  public static String parsePerc(String string)
  {
    if(string.indexOf("%") == -1)
      return string;
    return string.substring(0, string.indexOf("%"));
  }
}
