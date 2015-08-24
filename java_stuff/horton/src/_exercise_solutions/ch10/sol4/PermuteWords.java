// Chapter 10 Exercise 4
/*
 Write a program that, for a given String object defined in the code, writes strings to a file
 in the local character encoding (as bytes) corresponding to all possible permutations of the words
 in the string. For example, for the string the fat cat, you would write the strings
 the fat cat, the cat fat, cat the fat, cat fat the,  fat the cat, and fat cat the to the file,
 although not necessarily in that sequence.
 (Don't use very long strings; with n words in the string,
  the number of permutations is n!, so a string with 10 words has 3,628,800 permutations!).
*/

/*
 This exercise is a wolf in sheep's clothing.
 Creating all permutations of the words in the phrase is the most difficult part of the exercise.
 If you can crack this, then writing the file is trivial :-)
 I chose to use a recursive approach because it is relatively easy to understand, especially
 if you consider how an example might work..

 Suppose we want to obtain pernmutations of 4 words. You can reason as follows:

  * You can get the permutaions that start with the first word by obtaining the permutations
    of the remaining three words and appending them to the first word.
  * There are four possible first words, so if you repeat the process for each possible first word,
    you will have all possible permutations.
  * You can get the permutations of the remaining three words in each instance in the same way - append
    each of the permutations of the last two words and to the first word. Repeat this for the three possible
    first words.
  * Of course, there are two permutations of two words, the first followed by the second, and the second
    followed by the first.

 All you need to do is implement this as a recursive method.
 A limitation of the method I have implemented is that it ignores the possibility of duplicate words.

 The file should be written in the default charset, which leadsd to a BufferedWriter as the best choice
 for output to the file. Writing the data in the default charset means that the file is easily readable.
 You can view the contents with Notepad to check that it works.
*/

import java.io.BufferedWriter;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;

class PermuteWords {

  public static void main(String args[]) {
    // You could easily arrange to read this from the command line
    String phrase = "Many hands make light work";

    // Extract the words from phrase into an array.
    String[] words = extractWords(phrase);
    String[] permutations = permute(words);

    // Create the path for the file to contain permutations
    String fileName = "permuted.txt";
    Path path = Paths.get(System.getProperty("user.home")).resolve("Beginning Java Stuff").resolve(fileName);

    // Make sure we have a directory for the file
    try {
      Files.createDirectories(path.getParent());
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }

    // Create a buffered write to write characters in the default charset
    // then write the permutations toi the file
    try(BufferedWriter writer = Files.newBufferedWriter(path, Charset.defaultCharset())){
      // Write permutations to the file
      for(String permutation : permutations) {
        writer.write(permutation, 0, permutation.length());                        // Write the permutation  to the file
        writer.newLine();                                                          // Write a newline
      }
      System.out.format("\nFile %s in directory %s written.", fileName, path.getParent().toString());
    } catch(IOException e) {
      System.err.format("\nI/O Error. %s not written. ", fileName);
      e.printStackTrace();
      System.exit(1);
    }
  }

  // Extract the words from the phrase
  public static String[] extractWords(String phrase) {
    // First figure out how many words there are, assuming words are separated by spaces...
    int fromIndex = 0;
    phrase = phrase.trim();                                                        // Remove any leading or trailing spaces

    int wordCount = 1;                                                             // At least one word
    while((fromIndex = phrase.indexOf(' ', fromIndex)) != -1) {
      ++wordCount;

      while(phrase.charAt(++fromIndex) == ' ')                                     // Skip trailing contiguous spaces
        ;
    }

    String[] words = new String[wordCount];                                        // Array to hold the words from the phrase
    String[] permutation = new String[wordCount];                                  // Array to hold permuted words

    // Now get the words
    fromIndex = 0;                                                                 // Starting position
    int toIndex = 0;                                                               // Word end position
    int index = 0;                                                                 // Free element in words array
    while((toIndex = phrase.indexOf(' ', fromIndex)) != -1) {
      words[index++] = phrase.substring(fromIndex, toIndex);
      fromIndex = toIndex;

      while(phrase.charAt(++fromIndex) == ' ')                                     // Skip trailing contiguous spaces
        ;
    }

    if(fromIndex < phrase.length()) {
      words[index] = phrase.substring(fromIndex, phrase.length());
    }
    return words;
  }

  // REcursive method to generate permutaions
  public static String[] permute(String[] items) {
    if(items.length == 2) {                                                          // If there are only two items, there are just two permutations...
      String[] results = {items[0]+ " " + items[1], items[1] + " " + items[0]};      // ... so we can create them ...
      return results;                                                                // ... and return the array.
    }

    // There are more that two items if we get to here so we must get all possible
    // first items, each followed by the permutations of the others
    String[] results = new String[factorial(items.length)];                          // Holds permutations of items
    int freeIndex = 0;                                                               // Index to results
    String[] subsetItems = new String[items.length - 1];                             // Array to hold subset of items, excluding the first

    // Make each of the items in turn the first item
    String temp = null;                                                              // Used to swap items
    for(int i = 0 ; i < items.length ; ++i) {
      if(i > 0) {                                                                    // Only swap with first if it is not the first item
        temp = items[0];
        items[0] = items[i];
        items[i] = temp;
      }

      // Get the subset of items exluding the current first item
      for(int j = 0 ; j< subsetItems.length ; ++j) {
        subsetItems[j] = items[j + 1];
      }

      String[] perms = permute(subsetItems);                                         // Get permutations of the subset
      for(int k = 0 ; k < perms.length ; ++k){                                       // Append each of the subset permutations to the first item
        results[freeIndex++] = items[0] + " " + perms[k];                            // and store in the results array
      }
    }
    return results;
  }

  // Factorial of integer n
  // Yoou could implement this as a recursive method but a loop is much faster
  public static int factorial(int n) {
    if(n < 2 ) {                                                                     // 0! and 1! are both 1
      return 1;
    }

    // Factorial of n is the product of all the integers from 2 to n
    int result = 2;
    for(int i = 3 ; i <= n ; ++i) {
      result *= i;
    }
    return result;
  }
}
