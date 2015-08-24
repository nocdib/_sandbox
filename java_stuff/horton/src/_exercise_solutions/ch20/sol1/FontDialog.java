// Class to define a dialog to choose a font
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import static Constants.SketcherConstants.*;

@SuppressWarnings("serial")
class FontDialog extends JDialog
                 implements ActionListener,                             // For buttons etc.
                            ListSelectionListener,                      // For list box
                            ChangeListener {                            // For the spinner
  // Constructor
  public FontDialog(SketcherFrame window) {
    // Call the base constructor to create a modal dialog
    super(window, "Font Selection", true);
    font = window.getFont();                                            // Get the current font

  // Create the dialog button panel
  JPanel buttonPane = new JPanel();                                     // Create a panel to hold buttons

  // Create and add the buttons to the buttonPane
  buttonPane.add(ok = createButton("OK"));                              // Add the OK button
  buttonPane.add(cancel = createButton("Cancel"));                      // Add the Cancel button
  getContentPane().add(buttonPane, BorderLayout.SOUTH);                 // Add pane

    // Code to create the data input panel
    JPanel dataPane = new JPanel();                                     // Create the data entry panel
    dataPane.setBorder(BorderFactory.createCompoundBorder(              // Pane border
                       BorderFactory.createLineBorder(Color.BLACK),
                       BorderFactory.createEmptyBorder(5, 5, 5, 5)));
    GridBagLayout gbLayout = new GridBagLayout();                       // Create the layout
    dataPane.setLayout(gbLayout);                                       // Set the pane layout
    GridBagConstraints constraints = new GridBagConstraints();

    // Create the font choice and add it to the input panel
    JLabel label = new JLabel("Choose a Font");
    constraints.fill = GridBagConstraints.HORIZONTAL;
    constraints.gridwidth = GridBagConstraints.REMAINDER;
    gbLayout.setConstraints(label, constraints);
    dataPane.add(label);

    // Code to set up font list choice component
    GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
    String[] fontNames = e.getAvailableFontFamilyNames();               // Get font names

    fontList = new JList<>(fontNames);                                  // Create list of font names
    fontList.setValueIsAdjusting(true);                                 // single event selection
    fontList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    fontList.setSelectedValue(font.getFontName(),true);
    fontList.addListSelectionListener(this);
    fontList.setToolTipText("Choose a font");
    JScrollPane chooseFont = new JScrollPane(fontList);                 // Scrollable list
    chooseFont.setMinimumSize(new Dimension(400,100));
    chooseFont.setWheelScrollingEnabled(true);                          // Enable mouse wheel scroll

    // Panel to display font sample
    JPanel display = new JPanel(true);
    fontDisplay = new JLabel("Sample Size: x X y Y z Z");
    fontDisplay.setFont(font);
    fontDisplay.setPreferredSize(new Dimension(350,100));
    display.add(fontDisplay);

    //Create a split pane with font choice at the top and font display at the bottom
    JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                               true,
                               chooseFont,
                               display);
    gbLayout.setConstraints(splitPane, constraints);                    // Split pane constraints
    dataPane.add(splitPane);                                            // Add to the data pane

    // Set up the size choice using a spinner
    JPanel sizePane = new JPanel(true);                                 // Pane for size choices
    label = new JLabel("Choose point size: ");                          // Prompt for point size
    sizePane.add(label);                                                // Add the prompt

    chooseSize = new JSpinner(new SpinnerNumberModel(font.getSize(),
                                  POINT_SIZE_MIN, POINT_SIZE_MAX, POINT_SIZE_STEP));
    chooseSize.setValue(font.getSize());                                // Set current font size
    chooseSize.addChangeListener(this);
    sizePane.add(chooseSize);

     // Add spinner to pane
    gbLayout.setConstraints(sizePane, constraints);                     // Set pane constraints
    dataPane.add(sizePane);                                             // Add the pane

    // Set up style options using radio buttons
    bold = new JRadioButton("Bold", (font.getStyle() & Font.BOLD) > 0);
    italic = new JRadioButton("Italic", (font.getStyle() & Font.ITALIC) > 0);
    bold.addItemListener(new StyleListener(Font.BOLD));                 // Add button listeners
    italic.addItemListener(new StyleListener(Font.ITALIC));
    JPanel stylePane = new JPanel(true);                                // Create style pane
    stylePane.add(bold);                                                // Add buttons
    stylePane.add(italic);                                              // to style pane...
    gbLayout.setConstraints(stylePane, constraints);                    // Set pane constraints
    dataPane.add(stylePane);                                            // Add the pane

    getContentPane().add(dataPane, BorderLayout.CENTER);
    pack();
    setVisible(false);
  }

  // Create a dialog button
  JButton createButton(String label) {
    JButton button = new JButton(label);                                // Create the button
    button.setPreferredSize(new Dimension(80,20));                      // Set the size
    button.addActionListener(this);                                     // Listener is the dialog
    return button;                                                      // Return the button
  }

  // Handler for button events
  public void actionPerformed(ActionEvent e) {
    if(e.getSource()== ok)  {                                           // If it's the OK button
      ((SketcherFrame)getOwner()).setFont(font);                        // ...set selected font
    } else {
      font = ((SketcherFrame)getOwner()).getFont();                     // Restore the current font
      fontDisplay.setFont(font);
      chooseSize.setValue(font.getSize());                              // Restore the point size
      fontList.setSelectedValue(font.getName(),true);
      int style = font.getStyle();
      bold.setSelected((style & Font.BOLD) > 0);                        // Restore the
      italic.setSelected((style & Font.ITALIC) > 0);                    // style options
    }
    // Now hide the dialog - for ok or cancel
    setVisible(false);
  }

  // List selection listener method
  public void valueChanged(ListSelectionEvent e) {
    if(!e.getValueIsAdjusting()) {
      font = new Font(fontList.getSelectedValue(), font.getStyle(), font.getSize());
      fontDisplay.setFont(font);
      fontDisplay.repaint();
    }
  }

  // Handle spinner state change events
  public void stateChanged(ChangeEvent e) {
    int fontSize = ((Number)(((JSpinner)e.getSource()).getValue())).intValue();
    font = font.deriveFont((float)fontSize);
    fontDisplay.setFont(font);
    fontDisplay.repaint();
  }

  // Iner class defining listeners for radio buttons
  class StyleListener implements ItemListener {
    public StyleListener(int style) {
      this.style = style;
    }

    // Event handler for font style changes
    public void itemStateChanged(ItemEvent e) {
      int fontStyle = font.getStyle();
      if(e.getStateChange()==ItemEvent.SELECTED) {                      // If style was selected
        fontStyle |= style;                                             // turn it on in the font style
      } else {
        fontStyle &= ~style;                                            // otherwise turn it off
      }
      font = font.deriveFont(fontStyle);                                // Get a new font
      fontDisplay.setFont(font);                                        // Change the label font
      fontDisplay.repaint();                                            // repaint
    }
     private int style;                                                 // Style for this listener
  }

  private JList<String> fontList;                                       // Font list
  private JButton ok;                                                   // OK button
  private JButton cancel;                                               // Cancel button
  private JRadioButton bold;                                            // Bold style button
  private JRadioButton italic;                                          // Italic style button
  private Font font;                                                    // Currently selected font
  private JSpinner chooseSize;                                          // Font size options
  private JLabel fontDisplay;                                           // Font sample
}
