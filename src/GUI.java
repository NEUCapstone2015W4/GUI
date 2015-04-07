import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class GUI extends SerialClass
{

	// ----- Fields
	
	JFrame gui;
	JFrame popup;
	JPanel panel;
	JPanel panelcont;
	//Keypad keys;
	CrossKeypad keys;
	
	Entrybox text;
	SerialClass obj;
	calibration box;
	
	
	// ----- Main
	
	public static void main(String[] args) 
	{
		// Create an instance of the GUI
		new GUI();
	}
	
	// ----- Constructor
	
	public GUI() 
	{
	
		// Set cross-platform look & feel
		
		try 
		{
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} 
		catch (ClassNotFoundException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		catch (InstantiationException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		catch (IllegalAccessException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		catch (UnsupportedLookAndFeelException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// Initialize fields
		gui = new JFrame();
		panel = new JPanel();
		//keys = new Keypad();
		keys = new CrossKeypad();
		text = new Entrybox();
		obj = new SerialClass();
		obj.initialize();
		popup= new JFrame();
		box = new calibration();
		// Setting up calibration frame
		popup.add(box);
		popup.setSize(1000,1000);
		popup.setTitle("Calibration Screen");
		popup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		popup.setLocationRelativeTo(null);
		popup.setAlwaysOnTop(true);
		
		// Make sure the program exits when the frame closes 
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setTitle("EOG Keyboard"); 
		gui.setExtendedState(JFrame.MAXIMIZED_BOTH);
		// Add the various components
		//gui.add(box);
		gui.add(keys.getHandle(), BorderLayout.SOUTH);
		gui.add(text.getHandle(), BorderLayout.NORTH);
		gui.add(panel);
		

		
		// DEBUG - set up button handling. Will soon switch
		// to USB communications
		box.getInputMap().put(KeyStroke.getKeyStroke("B"),"closeframe");
		box.getActionMap().put("closeframe",closeframe);
		panel.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "moveRight");
		panel.getActionMap().put("moveRight", moveRight);
		panel.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "moveLeft");
		panel.getActionMap().put("moveLeft", moveLeft);
		panel.getInputMap().put(KeyStroke.getKeyStroke("UP"), "moveUp");
		panel.getActionMap().put("moveUp", moveUp);
		panel.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "moveDown");
		panel.getActionMap().put("moveDown", moveDown);
		panel.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "select");
		panel.getActionMap().put("select", select);
		panel.getInputMap().put(KeyStroke.getKeyStroke("TAB"), "autocomp");
		panel.getActionMap().put("autocomp", autocomp);

		// This will center the JFrame in the middle of the screen 		
		gui.setLocationRelativeTo(null);
		
		
		// Make sure the JFrame is visible 
		gui.setVisible(true); 
		popup.setVisible(true);
		
	}
	
	// ----- GUI Actions
	
	private Action moveUp = new AbstractAction() {
	    public void actionPerformed(ActionEvent e) 
	    {	//keys returns a bool if true move the direction
	    	//if false then do select
	        keys.moveUp();
	    }
	};
	
	private Action moveDown = new AbstractAction() {
	    public void actionPerformed(ActionEvent e) 
	    {
	        keys.moveDown();
	    }
	};
	
	private Action moveLeft = new AbstractAction() {
	    public void actionPerformed(ActionEvent e) 
	    {
	        keys.moveLeft();
	    }
	};
	
	private Action moveRight = new AbstractAction() {
	    public void actionPerformed(ActionEvent e) 
	    {
	        keys.moveRight();
	    }
	};
	
	private Action select = new AbstractAction() {
	    public void actionPerformed(ActionEvent e) 
	    {
	        text.typeChar(keys.Press());
	    }
	};
	
	private Action autocomp = new AbstractAction() {
	    public void actionPerformed(ActionEvent e) 
	    {
	        text.autoComplete();
	    }
	};
//Close action for calibration screen
	private Action closeframe = new AbstractAction() {
		public void actionPerformed(ActionEvent e)
		{
			popup.dispose();
		}
	};

}
