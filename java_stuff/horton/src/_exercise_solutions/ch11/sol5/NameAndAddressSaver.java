// Chapter 11 Exercise 5

/*
 Write a program that allows either one or more names and addresses
 to be entered from the keyboard and appended to a file,
  or the contents of the file to be read back and output to the command line.
 */

/* I used a channel opened for reading and writing to access the file
   containing the names and addresses.
   You can arrange for the file to be created automatically if it does
   not already exist.
 */

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.channels.SeekableByteChannel;
import java.nio.ByteBuffer;
import java.util.EnumSet;
import static java.nio.file.StandardOpenOption.*;

public class NameAndAddressSaver {
  // These static members ar eall accessible from the static methods
  private static final int charSize = 2;                                                           // Size of type char
  private static final int intSize = 4;                                                            // Size of type int
  private static String fileName = "NamesAndAddresses.bin";                                        // File name
  private static BufferedReader kb = new BufferedReader(new InputStreamReader(System.in));         // Keyboard input
  private static ByteBuffer lengthBuf = ByteBuffer.allocate(intSize);                              // Buffer for record length
  private static ByteBuffer dataBuf = null;                                                        // Buffer for name & address

  public static void main(String[] args) {
    Path file = Paths.get(System.getProperty("user.home")).resolve("Beginning Java Stuff").resolve(fileName);
    try(SeekableByteChannel channel = Files.newByteChannel(file, EnumSet.of(CREATE, READ, WRITE))) {
    boolean end = false;
    while(!end) {
      switch(selectOperation()) {

        case 'N': case 'n':                                                                        // Read and add a new name and address
          addNameAndAddress(channel);
          break;

        case 'D': case 'd':                                                                        // Display the contents of the file
          displayNamesAndAddresses(channel);
          break;

        case 'Q': case 'q':                                                                        // Quit the program
          end = true;
          break;

        default:
          System.out.println("Invalid Input. Try again.");
          break;
      }
    }
    } catch(IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
  }


  // Method to read a character entered on the keyboard
  private static char selectOperation() {
    System.out.println("\nEnter a letter to select an operation:" +
                       "\n Q or q to quit the program." +
                       "\n N or n to enter a new name and address." +
                       "\n D or d to display the contents of the file.");
  String s = null;
  try {
    while(true) {
      s = kb.readLine();
      if(s.trim().length() == 0) {                                                               // Check for empty string
        System.out.println("Nothing entered. Try again.");
      } else {
        break;
      }
    }
  } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
  }
  return s.charAt(0);                                                                              // Return the first non-blank letter
  }

  // Method to output the contents of the name and address file to the command line
  private static void displayNamesAndAddresses(SeekableByteChannel channel) throws IOException {
    channel.position(0L);                                                                          // Go to the beginning
    ByteBuffer lengthBuf = ByteBuffer.allocate(intSize);
    while(channel.read(lengthBuf) != -1) {                                                         // Loop until EOF found
      lengthBuf.flip();                                                                            // Ready to get length
      displayEntry(lengthBuf.getInt(), channel);
      lengthBuf.clear();                                                                           // Clear length buffer for next iteration
    }
  }

  // Method to display the entry at the current position in the file
  private static void displayEntry(int entryLength, SeekableByteChannel channel) throws IOException {
      dataBuf = ByteBuffer.allocate(charSize*entryLength);                                         // Create record buffer of appropriate size
      channel.read(dataBuf);                                                                       // Read the next record
      dataBuf.flip();                                                                              // Flip to access buffer contents

     // We can use the split method in the String class to separate the lines in the name & address
     String[] result = dataBuf.asCharBuffer().toString().split("\n");
     System.out.println();
     for (int i = 0 ; i < result.length ; ++i) {
         System.out.println(result[i]);
     }
  }

  // Method to add a new name and address to the file
  private static  void addNameAndAddress(SeekableByteChannel channel) throws IOException {
    StringBuffer sb = new StringBuffer();                                                          // Will hold the name & address
    String secondName = null;
    try {
      // The name will be stored in sb as a single line:
      //               "firstname secondname\n"
      System.out.println("Enter first name:");
      sb.append(kb.readLine().trim()).append(' ');
      System.out.println("Enter second name:");
      secondName = kb.readLine().trim();
      sb.append(secondName).append('\n');

      // We will add each address line to sb terminated by '\n'
      System.out.println("Enter address one line at a time. Enter ! to end:");
      String s = null;
      while((s = kb.readLine().trim()).charAt(0) != '!') {
       sb.append(s).append('\n');
      }
    } catch(IOException e) {
      System.err.println("Error reading from keyboard\n");
      e.printStackTrace();
      System.exit(1);
    }

    // Now we can write the name and address to the file
    // We will write the length(character count) of the name and address first,
    // then the name and address itself
    lengthBuf.putInt(sb.length());
    lengthBuf.flip();
    ByteBuffer dataBuf = ByteBuffer.allocate(charSize*sb.length());
    dataBuf.asCharBuffer().put(sb.toString());
    channel.position(channel.size());                                                              // Set the position to the end
    channel.write(lengthBuf);
    channel.write(dataBuf);
    lengthBuf.clear();
  }
 }