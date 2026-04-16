import java.util.*;

class Main {

    static final int MAX = 100;

    // Stack for operators
    static Stack<Character> stack = new Stack<>();

    // Temp variable counter
    static int tempCount = 1;

    // Precedence
    static int precedence(char ch) {
        if (ch == '+' || ch == '-') return 1;
        if (ch == '*' || ch == '/') return 2;
        return 0;
    }

    // Generate temp variable
    static String newTemp() {
        return "t" + (tempCount++);
    }

    // Infix → Postfix
    static String infixToPostfix(String exp) {
        StringBuilder postfix = new StringBuilder();
        stack.clear();

        for (int i = 0; i < exp.length(); i++) {
            char ch = exp.charAt(i);

            if (Character.isLetterOrDigit(ch)) {
                postfix.append(ch);
            }
            else if (ch == '(') {
                stack.push(ch);
            }
            else if (ch == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfix.append(stack.pop());
                }
                if (!stack.isEmpty()) stack.pop();
            }
            else {
                while (!stack.isEmpty() && precedence(stack.peek()) >= precedence(ch)) {
                    postfix.append(stack.pop());
                }
                stack.push(ch);
            }
        }

        while (!stack.isEmpty()) {
            postfix.append(stack.pop());
        }

        return postfix.toString();
    }

    // Generate TAC
    static void generateTAC(String postfix, String lhs) {
        Stack<String> st = new Stack<>();

        for (int i = 0; i < postfix.length(); i++) {
            char ch = postfix.charAt(i);

            if (Character.isLetterOrDigit(ch)) {
                st.push(String.valueOf(ch));
            }
            else {
                String op2 = st.pop();
                String op1 = st.pop();

                String temp = newTemp();
                System.out.println(temp + " = " + op1 + " " + ch + " " + op2);

                st.push(temp);
            }
        }

        System.out.println(lhs + " = " + st.pop());
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of expressions: ");
        int n = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < n; i++) {

            System.out.println("\nEnter expression (e.g., c=(a+b)*(c+d)):");
            String input = sc.nextLine();

            // Remove spaces
            String clean = input.replaceAll(" ", "");

            // Split LHS and RHS
            String[] parts = clean.split("=");
            String lhs = parts[0];
            String rhs = parts[1];

            // Convert to postfix
            String postfix = infixToPostfix(rhs);

            // Generate TAC
            System.out.println("Three Address Code:");
            generateTAC(postfix, lhs);
        }

        sc.close();
    }
}