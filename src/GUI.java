
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GUI extends JFrame {
	private JFrame j= new JFrame("Keyboard GUI");
	private JPanel main= new JPanel();
	private JPanel ta = new JPanel();
	private JPanel btn=new JPanel();
	private JTextArea textarea;
	private JTextField textfield;
	private JButton[][] buttons;
	private static final String[][] key = {
			{"A", "Z", "E", "R", "T", "Y", "U", "I","O", "P"," ","1","2","3"}, 
	       {"Q", "S", "D", "F", "G", "H", "J", "K", "L", "M","!","4","5","6"}, 
	       {"W", "X", "C", "V", "B","N", ",", ";", ":", "=","'","7", "8","9"}};
	
	public GUI(){
		  super("Keyboard GUI");
	      textarea = new JTextArea(5,13);
	      //JScrollPane scrollPane = new JScrollPane(textArea);  need to work on this one
	      textarea.setEditable(false);
	      add(textarea, BorderLayout.SOUTH);
		  int row=3;
		  int col=14;
	      buttons = new JButton[row][col];
	      for (int i = 0; i < key.length; i++) {
	    	  btn=new JPanel(new GridLayout(4,15));
	         for (int j = 0; j < key[i].length; j++) {
	            final int curRow = i;
	            final int curCol = j;
	            buttons[i][j] = new JButton(key[i][j]);
	            buttons[i][j].putClientProperty("column", j);
	            buttons[i][j].putClientProperty("row", i);
	            buttons[i][j].putClientProperty("key", key[i][j]);
	            buttons[i][j].addKeyListener(enter);
	            buttons[i][j].addKeyListener(new KeyAdapter() {
	               @Override
	               public void keyPressed(KeyEvent e) {
	                  switch (e.getKeyCode()) {
	                  case KeyEvent.VK_UP:
	                     if (curRow > 0)
	                        buttons[curRow - 1][curCol].requestFocus();
	                     break;
	                  case KeyEvent.VK_DOWN:
	                     if (curRow < buttons.length - 1)
	                        buttons[curRow + 1][curCol].requestFocus();
	                     break;
	                  case KeyEvent.VK_LEFT:
	                     if (curCol > 0)
	                        buttons[curRow][curCol - 1].requestFocus();
	                     break;
	                  case KeyEvent.VK_RIGHT:
	                     if (curCol < buttons[curRow].length - 1)
	                        buttons[curRow][curCol + 1].requestFocus();
	                     break;
	                  default:
	                     break;
	                  }
	               }
	            });
	            btn.add(buttons[i][j]);
	         }
	         main.add(btn, BorderLayout.NORTH);
	      }
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        add(main);
	      
	        pack();
	        setVisible(true);
	        
	        
	   }

	   private KeyListener enter = new KeyAdapter() {
	      @Override
	      public void keyTyped(KeyEvent e) {
	         if (e.getKeyChar() == KeyEvent.VK_ENTER) {
	            ((JButton) e.getComponent()).doClick();
	            String word;
	            JButton btns = (JButton) e.getSource();
	            word =(String) btns.getClientProperty("key");
	            textarea.append(word);
	            
	         }
	      }
	   };
	   
	    public static void main(String[] args) {
	        EventQueue.invokeLater(new Runnable() {
	            @Override
	            public void run() {
	                GUI guI = new GUI();
	            }
	        });
	    }	   
	   
}//end of main class



