// Chapter 9 Exercise 1

/*
 Write a program that lists all the directories in a directory
 defined by a path supplied as a command-line argument.
 */

 /*
  I chose to output the full path for the initial directory,
  then just the directory names for the rest.
  I also chose to indent the directory name output by the length
  of the containing directory name. This provides a cue to the
  contents of each directory. You could also indent by a fixed
  amount if the directory depth was too deep for ouput in a
  single line.
  */

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.FileVisitor;
import java.io.IOException;


public class ListAllDirectories {

  public static void main(String[] args) {
    Path treeBase = null;
    if(args.length > 0) {
      treeBase = Paths.get(args[0]);
      if(!isDirectory(treeBase)) {
      System.out.format("%s is not a directory.", treeBase);
        System.exit(0);
      }
    } else {
      System.out.println("You must supply a directory as a command line argument.");
      System.exit(0);
    }
    FileVisitor<Path> listDirectories = new ListDirectories();
    int depth = 3;
    try {
      Files.walkFileTree(treeBase, listDirectories);
    } catch(IOException e) {
      e.printStackTrace();
    }
  }

  // Verify a path is a directory
  static boolean isDirectory(Path path) {
    try {
    BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
    return attr.isDirectory();
    } catch(IOException e) {
      System.err.println("I/O error in isDirectory() method. " + e);
    }
    return false;
  }

}
