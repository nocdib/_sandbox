// Chapter 20 Exercise 6

/*
 Add a Scale menu item to the element context menu that allows
 a geometric element to be scaled by dragging the mouse cursor.
 */

/*
  The obvious mechanism to use for scaling in the scaling affine transform capability.
  This requires modifying the Element class to include a scale tranform when drawing
  an element. It is important not to forget to scale the bounding rectangle too!

  To implement scaling I used the mouseDragged() method in the MouseHandler inner class
  to SketchView. I chose to implement scaling in x and y independently. You could
  implement scaling such that scaling in x and y  would always be the same and this
  would be easier, but a lot less interesting.

  To use mouse dragging for scaling you need to devise a scheme such that the
  scaling does not run out of control or result in a scale of zero. I chose not to allow
  negative scale factors and I made the minimum scale factor 0.2. To determine the scale
  factor, I divided the distance in x and y that the mouse was dragged by the width
  or height of the bounding reactangle, and added the result to the scale factors
  in x and y. This has the effect that the scaling will be the distance the mouse
  cursor is dragged in x and y.

  You can see that the line thickness is also scale by the scaling transformation. Note
  scaling only applies to geometry, not Text elements. To scale text you would need to
   modify the point size.


  SketcherView class:
   - added the scaleItem field
   - created the scale action item and added a menu item for it to the element menu
   - modified the actionPerformed() method to process action events from the
     Scale menu item.
   - added an import for Point2D
   - added a field, elementPosition, of type Point2D.Double to the MouseHandler
     class to record the position of the current element
   - modified the mousePressed() method in MouseHandler to obtain the position of
     the current element when SCALE mode is in effect.
   - modified the mouseDragged() method in MouseHandler to do the element scaling
     when SCALE mode is in effect and the element is a geometric element.
   - modified the processPopupTrigger() method in the inner MouseHandler class to
     show the Scale menu item in the popup menu as long as the element is not Text

  SketcherConstants class:
  - added the definition of the SCALE mode constamt

  Element class:
  - added scaleX and scaleY fields to record the x and y scale factors for an element.
    with a default scale of 1.0 in both x and y. This allows independent scaling
    in x and y.
  - added a scale() method that applies a scaling to the existing scale factors.
  - modified the draw() method to apply a scaling transform
  - modified the getBounds() method to apply a scaling transform.

  I did not update the getInfo() method in the element classes. You could easily amend
  these to output the scale factor. A slightly complication arises with circles. If
  the x and y scales are not the same then the circle will be an ellipse so you
  should then output the width and height.
*/


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Sketcher {
  public static void main(String[] args) {
     theApp = new Sketcher();                                           // Create the application object
   SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                          theApp.createGUI();                           // Call GUI creator
            }
        });
  }

  // Method to create the application GUI
  private void createGUI() {
    window = new SketcherFrame("Sketcher", this);                       // Create the app window
    Toolkit theKit = window.getToolkit();                               // Get the window toolkit
    Dimension wndSize = theKit.getScreenSize();                         // Get screen size

    // Set the position to screen center & size to half screen size
    window.setSize(wndSize.width/2, wndSize.height/2);                  // Set window size
    window.setLocationRelativeTo(null);                                 // Center window

    window.addWindowListener(new WindowHandler());                      // Add window listener

    sketch = new SketcherModel();                                       // Create the model
    view = new SketcherView(this);                                      // Create the view
    sketch.addObserver(view);                                           // Register view with the model
    window.getContentPane().add(view, BorderLayout.CENTER);
    window.setVisible(true);
  }

  // Return a reference to the application window
  public SketcherFrame getWindow() {
     return window;
  }

  // Return a reference to the model
  public SketcherModel getModel() {
     return sketch;
  }

  // Return a reference to the view
  public SketcherView getView() {
     return view;
  }

  // Handler class for window events
  class WindowHandler extends WindowAdapter {
    // Handler for window closing event
    @Override
    public void windowClosing(WindowEvent e) {
      // Code to be added here...
    }
  }

  private SketcherModel sketch;                                         // The data model for the sketch
  private SketcherView view;                                            // The view of the sketch
  private SketcherFrame window;                                         // The application window
  private static Sketcher theApp;                                       // The application object
}