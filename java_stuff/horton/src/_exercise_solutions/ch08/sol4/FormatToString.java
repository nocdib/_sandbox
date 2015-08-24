// Chapter 8 Exercise 4

/*
 Use a Formatter object to format 20 random values of type double between –50 and +50
 and output the entire set of 20 in a single call of System.out.print() or System.out.println().
 */

import java.util.Formatter;
import static java.lang.Math.random;

public class FormatToString {
  public static void main(String[] args) {
    double[] data = new double[20];

    for (int i = 0 ; i < data.length ; ++i) {
      data[i] = 100.0*random() - 50.0;
    }

    StringBuilder buf = new StringBuilder();          // Buffer to hold output
    Formatter formatter = new Formatter(buf);         // Formatter to format data into buf
    int i = 0;
    for(double x : data) {
      formatter.format("%4d)%+7.2f", ++i, x);
      if(i%5 == 0) {
        formatter.format("%n");
      }
    }
    System.out.println(buf);                          // Output all the values in one go
  }
}