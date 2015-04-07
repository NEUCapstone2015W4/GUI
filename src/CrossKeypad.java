import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JPanel;



// Implements a cross style of keys

public class CrossKeypad 
{
	
	// ----- Fields
	private Suggestion sugObject;
	private Point cursor;
	private Key[][] keys = new Key[rows][cols];
	private JPanel grid;
	
	// ----- Local Statics
	private static final int rows = 9;
	private static final int cols = 19;
	private static final String[][] keyValues = {
	   {"N", "P", "B", "I", " ", "A", "O", "M", "L"}, 
       {"X", "Q", "K", "U", "G", "R", "F", "H", "S", " ", "T", "W", "C", "D", "E", "Y", "V", "J", "Z"}, 
	};
	
	// ----- Constructor
	public CrossKeypad ()
	{
		// Initialize cursor
	
		cursor = new Point(rows/2,cols/2);
		
		// Initialize suggestion object so that the file read doesn't get called every time
		sugObject = new Suggestion();
		
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
				keys[i][j] = new Key(" ", i, j);
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
				keys[i][j] = new Key(" ", i, j);
				keys[i][j].setVisible(false);
				grid.add(keys[i][j].getHandle());
				}
			}
		}
		
		// Select the initial key
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
	
	// Internal, add values to current positions. Wrap when in one of the arms of the cross or at the ends.
	
	private boolean movePosn(int dX, int dY)
	{
		// Determine the changes
		
		int x = cursor.x + dX;
		int y = cursor.y + dY;
		
		// Bool value for returning selection
		
		boolean canMove = true;

		//case for center gives Left&Right Priority
		if((cursor.y == Math.floor(cols/2)) && (cursor.x == Math.floor(rows/2))
				&& (y != Math.floor(cols/2)))
		{
			x = rows/2;
		}
		else
		{
			// Wrap x
			if (cursor.y != Math.floor(cols/2))
			{
				if(dX != 0)
				{
					canMove = false;
				}
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
				if(dY != 0)
				{
					canMove = false;
				}
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
		addOptions(x,y);

		cursor.setLocation(x,y);
		
		return canMove;
	}
	
	
	private void addOptions(int nextX, int nextY)
	{
		//update if moving in X direction(up,down)
		if(nextX != cursor.x)
		{
			//makes sure to not delete main keys
			if(cursor.x != Math.floor(rows/2))
			{
				//makes previous 2 options invisible
				keys[cursor.x][cursor.y-1].setVisible(false);
				keys[cursor.x][cursor.y+1].setVisible(false);		
			}
			
			//makes sure not to overwrite main keys going back
			if(nextX != Math.floor(rows/2))
			{
			//makes next 2 keys new characters
			keys[nextX][nextY-1].setText("Sel");
			keys[nextX][nextY+1].setText("Aut");
			
			//makes next 2 keys visible
			keys[nextX][nextY-1].setVisible(true);
			keys[nextX][nextY+1].setVisible(true);
			}
		}
		
		//update if moving in Y direction(left,right)
		if(nextY != cursor.y)
		{
			//makes sure to not delete main keys
			if(cursor.y != Math.floor(cols/2))
			{
				//makes previous 2 options invisible
				keys[cursor.x+1][cursor.y].setVisible(false);
				keys[cursor.x-1][cursor.y].setVisible(false);		
			}
			//makes sure to not overwrite main keys going back
			if(nextY != Math.floor(cols/2))
			{
			//makes next 2 keys new characters
			keys[nextX+1][nextY].setText("Aut");
			keys[nextX-1][nextY].setText("Sel");
			
			//makes next 2 keys visible
			keys[nextX+1][nextY].setVisible(true);
			keys[nextX-1][nextY].setVisible(true);
			}
		}
	}
	
	// Recreates the keyboard based off of the last letter chosen
	private void redraw(String chosenStr)
	{ 
		String nextGrid[] = sugObject.returnList(chosenStr);
		
		int posInChosenStr = 12;
		
		//builds vertical elements of array
		for(int x = 0; x < rows; x++)
		{
			//sets new value but not in middle
			if(x != Math.floor(rows/2))
			{
				keys[x][(int)Math.floor(cols/2)].setText(nextGrid[posInChosenStr]);
			}
			//updates position variable to stride through letter list properly
			if(x < Math.floor(rows/2))
			{	
				posInChosenStr -= 4;
				if(posInChosenStr == 0)
				{
					posInChosenStr = 2;
				}
			}
			if(x > Math.floor(cols/2))
			{
				posInChosenStr += 4;
			}
		}
		
		//hard codes horizontal elements of array due to odd structure
		keys[(int)Math.floor(rows/2)][0].setText(nextGrid[25]);
		keys[(int)Math.floor(rows/2)][1].setText(nextGrid[23]);
		keys[(int)Math.floor(rows/2)][2].setText(nextGrid[21]);
		keys[(int)Math.floor(rows/2)][3].setText(nextGrid[19]);
		keys[(int)Math.floor(rows/2)][4].setText(nextGrid[17]);
		keys[(int)Math.floor(rows/2)][5].setText(nextGrid[15]);
		keys[(int)Math.floor(rows/2)][6].setText(nextGrid[11]);
		keys[(int)Math.floor(rows/2)][7].setText(nextGrid[7]);
		keys[(int)Math.floor(rows/2)][8].setText(nextGrid[3]);
		keys[(int)Math.floor(rows/2)][10].setText(nextGrid[1]);
		keys[(int)Math.floor(rows/2)][11].setText(nextGrid[5]);
		keys[(int)Math.floor(rows/2)][12].setText(nextGrid[9]);
		keys[(int)Math.floor(rows/2)][13].setText(nextGrid[13]);
		keys[(int)Math.floor(rows/2)][14].setText(nextGrid[16]);
		keys[(int)Math.floor(rows/2)][15].setText(nextGrid[18]);
		keys[(int)Math.floor(rows/2)][16].setText(nextGrid[20]);
		keys[(int)Math.floor(rows/2)][17].setText(nextGrid[22]);
		keys[(int)Math.floor(rows/2)][18].setText(nextGrid[24]);
	}
	
	// Handles reset back to center position and redrawing
	public void onSelect(String chosenChar)
	{
		redraw(chosenChar);
		keys[rows/2][cols/2].Select();
		cursor.setLocation(rows/2, cols/2);
	}
	
	// Move up, wrapping handled. Changes the color of the
	// previous button back to normal and set the new button
	// to red.
	
	public boolean moveUp()
	{
		boolean canMove;
		getCurrentKey().UnSelect();
		canMove = movePosn(-1,0);
		getCurrentKey().Select();
		return canMove;
	}
	
	// Move down, wrapping handled. Changes the color of the
	// previous button back to normal and set the new button
	// to red.
	
	public boolean moveDown()
	{
		boolean canMove;
		getCurrentKey().UnSelect();
		canMove = movePosn(1,0);
		getCurrentKey().Select();
		return canMove;
	}
	
	// Move left, wrapping handled. Changes the color of the
	// previous button back to normal and set the new button
	// to red.
	
	public boolean moveLeft()
	{
		boolean canMove;
		getCurrentKey().UnSelect();
		canMove = movePosn(0,-1);
		getCurrentKey().Select();
		return canMove;
	}
	
	// Move right, wrapping handled. Changes the color of the
	// previous button back to normal and set the new button
	// to red.
	
	public boolean moveRight()
	{
		boolean canMove;
		getCurrentKey().UnSelect();
		canMove = movePosn(0,1);
		getCurrentKey().Select();
		return canMove;
	}
	
	// ----- GUI

}
