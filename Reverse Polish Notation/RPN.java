import java.util.Scanner;
import java.util.Stack;
import java.util.LinkedList;


public class RPN {

	private static boolean resultIsOperand = true;
	private static boolean resultPrecedence = true;
	private static boolean resultRightAssociative = true;
	
	public RPN() {}

	// 3 + 1 = 4, 3 -> operand, '+' -> operator
	public static boolean isOperand(String currentChar) {
		
		switch (currentChar) {
			case '-':
			case '+':
			case '*':
			case '/':
			case '^':
			case '(': 
			case ')': 
				isOperandResult = false; break;
			default: {
				// Not an operation, thus an operator. Add it to the output.
				isOperand = true; break;	
			}
		}
		return resultIsOperand;
	}

	public static int[] getOperatorPrecedenceCode(String operator, String operatorStack) {
		
		// The 0th element - the operator we are about to add to the stack.
		// The 1th element - the one we have fetched from the stack.
		int[] operators = {operator, operatorStack};
		int result = new int[2];

			for (int i=0; i<2; i++) 
			{
				if (operators[i] == "+" || operators[i] == "-")
					result[i] = 0;
				else if (operators[i] == "*" || operators[i] == "/" || operators[i] == "%")
					result[i] = 1;
				else if (operators[i] == "^")
					result[i] = 2;
				else if (operators[i] == "(") // parenthesis
					result[i] = 3;
				else if (operators[i] == ")")
					result[i] = 4;
				else { // error
					System.err.println("Exception in getOperatorPrecedenceCode(): "
									   + "no such operator defined");
				}
			}

		return result;
	}

	public static boolean isRightAssociative(String operator) {
		if (operator != "^")
			resultRightAssociative = false;
		else 
			resultRightAssociative = true;

		return resultRightAssociative;
	}

	public static boolean hasLessPrecedence(String operator, String operatorStack) {

		int operatorPrecedenceCode = new int[2];

		operatorPrecedenceCode = RPN.getOperatorPrecedenceCode(operator, operatorStack);

		return operatorPrecedenceCode[0] < operatorPrecedenceCode[1] ? true : false;
	}


	public static boolean hasEqualPrecedence(String operator, String operatorStack) {

		int operatorPrecedenceCode = new int[2];

		operatorPrecedenceCode = RPN.getOperatorPrecedenceCode(operator, operatorStack);

		return operatorPrecedenceCode[0] == operatorPrecedenceCode[1] ? true : false;
	}

	public static void main(String[] args) {

		System.out.println("Reverse Polish Notation Calculator\n\n");

		Scanner input = new Scanner(System.in);
		String currentChar = null;
		boolean hasPolynomial = false;

		Stack<String> stack = new Stack<String>();
		LinkedList<String> outputString = new LinkedList<String>();
		
		// The Shunting-yard algorithm. 
		// Converts infix notation to postfix (reverse polish) notation.
		// Get the input polynomial symbow wise. Check for an empty string.
		hasPolynomial = input.hasNext();

		// Error handling - empty input.
		if (!hasPolynomial)
		{
			System.err.println("Error: please provide a polynomial.");
			input.close();
			System.exit(-1);			
		}

		do {
			currentChar = (String) input.next();
			
			if (RPN.isOperand(currentChar)) 
			{
				outputString.add(currentChar);
			}
			else  // It's an operator: '+', '-', '*'...
			{
				while (
						(!stack.empty() 
						 && RPN.hasLessPrecedence(currentChar, stack.peek()))
						
						|| 
						
						(!stack.empty() 
						 && RPN.hasEqualPrecedence(currentChar, stack.peek())
						 && !RPN.isRightAssociative())

						||

						(!stack.empty()
						 && stack.peek() != "(")) 
				{
					outputString.add(stack.pop());
				}
				stack.push(currentChar);
			}

			if (currentChar == "(") {
				stack.push(currentChar);
			}
			if (currentChar == ")") {
				while (stack.peek() != "(") {
					// Append all operators to the rpn string.
					outputString.add(stack.pop());
				}
				stack.pop(); // Remove the "("
			}
		} while (input.hasNext());

		// Check if there is something left in the stack, print it.
		while (!stack.empty()) {
			outputString.add(stack.pop());
		}

		System.out.print("RPN: ");
		for (int i=0; i<outputString.size(); i++) {
			System.out.print(outputString.get(i));
		}

		input.close();
	}
}