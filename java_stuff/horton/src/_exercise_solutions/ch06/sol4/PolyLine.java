// Chapter 6 Exercise 4

// This has been modified to output the point in reverse order
// to test the new LinkedList capability for traversing a list backwards

public class PolyLine {

  // Construct a polyline from an array of coordinate pairs:
  public PolyLine(double[][] coords) {
    Point[] points = new Point[coords.length];                         // Array to hold points.

    // Create points from the coordinates:
    for(int i = 0 ; i < coords.length ; ++i) {
      points[i] = new Point(coords[i][0], coords[i][1]);
    }

    // Create the polyline from the array of points:
    polyline = new LinkedList(points);
  }

  // Construct a polyline from an array of points:
  public PolyLine(Point[] points) {
    polyline = new LinkedList(points);                                 // Create the polyline.
  }

  // Add a Point object to the list:
   public void addPoint(Point point) {
    polyline.addItem(point);                                           // Add the point to the list.
   }

  // Add a point from a coordinate pair to the list:
   public void addPoint(double x, double y) {
     polyline.addItem(new Point(x, y));                                // Add the point to the list.
  }

  // Output the polyline in reverse order:
  public void show() {
    System.out.println("\nPolyline points are:");

    // Set the 1st point as start:
    Point nextPoint = (Point)polyline.getLast();                       //  ***

    // Output the points:
    while(nextPoint != null) {
      System.out.println(nextPoint);                                   // Output the current point.
      nextPoint = (Point)polyline.getPrevious();                       // Get the next point.
    }
  }

  protected LinkedList polyline;                                       // The linked list of points.
}

