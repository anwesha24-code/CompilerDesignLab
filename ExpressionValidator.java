import java.util.*;

public class ExpressionValidator {

    // Operators
    static Set<String> binaryOps = Set.of("+","-","*","/","%");
    static Set<String> unaryOps = Set.of("+","-","!","~");

    // Token class
    static class Token {
        String value, type;
        Token(String v, String t){
            value = v;
            type = t;
        }
    }

    // Step (a): Lexical Analysis
    static List<Token> tokenize(String expr) {
        List<Token> tokens = new ArrayList<>();

        String[] parts = expr.split("\\s+|(?=[()+\\-*/%])|(?<=[()+\\-*/%])");

        for(String p : parts){
            if(p.trim().isEmpty()) continue;

            if(p.matches("\\d+")) {
                tokens.add(new Token(p, "OPERAND"));
            }
            else if(p.matches("[a-zA-Z]+")) {
                tokens.add(new Token(p, "OPERAND"));
            }
            else if(binaryOps.contains(p)) {
                tokens.add(new Token(p, "OPERATOR"));
            }
            else if(p.equals("(") || p.equals(")")) {
                tokens.add(new Token(p, "PARENTHESIS"));
            }
            else {
                tokens.add(new Token(p, "INVALID"));
            }
        }
        return tokens;
    }

    // Step (d): Parentheses check
    static boolean checkParentheses(List<Token> tokens) {
        Stack<String> st = new Stack<>();

        for(Token t : tokens){
            if(t.value.equals("(")) st.push("(");
            else if(t.value.equals(")")){
                if(st.isEmpty()) return false;
                st.pop();
            }
        }
        return st.isEmpty();
    }

    // Step (b) & (c): Syntax + operator validation
    static boolean checkSyntax(List<Token> tokens) {

        for(int i = 0; i < tokens.size(); i++){
            Token curr = tokens.get(i);

            // Check invalid tokens
            if(curr.type.equals("INVALID")) return false;

            // Binary operator rules
            if(curr.type.equals("OPERATOR")) {

                // cannot be first or last
                if(i == 0 || i == tokens.size() - 1)
                    return false;

                Token prev = tokens.get(i - 1);
                Token next = tokens.get(i + 1);

                // must be between operands or )
                if(!(prev.type.equals("OPERAND") || prev.value.equals(")")))
                    return false;

                if(!(next.type.equals("OPERAND") || next.value.equals("(")))
                    return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter expression:");
        String expr = sc.nextLine();

        // (a) Tokenization
        List<Token> tokens = tokenize(expr);

        System.out.println("\nTokens:");
        for(Token t : tokens){
            System.out.println(t.value + " -> " + t.type);
        }

        // (d) Parentheses check
        boolean parenValid = checkParentheses(tokens);

        // (b) & (c) Syntax validation
        boolean syntaxValid = checkSyntax(tokens);

        // Final result
        System.out.println("\nParentheses Valid: " + parenValid);
        System.out.println("Syntax Valid: " + syntaxValid);

        if(parenValid && syntaxValid){
            System.out.println("\nExpression is SYNTACTICALLY VALID");
        } else {
            System.out.println("\nExpression is SYNTACTICALLY INVALID");
        }

        sc.close();
    }
}