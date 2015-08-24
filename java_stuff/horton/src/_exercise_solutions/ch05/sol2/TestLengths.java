// Chapter 5, Exercise 2

/*
 Define a class, mcmLength, to represent a length measured in meters, centimeters, and millimeters,
 each stored as integers. Include methods to add and subtract objects, to multiply and divide an object by an integer value,
 to calculate an area resulting from the product of two objects, and to compare objects.
 Include constructors that accept three arguments—meters, centimeters, and millimeters;
 one integer argument in millimeters; one double argument in centimeters; and no arguments,
 which creates an object with the length set to zero. Check the class by creating some objects and testing the class operations.
 */

public class TestLengths {
  public static void main(String args[]) {
    mcmLength[] lengths = new mcmLength[4];

    // Test the constructors:
    lengths[0] = new mcmLength(274.65);
    lengths[1] = new mcmLength(274);
    lengths[2] = new mcmLength(274,2,3);
    lengths[3] = new mcmLength();

    //Display the figures:
    for(int i = 0 ; i < lengths.length ; ++i) {
      System.out.println("Length " + i + " is " + lengths[i]);
    }

    // Test the arithmetic and area operations
    System.out.println("Addition: " + lengths[0] + " plus " + lengths[1] + " is " + lengths[0].add(lengths[1]));
    System.out.println("Subtraction: " + lengths[0] + " minus " + lengths[1]+ " is " + lengths[0].subtract(lengths[1]));
    System.out.println("Multiplication: " + lengths[0] + " times 10 is " + lengths[0].multiply(10));
    System.out.println("Division: " + lengths[0] + " divided by 10 is " + lengths[0].divide(10));
    System.out.println("Area: "  + lengths[0] + " by " + lengths[1] + " is " + lengths[0].area(lengths[1]) + " square mm");

    // Test comparison methods
    if(lengths[0].greaterThan(lengths[1])) {
      System.out.println("Length "+ lengths[0] + " is greater than length " + lengths[1]);
    } else if(lengths[0].lessThan(lengths[1])) {
      System.out.println("Length "+ lengths[0] + " is less than length " + lengths[1]);
    } else {
      System.out.println("Length "+ lengths[0] + " is the same length as length " + lengths[1]);
    }

    // Compare successive lengths using compare() method
    String compareStr = null;
    for(int i = 0 ; i < lengths.length-1 ; ++i) {
      switch(lengths[i].compare(lengths[i+1])) {
        case -1:
         compareStr = " is less than length ";
         break;
        case 0:
         compareStr = " is equal to length ";
         break;
        case 1:
         compareStr = " is greater than length ";
         break;
      }
      System.out.println("Length " + lengths[i] + compareStr + lengths[i+1]);
    }
  }
}