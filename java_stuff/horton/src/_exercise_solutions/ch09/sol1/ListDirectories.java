import static java.nio.file.FileVisitResult.CONTINUE;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.io.IOException;

public class ListDirectories extends SimpleFileVisitor<Path> {

  // Output the directory path and set the indent for entries
  @Override
  public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attr) {
    if(indent.length() < spaces.length()) {
      // This is the first level so output the full path
      System.out.format("\n%sDirectory: %s contains the following directories:", indent, dir);
      indent.append(spaces);
    } else {
      // All subdirectories are indented so output just the name
      String dirName = dir.getFileName().toString();

      // Increase the indent by one more than the length of the directory name
      System.out.format("\n%s%s:", indent, dirName);
      for(int i = 0 ; i <= dirName.length() ; ++i) {
        indent.append(" ");
      }
    }
    return CONTINUE;
  }

    // This is called after all the contents of a directory have been visited
    // This note any error after walking a directory and resets the indent
    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException e) {
      if(e != null) {
        System.err.format("\n%sError walking directory: %s\n%s", indent, dir, e);
      }

     // Reset the indent
     int len = indent.length();
     if(len > spaces.length()) {
       // Here the indent is greater than the length of spaces so
       // the current directory name has been appended
       String dirName = dir.getFileName().toString();
       indent.delete(len - dirName.length() - 1, len);
     } else {
       // Here it is just the initial indent of spaces
       indent.delete(len - spaces.length(), len);
     }
     return CONTINUE;
    }

    // This is called for every entry in a directory, both files and directories
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attr) {
      // We only want to do something for directories
      if(attr.isDirectory()) {
        // It is a directory so output just the name indented
        String dirName = file.getFileName().toString();
          System.out.format("\n%s%s", indent , file.getFileName());
      }
      return CONTINUE;
    }

    // Record file visit failure
    @Override
    public FileVisitResult visitFileFailed(Path file, IOException e) {
      System.out.format("\n%sFile attributes could not be read for: %s\n%s", indent, file, e);
      return CONTINUE;
    }

   private String spaces = "     ";                                    // First indent
   private StringBuilder indent = new StringBuilder();                 // Indent for entries
}
