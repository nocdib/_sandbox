// Chapter 11 Exercise 6

/*
 Modify the previous example to store an index to the name and address file
 in a separate file. The index file should contain each person's second name,
 plus the position where the corresponding name and address can be found in
 the name and address file. Provide support for an optional command argument
 allowing a person's second name to be entered. When the command-line argument
 is present, the program should then find the name and address and output it
 to the command line. */

/* I used a channel opened for reading and writing to access the file
   containing the names and addresses and the same mechanism for the
   index file. The index file records are of the form:
   index to name and address file : surname length : surname

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
  // These static members are all accessible from the static methods
  private static final int charSize = 2;                                                           // Size of type char
  private static final int intSize = 4;                                                            // Size of type int
  private static final int longSize = 8;                                                           // Size of type long
  private static String fileName = "NamesAndAddresses.bin";                                        // File name
  private static String indexFileName = "Index.bin";                                               // Index file for name & address file
  private static BufferedReader kb = new BufferedReader(new InputStreamReader(System.in));         // Keyboard input
  private static ByteBuffer lengthBuf = ByteBuffer.allocate(intSize);                              // Buffer for record length
  private static ByteBuffer indexBuf = ByteBuffer.allocate(longSize + intSize);                    // Buffer for record length and index
  private static ByteBuffer dataBuf = null;                                                        // Buffer for name & address


  public static void main(String[] args) {
    Path file = Paths.get(System.getProperty("user.home")).resolve("Beginning Java Stuff").resolve(fileName);
    Path indexFile = Paths.get(System.getProperty("user.home")).resolve("Beginning Java Stuff").resolve(indexFileName);
    try(SeekableByteChannel channel = Files.newByteChannel(file, EnumSet.of(CREATE, READ, WRITE)) ;
         SeekableByteChannel indexChannel = Files.newByteChannel(indexFile, EnumSet.of(CREATE, READ, WRITE))) {
      if(args.length > 0) {
        findEntry(args[0], indexChannel, channel);
      }
      boolean end = false;
      while(!end) {
        switch(selectOperation()) {

          case 'N': case 'n':                                                                        // Read and add a new name and address
            addNameAndAddress(channel, indexChannel);
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

  // Find a name and adddress entry corresponding to a surname
  private static void findEntry(String surname, SeekableByteChannel indexChannel, SeekableByteChannel channel) throws IOException {
    // First find the index corresponding to the surname
    System.out.println("Searching for \"" + surname + "\":");
    ByteBuffer surnameBuf = null;
    indexChannel.position(0);                                                                          // The start of the index file
    int count = 0;                                                                                     // Count of entries found

    // Search the entire index file for surname
    // and output all records for this name.
    while(indexChannel.read(indexBuf) != -1) {                                                         // Loop until EOF found
      indexBuf.flip();
      long index = indexBuf.getLong();                                                                 // First get the index
      int length = indexBuf.getInt();                                                                  // then the surname length
      indexBuf.clear();
      surnameBuf = ByteBuffer.allocate(charSize*length);                                               // Buffer for surname
      indexChannel.read(surnameBuf);                                                                   // Read the surname from the file
      surnameBuf.flip();
      if(surname.equals(surnameBuf.asCharBuffer().toString())) {                                       // Is it what we are looking for
        ++count;                                                                                       // Yes! Found one.
        displayEntryAtIndex(channel, index);                                                           // Now get the entry from the name and address file
      }
    }
    if(count == 0) {
      System.out.println("No entries found.");
    } else {
      System.out. println(count + (count > 1 ? " entries" : " entry") + " found.");
    }
  }

  // Display an entry for a given index position
  private static void displayEntryAtIndex(SeekableByteChannel channel, long index) throws IOException {
    channel.position(index);                                                                           // Set file position to index
    channel.read(lengthBuf);                                                                           // Get the length of the entry
    lengthBuf.flip();
    displayEntry(lengthBuf.getInt(), channel);                                                         // Display the entry
    lengthBuf.clear();
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
  private static  void addNameAndAddress(SeekableByteChannel channel, SeekableByteChannel indexChannel) throws IOException {
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
    lengthBuf.putInt(sb.length()).flip();
    ByteBuffer dataBuf = ByteBuffer.allocate(charSize*sb.length());
    dataBuf.asCharBuffer().put(sb.toString());
    long index = channel.size();                                                          // Record the index position for the entry
    channel.position(index);                                                              // Set the position to the end
    channel.write(lengthBuf);                                                             // Write length of name and address
    channel.write(dataBuf);                                                               // Write name and address
    lengthBuf.clear();

    // Now we can write the index file.
    // We will write the index to the name and address file
    // followed by the length of the surname
    // followed by the surname.
    indexChannel.position(indexChannel.size());                                           // Go to the end of the index file
    int length = secondName.length();                                                     // Length of surname
    indexBuf.putLong(index).putInt(length).flip();
    indexChannel.write(indexBuf);
    indexBuf.clear();
    dataBuf = ByteBuffer.allocate(charSize*length);
    dataBuf.asCharBuffer().put(secondName);
    indexChannel.write(dataBuf);
  }
 }