// Chapter 20 Exercise 4

/*
 Change the implementations of the element classes to make use of
 the combined translate and rotate operation.
 */

/*
 This exercise is really to get you thinking about what these transforms - and the
 combined rotate and translate in particular - do.
 The operation:

   g2D.rotate(angle, x, y);

   is equivalent to:

   g2D.translate(x, y);   // Move to new point
   g2D.rotate(angle);     // Rotate about that point
   g2D.translate(-x, -y); // Move back to the original point

 In other words, the operation rotates the coordinate system about the point x,y
 but leaves the origin where it is.

 This is not particularly useful in our Element classes. The use of this operation in our
 element classes is to apply the rotation about the reference point, position, for an
 element, but you still must apply the translation to position before drawing the element
 because all elements are defined relative to the origin.
 The net effect therefore is to change to sequence of the rotate and translate operations.
 Since this involves two more translations than the previous code, the old code is
 better! Still, you should have a clearer idea of what this operation does.

 Element class:
  - removed the original rotate operation in the draw() method.
  - added a translate/rotate operation BEFORE the existing translate operation

  If you are really keen for more experience, you could redesign the Element class
  and its inner classes to make more effective  use of the combined translate and
  rotate operation. You would need to make all the element types apart from Curve
  define the shape at their original defined position. The Curve class would be best left
  more or less as it is as a special case with the line-to segment end points relative to
  position. Otherwise, having to move all the segments for a curve would be a complicated operation.
  All of the shape classes would need to have their own move() method defined.

  With all but one of the elements draw at their original position, you could use just the translate
  and rotate operation when these elements are drawn because the origin does not need to be moved.
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