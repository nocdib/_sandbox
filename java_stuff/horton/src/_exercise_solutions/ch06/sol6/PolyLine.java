// Chapter 6 Exercise 6

// Modified to iterate through the points and insert and delete points:

public class PolyLine {
   LinkedList polyline;                                                // The linked list of points.

   // Construct a polyline from an array of coordinate pairs:
   public PolyLine(double[][] coords) {
     Point[] points = new Point[coords.length];                       // Array to hold points.

      // Create points from the coordinates:
      for(int i = 0 ; i < coords.length ; ++i) {
        points[i] = new Point(coords[i][0], coords[i][1]);
      }

      // Create the polyline from the array of points:
      polyline = new LinkedList(points);
   }

   // Construct a polyline from an array of points
   public PolyLine(Point[] points) {
      polyline = new LinkedList(points);                               // Create the polyline.
   }

   // Add a Point object to the list:
   public void addPoint(Point point) {
     polyline.addItem(point);                                          // Add the point to the list.
   }

   // Add a point from a coordinate pair to the list:
   public void addPoint(double x, double y) {
     polyline.addItem(new Point(x, y));                                // Add the point to the list.
   }

   // Output the polyline in reverse order:
   public void show() {
     System.out.println("Polyline points are:");

     // Set the 1st point as start:
     Point nextPoint = (Point)polyline.getLast();                      //  ***

     // Output the points:
     while(nextPoint != null) {
       System.out.println(nextPoint);                                  // Output the current point.
       nextPoint = (Point)polyline.getPrevious();                      // Get the next point.
     }
   }

   // Get the first point:
   public Point getFirst() {
     return (Point)polyline.getFirst();
   }

   // Get the next point after current:
   public Point getNext() {
     return (Point)polyline.getNext();
   }

   // Insert a point after current:
   public void insert(Point point) {
     polyline.insertItem(point);
   }

   // Delete the current point:
   void delete() {
     polyline.deleteItem();
   }

   // Get the length of a polyline
   public int length() {
    return polyline.size();
  }
   // Method to insert point after the Point, position:
   public boolean insert(Point point, Point position) {
      return polyline.insertItem(point, position);
   }
}

