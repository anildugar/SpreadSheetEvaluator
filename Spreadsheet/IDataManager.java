package Spreadsheet;

import java.util.ArrayList;

/* 
 * This interface is implemented by SpreadSheetDataManager and will be consumed by 
 * It returns the data associated with the spreadsheet file.
 * It will be consumed by 
 * 		1. CyclicDependencyDetector class : It needs IDataManager to return the data within the spreadsheet and detect cyclic dependencies.
 * 		2. SpreadSheetExpressionManager class : It needs IDataManager to create MathematicalExpression per line.
 */
public interface IDataManager {

    String getLine(int line);

    ArrayList<String> getAllSpreadSheetCells();
}
