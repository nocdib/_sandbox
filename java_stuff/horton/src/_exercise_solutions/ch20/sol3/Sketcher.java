// Chapter 20 Exercise 3

/*
 Display a special context menu when the cursor is over a TEXT object
 that provides a menu option to edit the text through a dialog.
 */

/*
  SketcherView class:
   - added a textEditItem menu item member for the additional popup menu item for a Text element
   - added an anonymous class to handle action events for the new menu item
   - added a editElementText() method to manage editing the text in the current Text element
   - amended the showContextMenu() method in the MouseHandler inner class to add the textEditItem
     to the popup when the element is type Element.Text. It also removes the item when the standard
     element popup is displayed. Removing an element that is not present does nothing.

  Element.Text class:
  - added get and set methods for the text. The set method reecomputes the bounding rectangle.
  - modified the constructor to remember the FontMetrics object for the font in a new member. This is
    used by the setText() method to obtain the new bounds rectangle.
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