// Chapter 6 Exercise 2

// CLASS TO DEFINE A LINKED LIST OF SHAPES.
// This class has an inner class defining a node in a linked list.
// Because Shape is a super-class of the classes defining specific shapes,
// a variable of type Shape can store any kind of shape of a sub-class type.

public class ShapeList {
  // Construct a ShapeList from a single shape:
  public ShapeList(Shape shape) {
    // Create a one shape list:
    start = new ShapeNode(shape);
    end = start;
  }

  // Construct a ShapeList from an array of Shapes:
  public ShapeList(Shape[] shapes) {
    this(shapes[0]);                                                   // Create a one shape list.

    // Now add the other shapes:
    for(int i = 1 ; i < shapes.length ; ++i) {
      add(shapes[i]);
    }
  }

  // Add a shape to the list:
  public void add(Shape data) {
    ShapeNode newEnd = new ShapeNode(data);                            // Create a new list node
    if(start == null) {                                                // If the list is empty
      end = start = newEnd;                                            // set the first & last
    } else {                                                           // references to refer to new shape
      end.setNext(newEnd);                                             // Otherwise set next reference to new shape's reference.
      end = newEnd;                                                    // Update end reference.
    }
  }


  // Output all the shapes in the list:
  public void show() {
    System.out.println("\nShapeList shapes are:");
    ShapeNode current = start;                                         // Set current to reference start.

    while(current != null) {                                           // If there is a current shape.
      current.show();                                                  // Display it.
      current = current.getNext();                                     // Get the next element in the list.
    }
  }

  private ShapeNode start;                                             // Reference to first Shape in List.
  private ShapeNode end;                                               // Reference to last Shape in List.

  // Inner class defines a node in a linked list of shapes:
  private class ShapeNode {
    // Constructor:
    public ShapeNode(Shape shape) {
      this.shape = shape;                                              // Store the Shape object.
      next = null;                                                     // Initialize the next reference.
    }

    // Method to set the reference to the next shape:
    public void setNext(ShapeNode next) {
      this.next = next;
    }

    // Get the reference to the next shape:
    public ShapeNode getNext()  {
      return next;
    }

    @Override
    public String toString() {
      return shape.toString();
    }

    // Output the shape contained in the ShapeNode:
    public void show() {
      System.out.println(toString());
    }

    ShapeNode next;                                                    // A reference to the next shape node in the list.
    Shape shape;                                                       // A reference to the shape object.
  }
}
