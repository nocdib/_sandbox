// Chapter 10 Exercise 2

/*
 Write a program that creates an integer array of date values containing
  month, day, and year as integers for some number of dates
 (10, say, so the integer array is two-dimensional with 10 rows and 3 columns).
 The program should write a file with a string representation of each date written
 as Unicode characters. For example, the date values 3,2,1990 would be written
 to the file as 2nd March 1990.
 Make sure that the date strings can be read back, either by using a separator character
 of some kind to mark the end of each string or by writing the length of each string
 before you write the string itself.
 */

 /*
  Converting the numeric dates to text is quite a lot of work because of the irregularity
  of the ending for the date in the month. Writing the file is easy once you have assembled
  the data into view buffers. The FileChannel class provides a method to write an array of
  ByteBuffer objects to a channel, which comes in useful here.
  Of course, you couild use a Writert instead of a channel. Using a channel is probably more
  efficient.
  */
import static java.nio.file.StandardOpenOption.*;
import java.nio.channels.FileChannel;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.EnumSet;
import java.io.IOException;

class WriteDateFile {
  static String[] months = { "January "  , "February ", "March "   , "April "   ,
                             "May "      ,  "June "   , "July "    , "August "  ,
                             "September ", "October " , "November ", "December ",  };


  public static void main(String args[]) {
    int[][] dates = { {12, 11, 1932}, {2, 29, 1944}, {3, 9,1944} , {4, 1, 1999},   // Dates to be written to file
                      {12,  5, 1939}, {3,  8, 1968}, {1, 1,2011} , {2, 2, 2002},
                      {10, 23, 1946}, {8,  3, 1986} };

    String[] dateStrings = new String[dates.length];                               // Stores date strings

    // Convert dates to string representations
    for(int i = 0 ; i < dates.length ; ++i)
      dateStrings[i] = dateToString(dates[i]);

    // Create the path for the file to contain dates
    String fileName = "dates.txt";
    Path datesFilePath = Paths.get(System.getProperty("user.home")).resolve("Beginning Java Stuff").resolve(fileName);

    // Make sure we have a directory for the file
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

    try(FileChannel channel = (FileChannel)Files.newByteChannel(datesFilePath, EnumSet.of(CREATE, WRITE))){
      // Write sayings to the file
      for(String dateString : dateStrings) {
        buffers[0].putInt(dateString.length()).flip();                             // Date string length into the buffer
        buffers[1] = ByteBuffer.allocate(2*dateString.length());                   // Buffer size to accommodate date string
        buffers[1].asCharBuffer().put(dateString);                                 // Put the date string into the buffer
        channel.write(buffers);
        buffers[0].clear();                                                        // Clear ready for next string length
      }
      System.out.format("\nFile %s in directory %s written.", fileName, datesFilePath.getParent().toString());
    } catch(IOException e) {
      System.err.format("\nI/O Error. %s not written. ", fileName);
      e.printStackTrace();
      System.exit(1);
    }
  }

  // Helper method to convert an int[] array with three elements containing month, day, year, to a string
  // representation of the date e.g.  convert array { 4, 14, 1962 } to "14th April 1962"
  private static String dateToString(int[] date) {
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
