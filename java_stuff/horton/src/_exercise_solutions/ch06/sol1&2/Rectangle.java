// Chapter 6 Exercises 1 & 2

public class Rectangle extends Shape {
// Constructor
  public Rectangle(Point startDiag, Point endDiag) {
    // Position of rectangle is top left corner - minimum x and y:
    position = new Point(Math.min(startDiag.x, endDiag.x), Math.min(startDiag.y, endDiag.y));

    // Bottom right is relative to top left:
    bottomRight = new Point(Math.max(startDiag.x,endDiag.x) - position.x,
                            Math.max(startDiag.y,endDiag.y) - position.y);
  }

  @Override
  public String toString()  {
    // Return a string representation of the object:
    return "Rectangle: Top Left: " + position + " Bottom Right: " + position.add(bottomRight);
  }

  // Display the rectangle:
  public void show() {
    System.out.println("\n" + toString());
  }

  // Bottom right of rectangle is defined relative to the reference point, position:
  private Point bottomRight;        // Bottom right of rectangle.
}
