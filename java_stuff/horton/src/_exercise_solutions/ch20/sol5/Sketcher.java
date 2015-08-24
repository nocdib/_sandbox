// Chapter 20 Exercise 5

/*
 Add a toolbar button to switch highlighting on and off.
 The same button should turn it on when it is off and vice versa,
 so you need to change the button label appropriately.
 */

/*
  I did the following to the SketcherFrame class to implement this:

 - created icon images for the selected and unselected state for the new toolbar button and
   added the icon definitions to SketcherConstants.java
 - added a JButton member for the highlight button
 - amended the addToolbarButton() method to return a reference to the button that it adds to the toolbar
 - added the highlight button to the toolbar  in the createToolbar() method.
 - added an icon to the button for its selected state so the button icon will toggle when it is clicked.
 - added an anonymous action listenewr class to toggle the highlight button state
 - added an isHighlighting() method to return the button's selected state

  In the Sketcher View class:
 - nodified the mouseMoved() method in the MouseHandler class to prevent highlighting of elements
   when the highlight button is in the selected state.

   Note: I left the tracking of the element under the cursor in place, even when highlighting is off.
   The button just toggles the element highlighting, not the ability to move and rotate elements.
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