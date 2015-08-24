// Chapter 5, Exercise 2

// The mcmLength class definition
// The main() method in the TestLengths class tries this out

public class mcmLength {

  public static final int CM_PER_M = 100;
  public static final int MM_PER_CM = 10;
  public static final int MM_PER_M = MM_PER_CM*CM_PER_M;

  // Private member variables:
  private int meters = 0;
  private int centimeters = 0;
  private int millimeters = 0;

  // Constructors:
  public mcmLength(double cm) {
    this((int)Math.round(cm*MM_PER_CM));
  }

  public mcmLength(int mm) {
    meters = mm/MM_PER_M;
    centimeters = (mm - meters*MM_PER_M)/MM_PER_CM;
    millimeters = mm - meters*MM_PER_M - centimeters*MM_PER_CM;
  }

  // If we were to just store the argument values, we could
  // end up with invalid mm and cm values in the object if the
  // values passed as arguments are not valid.
  // With the approach here we guarantee all values are valid
  // in the object that is created.
  public mcmLength(int m, int cm, int mm) {
    this(m*MM_PER_M + cm*MM_PER_CM + mm);
  }

  public mcmLength(){}

  // Methods
  // Replaces the default toString method in Object:
  public String toString() {
    return Integer.toString(meters) + "m " + centimeters + "cm " + millimeters + "mm";
  }

  public int toMM() {
    return meters*MM_PER_M + centimeters*MM_PER_CM + millimeters;
  }


  public double toMeters() {
    return meters + ((double)(centimeters))/CM_PER_M + ((double)(millimeters))/MM_PER_M;
  }

  // All of the following methods use the toMM():
  public mcmLength add(mcmLength length)
  {
    return new mcmLength(toMM()+length.toMM());
  }

  public mcmLength subtract(mcmLength length)
  {
    return new mcmLength(toMM()-length.toMM());
  }

  public mcmLength multiply(int n)
  {
    return new mcmLength(n*toMM());
  }

  public mcmLength divide(int y)
  {
    return new mcmLength(toMM()/y);
  }

  //Calculate area in square mm
  public long area(mcmLength length) {
    return (toMM()*length.toMM());
  }

  // Compare two lengths
  // Return value is 1 if current greater than arg
  //                 0 if current equal to arg
  //                -1 if current less than arg
  public int compare(mcmLength length) {
    return greaterThan(length) ? 1 : (equals(length) ? 0 : -1);
  }

  public boolean equals(mcmLength length) {
  	return toMM() == length.toMM();
  }

  public boolean lessThan(mcmLength length) {
  	return toMM() < length.toMM();
  }

  public boolean greaterThan(mcmLength length) {
  	return toMM() > length.toMM();
  }
}
