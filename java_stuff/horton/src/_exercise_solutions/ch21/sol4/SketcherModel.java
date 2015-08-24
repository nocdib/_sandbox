import java.io.Serializable;
import java.util.*;
import java.awt.Rectangle;

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

  // Get the rectangle enclosing an entire sketch
  Rectangle getModelExtent() {
    Rectangle rect = new Rectangle();                                   // An empty rectangle
    for(Element element : elements) {                                   // Go through the list
      rect.add(element.getBounds());                                    // Expand union
    }
    if(rect.width == 0) {                                               // Make sure width
      rect.width = 2;                                                   // is non-zero
    }
    if(rect.height == 0) {                                              // and the height
      rect.height = 2;
    }
    return rect;
  }

  protected LinkedList<Element> elements = new LinkedList<>();
  private final static long serialVersionUID = 1001L;
}
