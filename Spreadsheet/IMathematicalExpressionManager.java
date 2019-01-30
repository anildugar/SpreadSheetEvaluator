package Spreadsheet;

/* 
 * This interface is implemented by SpreadSheetExpressionManager and will be consumed by 
 * It returns the MatehmaticalExpression associated with the spreadsheet file.
 * It will be consumed by 
 * 		1. CyclicDependencyDetector class : It needs IMathematicalExpressionManager to return the mathematical expression 
 * 		& detect cyclic dependencies within Mathematical expressions.
 */
interface IMathematicalExpressionManager {
	MathematicalExpression getMathematicalExpression(String cell);
}