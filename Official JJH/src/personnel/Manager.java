package personnel;

import java.io.Serializable;

public class Manager implements Serializable
{
  private static final long serialVersionUID = 7323292373048884329L;
  private String name, username, password, notes;
  
  /**
   * @param name
   * @param username
   * @param password
   * @param notes
   */
  public Manager(String name, String username, String password, String notes)
  {
    this.name = name;
    this.username = username;
    this.password = password;
    this.notes = notes;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getUsername()
  {
    return username;
  }

  public void setUsername(String username)
  {
    this.username = username;
  }

  public String getPassword()
  {
    return password;
  }

  public void setPassword(String password)
  {
    this.password = password;
  }

  public String getNotes()
  {
    return notes;
  }

  public void setNotes(String notes)
  {
    this.notes = notes;
  } 
  
  @Override
  public String toString()
  {
    return name;
  }
}
