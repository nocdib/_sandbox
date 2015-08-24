// Chapter 6 Exercises 1 & 2

public class Point {
  // No-arg constructor
  public Point(){}

  // Construct a Point from its coordinates:
  public Point(double x, double y) {
    this.x = x;
    this.y = y;
  }

  // Construct a Point from another Point:
  public Point(Point point) {
    x = point.x;
    y = point.y;
  }

  // Method to return a point defined relative to this
  // point in coordinates:
  public Point add(Point z) {
    return new Point(x + z.x, y + z.y);
  }

  @Override
  public String toString() {
    return "Point: (" + x + "," + y + ")";
  }

  protected double x;
  protected double y;
}
