import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;

import gnu.io.CommPortIdentifier; 
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent; 
import gnu.io.SerialPortEventListener; 

import java.util.Enumeration;
import java.util.Observable;

import javax.swing.AbstractAction;
import javax.swing.Action;

public class SerialClass extends Observable implements SerialPortEventListener{
		SerialPort serialPort;
		public char ch;
		public int count;
		public void changechar(char ch){
			this.ch=ch;
			setChanged();
			notifyObservers(ch);
		}
		
	        /** The port we're normally going to use. */
		private static final String PORT_NAMES[] = { 
				//"/dev/tty.usbserial-A9007UX1", // Mac OS X
				//"/dev/ttyUSB0", // Linux
				"COM3", // Windows
		};
		/**
		* A BufferedReader which will be fed by a InputStreamReader 
		* converting the bytes into characters 
		* making the displayed results codepage independent
		*/
		public static BufferedReader input;
		/** The output stream to the port */
		public static OutputStream output;
		/** Milliseconds to block while waiting for port open */
		private static final int TIME_OUT = 2000;
		/** Default bits per second for COM port. */
		private static final int DATA_RATE = 115200;

		public void initialize() {
	        CommPortIdentifier portId = null;
			Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

			//First, Find an instance of serial port as set in PORT_NAMES.
			while (portEnum.hasMoreElements()) {
				CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
				for (String portName : PORT_NAMES) {
					if (currPortId.getName().equals(portName)) {
						portId = currPortId;
						break;
					}
				}
			}
			if (portId == null) {
				System.out.println("Could not find COM port.");
				return;
			}

			try {
				// open serial port, and use class name for the appName.
				serialPort = (SerialPort) portId.open(this.getClass().getName(),
						TIME_OUT);

				// set port parameters
				serialPort.setSerialPortParams(DATA_RATE,
						SerialPort.DATABITS_8,
						SerialPort.STOPBITS_1,
						SerialPort.PARITY_NONE);

				// open the streams
				input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
				output = serialPort.getOutputStream();

				// add event listeners
				serialPort.addEventListener(this);
				serialPort.notifyOnDataAvailable(true);
			} catch (Exception e) {
				System.err.println(e.toString());
			}
		}

		/**
		 * This should be called when you stop using the port.
		 * This will prevent port locking on platforms like Linux.
		 */
		public synchronized void close() {
			if (serialPort != null) {
				serialPort.removeEventListener();
				serialPort.close();
			}
		}

		/**
		 * Handle an event on the serial port. Read the data and print it.
		 */
		public synchronized void serialEvent(SerialPortEvent oEvent) {
			if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
				try {
					String inputLine=input.readLine();
					System.out.println(inputLine);
					if(inputLine.charAt(0) == 'r'){
						System.out.println("Will move right");
						try{
							Robot robot= new Robot();
							robot.keyPress(KeyEvent.VK_RIGHT);
							robot.delay(10);
							robot.keyRelease(KeyEvent.VK_RIGHT);
							count  = 0;
						}catch(AWTException e){
							e.printStackTrace();
						}
					}
					if(inputLine.charAt(0) == 'l'){
						System.out.println("Will move left");
						try{
							Robot robot= new Robot();
							robot.keyPress(KeyEvent.VK_LEFT);
							robot.delay(10);
							robot.keyRelease(KeyEvent.VK_LEFT);
							count = 0;
						}catch(AWTException e){
							e.printStackTrace();
						}
					}
					if(inputLine.charAt(0) == 'u'){
						System.out.println("Will move up");
						try{
							Robot robot= new Robot();
							robot.keyPress(KeyEvent.VK_UP);
							robot.delay(10);
							robot.keyRelease(KeyEvent.VK_UP);
							count = 0;
						}catch(AWTException e){
							e.printStackTrace();
						}
					}
					if(inputLine.charAt(0)== 'd'){
						System.out.println("Will move down");
						try{
							Robot robot= new Robot();
							robot.keyPress(KeyEvent.VK_DOWN);
							robot.delay(10);
							robot.keyRelease(KeyEvent.VK_DOWN);
							count = 0;
						}catch(AWTException e){
							e.printStackTrace();
						}
					
					}

					if(inputLine.charAt(0)== 'i'){
						count = count + 1;
						if(count ==30 ){
							try{
							Robot robot= new Robot();
							robot.keyPress(KeyEvent.VK_ENTER);
							robot.delay(500);
							robot.keyRelease(KeyEvent.VK_ENTER);
							
							}catch(AWTException e){
								e.printStackTrace();
							}
						}
						if(count==50){
							Robot robot= new Robot();
							robot.keyPress(KeyEvent.VK_P);
							robot.delay(500);
							robot.keyRelease(KeyEvent.VK_P);
						}
					
					}

					if(inputLine.charAt(0)== '1'){
							try{
							Robot robot= new Robot();
							robot.keyPress(KeyEvent.VK_A);
							robot.delay(500);
							robot.keyRelease(KeyEvent.VK_A);
							
							}catch(AWTException e){
								e.printStackTrace();
							}
						}
					if(inputLine.charAt(0)== '0'){
						try{
						Robot robot= new Robot();
						robot.keyPress(KeyEvent.VK_C);
						robot.delay(500);
						robot.keyRelease(KeyEvent.VK_C);
						
						}catch(AWTException e){
							e.printStackTrace();
						}
					}
				}
					catch (Exception e) {
					System.err.println(e.toString());
				}
			}
			// Ignore all the other eventTypes, but you should consider the other ones.
		}
		
		public synchronized void write(String data){
			 System.out.println("Sending: " + data);
			 try {
			 output.write(data.getBytes());
			 } catch (Exception e) {
			 System.out.println("Can't write to COM port");
			 }
		}
		public static void main(String[] args) throws Exception {
			SerialClass main = new SerialClass();
			main.initialize();
			Thread t=new Thread() {
				public void run() {
					//the following line will keep this app alive for 1000 seconds,
					//waiting for events to occur and responding to them (printing incoming messages to console).
					try {Thread.sleep(1000000);} catch (InterruptedException ie) {}
				}
			};
			t.start();
			System.out.println("Started");
		}
	
}
