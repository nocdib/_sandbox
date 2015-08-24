// Chapter 6 Exercise 6
/*
 Implement a method in the LinkedList class to insert an object following an object passed as an argument.
 Assume the objects stored in the list implement an equals() method that compares the this object
 with an object passed as an argument and returns true if they are equal.
 */

public class TryPolyLine {
   public static void main(String[] args) {
      // Create an array of coordinate pairs:
      double[][] coords = { {1., 1.}, {1., 2.}, { 2., 3.},
                            {-3., 5.}, {-5., 1.}, {0., 0.} };

      // Create a polyline from the coordinates and display it:
      PolyLine polygon = new PolyLine(coords);
      int count = polygon.length();                                    // Get length of polyline
      System.out.println("Polyline has " + count + " points.");
      polygon.show();

      // Add a point and display the polyline again:
      System.out.println("\nAdding a point.");
      polygon.addPoint(10., 10.);
      count = polygon.length();                                        // Get length of polyline
      System.out.println("\nPolyline has " + count + " points.");
      polygon.show();

      // Create Point objects from the coordinate array:
      Point[] points = new Point[coords.length];
      for(int i = 0 ; i < points.length ; ++i)
         points[i] = new Point(coords[i][0],coords[i][1]);

      // Use the points to create a new polyline and display it:
      PolyLine newPoly = new PolyLine(points);
      count = newPoly.length();                                        // Get length of polyline
      System.out.println("\nPolyline has " + count + " points.");
      newPoly.show();
      count = newPoly.length();                                        // Get length of polyline
      System.out.println("\nPolyline has " + count + " points." +
                         " Inserting a point after point number " + (count/2));

      int insertCount = count/2;
      Point point = newPoly.getFirst();
      while(--insertCount > 0) {
        point = newPoly.getNext();
      }

      System.out.println("\nCurrent point is " + point);

      newPoly.insert(new Point(99.0,99.0));                            // Insert a point.
      count = newPoly.length();                                        // Get length of polyline
      System.out.println("\nPolyline has " + count + " points.");
      newPoly.show();                                                  // Output the polyline.

      if(newPoly.insert(new Point(88.0, 88.0), new Point(99.0, 99.0))) {
       System.out.println("\nInsertion after a given point successful.");
      } else {
       System.out.println("\nInsertion after a given point failed.");
      }
      count = newPoly.length();                                        // Get length of polyline
      System.out.println("\nPolyline has " + count + " points.");
      newPoly.show();                                                  // Output the polyline.

      // Now delete the first point we inserted:
      int deleteCount = 1 + count/2;                                   // Count to position of point.

      // Go to the point we want to delete:
      point = newPoly.getFirst();
      while(--deleteCount > 0) {
        point = newPoly.getNext();
      }
      System.out.println("\nDeleting the current point which is " + point);

      newPoly.delete();                                                // Delete the point.
      count = newPoly.length();                                        // Get length of polyline
      System.out.println("\nPolyline has " + count + " points.");
      newPoly.show();                                                  // Output the polyline.
   }
}

