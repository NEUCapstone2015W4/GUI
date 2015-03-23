import javax.swing.*;

import sun.font.TrueTypeFont;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;


//I went with a moving circle easier to use than drawing arrows note.  A little harder to make than I thought


public class calibration extends JPanel implements ActionListener, KeyListener{
	//setting a timer to wait for actions and to use to repaint the circle later
	Timer t = new Timer(1000,this);
	int count=0;
	// open up calibration to listen to the serial
	SerialClass obj=new SerialClass();
	//set up parameters for circle
	double x = 500, y = 500;
	//set initial label for panel
	
	public calibration(){
		//t.start();
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		obj.write("i\r\n");
	}

	
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

		g2.drawString(s, 325, 600);
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
			/*
			if (code == KeyEvent.VK_UP){
				uppotential();
				
			}
			if (code == KeyEvent.VK_DOWN){
				downpotential();
				
			}
			if (code == KeyEvent.VK_LEFT){
				leftpotential();
				
			}
			if (code == KeyEvent.VK_RIGHT){
				rightpotential();
				
			}
			*/
			}

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
		}


		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			}
		
		//Functions to change where the circle will be drawn
		//Also used to change label contents
		//After the last 'r' is recieved should close out of panel and into GUI
		private void rightpotential() {
			// TODO Auto-generated method stub
			//want to close out of calibration and go into GUI
			//Need to figure out how to automate the close of the calibration
			obj.write("ok\r\n");
			}

		private void leftpotential() {
			// TODO Auto-generated method stub
			x= 900;
			y = 500;
			s="Please look to the right";
			repaint();
			try {
				Thread.sleep(5000);
				obj.write("r\r\n");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		private void downpotential() {
			// TODO Auto-generated method stub
			x= 100;
			y = 500;
			s="Please look to the left";
			repaint();
			try {
				Thread.sleep(5000);
				obj.write("l\r\n");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		private void uppotential() {
			// TODO Auto-generated method stub
			x = 500;
			y = 900;
			s="Please look towards down";
			repaint();
			try {
				Thread.sleep(5000);
				obj.write("d\r\n");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		private void restpotential() {
			// TODO Auto-generated method stub
			x = 500;
			y = 100;
			s="Please look towards up";
			repaint();
			try {
				Thread.sleep(5000);
				obj.write("u\r\n");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


//actionlistener to redraw circle in designated spot
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			repaint();
			
			
		}

		public static void main(String[] args) 
		{
			calibration box = new calibration();
			
			JFrame bigboy = new JFrame();
			bigboy.add(box);
			bigboy.setSize(1000,1000);
			bigboy.setVisible(true);
			bigboy.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}

}
