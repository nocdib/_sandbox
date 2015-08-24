// Chapter 12 Exercise 4

/*
 Use the index file to provide random direct access to the object file
 for querying random names entered from the keyboard.
 Entering a name from the keyboard should result in the address for the individual,
 or a message indicating the entry is not present in the file.
 The process is to first search the index file for an object with a name field
 matching the keyboard entry. When an IndexEntry is found, you use the sequence number
 it contains to retrieve the appropriate Person object.
*/

/*
  This exercise is intended to make you try accessing two object files at the same time.
  In fact the indexfile is not necessary to allow serching of the file containing Person objects.
  If you have got solutions to the first three exercises this is a small extension using
  the index file you have created.
*/

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.EOFException;
import java.nio.file.attribute.BasicFileAttributes;

public class PersonSaver {
  public static void main(String[] args) {
    Path file = Paths.get(System.getProperty("user.home")).resolve("Beginning Java Stuff").resolve(fileName);
    Path indexFile = Paths.get(System.getProperty("user.home")).resolve("Beginning Java Stuff").resolve(indexFileName);

    // Ensure parent directory exists
    try {
      Files.createDirectories(file.getParent());
    } catch (IOException e) {
      System.err.println("I/O error creating parent directory.");
      e.printStackTrace();
      System.exit(1);
    }
    currentIndex = getFreeIndex(file);

    // Get operation selection from the keyboard
    while(true) {
      switch(selectOperation()) {

        case 'N': case 'n':                                                                        // Read and add a new person
          addPerson(file, indexFile);
          break;

        case 'Q': case 'q':                                                                        // Quit the progra
          System.exit(0);
          break;

        case 'L': case 'l':                                                                        // List the file contents
          listFile(file);
          break;

        case 'F': case 'f':                                                                        // Find entries for a given name
          findPerson(indexFile, file);
          break;

        default:
          System.out.println("Invalid Input. Try again.");
          break;
      }
    }
  }

  // Get the index for the next object added to the file
  // This will be the number of objects in the file
  private static int getFreeIndex(Path file) {
    int index = 0;
    if(appendFileCheck(file)) {
      try(ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(Files.newInputStream(file)))) {
        while(true) {
          in.readObject();                                                                           // This throws EOFException when the EOF is reached
          ++index;
        }
      } catch(EOFException  e) {                                                                     // Here we have finished reading objects from the file
        // So continue and return the index value
      } catch(ClassNotFoundException  e) {                                                           // This should not happen
        System.err.println("Person class not found.");
        System.exit(1);
        e.printStackTrace();
      } catch(IOException e){
        e.printStackTrace();
        System.exit(1);
      }
    }
    return index;
  }

  // List the file contents
  private static void listFile(Path file) {
    try(ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(Files.newInputStream(file)))) {
      System.out.println("The file contains the following:\n");
      while(true) {
        System.out.println((Person)in.readObject());                                               // This throws EOFException when the EOF is reached
      }
    } catch(EOFException  e) {                                                                     // Here we have finished reading objects from the file
      System.out.println("End of file reached.");
    } catch(ClassNotFoundException  e) {                                                           // This should not happen
      System.err.println("Person class not found.");
      e.printStackTrace();
      System.exit(1);
    } catch(IOException e){
      e.printStackTrace();
      System.exit(1);
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
  private static void addPerson(Path file, Path indexFile) {
    try(ObjectOutputStream out = AppendableObjectOutputStream.newObjectOutputStream(file, appendFileCheck(file));
        ObjectOutputStream indexOut = AppendableObjectOutputStream.newObjectOutputStream(indexFile, appendFileCheck(indexFile))) {
      Person person = getPerson();
      out.writeObject(person);
      indexOut.writeObject(new IndexEntry(person.getName().toString(), currentIndex++));
    } catch(IOException e){
      e.printStackTrace();
      System.exit(1);
    }
  }

  // Method to read a character entered on the keyboard
  private static char selectOperation() {
    System.out.println("\nEnter a letter to select an operation:" +
                       "\n Q or q to quit the program." +
                       "\n N or n to enter a new name and address." +
                       "\n L or l to list the file contents." +
                       "\nF or f to find the entry for a name");

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

  // Method to find records for a given name, assuming there may be multiple occurrences
  // The method searches the index file sequentially for a name name.
  // When a match is found, the Persons.bin file is accessed to find the person for the index.
  // The entries in the index file and the persons file are int the same sequence so
  // it is not necessary to start at the beginning of the persons file to find persons for
  // subsequent index file entries.
  private static void findPerson(Path indexFile, Path file) {
    int index = -1;                                                                                // Index for a name
    try(ObjectInputStream indexIn = new ObjectInputStream(new BufferedInputStream(Files.newInputStream(indexFile)));
        ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(Files.newInputStream(file)))) {
        System.out.println("Enter the first name:");
        String name = kb.readLine().trim();
        System.out.println("Enter the second name:");
        name += " " + kb.readLine().trim();
        System.out.println("Entries for " + name + ":\n");
          int personIndex = 0;                                                                     // Position in the Persons.bin file
          Person person = null;
        while(true) {
          IndexEntry entry = (IndexEntry)indexIn.readObject();
          if(entry.getName().equals(name)) {
            index = entry.getPosition();
            while(personIndex <= index) {
              person = (Person)in.readObject();
              ++personIndex;
            }
            System.out.println(person);
          }
        }
    } catch(EOFException  e) {                                                                     // Here we have finished reading objects from the file
      System.out.println("End of file reached.");
      if(index < 0) {
      System.out.println("No entries found.");
      }
    } catch(ClassNotFoundException  e) {                                                           // This should not happen
      System.err.println("Class not found.");
      e.printStackTrace();
      System.exit(1);
    } catch(IOException e){
      e.printStackTrace();
      System.exit(1);
    }
  }

  private static String fileName = "Persons.bin";                                                  // Name of file that stores Person objects
  private static String indexFileName = "PersonIndexes.bin";                                       // Name of file that stores IndexEntry objects

  private static BufferedReader kb = new BufferedReader(new InputStreamReader(System.in));         // Buffered keyboard input stream
  private static int currentIndex;                                                                 // Next free index to the Persons file

}
