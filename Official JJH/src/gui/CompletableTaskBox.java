package gui;

import javafx.scene.control.CheckBox;
import util.Completable;

public class CompletableTaskBox<E extends Completable> extends CheckBox
{
  private E completable;

  public CompletableTaskBox(E completable)
  {
    this.completable = completable;
    setText(completable.getDesc());
    setWrapText(true);
    setStyle("-fx-text-fill: white;");
    
    
  }
  
  public E getTask()
  {
    return completable;
  }
}
