// Class defining a status bar
import javax.swing.*;
import java.awt.*;
import javax.swing.border.BevelBorder;
import static Constants.SketcherConstants.*;

@SuppressWarnings("serial")
class StatusBar extends JPanel {
  // Constructor
  public StatusBar() {
    setLayout(new FlowLayout(FlowLayout.LEFT, 10, 3));
    setBackground(Color.LIGHT_GRAY);
    setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
    setColorPane(DEFAULT_ELEMENT_COLOR);
    setTypePane(DEFAULT_ELEMENT_TYPE);
    add(colorPane);                                                     // Add color pane to status bar
    add(typePane);                                                      // Add type pane to status bar
  }

  // Set color pane contents
  public void setColorPane(Color color) {
    String text = null;                                                 // Text for the color pane
    Icon icon = null;                                                   // Icon to be displayed
    if(color.equals(Color.RED)) {
      text = "RED";
      icon = RED16;
    } else if(color.equals(Color.YELLOW)) {
      text = "YELLOW";
      icon = YELLOW16;
    } else if(color.equals(Color.GREEN)) {
      text = "GREEN";
      icon = GREEN16;
    } else if(color.equals(Color.BLUE)) {
      text = "BLUE";
      icon = BLUE16;
    } else {
      text = "CUSTOM";
      icon = CUSTOM16;
    }
    colorPane.setIcon(icon);
    colorPane.setText(text);                                            // Set the pane text
  }

  // Set type pane label
  public void setTypePane(int elementType) {
    String text = null;                                                 // Text for the type pane
    switch(elementType) {
      case LINE:
        text = "LINE";
        break;
      case RECTANGLE:
        text = "RECTANGLE";
        break;
      case CIRCLE:
        text = "CIRCLE";
        break;
      case CURVE:
        text = "CURVE";
        break;
        case TEXT:
        text = "TEXT";
        break;
      default:
        assert false;
    }
    typePane.setText(text);                                             // Set the pane text
  }

  // Panes in the status bar
  private StatusPane colorPane = new StatusPane("BLUE", BLUE16);;
  private StatusPane typePane = new StatusPane("LINE");

  // Class defining a status bar pane
  class StatusPane extends JLabel {
    // Constructor - text only
    public StatusPane(String text) {
      super(text, LEFT);
      setupPane();
    }

    // Constructor - text with an icon
    public StatusPane(String text, Icon icon) {
      super(text, icon, LEFT);
      setupPane();
    }

    // Helper method for use by constructors
    private void setupPane() {
      setBackground(Color.LIGHT_GRAY);                                  // Set background color
      setForeground(Color.BLACK);                                       // Set foreground color
      setFont(paneFont);                                                // Set the fixed font
      setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createBevelBorder(BevelBorder.LOWERED),           // Outside border
        BorderFactory.createEmptyBorder(0,5,0,3)));                     // Inside border
      setPreferredSize(new Dimension(80,20));
    }

    // Font for pane text
    private Font paneFont = new Font("Serif", Font.PLAIN, 10);
  }
}
