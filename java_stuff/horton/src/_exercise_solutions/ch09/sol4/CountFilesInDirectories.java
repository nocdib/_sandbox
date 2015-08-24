// Chapter 9 Exercise 4
/*
 Write a program that will output a count of the total number of files in each directory
 in a file tree that is specified by a path entered on the command line and output
 the total number of directories and files in the tree.
 List the directories by name with each name followed by the file count in that directory.
 */

/*
 You could use a DirectoryStream to do this but using a SimpleFileVisitor object does most of the work for you.
 One problem is that you cannot be sure that all the files in a directory will be counted  before you start
 walking a subdirectory. This means you need a mechanism to allow for counts being accumulated for
 several directories at the same time. Another problem is that you have to allow for any depth of subdirectories
 in the tree. I used an array to store counts, and every time a subdirectory is entered, I increment the array index
 to use a new element to sotre the count. When the count for a directory is finished, I decrement the current array index
 and reset that count back to zero. I also allow for the possibilty of the number of elements in the array being
 insufficient for the depth of the file tree.
 */
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.FileVisitor;
import java.io.IOException;

public class CountFilesInDirectories {

    public static void main(String[] args) {
      if(args.length == 0) {
        System.out.println("You must supply a directory as a command line argument. Terminating...");
        System.exit(0);
      }
      Path treeBase = Paths.get(args[0]);
      treeBase = treeBase.toAbsolutePath();                            // Ensure path is absolute
      checkDirectory(treeBase);                                        // Ensure path exists and is a directory

    FileVisitor<Path> countFiles = new CountFiles();
    try {
      Files.walkFileTree(treeBase, countFiles);
    } catch(IOException e) {
      e.printStackTrace();
    }
  }

  // Method to ensure path represents a directory that exists
  static void checkDirectory(Path path) {
     if(!Files.exists(path)) {
      System.out.format("%s does not exist. Ending...", path);
      System.exit(0);
     }

    // The path could reference a file so verify that the path references a director
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
