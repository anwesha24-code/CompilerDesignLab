import java.util.*;

public class ExpressionValidator {

    // Check if operator
    static boolean isOperator(char ch) {
        return "+-*/".indexOf(ch) != -1;
    }

    // Check if operand (letter or digit)
    static boolean isOperand(char ch) {
        return Character.isLetterOrDigit(ch);
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter expression: ");
        String exp = sc.nextLine();

        List<String> tokens = new ArrayList<>();

        // ---------------- a) Tokenization ----------------
        for (int i = 0; i < exp.length(); i++) {
            char ch = exp.charAt(i);

            if (ch == ' ')
                continue;

            if (isOperator(ch) || ch == '(' || ch == ')') {
                tokens.add(Character.toString(ch));
            } else if (isOperand(ch)) {
                String temp = "";
                while (i < exp.length() && isOperand(exp.charAt(i))) {
                    temp += exp.charAt(i);
                    i++;
                }
                i--;
                tokens.add(temp);
            } else {
                tokens.add("INVALID");
            }
        }

        System.out.println("\nTokens: " + tokens);

        // ---------------- d) Parentheses Check ----------------
        Stack<Character> stack = new Stack<>();
        boolean valid = true;

        for (char ch : exp.toCharArray()) {
            if (ch == '(')
                stack.push(ch);
            else if (ch == ')') {
                if (stack.isEmpty()) {
                    valid = false;
                    System.out.println("Error: Unmatched ')'");
                    break;
                }
                stack.pop();
            }
        }

        if (!stack.isEmpty()) {
            valid = false;
            System.out.println("Error: Unmatched '('");
        }

        // ---------------- b & c) Operator & Syntax Check ----------------
        String prev = "";

        for (int i = 0; i < tokens.size(); i++) {
            String curr = tokens.get(i);

            if (curr.equals("INVALID")) {
                valid = false;
                System.out.println("Error: Invalid token");
                break;
            }

            // If operator
            if (curr.length() == 1 && isOperator(curr.charAt(0))) {

                // Unary allowed only at start or after '('
                if (i == 0 || prev.equals("(")) {
                    System.out.println(curr + " is Unary Operator");
                } else {
                    // Binary operator must have operand before and after
                    if (i == tokens.size() - 1) {
                        valid = false;
                        System.out.println("Error: Operator at end");
                        break;
                    }
                    if (!(isOperand(prev.charAt(0)) || prev.equals(")"))) {
                        valid = false;
                        System.out.println("Error: Invalid binary operator usage");
                        break;
                    }
                }
            }

            // Operand after operand (invalid)
            if (i > 0 && isOperand(curr.charAt(0)) && isOperand(prev.charAt(0))) {
                valid = false;
                System.out.println("Error: Missing operator between operands");
                break;
            }

            prev = curr;
        }

        // ---------------- e) Final Result ----------------
        if (valid) {
            System.out.println("\nExpression is SYNTACTICALLY VALID");
        } else {
            System.out.println("\nExpression is SYNTACTICALLY INVALID");
        }

        sc.close();
    }
}