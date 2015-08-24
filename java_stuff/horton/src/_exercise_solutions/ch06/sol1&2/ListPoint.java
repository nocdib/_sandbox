// Chapter 6 Exercises 1 & 2

public class ListPoint extends Point {
  // Constructor to create an object from coordinates:
  public ListPoint(double x, double y) {
    super(x, y);                                                       // Call Point constructor.
    next = null;                                                       // Set next as end point.
  }

  // Constructor to create an object from a Point object:
  public ListPoint(Point point) {
    super(point);
    next = null;
  }

  // Set the pointer to the next ListPoint:
  public void setNext(ListPoint next) {
    this.next = next;
  }

  // Get the next point in the list:
  public ListPoint getNext() {
    return next;
  }

  // Return class name & coordinates:
  @Override
  public String toString() {
    return "ListPoint (" + x + "," + y + ")";
  }

  ListPoint next;                                                     // Points to next point in the list.
}
