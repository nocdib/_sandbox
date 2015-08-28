package _exercise_solutions.ch02.sol3;

//Chapter 2, Exercise 3
/*
 Write a program that defines a floating-point variable initialized with a dollar value for your income
 and a second floating-point variable initialized with a value corresponding to a tax rate of 35 percent.
 Calculate and output the amount of tax you must pay with the dollars and cents stored as separate integer values
 (use two variables of type int to hold the tax, perhaps taxDollars and taxCents).
 */

public class TaxCalc {
  public static void main(String args[]) {
    double income = 87562.34;
    double taxRate = 0.35;                                             // 35%  corresponds to 0.35
    double tax = income*taxRate;

    int taxDollars = (int)tax;
    int taxCents = (int)Math.round((tax - taxDollars)*Math.pow(10.0, 2.0));

    // Output the results:
    System.out.println("Gross income is $" + (int)income +"." + (int)Math.round((income - (int)income)*Math.pow(10.0, 2.0)));
    System.out.println("Tax to pay is " + taxDollars + " dollars and " + taxCents + " cents.");

 /*
    The output statements above show how you can put arithmetic expressions as part of the argument
    to the println() method. The compiler takes care of converting the values that result from the
    arithmetic expressions to a string.
 */
  }
}

