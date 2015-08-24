import java.awt.*;
import java.io.Serializable;
import static Constants.SketcherConstants.*;
import java.awt.geom.*;
import org.w3c.dom.Document;
import org.w3c.dom.Attr;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;

public abstract class Element implements Serializable{

  public Element(Point position, Color color) {
    this.position = new Point(position);
    this.color = color;
  }

  protected Element(Color color) {
    this.color = color;
  }

  protected Element(){}

  // Returns the color of the element
  public Color getColor() {
    return color;
  }

  // Returns the position of the element
  public Point getPosition() {
    return position;
  }

  // Set angle field value from a node
  protected void setAngleFromXML(Node node) {
    angle = Double.valueOf(((Attr)(node.getAttributes().getNamedItem("angle"))).getValue());
  }

  // Set position field from a node
  protected void setPositionFromXML(Node node) {
    NamedNodeMap attrs = node.getAttributes();
    position = new Point(Integer.valueOf(((Attr)(attrs.getNamedItem("x"))).getValue()),
                         Integer.valueOf(((Attr)(attrs.getNamedItem("y"))).getValue()));
  }

  // Set color field from a node
  protected void setColorFromXML(Node node) {
    NamedNodeMap attrs = node.getAttributes();
    color = new Color(Integer.valueOf(((Attr)(attrs.getNamedItem("R"))).getValue()),
                      Integer.valueOf(((Attr)(attrs.getNamedItem("G"))).getValue()),
                      Integer.valueOf(((Attr)(attrs.getNamedItem("B"))).getValue()));
  }

  // Set bounds field from a node
  protected void setBoundsFromXML(Node node) {
    NamedNodeMap attrs = node.getAttributes();
    bounds = new java.awt.Rectangle(Integer.valueOf(((Attr)(attrs.getNamedItem("x"))).getValue()),
                                    Integer.valueOf(((Attr)(attrs.getNamedItem("y"))).getValue()),
                                    Integer.valueOf(((Attr)(attrs.getNamedItem("width"))).getValue()),
                                    Integer.valueOf(((Attr)(attrs.getNamedItem("height"))).getValue()));
  }

  // Initialize a base class field from a Node
  protected boolean setBaseFieldsFromXML(Node aNode) {
    switch(aNode.getNodeName()) {
      case "position":
        setPositionFromXML(aNode);
        return true;
      case "color":
        setColorFromXML(aNode);
        return true;
      case "bounds":
        setBoundsFromXML(aNode);
        return true;
    }
    return false;
  }
  // Returns the bounding rectangle enclosing an element boundary
  public java.awt.Rectangle getBounds() {
    AffineTransform at = AffineTransform.getTranslateInstance(position.x, position.y);
    at.rotate(angle);
    at.translate(-position.x, -position.y);
    return  at.createTransformedShape(bounds).getBounds();
  }

  // Set or reset highlight color
  public void setHighlighted(boolean highlighted) {
    this.highlighted = highlighted;
  }

  // Create a new element
  public static Element createElement(int type, Color color, Point start, Point end) {
    switch(type) {
      case LINE:
        return new Element.Line(start, end, color);
      case RECTANGLE:
         return new Rectangle(start, end, color);
      case CIRCLE:
        return new Circle(start, end, color);
      case CURVE:
        return new Curve(start, end, color);
      default:
       assert false;                                                   // We should never get to here
    }
    return null;
  }

  // Draw a geometric element of type Shape
  protected void draw(Graphics2D g2D, Shape element) {
    g2D.setPaint(highlighted ? Color.MAGENTA : color);                 // Set the element color
    AffineTransform old = g2D.getTransform();                          // Save copy of current transform
    g2D.translate((double)position.x, (double)position.y);             // Add a translation to current transform
    g2D.rotate(angle);                                                 // Rotate about position
     g2D.draw(element);                                                // Draw the element
    g2D.setTransform(old);                                             // Restore original transform
  }

  // Move an element
  public void move(int deltaX, int deltaY) {
    position.translate(deltaX, deltaY);
    bounds.translate(deltaX, deltaY);
  }

  // Rotate an element
  public void setRotation(double angle) {
    this.angle = angle;
  }

  // Get the rotation angle
  public double getRotation() {
    return angle;
  }

  // Create an XML element for color
  protected org.w3c.dom.Element createColorElement(Document doc) {
    org.w3c.dom.Element colorElement = doc.createElement("color");

    Attr attr = doc.createAttribute("R");
    attr.setValue(String.valueOf(color.getRed()));
    colorElement.setAttributeNode(attr);

    attr = doc.createAttribute("G");
    attr.setValue(String.valueOf(color.getGreen()));
    colorElement.setAttributeNode(attr);

    attr = doc.createAttribute("B");
    attr.setValue(String.valueOf(color.getBlue()));
    colorElement.setAttributeNode(attr);
    return colorElement;
  }

  // Create an XML element representing a point
  protected org.w3c.dom.Element createPointTypeElement(Document doc,
                                                       String name,
                                                       String xValue,
                                                       String yValue) {
    org.w3c.dom.Element element = doc.createElement(name);

    Attr attr = doc.createAttribute("x");                              // Create attribute x
    attr.setValue(xValue);                                             // and set its value
    element.setAttributeNode(attr);                                    // Insert the x attribute

    attr = doc.createAttribute("y");                                   // Create attribute y
    attr.setValue(yValue);                                             // and set its value
    element.setAttributeNode(attr);                                    // Insert the y attribute
    return element;
  }

  // Create the XML element for the position of a sketch element
  protected org.w3c.dom.Element createPositionElement(Document doc) {
    return createPointTypeElement(doc, "position",
                                  String.valueOf(position.x),
                                  String.valueOf(position.y));
  }

  // Create XML element for a bounding rectangle
  protected org.w3c.dom.Element createBoundsElement(Document doc) {
    org.w3c.dom.Element boundsElement = doc.createElement("bounds");

    Attr attr = doc.createAttribute("x");
    attr.setValue(String.valueOf(bounds.x));
    boundsElement.setAttributeNode(attr);

    attr = doc.createAttribute("y");
    attr.setValue(String.valueOf(bounds.y));
    boundsElement.setAttributeNode(attr);

    attr = doc.createAttribute("width");
    attr.setValue(String.valueOf(bounds.width));
    boundsElement.setAttributeNode(attr);

    attr = doc.createAttribute("height");
    attr.setValue(String.valueOf(bounds.height));
    boundsElement.setAttributeNode(attr);
    return boundsElement;
  }

  // Nested class defining a line
  public static class Line extends Element {
    public Line(Point start, Point end, Color color) {
      super(start, color);
      line = new Line2D.Double(origin.x, origin.y, end.x - position.x, end.y - position.y);
      bounds = new java.awt.Rectangle(
                     Math.min(start.x ,end.x),    Math.min(start.y, end.y),
                     Math.abs(start.x - end.x)+1, Math.abs(start.y - end.y)+1);
    }

    // Change the end point for the line
    public void modify(Point start, Point last) {
      line.x2 = last.x - position.x;
      line.y2 = last.y - position.y;
      bounds = new java.awt.Rectangle(
                   Math.min(start.x ,last.x),    Math.min(start.y, last.y),
                   Math.abs(start.x - last.x)+1, Math.abs(start.y - last.y)+1);
    }

    // Create Line object from XML node
    public Line(Node node) {
      setAngleFromXML(node);
      NodeList childNodes = node.getChildNodes();
      Node aNode = null;
      for(int i = 0 ; i<childNodes.getLength() ; ++i) {
        aNode = childNodes.item(i);
        switch(aNode.getNodeName()) {
          case "position":
            setPositionFromXML(aNode);
            break;
          case "color":
            setColorFromXML(aNode);
            break;
          case "bounds":
            setBoundsFromXML(aNode);
            break;
          case "endpoint":
            NamedNodeMap coords = aNode.getAttributes();
            line = new Line2D.Double();
            line.x2 =
                 Double.valueOf(((Attr)(coords.getNamedItem("x"))).getValue());
            line.y2 =
                 Double.valueOf(((Attr)(coords.getNamedItem("y"))).getValue());
            break;
          default:
            System.err.println("Invalid node in <line>: " + aNode);
            break;
        }
      }
    }

    // Display the line
    public  void draw(Graphics2D g2D) {
      draw(g2D, line);                                                 // Draw the line
    }

    // Create XML element for a line
    public void addElementNode(Document doc) {
      org.w3c.dom.Element lineElement = doc.createElement("line");

      // Create the angle attribute and attach it to the <line> node
      Attr attr = doc.createAttribute("angle");
      attr.setValue(String.valueOf(angle));
      lineElement.setAttributeNode(attr);

      // Append the <color>, <position>, and <endpoint> nodes as children
      lineElement.appendChild(createColorElement(doc));
      lineElement.appendChild(createPositionElement(doc));
      lineElement.appendChild(createBoundsElement(doc));
      lineElement.appendChild(createEndpointElement(doc));

      // Append the <line> node to the document root node
      doc.getDocumentElement().appendChild(lineElement);
    }

    // Create XML element for the end point of a line
    private org.w3c.dom.Element createEndpointElement(Document doc) {
      return createPointTypeElement(doc, "endpoint", String.valueOf(line.x2), String.valueOf(line.y2));
    }

    private Line2D.Double line;
    private final static long serialVersionUID = 1001L;
  }

  // Nested class defining a rectangle
  public static class Rectangle extends Element {
    public Rectangle(Point start, Point end, Color color) {
      super(new Point(Math.min(start.x, end.x), Math.min(start.y, end.y)), color);
      rectangle = new Rectangle2D.Double(
        origin.x, origin.y,                                            // Top-left corner
        Math.abs(start.x - end.x), Math.abs(start.y - end.y));         // Width & height
      bounds = new java.awt.Rectangle(
                    Math.min(start.x ,end.x),    Math.min(start.y, end.y),
                    Math.abs(start.x - end.x)+1, Math.abs(start.y - end.y)+1);
    }

    // Create Rectangle object from XML node
    public Rectangle(Node node) {
      setAngleFromXML(node);
      NodeList childNodes = node.getChildNodes();
      Node aNode = null;
      for(int i = 0 ; i<childNodes.getLength() ; ++i) {
        aNode = childNodes.item(i);
        switch(aNode.getNodeName()) {
          case "position":
            setPositionFromXML(aNode);
            break;
          case "color":
            setColorFromXML(aNode);
            break;
          case "bounds":
            setBoundsFromXML(aNode);
            break;
          default:
            System.err.println("Invalid node in <rectangle>: " + aNode);
            break;
        }
      }
      NamedNodeMap attrs = node.getAttributes();
      rectangle = new Rectangle2D.Double();
      rectangle.width = Double.valueOf(((Attr)(attrs.getNamedItem("width"))).getValue());
      rectangle.height = Double.valueOf(((Attr)(attrs.getNamedItem("height"))).getValue());
    }

    // Display the rectangle
    public  void draw(Graphics2D g2D) {
      draw(g2D, rectangle);                                            // Draw the rectangle
    }

    // Method to redefine the rectangle
    public void modify(Point start, Point last) {
      bounds.x = position.x = Math.min(start.x, last.x);
      bounds.y = position.y = Math.min(start.y, last.y);
      rectangle.width = Math.abs(start.x - last.x);
      rectangle.height = Math.abs(start.y - last.y);
      bounds.width = (int)rectangle.width + 1;
      bounds.height = (int)rectangle.height + 1;
    }

    // Create an XML element for a rectangle
    public void addElementNode(Document doc) {
      org.w3c.dom.Element rectElement = doc.createElement("rectangle");

      // Create the width & height attributes and attach them to the node
      Attr attr = doc.createAttribute("width");
      attr.setValue(String.valueOf(rectangle.width));
      rectElement.setAttributeNode(attr);
      attr = doc.createAttribute("height");
      attr.setValue(String.valueOf(rectangle.height));
      rectElement.setAttributeNode(attr);

      // Create the angle attribute and attach it to the <rectangle> node
      attr = doc.createAttribute("angle");
      attr.setValue(String.valueOf(angle));
      rectElement.setAttributeNode(attr);

      // Append the <color>, <position>, and <bounds> nodes as children
      rectElement.appendChild(createColorElement(doc));
      rectElement.appendChild(createPositionElement(doc));
      rectElement.appendChild(createBoundsElement(doc));

      doc.getDocumentElement().appendChild(rectElement);
    }

    private Rectangle2D.Double rectangle;
    private final static long serialVersionUID = 1001L;
  }

  // Nested class defining a circle
  public static class Circle extends Element {
    public Circle(Point center, Point circum, Color color) {
      super(color);

      // Radius is distance from center to circumference
      double radius = center.distance(circum);
      position = new Point(center.x - (int)radius, center.y - (int)radius);
      circle = new Ellipse2D.Double(origin.x, origin.y, 2.*radius, 2.*radius);
      bounds = new java.awt.Rectangle(position.x, position.y,
                              1 + (int)circle.width, 1 + (int)circle.height);
    }

    // Create Circle object from XML node
    public Circle(Node node) {
      setAngleFromXML(node);
      NodeList childNodes = node.getChildNodes();
      Node aNode = null;
      for(int i = 0 ; i<childNodes.getLength() ; ++i) {
        aNode = childNodes.item(i);
        switch(aNode.getNodeName()) {
          case "position":
            setPositionFromXML(aNode);
            break;
          case "color":
            setColorFromXML(aNode);
            break;
          case "bounds":
            setBoundsFromXML(aNode);
            break;
          default:
            System.err.println("Invalid node in <circle>: " + aNode);
            break;
        }
      }
      NamedNodeMap attrs = node.getAttributes();
      circle = new Ellipse2D.Double();
      circle.width = circle.height = Double.valueOf(((Attr)(attrs.getNamedItem("diameter"))).getValue());
    }

    // Display the circle
    public  void draw(Graphics2D g2D) {
      draw(g2D, circle);                                               // Draw the circle
    }

    // Recreate this circle
    public void modify(Point center, Point circum) {
      double radius = center.distance(circum);
      circle.width = circle.height = 2*radius;
      position.x = center.x - (int)radius;
      position.y = center.y - (int)radius;
      bounds = new java.awt.Rectangle(position.x, position.y,
                              1 + (int)circle.width, 1 + (int)circle.height);
    }

    // Create an XML element for a circle
    public void addElementNode(Document doc) {
      org.w3c.dom.Element circleElement = doc.createElement("circle");

      // Create the diameter attribute and attach it to the <circle> node
      Attr attr = doc.createAttribute("diameter");
      attr.setValue(String.valueOf(circle.width));
      circleElement.setAttributeNode(attr);

      // Create the angle attribute and attach it to the <circle> node
      attr = doc.createAttribute("angle");
      attr.setValue(String.valueOf(angle));
      circleElement.setAttributeNode(attr);

      // Append the <color> and <position> nodes as children
      circleElement.appendChild(createColorElement(doc));
      circleElement.appendChild(createPositionElement(doc));
      circleElement.appendChild(createBoundsElement(doc));

      doc.getDocumentElement().appendChild(circleElement);
    }

    private Ellipse2D.Double circle;
    private final static long serialVersionUID = 1001L;
  }

  // Nested class defining a curve
  public static class Curve extends Element {
    public Curve(Point start, Point next, Color color) {
      super(start, color);
      curve = new GeneralPath();
      curve.moveTo(origin.x, origin.y);                                // Set current position as origin
      curve.lineTo(next.x - position.x, next.y - position.y);          // Add segment
      bounds = new java.awt.Rectangle(
                   Math.min(start.x, next.x),    Math.min(start.y, next.y),
                   Math.abs(next.x - start.x)+1, Math.abs(next.y - start.y)+1);
    }

    // Create Curve object from XML node
    public Curve(Node node) {
      curve = new GeneralPath();
      curve.moveTo(origin.x, origin.y);                                // Set current position as origin
      setAngleFromXML(node);
      NodeList childNodes = node.getChildNodes();
      Node aNode = null;
      for(int i = 0 ; i<childNodes.getLength() ; ++i) {
        aNode = childNodes.item(i);
        switch(aNode.getNodeName()) {
          case "position":
            setPositionFromXML(aNode);
            break;
          case "color":
            setColorFromXML(aNode);
            break;
          case "bounds":
            setBoundsFromXML(aNode);
            break;
          case "point":
            NamedNodeMap attrs = aNode.getAttributes();
            curve.lineTo(Double.valueOf(((Attr)(attrs.getNamedItem("x"))).getValue()),
                         Double.valueOf(((Attr)(attrs.getNamedItem("y"))).getValue()));

          break;
          default:
            System.err.println("Invalid node in <curve>: " + aNode);
            break;
        }
      }
    }

    // Add another segment
    public void modify(Point start, Point next) {
      curve.lineTo(next.x - position.x, next.y - position.y);          // Add segment
      bounds.add(new java.awt.Rectangle(next.x,next.y, 1, 1));         // Extend bounds
    }

    // Display the curve
    public  void draw(Graphics2D g2D) {
      draw(g2D, curve);                                                // Draw the curve
    }

    // Create an XML element for a curve
    public void addElementNode(Document doc) {
      org.w3c.dom.Element curveElement = doc.createElement("curve");

      // Create the angle attribute and attach it to the <curve> node
      Attr attr = doc.createAttribute("angle");
      attr.setValue(String.valueOf(angle));
      curveElement.setAttributeNode(attr);

      // Append the <color> and <position> nodes as children
      curveElement.appendChild(createColorElement(doc));
      curveElement.appendChild(createPositionElement(doc));
      curveElement.appendChild(createBoundsElement(doc));

      // Get the defining points via a path iterator
      PathIterator iterator = curve.getPathIterator(new AffineTransform());
      int maxCoordCount = 6;                                           // Maximum coordinates for a segment
      float[] temp = new float[maxCoordCount];                         // Stores segment data

      int result = iterator.currentSegment(temp);                      // Get first segment
      assert result == PathIterator.SEG_MOVETO;                        // ... should be move to

      iterator.next();                                                 // Next segment
      while(!iterator.isDone())   {                                    // While you have segments
        result = iterator.currentSegment(temp);                        // Get the segment data
        assert result == PathIterator.SEG_LINETO;                      // Should all be lines

        // Create a <point> node and add it to the list of children
        curveElement.appendChild(createPointTypeElement(doc, "point",
                                      String.valueOf(temp[0]),
                                      String.valueOf(temp[1])));
        iterator.next();                                               // Go to next segment
      }
      doc.getDocumentElement().appendChild(curveElement);
    }

    private GeneralPath curve;
    private final static long serialVersionUID = 1001L;
  }

  // Nested class defining a Text element
  public static class Text extends Element  {
    public Text(String text, Point start, Color color, FontMetrics fm) {
      super(start, color);
      this.text = text;
      this.font = fm.getFont();
        maxAscent = fm.getMaxAscent();
        bounds = new java.awt.Rectangle(position.x, position.y,
                 fm.stringWidth(text) + 4, maxAscent+ fm.getMaxDescent() + 4);
    }

    // Create element from XML data from SAX
    public Text(String text, Point start, Color color, java.awt.Rectangle bounds, Font font, int maxAscent) {
      super(start, color);
      this.text = text;
      this.font = font;
      this.maxAscent = maxAscent;
      this.bounds = bounds;
    }

    // Create Text object from XML node
    public Text(Node node) {
      NamedNodeMap attrs = node.getAttributes();
      angle = Double.valueOf(((Attr)(attrs.getNamedItem("angle"))).getValue());
      maxAscent = Integer.valueOf(((Attr)(attrs.getNamedItem("maxascent"))).getValue());
      NodeList childNodes = node.getChildNodes();
      Node aNode = null;
      for(int i = 0 ; i<childNodes.getLength() ; ++i) {
        aNode = childNodes.item(i);
        switch(aNode.getNodeName()) {
          case "position":
            setPositionFromXML(aNode);
            break;
          case "color":
            setColorFromXML(aNode);
            break;
          case "bounds":
            setBoundsFromXML(aNode);
            break;
          case "font":
            setFontFromXML(aNode);
          break;
          case "string":
            text = aNode.getTextContent();
            break;
          default:
            System.err.println("Invalid node in <text>: " + aNode);
            break;
        }
      }
    }

    // Set the font field from an XML node
    private void setFontFromXML(Node node) {
      NamedNodeMap attrs = node.getAttributes();
      String fontName = ((Attr)(attrs.getNamedItem("fontname"))).getValue();
      String style = ((Attr)(attrs.getNamedItem("fontstyle"))).getValue();
      int fontStyle = 0;
      switch(style){
        case "plain":
          fontStyle = Font.PLAIN;
          break;
        case "bold":
          fontStyle = Font.BOLD;
          break;
        case "italic":
          fontStyle = Font.ITALIC;
          break;
        case "bold-italic":
          fontStyle = Font.ITALIC|Font.BOLD;
          break;
        default:
          System.err.println("Invalid font style code: " + style);
          break;
      }
      int pointSize = Integer.valueOf(((Attr)(attrs.getNamedItem("pointsize"))).getValue());
      font = new Font(fontName, fontStyle, pointSize);
    }

    public void draw(Graphics2D g2D)  {
      g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);
      Font oldFont = g2D.getFont();                                    // Save the old font
      g2D.setFont(font);                                               // Set the new font

      AffineTransform old = g2D.getTransform();                        // Save the current transform
      g2D.translate((double)position.x, (double)position.y);           // Add translation transform to current
      g2D.rotate(angle);                                               // Rotate about position
      // Reference point for drawString() is the baseline of the 1st character
      g2D.drawString(text, origin.x + 2, maxAscent + 2);
      g2D.setTransform(old);                                           // Restore original transform
      g2D.setFont(oldFont);                                            // Restore the old font
    }

    public void modify(Point start, Point last) {
      // No code is required here, but you must supply a definition
    }

    // Create an XML element for a sketch Text element
    public void addElementNode(Document doc) {
      org.w3c.dom.Element textElement = doc.createElement("text");

      // Create the angle attribute and attach it to the <text> node
      Attr attr = doc.createAttribute("angle");
      attr.setValue(String.valueOf(angle));
      textElement.setAttributeNode(attr);

      // Create the maxascent attribute and attach it to the <text> node
      attr = doc.createAttribute("maxascent");
      attr.setValue(String.valueOf(maxAscent));
      textElement.setAttributeNode(attr);

      // Append the <color> and <position> nodes as children
      textElement.appendChild(createColorElement(doc));
      textElement.appendChild(createPositionElement(doc));
      textElement.appendChild(createBoundsElement(doc));

      // Create and apppend the <font> node
      org.w3c.dom.Element fontElement = doc.createElement("font");
      attr = doc.createAttribute("fontname");
      attr.setValue(font.getName());
      fontElement.setAttributeNode(attr);

      attr = doc.createAttribute("fontstyle");
      String style = null;
      int styleCode = font.getStyle();
      if(styleCode == Font.PLAIN) {
        style = "plain";
      } else if(styleCode == Font.BOLD) {
        style = "bold";
      } else if(styleCode == Font.ITALIC) {
        style = "italic";
      } else if(styleCode == Font.ITALIC+Font.BOLD) {
          style = "bold-italic";
      }
      assert style != null;
      attr.setValue(style);
      fontElement.setAttributeNode(attr);

      attr = doc.createAttribute("pointsize");
      attr.setValue(String.valueOf(font.getSize()));
      fontElement.setAttributeNode(attr);
      textElement.appendChild(fontElement);

      // Create the <string> node
      org.w3c.dom.Element string = doc.createElement("string");
      string.setTextContent(text);
      textElement.appendChild(string);

      doc.getDocumentElement().appendChild(textElement);
    }

    private Font font;                                                 // The font to be used
    private int maxAscent;                                             // Maximum ascent
    private String text;                                               // Text to be displayed
    private final static long serialVersionUID = 1001L;
  }

  // Abstract Element class methods
  public abstract void draw(Graphics2D g2D);
  public abstract void modify(Point start, Point last);
  public abstract void addElementNode(Document document);

  // Element class fields
  protected Point position;                                            // Position of a shape
  protected Color color;                                               // Color of a shape
  protected java.awt.Rectangle bounds;                                 // Bounding rectangle
  protected static final Point origin = new Point();                   // Origin for elements
  protected boolean highlighted = false;                               // Highlight flag
  protected double angle = 0.0;                                        // Element rotation
  private final static long serialVersionUID = 1002L;
}
