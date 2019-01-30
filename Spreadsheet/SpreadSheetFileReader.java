package Spreadsheet;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

/*
 * It is responsible for reading individual lines which are part of the spreadsheet file and finally this line will be converted into 
 * an object of type MathematicalExpression.
 * 
 */
final class SpreadSheetFileReader {
	
	/*		
	 * Stores all the lines which are part of the spreadsheet file.
	 */
	private List<String> lines;

	public SpreadSheetFileReader(BufferedReader reader) {
		//System.out.println("Displaying File...");
		try {
			lines = new ArrayList<String>();
			String line;
			while ((line = reader.readLine()) != null) {
				//System.out.println(line);
				lines.add(line);
			}
		} catch (IOException io) {
			io.printStackTrace();
		}
	}
	
	/*
	 * Based on the line no, return the line within the spreadsheet file.
	 */
	public String getLine(int index) {
		try
		{
			if (index < lines.size()) 
				return lines.get(index);			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}
	
	/*
	 * returns the total number of lines within the spreadsheet.
	 */
	public int getTotalLines() {
		return lines.size();
	}
}
