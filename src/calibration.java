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
	int secondrun=1;
	// open up calibration to listen to the serial port
	SerialClass obj=new SerialClass();
	//set up parameters for circle
	double x = 500, y = 500;
	int a = 350, b =600;
	
	//initializing calibration panel
	public calibration(){
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		obj.initialize();
	}

	//Setting up initial string drawn at calibration screen
	String s="Please look at the center";
	
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
			try {
				Thread.sleep(5000);
				obj.write("i\r\n");
				//System.out.print("\0007");
				//System.out.flush();
				Toolkit.getDefaultToolkit().beep();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
				//restpotential();
			}
			if (code == KeyEvent.VK_C){
				restart();
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
			System.out.println("Restarting because of 0 recieved");
			
		}

		//mechanism to go to next direction in calibration process
		private void nextimage() {
			// TODO Auto-generated method stub
			count=count+1;
			if (count==1){
				restpotential();
			}
			if (count==2){
				uppotential();
			}
			if (count==3){
				downpotential();
			}
			if(count==4){
				leftpotential();
			}
			if(count==5){
				rightpotential();
			}
			if(count==6){
				closeimage();
			}
		}


		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			}
		
		//Functions to change where the circle will be drawn
		//Also used to change label contents
		//After the last 'r' is recieved should close out of panel and into GUI

		private void closeimage() {
			// TODO Auto-generated method stub
			
			if(secondrun==1){
				Robot robot;
				try {
					// stuff for second run calibration
					robot = new Robot();
					robot.delay(3000);
					robot.keyPress(KeyEvent.VK_B);
					robot.delay(500);
					robot.keyRelease(KeyEvent.VK_B);
					System.out.println("PRESSING B");
				} catch (AWTException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
			
		}

		private void rightpotential() {
			// TODO Auto-generated method stub
			x=500;
			y=500;
			s="Finished Calibration";
			repaint();
			
			obj.write("ok\r\n");
			obj.write("clr\r\n");
					
			if(secondrun==1){
					Robot robot;
					try {
						// stuff for second run calibration
						robot = new Robot();
						robot.delay(2000);
						robot.keyPress(KeyEvent.VK_A);
						robot.delay(500);
						robot.keyRelease(KeyEvent.VK_A);
						System.out.println("PRESSING A");
					} catch (AWTException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			}

		private void leftpotential() {
			// TODO Auto-generated method stub
			x= 900;
			y = 500;
			s="Please look right";
			a = 400;
			b = 600;
			repaint();
			try {
				Thread.sleep(5000);
				obj.write("r\r\n");

				Toolkit.getDefaultToolkit().beep();

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		private void downpotential() {
			// TODO Auto-generated method stub
			x= 100;
			y = 500;
			s="Please look left";
			a= 400;
			b=600;
			repaint();
			try {
				Thread.sleep(5000);
				obj.write("l\r\n");

				Toolkit.getDefaultToolkit().beep();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		private void uppotential() {
			// TODO Auto-generated method stub
			x = 500;
			y = 900;
			s="Please look down";
			a= 400;
			b= 600;
			repaint();
			try {
				Thread.sleep(5000);
				obj.write("d\r\n");

				Toolkit.getDefaultToolkit().beep();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		private void restpotential() {
			// TODO Auto-generated method stub
			x = 500;
			y = 100;
			s="Please look up";
			a=400;
			b=600;
			repaint();
			try {
				Thread.sleep(5000);
				obj.write("u\r\n");

				Toolkit.getDefaultToolkit().beep();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		/*
		public static void main(String[] args) 
		{
			calibration box = new calibration();
			
			JFrame bigboy = new JFrame();
			bigboy.add(box);
			bigboy.setSize(1000,1000);
			bigboy.setVisible(true);
			bigboy.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
*/
}
