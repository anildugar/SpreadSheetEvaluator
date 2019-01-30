package Spreadsheet;

import java.util.ArrayList;

/*
 * Creates spreadSheet cells, and validates the spreadsheet data. 
 */
public class SpreadSheetDataManager implements IDataManager {
	private int nRows, nColumns;
	private SpreadSheetFileReader spreadSheetReader;

	/*
	 * Holds all the cells within the spreadsheet.
	 */
	private ArrayList<String> spreadSheetCells = new ArrayList<String>();

	public SpreadSheetDataManager(SpreadSheetFileReader reader) {
		spreadSheetReader = reader;
	}

	@Override
	public String getLine(int line) {
		return spreadSheetReader.getLine(line);
	}

	@Override
	public ArrayList<String> getAllSpreadSheetCells() {
		return spreadSheetCells;
	}

	public void initialize() {
		createSpreadSheetCells();
	}

	/*
	 * Every line in the spreadsheet corresponds to a spreadsheet cell for e.g Line
	 * 1 -> A1 Line 2 -> A2. etc It will construct total no of cells based on the
	 * particular row and column values.
	 */
	private void createSpreadSheetCells() {
		for (int col = 0; col < nColumns; ++col) {
			char alphabet = (char) (65 + col);
			for (int row = 0; row < nRows; ++row) {
				String tableCell = String.format("%c%d", alphabet, row + 1);
				spreadSheetCells.add(tableCell);
			}
		}
	}

	/*
	 * It validates the following Case 1. Validate whether the first line contains 2
	 * tokens only corresponding to row and column values. If it is not 2,
	 * spreadsheet is invalid Case 2. If Case 1 is valid, parse the provided row &
	 * column values, if it is fails, spreadsheet is invalid. Case 3. The total no
	 * of lines within the spreadsheet should be equal to (nRows * nColumns + 1). If
	 * the total lines in the spreadsheet dont match, the spreadsheet file is
	 * invalid and no need to further process.
	 */
	public boolean validate() {
		try {
			String firstLineExpression = spreadSheetReader.getLine(0);
			String[] values = firstLineExpression.split(" ");
			// Case 1. Validate whether the first line contains 2 tokens only corresponding
			// to row and column values. If it is not 2, spreadsheet is invalid
			if (values.length != 2) {
				System.out.println(
						"First line contains more than 2 tokens. It should only contain 2 integer values representing number of rows and columns.");
				return false;
			}
			// Case 2. If Case 1 is valid, parse the provided row & column values, if it is
			// fails, spreadsheet is invalid.
			nRows = Integer.parseInt(values[0]);
			nColumns = Integer.parseInt(values[1]);

			// Case 3. The total no of lines within the spreadsheet should be equal to
			// (nRows * nColumns + 1). If the total lines in the spreadsheet dont
			boolean bResult = ((nRows * nColumns + 1) == spreadSheetReader.getTotalLines());
			if (!bResult) {
				System.out.println("Total Number of lines must be row * column + 1. Invalid Spreadsheet input...");
				return false;
			}
			return true;
		} catch (NumberFormatException ex) {
			System.out.println(
					"Invalid row value or column value in the first line. Row value or column value must be an integer.");

			return false;
		}
	}
}
