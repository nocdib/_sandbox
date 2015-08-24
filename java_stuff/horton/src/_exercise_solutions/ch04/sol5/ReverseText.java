//Chapter 4, Exercise 5
/*
 Write a program that reverses the sequence of letters in each word of your chosen paragraph from Exercise 3.
 For instance, "To be or not to be." becomes "oT eb ro ton ot eb."
 */

public class ReverseText {
  public static void main(String args[]) {
    // The String that is to be processed with a few newlines to make the
    // output a little more readable
    String text = "Into the face of the young man who sat on the terrace " +
                  "\nof the Hotel Magnifique at Cannes crept a look of furtive " +
                  "\nshame, the shifty, hangdog look which announces that " +
                  "\nan Englishman is about to talk French." ;

    boolean isWord = false;                                            // Indicates start of a word found
    int start = 0;                                                     // Index of word start
    StringBuffer reversedText = new StringBuffer();
    StringBuffer word = new StringBuffer();
    for(int i = 0 ; i  <text.length() ; ++i) {
      if(!isWord) {                                                    // If we are not in a word...
        if(Character.isLetter(text.charAt(i))) {                       // look for first letter of a word.
          word.append(text.charAt(i));                                 // and append it to word
          isWord = true;                                               // Flag that we are in a word
        } else {
          reversedText.append(text.charAt(i));
        }
      } else {                                                         // We are in a word
        if(Character.isLetter(text.charAt(i)) || text.charAt(i) == '\'') {
          word.append(text.charAt(i));                                 // Append to word
          continue;
        }
        else {                                                         // It is the end of the word
          reversedText.append(word.reverse());                         // so append the reversed word
          reversedText.append(text.charAt(i));                         // Don't forget the current character
          word.delete(0,word.length());                                // Delete the contents of word
          isWord = false;                                              // and reset word indicator
        }
      }
    }
    if(word.length() > 0)                                              // Is there a last word?
      reversedText.append(word.reverse());                             // If so append it - reversed of course.



    // Display the Reversed text
    System.out.println(reversedText);
  }
}

