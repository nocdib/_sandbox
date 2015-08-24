// Chapter 11 Exercise 2
/*
 Extend the ReadPrimes example that you produced in this chapter to optionally
 display the nth prime, when n is entered from the keyboard.
 */

import java.nio.file.*;
import java.nio.*;
import java.nio.channels.FileChannel;
import java.io.IOException;

public class ReadPrimes {
  public static void main(String[] args) {
    boolean listAll = false;
    int requiredPrime = 0;                                                         // Stores sequence number of prime required - 1 for the first, 2 for the second etc.
    System.out.println("Arg array length: " + args.length);
    if(args.length >= 1) {
      try {
        requiredPrime = Integer.parseInt(args[0]);
        System.out.println("Required prime: " + requiredPrime);
      } catch(NumberFormatException nfe) {
          System.out.println("Command line argument " + args[0] + " not a valid integer. Continuing to list file contents.");
          listAll = true;
      }
    }

    Path file = Paths.get(System.getProperty("user.home")).resolve("Beginning Java Stuff").resolve("primes.bin");
    if(!Files.exists(file)) {
      System.out.println(file + " does not exist. Terminating program.");
      System.exit(1);
    }

    ByteBuffer buf = null;
    final int PRIMECOUNT = 6;
    final int LONG_BYTES = 8;                                                      // Number of bytes for type long
    try (FileChannel inCh = (FileChannel)Files.newByteChannel(file)) {
      long fileSize = inCh.size();
      // Ensure the required prime is within the file
      if(8*requiredPrime > fileSize) {
        System.out.println("Prime " + requiredPrime + " is not in the file. Listing the " + (fileSize/8) +
                           " primes in the file.");
        requiredPrime = 0;
      }

      if(requiredPrime > 0 && !listAll) {
        // Read from the position in the file determined by the index to the required prime
        inCh.position(LONG_BYTES*(requiredPrime-1));                               // File position for the required prime
        buf = ByteBuffer.allocate(LONG_BYTES);                                     // Buffer for one prime
        inCh.read(buf);
        long prime = ((ByteBuffer)(buf.flip())).asLongBuffer().get();
        System.out.println("Prime " + requiredPrime + " is " + prime);
      } else {
        int primesRead = 0;
        buf = ByteBuffer.allocate(PRIMECOUNT*LONG_BYTES);                          // Buffer to hold six primes
        long[] primes = new long[PRIMECOUNT];                                      // Array to hold primes read
        while(inCh.read(buf) != -1) {
          LongBuffer longBuf = ((ByteBuffer)(buf.flip())).asLongBuffer();
          primesRead = longBuf.remaining();
          longBuf.get(primes, 0, primesRead);                                      // Extract the primes from the buffer into the array

          // List the primes read on the same line
          System.out.println();
          for(int i = 0 ; i < primesRead ; ++i) {
            System.out.printf("%10d", primes[i]);
          }
          buf.clear();                                                             // Clear the buffer for the next read
        }
      }
    } catch(IOException e) {
        e.printStackTrace();
        System.exit(1);
    }
  }
}
