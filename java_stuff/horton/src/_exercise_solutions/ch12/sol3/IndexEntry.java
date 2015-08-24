// Class encapsulating an index to the IndexedPersons.bin file
// Each object stores a second name and the position in the file where
// the corresponding record can be found. i.e. the first record
// is at position 0, the second at position 1, the third at position 2, etc.
import java.io.Serializable;

public class IndexEntry implements Serializable {
  // Constructor
  public IndexEntry(String name, int position) {
    this.name = name;
    this.position = position;
  }

  public String getName() {
    return name;
  }

  public int getPosition() {
    return position;
  }

  // Method to compare two IndexEntry objects
  public int compareTo(IndexEntry entry) {
    return name.compareTo(entry.name);
  }

  private String name;
  private int position;
  private static final long serialVersionUID = 7001L;
}