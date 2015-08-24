package _exercise_solutions.ch02.sol1;

//Chapter 2, Exercise 1
/*
  Write a console program to define and initialize a variable of type byte to 1,
 and then successively multiply it by 2 and display its value 8 times.
 Explain the reason for the last result.
 */

public class ByteTest {     // Ensure classes/variables have distinctive names.
  public static void main(String args[]) {
    // Initialize variable data of type byte to 1:
    byte data = 1;

    data *= 2; // Multiply by 2: 1st time
    System.out.println("data = " + data);
    data *= 2; // Multiply by 2: 2nd time
    System.out.println("data = " + data);
    data *= 2; // Multiply by 2: 3rd time
    System.out.println("data = " + data);
    data *= 2; // Multiply by 2: 4th time
    System.out.println("data = " + data);
    data *= 2; // Multiply by 2: 5th time
    System.out.println("data = " + data);
    data *= 2; // Multiply by 2: 6th time
    System.out.println("data = " + data);
    data *= 2; // Multiply by 2: 7th time
    System.out.println("data = " + data);

    // Value of data is now out of the byte range, which is -128 to +127
    // At this point we have stored 128 in data - in binary 1000 0000
    // which corresponds to -128 as a value of type byte.

    data *= 2; // Multiply by 2: 8th time
    System.out.println("data = " + data);

    // Don't forget that these integer calculations will be done
    // with 32 bits precision. The result is then stored in data taking the
    // low-order 8 bits of the result as the value to be stored.
    // Since we multiply the -128 value by 2, the result is -256. In binary the
    // low-order 8 bits for this value are zero.

    // This calculation would normally be done in a loop ( a subject covered in chapter 3).
  }
}

