// Chapter 7 Exercise 2
/*
 Add an exception class to the last example that differentiates between
 the index-out-of-bounds error possibilities,
 rethrows an appropriate object of this exception class in divide(),
 and handles the exception in main().
*/

public class TryChainedExceptions {

  public static void main(String[] args) {
    int[] x = {10, 5, 0, 3, 12, 0, 6};                                 // Array of  integers
    System.out.print("Values: ");
    for(int xValue : x) {
      System.out.print("  " + xValue);
    }
    System.out.println();

    for(int i = 0 ; i < x.length ; ++i) {
      // This block only throws an exception if method divide() does
      try {
        System.out.println("First try block in main()entered");
        System.out.println("result = " + divide(x,i));
      } catch(ZeroDivideException e) {
        System.out.println("\nZeroDivideException caught in main()");
        int index = e.getIndex();                                      // Get the index for the error
        if(index > 0) {                                                // Verify it is valid and now fix the array
          x[index] = 1;                                                // ...set the divisor to 1...
          x[index + 1] = x[index - 1];                                 // ...and set the result
          e.printStackTrace();
          System.err.println("Zero divisor at x[" + index + "] corrected to " + x[index]);
        }
      } catch(ArithmeticException e) {
        System.out.println("Arithmetic exception caught in main()");
      } catch(IdentifyIndexOutOfBoundsException e) {
        System.out.println("Precise-index-out-of-bounds exception caught in main()");

        Throwable original = e.getCause();
        if(original != null) {
          System.out.println("Original exception was: " + original);
        }

        if(e.hasIndex()){
        System.out.println("Index value out of bounds is: " + e.getIndex());
        }
      }
      System.out.println("Outside first try block in main()");
    }
    System.out.print("Values: ");
    for(int xValue : x) {
      System.out.print("  " + xValue);
    }
    System.out.println();
  }

  public static int divide(int[] array, int index) throws ZeroDivideException, IdentifyIndexOutOfBoundsException {
    try {
      System.out.println("\nFirst try block in divide() entered");
      array[index] = array[index+2]/array[index + 1];
      System.out.println("Code at end of first try block in divide()");
      return array[index + 2];

    } catch(ArithmeticException e) {
      System.out.println("Arithmetic exception caught in divide()");
      ZeroDivideException zde = new ZeroDivideException(index + 1, e);
      System.out.println("Throwing ZeroDivideException");
      throw zde;                                                       // Throw the new exception
    } catch(ArrayIndexOutOfBoundsException e) {
      System.out.println("Index-out-of-bounds index exception caught in divide()");

      // We need to figure which of the three index values caused the problem
      int errorIndex = 0;                                              // Record the index value in error
      if(index < 0 || index >= array.length) {                         // Check for negative index or index greater than array length
        errorIndex = index;
      } else {
        // Two possibilities remain. The error index value was either
        // index+1 or index+2
        errorIndex = index + 1 > array.length ? index + 1 : index + 2;
      }
      throw new IdentifyIndexOutOfBoundsException(errorIndex, e);
    }
    // Because we now throw an exception in the previous catch block,
    // the following code annot be reached so it is commented out.
//    System.out.println("Executing code after try block in divide()");
//    return array[index];
  }
}
