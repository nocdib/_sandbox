import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.MouseInputAdapter;
import static Constants.SketcherConstants.*;
import java.awt.geom.Line2D;

@SuppressWarnings("serial")
public class SketcherView extends JComponent implements Observer {
  public SketcherView(Sketcher theApp) {
    this.theApp = theApp;
    MouseHandler handler = new MouseHandler();                          // create the mouse listener
    addMouseListener(handler);                                          // Listen for button events
    addMouseMotionListener(handler);                                    // Listen for motion events

    // Add the element pop-up menu items
    JMenuItem moveItem = elementPopup.add(new JMenuItem("Move"));
    JMenuItem deleteItem = elementPopup.add(new JMenuItem("Delete"));
    JMenuItem rotateItem = elementPopup.add(new JMenuItem("Rotate"));
    JMenuItem sendToBackItem = elementPopup.add(new JMenuItem("Send-to-back"));
    JMenuItem infoItem = elementPopup.add(new JMenuItem("Element Info")); // Element info menu item

    // Create the menu item listeners
    moveItem.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        mode = MOVE;
        selectedElement = highlightElement;
      }
    });
    deleteItem.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        deleteElement();
      }
    });
    rotateItem.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        mode = ROTATE;
        selectedElement = highlightElement;
      }
    });
    sendToBackItem.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        sendToBack();                                                   // Handle the send-to-back event
      }
    });

    infoItem.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        if(highlightElement != null) {
          JOptionPane.showMessageDialog(null, highlightElement.getInfo());
        }
      }
    });

    textEditItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e){
        if(highlightElement != null && highlightElement instanceof Element.Text) {
          editTextElement();
        }
      }
    });

  }

  // Edit text in a text element
  private void editTextElement() {
    String newText = JOptionPane.showInputDialog("Edit the current element text:", ((Element.Text)highlightElement).getText());

    if(newText != null && newText.length()!=0)  {                      // Do we have new text?
      // If so, update the element
      // You must repaint the area occupied by the larger of the new text and the old.
      // If you don't, old text will be left on the screen when you deleter some of the current text.
      Element.Text element = (Element.Text)highlightElement;
      java.awt.Rectangle bounds = element.getBounds();                 // Get the original bounding rectangle
      element.setText(newText);                                        // Set the new text
      repaint(bounds.union(element.getBounds()));                      // Repaint the larger of the bounding rectangles
    }
  }

  // Send-to Back operation
  private void sendToBack() {
    if(highlightElement != null) {
      SketcherModel sketch = theApp.getModel();
      if(sketch.remove(highlightElement)) {
        sketch.add(highlightElement);
      } else {
        JOptionPane.showMessageDialog(
                        SketcherView.this,"Element not found to remove.",
                        "Remove Element from Sketch", JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  // Delete element operation
  private void deleteElement() {
    if(highlightElement != null) {
      if(!theApp.getModel().remove(highlightElement)) {
        JOptionPane.showMessageDialog(
                        SketcherView.this,"Element not found to remove.",
                        "Remove Element from Sketch", JOptionPane.ERROR_MESSAGE);
      }
      highlightElement = null;
    }
  }

  // Method called by Observable object when it changes
  public void update(Observable o, Object rectangle) {
    if(rectangle != null) {
      repaint((java.awt.Rectangle)rectangle);
    } else {
      repaint();
    }
  }

  // Method to draw on the view
  @Override
  public void paint(Graphics g) {
    Graphics2D g2D = (Graphics2D)g;                                     // Get a 2D device context
    for(Element element: theApp.getModel()) {                           // For each element in the model
      element.draw(g2D);                                                // ...draw the element
    }
  }

  class MouseHandler extends MouseInputAdapter {
    @Override
    public void mousePressed(MouseEvent e)  {
      start = e.getPoint();                                             // Save the cursor position in start
      buttonState = e.getButton();                                      // Record which button was pressed

      if(showContextMenu(e)) {
        start = null;
        buttonState = MouseEvent.NOBUTTON;
        return;
      }

      if(theApp.getWindow().getElementType() == TEXT && mode == NORMAL) return;

      // Initialize rotation angles when mode is ROTATE
      if(mode == ROTATE && selectedElement != null) {
        oldAngle = selectedElement.getRotation();
        angle = 0.0;
      }

      if(buttonState == MouseEvent.BUTTON1 && mode == NORMAL) {
        g2D = (Graphics2D)getGraphics();                                // Get graphics context
        g2D.setXORMode(getBackground());                                // Set XOR mode
      }
    }


    // Show the context menu when an element is highlighted
    private boolean showContextMenu(MouseEvent e) {
      if(e.isPopupTrigger()){
        if(last != null) {                                              // If mouse was dragged
          start = last;                                                 // show popup at last cursor position
        }
        if(highlightElement == null) {                                  // If there is no highlighted element
          // Show the  popup menu from the app window
          theApp.getWindow().getPopup().show(SketcherView.this, start.x, start.y);
        } else {                                                        // Otherwise...
          if(highlightElement instanceof Element.Text) {
            System.out.println("Show text popup");
            // Append the additional menu item for editing text to the popup
            elementPopup.add(textEditItem);                             // Add the text-specific menu item...
            elementPopup.show(SketcherView.this, start.x, start.y);     // ...display the popup.
          }  else {
            // Show the standard element operations context menu
            elementPopup.remove(textEditItem);                          // Remove the textEditItem if it is there
            elementPopup.show(SketcherView.this, start.x, start.y);
          }
        }
        return true;
      }
      return false;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
      last = e.getPoint();                                              // Save cursor position
      if(theApp.getWindow().getElementType() == TEXT && mode == NORMAL) return;

      // Select operation based on sketching mode
      switch(mode){
        case NORMAL:
          // Creating an element
          if(buttonState == MouseEvent.BUTTON1) {
            if(tempElement == null) {                                   // Is there an element?
              tempElement = Element.createElement(                      // No, so create one
                                     theApp.getWindow().getElementType(),
                                     theApp.getWindow().getElementColor(),
                                     start, last);
            } else {
              tempElement.draw(g2D);                                    // Yes – draw to erase it
              tempElement.modify(start, last);                          // Now modify it
            }
            tempElement.draw(g2D);                                      // and draw it
         }
         break;
       case MOVE:
         // Moving an element
         if(buttonState == MouseEvent.BUTTON1 && selectedElement != null) {
            selectedElement.move(last.x-start.x, last.y-start.y);       // Move it
            repaint();
            start = last;                                               // Make start current point
         }
         break;
       case ROTATE:
         // Rotating an element
         if(buttonState == MouseEvent.BUTTON1 && selectedElement != null) {
            angle += getAngle(selectedElement.getPosition(), start, last);
            if(angle != 0.0) {                                          // If there is rotation
              selectedElement.setRotation(oldAngle + angle);            // Rotate the element
              repaint();                                                // Repaint the view
              start = last;                                             // last is start next time
            }
         }
         break;
      }
    }

    // Helper method for calculating the rotation angle
    double getAngle(Point position, Point start, Point last) {
      // Get perpendicular distance from last to line from position to start
      double perp = Line2D.ptLineDist(position.x, position.y,
                                      last.x, last.y, start.x, start.y);

      // Get the distance from position to start
      double hypotenuse = position.distance(start);

      if(perp < 1.0 || hypotenuse < 1.0) return 0.0;                    // Ensure sensible values

      // Angle is the arc sine of perp/hypotenuse. Clockwise is positive angle
      return -Line2D.relativeCCW(position.x, position.y, start.x, start.y,
                                 last.x, last.y)*Math.asin(perp/hypotenuse);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
      if(mode == MOVE || mode == ROTATE) {
        selectedElement = null;
        start = last = null;
        mode = NORMAL;
        return;
      }

      if(showContextMenu(e)) {
        start = last = null;
        return;
      }

      // Check for TEXT being the element type
      if(theApp.getWindow().getElementType() == TEXT && mode == NORMAL) {
        if(last != null) {
          start = last = null;
        }
        return;
      }

      if(e.getButton() == MouseEvent.BUTTON1) {
        buttonState = MouseEvent.NOBUTTON;                              // Reset the button state

        if(tempElement != null) {                                       // If there is an element...
          theApp.getModel().add(tempElement);                           // ...add it to the model...
          tempElement = null;                                           // ...and reset the field
        }
        if(g2D != null) {                                               // If there’s a graphics context
          g2D.dispose();                                                // ...release the resource...
          g2D = null;                                                   // ...and reset field to null
        }
        start = last = null;                                            // Remove any points
        mode = NORMAL;                                                  // Always normal after mouse released
      }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
      // Only if it's TEXT and button 1 was clicked
      if(theApp.getWindow().getElementType() == TEXT &&
                                         buttonState == MouseEvent.BUTTON1) {
        String text = JOptionPane.showInputDialog(
                            theApp.getWindow(),"Enter Input:",
                            "Create Text Element", JOptionPane.PLAIN_MESSAGE);

        if(text != null && !text.isEmpty()) {                // Only if text was entered
          g2D = (Graphics2D)getGraphics();
          tempElement = new Element.Text(text,
                                         start,
                                         theApp.getWindow().getElementColor(),
                                         g2D.getFontMetrics(theApp.getWindow().getFont()));
          g2D.dispose();
          g2D = null;
          if(tempElement != null) {
            theApp.getModel().add(tempElement);
          }
        }
        tempElement = null;                                             // Reset for next element creation
        start = null;                                                   // Reset for next element
      }
    }

    // Handle mouse move events
    @Override
    public void mouseMoved(MouseEvent e) {
      Point cursor = e.getPoint();                                      // Get current cursor position

      for(Element element : theApp.getModel())  {                       // Go through the list...
        if(element.getBounds().contains(cursor)) {                      // ....under the cursor
          if(element==highlightElement) {                               // If it's already highlighted
            return;                                                     // we are done
          }

          // Un-highlight any existing highlighted element
          if(highlightElement!= null) {                                 // If an element is highlighted
            highlightElement.setHighlighted(false);                     // un-highlight it and
            repaint(highlightElement.getBounds());                      //... redraw it
          }

          element.setHighlighted(true);                                 // Set highlight for new element
          highlightElement = element;                                   // Store new highlighted element
            repaint(highlightElement.getBounds());
          return;
        }
      }

      // Here there is no element under the cursor so...
      if(highlightElement!=null)   {                                    // If an element is highlighted...
        highlightElement.setHighlighted(false);                         // ...turn off highlighting...
        repaint(highlightElement.getBounds());                          // ... and redraw the element
        highlightElement = null;                                        // No element highlighted
      }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
      setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    }

    @Override
    public void mouseExited(MouseEvent e) {
      setCursor(Cursor.getDefaultCursor());
    }

    private Point start;                                                // Stores cursor position on press
    private Point last;                                                 // Stores cursor position on drag
    private Element tempElement = null;                                 // Stores a temporary element
    private int buttonState = MouseEvent.NOBUTTON;                      // Records button state
    private Graphics2D g2D = null;                                      // Temporary graphics context
   }

  private Sketcher theApp;                                              // The application object
  private Element highlightElement;                                     // Highlighted element
  private Element selectedElement;                                      // Element for move or rotate operation
  private double oldAngle = 0.0;                                        // Initial element rotation
  private double angle = 0.0;                                           // Additional rotation

  // Element operations context menu
  private JPopupMenu elementPopup = new JPopupMenu("Element Operations");

  // Text element popup menu item
  private JMenuItem textEditItem = new JMenuItem("Edit Text");

  private String mode = NORMAL;                                         // Sketching mode
}
