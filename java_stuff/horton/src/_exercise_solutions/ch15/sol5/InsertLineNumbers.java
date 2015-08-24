// Chapter 15 Exercise 5
/*
 Write a program using a regular expression to reproduce a file with a sequential line number
 starting at "0001" inserted at the beginning of each line in the original file.
 You can use a copy of your Java source file as the input to test this.
 */

/*
 This should be very easy if you managed the previous example. I added a space after
 the line number to separate it from the line data so a line number consists of
 a sequence of digits followed by a space.
 This is an oppportunity to use a Formatter under the covers through the static format()
 method in the String class.
 Of course, line numbers could be added easily without recourse to regular expressions.
*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.charset.Charset;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class InsertLineNumbers {
  public static void main(String[] args) {
    // Get the file name and path from the command line
    String message =     "\n\njava InsertLineNumbers \"PATH_TO_FILE\"  \"CHARACTER_SET\"" +
                         "\n\nwhere PATH_TO_FILE is an absolute path to the file whose you want to process " +
                         "\nand CHARACTER_SET is the file character encoding, either US_ASCII or UTF_16." +
                         "\nYou should use either forward slashes or double backslashes as separators in the path.";

    if(args.length == 0 ) {
      System.out.println("No file specified. Usage is:" + message);
      System.exit(1);
    } else {
      if(args.length == 1) {
        System.out.println("No file character encoding specified. Usage is:" + message);
        System.exit(1);
      }
    }

    Path fileIn = Paths.get(args[0]).toAbsolutePath();                                             // Get the file path
    if(!Files.exists(fileIn)) {                                                                    // Check that there is a file for the path
      System.out.println("File " + fileIn + " does not exist. Terminating.");
      System.exit(1);
    }

    if(!(args[1].equals("US-ASCII") || args[1].equals("UTF-16"))) {                                // Check that the charset is valid
      System.out.println("Charset " + args[1] + " is not valid. Terminating.");
      System.exit(1);
    }

    // The regular expression to match the start of a line
    Pattern lineStart = Pattern.compile( "^");
    Matcher startMatch = null;                                                                     // Matcher for the start of a line

    Charset charset = Charset.forName(args[1]);                                                    // Create the charset for file read/write operations
    Path fileOut = getCopyFilePath(fileIn);                                                        // The path to the new file
    String inStr = null;                                                                           // Stores a line of input
    StringBuffer outStr = null;                                                                    // Buffer for output line
    String numberStr = null;                                                                       // Stores a line number as a string
    int lineNumber = 0;                                                                            // Line number counter
    try(BufferedReader in = Files.newBufferedReader(fileIn, charset);
        BufferedWriter out = Files.newBufferedWriter(fileOut, charset)) {
        while(true) {
          inStr = in.readLine();
          if(inStr == null) break;

          // Add a line number to the start of a line
          startMatch = lineStart.matcher(inStr);                                                   // Matcher for the current line
          outStr = new StringBuffer();                                                             // Buffer for processed line
          numberStr = String.format("%04d ", ++lineNumber);                                        // Create the line number string
          startMatch.find();
          startMatch.appendReplacement(outStr, numberStr);                                         // Append the line number
          startMatch.appendTail(outStr);                                                           // Append what's left of the line
          out.write(outStr.toString());                                                            // Write the line
          out.newLine();                                                                           // Write a newline
        }
    } catch(IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
    System.out.println("File " + fileOut + " created with line numbers.");
  }

  // Method to create a unique backup file Path object
  // This appends _copy1 or _copy2, etc. to the input file name.
  // The integer part is incremented until we get a name that does not already exist.
  // This method will not work if you try to create more than 2,147,483,647 copies of the same file.
  public static Path getCopyFilePath(Path aFile) {
     String name = aFile.getFileName().toString();                                                 // Get the file name as a string
     Path parent = aFile.getParent();                                                              // Get the parent directory path
     int period = name.indexOf('.');                                                               // Find the extension separator
     if(period == -1) {                                                                            // If there isn't one
       period = name.length();                                                                     // set it to the end of the string
     }

     // Create a File object that is unique by appending _copyn where n is an integer
     int copyNumber = 0;                                                                           // Copy number
     String nameAdd = null;                                                                        // String to be appended
     Path copy = null;
     do {
       nameAdd = "_copy" + Integer.toString(++copyNumber);                                         // String to be appended
       copy = parent.resolve(name.substring(0,period) + nameAdd + name.substring(period));
     } while(Files.exists(copy));                                                                  // If the name already exists, go again
     System.out.println(copy);
     return copy;
  }
}