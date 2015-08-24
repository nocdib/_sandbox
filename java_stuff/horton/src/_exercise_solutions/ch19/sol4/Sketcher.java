// Chapter 19 Exercise 4

/*
 Extend the curve class to support filled shapes.
 */

 /*
  This is a no-brainer because open curves defined by a GeneralPath object can be filled in the same way
  as a Rectangle2D object.

  I did the following:
  Element class:
  - modified the constructor for the Curve class to add a parameter set the filled member inherited from Element.
  - modified the draw() method in the Curve class to fill when necessary.
  - modified the createElement() method to call the Curve constructor with the additional fill argument.
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