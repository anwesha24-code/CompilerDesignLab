import java.util.*;

public class LexicalAnalyser {
    // List of keywords
    static String[] keywords = {
            "int", "float", "double", "char", "if", "else",
            "while", "for", "return", "void"
    };
    // Check if word is keyword
    static boolean isKeyword(String word) {
        for (String k : keywords) {
            if (k.equals(word))
                return true;
        }
        return false;
    }
    // Check if valid identifier
    static boolean isIdentifier(String word) {
        if(word.length()==0)return false;
        for (int i = 0; i < word.length(); i++) {
            if (!Character.isLetterOrDigit(word.charAt(i)))
                return false;
        }
        return true;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter program (end with #):");

        String input = "";
        String line;

        // Read multiple lines
        while (!(line = sc.nextLine()).equals("#")) {
            input += line + " ";
        }
        // Token split (basic)
        String[] tokens = input.split("\\s+|(?=[=+\\-*/;,()])|(?<=[=+\\-*/;,()])");
        // Symbol table
        Set<String> symbolTable = new HashSet<>();

        int idCount = 0, litCount = 0, opCount = 0, puncCount = 0, keyCount = 0;

        System.out.println("\nToken Stream:");

        for (String token : tokens) {
            if (token.trim().isEmpty())
                continue;
            // Keyword
            if (isKeyword(token)) {
                System.out.println(token + " -> KEYWORD");
                keyCount++;
            }
            // Identifier
            else if (isIdentifier(token)) {
                System.out.println(token + " -> IDENTIFIER");
                symbolTable.add(token);
                idCount++;
            }
            // Number literal
            else if (token.matches("\\d+")) {
                System.out.println(token + " -> LITERAL");
                litCount++;
            }
            // Operator
            else if (token.matches("[+\\-*/=]")) {
                System.out.println(token + " -> OPERATOR");
                opCount++;
            }
            // Punctuation
            else if (token.matches("[;,()]")) {
                System.out.println(token + " -> PUNCTUATION");
                puncCount++;
            }
            // Error
            else {
                System.out.println(token + " -> ERROR");
            }
        }
        // Symbol Table
        System.out.println("\nSymbol Table:");
        for (String s : symbolTable) {
            System.out.println(s);
        }
        // Count Summary
        System.out.println("\nCount:");
        System.out.println("Identifiers: " + idCount);
        System.out.println("Literals: " + litCount);
        System.out.println("Operators: " + opCount);
        System.out.println("Punctuations: " + puncCount);
        System.out.println("Keywords: " + keyCount);

        sc.close();
    }
}