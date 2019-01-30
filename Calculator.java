/*
 * It is a calculator, which determines
 * 1. An Operand
 * 2. An Operator
 * 3. Calculates a set of operands based on the operator. 
 */
final class Calculator {
	public static boolean isOperator(String mathoperator) {
		switch (mathoperator) {
		case "+":
		case "-":
		case "*":
		case "/":
			return true;
		}
		return false;
	}

	public static boolean isOperandOrOperator(String expressionSymbol) {
		return isOperator(expressionSymbol) || isOperand(expressionSymbol);
	}

	/*
	 * If it is a value it will get parsed. 1. For e.g if the operand value is 5,
	 * IsOperand function will return true. 2. For e.g if the operand value is a
	 * cell value (A1, A2, etc), IsOperand function will return false.
	 */
	public static boolean isOperand(String operand) {
		boolean bParsed;
		try {
			Float.parseFloat(operand);
			bParsed = true;
		} catch (NumberFormatException ex) {
			bParsed = false;
		}
		return bParsed;
	}

	public static float calculate(float leftOperand, float rightOperand, String mathoperator) {
		try {
			switch (mathoperator) {
			case "+":
				return leftOperand + rightOperand;
			case "-":
				return leftOperand - rightOperand;
			case "*":
				return leftOperand * rightOperand;
			case "/":
				return leftOperand / rightOperand;
			}
			return 0;
		} catch (Exception ex) {
			return 0;
		}

	}
}