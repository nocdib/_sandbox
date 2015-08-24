import java.io.Serializable;
import java.util.*;

public class SketcherModel extends Observable implements Serializable, Iterable<Element> {

  //Remove an element from the sketch
  public boolean remove(Element element) {
    boolean removed = elements.remove(element);
    if(removed) {
      setChanged();
      notifyObservers(element.getBounds());
    }
    return removed;
  }

  //Add an element to the sketch
  public void add(Element element) {
    elements.add(element);
    setChanged();
    notifyObservers(element.getBounds());
  }

  // Get iterator for sketch elements
  public Iterator<Element> iterator() {
    return elements.iterator();
  }


  protected LinkedList<Element> elements = new LinkedList<>();
  private final static long serialVersionUID = 1001L;
}
