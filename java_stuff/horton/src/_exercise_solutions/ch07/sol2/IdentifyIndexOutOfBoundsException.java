// Chapter 7 Exercise 2

// Exception class recording index value for an ArrayIndexOutOfBoundsException
/*
 Because the class extends Exception, exceptions of this type must be caught.
 This exception type can be chained to an originating exception object.

 Unless the constructor with the index parameter is called, an index vaalue won't be set.
 Therefore thaere must be a way to determine whether the invalid index value is available.
 The hasIndex() method that returns gotIndex does this. gotIndex will only be true when the
 constructor that sets the index value is called.
 */
public class IdentifyIndexOutOfBoundsException extends Exception {
  // Default Constructor
  public IdentifyIndexOutOfBoundsException(){ }

  // Constructor that can be chained
  public IdentifyIndexOutOfBoundsException(String s, Throwable cause) {
    super(s, cause);                                                   // Call the base constructor
  }

  // Contructor recording an index value & can be chained
  public IdentifyIndexOutOfBoundsException(int index, Throwable cause) {
    super(cause);                                                      // Call the base constructor
    this.index = index;                                                // Set the index value
    gotIndex = true;                                                   // Set index flag
  }

  // Text for when an index value is available
  boolean hasIndex() {
    return gotIndex;
  }
  // Get the array index value for the error
  public int getIndex() {
    return index;                                                      // Return the index value
  }

  private boolean gotIndex = false;                                    // True indicates an index value is available
  private int index = 0;                                               // In index in error
}
