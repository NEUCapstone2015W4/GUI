// Dictionary class. Reads in a large text file and uses this to auto-complete words.
// UNOPTOMIZED - may be quite slow, just for POC testing.

import java.io.*;
import java.util.ArrayList;

public class Dictionary 
{
	
	// Class members
	
	private ArrayList<String> words;
	
	// Constructor - calls helper to read in file
	
	public Dictionary ()
	{
		
		// Populate the dictionary
		words = this.Readfile();
	}
	
	// Helper function for constructor, reads in a text file
	// line by line to populate the word list.
	
	private ArrayList<String> Readfile()
	{
		FileReader fr = null;
		try {
			fr = new FileReader("wordsFreq.txt");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BufferedReader textReader = new BufferedReader(fr);
		
		ArrayList<String> list = new ArrayList<String>();
		String line = new String();
		
		try 
		{
			do
			{
				try 
				{
					line = textReader.readLine();
				} 
				catch (IOException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				list.add(line);
			}
			while(textReader.ready());
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try 
		{
			textReader.close();
		} catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	// Guess the word being typed based on the input string. Returns
	// guessed word in string form or the original word if no matched
	// word was found. Returns only the untyped characters.
	
	public String Guess(String input)
	{
		
		// Cycle through the dictionary, return the first possible
		// match for now.
		
		for (int i=0; i<words.size(); i++)
		{
			// if there is a match, set and stop
			
			if (words.get(i).toString().startsWith(input))
			{
				// Trim off duplicate letters
				return words.get(i).substring(input.length());
			}
		}
		
		return "";
	}
	
	
	
}
