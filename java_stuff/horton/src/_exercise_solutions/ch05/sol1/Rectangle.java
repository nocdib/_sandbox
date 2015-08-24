// Chapter 5, Exercise 1

// This is the Rectangle class definition. It makes use of
// the Point class definition that is in the same directory as this file.

public class Rectangle {
  private Point topLeft;
  private Point bottomRight;

  // Constructors
  public Rectangle(double x1, double y1, double x2, double y2) {
    // Ensure topleft uses the smaller of x1,x2 and y1,y2
    // and topright uses the larger of x1,x2 and y1,y2
    topLeft = new Point(Math.min(x1,x2),Math.min(y1,y2));
    bottomRight = new Point(Math.max(x1,x2),Math.max(y1,y2));
  }

  public Rectangle(Point tl, Point br) {
    this(tl.getX(), tl.getY(), br.getX(), br.getY());
  }

  public Rectangle(Rectangle rect) {
    topLeft = new Point(rect.topLeft);
    bottomRight = new Point(rect.bottomRight);
  }

  // Methods to create a rectangle enclosing the current rectangle and the argument
  public Rectangle encloses(Rectangle rect) {
    // Return a new rectangle defined by the minimum x,y for to left and the and maximum x,y for bottom right
    return new Rectangle(Math.min(topLeft.getX(), rect.topLeft.getX()),
                         Math.min(topLeft.getY(), rect.topLeft.getY()),
                         Math.max(bottomRight.getX(), rect.bottomRight.getX()),
                         Math.max(bottomRight.getY(), rect.bottomRight.getY()));

  }

  public String toString()  {
    return "Rectangle: " + topLeft + " : " + bottomRight;
  }
}


