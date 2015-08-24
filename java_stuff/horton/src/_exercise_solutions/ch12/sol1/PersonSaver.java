// Chapter 12 Exercise 1

/*
 Define a Person class to encapsulate a person's name and address
 with the name and address being fields of type Name and Address.
 Write a program to allow names and addresses to be entered from
 the keyboard and stored as Person objects in a file.
 After the file exists new entries should be appended to the file.
 */

/*
  This exercise is another wolf in sheep's clothing. It requires a bit of
  research to come up with a solution.
  This is because there is a major problem with the ObjectOutputStream class; it does
  not provide for appending objects to an existing file.
  If you apend objects to an existing file, reading the appended objects back causes
  a StreamCorruptedException to be thrown.

  The reason for this is that the ObjectOutputStream constructor calls the protected
  writeStreamHeader() method, even when the file is to be appended to. Thus your
  file will have two stream headers - one at the beginning and one at the end
  before any objects have been appended.

  So how can you get around this?

  One way to solve the problem is to subclass the ObjectOutputStream class.
  This will allow you to override the writeStreamHeader() method to prevent
  the stream header being written when you are appending to an existing file
  containing objects. This is the approach my solution takes.

  Anther possible approach is to write a new file each time and copy the contents
  of the existing file to it. You can then append objects to the new file before
  closing it. When you have finished update the file, you can delete the old file and
  rename the new file to be the same as the old.
*/

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.ObjectOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.file.attribute.BasicFileAttributes;

public class PersonSaver {
  public static void main(String[] args) {
    Path file = Paths.get(System.getProperty("user.home")).resolve("Beginning Java Stuff").resolve(fileName);

    // Ensure parent directory exists
    try {
      Files.createDirectories(file.getParent());
    } catch (IOException e) {
      System.err.println("I/O error creating parent directory.");
      e.printStackTrace();
      System.exit(1);
    }

    // Get operation selection from the keyboard
    while(true) {
      switch(selectOperation()) {

        case 'N': case 'n':                                                                        // Read and add a new person
          addPerson(file);
          break;

        case 'Q': case 'q':                                                                        // Quit the progra
          System.exit(0);
          break;

        default:
          System.out.println("Invalid Input. Try again.");
          break;
      }
    }
  }

  // Check file to see if we are appending
  private static boolean appendFileCheck(Path file) {
    try {
      // If the file exists and its size is non-zero, it contains data so we are appending
      if(Files.exists(file) && Files.readAttributes(file, BasicFileAttributes.class).size() > 0) {
        return true;
      }
    } catch (IOException e) {
      System.err.println("I/O error checking file.");
      e.printStackTrace();
      System.exit(1);
    }
    return false;
  }

  // Add a new person to the file
  private static void addPerson(Path file) {
    try(ObjectOutputStream out = AppendableObjectOutputStream.newObjectOutputStream(file, appendFileCheck(file))) {
      out.writeObject(getPerson());
    } catch(IOException e){
      e.printStackTrace();
      System.exit(1);
    }
  }

  // Method to read a character entered on the keyboard
  private static char selectOperation() {
    System.out.println("\nEnter a letter to select an operation:" +
                       "\n Q or q to quit the program." +
                       "\n N or n to enter a new name and address.");

    String s = null;
    try {
      s = kb.readLine();
    } catch (IOException e) {
      System.err.println("I/O error reading an operation selection from the keyboard.");
        e.printStackTrace();
        System.exit(1);
    }

    return s.trim().charAt(0);                                                                     // Return the first non-blank letter
  }

  // Method to add a new Person object to the file
  private static Person getPerson() {
    Person person = null;
    String secondname = null;
    try {
      System.out.println("Enter first name:");
      String firstname = kb.readLine().trim();
      System.out.println("Enter second name:");
      secondname = kb.readLine().trim();
      Name name = new Name(firstname, secondname);

      StringBuffer sb = new StringBuffer();                                                        // To store the address

      // We will add each address line to sb terminated by '\n'
      System.out.println("Enter address one line at a time. Enter ! to end:");
      String s = null;
      while((s = kb.readLine().trim()).charAt(0) != '!') {
        sb.append(s).append('\n');
      }
      return person = new Person(new Name(firstname, secondname), new Address(sb.toString()));
    } catch(IOException e) {
      System.err.println("Error reading from keyboard. Terminating...\n");
      e.printStackTrace();
      System.exit(1);
    }
    return null;
  }

  private static String fileName = "Persons.bin";                                                  // Name of file that stores Person objects
  private static BufferedReader kb = new BufferedReader(new InputStreamReader(System.in));         // Buffered keyboard input stream

}
