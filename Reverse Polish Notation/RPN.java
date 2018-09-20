import java.util.Scanner;
import java.util.Stack<E>;
import java.util.LinkedList<E>;


public class RPN {

	private static boolean resultIsOperand = true;
	private static boolean resultPrecedence = true;
	
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

	public static int[2] getOperatorPrecedenceCode(String operator, String operatorStack) {
		
		// The 0th element - the operator we are about to add to the stack.
		// The 1th element - the one we have fetched from the stack.
		int[2] operators = [operator, operatorStack];
		int[2] result;

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

	public static boolean hasLessThanOrEqualPrecedence(String operator, String operatorStack) {

		int operatorPrecedenceCode[2];

		operatorPrecedenceCode = RPN.getOperatorPrecedenceCode(operator, operatorStack);

		return operatorPrecedenceCode[0] <= operatorPrecedenceCode[1] ? true : false;
	}

	public static void main(String[] args) {

		System.out.println("Reverse Polish Notation Calculator\n\n");

		Scanner input = new Scanner(System.in);
		String currentChar = null;
		boolean hasPolynomial = false;

		Stack<String> stack = new Stack<String>;
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
						  && RPN.hasLessThanOrEqualPrecedence(currentChar, stack.peek())
						)
						|| 
					  ) 
				{

				}
			}		

		} while (input.hasNext());

		input.close();
	}
}