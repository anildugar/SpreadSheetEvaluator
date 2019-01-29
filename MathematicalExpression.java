import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Stack;

//The Type of the expression. The expression could be of type 
// 1. Raw values  : (5 4 *)
// 2. Placeholder : (D1 4 *)
enum MathematicalExpressionType
{
	PlaceHolder,
	RawValues
}

/*
 * This class represents a Mathematical Expression within a spreadsheet for a spreadsheet cell.
 */
final class MathematicalExpression
{
	//SpreadSheet Cells on which this expression is dependent upon. 
	private ArrayList<String> dependencySymbols = new ArrayList<String>();

	//All the symbols or elements which are part of the exception.
	private ArrayList<String> expressionSymbols = new ArrayList<String>();
	
	//The evaluated result of this Mathematical Expression.
	private float result;

	//The Type of the expression. The expression could be of type 
	// 1. Raw values  : (5 4 *)
	// 2. Placeholder : (D1 4 *).
	public MathematicalExpressionType expressionType;

	/*
	 * Constructor of a Mathematical Expression will do the following things.
	 * 1. Split the expression into individual symbols
	 * 2. Evaluate the expression type based on the individual symbols.
	 * 3. Evaluate and store all the dependencies for a given cell based on the individual expression symbols. 
	 */
	public MathematicalExpression(String expressionLine)
	{        	
		for (String symbol : expressionLine.split(" ")) {
			if (!symbol.trim().equals(""))
				expressionSymbols.add(symbol);
		} 

		EvaluateExpressionType();

		EvaluateDepedencysymbols();
	}

	public ArrayList<String> GetDependencySymbols() { return dependencySymbols; }

	/*
	 * For a given cell, Get all the dependencies in the mathematical expression.
	 */
	private void EvaluateDepedencysymbols()
	{
		//System.out.println("Start EvaluateDepedencysymbols..");
		for (String expressionSymbol : expressionSymbols)
		{
			if (!Calculator.IsOperandOrOperator(expressionSymbol))
				dependencySymbols.add(expressionSymbol);
		}
		//System.out.println("End EvaluateDepedencysymbols..");
	}

	/*
	 * Update the expression symbols individually with their respective values.
	 */
	public MathematicalExpressionType UpdateExpression(HashMap<String, Float> spreadSheetCellValues)
	{
		try
		{
			//System.out.println("Start UpdateExpression..");
			for(int iIndex=0; iIndex < expressionSymbols.size() ; ++iIndex)
			{
				String key = expressionSymbols.get(iIndex);
				if (spreadSheetCellValues.containsKey(key))
				{
					float value = spreadSheetCellValues.get(key);
					expressionSymbols.set(iIndex, Float.toString(value));
				}
			}
			EvaluateExpressionType();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		//System.out.println("End UpdateExpression..");
		return expressionType;
	}

	/*
	 * Check and determine whether the given expression is of type RawValues or Placeholder.
	 * If a given expression contains a spreadsheet cell as part of expression expression is of type placeholder. 
	 */
	private void EvaluateExpressionType()
	{
		//System.out.println("Start EvaluateExpressionType..");
		for (String expressionSympol : expressionSymbols)
		{
			if (Calculator.IsOperandOrOperator(expressionSympol))
			{
				expressionType = MathematicalExpressionType.RawValues;
			}
			else
			{
				expressionType = MathematicalExpressionType.PlaceHolder;
				break;
			}
		}
		//System.out.println("End EvaluateExpressionType..");
	}

	/*
	 * Calculated result of this mathematical expression.
	 */
	public float getResult()
	{
		return result;
	}

	/*
	 * Calculate the postfix expression. 
	 */
	public float Calculate()
	{
		try
		{
			//System.out.println("Start Calculate..");
			Stack<Float> stack = new Stack<Float>();
			for (String symbol : expressionSymbols)
			{
				if (Calculator.IsOperator(symbol))
				{
					float left = stack.pop();
					float right = stack.pop();
					stack.push(Calculator.Calculate(right, left, symbol));
				}
				else if (Calculator.IsOperand(symbol))
				{
					stack.push(Float.parseFloat(symbol));
				}
			}
			result = stack.pop();
		}
		catch(EmptyStackException ex)
		{
			ex.printStackTrace();
		}
		//System.out.println("End Calculate..");
		return result;
	}
}
