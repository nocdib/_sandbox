// Chapter 10 Exercise 3
/*
 Extend the previous example to write a second file at the same time as the first,
 but containing the month, day, and year values as binary data.
 You should have both files open and be writing to both at the same time.
 */
/* the additional and modified code from Solution 2 that deals with writing
  the binary file is indicated by comments.
  The binary data is added to the buffer for the binary file int the loop
  that write the text file. The binary file is written in one go after the
  text file has been written.
*/

import static java.nio.file.StandardOpenOption.*;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.EnumSet;
import java.io.IOException;

class WriteDateFiles {                                                                                                           // ***** class name changed *****
  static String[] months = { "January "  , "February ", "March "   , "April "   ,
                             "May "      ,  "June "   , "July "    , "August "  ,
                             "September ", "October " , "November ", "December ",  };


  public static void main(String args[]) {
    int[][] dates = { {12, 11, 1932}, {2, 29, 1944}, {3, 9,1944} , {4, 1, 1999},   // Dates to be written to file
                      {12,  5, 1939}, {3,  8, 1968}, {1, 1,2000} , {2, 2, 2002},
                      {10, 23, 1946}, {8,  3, 1986} };

    String[] dateStrings = new String[dates.length];                               // Stores date strings

    // Convert dates to string representations
    for(int i = 0 ; i < dates.length ; ++i) {
      dateStrings[i] = dateToString(dates[i]);
    }

    // Create the path for the file to contain dates
    String fileName = "dates.txt";
    String binaryFileName = "binary_dates.bin";
    Path datesFilePath = Paths.get(System.getProperty("user.home")).resolve("Beginning Java Stuff").resolve(fileName);
    Path binaryFilePath = Paths.get(System.getProperty("user.home")).resolve("Beginning Java Stuff").resolve(binaryFileName);    // ***** added *****

    // Make sure we have a directory for the files
    try {
      Files.createDirectories(datesFilePath.getParent());
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }

    // Create an array of two byte buffers. The first buffer will hold the length of
    //  the date string as a binary value and therefore fixed at four bytes long.
    // The second buffer will be created in the loop that follows to suit the
    // length of each date string.
    ByteBuffer[] buffers = {ByteBuffer.allocate(4), null};

    // Create a buffer for the binary file
    ByteBuffer binaryBuffer = ByteBuffer.allocate(4*dates.length*dates[0].length);                                               // ***** added *****

    try(WritableByteChannel binaryChannel = Files.newByteChannel(binaryFilePath, EnumSet.of(CREATE, WRITE)) ;                    // ***** added *****
        FileChannel channel = (FileChannel)Files.newByteChannel(datesFilePath, EnumSet.of(CREATE, WRITE))){
      // Write sayings to the file
      for(int i = 0 ; i < dateStrings.length ; ++i) {                                                                            // ***** modified *****
        buffers[0].putInt(dateStrings[i].length()).flip();                                                                       // ***** modified *****
        buffers[1] = ByteBuffer.allocate(2*dateStrings[i].length());                                                             // ***** modified *****
        buffers[1].asCharBuffer().put(dateStrings[i]);                                                                           // ***** modified *****
        channel.write(buffers);
        buffers[0].clear();                                                        // Clear ready for next string length

        // Add current date values to binary buffer
        for(int j = 0 ; j < dates[i].length ; ++j) {                                                                             // ***** added *****
          binaryBuffer.putInt(dates[i][j]);                                                                                      // ***** added *****
        }
      }
      System.out.format("\nFile %s in directory %s written.", fileName, datesFilePath.getParent().toString());

      // Write the binary file
        binaryChannel.write((ByteBuffer)(binaryBuffer.flip()));                                                                  // ***** added *****
        System.out.format("\nFile %s in directory %s written.", binaryFileName, datesFilePath.getParent().toString());           // ***** added *****
    } catch(IOException e) {
      System.err.format("\nI/O Error.");                                                                                         // ***** modified *****
      e.printStackTrace();
      System.exit(1);
    }
  }

  // Helper method to convert an int[] array with three elements containing month, day, year, to a string
  // representation of the date e.g.  convert array { 4, 14, 1962 } to "14th April 1962"
  private static String dateToString(int[] date) {
    assert date.length == 3;                                                       // Array must have three elements
    StringBuffer dateString = new StringBuffer(String.valueOf(date[1]));

    String ending = null;                                                          // Day ending, st, nd, or th
    if(date[1] == 1 || date[1] == 21 || date[1] == 31)
      ending = "st ";
    else if(date[1] == 2 || date[1] == 22)
      ending = "nd ";
    else if(date[1] == 3 || date[1] == 23)
      ending = "rd ";
    else
      ending = "th ";
    return dateString.append(ending).append(months[date[0] - 1]).append(String.valueOf(date[2])).toString();
  }
}
