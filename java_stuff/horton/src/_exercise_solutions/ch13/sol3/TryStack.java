// Chapter 13 Exercise 3

/*
 Modify your Stack<> type to make it serializable.
 Demonstrate that this is the case by creating a
 Stack<String> object, adding 10 strings to it,
 serializing and deserializing the Stack<String> object,
 and listing the contents of the deserialized stack.
 */

/*
 Not quite so easy as the previous exercise, but not difficult either.
 This just involves applying what you learned in the previous chapter
 to the Stack class.
 Making Stack serializable only requires that it inplements the Serializable
 interface and defines the serialVersionUID member. Its data members are all
 serializable.
 */

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedInputStream;
import java.io.IOException;

public class TryStack {
  @SuppressWarnings("unchecked")
  public static void main(String[] args) {
    // Values to be stored in stack and then retrieved
    String[] strings = {
           "The bigger they are, the harder they hit.",
           "It's not the knowing that is difficult, but the doing." ,
           "It's a mistake to change horses in midstream.",
           "An ounce of practice is worth a pound of instruction.",
           "It's a poor dog that's not worth whistling for.",
           "If wishes were horses, beggars would ride.",
           "To leap high you must take a long run.",
           "Things are never so bad that they can't get worse.",
           "If in doubt, do nowt.",
           "A cat in gloves will catch no mice." };

    Stack<String> data = new Stack<>();

    // Store the strings on the stack
    System.out.println("Strings pushed onto the stack in sequence are:");
    for(String string : strings) {
      System.out.println(string);
      data.push(string);
    }
    String directory = "Beginning Java Stuff";
    String fileName = "StringStack.bin";                               // Stores Stack object
    Path file = Paths.get(System.getProperty("user.home")).resolve(directory).resolve(fileName);

    // Ensure parent directory exists
    try {
      Files.createDirectories(file.getParent());
    } catch (IOException e) {
      System.err.println("I/O error creating parent directory.");
      e.printStackTrace();
      System.exit(1);
    }

    // Write the Stack object to a file
    try (ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(Files.newOutputStream(file)))){
      out.writeObject(data);
    } catch(Exception e) {
      e.printStackTrace();
      System.exit(1);
    }

    // Now read the Stack object back from the file
    Stack<String> newStack = null;
    try(ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(Files.newInputStream(file)))) {
      newStack = (Stack<String>)in.readObject();
    } catch(Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
    // List the contents of the new stack
    System.out.println("\nContents of stack read from file:");
    newStack.listAll();
  }
}