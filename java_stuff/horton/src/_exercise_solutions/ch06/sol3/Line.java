// Chapter 6 Exercise 3

// This class now inherits implementation of the ShapeInterface interface
// from the Shape class so it must implement the show() method, otherwise
// it would be an abstract class.

public class Line extends Shape {
  // Constructor
  public Line(Point start, Point end) {
    // Assume line is drawn from reference point:
    position = new Point(start);                                       // First point is position.
    this.end = new Point(end.x - start.x, end.y - start.y);            // Get end point coords relative to start.
  }

  @Override
  public String toString()  {
    // Create a string representation of the Line object:
    return "Line: " + position + " to " + position.add(end);
  }

  // Method to display a line:
  public void show() {
    System.out.println("\n" + toString());
  }

  // End point is defined relative to start point:
  private Point end;                                                   // Line end point.
}
