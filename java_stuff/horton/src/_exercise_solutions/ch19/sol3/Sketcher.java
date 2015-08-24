// Chapter 19 Exercise 3

/*
 Extend the classes defining rectangles, circles, and ellipses to support filled shapes.
 */

 /*
  There is more than one way to do this. I implemented it as follows:

  Elements are created as filled or unfilled depending on the state of the button
  determining fill. The fill button is 'unfilled' initially, and toggles between
  the filled and unfilled state when it is clicked.

 SketcherFrame class:
 - added method isFillOn()to return true if fill is on for the fill button. The fill button
   indicates an element is to be filled when it is in its selected state.

 Element class:
 - added a filled member of type boolean with a default of false. This will indicate
   whether or not the element is to be filled
 - amended the CreateElement() method to accept an additional argument of type boolean for fill. This method
   calls the element constructor with the filled parameter when it applies.
 - amended the constructors in the Rectangle, Circle and Ellipse classes to set the inherited filled member from an additional argument.
 - modified the draw() method int the Rectangle, Circle, and Ellipse inner classes to fill the element when filled is true.

 SketcherView.MouseHandler class:
 - modified the createElement() method call to pass the fill button state as an additional argument.

  */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Sketcher {
  public static void main(String[] args) {
     theApp = new Sketcher();                                          // Create the application object
   SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                          theApp.createGUI();                          // Call GUI creator
            }
        });
  }

  // Method to create the application GUI
  private void createGUI() {
    window = new SketcherFrame("Sketcher", this);                      // Create the app window
    Toolkit theKit = window.getToolkit();                              // Get the window toolkit
    Dimension wndSize = theKit.getScreenSize();                        // Get screen size

    // Set the position to screen center & size to half screen size
    window.setSize(wndSize.width/2, wndSize.height/2);                 // Set window size
    window.setLocationRelativeTo(null);                                // Center window

    window.addWindowListener(new WindowHandler());                     // Add window listener

    sketch = new SketcherModel();                                      // Create the model
    view = new SketcherView(this);                                     // Create the view
    sketch.addObserver(view);                                          // Register view with the model
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

  private SketcherModel sketch;                                        // The data model for the sketch
  private SketcherView view;                                           // The view of the sketch
  private SketcherFrame window;                                        // The application window
  private static Sketcher theApp;                                      // The application object
}