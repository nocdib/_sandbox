//Chapter 4, Exercise 2
/*
 Write a program to create a rectangular array containing a multiplication table from 1 x 1 up to 12 x 12.
 Output the table as 13 columns with the numeric values right-aligned in the columns.
  (The first line of output is the column headings, the first column with no heading,
   then the numbers 1 to 12 for the remaining columns. The first item in each of the
   succeeding lines is the row heading, which ranges from 1 to 12.)
 */

public class Tables {
  public static void main(String[]args) {
    final int TABLE_SIZE = 12;
    // Declare the rectangular array to store the multiplication table:
    int[][] table = new int[TABLE_SIZE][TABLE_SIZE];

    // Fill in the array with the multiplication table:
    for(int i = 0 ; i < table.length ; ++i) {
      for(int j = 0 ; j < table[i].length ; ++j) {
        table[i][j] = (i+1)*(j+1);
      }
    }

    // Output the table heading
    System.out.print("      :");             // Row name column heading
    for(int j = 1 ; j <= table[0].length ; ++j) {
      System.out.print((j<10 ? "   ": "  ") + j);
    }
    System.out.println("\n-------------------------------------------------------");

    // Output the table contents
    // Each entry in the table should be four characters wide so we output
    // three spaces preceding values less than 10, two spaces preceding values
    // from 10 to 99 and one space for values exceeding 100.
    // This exercise would be much easier if you know about the printf() method
    // that you'll see in detail in Chapter 8.
    for(int i = 0 ; i < table.length ; ++i) {
      System.out.print("Row" + (i<9 ? "  ":" ") + (i+1) + ":");

      for(int j = 0; j < table[i].length; ++j) {
        System.out.print((table[i][j] < 10 ? "   " : table[i][j] < 100 ? "  " : " ") + table[i][j]);
      }
      System.out.println();
    }
  }
}

