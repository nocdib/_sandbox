// Chapter 17 Exercise 6

/*
 In a SpringLayout each component is constrained by a SpringLayout.Constraints object
 that defines four Spring objects that constrain the top left corner of the component
 (the x and y constraints, corresponding to the west and north edges), and its width
 and height (corresponding to the width and height constraints).

 Laying out components in a container using a SpringLayout therefore involves defining
 the four String objects that are to constrain each component. The strength of each set
 of four springs will determine the position and size of the component to which they
 apply.

 Note that the container itself also has a SpringLayout.Constraints object that
 determines its width and height. If these are not set the container will be collapsed
 when it is displayed so the components it contains will not be visible.
*/

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import javax.swing.SpringLayout;
import javax.swing.Spring;
import java.awt.Toolkit;
import java.awt.Container;

@SuppressWarnings("serial")
public class SpringTriangle extends JFrame {
  public SpringTriangle(String title) {
    super(title);

    setDefaultCloseOperation(EXIT_ON_CLOSE);
    createSpringTriangle();

    // Layout the window and its contents according to the constraints
    // Since we have not set the size of the window, the size will be determined
    // by the springs applied to the content pane and its components
    pack();
    setVisible(true);
  }

  // Create the traingle of buttons within the content pane
  private void createSpringTriangle() {
    SpringLayout layout = new SpringLayout();                                  // Create a layout manager
    Container content = getContentPane();                                      // Get the content pane
    content.setLayout(layout);                                                 // Content pane layout mgr as SpringLayout

    // Create the springs that we will use
    Spring xSpring = Spring.constant(5,15,25);                                 // Spring x
    Spring twoX = Spring.sum(xSpring,xSpring);                                 // Spring that is 2x
    Spring wSpring = Spring.constant(25,50,75);                                // Spring w
    Spring ySpring = Spring.constant(10,30, 50);                               // Spring y for button height
    Spring twoW = Spring.sum(wSpring,wSpring);                                 // Spring that is 2w for button width


    // Create array of six buttons and add them to the content pane
    // Retrieve the spring constraint object for each and set the width and height constraintss
    JButton[] buttons = new JButton[6];
    SpringLayout.Constraints[] buttonConstr = new SpringLayout.Constraints[buttons.length];

    for(int i = 0 ; i < buttons.length ; ++i) {
      buttons[i] = new JButton("Press " + (i+1));                              // Create a button
      content.add(buttons[i]);                                                 // and add it to content pane

      // Get the constraints object for each button
      buttonConstr[i] = layout.getConstraints(buttons[i]); // Get constraints object

      // If you want the layout manager to adjust the width & height
      // of the buttons as the window size changes, uncomment the next two statements.
      //buttonConstr[i].setWidth(twoW);                                        // Width constraint is 2w
      //buttonConstr[i].setHeight(ySpring);                                    // Height constraint is y
    }

    // Get the constraint object for the content pane
    SpringLayout.Constraints constr = layout.getConstraints(content);

    // SET VERTICAL CONSTRAINTS ON BUTTONS:

    // Tie the top button (button 5) to the top edge of the container
    // This sets thge vertical position of the top row relative to the container
    buttonConstr[5].setY(ySpring);                                             // Constraint = y

    // Tie the top of buttons 3 & 4 in the middle row to the bottom of button 5 at the top
    // This sets the vertical position of the middle row relative to the top row
    layout.putConstraint(SpringLayout.NORTH,buttons[3],ySpring,SpringLayout.SOUTH, buttons[5]);
    layout.putConstraint(SpringLayout.NORTH,buttons[4],ySpring,SpringLayout.SOUTH, buttons[5]);

    // Tie the top of the buttons in the bottom row to the bottom of the first buttom
    // in the middle row, Spring is equivalent to y.
    // This sets the vertical position of the bottom row relative to the middle row
    for(int i = 0 ; i < 3 ; ++i) {
      layout.putConstraint(SpringLayout.NORTH,buttons[i],ySpring,SpringLayout.SOUTH, buttons[3]);
    }

    // SET HORIZONTAL CONSTRAINTS ON BUTTONS:

    // Set horizontal contraint on 1st button in middle row with spring = 3x+w to position
    // the button midway between the 1st two buttons on the bottom row - buttons 0 and 1.
    buttonConstr[3].setX(Spring.sum(Spring.sum(twoX, xSpring), wSpring));      // Constraint = 3x+w

    // Tie adjacent buttons together horizontally: 0 & 1 and 1 & 2 in the bottom row,
    // and 3 & 4 in the middle row, with Spring equivalent to 2x between each pair
    layout.putConstraint(SpringLayout.WEST,buttons[1],twoX,SpringLayout.EAST, buttons[0]);
    layout.putConstraint(SpringLayout.WEST,buttons[2],twoX,SpringLayout.EAST, buttons[1]);
    layout.putConstraint(SpringLayout.WEST,buttons[4],twoX,SpringLayout.EAST, buttons[3]);

    // Make horizontal position of button 5 same as button 1 in middle of bottom row
    layout.putConstraint(SpringLayout.WEST,buttons[5],0,SpringLayout.WEST, buttons[1]);

    // Tie 1st button in bottom row to left of container with spring value 2x
    buttonConstr[0].setX(twoX);

    // SET CONSTRAINTS ON THE CONTAINER - THE CONTENT PANE
    /* We want the constraint on the container width to be the constraint between
       its WEST edge and the EAST edge of button 2(the last button in the bottom row)
       plus the constraint between its WEST edge and the WEST edge of button 0 in the
       bottom row.
       We want the constraint on the height to be the constraint between its NORTH
       edge and the SOUTH edge of button 0 (bottom row) plus the constraint between
       its NORTH edge and the NORTH edge of button 5 in the top row.
       This should ensure sufficient width and height to accommodate all the buttons
       and that the buttons are equidistant from the top and bottom, and left and
       right of the content pane when the window is resized.
     */
    Spring spring = Spring.sum(buttonConstr[2].getConstraint(SpringLayout.EAST),
                               buttonConstr[0].getConstraint(SpringLayout.WEST));
    constr.setConstraint(SpringLayout.EAST, spring);
    spring = Spring.sum(buttonConstr[0].getConstraint(SpringLayout.SOUTH),
                        buttonConstr[5].getConstraint(SpringLayout.NORTH));
    constr.setConstraint(SpringLayout.SOUTH, spring);
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
            public void run() {
              SpringTriangle window = new SpringTriangle("This is a Spring Layout");
            }
        });
  }
}