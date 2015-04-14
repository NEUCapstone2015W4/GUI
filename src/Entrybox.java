import java.awt.Font;

import javax.swing.JTextArea;

// Implements a entrybox with auto-completion capabilities

public class Entrybox 
{
	
	// ----- Fields
	
	private String text;
	private String currWord;
	private String newword;
	private int leng;
	private int endleng;
	private Dictionary dict;
	private int cursorPosn;
	private int initialcursorposn;
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

		initialcursorposn=0;
		
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
		// Erase any suggested characters
		
		text = text.substring(0, cursorPosn);
		// Append the new character to the buffer, advance cursor
		
		text = text.concat(c);
		cursorPosn++;
		
		// If this is a space character, clear the current word. If not,
		// add this character as well.
		
		if (c.equals(" "))
		{	initialcursorposn=cursorPosn;
			currWord = "";
		}
		else
		{
			currWord = currWord.concat(c);
		}
		
		// If the current word is more at least one character
		// long, attempt to auto-complete. Since an invalid suggestion
		// will return an empty string, always append the result.
		
		if (currWord.length() >= 1)
		{
			
			//text = text.concat(dict.Guess(currWord));
		}
		
		// Update the actual GUI object, highlighting any suggested text
		
		box.setText(text);
		box.select(cursorPosn, text.length());
	}
	
	// Clear the entire text buffer
	
	public void clearText()
	{
		// Clear text and all associated fields
		
		text = "";
		currWord = "";
		cursorPosn = 0;
		initialcursorposn=0;
		
		// Update GUI object
		
		box.setText(text);
	}
	
	// Clear one character from the butter
	
	public void clearChar()
	{
		// If there is nothing in the buffer, stop
		
		if(text.length() == 0)
		{	cursorPosn=0;
			initialcursorposn=0;
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
			
			// If this action has left the current word empty, populate it
			// with the previous word in the buffer in case the user is making
			// a longer word.
/*
			if (currWord.length() == 0 )
			{
				currWord = text.substring(text.lastIndexOf(" "), text.length());
			}
*/
	
		}
		// TBD (ask group) try and suggest another word here??
		
		// Update the GUI object
		
		box.setText(text);
	}
	
	// Take the auto-complete suggestion.
	
	public void autoComplete(String c)
	{	//Takes in the char from the button and adds it to the current word
		leng=currWord.length();
		initialcursorposn=cursorPosn;
		currWord=currWord.concat(c);
		
		
		//text= text.concat(currWord);
		//casts the contents of the words to lowercase
		currWord=currWord.toLowerCase();
		
		
		//tries to formulate the whole word 
		
		
		//adds the word to the text
		text = text.concat(currWord);
		
		
		//sets the text
		box.setText(text);
		
		endleng=currWord.length();
		//moves the cursor over to the new text
		cursorPosn = text.length();
		
	}
	
	//method to clear selection when scrolling through buttons deletes until the past cursor position
	public void clearSelection(){
		if(leng==0){
			while(cursorPosn != initialcursorposn){
				clearChar();
			//problem is it is clearing current word too which is a problem for individual letters
			}
		}
		else{
			while(endleng>leng){
				clearChar();
				endleng--;
				
			}
			text=text.substring(0,cursorPosn-leng);
			box.setText(text);
			cursorPosn=text.length();
			initialcursorposn=text.length();
		}
		}
		
	
	public void select(boolean a){
		if (a){
			initialcursorposn=text.length();
		}
		else{
			initialcursorposn=text.length();
			
		}
	}

}
