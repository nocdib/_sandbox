// Chapter 18 Exercise 2
/*
 Replace the action listener for the selection buttons in the Lottery applet with a mouse listener
 and use the mousePressed() method to update the selection with a new value.
 */

// Chapter 18 Exercise 3
 /*
 Modify the Lottery applet to implement the mouse listener for a selection button as an inner class to the Lottery class.
 */

 /*
  There's more than one way to do this. I chose to move the MouseHandler class inside
  the Lottery class as the simplest approach. That gives MoluseHandler objectsd access to the members
  of the Lottery class. A Selection button then has just one handler for everything. The Selection class
  no longer needs to be a listener so I removed the the interface implementation.
  This solution also completes Exercise 3.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;                                               // For random number generator
import java.util.Arrays;

@SuppressWarnings("serial")
public class Lottery extends JApplet {
  // Generate NUMBER_COUNT random selections from the VALUES array
  private static int[] getNumbers() {
    int[] numbers = new int[NUMBER_COUNT];                             // Store for the numbers to be returned
    int candidate = 0;                                                 // Stores a candidate selection
    for(int i = 0 ; i < NUMBER_COUNT ; ++i) {                          // Loop to find the selections

      search:
      // Loop to find a new selection different from any found so far
      while(true) {
        candidate = VALUES[choice.nextInt(VALUES.length)];
        for(int j = 0 ; j < i ; ++j) {                                 // Check against existing selections
          if(candidate==numbers[j]) {                                  // If it is the same
            continue search;                                           // get another random selection
          }
        }
       numbers[i] = candidate;
        break;                                                         // and go to find the next
      }
    }
    Arrays.sort(numbers);
    return numbers;                                                    // Return the selections
  }

  // Initialize the applet
  @Override
  public void init() {
    SwingUtilities.invokeLater(                                        // Create interface components
      new Runnable() {                                                 // on the event dispatching thread
        public void run() {
          createGUI();
        }
    });
  }

  // Create User Interface for applet
  public void createGUI() {
    // Set up the selection buttons
    Container content = getContentPane();
    content.setLayout(new GridLayout(0,1));                            // Set the layout for the applet

    // Set up the panel to hold the lucky number buttons
    JPanel buttonPane = new JPanel();                                  // Add the pane containing numbers

    // Let’s have a fancy panel border
    buttonPane.setBorder(BorderFactory.createTitledBorder(
                         BorderFactory.createEtchedBorder(Color.cyan,
                                                          Color.blue),
                                                          "Every One a Winner!"));

    int[] choices = getNumbers();                                      // Get initial set of numbers
    MouseHandler mouseHandler = new MouseHandler();                    // Create the listener
    for(int i = 0 ; i < NUMBER_COUNT ; ++i) {
      luckyNumbers[i] = new Selection(choices[i]);
      luckyNumbers[i].addMouseListener(mouseHandler);
      buttonPane.add(luckyNumbers[i]);
    }
    content.add(buttonPane);

    // Add the pane containing control buttons
   JPanel controlPane = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 10));

    // Add the two control buttons
    JButton button;                                                    // A button variable
    Dimension buttonSize = new Dimension(100,20);                      // Button size

    controlPane.add(button = new JButton("Lucky Numbers!"));
    button.setBorder(BorderFactory.createRaisedBevelBorder());
    button.addActionListener(new HandleControlButton(PICK_LUCKY_NUMBERS));
    button.setPreferredSize(buttonSize);

    controlPane.add(button = new JButton("Color"));
    button.setBorder(BorderFactory.createRaisedBevelBorder());
    button.addActionListener(new HandleControlButton(COLOR));
    button.setPreferredSize(buttonSize);

    content.add(controlPane);
  }

  // Set the button values in ascending order
  void orderButtonValues() {
    int[] numbers = new int[NUMBER_COUNT];
    for(int i = 0 ; i < NUMBER_COUNT ; ++i) {
      numbers[i] = luckyNumbers[i].getValue();
    }
    Arrays.sort(numbers);                                              // Get numbers in ascending sequence
    for(int i = 0 ; i < NUMBER_COUNT ; ++i) {
      luckyNumbers[i].setValue(numbers[i]);
    }
  }

  // Class defining custom buttons showing lottery selection
  private class Selection extends JButton {
    // Constructor
    public Selection(int value) {
      super(Integer.toString(value));                                  // Call base constructor and set the label
      this.value = value;                                              // Save the value
      setBackground(startColor);
      setBorder(BorderFactory.createRaisedBevelBorder());              // Add button border
      setPreferredSize(new Dimension(80,20));
    }

    // Set the value for the selection
    public void setValue(int value) {
      setText(Integer.toString(value));                                // Set value as the button label
      this.value = value;                                              // Save the value
    }

    // Get the current selection value
    public int getValue() {
      return value;
    }

    // Check the current choices
    boolean isCurrentSelection(int possible) {
      for(int i = 0 ; i < NUMBER_COUNT ; ++i) {                        // For each button
        if(luckyNumbers[i].getValue() == possible) {                   // check against possible
          return true;                                                 // Return true for any =
        }
      }
      return false;                                                    // Otherwise return false
    }

    private int value;                                                 // Value for the selection button
  }

  // Class defining a handler for a control button
  private class HandleControlButton implements ActionListener {
    // Constructor
    public HandleControlButton(int buttonID) {
      this.buttonID = buttonID;                                        // Store the button ID
    }

    // Handle button click
    public void actionPerformed(ActionEvent e) {
      switch(buttonID) {
        case PICK_LUCKY_NUMBERS:
          int[] numbers = getNumbers();                                // Get maxCount random numbers
          for(int i = 0 ; i < NUMBER_COUNT ; ++i) {
            luckyNumbers[i].setValue(numbers[i]);                      // Set the button VALUES
          }
          break;
        case COLOR:
          Color color = new Color(
                flipColor.getRGB()^luckyNumbers[0].getBackground().getRGB());
          for(int i = 0 ; i < NUMBER_COUNT ; ++i)
            luckyNumbers[i].setBackground(color);                      // Set the button colors
          break;
      }
    }

    private int buttonID;
  }

  // Mouse handler class now a nested class...
  // with the mousePressed() method implemented...
  private class MouseHandler extends MouseAdapter {
    Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
    Cursor defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);

    // Handle mouse entering the selection button
    @Override
    public void mouseEntered(MouseEvent e) {
      e.getComponent().setCursor(handCursor);                          // Switch to hand cursor
    }

    // Handle mouse exiting the selection button
    @Override
    public void mouseExited(MouseEvent e) {
      e.getComponent().setCursor(defaultCursor);                       // Change to default cursor
    }

    // Handle mouse press for the selection button
    @Override
    public void mousePressed(MouseEvent e) {
      Selection selection = (Selection)e.getComponent();               // Get the button for the event

      // Change this selection to a new selection
      int candidate = 0;
      while(true) {                                                    // Loop to find a different selection
        candidate = VALUES[choice.nextInt(VALUES.length)];
        if(!selection.isCurrentSelection(candidate)) {                 // If it is different
          break;                                                       // end the loop
        }
      }
      selection.value = candidate;
      orderButtonValues();                                             // We have one so set the button values
    }
  }

  final static int NUMBER_COUNT = 6;                                   // Number of lucky numbers
  final static int MIN_VALUE = 1;                                      // Minimum in range
  final static int MAX_VALUE = 49;                                     // Maximum in range
  final static int[] VALUES = new int[MAX_VALUE-MIN_VALUE+1];          // Array of possible VALUES
  static {                                                             // Initialize array
    for(int i = 0 ; i < VALUES.length ; ++i)
      VALUES[i] = i + MIN_VALUE;
  }

  // An array of custom buttons for the selected numbers
  private Selection[] luckyNumbers = new Selection[NUMBER_COUNT];
  final public static int PICK_LUCKY_NUMBERS = 1;                      // Select button ID
  final public static int COLOR = 2;                                   // Color button ID

  // swap colors
  Color flipColor = new Color(Color.YELLOW.getRGB()^Color.RED.getRGB());

  Color startColor = Color.YELLOW;                                     // start color

  private static Random choice = new Random();                         // Random number generator
}
