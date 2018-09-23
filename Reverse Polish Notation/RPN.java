import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Stack;
import java.util.LinkedList;


public class RPN {

	private static boolean resultIsOperand = true;
	private static boolean resultIsOperator = false;
	private static boolean resultPrecedence = true;
	private static boolean resultRightAssociative = true;
	
	public RPN() {}

	// 3 + 1 = 4, 3 -> operand, '+' -> operator
	public static boolean isOperand(Character currentChar) {
		
		switch (currentChar) {
			case '-':
			case '+':
			case '*':
			case '/':
			case '^':
			case '(': 
			case ')': 
				resultIsOperand = false; break;
			default: {
				// Not an operation, thus an operator. Add it to the output.
				resultIsOperand = true; break;	
			}
		}
		return resultIsOperand;
	}

	public static boolean isOperator(Character currentChar) {

		switch (currentChar) { // Isolate the brackets as separate case.
			case '-':
			case '+':
			case '*':
			case '/':
			case '^':
				resultIsOperator = true; break;
			default:
				resultIsOperator = false; break;
		}

		return resultIsOperator;
	}

	public static int[] getOperatorPrecedenceCode(Character operator, Character operatorStack) {
		
		// The 0th element - the operator we are about to add to the stack.
		// The 1th element - the one we have fetched from the stack.
		Character[] operators = {operator, operatorStack};
		int[] result = new int[2];

			for (int i=0; i<2; i++) 
			{
				switch (operators[i])
				{
					case '(': result[i] = 0; break;
					case '+': case '-': result[i] = 1; break;
					case '*': case '/':  case '%': result[i] = 2; break;
					case '^': result[i] = 3; break;
					default: 
						System.err.println("Exception in getOperatorPrecedenceCode(): "
									   	   + "no such operator defined"); break;
				}
			}

		return result;
	}

	public static boolean isRightAssociative(Character operator) {

		if (operator != '^')
			resultRightAssociative = false;
		else 
			resultRightAssociative = true;

		return resultRightAssociative;
	}

	public static boolean hasLessPrecedence(Character operator, Character operatorStack) {

		int[] operatorPrecedenceCode = new int[2];

		operatorPrecedenceCode = RPN.getOperatorPrecedenceCode(operator, operatorStack);

		return operatorPrecedenceCode[0] < operatorPrecedenceCode[1] ? true : false;
	}


	public static boolean hasEqualPrecedence(Character operator, Character operatorStack) {

		int[] operatorPrecedenceCode = new int[2];

		operatorPrecedenceCode = RPN.getOperatorPrecedenceCode(operator, operatorStack);
		
		return operatorPrecedenceCode[0] == operatorPrecedenceCode[1] ? true : false;
	}

	public static void main(String[] args) {

		System.out.println("Reverse Polish Notation Calculator\n\n");

		System.out.println("The operators the program works with: ");
		System.out.printf(" addition       --> %s\n",  "+");
		System.out.printf(" subtraction    --> %s\n",  "-");
		System.out.printf(" multiplication --> %s\n",  "*");
		System.out.printf(" division       --> %s\n",  "/");
		System.out.printf(" modulus        --> %s\n",  "%");
		System.out.printf(" power          --> %s\n",  "^");

		System.out.print("\n\n\n");

		BufferedReader input = new BufferedReader (new InputStreamReader(System.in));
		String polynomial = null;
		Character currentChar = null;

		Stack<Character> stack = new Stack<Character>();
		LinkedList<Character> outputString = new LinkedList<Character>();
		
		// The Shunting-yard algorithm. 
		// Converts infix notation to postfix (reverse polish) notation.
		// Get the input polynomial symbow wise. Check for an empty string.

		try {
			polynomial = input.readLine();
			if (polynomial == null) {
				System.out.println("Error: no polynomial provided.");
				System.exit(-1);
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		finally {
			try {input.close();}
			catch(IOException e) 
				{System.err.println(e.getMessage());}
		}

		for (int i=0; i<polynomial.length(); i++)
		{
			currentChar = polynomial.charAt(i);

			if (currentChar.equals(' '))
				// Skip
				continue;
			else if (RPN.isOperand(currentChar)) // operand -> coeficient, power
			{
				outputString.add(currentChar);
			}
			else if (RPN.isOperator(currentChar)) // It's an operator: '+', '-', '*'...
			{
				while (
						!stack.empty()

						&& 

						( RPN.hasLessPrecedence(currentChar, stack.peek())
						
							|| 
						
							(RPN.hasEqualPrecedence(currentChar, stack.peek())
						 	&& !RPN.isRightAssociative(currentChar)))
					  ) 
				{
					outputString.add(stack.pop());
				}
				stack.push(currentChar);
			} // Look at the bracket cases.
			else if (currentChar == '(') {
				stack.push(currentChar);
			}
			else if (currentChar == ')') {
				while (stack.peek() != '(') {
					// Append all operators to the rpn string.
					outputString.add(stack.pop());
				}
				stack.pop(); // Remove the opening bracket.
			}
			else {
				System.err.println("Unsupported character used.");
				System.exit(-1);
			}
		}

		// Check if there is something left in the stack, print it.
		while (!stack.empty()) {
			outputString.add(stack.pop());
		}

		System.out.print("\nRPN: ");
		for (int i=0; i<outputString.size(); i++) {
			System.out.print(outputString.get(i));
		}
		System.out.print("\n");
	}
}