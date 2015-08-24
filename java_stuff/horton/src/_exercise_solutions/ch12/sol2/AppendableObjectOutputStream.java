// Derived stream class to allow appending to an existing object file
/*
  The ObjectOutputStream constructor write a header to the file, regardless of
  whether the file exists or not. This results in an extra header being written at
  the end of an existing file that is opened in append mode. This results in a
  StreamCorruptedException when reading of any objects added to the file.

  This derived class allows objects to be appended.
  By using a factory method to create class object, we can set the append flag
  to signal when objects will be appended to an exixting file.
  This enables the override of the writeStreamHeader() method from the base class
  to decide whether or not the stream header should be written.
  You cannot construct the objects directly because the comnstructor must call
  the base class constructor first. This means you cannot set the append flag
  before the base class constructor is called, and it is the base class
  constructor that calls the writeStreamHeader() method.
*/

import static java.nio.file.StandardOpenOption.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.ObjectOutputStream;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.io.IOException;


public class AppendableObjectOutputStream extends ObjectOutputStream {
  // Constructor
  private AppendableObjectOutputStream(OutputStream out) throws IOException {
    super(out);
  }

  // Factory method to create objects of this class type
  public static ObjectOutputStream newObjectOutputStream(Path file, boolean appendToFile) throws IOException {
    append = appendToFile;
    return new AppendableObjectOutputStream(new BufferedOutputStream(Files.newOutputStream(file, CREATE, APPEND)));
  }

  // Overrides the base class method to only write the stream header when a noew file is being written
  @Override
  protected void writeStreamHeader() throws IOException {
    if(append) {                                                                         // If we are appending objects
      reset();                                                                           // just reset the stream
    } else {                                                                             // otherwise it's a new file
      super.writeStreamHeader();                                                         // so write the stream header
    }
  }

  private static boolean append;                                                         // Flag indicating when we are appending objects
}