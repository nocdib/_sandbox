// Chapter 7 Exercise 1
/*
 Write a program that generates exceptions of type NullPointerException, NegativeArraySizeException, and IndexOutOfBoundsException.
 Record the catching of each exception by displaying the message stored in the exception object and the stack trace record.
 */
public class TestSimple {

  public static void main(String args[]) {
    int result = 0;                                                    // Somewhere to store a result.
    int arraySize = 3;
    int badSize = -1;                                                  // A negative size to generate NegativeArraySizeException.
    int[] array = null;                                                // Define an integer array variable.

    for(int choice = 0; choice<3 ; ++choice)
    try {
      switch(choice) {
        case 0:
         result = array[0];                                            // Generates a NullPointerException.
         break;
        case 1:
         array = new int[badSize];                                     // Generates a NegativeArraySizeException.
         break;
        case 2:
         array = new int[arraySize];
         result = array[arraySize];                                    // Generates an ArrayIndexOutOfBoundsException.
         break;
      }
    } catch(NullPointerException e) {                                  // Catch the NullPointerException:
      System.out.println("\nNullPointerException Exception caught. " +
                         "Message in NullPointerException is:\n" +
                         "\n" + e + "\n");
      System.out.println("The contents of the stack trace are:\n");
      e.printStackTrace();
    } catch(NegativeArraySizeException e) {                            // Catch the NegativeArraySizeException:
      System.out.println("\nNegativeArraySizeException Exception caught. " +
                         "Message in NegativeArraySizeException is:\n" +
                         "\n" + e + "\n");
      System.out.println("The contents of the stack trace are:\n");
      e.printStackTrace();
    } catch(ArrayIndexOutOfBoundsException e) {                        // Catch the ArrayIndexOutOfBoundsException:
      System.out.println("\nArrayIndexOutOfBoundsException Exception caught. " +
                         "Message in ArrayIndexOutOfBoundsException is:\n" +
                         "\n" + e + "\n");
      System.out.println("The contents of the stack trace are:\n");
      e.printStackTrace();
    }
  }
}
