package Spreadsheet;

/*
 * Detects cyclic dependency within the spreadsheet.
 */
final class CyclicDependencyDetector {
	private IDataManager dataManager;
	private IMathematicalExpressionManager mathematicalExpressionManager;

	public CyclicDependencyDetector(IDataManager datamanager,
			IMathematicalExpressionManager mathematicalexpressionmanager) {
		mathematicalExpressionManager = mathematicalexpressionmanager;
		this.dataManager = datamanager;
	}

	/*
	 * Checks whether the spreadsheet is cyclic dependent. Every cell in the
	 * spreadsheet is evaluated against the symbols within the mathematical
	 * expression and then check if there is a cyclic dependency between the 2
	 * cells.
	 */
	public boolean isSpreadSheetCyclicDependent() {
		// System.out.println("Start IsSpreadSheetCyclicDependent..");
		boolean bIsCyclicDependent = false;
		try {
			for (String tableCell : dataManager.getAllSpreadSheetCells()) {
				MathematicalExpression expression = mathematicalExpressionManager.getMathematicalExpression(tableCell);

				// If the expression contains raw values only, for eg. (4 5 *), no need to check
				// for dependency
				if (expression.expressionType == MathematicalExpressionType.RawValues)
					continue;

				for (String depedencySymbol : expression.getDependencySymbols()) {
					MathematicalExpression depedencyExpression = mathematicalExpressionManager
							.getMathematicalExpression(depedencySymbol);

					// There is a possibility that there maybe an invalid cell as part of the
					// expression, hence the entire spreadsheet input is invalid.
					if (depedencyExpression == null) {
						bIsCyclicDependent = true;
						System.out.println("Invalid Cell in the SpreadSheet : " + depedencySymbol);
						return bIsCyclicDependent;
					}

					// If the expression contains raw values only, no need to check for dependency
					if (depedencyExpression.expressionType == MathematicalExpressionType.RawValues)
						continue;

					// Check if one cell is dependent on another one.
					if (depedencyExpression.getDependencySymbols().contains(tableCell)) {
						// Cyclic Dependency Detected.
						bIsCyclicDependent = true;
						System.out.println(
								"There is a cyclic Dependency between " + tableCell + " and " + depedencySymbol);
					}
				}
			}
			// System.out.println("End IsSpreadSheetCyclicDependent..");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return bIsCyclicDependent;
	}
}