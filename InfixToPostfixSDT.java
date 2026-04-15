import java.util.*;

public class InfixToPostfixSDT {

    static int precedence(char ch) {
        if (ch == '+' || ch == '-') return 1;
        if (ch == '*' || ch == '/') return 2;
        return 0;
    }

    // Convert using SDT logic (stack-based parsing)
    static String convert(String exp) {
        Stack<Character> stack = new Stack<>();
        String result = "";

        for (int i = 0; i < exp.length(); i++) {
            char ch = exp.charAt(i);

            // Operand → print immediately
            if (Character.isLetterOrDigit(ch)) {
                result += ch;
            }
            // '('
            else if (ch == '(') {
                stack.push(ch);
            }
            // ')'
            else if (ch == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    result += stack.pop(); // SDT action
                }
                stack.pop();
            }
            // Operator
            else {
                while (!stack.isEmpty() &&
                        precedence(stack.peek()) >= precedence(ch)) {
                    result += stack.pop(); // SDT action
                }
                stack.push(ch);
            }
        }

        // Remaining operators
        while (!stack.isEmpty()) {
            result += stack.pop();
        }

        return result;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of expressions: ");
        int n = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < n; i++) {
            System.out.println("\nEnter infix expression:");
            String exp = sc.nextLine().replaceAll(" ", "");

            String postfix = convert(exp);
            System.out.println("Postfix expression: " + postfix);
        }

        sc.close();
    }
}