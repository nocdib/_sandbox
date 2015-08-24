// Chapter 11 Exercise 3
/*
 Extend the ReadPrimes program further to output a given number of primes,
 starting at a given number.
 For example, output 15 primes starting at the 30th.
 The existing capabilities should be retained.
 */
/*
 I chose to treat reading all the primes and reading a range of primes as essentially the same.
  Reading all the primes means reading the count of the primes in the file with the first prime index set to 1.
 */

import java.nio.file.*;
import java.nio.*;
import java.nio.channels.FileChannel;
import java.io.IOException;

public class ReadPrimes {
  public static void main(String[] args) {
    int requiredPrime = 1;                                                         // Stores sequence number of prime required - 1 for the first, 2 for the second etc.
    int totalPrimesRequired = 0;                                                   // Count of total primes required
    System.out.println("Arg array length: " + args.length);
    if(args.length >= 1) {
      try {
        requiredPrime = Integer.parseInt(args[0]);
        if(requiredPrime < 1) {
          requiredPrime = 1;
          System.out.println("Required prime index must be at least 1.");
        }
        totalPrimesRequired = args.length >= 2 ? Integer.parseInt(args[1]) : 1;
      } catch(NumberFormatException nfe) {
          System.out.println("A command line argument is not a valid integer. Continuing to list file contents.");
          requiredPrime = 1;
          totalPrimesRequired = 0;;
      }
    }

    Path file = Paths.get(System.getProperty("user.home")).resolve("Beginning Java Stuff").resolve("primes.bin");
    if(!Files.exists(file)) {
      System.out.println(file + " does not exist. Terminating program.");
      System.exit(1);
    }

    final int PRIMECOUNT = 6;
    final int LONG_BYTES = 8;                                                      // Number of bytes for type long
    try (FileChannel inCh = (FileChannel)Files.newByteChannel(file)) {
      long fileSize = inCh.size();
      int primeCount = (int)fileSize/LONG_BYTES;

      // If the number required is zero, set count for them them all
      if(totalPrimesRequired == 0) {
        totalPrimesRequired = primeCount;
      }

      // Ensure the required prime is within the file
      if(requiredPrime > primeCount) {
        System.out.println("Prime "+ requiredPrime + " is not in the file. Listing the " + (fileSize/LONG_BYTES) +
                           " primes in the file.");
        requiredPrime = 1;
      }

      // Ensure the prime count does not run beyond the end of the file
      if((totalPrimesRequired + requiredPrime - 1) > primeCount) {
        System.out.println("Unsufficient primes in the file. Listing those available.");
        totalPrimesRequired = primeCount - requiredPrime + 1;
      }

      // Read from the position in the file determined by the index to the required prime
      System.out.println("Outputting "  + totalPrimesRequired + " primes starting with prime number " + requiredPrime + ".");
      inCh.position(LONG_BYTES*(requiredPrime-1));                               // File position for the first prime
      ByteBuffer buf = ByteBuffer.allocate(LONG_BYTES*totalPrimesRequired);      // Buffer for primes
      long[] primes = new long[totalPrimesRequired];                             // Array to hold primes read
      inCh.read(buf);
      LongBuffer longBuf = ((ByteBuffer)(buf.flip())).asLongBuffer();
      int primesRead = longBuf.remaining();
      longBuf.get(primes, 0, primesRead);                                        // Extract the primes from the buffer into the array

      // List the primes read six on a line line
      System.out.println();
      for(int i = 0 ; i < primesRead ; ++i) {
        if(i%6 == 0) {
          System.out.println();
        }
        System.out.printf("%10d", primes[i]);
      }
    } catch(IOException e) {
        e.printStackTrace();
        System.exit(1);
    }
  }
}
