//Chapter 3, Exercise 3
/*
 A lottery requires that you select six different numbers from the integers 1 to 49.
 Write a program to do this for you and generate five sets of entries.
 */

public class Lottery {
  public static void main(String[]args) {
    int setCount = 5;                                                  // Number of sets of lucky numbers.
    int setSize = 6;                                                   // Number of lucky numbers in the set.
    int range = 49;                                                    // Assume selecting integers between 1 and range.
    int lucky;                                                         // Holds a lucky number candidate.
    int luckyCount;                                                    // Holds count of lucky numbers in a set.

    for(int i = 0 ; i < setCount ; ++i) {
      int lucky1 = 0;                                                  // Lucky numbers for the set of 6.
      int lucky2 = 0;
      int lucky3 = 0;
      int lucky4 = 0;
      int lucky5 = 0;
      int lucky6 = 0;

      luckyCount = 0;                                                  // Count of numbers found in the current set
      while(luckyCount < setSize) {
	      // Generate a lucky number between 0 and 48 and add 1:
	      lucky = (int)(range*Math.random()) + 1;
        switch(luckyCount) {
          case 0:                                                      // It is the first one
            lucky1 = lucky;                                            // so just store it
            luckyCount++;                                              // and increment the count
            break;
          case 1:                                                      // For the second we must
            if(lucky != lucky1) {                                      // check that it is different from the first
              lucky2 = lucky;                                          // It is, so store it
              luckyCount++;                                            // and increment the count
            }
            break;
          case 2:                                                      // For the third we check aginst the previous two
            if(lucky != lucky1 && lucky != lucky2) {
              lucky3 = lucky;
              luckyCount++;
            }
            break;
           case 3:                                                     // Check against the previous three...
            if(lucky != lucky1 && lucky != lucky2 && lucky != lucky3) {
              lucky4 = lucky;
              luckyCount++;
            }
            break;
           case 4:                                                     // Check against the previous four...
            if(lucky != lucky1 && lucky != lucky2 && lucky != lucky3 && lucky != lucky4) {
              lucky5 = lucky;
              luckyCount++;
            }
            break;
           case 5:                                                     // Check against the previous five...
            if(lucky != lucky1 && lucky != lucky2 && lucky != lucky3 && lucky != lucky4 && lucky != lucky5) {
              lucky6 = lucky;
              luckyCount++;
            }
            break;
        }
      }

      System.out.print("\nSet " + (i + 1) + ":");                      // Identify the set

      System.out.print(" " + lucky1 + " " + lucky2  + " " + lucky3  +  // and output the numbers
                       " " + lucky4  + " " + lucky5  + " " + lucky6);


      // If you want to be sure the numbers line up in columns you could use a
      // rather more complicated statement here instead of the above:
/*
      System.out.print((lucky1>9 ? " " :"  ") + lucky1 +
                       (lucky2>9 ? " " :"  ") + lucky2 +
                       (lucky3>9 ? " " :"  ") + lucky3 +
                       (lucky4>9 ? " " :"  ") + lucky4 +
                       (lucky5>9 ? " " :"  ") + lucky5 +
                       (lucky6>9 ? " " :"  ") + lucky6);
*/
      // This makes use of the conditional operator to output an extra space
      // when a lucky number is a single digit.
    }
  }
}

