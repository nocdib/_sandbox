//Chapter 3, Exercise 2
/*
  When testing whether an integer is a prime, it is sufficient to try to divide by integers
  up to and including the square root of the number being tested.
  Rewrite the program example from this chapter to use this approach.
 */

public class Primes {
  public static void main(String args[]) {
    int maxValue = 50;                                                 // The maximum value to be checked.

    // Check all values from 2 to maxValue:
    OuterLoop:
    for(int i = 2 ; i <= maxValue ; ++i) {
      // Try dividing by all integers from 2 to square root of i:
      for(int j = 2 ; j <= Math.sqrt(i) ; ++j) {
        if(i%j == 0)                                                   // This is true if j divides exactly
          continue OuterLoop;                                          // so exit the loop.
      }
      // We only get here if we have a prime:
      System.out.println(i);                                           // so output the value.
    }
  }
}

