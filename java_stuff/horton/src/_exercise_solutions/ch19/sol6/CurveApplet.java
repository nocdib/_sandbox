// Chapter 19 Exercise 6
/*
 Modify the previous example to ensure that the curve is continuous —
 this implies that the control points on either side of an interior point,
 and the interior point itself, should be on a straight line.
 Allow control points to be dragged with the mouse, but still maintain the continuity of the curve.
 */

/*
  Most of the work was done in the previous exercise solution. Here, all we need
  to do is ensure that moving markers does not make the curve discontinuous.

  When a control marker is moved, we need to ensure that the control markers either
  side of a defining point remain be the same distance from the defining point
  and the control markers and the defining point are collinear.

  When a defining point is moved, we must ensure that the control markers that apply
  to the segment(s) either side of the point are moved the same distance.
  One limitation on function here is that you can't change the first or last control
  points because they are hidden by the defining points. A simple send-to-back
  operation would fix this though.
*/


import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.border.EtchedBorder;
import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Vector;

@SuppressWarnings("serial")
public class CurveApplet extends JApplet {
  // Initialize the applet
  @Override
  public void init() {
    pane = new CurvePane();                                            // Create pane containing curves
    Container content = getContentPane();                              // Get the content pane

    // Add the pane displaying the curves to the content pane for the applet
    content.add(pane);                                                 // BorderLayout.CENTER is default position
    MouseHandler handler = new MouseHandler();                         // Create the listener
    pane.addMouseListener(handler);                                    // Monitor mouse button presses
    pane.addMouseMotionListener(handler);                              // as well as movement

    // Create the panel to hold the buttons
    JPanel buttonPane = new JPanel();
    buttonPane.setLayout(new FlowLayout());
    buttonPane.setBackground(Color.DARK_GRAY);
    buttonPane.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED,
                                                      Color.BLUE,
                                                      Color.CYAN));

    // Create and add the button to clear the display
    JButton button = new JButton("Clear Pane");
    button.setBorder(BorderFactory.createRaisedBevelBorder());
    button.addActionListener(new ActionListener(){    // Action listener for the button
      public void actionPerformed(ActionEvent e) {
        clearPane();
      }
    });
    buttonPane.add(button);

    // Create and add the button to end the definition of the curve
    button  = new JButton("End Curve");
    button.setBorder(BorderFactory.createRaisedBevelBorder());
    button.addActionListener(new ActionListener(){   // Action listener for the button
      public void actionPerformed(ActionEvent e) {
      curveComplete = true;
      pane.repaint();
      }
    });

    buttonPane.add(button);
    content.add(buttonPane, BorderLayout.SOUTH);
  }

  // Method to clear the screen ready for a new set of points
  private void clearPane() {
    pointMarkers = new Vector<Marker>();   // Remove the point markers
    curveSegments = null;                  // and the array of curve segments
    curveComplete = false;                 // Reset curve complete flag
    repaint();
  }

  private void createCurves() {
    // Create the curves
    int n = pointMarkers.size();
    Point2D.Double[] points = new Point2D.Double[n];

    // Extract the points from the vector of Marker objects
    for(int i = 0 ; i<n ; i++)
      points[i] = pointMarkers.elementAt(i).getCenter();

    int m =  (n - 2);             // Number of coeffients for calculating control vectors
    double[] f = new double[m+1]; // Array used to calculate coefficients, a
    f[0] = 1.0;
    f[1] = -4 + n%2 ;            // -3 when n is odd and -4 when n is even
    for(int j = 2 ; j<f.length ; j++)
      f[j] = -4.0*f[j-1] - f[j-2];

    // Calculate the coefficients. This array could be limited to 5 elements
    // since the values of elements of a beyond a[4] are very small.
    double[] a = new double[m];
    double factor = 1.0/f[f.length-1];
    for(int j = 0 ; j<a.length ; j++)
      a[j] = -f[m-j-1]*factor;

    // Calculate control vectors, one for each point. The control points
    // for the curve are calculated by adding and/or subtracting the control
    // vector from the corresponding curve point.
    controlVec = new Point2D.Double[n];
    controlVec[0] = new Point2D.Double(0.0, 0.0);
    controlVec[n-1] = controlVec[0];
    for(int i = 1 ; i < n-1 ; ++i) {
      double x = 0.0;
      double y = 0.0;
      int n1 = 0;
      int n2 = 0;
      for(int k = 0 ; k < a.length ; ++k) {
        n1 = (i + k + 1)%n;
        n2 = Math.abs( i- k - 1);
        x += a[k]*(points[n1].x - points[n2].x);
        y += a[k]*(points[n1].y - points[n2].y);
      }
      controlVec[i] = new Point2D.Double(x, y);
    }

    // Define the curve segments using the defining points and control points
    // created using the vectors in the controlVec array
    curveSegments = new CubicCurve2D.Double[n-1];
    controlMarkers = new Marker[2*curveSegments.length];
    Point2D.Double controlPt1 = null;
    Point2D.Double controlPt2 = null;
    int j = 0;
    for(int i = 0 ; i < curveSegments.length ; ++i) {
      curveSegments[i] = new CubicCurve2D.Double();
      controlPt1 = new Point2D.Double(points[i].x+controlVec[i].x,
                                      points[i].y+controlVec[i].y);
      controlPt2 = new Point2D.Double(points[i+1].x-controlVec[i+1].x,
                                      points[i+1].y-controlVec[i+1].y);
      curveSegments[i].setCurve(points[i], controlPt1, controlPt2, points[i+1]);
      controlMarkers[j++] = new Marker(controlPt1, Color.RED);
      controlMarkers[j++] = new Marker(controlPt2, Color.RED);
    }
  }

  // Class defining a pane on which to draw
  class CurvePane extends JComponent {
    // Constructor
    public void paint(Graphics g) {
      Graphics2D g2D = (Graphics2D)g;                   // Get a 2D device context

      // If there are any curves, draw them in green
      if(curveSegments != null) {
        g2D.setColor(Color.GREEN);
        for(CubicCurve2D.Double curveSegment : curveSegments) {
          g2D.draw(curveSegment);
        }
      }

      // Draw the point markers
      for(Marker pointMarker : pointMarkers) {
        pointMarker.draw(g2D);
      }

      // If the curve is finished, draw the control markers and tangents
      if(curveComplete) {
        for(Marker controlMarker : controlMarkers) {
          controlMarker.draw(g2D);
        }

        // Draw the tangent lines
        Line2D.Double tangent = null;
        g2D.setColor(Color.RED);
        for(CubicCurve2D.Double curveSegment : curveSegments) {
          tangent = new Line2D.Double(curveSegment.getP1(),curveSegment.getCtrlP1());
          g2D.draw(tangent);
          tangent = new Line2D.Double(curveSegment.getP2(),curveSegment.getCtrlP2());
          g2D.draw(tangent);
        }
      }
    }
  }

  // Inner class defining a control point marker
  class Marker {
    public Marker(Point2D.Double point, Color color)  {
      center = point;                                                          // Save point as circle center
      this.color = color;

      // Create circle around control point
      circle = new Ellipse2D.Double(center.x-radius, center.y-radius, 2.0*radius, 2.0*radius);
    }

      // Draw the marker
      public void draw(Graphics2D g2D) {
        g2D.setColor(color);
        g2D.draw(circle);
      }

     // Get center of marker - the control point position
      Point2D.Double getCenter() {
        return center;
    }

    // Test if a point x,y is inside the marker
    public boolean contains(double x, double y) {
      return circle.contains(x,y);
    }

    // Sets a new control point location
    public void setLocation(double x, double y) {
      center.x = x;                                                            // Update control point
      center.y = y;                                                            // coordinates
      circle.x = x - radius;                                                   // Change circle position
      circle.y = y - radius;                                                   // correspondingly
    }

    Ellipse2D.Double circle;                                                   // Circle around control point
    Point2D.Double center;                                                     // Circle center - the control point
    Color color;                                                               // Color of this marker, controls red, curve pts blue
    static final double radius = 3;                                            // Radius of circle
  }

 private  class MouseHandler extends MouseInputAdapter {
    // In this method we are defining a new curve point when curveComplete is false
    // or potentially selecting a control or defining point when curveComplete is true.
    @Override
    public void mousePressed(MouseEvent e) {
      Point p = e.getPoint();
      if(curveComplete) {
        // Check if the cursor is inside any marker
        // First check the point markers
        int i = 0;
        for(Marker pointMarker : pointMarkers) {
          if(pointMarker.contains(p.x, p.y)) {
            selected = pointMarker;
            selectedIndex = i;
            return;
          }
          ++i;
        }

        // It was not a point marker so check the control markers
        i = 0;
        for(Marker controlMarker : controlMarkers) {
          if(controlMarker.contains(p.x, p.y)) {
            selected = controlMarker;
            selectedIndex = i;
            return;
          }
          ++i;
        }
      } else {
        // We are adding a new defining point
        pointMarkers.add(new Marker(new Point2D.Double(p.x,p.y), Color.BLUE));
        if(pointMarkers.size() > 3) {
          createCurves();
        }
        pane.repaint();
      }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
      selected = null;                                                         // Deselect any selected marker
    }

    // If selected is not null, this method moves the selected marker
    // and modifies the curve segments that are affected.
    @Override
    public void mouseDragged(MouseEvent e) {
      if(selected != null) {                                                   // If a marker is selected
        // Set the marker to current cursor position
        selected.setLocation(e.getX(), e.getY());
        if(selected == controlMarkers[selectedIndex]){
          // It must be a control marker so update it in the curve
          if(selectedIndex%2 == 0) {
            // It is the 1st control point that was changed
            curveSegments[selectedIndex/2].setCurve(curveSegments[selectedIndex/2].getP1(),
                                             selected.getCenter(),
                                             curveSegments[selectedIndex/2].getCtrlP2(),
                                             curveSegments[selectedIndex/2].getP2());

            /* For control points interior to the set of curve segments, we must adjust
               the control marker the other side of curve point. This will be the
               second marker for the preceding curve segment.

               There are two control markers for each point apart from the first and the
               last, so ifselectedIndex than the index for the first and less than the
               index for the last, we have another control marker and curve segment to
               adjust.
            */
            if(selectedIndex>0 && selectedIndex<controlMarkers.length-1) {
              Point2D.Double point = (Point2D.Double)(curveSegments[selectedIndex/2-1].getP2());
              double x = point.x + (point.x - selected.getCenter().x);
              double y = point.y + (point.y - selected.getCenter().y);
              controlMarkers[selectedIndex-1].setLocation(x, y);
              curveSegments[selectedIndex/2-1].setCurve(
                                             curveSegments[selectedIndex/2-1].getP1(),
                                             curveSegments[selectedIndex/2-1].getCtrlP1(),
                                             controlMarkers[selectedIndex-1].getCenter(),
                                             curveSegments[selectedIndex/2-1].getP2());
            }
          } else {
            // It is the 2nd control point that was changed
            curveSegments[selectedIndex/2].setCurve(curveSegments[selectedIndex/2].getP1(),
                                             curveSegments[selectedIndex/2].getCtrlP1(),
                                             selected.getCenter(),
                                             curveSegments[selectedIndex/2].getP2());
            // For interior control markers we must adjust the next one analogous
            // to the previous case
            if(selectedIndex>0 && selectedIndex<controlMarkers.length-1) {
              Point2D.Double point = (Point2D.Double)(curveSegments[selectedIndex/2+1].getP1());
              double x = point.x + (point.x - selected.getCenter().x);
              double y = point.y + (point.y - selected.getCenter().y);
              controlMarkers[selectedIndex+1].setLocation(x, y);
              curveSegments[selectedIndex/2+1].setCurve(
                                         curveSegments[selectedIndex/2+1].getP1(),
                                         controlMarkers[selectedIndex+1].getCenter(),
                                         curveSegments[selectedIndex/2+1].getCtrlP2(),
                                         curveSegments[selectedIndex/2+1].getP2());

            }
          }
        } else {
          // It must be a point marker. If the index is other than the first or
          // the last, two curve segments have to be updated
          double xDelta = 0.0;
          double yDelta = 0.0;
          Point2D point = null;
          if(selectedIndex>0) {
            // Change the segment where this is the second point
            point = curveSegments[selectedIndex-1].getP2();
            xDelta = selected.getCenter().getX() - point.getX();
            yDelta = selected.getCenter().getY() - point.getY();
            point = curveSegments[selectedIndex-1].getCtrlP2();
            controlMarkers[2*selectedIndex-1].setLocation(point.getX()+xDelta,
                                                         point.getY()+yDelta);
            curveSegments[selectedIndex-1].setCurve(curveSegments[selectedIndex-1].getP1(),
                                             curveSegments[selectedIndex-1].getCtrlP1(),
                                             controlMarkers[2*selectedIndex-1].getCenter(),
                                             selected.getCenter());
          }
          if(selectedIndex<curveSegments.length) {
            // Change the segment where this is the first point
            point = curveSegments[selectedIndex].getP1();
            xDelta = selected.getCenter().getX() - point.getX();
            yDelta = selected.getCenter().getY() - point.getY();
            point = curveSegments[selectedIndex].getCtrlP1();
            controlMarkers[2*selectedIndex].setLocation(point.getX()+xDelta, point.getY()+yDelta);
            curveSegments[selectedIndex].setCurve(selected.getCenter(),
                                             controlMarkers[2*selectedIndex].getCenter(),
                                             curveSegments[selectedIndex].getCtrlP2(),
                                             curveSegments[selectedIndex].getP2());
          }
        }
        pane.repaint();                                                        // Redraw pane contents
      }
    }

    private Marker selected = null;                                            // Stores reference to selected marker
    private int selectedIndex = 0;                                             // Index position of selected Marker
  }


  Vector<Marker> pointMarkers = new Vector<>();                                // Vector of Marker objects
  CubicCurve2D.Double[] curveSegments = null;                                  // Array of cubic curve segments
  Point2D.Double[] controlVec = null;                                          // Tangent vectors for control points
  Marker[] controlMarkers = null;                                              // Array of control point markers
  CurvePane pane = new CurvePane();                                            // Pane to contain curves
  boolean curveComplete = false;                                               // Indicator for complete curve
}
