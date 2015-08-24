// Mouse event handler for a control button
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class ControlMouseHandler extends MouseAdapter {
  Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
  Cursor defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);

  // Handle mouse entering the control button
  public void mouseEntered(MouseEvent e) {
    e.getComponent().setCursor(handCursor);    // Switch to hand cursor
  }

  // Handle mouse exiting the control button
  public void mouseExited(MouseEvent e) {
    e.getComponent().setCursor(defaultCursor); // Change to default cursor
  }

}
