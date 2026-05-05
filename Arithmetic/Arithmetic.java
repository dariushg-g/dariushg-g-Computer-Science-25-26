import java.util.Scanner;
import java.util.Stack;

public class Arithmetic {

	// Evaluates a String exp that has an arithmetic expression, written in classic notation
	public static int evaluate(String exp) {
		var scanner = new Scanner(exp);
		var number_stack = new Stack<Integer>();
		var op_stack = new Stack<String>();
		while (scanner.hasNext()) {
			var value = scanner.next();
			if (value.equals("(")) {
				continue;
			} else if (value.equals(")")) {
				var right = number_stack.pop();
				var left = number_stack.pop();
				var op = op_stack.pop();
				number_stack.push(operate(left, right, op));
			} else if (value.equals("+") || value.equals("-") || value.equals("*")
					|| value.equals("/")) {
				op_stack.push(value);
			} else {
				number_stack.push(Integer.parseInt(value));
			}
		}

		scanner.close();
		return number_stack.pop();
	}

	// Returns the result of doing operand1 operation operand2,
	// e.g. operate(5, 2, "-") should return 3
	public static int operate(int operand1, int operand2, String operation) {
		switch (operation) {
			case "+":
				return operand1 + operand2;
			case "-":
				return operand1 - operand2;
			case "*":
				return operand1 * operand2;
			case "/":
				return operand1 / operand2;
			default:
				return 0;
		}
	}

	// Evaluates a String exp that has an arithmetic expression written in STOUT notation
	public static int evaluateStout(String exp) {
		var scanner = new Scanner(exp);
		var number_stack = new Stack<Integer>();

		while (scanner.hasNext()) {
			var val = scanner.next();
			if (val.equals("+") || val.equals("-") || val.equals("/") || val.equals("*")) {
				var right = number_stack.pop();
				var left = number_stack.pop();
				number_stack.push(operate(left, right, val));
			} else {
				number_stack.push(Integer.parseInt(val));
			}
		}

		scanner.close();
		return number_stack.pop();
	}

	public static String convertClassicToStout(String exp) {
		var scanner = new Scanner(exp);
		var op_stack = new Stack<String>();

		var returned = new StringBuilder();

		while (scanner.hasNext()) {
			var val = scanner.next();
			if (val.equals("(")) {
			} else if (val.equals("+") || val.equals("-") || val.equals("/") || val.equals("*")) {
				op_stack.push(val + " ");
			} else if (val.equals(")")) {
				var op = op_stack.pop();
				returned.append(op);
			} else {
				returned.append(val + " ");
			}
		}

		scanner.close();
		return returned.toString();
	}


}
