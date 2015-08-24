// Chapter 6 Exercises 1 & 2

public class Line extends Shape {
  // Constructor
  public Line(Point start, Point end) {
    // Assume line is drawn from reference point:
    position = new Point(start); 	                                     // First point is position.
    this.end = new Point(end.x - start.x, end.y - start.y);            // Get end point coords relative to start.
  }

  @Override
  public String toString() {
    // Create a string representation of the object:
    return "Line: " + position + " to " + position.add(end);
  }

  // Method to display a line:
  public void show() {
    System.out.println("\n" + toString());
  }

  // End point is defined relative to start point:
  private Point end;                                                   // Line end point.
}
