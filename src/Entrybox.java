import java.awt.Font;

import javax.swing.JTextArea;

// Implements a entrybox with auto-completion capabilities

public class Entrybox 
{
	
	// ----- Fields
	
	private String text;
	private String currWord;
	private String newword;
	private Dictionary dict;
	private int leng;
	private int textleng;
	private int cursorPosn;
	private JTextArea box;
	
	// ----- Local Statics
	
	private static final int numLines = 1;
	private static final int lineLength = 80;

	// ----- Constructor
	
	public Entrybox()
	{
		
		// Initialize basic fields
		
		text = new String();
		currWord = new String();
		cursorPosn = 0;
		
		// Initialize Dictionary
		
		dict = new Dictionary();
		
		// Configure GUI object
		
		box = new JTextArea(numLines, lineLength);
		box.setFocusable(false);
		box.setFont(new Font("Serif", Font.PLAIN, 48));
	}
	
	// ----- Get
	
	// Get the buffer contents
	
	public String getText()
	{
		
		return text;
	}
	
	// Get the current word
	
	public String getWord()
	{
		
		return currWord;
	}
	
	// Get the GUI object handle
	
	public JTextArea getHandle()
	{
		
		return box;
	}
	
	// ----- Actions
	
	// Type a character. Adds to the buffer, increments cursor.
	// attempts to add a guess. 
	
	public void typeChar(String c)
	{
		// Append the new character to the buffer, advance cursor
		
		text = text.concat(c);
		cursorPosn++;
		
		// If this is a space character, clear the current word. If not,
		// add this character as well.
		
		if (isspace(c))
		{	
			currWord = "";
		}
		else
		{
			currWord = currWord.concat(c);
		}
		
		// Update the actual GUI object, highlighting any suggested text
		
		box.setText(text);
	}
	
	// Clear the entire text buffer
	
	public void clearText()
	{
		// Clear text and all associated fields
		
		text = "";
		currWord = "";
		cursorPosn = 0;
		
		// Update GUI object
		
		box.setText(text);
	}
	
	// Clear one character from the butter
	
	public void clearChar()
	{
		// If there is nothing in the buffer, stop
		
		if(text.length() == 0)
		{
			cursorPosn=0;
			return;
		}
		
		// Any suggested characters are removed, along with
		// one extra character. Cursor position is decremented.
		
		cursorPosn--;
		text = text.substring(0, cursorPosn);
		
		// If it is not already empty, current word is also shaved
		// of one character.
		if (currWord.length() != 0)
		{
			// Remove the character
			
			currWord = currWord.substring(0, currWord.length()-1);
		}
		else
		{	
			int index=text.lastIndexOf(" ");
			
			if(index==-1)
			{
				currWord=text;
			}
			else
			{
				currWord = text.substring(index+1, text.length());
				
			}
		}
		
		// TBD (ask group) try and suggest another word here??
		
		// Update the GUI object
		
		box.setText(text);
	}
	
	// Take the auto-complete suggestion.
	
	public void autoComplete(String c)
	{	
		String temp=currWord.concat(c);

		temp=temp.toLowerCase();
		text=text.concat(c);
		text = text.concat(dict.Guess(temp));
		text=text.toUpperCase();
		box.setText(text);
		
	}
	public void clearSelection(){
		text=text.substring(0,cursorPosn);
		box.setText(text);
	
		
	}

	public void takeSuggestion(){
		text=text.concat(" ");
		cursorPosn=text.length();
		currWord="";
		
	}

	public boolean isspace(String c){
		if(c.equals(" ")){
		return true;
		}
		else{
			return false;
		}
	}
}