// Chapter 6 Exercise 2
/*
 Define a class, ShapeList, that can store an arbitrary collection of any objects of subclasses of the Shape class.
*/

// Tests the operation of the ShapeList class:

public class TestShapeList {
  public static void main(String args[]) {
    ShapeList shapes = null;                                           // Stores a shape list.
    Shape shape = null;                                                // Stores a shape.
    int shapeCount = 10;                                               // Number of shapes.
    Point[] pts = null;                                                // Declare a variable referencing an array of points.
    Point pt1 = null;                                                  // First working Point store.
    Point pt2 = null;                                                  // Second working Point store.
    int choice = 0;                                                    // Selector for shape type.
    final double minRadius = 2.0;                                      // Minimum radius of a circle.
    final double maxRadius = 20.0;                                     // Maximum radius of a circle.
    final double maxDist = 100.0;                                      // Maximum x,y coordinate value.
    final int minPts = 2;                                              // Minimum number of points in PolyLines.
    final int maxPts = 15;                                             // Maximum number of points in PolyLines.

    for(int i = 0 ; i < shapeCount ; ++i) {
      // Generate one of four random shapes:
      choice = (int)(4.0*Math.random());                               // Shape choice 0 to 3.

      // Coordinates are cast to int just to make the output less messy.
      // The compiler will arrange to cast the integer arguments back to double.

      switch(choice) {
        case 0:       // Generate a Line:
          // Generate two random points:
          pt1 = new Point((int)(Math.random()*maxDist),(int)(Math.random()*maxDist));
          pt2 = new Point((int)(Math.random()*maxDist),(int)(Math.random()*maxDist));
          shape = new Line(pt1,pt2);
          break;

        case 1:      // Generate a Circle:
          pt1 = new Point((int)(Math.random()*maxDist), (int)(Math.random()*maxDist));
          shape = new Circle(pt1, (int)(minRadius + (maxRadius - minRadius)*Math.random()));
          break;

        case 2:      // Generate a Rectangle:
          // Generate two random points
          pt1 = new Point((int)(Math.random()*maxDist),(int)(Math.random()*maxDist));
          pt2 = new Point((int)(Math.random()*maxDist),(int)(Math.random()*maxDist));
          shape = new Rectangle(pt1,pt2);
          break;

        case 3:      // Generate a PolyLine:
          // Create a Point array with a random number of elements:
          pts = new Point[minPts + (int)((maxPts - minPts)*Math.random())];

          // Fill array with random points:
          for(int j = 0 ; j < pts.length ; ++j) {
            pts[j] = new Point((int)(Math.random()*maxDist), (int)(Math.random()*maxDist));
          }

          shape = new PolyLine(pts);                                   // Create a PolyLine:
          break;

        default:                                                      // We should never get to here.
          System.out.println("\nInvalid shape choice = " + choice);
          System.exit(1);
          break;
      }
      if(shapes == null) {
        shapes = new ShapeList(shape);
      } else {
        shapes.add(shape);
      }
    }

    // Output all the shapes in the Shape array:
    shapes.show();
  }
}
