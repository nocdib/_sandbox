// Mouse event handler for a control button
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

// Mouse event handler for toolbar buttons
public class CursorHandler extends MouseAdapter {
  @Override
  public void mouseEntered(MouseEvent e) {
    e.getComponent().setCursor(handCursor);                            // Switch to hand cursor
  }

  @Override
  public void mouseExited(MouseEvent e) {
    e.getComponent().setCursor(defaultCursor);                         // Change to default cursor
  }

  Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
  Cursor defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);
}