import java.util.*;

public class DFASimulator {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // 1. Number of states
        System.out.print("Enter number of states: ");
        int n = sc.nextInt();

        // 2. Input alphabet
        System.out.print("Enter number of symbols: ");
        int m = sc.nextInt();

        char[] symbols = new char[m];
        System.out.println("Enter symbols:");
        for (int i = 0; i < m; i++) {
            symbols[i] = sc.next().charAt(0);
        }

        // 3. Transition table
        int[][] transition = new int[n][m];

        System.out.println("Enter transition table δ(q, a):");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print("δ(q" + i + ", " + symbols[j] + ") = ");
                transition[i][j] = sc.nextInt();
            }
        }

        // 4. Start state
        System.out.print("Enter start state: ");
        int start = sc.nextInt();

        // 5. Final states
        System.out.print("Enter number of final states: ");
        int f = sc.nextInt();

        int[] finalStates = new int[f];
        System.out.println("Enter final states:");
        for (int i = 0; i < f; i++) {
            finalStates[i] = sc.nextInt();
        }

        // Input string
        System.out.print("Enter input string: ");
        String input = sc.next();

        int current = start;
        boolean valid = true;

        // Process string symbol by symbol
        for (int i = 0; i < input.length(); i++) {

            char ch = input.charAt(i);
            int index = -1;

            // Check if symbol belongs to alphabet
            for (int j = 0; j < m; j++) {
                if (symbols[j] == ch) {
                    index = j;
                    break;
                }
            }

            if (index == -1) {
                System.out.println("Error: Symbol '" + ch + "' not in alphabet");
                valid = false;
                break;
            }

            int next = transition[current][index];

            // Undefined transition
            if (next < 0 || next >= n) {
                System.out.println("Error: Undefined transition");
                valid = false;
                break;
            }

            System.out.println("δ(q" + current + ", " + ch + ") = q" + next);
            current = next;
        }

        if (valid) {
            System.out.println("Final state reached: q" + current);

            boolean accepted = false;
            for (int x : finalStates) {
                if (x == current) {
                    accepted = true;
                    break;
                }
            }

            if (accepted) {
                System.out.println("Result: ACCEPTED");
            } else {
                System.out.println("Result: REJECTED");
            }
        } else {
            System.out.println("Result: REJECTED");
        }

        sc.close();
    }
}