//Chapter 2, Exercise 4
/*
 The diameter of the sun is approximately 865,000 miles.
 The diameter of Earth is approximately 7600 miles. Use the methods in the class Math to calculate the following:
? The volume of Earth in cubic miles
? The volume of the sun in cubic miles
? The ratio of the volume of the sun to the volume of Earth
 */

public class Volumes {
  public static void main(String args[]) {
    double sunRad = 865000.0/2.0;                                      // Sun radius in miles is half the diameter
    double earthRad = 7600.0/2.0;                                      // Earth radius likewise
    double fourOverThree = 4.0/3.0;                                    // A convenient constant, 4/3
    double sunVol = 0;
    double earthVol = 0;
    double ratioVol = 0;
/*
    You can declare several variables of the same type in a single statement if you wish,
    so we could write the definitions as:

     double sunRad = 865000.0/2.0, earthRad = 7600.0/2.0,
     double fourOverThree = 4.0/3.0;
     double sunVol = 0, earthVol = 0, ratioVol = 0;

   However, except in trivial cases it is better to declare variables in separate statements
   as this is clearer and reduces the possibility of errors.
*/

    // Find the volumes of earth and sun:
    earthVol = fourOverThree*Math.PI*Math.pow(earthRad,3);
    sunVol = fourOverThree*Math.PI*Math.pow(sunRad,3);

    // Find the ratio of their volumes:
    ratioVol = sunVol/earthVol;

    // Output the results:
    System.out.println("Volume of the earth is " + earthVol + " cubic miles");
    System.out.println("Volume of the sun is " + sunVol + " cubic miles");
    System.out.println("The sun's volume is " + ratioVol + " times greater than the earth's.");
  }
}

