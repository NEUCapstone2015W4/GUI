import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javafx.application.Application;
 
public class Suggestion {

  public static String[][] probArray;
		
  public Suggestion() 
  {
  buildCSVArray();
  }
  //main for testing
  /*
  public static void main(String[] args)
  {
	  
	  Suggestion test = new Suggestion();
	  
	  String[] bestLetters = new String[2];
	 
	  bestLetters = test.Suggest("p");
	  System.out.printf("%s, %s", bestLetters[0],bestLetters[1]);
	  
  }
  */ 
  //Standard code to read in a matrix based off of an input file
	private static void buildCSVArray()
	{
		probArray = new String[728][3];
		
		Scanner scanIn = null;
		int Rowc = 0;
		int Row = 0;
		int Colc = 0;
		int Col = 0;
		String InputLine = "";
		int xnum = 0;
		
		String xfileLocation;
		
		xfileLocation = "LetterProbabilityData.csv";
		
		try
		{
			scanIn = new Scanner(new BufferedReader(new FileReader(xfileLocation)));
		
			while (scanIn.hasNextLine())
			{
				InputLine = scanIn.nextLine();
				
				String[] InArray = InputLine.split(",");
				
				for(int x = 0; x < InArray.length; x++)
				{
					
					probArray[Rowc][x] = InArray[x];
				}
				Rowc++;
			}
			/* Print Statement for Debugging
			for(int i = 0; i < probArray.length; i++)
			   {
			      for(int j = 0; j < 3; j++)
			      {
			         System.out.printf("%s ", probArray[i][j]);
			      }
			      System.out.println();
			   }*/
		}catch (Exception e)
		{

			System.out.println(e);
			
		}
		
	}
	
	public static String[] Suggest(String chosenLetter)
	{
		String[] bestLetters = new String[2];
		
		int incrementCol1 = 0;
		//finds the row where the character first exists
		while(!chosenLetter.equals(probArray[incrementCol1][0]))
		{
			incrementCol1++;
		}
		
		int bestChoice = 0;
		int secondbestChoice = 0;
		int checkforBest = incrementCol1;
		//first loop to pick best letter
		while(chosenLetter.equals(probArray[checkforBest][0]))
		{
			if(Double.parseDouble(probArray[checkforBest][2]) > 
			Double.parseDouble(probArray[bestChoice][2]))
			{
				bestChoice = checkforBest;
			}
			checkforBest++;
		}
		checkforBest = incrementCol1;
		//second loop to pick next best letter
		while(chosenLetter.equals(probArray[checkforBest][0]))
		{
			if(
			(Double.parseDouble(probArray[checkforBest][2]) > 
			Double.parseDouble(probArray[secondbestChoice][2])
			)
			&& 
			(Double.parseDouble(probArray[checkforBest][2]) < 
			Double.parseDouble(probArray[bestChoice][2])
		    ))	
			{
				secondbestChoice = checkforBest;
			}
			checkforBest++;
		}
		bestLetters[0] = probArray[bestChoice][1];
		bestLetters[1] = probArray[secondbestChoice][1];
		
		return bestLetters;
	}
	
	
	//returns all possible characters from each letter
	//This assumes that there are 26 characters to grab
	public static String[] returnList(String chosenLetter)
	{
		String[] allLetters = new String[26];
		
		int incrementCol1 = 0;
		//finds the row where the character first exists
		while(!chosenLetter.equals(probArray[incrementCol1][0]))
		{
			incrementCol1++;
		}
		for(int i = incrementCol1; i < incrementCol1+26; i++)
		{
			allLetters[i-incrementCol1] = probArray[i][1];
		}
		
		return allLetters;
	}
}


