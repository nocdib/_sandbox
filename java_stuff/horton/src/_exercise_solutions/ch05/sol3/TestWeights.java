// Chapter 5 Exercise 3
/*
 Define a class, tkgWeight, to represent a weight in tons, kilograms, and grams,
 and include a similar range of methods and constructors as the previous example.
 Demonstrate this class by creating and combining some class objects.
 */

public class TestWeights {

  public static void main(String args[]) {
   // Test the constructors
    tkgWeight[] weights = {
                             new tkgWeight(274.65) , new tkgWeight(274),
                             new tkgWeight(274,2,3), new tkgWeight()
                          };

    //Display the weights
    for(int i = 0 ; i < weights.length ; ++i)
    System.out.println("Weight " + i + " is " + weights[i]);

   // Test the operations
    System.out.println("Addition: " + weights[0] + " plus " + weights[1] + " is " + weights[0].add(weights[1]));
    System.out.println("Subtraction: " + weights[0] + " minus " + weights[1] + " is " + weights[0].subtract(weights[1]));
    System.out.println("Multiplication: "+ weights[0] + " times 10 is " + weights[0].multiply(10));
    System.out.println("Division: " + weights[0] + " divided by 10 is " + weights[0].divide(10));

    // Test comparison methods
    if(weights[0].greaterThan(weights[1])) {
      System.out.println("Weight "+ weights[0] + " is greater than length " + weights[1]);
    } else if(weights[0].lessThan(weights[1])) {
      System.out.println("Weight "+ weights[0] + " is less than length " + weights[1]);
    } else {
      System.out.println("Weight "+ weights[0] + " is the same length as length " + weights[1]);
    }

    // Compare successive weights using compare() method
    String compareStr = null;
    for(int i = 0 ; i < weights.length - 1 ; ++i) {
      switch(weights[i].compare(weights[i+1])) {
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
      System.out.println("Weight " + weights[i] + compareStr + weights[i+1]);
    }
  }
}