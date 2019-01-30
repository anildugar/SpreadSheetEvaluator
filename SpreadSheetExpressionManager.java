import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/*
 * This class manages all mathematical expressions within the spreadsheet.
 * The overall purpose of the SpreadSheetExpressionManager is
 * 1. Creating the Mathematical Expressions
 * 2. Evaluating the Mathematical Expressions
 * 3. Printing the Mathematical Expressions
 */
final class SpreadSheetExpressionManager implements IMathematicalExpressionManager {
	/*
	 * This holds a raw expression which is provided as part of the input. Per cell
	 * it will return a mathematical expression. The data is stored in the ordered
	 * format (A1, A2, A3, B1, etc).
	 */
	private LinkedHashMap<String, MathematicalExpression> rawExpressionByCell = new LinkedHashMap<String, MathematicalExpression>();

	/*
	 * All the mathematical expressions are evaluated one by one and the below
	 * variable holds the result of per cell mathematical expressions. The order is
	 * not maintained, the one which gets evaluated first, is stored first.
	 */
	private HashMap<String, Float> spreadSheetEvaluatedExpressions = new HashMap<String, Float>(10);

	/*
	 * Needs data to be read from SpreadSheetDataManager
	 */
	private IDataManager dataManager;

	public SpreadSheetExpressionManager(IDataManager dataManager) {
		this.dataManager = dataManager;
	}

	/*
	 * Construct all the Mathematical expression in the spreadsheet. Those
	 * expressions which contain rawvalues only will all also be evaluated.
	 */
	public void initialize() {
		initializeAndEvaluateMathematicalExpressions();
	}

	/*
	 * Evaluates all the mathematical expressions which are all stored in a
	 * collection.
	 */
	public void evaluateAndPrint() {
		evaluateExpressions(rawExpressionByCell);
		printExpressionResults();
	}

	private void initializeAndEvaluateMathematicalExpressions() {
		int nLineNo = 1;
		for (String spreadSheetCell : dataManager.getAllSpreadSheetCells()) {
			MathematicalExpression expression = new MathematicalExpression(dataManager.getLine(nLineNo));
			rawExpressionByCell.put(spreadSheetCell, expression);
			if (expression.expressionType == MathematicalExpressionType.RawValues)
				spreadSheetEvaluatedExpressions.put(spreadSheetCell, expression.calculate());
			++nLineNo;
		}
	}

	/*
	 * Prints the results of the expression within the spreadsheet one by one.
	 */
	private void printExpressionResults() {
		// Print the first line containing row and column values.
		String firstLineRowColumnValue = dataManager.getLine(0);
		System.out.println(firstLineRowColumnValue);

		// Now print all the evaluated mathematical expressions within the spreadsheet.
		for (Map.Entry<String, MathematicalExpression> expressionPair : rawExpressionByCell.entrySet()) {
			String value = String.format("%.5f", expressionPair.getValue().getResult());
			System.out.println(value);
		}
	}

	/*
	 * The mathematical expression could of 2 types RawValues and Placeholder. The
	 * input of the calculated expressions which are of type RawValues are stored in
	 * a collection and passed as input for placeholder expression This function
	 * keeps evaluating those mathematical expression which are Placeholder types.
	 * Every mathematical expression which is of type Placeholder will try to update
	 * the cells within the expression. Once the expression type becomes RawValues,
	 * the evaluation of that particular expression is completed. Once All the
	 * expression type becomes RawValues, the spreadsheet evaluation is complete.
	 */
	private void evaluateExpressions(HashMap<String, MathematicalExpression> expressions) {
		HashMap<String, MathematicalExpression> placeholderExpressions = new HashMap<String, MathematicalExpression>();

		for (Map.Entry<String, MathematicalExpression> expressionByCell : expressions.entrySet()) {
			if (expressionByCell.getValue().expressionType == MathematicalExpressionType.RawValues)
				continue;

			MathematicalExpressionType expressionType = expressionByCell.getValue()
					.updateExpression(spreadSheetEvaluatedExpressions);
			if (expressionType == MathematicalExpressionType.RawValues) {
				float result = expressionByCell.getValue().calculate();
				spreadSheetEvaluatedExpressions.put(expressionByCell.getKey(), result);
			} else {
				placeholderExpressions.put(expressionByCell.getKey(), expressionByCell.getValue());
			}
		}

		if (placeholderExpressions.size() > 0)
			evaluateExpressions(placeholderExpressions);
	}

	/*
	 * For a given cell, it will return a mathematical expression.
	 */
	public MathematicalExpression getMathematicalExpression(String cell) {
		if (rawExpressionByCell.containsKey(cell))
			return rawExpressionByCell.get(cell);
		return null;
	}
}