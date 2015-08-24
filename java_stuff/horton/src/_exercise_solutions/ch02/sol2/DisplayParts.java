package _exercise_solutions.ch02.sol2;

//Chapter 2, Exercise 2

/*
 Write a console program to declare and initialize a double variable
 with some value such as 1234.5678.
 Retrieve the integral part of the value and store it in a variable of type long,
 and retrieve the first four digits of the fractional part and store them in an integer of type short.
 Display the value of the double variable by outputting the two values stored as integers.
 */

public class DisplayParts {
  public static void main(String args[]) {
    // Initialize a double variable to some value (1234.5678):
    double myNumber = 1234.5678;
    long integPart = 0;
    short fracPart = 0;
    int decPlaces = 4;

    // Find the integer part:
    integPart = (long)myNumber;

    // Find the number of Decimal places:
    fracPart = (short)((myNumber - integPart)*Math.pow(10, decPlaces));
/*
     This works by subtracting the integral part from the original leaving
     just the fractional part, and then multiplying this by 10 to the power
     of the number of decimal places required. This result in a double value
     that has an integral part with decPlaces decimal digits.
     Finally this result is cast to type short to get the digits
     of the fractional part.

     This is OK as far as it goes. If decPlaces had a value that was greater
     that 4 this would not be satisfactory. Type short just won't accommodate
     enough digits. The exercise said to use type short just because that fits
     the result in this case. In general though it would be better to make fracPart
     type long.

     A further thought is that this approach just chops off the digits beyond the
     number of decimal places required in the fractional part. It would be better
     to round to the nearest digit. This is equivalent to adding 0.5 to the double
     value after we have used the pow() method to multiply by 10 to the appropriate
     power. We could do this explicitly like this:

      fracPart = (short)(0.5+(myNumber-integPart)*Math.pow(10,decPlaces));

      Alternatively we could use the round() method that is defined in the Math
      class, like this:

      fracPart = (short)Math.round((myNumber-integPart)*Math.pow(10,decPlaces));

      This does exactly the same as the previous statement.
*/

    System.out.println("The integral part of " + myNumber + " is " + integPart);
    System.out.println("The fractional part to " + decPlaces + " decimal places is " + fracPart);

/*
     There is also something wrong with this approach for numbers in general. Can
     you see what it is?

     If the fractional part starts with a zero digit, 123.0456 say, the result will
     be incorrect. The integral part will be 123, which is fine, but the fractional
     part will be 456, so the output for the fractional will be 456, which is not so fine!
     We want 0456, but to get that you need a decision process that will allow you to insert
     leading zeroes in the fractional part when necessary. You will learn how to make
     such decisions later in the book.
*/
  }
}

