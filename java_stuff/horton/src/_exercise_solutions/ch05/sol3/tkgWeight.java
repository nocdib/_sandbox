// Chapter 5 Exercise 3

public class tkgWeight {
  public static final int KG_PER_TON = 1000;
  public static final int GRAMS_PER_KG = 1000;
  public static final int GRAMS_PER_TON = GRAMS_PER_KG*KG_PER_TON;

  // private member variables
  private int tons = 0;
  private int kilograms = 0;
  private int grams = 0;

  // Constructors:
  public tkgWeight(double kg) {
    this((int)Math.round(kg*GRAMS_PER_KG));
  }

  public tkgWeight(int g) {
    tons = g/GRAMS_PER_TON;
    kilograms = (g - tons*GRAMS_PER_TON)/GRAMS_PER_KG;
    grams = g - tons*GRAMS_PER_TON - kilograms*GRAMS_PER_KG;
  }

  public tkgWeight(int t, int kg, int g) {
    this(t*GRAMS_PER_TON + kg*GRAMS_PER_KG + g);
  }

  public tkgWeight(){}

  // Methods
  public String toString()  {
    return Integer.toString(tons) + "t " + kilograms + "kg " + grams + "g";
  }

  public int toGrams() {
    return tons*GRAMS_PER_TON + kilograms*GRAMS_PER_KG + grams;
  }

  public double toKilograms() {
    return ((double)toGrams())/GRAMS_PER_KG;
  }

  public tkgWeight add(tkgWeight weight) {
    return new tkgWeight(toGrams() + weight.toGrams());
  }

  public tkgWeight subtract(tkgWeight weight) {
    return new tkgWeight(toGrams() - weight.toGrams());
  }

  public tkgWeight multiply(int n) {
    return new tkgWeight(n*toGrams());
  }

  public tkgWeight divide(int n) {
    return new tkgWeight(toGrams()/n);
  }

  // Compare two weights
  // Return value is 1 if current greater than arg
  //                 0 if current equal to arg
  //                -1 if current less than arg
  public int compare(tkgWeight weight) {
    return greaterThan(weight) ? 1 : (equals(weight) ? 0 : -1);
  }

  public boolean equals(tkgWeight weight) {
    return toGrams() == weight.toGrams();
  }

  public boolean lessThan(tkgWeight weight) {
    return toGrams() < weight.toGrams();
  }

  public boolean greaterThan(tkgWeight weight) {
    return toGrams() > weight.toGrams();
  }
}