// Chapter 8 Exercise 1
/*
 Use a StreamTokenizer object to parse a string entered from the keyboard containing
 a series of data items separated by commas and output each of the items on a separate line.
 */

 /*
  If you don't make '\n' a whitespace character,
  a comma will be necessary at the end of the last token.
  End-of-line terminates the input so only a single line is processed.

  Here is some sample input and output:

  red  , "red and yellow", red and yellow, 123, 123.456, 1.2E10
    red
    "red and yellow"
    red and yellow
    123
    123.456
    1.2E10

As you see, this tokenizer setup processes anything as tokens.
  */

import java.io.StreamTokenizer;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class ParseString {
  public static void main(String[] args) {
    char separator = ',';
    StreamTokenizer tokenizer = new StreamTokenizer(new InputStreamReader(System.in));
    tokenizer.resetSyntax();

    // Characters other than comma and special characters are word characters
    tokenizer.wordChars('\u0000', (char)(separator - 1));                 // Everything is a word character
    tokenizer.wordChars((char)(separator + 1), '\u00ff');                 // except for the separator
    tokenizer.whitespaceChars('\n', '\n');                                // Make end-of-line whitespace(and therefore a word delimiter)
    tokenizer.whitespaceChars(separator, separator);                      // Delimiter separates words
    tokenizer.eolIsSignificant(true);                                     // End-of-line to be reported as TT_EOL

    int type = 0;                                                         // Stores the value returned by nextToken()
    String token = null;                                                  // Will store the tokens that we find
    try {
      while((type = tokenizer.nextToken()) != StreamTokenizer.TT_EOL) {   // As long as we don't have EOL
        if(type == StreamTokenizer.TT_WORD) {                             // Check for a word
          System.out.println("  "  + tokenizer.sval.trim());              // and output it
        } else {
          assert false;                                                   // We only expect words
        }
      }
    } catch(IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
  }
}