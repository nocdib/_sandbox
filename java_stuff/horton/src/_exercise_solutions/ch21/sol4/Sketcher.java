// Chapter 21 Exercise 4
/*
 Use a Book object to print a cover page plus the sketch
 spread over four pages as in the previous exercise.
 */

/*
  The additional code to do this is in the actionPerformed() method in the FileAction
  inner class to the SketcherFrame class. The print() and getNumberOfPages() methods
  in SketcherView are as in the solution to the previous exercise.

  The actionPerformed() method also has commented out code to illustrate how you might
  modify the Paper object.
*/

// Sketching application
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
    sketch.addObserver(window);                                         // Register window with the model
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

  // Insert a new sketch model
  public void insertModel(SketcherModel newSketch) {
    sketch = newSketch;                                                 // Store the new sketch
    sketch.addObserver(view);                                           // Add the view as observer
    sketch.addObserver(window);                                         // Add the app window as observer
    view.repaint();                                                     // Repaint the view
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
      window.checkForSave();
    }
  }

  private SketcherModel sketch;                                         // The data model for the sketch
  private SketcherView view;                                            // The view of the sketch
  private SketcherFrame window;                                         // The application window
  private static Sketcher theApp;                                       // The application object
}