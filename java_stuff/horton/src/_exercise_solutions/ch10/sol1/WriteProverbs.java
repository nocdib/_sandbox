// Chapter 10 Exercise 1

/*
 Implement a new version of the example that writes proverbs to a file
 that writes the proverbs to one file using a Writer and the length of each proverb
 to a separate file using an OutputStream object.
 */

/*
 This is relatively straightforward. I write both files in a single try block
 but you could have a separate try block for writing each file.
 */
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.BufferedWriter;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.charset.Charset;

public class WriteProverbs {
  public static void main(String[] args) {
    String[] sayings = {
      "Indecision maximizes flexibility.",
      "Only the mediocre are always at their best.",
      "A little knowledge is a dangerous thing.",
      "Many a mickle makes a muckle.",
      "Who begins too much achieves little.",
      "Who knows most says least.",
      "A wise man sits on the hole in his carpet."
    };

    // Create a path for both of the files we will be writing
    Path proverbsFile = Paths.get(System.getProperty("user.home")).resolve("Beginning Java Stuff").resolve("Proverbs.txt");
    Path proverbLengthsFile = Paths.get(System.getProperty("user.home")).resolve("Beginning Java Stuff").resolve("Proverb Lengths.bin");

    try {
      // Make sure we have the directory
      Files.createDirectories(proverbsFile.getParent());
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
    System.out.println("New file for proverbs is: " + proverbsFile);
    System.out.println("New file for proverb lengths is: " + proverbLengthsFile);

    ByteBuffer buf = ByteBuffer.allocate(sayings.length*4);            // Buffer for proverb lengths
    IntBuffer lengthsBuf = buf.asIntBuffer();                          // View buffer for type int

    // Load the buffer with the proverb lengths
    for(String saying : sayings) {
      lengthsBuf.put(saying.length());
    }

    try(BufferedWriter proverbsOut = Files.newBufferedWriter(proverbsFile, Charset.forName("UTF-16")) ;
        BufferedOutputStream lengthsOut = new BufferedOutputStream(Files.newOutputStream(proverbLengthsFile))) {

      for(String saying : sayings) {                                   // Write all the proverbs to the file
        proverbsOut.write(saying, 0, saying.length());                 // Write a proverb
      }
      lengthsOut.write(buf.array(), 0, buf.capacity());                // Write the lengths to the other file

    System.out.println("Files written.");
    } catch(IOException e) {
      System.err.println("Error writing a file.");
      e.printStackTrace();
    }
  }
}

