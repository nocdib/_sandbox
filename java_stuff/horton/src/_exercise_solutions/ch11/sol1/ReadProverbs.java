// Chapter 11 Exercise 1
/*
 Write a program to read back the contents of the files written by the first exercise in the previous chapter
 and output the proverb length that was read and the proverb itself for each of the proverbs.
*/

/*
 The process is to discover the size of the file containing the lengths of proverbs
 and then read the entire file into a buffer.
 Once you have all the lengths, you can read the file containing the proverbs in a loop.
 */

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.io.BufferedReader;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.charset.Charset;

public class ReadProverbs {

  public static void main(String[] args) {
    // Create a path for both of the files we will be writing
    Path proverbsFile = Paths.get(System.getProperty("user.home")).resolve("Beginning Java Stuff").resolve("Proverbs.txt");
    Path proverbLengthsFile = Paths.get(System.getProperty("user.home")).resolve("Beginning Java Stuff").resolve("Proverb Lengths.bin");

    // Verify that both files exist
    if(!Files.exists(proverbsFile)) {
      System.out.println(proverbsFile + " does not exist. Terminating program.");
      System.exit(1);
    } else if(!Files.exists(proverbLengthsFile)) {
      System.out.println(proverbLengthsFile + " does not exist. Terminating program.");
      System.exit(1);;
    }
    int[] lengths = null;
    ByteBuffer lengthBuf = null;                                                   // Reference to the buffer to store lengths
    try (ReadableByteChannel lengthsIn = Files.newByteChannel(proverbLengthsFile)) {
      // Get the lengths file attributes to allow the file size top be determined
      BasicFileAttributes attr = Files.readAttributes(proverbLengthsFile, BasicFileAttributes.class);
      int length = (int)attr.size();

      lengthBuf = ByteBuffer.allocate(length);                                     // Buffer to store all the lengths
      lengthsIn.read(lengthBuf);                                                   // Read the lengths into the buffer
      lengthBuf.flip();                                                            // Flip the buffer ready for access
      lengths = new int[length/4];                                                 // Array to store lengths
      lengthBuf.asIntBuffer().get(lengths);                                        // Fill the array with lengths from the buffer
    } catch(IOException e) {
      System.err.println("I/O error reading " + proverbLengthsFile.getFileName().toString() + ".");
      e.printStackTrace();
    }

    // We have all the lengths so now we can read all thwe proverbs
    try (BufferedReader proverbsIn = Files.newBufferedReader(proverbsFile, Charset.forName("UTF-16"))) {
      char[] proverb = null;
      // Read the proverbs from the file
      for(int proverbLength : lengths){
        proverb = new char[proverbLength];
        proverbsIn.read(proverb, 0, proverbLength);
        System.out.format("%nLength: %3d Proverb: %s", proverbLength, new String(proverb));
      }
    } catch(IOException e) {
      System.err.println("I/O error reading " + proverbsFile.getFileName().toString() + ".");
      e.printStackTrace();
    }
  }
}