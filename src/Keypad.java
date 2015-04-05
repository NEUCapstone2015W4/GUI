import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JPanel;



// Implements a full Keyboard of keys

public class Keypad 
{
	
	// ----- Fields
	
	private Point cursor;
	private Key[][] keys = new Key[rows][cols];
	private JPanel grid;
	
	// ----- Local Statics
	
	private static final int rows = 3;
	private static final int cols = 14;
	private static final String[][] keyValues = {
	   {"A", "Z", "E", "R", "T", "Y", "U", "I","O", "P"," ","1","2","3"}, 
       {"Q", "S", "D", "F", "G", "H", "J", "K", "L", "M","!","4","5","6"}, 
       {"W", "X", "C", "V", "B","N", ",", ";", ":", "=","'","7", "8","9"}};
	
	// ----- Constructor
	
	public Keypad ()
	{
		// Initialize cursor
	
		cursor = new Point(0,0);
		
		// Initialize the GUI object, adding each key to the grid
		
		grid = new JPanel(new GridLayout(rows,cols,5,5));
		grid.setFocusable(false);
		
		for (int i=0; i<rows; i++)
		{
			for (int j=0; j<cols; j++)
			{
				// Set up each button, add it to the keypad
				
				keys[i][j] = new Key(keyValues[i][j], i, j);
				grid.add(keys[i][j].getHandle());
			}
		}
		
		// Select the initial key
		
		keys[0][0].Select();
	}
	
	// ----- Get
	
	// Get the current key
	
	private Key getCurrentKey()
	{
		
		return keys[cursor.x][cursor.y];
	}
	
	// Get the current cursor position
	
	public Point getPosn()
	{
		
		return cursor;
	}
	
	// Get the GUI control handle
	
	public JPanel getHandle()
	{
		
		return grid;
	}
	
	// ----- Actions
	
	// Select the current key, return its value.
	
	public String Press()
	{
		
		return keys[cursor.x][cursor.y].getValue();
	}
	
	// Internal, add values to current positions. Wrap on edges.
	// (0,0) is the top left corner. (0,rows) is the bottom left corner.
	// (cols,0) is the top right corner, and (cols,rows) is the bottom right.
	
	private void movePosn(int dX, int dY)
	{
		// Determine the changes
		
		int x = cursor.x + dX;
		int y = cursor.y + dY;
		
		// Wrap x
		
		if (x < 0)
		{
			x += rows;
		}
		else if (x >= rows)
		{
			x -= rows;
		}
		
		// Wrap y
		
		if (y < 0)
		{
			y += cols;
		}
		else if (y >= cols)
		{
			y -= cols;
		}
		
		// Update cursor with valid position
		
		cursor.setLocation(x,y);
	}
	
	// Move left, wrapping handled. Changes the color of the
	// previous button back to normal and set the new button
	// to red.
	
	public void moveUp()
	{
		getCurrentKey().UnSelect();
		movePosn(-1,0);
		getCurrentKey().Select();
	}
	
	// Move right, wrapping handled. Changes the color of the
	// previous button back to normal and set the new button
	// to red.
	
	public void moveDown()
	{
		getCurrentKey().UnSelect();
		movePosn(1,0);
		getCurrentKey().Select();
	}
	
	// Move up, wrapping handled. Changes the color of the
	// previous button back to normal and set the new button
	// to red.
	
	public void moveLeft()
	{
		getCurrentKey().UnSelect();
		movePosn(0,-1);
		getCurrentKey().Select();
	}
	
	// Move up, wrapping handled. Changes the color of the
	// previous button back to normal and set the new button
	// to red.
	
	public void moveRight()
	{
		getCurrentKey().UnSelect();
		movePosn(0,1);
		getCurrentKey().Select();
	}
	
	// ----- GUI
	
}
