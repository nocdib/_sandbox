// Chapter 22 Exercise 1
/*
 Write a program using SAX that counts the number of occurrences of each element type in an XML document
 and displays them. The document file to be processed should be identified by the first command-line argument.
 */

 /*
  The inner ElementCounter class defines a SAX handler that counts element. For each
  type of element and ElementRecord object is created that records the count for that type.
  ElementRecord objects are stored in the elementRecords field of the ElementCounter object.
  The ElementRecord class is an inner class to the ElementCounter class.

  Note that the sample XML file, Boat.xml, has a DOCTYPE that implies
  that the dtd is in the same directory as this file. When you execute this,
  the .class file, the XML file and the DTD should all be in the same directory.

*/
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.Vector;

public class CountXMLElementsUsingSAX {
  public static void main(String args[]) {
    if(args.length == 0) {
      System.out.println("No file to process. Usage is:" + "\njava CountXMLElements \"filename\" ");
      return;
    } else if(!Files.exists(Paths.get(args[0]))) {
      System.out.println("File " + args[0] + "does not exist.");
      return;
    }

    // We have a file so it's go for parsing...
    File file = new File(args[0]);
    SAXParserFactory spf = SAXParserFactory.newInstance();
    SAXParser parser = null;
    spf.setNamespaceAware(true);
    spf.setValidating(true);

    try {
     parser = spf.newSAXParser();
     System.out.println("Parser object is: " + parser);
    } catch(SAXException | ParserConfigurationException e) {
        e.printStackTrace();
        System.exit(1);
    }

    System.out.println("\nStarting processing of "+file+"\n");
    ElementCounter elementCounter = new ElementCounter();
    try {
       parser.parse(file, elementCounter);
    } catch(IOException | SAXException e) {
        e.printStackTrace();
    }
    elementCounter.listElementCounts();
  }

  // Defines a SAX handler to count elements
  static class ElementCounter extends DefaultHandler {
    public void startDocument() {
      System.out.println("Starting document processing... ");
    }

    public void endDocument()  {
      System.out.println("Document processing finished. ");
    }

    public void startElement(String uri, String localName, String qname, Attributes attr) {
      ElementRecord element = findElement(localName);
      if(element == null) {
        elementRecords.add(new ElementRecord(localName));
      } else {
        element.increment();
      }
    }

    private ElementRecord findElement(String name) {
      for(ElementRecord element : elementRecords) {
        if(name.equals(element.getName())) {
          return element;
        }
      }
      return null;
    }

    public void listElementCounts() {
      for(ElementRecord element : elementRecords) {
        System.out.println(element);
      }
    }

    public void ignorableWhitespace(char[] ch, int start, int length) {
      // Uncomment below to see that this is happening
      // System.out.println("Ignoring ignorable whitespace... ");
    }

    // Defines an object recording the count for an element
    class ElementRecord {
      public ElementRecord(String name) {
        this.name = name;
      }

      public void increment() {
        ++count;
      }

      public int getCount() {
        return count;
      }

      public String getName() {
        return name;
      }

      public String toString() {
        return name + " " + count;
      }

      private String name = null;
      private int count = 1;
    }

    private Vector<ElementRecord> elementRecords = new Vector<>();
  }
}