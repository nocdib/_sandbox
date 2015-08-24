// Chapter 6 Exercises 1
/*
 Define an abstract base class Shape that includes protected data members for the (x, y) position of a shape,
 a public method to move a shape, and a public abstract method show() to output a shape.
 Derive subclasses for lines, circles, and rectangles. Also, define the class PolyLine
 that you saw in this chapter with Shape as its base class.
 You can represent a line as two points, a circle as a center and a radius,
 and a rectangle as two points on diagonally opposite corners.
 Implement the toString() method for each class.
 Test the classes by selecting ten random objects of the derived classes,
 and then invoking the show() method for each.
 Use the toString() methods in the implementation of show() in the derived classes.
 */

public class TestShapes {
  public static void main(String args[]) {
    Shape[] shapes = new Shape[10];	                                   // Define an array of shapes.
    Point[] pts = null;                                                // Stores a reference to an array of points.
    Point pt1 = null;                                                  // First working Point store.
    Point pt2 = null;                                                  // Second working Point store.
    double minRadius = 2.0;                                            // Minimum radius of a circle.
    double maxRadius = 20.0;                                           // Maximum radius of a circle.
    double maxDist = 100.0;                                            // Maximum x,y coordinate value.
    int minPts = 2;                                                    // Minimum number of points in PolyLines.
    int maxPts = 15;                                                   // Maximum number of points in PolyLines.
    int choice = 0;                                                    // Selector for shape type.

    for(int i = 0 ; i < shapes.length ; ++i) {
      // Generate one of four random shapes:
      choice = (int)(4.0*Math.random());                               // Shape choice 0 to 3.

      // Coordinates are cast to int just to remove the frctional part and make the output less messy.
      // The compiler will arrange to cast the integer arguments back to double.
      switch(choice) {
        case 0:      // Generate a Line:
          // Generate two random points:
          pt1 = new Point((int)(Math.random()*maxDist), (int)(Math.random()*maxDist));
          pt2 = new Point((int)(Math.random()*maxDist), (int)(Math.random()*maxDist));
          shapes[i] = new Line(pt1,pt2);                               // Add Line to Shape array.
          break;

        case 1:      // Generate a Circle:
          pt1 = new Point((int)(Math.random()*maxDist), (int)(Math.random()*maxDist));
          shapes[i] = new Circle(pt1, (int)(minRadius + (maxRadius-minRadius)*Math.random()));
          break;

        case 2:      // Generate a Rectangle:
          // Generate two random points:
          pt1 = new Point((int)(Math.random()*maxDist), (int)(Math.random()*maxDist));
          pt2 = new Point((int)(Math.random()*maxDist), (int)(Math.random()*maxDist));
          shapes[i] = new Rectangle(pt1,pt2);                          // Add Rectangle to Shape array.
          break;

        case 3:      // Generate a PolyLine:
          // Create a Point array with a random number of elements:
          pts = new Point[minPts + (int)((maxPts - minPts)*Math.random())];

          // Fill array with random points:
          for(int j = 0 ; j < pts.length ; ++j) {
            pts[j] = new Point((int)(Math.random()*maxDist), (int)(Math.random()*maxDist));
          }

          shapes[i] = new PolyLine(pts);                               // Add PolyLine to Shape array.
          break;

        default:                                                      // We should never get to here.
          System.out.println("\nInvalid shape choice = " + choice);
          System.exit(1);
          break;
      }
    }

    // Output all the shapes in the Shape array:
    for(Shape shape : shapes) {
      shape.show();
    }
  }
}
