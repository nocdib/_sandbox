// Chapter 9 Exercise 4

import static java.nio.file.FileVisitResult.CONTINUE;
import static java.nio.file.FileVisitResult.SKIP_SUBTREE;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.FileVisitResult;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.io.IOException;

public class CountFiles extends SimpleFileVisitor<Path>{
  // Called before a directory's contents are walked.
  // This method creates a new arrayt to store counts if the current array
  // is too small.
  @Override
  public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attr) {
      System.out.format("\n%sDirectory: %s", indent.toString(), dir);
      indent.append("    ");                                           // Increase indent for subsidiary directory
      if(++dirIndex == fileCounts.length) {                            // If the array of file counts is full...
        countLength += lengthIncrement;
        int[] newCounts = new int[countLength];                        // ... create a larger array
        for(int i = 0 ; i < fileCounts.length ; ++i) {                 // Copy existing counts to new array
          newCounts[i] = fileCounts[i];
        }
        fileCounts = newCounts;                                        // Replace old array of counts with new array
      }
      return CONTINUE;
  }

    // This is called after all the contents of a directory have been visited
    // This notes any error after walking a directory,
    // Outputs ther file count for the current directory
    // and frees up the array element for future use.
    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException e) {
      if(e != null) {
        System.err.format("\n%sError walking directory: %s\n%s", indent, dir, e);
      }
      indent.delete(indent.length()-4, indent.length());               // Remove last four spaces
      System.out.format("\n%sDirectory: %s contains %d files.", indent.toString(), dir, fileCounts[dirIndex]);
      fileCounts[dirIndex] = 0;                                        // Reset count and
      --dirIndex;                                                      //  make available for another directory
      return CONTINUE;
    }

    // This is called for every entry in a directory, both files and directories
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attr) {
      // We only want to count files so increment the current directory count if it is a file
      if(attr.isRegularFile()) {
        ++fileCounts[dirIndex];
      }
      return CONTINUE;
    }

    // Record file visit failure
    @Override
    public FileVisitResult visitFileFailed(Path file, IOException e) {
      System.out.format("\n%sFile attributes could not be read for: %s\n%s",
                                                                        indent, file,e);
      return CONTINUE;
    }

   private StringBuilder indent = new StringBuilder();                 // Indent for directory names
   int dirIndex = -1;                                                  // Index to count array for current directory
   int countLength = 2;                                                // Current array length for fileCounts
   int lengthIncrement = 3;                                            // Increment for array size when it needs to be increased
   int[] fileCounts = new int[countLength];                            // Array to store count of files in directories
}
