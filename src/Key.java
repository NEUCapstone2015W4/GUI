import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;

import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

// Implements an individual key on a keyboard

public class Key 
{
	
	// ----- Fields
	
	private String value;		// Associated value
	private Boolean isActive;	// Is the key selected
	private Boolean isVisible;  // Is the key a used usable
	private Point posn;			// Position in a grid
	private JButton button;		// Button object
	
	// ----- Local Statics
	
	private final static Border border = new LineBorder(Color.BLACK, 3);
	private final static Color defaultClr = Color.LIGHT_GRAY;
	private final static Color highlightClr = Color.RED;
	
	// ----- Constructors
	
	public Key (String val, int x, int y)
	{
		
		// Set fields
		
		posn = new Point(x,y);
		value = val.toUpperCase().substring(0,1);
		isActive = false;
		isVisible = true;
		
		// Initialize button
		button = new JButton(value);
		button.setPreferredSize(new Dimension(100,100));
		button.setBackground(defaultClr);
		button.setBorder(border);
		button.setFont(new Font("Terminal", Font.BOLD, 48));
		button.setFocusable(false);
	}
	
	// ----- Get
	
	// Get the associated string
	
	public String getValue()
	{
		
		return value;
	}

	// Get the selection status
	
	public Boolean isSelected()
	{
		
		return isActive.equals(true);
	}
	
	// Get the grid position
	
	public Point getPosn()
	{
		
		return posn;
	}
	
	// Get the GUI control handle
	
	public JButton getHandle()
	{
		
		return button;
	}
	
	// ----- Set
	
	// Set the buttons's value
	
	public void Set(String newVal)
	{
		value = newVal;
	}
	
	
	// Select the button
	
	public void Select()
	{
		
		isActive = true;
		Draw();
	}
	
	// Select the button
	
	public void UnSelect()
	{
		
		isActive = false;
		Draw();
	}
	
	// ----- Drawing
	
	// Draw the button
	
	private void Draw()
	{
		
		// Check active state - if active, request focus and draw red. 
		
		if (isActive)
		{
			button.setBackground(highlightClr);
			button.repaint();
		}
		else	// Draw with standard background
		{
			button.setBackground(defaultClr);
			button.repaint();
		}
	}
	
	public void setVisible(Boolean vis)
	{
		isVisible = vis;
		button.setVisible(isVisible);
	}

}
