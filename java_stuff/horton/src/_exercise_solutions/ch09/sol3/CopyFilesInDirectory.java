// Chapter 9 Exercise 3

/*
 Write a program that will copy all the files in one directory to another
 with the paths for the two directories being entered from the command line.
 The names of the file copies should have _old appended to them and
 no files in the destination folder should be overwritten.
 */

/*
 I chose to create trhe destination diorectory if it does not exist.
 My solution also accepts relative paths as command line arguments
 which will be relative to the current directory.

 I guess it would be better to append _new to the file names for the copies
 but I just implemented what the exercise says.
 */
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.io.IOException;

public class CopyFilesInDirectory {

  public static void main(String[] args) {
    if(args.length != 2) {
      System.out.println("Insuffient command line arguments.");
      System.out.println("You must specify a source directory and a destination directory. Terminating...");
      System.exit(0);
    }

    Path fromDir = Paths.get(args[0]);                                 // Source directory for files to be copied
    Path toDir = Paths.get(args[1]);                                   // Destination directory for file copies
    fromDir = fromDir.toAbsolutePath();                                // Make path absolute
    toDir = toDir.toAbsolutePath();                                    // Make path absolute

    // Verify that the source directory exists, and that it is a directory
    if(!Files.exists(fromDir)) {
      System.out.println("source directory " + fromDir + " does not exist. Terminating...");
      System.exit(0);
    }
    if(!Files.isDirectory(fromDir)) {
      System.out.println("source directory " + fromDir + " is not a directory. Terminating...");
      System.exit(0);
    }

    // Check the destination directory and create if necessary
    if(!Files.exists(toDir)) {
      System.out.println("Destination directory " + toDir + " does not exist. Creating it...");
      if(!createSingleDirectory(toDir)) {
        System.exit(1);
      }
    }

    // Copy files from source directory to destination
    System.out.println("Copying files from " + fromDir + " to " + toDir + ".");
    if(!copyFiles(fromDir, toDir)) {
        System.out.println("Copying files failed.");
    } else {
        System.out.println("Copying files completed.");
    }
  }

  // Create a directory with the given path
  static boolean createSingleDirectory(Path path) {
    try {
      Files.createDirectories(path);
      System.out.println("\n" + path + " directory created.");
      return true;
    } catch(IOException e) {
      System.err.println("\nDirectory creation failed:\n" + e);
      return false;
    }
  }

  // Copy the files in the from directory to the to directory
  static boolean copyFiles(Path from, Path to) {
    // This uses a DirectoryStream object for the from directory to
    // iterate over the files with names of the form *.*.
    // You could allow for files without an extension.
    // This would complicate the process a little because the period
    // separating the extension from the file name may or may not be present.
    try (DirectoryStream<Path> files = Files.newDirectoryStream(from, "*.*")) {
      System.out.println("Starting copy...");
      for(Path file : files) {
        String fileName = ensureUnique(to, file);                      // Ensure the name for the new file is unique
        Files.copy(file, to.resolve(fileName));                        // Copy the file
        System.out.println("  " + file.getFileName() + " copied.");
      }
    } catch(IOException e) {
      System.err.println("I/O error in copyFiles. " + e);
      return false;
    }
    return true;
  }

  // Creates a file name from file that does not already exist in directory dir
  // by appending _old at least once, and as many times as necessary.
  static String ensureUnique(Path dir, Path file) {
    // StringBuilder is more efficient that String when you are modifiying a string.
    StringBuilder fileName = new StringBuilder(file.getFileName().toString());
    while(true) {
      // The loop continues if a file with the current name exists in the destination directory.
      // The statement following appends _old to the current file name following the past period
      // in the name, then converts the result to a String object to pass to the resolve() method
      // which return the Path object that is the path for the newly named file.
      // If the static exists() method in the Files class returns false with that path as
      // the argument, the loop ends.
      if(!Files.exists(dir.resolve(fileName.insert(fileName.lastIndexOf("."), "_old").toString()))) {
        break;
      }
    }
    return fileName.toString();                                        // Return the name for the new file
  }
}


