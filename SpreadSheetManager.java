/*
 * SpreadSheetManager manages the overall 
 * 1. Creation & Validation of SpreadSheet Data via SpreadSheetDataManager
 * 2. Evaluation of SpreadSheet Data via SpreadSheetExpressionManager.
 * 3. Detection of Cyclic Dependency via CyclicDependencyDetector.
 * It validates the spreadsheet input, initializes the spreadsheet input data, detects cyclic dependencies and finally evaluates and prints the results.
 */
class SpreadSheetManager {
	
	//Holds & validates the spreadsheet data
	private SpreadSheetDataManager spreadSheetDataManager;
	
	//creates and evaluates mathematical expressions. 
	private SpreadSheetExpressionManager spreadSheetExpressionManager;
	
	//Detects cyclic dependency.
	private CyclicDependencyDetector cyclicDependency;

	public SpreadSheetManager(SpreadSheetFileReader spreadSheetReader) 
	{
		spreadSheetDataManager = new SpreadSheetDataManager(spreadSheetReader);
		spreadSheetExpressionManager = new SpreadSheetExpressionManager(spreadSheetDataManager);
		cyclicDependency = new CyclicDependencyDetector(spreadSheetDataManager, spreadSheetExpressionManager);
	}

	public void calculateSpreadSheet() {
		if (validate()) {
			evaluateAndPrint();
		}
	}

	private boolean validate() {
		boolean bResult = spreadSheetDataManager.validate();
		if (bResult) {
			initialize();
			bResult = !cyclicDependency.IsSpreadSheetCyclicDependent();
		}
		return bResult;
	}

	private void initialize() {
		spreadSheetDataManager.initialize();
		spreadSheetExpressionManager.initialize();
	}	

	private void evaluateAndPrint() {
		spreadSheetExpressionManager.evaluateAndPrint();
	}
}