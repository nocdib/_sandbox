// Chapter 7 Exercise 2

public class ZeroDivideException extends Exception {
  private int index = -1;                                              // Index of array element causing error

  // Default Constructor
  public ZeroDivideException(){ }

  // Constructor that can be chained
  public ZeroDivideException(String s, Throwable cause) {
    super(s, cause);                                                   // Call the base constructor
  }

  // Contructor recording an index value & can be chained
  public ZeroDivideException(int index, Throwable cause) {
    super(cause);                                                      // Call the base constructor
    this.index = index;                                                // Set the index value
  }

  // Get the array index value for the error
  public int getIndex() {
    return index;                                                      // Return the index value
  }
}
