// Chapter 11 Exercise 4

/*
 Write a program that outputs the contents of a file to the command line
 as groups of eight hexadecimal digits with five groups to a line,
 each group separated from the next by a space.
 */

/*
 I tested this with the binary file that contains primes because it is
 easy to see if the correct output is being produced.
 */

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.ReadableByteChannel;

public class HexFileDump {
  public static void main(String[] args) {
    if(args.length == 0 ) {
      System.out.println("No file specified. Usage is:"
                        +"\n\njava HexFileDump \"PATH_TO_FILE\""
                        +"\n\nwhere PATH_TO_FILE is an absolute or relative path to the file whose you want to dump."
                        +"\nYou should use either forward slashes or double backslashes as separators in the path.");
      System.exit(1);
    }
    String filePath = args[0];
    Path file = Paths.get(filePath).toAbsolutePath();
    if(!Files.exists(file)) {
      System.out.println(file + " does not exist. Terminating program.");
      System.exit(1);
    }


    try (ReadableByteChannel inChannel = Files.newByteChannel(file)) {
      ByteBuffer buffer = ByteBuffer.allocate(160);                                // Buffer for 8 lines of output
      IntBuffer intBuffer = null;                                                  // View buffer for 4-byte integers
      while(inChannel.read(buffer) != -1) {
        buffer.flip();                                                             // Flip the buffer ready to get the data
        intBuffer = buffer.asIntBuffer();                                          // Extract the bytes as int values

        // We can use the 'X' conversion in a printf() statement to output type int as hexadecimal digits
        // by using a flag '0' preceding the field width of 8, the output will be padded with zeros.
        // Process all the data in the integer buffer...
        for(int i = 0 ; i < intBuffer.limit() ; ++i) {
          if(i%8 == 0) {
            System.out.println();
          }
          System.out.printf(" %08X", intBuffer.get());
        }
        buffer.clear();                                                            // Clear the buffer for the next read
      }
      System.out.println("\nEOF reached.");
    } catch(IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
  }
}