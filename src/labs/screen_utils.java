package labs;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
//import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.JFrame;

public class screen_utils {

	//pencerenin tam oratada aılmasını sağlayan kodlar 
	//http://stackoverflow.com/questions/3081913/center-swing-windows
	  public static Rectangle getScreenBounds(Component top){
		    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    GraphicsDevice[] gd = ge.getScreenDevices();

		    if (top != null){
		        Rectangle bounds = top.getBounds();
		        int centerX = (int) bounds.getCenterX();
		        int centerY = (int) bounds.getCenterY();

		        for (GraphicsDevice device : gd){
		            GraphicsConfiguration gc = device.getDefaultConfiguration();
		            Rectangle r = gc.getBounds();
		            if (r.contains(centerX, centerY)){
		                return r;
		            }
		        }
		    }
		    return gd[0].getDefaultConfiguration().getBounds();
		}

		public void centerWindowOnScreen(Window windowToCenter){
		    Rectangle bounds = getScreenBounds(windowToCenter);
		    Point newPt = new Point(bounds.x + bounds.width / 2, bounds.y + bounds.height / 2);
		    Dimension componentSize = windowToCenter.getSize();
		    newPt.x -= componentSize.width / 2;
		    newPt.y -= componentSize.height / 2;
		    windowToCenter.setLocation(newPt);

		}
		
		//http://stackoverflow.com/questions/144892/how-to-center-a-window-in-java
		/*public static void centreWindowFrame(Window frame) {
		    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		    int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
		    int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
		    frame.setLocation(x, y);
		}*/
		
		
		//http://stackoverflow.com/questions/144892/how-to-center-a-window-in-java
		//unutma sttatic de nesne tanımlamadan gidersin 
		//	  public  void centerWindowFrame(JFrame frame) {
		  public  void centerWindowFrame(JFrame frame) {

	        //  Insets insets = frame.getInsets();
	        //  frame.setSize(new Dimension(insets.left + insets.right + 500, insets.top + insets.bottom + 250));
	          frame.setVisible(true);
	          frame.setResizable(false);

	          Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	          int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
	          int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
	          frame.setLocation(x, y);
	       }
		
}
