// Chapter 8 Exercise 2

import java.io.StreamTokenizer;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class ParseInput {
  private StreamTokenizer tokenizer;
  private char separator;

  // Constructor requires the separator character as the argument
  public ParseInput(char separator){
    this.separator = separator;
    tokenizer = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
    setTokenizerState();
  }

  // Sets the tokenizer to accept anyhing except the separator and end-of-line as
  // part of a word
  private void setTokenizerState() {
    tokenizer.resetSyntax();
    tokenizer.wordChars('\u0000', (char)(separator - 1));                 // Everything is a word character
    tokenizer.wordChars((char)(separator + 1), '\u00ff');                 // except for the separator
    tokenizer.whitespaceChars('\n', '\n');                                // Make end-of-line whitespace(and therefore a word delimiter)
    tokenizer.whitespaceChars(separator, separator);                      // Delimiter passed to constructor seaparates words
    tokenizer.eolIsSignificant(true);                                     // End-of-line to be reported as TT_EOL

  }

  // Read tokens from the input
  public String[] readTokens() {
    int type = 0;                                                         // Store the value returned by nextToken()
    int strCount = 5;                                                     // String capacity increment
    String[] tokens = new String[strCount];                               // Will store the tokens that we find
    int nTokens = 0;                                                      // Number of tokens stored
    try {
      while((type = tokenizer.nextToken()) != StreamTokenizer.TT_EOL) {   // As long as we don'y have EOL
        // Verify we have space in the tokens array
        if(nTokens == tokens.length) {
          String[] newTokens = new String[tokens.length+strCount];        // tokens array is full so create a larger array

          // Copy old array contents to new array
          for(int i = 0 ; i < nTokens ; ++i) {
            newTokens[i] = tokens[i];
          }
          tokens = newTokens;                                             // Replace old array by new array
        }
        if(type == StreamTokenizer.TT_WORD) {                             // Check for a word
          tokens[nTokens++] = tokenizer.sval;                             // and save it in the vector
        } else {
          assert false;                                                   // We only expect words
        }

      }
      // Store tokens in array of exactly the right size
      if(nTokens < tokens.length) {
        String[] newTokens = new String[nTokens];
        // Copy old array contents to new array
        for(int i = 0 ; i < nTokens ; ++i) {
          newTokens[i] = tokens[i];
        }
        tokens = newTokens;                                               // Replace old array by new array
      }
    } catch(IOException e) {
      e.printStackTrace();
      System.exit(1);
    }

    return tokens;
  }

  // Convenience method to change the separator
  public void setSeparator(char separator) {
    this.separator = separator;
    setTokenizerState();
  }
}