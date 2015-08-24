// Chapter 8 Exercise 2

/*
 Create a class defining an object that parses each line of input
 from the keyboard that contains items separated by an arbitrary delimiter
 (for example, a colon, or a comma, or a forward slash, and so on)
 and return the items as an array of type String[]. For example, the input might be

1/one/2/two

The output would be returned as an array of type String[] containing "1", "one", "2", "two".
 */

/*
 The only slight difficulty over the previous exercise is that you must provide
 for an arbitrary number of tokens that you must store in an array.
 You will see later in the book there are easier ways to accommodate this.
 */

public class TestParseInput {
  public static void main(String[] args) {
    char separator = ',';
    ParseInput tokenizer = new ParseInput(separator);
    parseInputLine(tokenizer, separator);

    // Try a different separator
    separator = '/';
    tokenizer.setSeparator(separator);
    parseInputLine(tokenizer, separator);
  }

  // Reusable parse input operation
  static void parseInputLine(ParseInput tokenizer, char separator) {
    System.out.println("\nEnter items separated by " + separator + ":");
    String[] inputs = tokenizer.readTokens();

    if(inputs.length != 0) {
      System.out.println("The items that you entered are:");
      for( String input : inputs) {
        System.out.print("  " + input);
      }
    }
  }
}