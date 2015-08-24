// Class encapsulating an address
import java.io.Serializable;

public class Address implements Serializable {
  // Constructor
  public Address(String address) {
    this.address = address;
  }

  @Override
  public String toString() {
    return address;
  }

  private String address;
  private static final long serialVersionUID = 7001L;
}