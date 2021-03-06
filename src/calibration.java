import javax.swing.*;

import sun.font.TrueTypeFont;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;


//I went with a moving circle easier to use than drawing arrows note.  A little harder to make than I thought


public class calibration extends JPanel implements KeyListener{
	//initalize control fields
	int count=0;
	boolean control=true;
	int good=0;


	//Setting up initial string drawn at calibration screen
	String s="Please look at the center";
	// open up calibration to listen to the serial port
	SerialClass obj=new SerialClass();
	//set up parameters for circle
	double x = 500, y = 500;
	int a = 350, b =600;
	
	//initializing calibration panel
	public calibration(SerialClass o){
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		//obj=o;
		obj.initialize();
	}
	
	//make circle
	public void paint(Graphics g){
		
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.fill(new Ellipse2D.Double(x, y, 50 , 50));
		
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        Font font = new Font("Times New Roman", Font.PLAIN, 36);
        g2.setFont(font);

		g2.drawString(s, a, b);
		//sending initial rest state to arduino after first circle is drawn to screen

		if (count==0){
				System.out.println("Calibrating idle");
				obj.write("i\r\n");
				Toolkit.getDefaultToolkit().beep();
				count=count+1;
				}
				

		}
	
	
	//keylistener to listen over the COM to direct movement for circle to move
		@Override
	public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			int code = e.getKeyCode();
			if (code == KeyEvent.VK_A){
				nextimage();
				good=1;
			}
			if (code == KeyEvent.VK_C){
				restart();
			}
			}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			}


		//mechanism to go to next direction in calibration process
		private void nextimage() {
			// TODO Auto-generated method stub
			count=count+1;
			if (count==2){
				uppotential();
			}
			if (count==3){
				downpotential();
			}
			if (count==4){
				leftpotential();
			}
			if(count==5){
				rightpotential();
			}
			if(count==6){
				finishcal();
			}
			if(count==7){
				closeimage();
			}
		}
		
		//restart whole calibration process if 0 is received from arduino
		private void restart() {
			// TODO Auto-generated method stub
			count=0;
			x=500;
			y=500;
			s="Please look at the center";
			a = 350;
			b = 600;
			repaint();
			System.out.println("Restarting because of 0 recieved\nCalibrating idle");
			
		}
		//Functions to change where the circle will be drawn
		//Also used to change label contents
		//After the last 'r' is recieved should close out of panel and into GUI
		private void uppotential() {
			control=true;
			// TODO Auto-generated method stub
			x = 500;
			y = 100;
			s="Please look up";
			a=400;
			b=600;
			repaint();
				System.out.println("calibrating up");
				obj.write("u\r\n");

				Toolkit.getDefaultToolkit().beep();

			
		}
		private void downpotential() {
			control=true;
			// TODO Auto-generated method stub
			x = 500;
			y = 900;
			s="Please look down";
			a= 400;
			b= 600;
			repaint();
				System.out.println("calibrating down");
				obj.write("d\r\n");

				Toolkit.getDefaultToolkit().beep();
		}
		private void leftpotential() {
			control=true;
			// TODO Auto-generated method stub
			x= 100;
			y = 500;
			s="Please look left";
			a= 400;
			b=600;
			repaint();
				System.out.println("calibrating left");
				obj.write("l\r\n");

				Toolkit.getDefaultToolkit().beep();
		
				
		
		}
		private void rightpotential() {
			control=true;
			// TODO Auto-generated method stub
			x= 900;
			y = 500;
			s="Please look right";
			a = 400;
			b = 600;
			repaint();
				System.out.println("calibrating right");
				obj.write("r\r\n");

				Toolkit.getDefaultToolkit().beep();
			
		}

		private void finishcal() {
			// TODO Auto-generated method stub
			x=500;
			y=500;
			s="Finished Calibration";
			repaint();
			
			obj.write("ok\r\n");
			}
			
		private void closeimage() {
			// TODO Auto-generated method stub
				Robot robot;
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					obj.write("clr\r\n");
				
				try {

					// stuff for second run calibration
					robot = new Robot();
					robot.keyPress(KeyEvent.VK_B);
					robot.keyRelease(KeyEvent.VK_B);
				} catch (AWTException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
}
