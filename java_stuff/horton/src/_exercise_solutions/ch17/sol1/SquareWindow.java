// Chapter 17 Exercise 1
/*
 Create an application with a square window in the center of the screen
 that is half the height of the screen by deriving your own window class from JFrame.
 */

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.Toolkit;
import java.awt.Dimension;

@SuppressWarnings("serial")
public class SquareWindow extends JFrame {
  public SquareWindow(String title) {
    super(title);

    Toolkit theKit = this.getToolkit();
    Dimension wndSize = theKit.getScreenSize();

    // Calculate window side length as half the screen height
    int size = wndSize.height/2;

    setBounds((wndSize.width - size)/2, (wndSize.height-size)/2,               // Position
                           size, size);                                        // Size

    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setVisible(true);
  }

   public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
            public void run() {
              SquareWindow myWindow = new SquareWindow("Chapter 17 Exercise 1 - Look, I'm square...");
            }
        });
   }
}