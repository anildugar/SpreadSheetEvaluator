import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Spreadsheet
{
	public static void main(String args[]) 
	{
		 try
		 {
			//Read the piped input from the shell.
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));		
			SpreadSheetFileReader spreadSheetReader = new SpreadSheetFileReader(reader);
			SpreadSheetManager spreadSheetManager = new SpreadSheetManager(spreadSheetReader);
			spreadSheetManager.calculateSpreadSheet();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
