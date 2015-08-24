//Chapter 5, Exercise 1
/*
 Define a class for rectangle objects defined by two points,
 the top-left and bottom-right corners of the rectangle.
 Include a constructor to copy a rectangle, a method to return a rectangle object
 that encloses the current object and the rectangle passed as an argument,
 and a method to display the defining points of a rectangle.
 Test the class by creating four rectangles and combining these cumulatively
 to end up with a rectangle enclosing them all.
 Output the defining points of all the rectangles you create.
 */

// This tests the Rectangle class that appears in the same directory as this.

public class TestRectangle {
  public static void main(String args[]) {
    Rectangle[] rects = new Rectangle[4];
    Rectangle enclosing;

    // Initialize the rectangles as arbitrary sizes and at arbitrary positions:
    for(int i = 0 ; i < rects.length ; ++i) {
      rects[i] = new Rectangle(Math.random()*100, Math.random()*100, Math.random()*100, Math.random()*100);
      System.out.print("Coords of rectangle " + i + " are: ");
      System.out.println(rects[i]);
    }

    // Initialize the enclosing rectangle to be first rectangle
    enclosing = rects[0];

    // Combine it with each the other rectangles in turn.
    // This will result in the rectangle that encloses them all.
    for(Rectangle rect : rects)  {
      enclosing = enclosing.encloses(rect);
    }

    System.out.println("\nCoords of Enclosing rectangle are ");
    System.out.println(enclosing);
  }
}