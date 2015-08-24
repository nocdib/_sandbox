// Frame for the Sketcher application
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.*;
import java.nio.file.*;
import java.io.*;
import java.util.*;
import java.awt.image.BufferedImage;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import javax.print.PrintService;
import java.awt.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import javax.xml.transform.dom.*;
import javax.xml.validation.*;

import org.xml.sax.helpers.DefaultHandler;

import static java.awt.event.InputEvent.*;
import static java.awt.AWTEvent.*;
import static java.awt.Color.*;
import static Constants.SketcherConstants.*;
import static javax.swing.Action.*;

@SuppressWarnings("serial")
public class SketcherFrame extends JFrame implements ActionListener, Observer, Printable, ErrorHandler, ErrorListener {
  // Constructor
  public SketcherFrame(String title, Sketcher theApp) {

    printJob = PrinterJob.getPrinterJob();                             // Get a printing object
    pageFormat = printJob.defaultPage();                               // Get the page format
    printer = printJob.getPrintService();                              // Get the default printer

    checkDirectory(DEFAULT_DIRECTORY);                                 // Validate default directory
    fileChooser = new JFileChooser(DEFAULT_DIRECTORY.toString());

    setTitle(title);                                                   // Set the window title
    frameTitle = title;                                                // Remember original title

    this.theApp = theApp;                                              // Save app. object reference
    setJMenuBar(menuBar);                                              // Add the menu bar to the window
    setDefaultCloseOperation(EXIT_ON_CLOSE);                           // Default is exit the application

    setCustomIconColor(CUSTOM16,customColor);                          // Setup small custom color icon
    setCustomIconColor(CUSTOM24,customColor);                          // Setup large custom color icon
    createFileMenu();                                                  // Create the File menu
    createElementMenu();                                               // Create the element menu
    createColorMenu();                                                 // Create the element menu
    optionsMenu = new JMenu("Options");                                // Create options menu
    optionsMenu.setMnemonic('O');                                      // Create shortcut
    menuBar.add(optionsMenu);                                          // Add options to menu bar

    createPopupMenu();                                                 // Create popup

    // Add the font choice item to the options menu
    fontItem = new JMenuItem("Choose font...");
    fontItem.addActionListener(this);
    optionsMenu.add(fontItem);

    fontDlg = new FontDialog(this);                                    // Create the font dialog

    createToolbar();
    toolBar.setRollover(true);

    JMenu helpMenu = new JMenu("Help");                                // Create Help menu
    helpMenu.setMnemonic('H');                                         // Create Help shortcut

    // Add the About item to the Help menu
    aboutItem = new JMenuItem("About");                                // Create About menu item
    aboutItem.addActionListener(this);                                 // Listener is the frame
    helpMenu.add(aboutItem);                                           // Add item to menu
    menuBar.add(helpMenu);                                             // Add Help menu to menu bar

    getContentPane().add(toolBar, BorderLayout.NORTH);                 // Add the toolbar
    getContentPane().add(statusBar, BorderLayout.SOUTH);               // Add the statusbar
  }

  // Verify directory is available
  private void checkDirectory(Path directory) {
    if(Files.notExists(directory)) {
        JOptionPane.showMessageDialog(null,
                            "Creating directory: " + directory,
                            "Directory Not Found",
                            JOptionPane.INFORMATION_MESSAGE);
        try {
          Files.createDirectories(directory);
        } catch(IOException e) {
          e.printStackTrace(System.err);
          JOptionPane.showMessageDialog(null,
              "Cannot create: " +directory + ". Terminating Sketcher.",
              "Directory Creation Failed",
              JOptionPane.ERROR_MESSAGE);
          System.exit(1);
        }
    }
  }

  // Method called by SketcherModel object when it changes
  public void update(Observable o, Object obj) {
    sketchChanged = true;
  }

  // Create File menu item actions
  private void createFileMenuActions() {
    newAction = new FileAction("New", 'N', CTRL_DOWN_MASK);
    openAction = new FileAction("Open", 'O', CTRL_DOWN_MASK);
    closeAction = new FileAction("Close");
    saveAction = new FileAction("Save", 'S', CTRL_DOWN_MASK);
    saveAsAction = new FileAction("Save As...");
    printAction = new FileAction("Print", 'P', CTRL_DOWN_MASK);
    exportAction = new FileAction("Export XML", 'E', CTRL_DOWN_MASK);
    importAction = new FileAction("Import XML", 'I', CTRL_DOWN_MASK);
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
    exportAction.putValue(SHORT_DESCRIPTION, "Export sketch as an XML file");
    importAction.putValue(SHORT_DESCRIPTION, "Import sketch from an XML file");
    exitAction.putValue(SHORT_DESCRIPTION, "Exit Sketcher");
  }

  // Helper method to set custom icon color
  private void setCustomIconColor(ImageIcon icon, Color color) {
    BufferedImage image = (BufferedImage)(icon.getImage());
    int width = image.getWidth();                                      // Image width
    int indent =  width == 16 ? 3 : 2;                                 // Indent for filled rectangle
    int rectSize = width - 2*indent;                                   // Filled rectangle size
    Graphics2D g2D = image.createGraphics();
    g2D.setPaint(color);
    g2D.fillRect(indent,indent,rectSize,rectSize);                     // Fill centered rectangle
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
    JMenu fileMenu = new JMenu("File");                                // Create File menu
    fileMenu.setMnemonic('F');                                         // Create shortcut
    createFileMenuActions();                                           // Create Actions for File menu item

    // Create print setup menu item
    JMenuItem printSetupItem = new JMenuItem("Print Setup...");
    printSetupItem.setToolTipText("Setup the page for the printer");
    printSetupItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                  // update the page format
                  PageFormat pf = printJob.pageDialog(printAttr);
                  if(pf != null) {
                    pageFormat = pf;    // update the page format
                  }
                }
    });

    // Menu item to print the application window
    JMenuItem printWindowItem = new JMenuItem("Print Window");
    printWindowItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                  if(printer == null) {
                    JOptionPane.showMessageDialog(SketcherFrame.this,
                                               "No default printer available.",
                                               "Printer Error",
                                               JOptionPane.ERROR_MESSAGE);
                    return;
                  }
                  // The app window is the page source
                  printJob.setPrintable(SketcherFrame.this, pageFormat);
                  try {
                    printJob.print();
                  } catch(PrinterException pe) {
                    System.out.println(pe);
                    JOptionPane.showMessageDialog(SketcherFrame.this,
                                      "Error printing the application window.",
                                      "Printer Error",
                                                  JOptionPane.ERROR_MESSAGE);
                  }
                }
    });

    // Construct the file drop-down menu
    fileMenu.add(newAction);                                           // New Sketch menu item
    fileMenu.add(openAction);                                          // Open sketch menu item
    fileMenu.add(closeAction);                                         // Close sketch menu item
    fileMenu.addSeparator();                                           // Add separator
    fileMenu.add(saveAction);                                          // Save sketch to file
    fileMenu.add(saveAsAction);                                        // Save As menu item
    fileMenu.addSeparator();                                           // Add separator
    fileMenu.add(printAction);                                         // Print sketch menu item
    fileMenu.add(printSetupItem);                                      // Print page setup menu item
    fileMenu.add(printWindowItem);                                     // Print window menu item
    fileMenu.addSeparator();                                           // Add separator
    fileMenu.add(exportAction);                                        // Export XML menu item
    fileMenu.add(importAction);                                        // Import XML menu item
    fileMenu.addSeparator();                                           // Add separator
    fileMenu.add(exitAction);                                          // Print sketch menu item
    menuBar.add(fileMenu);                                             // Add the file menu
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
    elementMenu = new JMenu("Elements");                               // Create Elements menu
    elementMenu.setMnemonic('E');                                      // Create shortcut
    createRadioButtonDropDown(elementMenu, typeActions, lineAction);
    menuBar.add(elementMenu);                                          // Add the element menu
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
    colorMenu = new JMenu("Color");                                    // Create Elements menu
    colorMenu.setMnemonic('C');                                        // Create shortcut
    createRadioButtonDropDown(colorMenu, colorActions, blueAction);
    menuBar.add(colorMenu);                                            // Add the color menu
  }

  // Menu creation helper
  private void createRadioButtonDropDown(JMenu menu, Action[] actions, Action selected) {
    ButtonGroup group = new ButtonGroup();
    JRadioButtonMenuItem item = null;
    for(Action action : actions) {
      group.add(menu.add(item = new JRadioButtonMenuItem(action)));
      if(action == selected) {
        item.setSelected(true);                                        // This is default selected
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
        addToolbarButton(action);                                      // Add the toolbar button
    }
    toolBar.addSeparator();

    // Create Color menu buttons
    for(ColorAction action:colorActions){
        addToolbarButton(action);                                      // Add the toolbar button
    }

    toolBar.addSeparator();

    // Create Elements menu buttons
    for(TypeAction action:typeActions){
        addToolbarButton(action);                                      // Add the toolbar button
    }
 }

  // Create and add a toolbar button
  private void addToolbarButton(Action action) {
    JButton button = new JButton(action);                              // Create from Action
    button.setBorder(BorderFactory.createCompoundBorder(               // Add button border
           new EmptyBorder(2,5,5,2),                                   // Outside border
           BorderFactory.createRaisedBevelBorder()));                  // Inside border
    button.setHideActionText(true);                                    // No label on the button
    toolBar.add(button);                                               // Add the toolbar button
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
      JOptionPane.showMessageDialog(this,                              // Parent
                       "Sketcher Copyright Ivor Horton 2011",          // Message
                       "About Sketcher",                               // Title
                       JOptionPane.INFORMATION_MESSAGE);               // Message type
    } else if(e.getSource() == fontItem) {                             // Set the dialog window position
      Rectangle bounds = getBounds();
      fontDlg.setLocationRelativeTo(this);
      fontDlg.setVisible(true);                                        // Show the dialog
    }
  }

  // Display a custom file dialog
  private Path showDialog(String dialogTitle,
                          String approveButtonText,
                          String approveButtonTooltip,
                          ExtensionFilter filter,
                          Path file) {                                 // Current file path � if any
    fileChooser.setDialogTitle(dialogTitle);
    fileChooser.setApproveButtonText(approveButtonText);
    fileChooser.setApproveButtonToolTipText(approveButtonTooltip);
    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    fileChooser.addChoosableFileFilter(filter);                        // Add the filter
    fileChooser.setFileFilter(filter);                                 // and select it

    fileChooser.rescanCurrentDirectory();
    Path selectedFile = null;
    if(file == null) {
      selectedFile = Paths.get(
               fileChooser.getCurrentDirectory().toString(), DEFAULT_FILENAME);
    } else {
      selectedFile = file;
    }
    selectedFile = setFileExtension(selectedFile, filter == xmlFileFilter ? ".xml" : ".ske");
    fileChooser.setSelectedFile(selectedFile.toFile());

    // Show the file save dialog
    int result = fileChooser.showDialog(this, null);
    return (result == JFileChooser.APPROVE_OPTION) ?
                    Paths.get(fileChooser.getSelectedFile().getPath()) : null;
  }


  // Save the sketch if it is necessary
  private void saveOperation() {
    if(!sketchChanged) {                                               // If the sketch is unchanged...
      return;                                                          // ... do nothing
    }

    if(currentSketchFile != null) {                                    // If the sketch has been saved...
      if(saveSketch(currentSketchFile)) {                              // ... just save it.
        sketchChanged = false;                                         // Write successful
      }
      return;
    }

    // Here, the sketch was never saved...
    Path file = showDialog("Save Sketch",                              // ...so display Save dialog
                        "Save",
                        "Save the sketch",
                        sketchFilter,
                        Paths.get(DEFAULT_FILENAME));
    if(file == null) {                                                 // No file selected...
      return;                                                          // ... so we are done.
    }

    file = setFileExtension(file, "ske");                              // Make sure extension is .ske

    if(Files.exists(file) &&                                           // If the path exists and...
         JOptionPane.NO_OPTION ==                                      // .. NO selected in dialog...
              JOptionPane.showConfirmDialog(
                                  this,
                                  file.getFileName()+" exists. Overwrite?",
                                  "Confirm Save As",
                                  JOptionPane.YES_NO_OPTION,
                                  JOptionPane.WARNING_MESSAGE)) {
            return;                                                    // ...do nothing
    }
    if(saveSketch(file)) {                                             // Save the sketch
        currentSketchFile = file;                                      // Save successful
        setTitle(frameTitle + " - " + currentSketchFile);              // Update title bar
        sketchChanged = false;                                         // Sketch now unchanged
    }
  }

  // Save the sketch with a different file name and/or location
  private void saveAsOperation() {
    Path file = showDialog("Save Sketch As",
                           "Save",
                           "Save the sketch",
                           sketchFilter,
                           currentSketchFile == null ?
                           Paths.get(DEFAULT_FILENAME): currentSketchFile);

     if(file == null) {                                                // No file selected...
       return;                                                         // ...so we are done.
     }

    file = setFileExtension(file, "ske");                              // Make sure extension is .ske

    if(Files.exists(file) &&
          !file.equals(currentSketchFile) &&
            JOptionPane.NO_OPTION ==                                   // Overwrite warning
              JOptionPane.showConfirmDialog(this,
                                  file.getFileName() + " exists. Overwrite?",
                                  "Confirm Save As",
                                  JOptionPane.YES_NO_OPTION,
                                  JOptionPane.WARNING_MESSAGE)) {
      return;                                                          // No file selected
    }

    if(saveSketch(file)) {                                             // Save the sketch
      currentSketchFile = file;                                        // Save successful
      setTitle(frameTitle + " - " + currentSketchFile);                // Update title bar
      sketchChanged = false;                                           // Sketch now unchanged
    }
  }

  // Set the extension for a file path
  private Path setFileExtension(Path file, String extension) {
    StringBuffer fileName = new StringBuffer(file.getFileName().toString());
    if(fileName.indexOf(extension) >= 0) {
      return file;
    }
    int index = fileName.lastIndexOf(".");
    if(index < 0) {                                                    // No extension
      fileName.append(".").append(extension);                          // so append one
    }
    return file.getParent().resolve(fileName.toString());
  }

  // Write a sketch to file path file
  private boolean saveSketch(Path file) {
  try (ObjectOutputStream out =
                        new ObjectOutputStream(new BufferedOutputStream(Files.newOutputStream(file)))) {
      out.writeObject(theApp.getModel());                              // Write the sketch to the stream
    } catch(IOException e) {
      System.err.println(e);
      JOptionPane.showMessageDialog(this,
                                    "Error writing a sketch to " + file,
                                    "File Output Error",
                                    JOptionPane.ERROR_MESSAGE);
      return false;                                                    // Serious error - file not written
    }
    return true;
  }

  // Export a sketch as XML
  private void exportXMLOperation() {
    Path selectedFile = null;
    if(currentSketchFile == null) {
      selectedFile = Paths.get(
               fileChooser.getCurrentDirectory().toString(), DEFAULT_FILENAME);
    } else {
      selectedFile = currentSketchFile;
    }
    // Make extension .xml
    selectedFile = setFileExtension(selectedFile, ".xml");

    Path file  = showDialog("Export Sketch as XML", "Export",
                          "Export sketch as XML", xmlFileFilter, selectedFile);
    if(file == null) {                                                 // No file selected...
      return;                                                          // ... so we are done.
    }

    if(Files.exists(file) &&                                           // If the path exists and...
         JOptionPane.NO_OPTION ==                                      // .. NO selected in dialog...
              JOptionPane.showConfirmDialog(
                                  this,
                                  file.getFileName() + " exists. Overwrite?",
                                  "Confirm Save As",
                                  JOptionPane.YES_NO_OPTION,
                                  JOptionPane.WARNING_MESSAGE)) {
            return;                              // ...do nothing
    }
    saveXMLSketch(file);
  }

  // Write XML sketch to file
  private void saveXMLSketch(Path file) {
    Document document = createDocument();                              // XML representation of the sketch
    Node node = document.getDocumentElement();                         // Document tree base
    try(BufferedOutputStream xmlOut =
                       new BufferedOutputStream(Files.newOutputStream(file))) {
        TransformerFactory factory = TransformerFactory.newInstance();

        // Create transformer
        Transformer transformer = factory.newTransformer();
        transformer.setErrorListener(this);

        // Set properties - add whitespace for readability
        //                - include DOCTYPE declaration in output
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(
                  OutputKeys.DOCTYPE_SYSTEM, "D:/Beg Java Stuff/sketcher.dtd");

        // Source is the document object - result is the file
        DOMSource source = new DOMSource(node);
        StreamResult xmlFile = new StreamResult(xmlOut);
        transformer.transform(source, xmlFile);                        // Write XML to file
    } catch (TransformerConfigurationException tce) {
        System.err.println("Transformer Factory error: " + tce.getMessage());
    } catch (TransformerException te) {
        System.err.println("Transformation error: " + te.getMessage());
    } catch (IOException e) {
        System.err.println("I/O error writing XML file: " + e.getMessage());
    }
  }

  // Handle Import XML menu item events
  private void importXMLOperation() {
    checkForSave();

    // Now get a the destination file path
    Path file = showDialog(
                     "Open XML Sketch File",                           // Dialog window title
                     "Open",                                           // Button label
                     "Read a sketch from an XML file",                 // Button tooltip text
                     xmlFileFilter,                                    // File filter
                     null);                                            // No file selected
    if(file != null) {
      openXMLSketch(file);
    }
  }

  // Read an XML sketch from a file
  private void openXMLSketch(Path file) {
    SAXParserFactory spf = SAXParserFactory.newInstance();
    SAXParser parser = null;
    spf.setNamespaceAware(true);
    spf.setValidating(true);
    SAXElementHandler handler = new SAXElementHandler();
    try (BufferedInputStream xmlIn = new BufferedInputStream(Files.newInputStream(file))){
      parser = spf.newSAXParser();
       parser.parse(xmlIn, handler);


    // Not required for XML input using SAX

//      StreamSource source = new StreamSource(xmlIn);
//      DocumentBuilderFactory builderFactory =
//                                      DocumentBuilderFactory.newInstance();
//      builderFactory.setNamespaceAware(true);
//      builderFactory.setValidating(true);
//      DocumentBuilder builder = builderFactory.newDocumentBuilder();
//      builder.setErrorHandler(this);
//      Document xmlDoc = builder.newDocument();
//      DOMResult result = new DOMResult(xmlDoc);
//
//     // Create a factory object for XML transformers
//     TransformerFactory factory = TransformerFactory.newInstance();
//     Transformer transformer = factory.newTransformer();               // Create transformer
//     transformer.setErrorListener(this);
//     transformer.transform(source, result);                            // Read the XML file
//     theApp.insertModel(createModelFromXML(xmlDoc));                   // Create the sketch



     // Change file extension to .ske
     currentSketchFile = setFileExtension(file, ".ske");
     setTitle(frameTitle+currentSketchFile);                           // Update the window title
     sketchChanged = false;                                            // Status is unchanged
    } catch(SAXException | ParserConfigurationException e) {
      e.printStackTrace();
      System.exit(1);
     } catch(IOException e) {
     System.err.println(e);
     JOptionPane.showMessageDialog(this,
                                  "Error reading a sketch file.",
                                  "File Input Error",
                                  JOptionPane.ERROR_MESSAGE);
    }
  }

    // Not required for XML input using SAX
//  private SketcherModel createModelFromXML(Document xmlDoc) {
//    SketcherModel model = new SketcherModel();
//     NodeList nodes = xmlDoc.getDocumentElement().getChildNodes();
//    // The child nodes should be elements representing sketch elements
//    if(nodes.getLength() > 0) {                                        // If there are some...
//      Node elementNode = null;
//      for(int i = 0 ; i<nodes.getLength() ; ++i){                      // ...process them
//        elementNode = nodes.item(i);
//        switch(elementNode.getNodeName()) {
//          case "line":
//            model.add(new Element.Line(elementNode));
//            break;
//          case "rectangle":
//            model.add(new Element.Rectangle(elementNode));
//            break;
//          case "circle":
//            model.add(new Element.Circle(elementNode));
//            break;
//          case "curve":
//            model.add(new Element.Curve(elementNode));
//            break;
//          case "text":
//            model.add(new Element.Text(elementNode));
//            break;
//          default:
//            System.err.println("Invalid XML node: " + elementNode);
//            break;
//        }
//      }
//    }
//    return model;
//  }

  // Method for opening a sketch file and creating the model
  public boolean openSketch(Path file) {
    try (ObjectInputStream in = new ObjectInputStream(
                        new BufferedInputStream(Files.newInputStream(file)))){
      theApp.insertModel((SketcherModel)in.readObject());
      currentSketchFile = file;
      setTitle(frameTitle+" - "+currentSketchFile);                    // Update the window title
      sketchChanged = false;                                           // Status is unchanged
    } catch(Exception e) {
      System.err.println(e);
      JOptionPane.showMessageDialog(this,
                                    "Error reading a sketch file.",
                                    "File Input Error",
                                    JOptionPane.ERROR_MESSAGE);
      return false;
    }
    return true;
  }

  // Prompt for save operation when necessary for File Open
  public void checkForSave() {
    if(sketchChanged && JOptionPane.YES_OPTION ==
            JOptionPane.showConfirmDialog(this,
                             "Current file has changed. Save current file?",
                             "Confirm Save Current File",
                             JOptionPane.YES_NO_OPTION,
                             JOptionPane.WARNING_MESSAGE)) {
      saveOperation();
    }
  }

  // Method to return the name of the current sketch
  public String getSketchName() {
    return currentSketchFile == null ? DEFAULT_FILENAME.toString() :
                                       currentSketchFile.getFileName().toString();
  }

  // Method to return a reference to the current PageFormat object
  public PageFormat getPageFormat() {
    return pageFormat;
  }

  // Print the window
  public int print(Graphics g,
                   PageFormat pageFormat,
                   int pageIndex)
             throws PrinterException {

    if(pageIndex > 0)                                                  // Only one page page 0 to be printed
      return NO_SUCH_PAGE;

    // Scale the component to fit
    Graphics2D g2D = (Graphics2D) g;

    // Calculate the scale factor to fit the window to the page
    double scaleX = pageFormat.getImageableWidth()/getWidth();
    double scaleY = pageFormat.getImageableHeight()/getHeight();

    double scale = Math.min(scaleX,scaleY);                            // Get minimum scale factor

    // Move paper origin to page printing area corner
    g2D.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
    g2D.scale(scale,scale);                                            // Apply the scale factor

    printAll(g2D);                                                     // Draw the component
    return PAGE_EXISTS;
  }

  // Creates a DOM Document object encapsulating the current sketch
  public Document createDocument() {
    Document doc = null;
    try {
      DocumentBuilderFactory builderFactory =
                                          DocumentBuilderFactory.newInstance();
      builderFactory.setNamespaceAware(true);
      builderFactory.setValidating(true);
      builderFactory.setIgnoringElementContentWhitespace(true);
      DocumentBuilder builder = builderFactory.newDocumentBuilder();
      builder.setErrorHandler(this);
      DOMImplementation domImpl = builder.getDOMImplementation();
      Path dtdFile = Paths.get(System.getProperty("user.home")).
                    resolve("Beginning Java Stuff").resolve("sketcher.dtd");
      doc = domImpl.createDocument(null, "sketch", domImpl.createDocumentType(
                           "sketch", null, dtdFile.toString()));
    } catch(ParserConfigurationException pce) {
      JOptionPane.showMessageDialog(this,
                  "Parser configuration error while creating document",
                  "DOM Parser Error",
                  JOptionPane.ERROR_MESSAGE);
      System.err.println(pce.getMessage());
      pce.printStackTrace();
      return null;
    } catch(DOMException de) {
      JOptionPane.showInternalMessageDialog(null,
                  "DOM exception thrown while creating document",
                  "DOM Error",
                  JOptionPane.ERROR_MESSAGE);
      System.err.println(de.getMessage());
      de.printStackTrace(System.err);
      return null;
   }

    // Each element in the sketch can create its own node in the document
    SketcherModel elements = theApp.getModel();                        // Get the sketch
    for(Element element : elements) {                                  // For each element...
      element.addElementNode(doc);                                     // ...add its node.
    }
    return doc;
  }

  // Handles recoverable errors from parsing XML
  public void error(SAXParseException spe) {
      JOptionPane.showMessageDialog(SketcherFrame.this,
                                   "Error at line " + spe.getLineNumber() +
                                   "\n"+spe.getMessage(),
                                   "DOM Parser Error",
                                   JOptionPane.ERROR_MESSAGE);
  }

  // Handles fatal errors from parsing XML
  public void fatalError(SAXParseException spe) throws SAXParseException {
      JOptionPane.showMessageDialog(SketcherFrame.this,
                                 "Fatal error at line " + spe.getLineNumber() +
                                           "\n" + spe.getMessage(),
                                 "DOM Parser Error",
                                 JOptionPane.ERROR_MESSAGE);
      throw spe;
  }

  // Handles warnings from parsing XML
  public void warning(SAXParseException spe) {
      JOptionPane.showMessageDialog(SketcherFrame.this,
                                    "Warning at line "+spe.getLineNumber()
                                  + "\n"+spe.getMessage(),
                                    "DOM Parser Error",
                                    JOptionPane.ERROR_MESSAGE);
  }

  // Handles recoverable errors from transforming XML
  public void error(TransformerException te) {
    System.err.println("Error transforming XML: " + te.getMessage());
  }

  // Handles fatal errors from transforming XML
  public void fatalError(TransformerException te) {
    System.err.println("Fatal error transforming XML: " + te.getMessage());
    System.exit(1);
  }

  // Handles warnings from transforming XML
  public void warning(TransformerException te) {
    System.err.println("Warning transforming XML: " + te.getMessage());
  }

  // Defines a handler for SAX events when parsing an XML sketch
  /*
    At the start of a document we create an empty model. Data for Sketcher elements
    is recorded in the startElement() callback method and the element is ultimately
    created in the endElement() callback and added to the model. The model is inserted
    into the Sketcher application in the endDocument() callback method.
  */
  class SAXElementHandler extends DefaultHandler {
    public void startDocument() {
      xmlSketch = new SketcherModel();                                 // Create the new model
    }
    public void endDocument()  {
      theApp.insertModel(xmlSketch);                                   // Insert the new model
    }

    // This method will recognize an XML element and save any attributes it has
    // in a form to be used when the sketch element is created in the endElement() method
    public void startElement(String uri, String localName, String qname, Attributes attr) {
      // Determine what kind of element we have by checking localName for known element names
      switch(localName) {
        case "sketch":
          break;

        case "line":
          // Should be one attribute - the angle
          angle = Double.parseDouble(attr.getValue("angle"));
          elementType = LINE;
          break;

        case "rectangle":
        // Should be three attributes - the angle, the width and the height
        angle = Double.parseDouble(attr.getValue("angle"));
        width = (int)Double.parseDouble(attr.getValue("width"));
        height = (int)Double.parseDouble(attr.getValue("height"));
        elementType = RECTANGLE;
          break;

        case "circle":
        // Should be two attributes - the angle, and the diameter
        angle = Double.parseDouble(attr.getValue("angle"));
        diameter = (int)Double.parseDouble(attr.getValue("diameter"));
        elementType = CIRCLE;
          break;

        case "curve":
          // Should be one attribute - the angle
          angle = Double.parseDouble(attr.getValue("angle"));
          elementType = CURVE;
          break;

        case "text":
        // Should be two attributes - the angle, and the max ascent
        angle = Double.parseDouble(attr.getValue("angle"));
        maxAscent = Integer.parseInt(attr.getValue("maxascent"));
        elementType = TEXT;
          break;

        case "position":
          position = getPoint(attr);
          break;

        case "color":
        // Should be three attributes - the RGB values
        color = new Color(Integer.parseInt(attr.getValue("R")),
                          Integer.parseInt(attr.getValue("G")),
                          Integer.parseInt(attr.getValue("B")));
          break;

        case "endpoint":
          x2 = (int)Double.parseDouble(attr.getValue("x"));
          y2 = (int)Double.parseDouble(attr.getValue("y"));
          break;

        case "bounds":
          bounds = new java.awt.Rectangle(Integer.parseInt(attr.getValue("x")),
                                          Integer.parseInt(attr.getValue("y")),
                                          Integer.parseInt(attr.getValue("width")),
                                          Integer.parseInt(attr.getValue("height")));
          break;

        case "point":
          points.add(getPoint(attr));
          break;

        case "string":
          // The string data will be collected as characters
          break;

        case "font":
          // Should be three attributes - the font name, the font style, and the point size
          String styleStr = attr.getValue("fontstyle");
          int style = 0;

          // Figure out the style from the style attribute value
          switch(styleStr) {
            case "bold":
              style = Font.BOLD;
              break;
            case "plain":
              style = Font.PLAIN;
              break;
            case "italic":
              style = Font.ITALIC;
              break;
            case "bold-italic":
              style = Font.BOLD & Font.ITALIC;
              break;
            default:
              System.err.println("Invalid font style from XML: " + styleStr);
              style = Font.PLAIN;
              break;
          }

        font = new Font(attr.getValue("fontname"),                     // Create the font object
                        style,
                        Integer.parseInt(attr.getValue("pointsize")));
          break;

        default:
          System.err.println("Invalid start element name from XML: " + localName);
          break;
      }
    }

    // Helper method - creates a Point object from attributes
    private Point getPoint(Attributes attr) {
      return new Point((int)Double.parseDouble(attr.getValue("x")),
                       (int)Double.parseDouble(attr.getValue("y")));
    }

    // Creates Sketcher elements
    public void endElement(String uri, String localName, String qname) {
      // Determine what kind of element we are ending
      switch(localName) {
        case "sketch":
          break;

        case "line":
          // The end point is defined in XML relative to the origin so we must
          // add the position coordinates to make it relative to position.
          x2 += position.x;
          y2 += position.y;

          // Create the Line element
          Element.Line line = new Element.Line(position, new Point(x2, y2), color);
          line.setRotation(angle);                                     // Rotate it to the required angle
          xmlSketch.add(line);                                         // Add it to the sketch model

          // Reset data values for next time around
          resetLocalStores();
          x2 = y2 = 0;
          break;

        case "rectangle":
          Element.Rectangle rect = new Element.Rectangle(position, new Point(position.x + width, position.y + height), color);
          rect.setRotation(angle);                                     // Rotate it to the required angle
          xmlSketch.add(rect);                                         // Add it to the sketch model

          // Reset data values for next time around
          resetLocalStores();
          width = height = 0;
          break;

        case "circle":
          // The Circle constructor expects the center point and a point on the circumference
          int radius = diameter/2;
          position.x += radius;                                        // Center x coordinate
          position.y += radius;                                        // Center y coordinate
          Element.Circle circle = new Element.Circle(position,
                                                     new Point(position.x, position.y + radius),
                                                     color);
          circle.setRotation(angle);                                   // Rotate it to the required angle
          xmlSketch.add(circle);                                       // Add it to the sketch model

          // Reset data values for next time around
          resetLocalStores();
          diameter = 0;
          break;

        case "curve":
          // Create initial 2-point curve
          Point p = points.elementAt(0);
          p.x += position.x;
          p.y += position.y;
          Element.Curve curve = new Element.Curve(position, p, color);

          // Add the other points
          for(int i = 1 ; i < points.size() ; ++i) {
            p = points.elementAt(i);
            p.x += position.x;
            p.y += position.y;
            curve.modify(position, p);
          }

          curve.setRotation(angle);                                    // Rotate it to the required angle
          xmlSketch.add(curve);                                        // Add it to the sketch model

          // Reset data values for next time around
          resetLocalStores();
          points.clear();                                              // Remove the points from the vector
          break;

        case "text":
          Element.Text text = new Element.Text(textStr.toString(),
                                               position, color, bounds, font, maxAscent);
          text.setRotation(angle);                                     // Rotate it to the required angle
          xmlSketch.add(text);                                         // Add it to the sketch model

          // Reset data values for next time around
          resetLocalStores();
          font = null;
          textStr.setLength(0);
          break;

        case "position": case "endpoint": case "bounds": case "font":
        case "color": case "point": case "string":
          // Do nothing...
          break;

        default:
          System.err.println("Invalid end element name from XML: " + localName);
          break;
      }
    }

    // Helper to reset basic data stores
    private void resetLocalStores() {
      position = null;
      color = null;
      bounds = null;
      angle = 0.0;
    }

    // This accumulates the text for a Text element.
    // Note that this method may be called several times for a single
    // piece of text so we assemble the text in a string buffer.
    public void characters(char[] ch, int start, int length) {
      textStr.append(ch, start, length);
    }

    public void error(SAXParseException spe){
      JOptionPane.showMessageDialog(SketcherFrame.this,
                                    "Error at line " + spe.getLineNumber()
                                  + "\n" + spe.getMessage(),
                                    "SAX Parser Error",
                                     JOptionPane.ERROR_MESSAGE);
    }

    public void warning(SAXParseException spe){
      JOptionPane.showMessageDialog(SketcherFrame.this,
                                    "Warning at line " + spe.getLineNumber()
                                  + "\n" + spe.getMessage(),
                                    "SAX Parser Warning",
                                     JOptionPane.ERROR_MESSAGE);
    }

    // Because we get data for a Sketcher element piecemeal, we need to save
    // it in these fields until we have what we need to create the element
    private SketcherModel xmlSketch = null;                            // The new sketch model

    // Items common to all elements
    private Point position = null;                                     // Stores an element position
    private Color color = null;                                        // Stores an element color
    private java.awt.Rectangle bounds = null;                          // Bounds for a text element
    private double angle = 0.0;                                        // Stores the rotation angle of an element

    private int x2 = 0, y2 = 0;                                        // Stores line end point coordinates

    private int diameter = 0;                                          // Stores diameter of a Circle element

    private int width = 0, height = 0;                                 // Height and width of a Rectangle element

    private Vector<Point> points = new Vector<>();                     // Holds points for Curve element

    private Font font = null;                                          // Font for a Text element
    private StringBuffer textStr = new StringBuffer();                 // The text for a Text element
    private int maxAscent = 0;                                         // Maximum ascent for text
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
      if(this == saveAction) {
        saveOperation();
        return;
      } else if(this == saveAsAction) {
       saveAsOperation();
       return;
      } else if(this == openAction) {
        // Save current sketch if we need to
        checkForSave();

        // Now open a sketch file
        Path file = showDialog(
                           "Open Sketch File",                         // Dialog window title
                           "Open",                                     // Button label
                           "Read a sketch from file",                  // Button tooltip text
                           sketchFilter,                               // File filter
                           null);                                      // No file selected
        if(file != null) {                                             // If a file was selected
          if(openSketch(file)) {                                       // ...then read it
            currentSketchFile = file;                                  // Success!
            setTitle(frameTitle + " - " + currentSketchFile);
            sketchChanged = false;
          }
        return;
        }
      } else if(this == newAction || this == closeAction){
        checkForSave();
        theApp.insertModel(new SketcherModel());                       // Insert new empty sketch
        currentSketchFile = null;                                      // No file for it
        setTitle(frameTitle);
        sketchChanged = false;                                         // Not changed yet
        return;
      } else if(this == printAction) {
        // Get a printing object
        if(printer == null) {                                          // See if there is a printer
          JOptionPane.showMessageDialog(SketcherFrame.this,
                                        "No default printer available.",
                                        "Printer Error",
                                        JOptionPane.ERROR_MESSAGE);
          return;
        }

        printJob.setPageable(theApp.getView());
        boolean printIt = true;

        // If it�s not a toolbar button
        if(!(e.getSource() instanceof JButton)) {
          printIt = printJob.printDialog();                            // ...display the print dialog
        }

        if(printIt) {                                                  // If printIt is true...
          try {
            printJob.print();                                          // ...then print
          } catch(PrinterException pe) {
            System.err.println(pe);
            JOptionPane.showMessageDialog(SketcherFrame.this,
                                          "Error printing a sketch.",
                                          "Printer Error",
                                          JOptionPane.ERROR_MESSAGE);
          }
        }
      } else if(this == exitAction) {
        checkForSave();
        System.exit(0);
      } else if(this == exportAction) {
        exportXMLOperation();                                          // Export sketch as XML

      } else if(this == importAction) {
        importXMLOperation();                                          // Import an XML sketch
      }
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
          toolBar.repaint();                                           // Repaint the toolbar
        }
      } else {
          // This is just a standard color change
          elementColor = color;
      }
      statusBar.setColorPane(elementColor);                            // Update the status bar
      setChecks(colorMenu, e.getSource());                             // Set Color menu checks
    }

    private Color color;
  }

  // File actions
  private FileAction newAction,    openAction,   closeAction,
                     saveAction,   saveAsAction, printAction,
                     exportAction, importAction, exitAction;
  private FileAction[] fileActions;                                    // File actions as an array

  // Element type actions
  private TypeAction lineAction, rectangleAction, circleAction, curveAction, textAction;
  private TypeAction[] typeActions;                                    // Type actions as an array

// Element color actions
  private ColorAction redAction, yellowAction,greenAction, blueAction, customAction;
  private ColorAction[] colorActions;                                  // Color actions as an array

  private JMenuBar menuBar = new JMenuBar();                           // Window menu bar
  private JMenu elementMenu;                                           // Elements menu
  private JMenu colorMenu;                                             // Color menu
  private JMenu optionsMenu;                                           // Options menu

  private StatusBar statusBar = new StatusBar();                       // Window status bar
  private FontDialog fontDlg;                                          // The font dialog

  private JMenuItem aboutItem;                                         // About menu item
  private JMenuItem fontItem;                                          // Font chooser menu item

  private JPopupMenu popup = new JPopupMenu("General");                // Window pop-up
  private Color elementColor = DEFAULT_ELEMENT_COLOR;                  // Current element color
  private Color customColor = DEFAULT_ELEMENT_COLOR;                   // Current custom color
  private int elementType = DEFAULT_ELEMENT_TYPE;                      // Current element type
  private Font textFont = DEFAULT_FONT;                                // Default font for text elements
  private JToolBar toolBar = new JToolBar();                           // Window toolbar

  private String frameTitle;                                           // Frame title
  private Path currentSketchFile;                                      // Current sketch file on disk
  private boolean sketchChanged = false;                               // Model changed flag
  private JFileChooser fileChooser;                                    // File chooser dialog
  private ExtensionFilter sketchFilter = new ExtensionFilter(".ske", "Sketch files (*.ske)");
  private ExtensionFilter xmlFileFilter = new ExtensionFilter(".xml", "XML Sketch files (*.xml)");

  private PrinterJob printJob;                                         // The current printer job
  private PageFormat pageFormat;                                       // The printing page format
  private PrintService printer;                                        // The printer to be used
  private HashPrintRequestAttributeSet printAttr = new  HashPrintRequestAttributeSet();

  private Sketcher theApp;                                             // The application object
}
