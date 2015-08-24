// Frame for the Sketcher application
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;

import static java.awt.event.InputEvent.*;
import static java.awt.Color.*;
import static Constants.SketcherConstants.*;
import static javax.swing.Action.*;

@SuppressWarnings("serial")
public class SketcherFrame extends JFrame implements ActionListener {
  // Constructor
  public SketcherFrame(String title, Sketcher theApp) {
    setTitle(title);                                                    // Set the window title
    this.theApp = theApp;                                               // Save app. object reference
    setJMenuBar(menuBar);                                               // Add the menu bar to the window
    setDefaultCloseOperation(EXIT_ON_CLOSE);                            // Default is exit the application

    setCustomIconColor(CUSTOM16,customColor);                           // Setup small custom color icon
    setCustomIconColor(CUSTOM24,customColor);                           // Setup large custom color icon
    createFileMenu();                                                   // Create the File menu
    createElementMenu();                                                // Create the element menu
    createColorMenu();                                                  // Create the element menu
    optionsMenu = new JMenu("Options");                                 // Create options menu
    optionsMenu.setMnemonic('O');                                       // Create shortcut
    menuBar.add(optionsMenu);                                           // Add options to menu bar

    createPopupMenu();                                                  // Create popup

    // Add the font choice item to the options menu
    fontItem = new JMenuItem("Choose font...");
    fontItem.addActionListener(this);
    optionsMenu.add(fontItem);

    fontDlg = new FontDialog(this);                                     // Create the font dialog

    createToolbar();
    toolBar.setRollover(true);

    JMenu helpMenu = new JMenu("Help");                                 // Create Help menu
    helpMenu.setMnemonic('H');                                          // Create Help shortcut

    // Add the About item to the Help menu
    aboutItem = new JMenuItem("About");                                 // Create About menu item
    aboutItem.addActionListener(this);                                  // Listener is the frame
    helpMenu.add(aboutItem);                                            // Add item to menu
    menuBar.add(helpMenu);                                              // Add Help menu to menu bar

    getContentPane().add(toolBar, BorderLayout.NORTH);                  // Add the toolbar
    getContentPane().add(statusBar, BorderLayout.SOUTH);                // Add the statusbar
  }

  // Create File menu item actions
  private void createFileMenuActions() {
    newAction = new FileAction("New", 'N', CTRL_DOWN_MASK);
    openAction = new FileAction("Open", 'O', CTRL_DOWN_MASK);
    closeAction = new FileAction("Close");
    saveAction = new FileAction("Save", 'S', CTRL_DOWN_MASK);
    saveAsAction = new FileAction("Save As...");
    printAction = new FileAction("Print", 'P', CTRL_DOWN_MASK);
    exitAction = new FileAction("Exit", 'X', CTRL_DOWN_MASK);

    // Initialize the array
    FileAction[] actions = {openAction, closeAction, saveAction, saveAsAction, printAction, exitAction};
    fileActions = actions;

    // Add toolbar icons
    newAction.putValue(LARGE_ICON_KEY, NEW24);
    openAction.putValue(LARGE_ICON_KEY, OPEN24);
    saveAction.putValue(LARGE_ICON_KEY, SAVE24);
    saveAsAction.putValue(LARGE_ICON_KEY, SAVEAS24);
    printAction.putValue(LARGE_ICON_KEY, PRINT24);

    // Add menu item icons
    newAction.putValue(SMALL_ICON, NEW16);
    openAction.putValue(SMALL_ICON, OPEN16);
    saveAction.putValue(SMALL_ICON, SAVE16);
    saveAsAction.putValue(SMALL_ICON,SAVEAS16);
    printAction.putValue(SMALL_ICON, PRINT16);

    // Add tooltip text
    newAction.putValue(SHORT_DESCRIPTION, "Create a new sketch");
    openAction.putValue(SHORT_DESCRIPTION, "Read a sketch from a file");
    closeAction.putValue(SHORT_DESCRIPTION, "Close the current sketch");
    saveAction.putValue(SHORT_DESCRIPTION, "Save the current sketch to file");
    saveAsAction.putValue(SHORT_DESCRIPTION, "Save the current sketch to a new file");
    printAction.putValue(SHORT_DESCRIPTION, "Print the current sketch");
    exitAction.putValue(SHORT_DESCRIPTION, "Exit Sketcher");
  }

  // Helper method to set custom icon color
  private void setCustomIconColor(ImageIcon icon, Color color) {
    BufferedImage image = (BufferedImage)(icon.getImage());
    int width = image.getWidth();                                       // Image width
    int indent =  width == 16 ? 3 : 2;                                  // Indent for filled rectangle
    int rectSize = width - 2*indent;                                    // Filled rectangle size
    Graphics2D g2D = image.createGraphics();
    g2D.setPaint(color);
    g2D.fillRect(indent,indent,rectSize,rectSize);                      // Fill centered rectangle
    if(width == 24) {
      TextLayout textLayout = new TextLayout("C", g2D.getFont(), g2D.getFontRenderContext());
      Rectangle2D.Float rect = (Rectangle2D.Float)textLayout.getBounds();
      g2D.setPaint(new Color(255-color.getRed(),255-color.getGreen(), 255-color.getBlue()));
      g2D.drawString("C", (width-rect.width)/2, (width+rect.height)/2);
    }
    g2D.dispose();
  }


  // Create the File menu
  private void createFileMenu() {
    JMenu fileMenu = new JMenu("File");                                 // Create File menu
    fileMenu.setMnemonic('F');                                          // Create shortcut
    createFileMenuActions();                                            // Create Actions for File menu item

    // Construct the file drop-down menu
    fileMenu.add(newAction);                                            // New Sketch menu item
    fileMenu.add(openAction);                                           // Open sketch menu item
    fileMenu.add(closeAction);                                          // Close sketch menu item
    fileMenu.addSeparator();                                            // Add separator
    fileMenu.add(saveAction);                                           // Save sketch to file
    fileMenu.add(saveAsAction);                                         // Save As menu item
    fileMenu.addSeparator();                                            // Add separator
    fileMenu.add(printAction);                                          // Print sketch menu item
    fileMenu.addSeparator();                                            // Add separator
    fileMenu.add(exitAction);                                           // Print sketch menu item
    menuBar.add(fileMenu);                                              // Add the file menu
  }

  // Create Element  menu actions
  private void createElementTypeActions() {
    lineAction = new TypeAction("Line", LINE, 'L', CTRL_DOWN_MASK);
    rectangleAction = new TypeAction("Rectangle", RECTANGLE, 'R', CTRL_DOWN_MASK);
    circleAction =  new TypeAction("Circle", CIRCLE,'C', CTRL_DOWN_MASK);
    curveAction = new TypeAction("Curve", CURVE,'U', CTRL_DOWN_MASK);
    textAction = new TypeAction("Text", TEXT,'T', CTRL_DOWN_MASK);

    // Initialize the array
    TypeAction[] actions = {lineAction, rectangleAction, circleAction, curveAction, textAction};
    typeActions = actions;

    // Add toolbar icons
    lineAction.putValue(LARGE_ICON_KEY, LINE24);
    rectangleAction.putValue(LARGE_ICON_KEY, RECTANGLE24);
    circleAction.putValue(LARGE_ICON_KEY, CIRCLE24);
    curveAction.putValue(LARGE_ICON_KEY, CURVE24);
    textAction.putValue(LARGE_ICON_KEY, TEXT24);

    // Add menu item icons
    lineAction.putValue(SMALL_ICON, LINE16);
    rectangleAction.putValue(SMALL_ICON, RECTANGLE16);
    circleAction.putValue(SMALL_ICON, CIRCLE16);
    curveAction.putValue(SMALL_ICON, CURVE16);
    textAction.putValue(SMALL_ICON, TEXT16);

    // Add tooltip text
    lineAction.putValue(SHORT_DESCRIPTION, "Draw lines");
    rectangleAction.putValue(SHORT_DESCRIPTION, "Draw rectangles");
    circleAction.putValue(SHORT_DESCRIPTION, "Draw circles");
    curveAction.putValue(SHORT_DESCRIPTION, "Draw curves");
    textAction.putValue(SHORT_DESCRIPTION, "Draw text");
  }

  // Create the Elements menu
  private void createElementMenu() {
    createElementTypeActions();
    elementMenu = new JMenu("Elements");                                // Create Elements menu
    elementMenu.setMnemonic('E');                                       // Create shortcut
    createRadioButtonDropDown(elementMenu, typeActions, lineAction);
    menuBar.add(elementMenu);                                           // Add the element menu
  }

  // Create Color menu actions
  private void createElementColorActions() {
    redAction = new ColorAction("Red", RED, 'R', CTRL_DOWN_MASK|ALT_DOWN_MASK);
    yellowAction = new ColorAction("Yellow", YELLOW, 'Y', CTRL_DOWN_MASK|ALT_DOWN_MASK);
    greenAction = new ColorAction("Green", GREEN, 'G', CTRL_DOWN_MASK|ALT_DOWN_MASK);
    blueAction = new ColorAction("Blue", BLUE, 'B', CTRL_DOWN_MASK|ALT_DOWN_MASK);
    customAction = new ColorAction("Custom...", BLUE, 'C', CTRL_DOWN_MASK|ALT_DOWN_MASK);

    // Initialize the array
    ColorAction[] actions = {redAction, greenAction, blueAction, yellowAction, customAction};
    colorActions = actions;

    // Add toolbar icons
    redAction.putValue(LARGE_ICON_KEY, RED24);
    greenAction.putValue(LARGE_ICON_KEY, GREEN24);
    blueAction.putValue(LARGE_ICON_KEY, BLUE24);
    yellowAction.putValue(LARGE_ICON_KEY, YELLOW24);
    customAction.putValue(LARGE_ICON_KEY, CUSTOM24);

    // Add menu item icons
    redAction.putValue(SMALL_ICON, RED16);
    greenAction.putValue(SMALL_ICON, GREEN16);
    blueAction.putValue(SMALL_ICON, BLUE16);
    yellowAction.putValue(SMALL_ICON, YELLOW16);
    customAction.putValue(SMALL_ICON, CUSTOM16);

    // Add tooltip text
    redAction.putValue(SHORT_DESCRIPTION, "Draw in red");
    greenAction.putValue(SHORT_DESCRIPTION, "Draw in green");
    blueAction.putValue(SHORT_DESCRIPTION, "Draw in blue");
    yellowAction.putValue(SHORT_DESCRIPTION, "Draw in yellow");
    customAction.putValue(SHORT_DESCRIPTION, "Draw in custom color");
  }

  // Create the Color menu
  private void createColorMenu() {
    createElementColorActions();
    colorMenu = new JMenu("Color");                                     // Create Elements menu
    colorMenu.setMnemonic('C');                                         // Create shortcut
    createRadioButtonDropDown(colorMenu, colorActions, blueAction);
    menuBar.add(colorMenu);                                             // Add the color menu
  }

  // Menu creation helper
  private void createRadioButtonDropDown(JMenu menu, Action[] actions, Action selected) {
    ButtonGroup group = new ButtonGroup();
    JRadioButtonMenuItem item = null;
    for(Action action : actions) {
      group.add(menu.add(item = new JRadioButtonMenuItem(action)));
      if(action == selected) {
        item.setSelected(true);                                         // This is default selected
      }
    }
  }

  // Create pop-up menu
  private void createPopupMenu() {
    // Element menu items
    popup.add(new JMenuItem(lineAction));
    popup.add(new JMenuItem(rectangleAction));
    popup.add(new JMenuItem(circleAction));
    popup.add(new JMenuItem(curveAction));
    popup.add(new JMenuItem(textAction));

    popup.addSeparator();

    // Color menu items
    popup.add(new JMenuItem(redAction));
    popup.add(new JMenuItem(yellowAction));
    popup.add(new JMenuItem(greenAction));
    popup.add(new JMenuItem(blueAction));
    popup.add(new JMenuItem(customAction));
  }

  // Create toolbar buttons on the toolbar
  private void createToolbar() {
    for(FileAction action: fileActions){
      if(action != exitAction && action != closeAction)
        addToolbarButton(action);                                       // Add the toolbar button
    }
    toolBar.addSeparator();

    // Create Color menu buttons
    for(ColorAction action:colorActions){
        addToolbarButton(action);                                       // Add the toolbar button
    }

    toolBar.addSeparator();

    // Create Elements menu buttons
    for(TypeAction action:typeActions){
        addToolbarButton(action);                                       // Add the toolbar button
    }

    toolBar.addSeparator();
    addToolbarButton(new ColorDialogAction());

    toolBar.addSeparator();
    highlightButton = new JButton(NO_HIGHLIGHT24);
    highlightButton.setSelectedIcon(HIGHLIGHT24);
    highlightButton.setSelected(true);
    highlightButton.setBorder(BorderFactory.createCompoundBorder(       // Add button border
           new EmptyBorder(2,5,5,2),                                    // Outside border
           BorderFactory.createRaisedBevelBorder()));                   // Inside border
    highlightButton. addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        highlightButton.setSelected(!highlightButton.isSelected());
      }
    });
    toolBar.add(highlightButton);
 }

  // Create and add a toolbar button
  private void addToolbarButton(Action action) {
    JButton button = new JButton(action);                               // Create from Action
    button.setBorder(BorderFactory.createCompoundBorder(                // Add button border
           new EmptyBorder(2,5,5,2),                                    // Outside border
           BorderFactory.createRaisedBevelBorder()));                   // Inside border
    button.setHideActionText(true);                                     // No label on the button
    toolBar.add(button);                                                // Add the toolbar button
  }

  // Returns highlighting state
  public boolean isHighlighting() {
    return highlightButton.isSelected();
  }


  // Return the current drawing color
  public Color getElementColor() {
    return elementColor;
  }

  // Return the current element type
  public int getElementType() {
    return elementType;
  }

  // Return current text font
  public Font getFont() {
    return textFont;
  }

  // Method to set the current font
  public void setFont(Font font) {
    textFont = font;
  }

  // Retrieve the pop-up menu
  public JPopupMenu getPopup() {
    return popup;
  }

  // Set radio button menu checks
  private void setChecks(JMenu menu, Object eventSource) {
    if(eventSource instanceof JButton){
      JButton button = (JButton)eventSource;
      Action action = button.getAction();
      for(int i = 0 ; i<menu.getItemCount() ; ++i) {
        JMenuItem item = menu.getItem(i);
        item.setSelected(item.getAction() == action);
      }
    }
  }

  // Handle About menu events
  public void actionPerformed(ActionEvent e)  {
    if(e.getSource() == aboutItem) {
      // Create about dialog with the app window as parent
      JOptionPane.showMessageDialog(this,                               // Parent
                       "Sketcher Copyright Ivor Horton 2011",           // Message
                       "About Sketcher",                                // Title
                       JOptionPane.INFORMATION_MESSAGE);                // Message type
    } else if(e.getSource() == fontItem) {                              // Set the dialog window position
      Rectangle bounds = getBounds();
      fontDlg.setLocationRelativeTo(this);
      fontDlg.setVisible(true);                                         // Show the dialog
    }
  }

  // Defines an Action object for the toolbar button to display a color
  // selection dialog
  class ColorDialogAction extends AbstractAction {
    // Constructor
    public ColorDialogAction() {
      putValue(LARGE_ICON_KEY, COLOR_DIALOG24);
      putValue(SHORT_DESCRIPTION, "Select color...");
    }

    // When the button is clicked, this method displays an option dialog
    // for choosing a color. If the Custom... option is selected a
    // JColorChooser dialog is display for choosing a custom color.
    public void actionPerformed(ActionEvent e) {
      Rectangle bounds = getBounds();

      // Find the current element color to select in the dialog
      int choice = choices.length - 1;
      for(int i = 0 ; i < colors.length ; ++i) {
        if(elementColor.equals(colors[i])){
          choice = i;
          break;
        }
      }
      String input = (String)JOptionPane.showInputDialog(null, "Pick a color...",
                                  "Element Color Choice", JOptionPane.PLAIN_MESSAGE,
                                     null, choices, choices[choice]);
      if(input.equals(choices[choices.length-1])) {
        Color color = JColorChooser.showDialog(null, "Select Custom Color", elementColor);
        if(color != null) {
          elementColor = color;
        }
      } else {
        for(int i = 0 ; i < choices.length-1 ; ++i) {
          if(input.equals(choices[i])) {
            elementColor = colors[i];
            break;
          }
        }
      }
      statusBar.setColorPane(elementColor);
    }
  }

  // Inner class defining Action objects for File menu items
  class FileAction extends AbstractAction {
    // Create action with a name
    FileAction(String name) {
      super(name);
    }

    // Create action with a name and accelerator
    FileAction(String name, char ch, int modifiers) {
      super(name);
      putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(ch, modifiers));

      // Now find the character to underline
      int index = name.toUpperCase().indexOf(ch);
      if(index != -1) {
        putValue(DISPLAYED_MNEMONIC_INDEX_KEY, index);
      }
    }

    // Event handler
    public void actionPerformed(ActionEvent e) {
      // You will add action code here eventually...
    }
  }

  // Inner class defining Action objects for Element type menu items
  class TypeAction extends AbstractAction {
    // Create action with just a name property
    TypeAction(String name, int typeID) {
      super(name);
      this.typeID = typeID;
    }

    // Create action with a name and an accelerator
    private TypeAction(String name,int typeID, char ch, int modifiers) {
      this(name, typeID);
      putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(ch, modifiers));

      // Now find the character to underline
      int index = name.toUpperCase().indexOf(ch);
      if(index != -1) {
        putValue(DISPLAYED_MNEMONIC_INDEX_KEY, index);
      }
    }

    public void actionPerformed(ActionEvent e) {
      elementType = typeID;
      setChecks(elementMenu, e.getSource());
      statusBar.setTypePane(typeID);
    }

    private int typeID;
  }

  // Handles color menu items
  class ColorAction  extends AbstractAction {
    // Create an action with a name and a color
    public ColorAction(String name, Color color) {
      super(name);
      this.color = color;
    }

    // Create an action with a name, a color, and an accelerator
    public ColorAction(String name, Color color, char ch, int modifiers) {
      this(name, color);
      putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(ch, modifiers));

      // Now find the character to underline
      int index = name.toUpperCase().indexOf(ch);
      if(index != -1) {
        putValue(DISPLAYED_MNEMONIC_INDEX_KEY, index);
      }
    }

    public void actionPerformed(ActionEvent e) {
      if(this == customAction) {
        // This could be a new custom color
        Color newColor = JColorChooser.showDialog(SketcherFrame.this, "Select Custom Color", customColor);
        if(newColor != null) {
          elementColor = customColor = newColor;

          // Setup small custom color icons
          setCustomIconColor(CUSTOM16,customColor);
          setCustomIconColor(CUSTOM24,customColor);
          toolBar.repaint();                                            // Repaint the toolbar
        }
      } else {
          // This is just a standard color change
          elementColor = color;
      }
      statusBar.setColorPane(elementColor);                             // Update the status bar
      setChecks(colorMenu, e.getSource());                              // Set Color menu checks
    }

    private Color color;
  }

  // File actions
  private FileAction newAction, openAction, closeAction, saveAction, saveAsAction, printAction, exitAction;
  private FileAction[] fileActions;                                     // File actions as an array

  // Element type actions
  private TypeAction lineAction, rectangleAction, circleAction, curveAction, textAction;
  private TypeAction[] typeActions;                                     // Type actions as an array

// Element color actions
  private ColorAction redAction, yellowAction,greenAction, blueAction, customAction;
  private ColorAction[] colorActions;                                   // Color actions as an array

  private JMenuBar menuBar = new JMenuBar();                            // Window menu bar
  private JMenu elementMenu;                                            // Elements menu
  private JMenu colorMenu;                                              // Color menu
  private JMenu optionsMenu;                                            // Options menu

  private StatusBar statusBar = new StatusBar();                        // Window status bar
  private FontDialog fontDlg;                                           // The font dialog

  private JMenuItem aboutItem;                                          // About menu item
  private JMenuItem fontItem;                                           // Font chooser menu item

  private JButton highlightButton;                                      // Toolbar button to toggle highlighting

  private JPopupMenu popup = new JPopupMenu("General");                 // Window pop-up
  private Color elementColor = DEFAULT_ELEMENT_COLOR;                   // Current element color
  private Color customColor = DEFAULT_ELEMENT_COLOR;                    // Current custom color
  private int elementType = DEFAULT_ELEMENT_TYPE;                       // Current element type
  private Font textFont = DEFAULT_FONT;                                 // Default font for text elements
  private JToolBar toolBar = new JToolBar();                            // Window toolbar
  private Sketcher theApp;                                              // The application object
}
