// Chapter 8 Exercise 3

/*
 Write a program to generate 20 random values of type double between -50 and +50
 and use the printf() method for System.out to display them with two decimal places
 in the following form:

   1) +35.93   2) -46.94   3) +42.27   4) +32.09   5) +29.21
   6) +13.87   7) -47.87   8) +30.67   9) -25.20  10) +29.67
  11) +48.62  12)  +6.70  13) +28.97  14) -41.64  15) +16.67
  16) +17.01  17)  +9.62  18) -15.21  19)  +7.46  20)  +4.09

 */
import static java.lang.Math.random;

public class FormatDoubleData {
  public static void main(String[] args) {
    double[] data = new double[20];

    for (int i = 0 ; i < data.length ; ++i) {
      data[i] = 100.0*random() - 50.0;
    }
    int i = 0;
    for(double x : data) {
      System.out.printf("%4d)%+7.2f", ++i, x);
      if(i%5 == 0) {
        System.out.println();
      }
    }
  }
}