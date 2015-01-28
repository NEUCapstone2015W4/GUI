import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import javafx.application.Application;
 
public class ReadCSV {

	public String xStrPath;
	public static String[][] probArray;
	
  public static void main(String[] args) 
  {
  buildCSVArray();
  }
  
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
	
	/*
	public String[] getBestLetters(String chosenLetter)
	{
		return 
	}*/
}


