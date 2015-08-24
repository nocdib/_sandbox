//Chapter 5, Exercise 4
/*
 Put both the previous classes in a package called Measures.
 Import this package into a program that calculates and displays
 the total weight of the following:
   200 carpets—size: 4 meters by 2 meters 9 centimeters, that weigh 1.25 kilograms per square meter;
   and 60 carpets—size: 3 meters 57 centimeters by 5 meters, that weigh 1.05 kilograms per square meter.
*/


/*
  Make sure that the source and class files for mcmLength and tkgWeight
  Are not in the same directory as this file. Otherwise those classes will be
  used rather than the ones in the Measures package

  To compile this program the compiler needs to know where the Measures packages is.
  If the current directory contains Carpets.java and Measures is a subdirectory of this,
  you can compile Carpets by using the -classpath option like this:

  javac -classpath . Carpets.java

  If Measures is not a subdirectory of the current directory, you just need to add
  the path to the directory that contains Measures to -classpath. Note that you still need
  the period in the classpath, otherwise the compiler will not be able to find Carpets.java.
*/

import Measures.tkgWeight;
import Measures.mcmLength;

public class Carpets {
  public static void main(String args[]) {
    mcmLength[] carpetLengths = { new mcmLength(4,0,0), new mcmLength(3,57,0) };
    mcmLength[] carpetWidths = { new mcmLength(2,9,0), new mcmLength(5,0,0) };
    double[] carpetWtPerSqM = { 1.25, 1.05 };                          // Weight per sq m of carpets
    int[] carpetCounts = { 200, 60 };


    tkgWeight carpetWeight = null;                                    // Store for reference to weight of a carpet
    double carpetArea = 0.0;                                          // Store for area of a carpet


    for(int i = 0 ; i < carpetLengths.length ; ++i) {
      // Since we want areas in square meters it is easiest to calculate them directly
      carpetArea = carpetLengths[i].toMeters()*carpetWidths[i].toMeters();

      carpetWeight = new tkgWeight(carpetWtPerSqM[i]*carpetArea);

      System.out.println("\nCarpet " + (i + 1) + ": Size = " + carpetLengths[i] + " by "+ carpetWidths[i]);
      System.out.println("        " + "  Weight per sq. m. = " + carpetWtPerSqM[i]);
      System.out.println("        " + "  Area = " + carpetArea + " sq. m.");
      System.out.println("        " + "  Weight = " + carpetWeight);

      System.out.println("        " + "  Weight of " + carpetCounts[i] + " carpets = " + carpetWeight.multiply(carpetCounts[i]));

    }
  }
}
