import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JPanel;



// Implements a cross style of keys

public class CrossKeypad 
{
	
	// ----- Fields
	
	private Point cursor;
	private Key[][] keys = new Key[rows][cols];
	private JPanel grid;
	
	// ----- Local Statics
	private static final int rows = 9;
	private static final int cols = 19;
	private static final String[][] keyValues = {
	   {"F", "C", "H", "O", "T", "N", "D", "M"}, 
       {"Z", "X", "K", "B", "Y", "W", "L", "S", "A", "E","I","R","U","G","P","V","J","Q"}, 
	};
	
	// ----- Constructor
	
	public CrossKeypad ()
	{
		// Initialize cursor
	
		cursor = new Point(0,0);
		
		// Initialize the GUI object, adding each key to the grid
		
		grid = new JPanel(new GridLayout(rows,cols,4,4));
		grid.setFocusable(false);
		
		for (int i=0; i<rows; i++)
		{
			for (int j=0; j<cols; j++)
			{
				//statement for dead space in the middle
				if((j == Math.floor(cols/2)) && (i == Math.floor(rows/2)))
				{
				keys[i][j] = new Key("", i, j);
				grid.add(keys[i][j].getHandle());	
				}
				//adds vertical column of letters
				else if(j == Math.floor(cols/2))
				{
				keys[i][j] = new Key(keyValues[0][i], i, j);
				grid.add(keys[i][j].getHandle());
				}
				//adds horizontal column
				else if(i == Math.floor(rows/2))
				{
				keys[i][j] = new Key(keyValues[1][j], i, j);
				grid.add(keys[i][j].getHandle());
				}
				else
				{
				keys[i][j] = new Key("", i, j);
				grid.add(keys[i][j].getHandle());
				}
			}
		}
		
		// Select the initial key
		//TODO select [rows/2][cols/2]
		keys[rows/2][cols/2].Select();
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
		
		//case for center gives Left&Right Priority
		if((cursor.y == Math.floor(cols/2)) && (cursor.x == Math.floor(rows/2)))
		{
			x = rows/2;
		}
		else
		{
			// Wrap x
		
			if (cursor.y != Math.floor(cols/2))
			{
				x = rows/2;
			}
			else if (x >= rows)
			{
				x -= rows;
			}
			else if (x < 0)
			{
				x += rows;
			}
			
			// Wrap y
			
			if (cursor.x != Math.floor(rows/2))
			{
				y = cols/2;
			}
			else if (y >= cols)
			{
				y -= cols;
			}
			else if (y < 0)
			{
				y += cols;
			}
		}
		// Update cursor with valid position
		
		cursor.setLocation(x,y);
		
		//TODO add display of 2 new boxes on LR for vert traversal & UD for horiz traversal
		//TODO figure out how to display these new boxes
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
