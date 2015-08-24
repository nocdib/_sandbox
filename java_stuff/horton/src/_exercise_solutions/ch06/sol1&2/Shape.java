// Chapter 6 Exercises 1 & 2

// ABSTRACT BASE CLASS FOR SHAPE.
// The move() method that  is implemented here assumes shapes are defined
// relative to the reference point, position, so subclasses of this class
// must be defined in this way. Where this is not convenient an alternative is
// to implement a separate move() method in each derived class.

public abstract class Shape {
// Method to move a shape:
  public void move(double xDelta, double yDelta) {
    // Move the shape by xDelta in x, and yDelta in y:
    position.x += xDelta;
    position.y += yDelta;
  }

  public abstract void show();                                         // Abstract method to output a shape.

  protected Point position;                                            // Position of a shape.
}
