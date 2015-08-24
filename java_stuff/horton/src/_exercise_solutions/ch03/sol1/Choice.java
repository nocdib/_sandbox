//Chapter 3, Exercise 1
/*
 Write a program to display a random choice from a set of six choices for breakfast
 (you could use any set—for example, scrambled eggs, waffles, fruit, cereal, toast, or yogurt).
 */

public class Choice {
  public static void main(String args[]) {
    // Use an integer to determine the choice:
    int myChoice = 0;

    // Select a random value for myChoice between 0 and 5:
    myChoice = (int)(6.0*Math.random());

    // The above line ensures each integer from 0 to 5 is equiprobable.
    // Multiplying the value produced by random() by 6.0 results in a value
    // from 0.0 to 5.99999
    // Values from 0 to 0.99999 becomes 0, from 1.00 to 1.99999 becomes 1,
    // and so on, with values from 5.0 to 5.99999 becoming 5.

    // Print the appropriate Breakfast Choice depending on value myChoice:
    switch(myChoice) {
      case 0:
        System.out.println("Breakfast choice is scrambled eggs");
        break;
      case 1:
        System.out.println("Breakfast choice is waffles");
        break;
      case 2:
        System.out.println("Breakfast choice is fruit");
        break;
      case 3:
        System.out.println("Breakfast choice is cereal");
        break;
      case 4:
        System.out.println("Breakfast choice is toast");
        break;
      case 5:
        System.out.println("Breakfast choice is yogurt");
        break;
    }
  }
}

