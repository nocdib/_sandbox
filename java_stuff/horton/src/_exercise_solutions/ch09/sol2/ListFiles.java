// Chapter 9 Exercise 2

import static java.nio.file.FileVisitResult.CONTINUE;
import static java.nio.file.FileVisitResult.SKIP_SUBTREE;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.FileVisitResult;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.io.IOException;

public class ListFiles extends SimpleFileVisitor<Path> {
  public ListFiles(String ext) {
    extension = ext;
  }

  // Called before a directory's contents are walked.
  // We only want to walk through the contents of the starting directory.
  // The directoryEntered field acts as a switch to suppress exploring
  // directories after the first.
  @Override
  public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attr) {
    if(directoryEntered) {
      return SKIP_SUBTREE;                                             // This is a subsequenbt directory so skip the contents
    } else {
      directoryEntered = true;
      System.out.format("\n%s contains the following files", dir);
      if(extension == null) {
      System.out.print(":");
      } else {
      System.out.format(" with extension %s:", extension);
      }
      return CONTINUE;
    }
  }

    // This is called after all the contents of a directory have been visited
    // This notes any error after walking a directory and resets the indent
    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException e) {
      if(e != null) {
        System.err.format("\n%sError walking directory: %s\n%s", indent, dir, e);
      }
     return CONTINUE;
    }

    // This is called for every entry in a directory, both files and directories
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attr) {
      // We only want to do something for files,
      // and if extension is not null, we only list the file if it has that extension
      if(attr.isRegularFile() && ((extension == null) || file.toString().endsWith(extension))) {
        System.out.format("\n%s%s", indent , file.getFileName());
      }
      return CONTINUE;
    }

    // Record file visit failure
    @Override
    public FileVisitResult visitFileFailed(Path file, IOException e) {
      System.out.format("\n%sFile attributes could not be read for: %s\n%s", indent, file,e);
      return CONTINUE;
    }

   private String extension;                                           // The file extension for files to be listed
   private boolean directoryEntered = false;                           // Switch to prevent subdirectories from being walked
   private String indent = "    ";                                     // Indent for file names
}
