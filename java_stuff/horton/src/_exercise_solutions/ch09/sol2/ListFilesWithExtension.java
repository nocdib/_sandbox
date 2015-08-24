// Chapter 9 Exercise 2

/*
 Write a program to list all the files with a given extension in a directory.
 The absolute path for the directory and the extension should be read from the command line.
 If the extension is absent from the command-line input,
 the program should list all the files in the specified directory.
 */

 /*
  This is somewhat similar to the previous exercise.
  There's quite a lot of code to validate the directory path and to
  make sure the extension is valid. If the extension is not valid,
  the program lists all the files in the directory.
  */

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.FileVisitor;
import java.io.IOException;

public class ListFilesWithExtension {

  // There are deliberate switch fall-throughs so suppress the warning
  @SuppressWarnings("fallthrough")
  public static void main(String[] args) {
    Path treeBase = null;
    String extension = null;
    switch(args.length) {
      case 0:
        System.out.println("You must supply a directory and an extension as command line arguments. Ending.");
        System.exit(0);
      case 2:
        extension = args[1];
        extension = checkExtension(extension);

        // Intentional fall through to the next case...
      case 1:
        treeBase = Paths.get(args[0]);
        checkDirectory(treeBase);
        break;
      default:
        System.out.println("Too many command line arguments.");
        System.exit(0);
        break;
    }
    FileVisitor<Path> listDirectories = new ListFiles(extension);
    int depth = 3;
    try {
      Files.walkFileTree(treeBase, listDirectories);
    } catch(IOException e) {
      e.printStackTrace();
    }
  }

  // Check that the extension is valid
  static String checkExtension(String extension) {
    if(!validExtension(extension)) {
      System.out.format("You entered \"%s\" as the extension.", extension);
      System.out.println("\nIt should be just a file extension, with or without a period. Listing all files.");
      return null;
    }
    if(!extension.startsWith(".")) {
      extension = "." + extension.toString();
    }
    return extension;
  }

  // Verify that the extension is the correct form
  static boolean validExtension(String ext) {
    char ch = ext.charAt(0);                                           // First character
    if(ch != '.' && !Character.isLetterOrDigit(ch)) {                  // must be period or a letter or digit
      return false;
    }
    // All other characters must be a letter or a digit
    for(int i = 1 ; i < ext.length() ; ++i) {
      if(!Character.isLetterOrDigit(ext.charAt(i))) {
        return false;
      }
    }
    return true;
  }

  // Verify a path is a valid directory
  static void checkDirectory(Path path) {
    // Directory path must be absolute and must exist.
    if(!path.isAbsolute()) {
      System.out.format("%s must be an absolute path to a directory. Ending.", path);
      System.exit(0);
     } else if(!Files.exists(path)) {
      System.out.format("%s does not exist. Ending.", path);
      System.exit(0);
     }

    // The path could reference a file
    // Verify that the path references a directory
    try {
    BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
    if(!attr.isDirectory()) {
      System.out.format("%s is not a directory. Ending.", path);
      System.exit(0);
    }
    } catch(IOException e) {
      System.err.println("I/O error in isDirectory method. " + e);
      System.exit(1);
    }
  }
}
