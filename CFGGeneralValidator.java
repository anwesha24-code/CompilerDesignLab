import java.util.*;

public class CFGGeneralValidator {

    static Map<Character, List<String>> grammar = new HashMap<>();
    static String target;
    static int maxLen;

    // Recursive function to derive strings
    static boolean derive(String current) {

        // If length exceeds target → stop
        if (current.length() > maxLen)
            return false;

        // If fully terminal and matches
        if (current.equals(target))
            return true;

        // Try replacing non-terminals
        for (int i = 0; i < current.length(); i++) {
            char ch = current.charAt(i);

            // If non-terminal (uppercase)
            if (Character.isUpperCase(ch)) {

                List<String> productions = grammar.get(ch);

                for (String prod : productions) {

                    // Replace non-terminal with production
                    String next = current.substring(0, i) + prod + current.substring(i + 1);

                    if (derive(next))
                        return true;
                }
                return false; // important: stop after first non-terminal
            }
        }

        return false;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Read grammar
        System.out.print("Enter number of productions: ");
        int n = sc.nextInt();
        sc.nextLine();

        System.out.println("Enter productions (e.g., S->aSa|bSb|c):");

        for (int i = 0; i < n; i++) {
            String line = sc.nextLine();

            String[] parts = line.split("->");
            char left = parts[0].charAt(0);

            String[] right = parts[1].split("\\|");

            grammar.put(left, Arrays.asList(right));
        }

        // Input string
        System.out.print("Enter input string: ");
        target = sc.nextLine();

        maxLen = target.length();

        // Start derivation from S
        boolean result = derive("S");

        if (result) {
            System.out.println("String is DERIVABLE (Valid)");
        } else {
            System.out.println("String is NOT derivable (Invalid)");
        }

        sc.close();
    }
}