import java.util.*;

class Main {

    static final int MAX = 100;

    // ---------- QUADRUPLE ----------
    static class Quad {
        String op, arg1, arg2, result;
    }

    // ---------- TRIPLE ----------
    static class Triple {
        String op, arg1, arg2;
    }

    static Quad[] quad = new Quad[MAX];
    static Triple[] triple = new Triple[MAX];
    static int[] indirect = new int[MAX];

    // Mapping variable → triple index
    static Map<String, Integer> indexMap = new HashMap<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        for (int i = 0; i < MAX; i++) {
            quad[i] = new Quad();
            triple[i] = new Triple();
        }

        System.out.print("Enter number of TAC statements: ");
        int n = sc.nextInt();
        sc.nextLine();

        String[] input = new String[n];

        System.out.println("Enter TAC statements (e.g., S1 := 4 * i):");
        for (int i = 0; i < n; i++) {
            input[i] = sc.nextLine();
        }

        // ---------- QUADRUPLE ----------
        System.out.println("\n===== QUADRUPLE =====");

        for (int i = 0; i < n; i++) {
            parseQuad(input[i], quad[i]);
            System.out.println(i + ": (" + quad[i].op + ", " +
                    quad[i].arg1 + ", " +
                    quad[i].arg2 + ", " +
                    quad[i].result + ")");
        }

        // ---------- TRIPLE ----------
        System.out.println("\n===== TRIPLE =====");

        for (int i = 0; i < n; i++) {
            parseTriple(input[i], triple[i], i);
            System.out.println(i + ": (" + triple[i].op + ", " +
                    triple[i].arg1 + ", " +
                    triple[i].arg2 + ")");
        }

        // ---------- INDIRECT TRIPLE ----------
        System.out.println("\n===== INDIRECT TRIPLE =====");

        for (int i = 0; i < n; i++) {
            indirect[i] = i;
            System.out.println("Pointer[" + i + "] -> " + indirect[i]);
        }

        sc.close();
    }

    // ---------- PARSE QUADRUPLE ----------
    static void parseQuad(String s, Quad q) {
        s = s.replace(":=", "=");
        String[] parts = s.split("=");

        q.result = parts[0].trim();
        String rhs = parts[1].trim();

        if (rhs.contains("*")) {
            String[] p = rhs.split("\\*");
            q.op = "*";
            q.arg1 = p[0].trim();
            q.arg2 = p[1].trim();
        } else if (rhs.contains("/")) {
            String[] p = rhs.split("/");
            q.op = "/";
            q.arg1 = p[0].trim();
            q.arg2 = p[1].trim();
        } else if (rhs.contains("+")) {
            String[] p = rhs.split("\\+");
            q.op = "+";
            q.arg1 = p[0].trim();
            q.arg2 = p[1].trim();
        } else if (rhs.contains("-")) {
            String[] p = rhs.split("-");
            q.op = "-";
            q.arg1 = p[0].trim();
            q.arg2 = p[1].trim();
        } else if (rhs.contains("[")) {
            int i1 = rhs.indexOf("[");
            int i2 = rhs.indexOf("]");
            q.op = "=[]";
            q.arg1 = rhs.substring(0, i1).trim();
            q.arg2 = rhs.substring(i1 + 1, i2).trim();
        } else {
            q.op = "=";
            q.arg1 = rhs;
            q.arg2 = "";
        }
    }

    // ---------- PARSE TRIPLE ----------
    static void parseTriple(String s, Triple t, int index) {
        s = s.replace(":=", "=");
        String[] parts = s.split("=");

        String lhs = parts[0].trim();
        String rhs = parts[1].trim();

        if (rhs.contains("*")) {
            String[] p = rhs.split("\\*");
            t.op = "*";
            t.arg1 = getArg(p[0].trim());
            t.arg2 = getArg(p[1].trim());
        } else if (rhs.contains("/")) {
            String[] p = rhs.split("/");
            t.op = "/";
            t.arg1 = getArg(p[0].trim());
            t.arg2 = getArg(p[1].trim());
        } else if (rhs.contains("+")) {
            String[] p = rhs.split("\\+");
            t.op = "+";
            t.arg1 = getArg(p[0].trim());
            t.arg2 = getArg(p[1].trim());
        } else if (rhs.contains("-")) {
            String[] p = rhs.split("-");
            t.op = "-";
            t.arg1 = getArg(p[0].trim());
            t.arg2 = getArg(p[1].trim());
        } else if (rhs.contains("[")) {
            int i1 = rhs.indexOf("[");
            int i2 = rhs.indexOf("]");
            t.op = "=[]";
            t.arg1 = rhs.substring(0, i1).trim();
            t.arg2 = getArg(rhs.substring(i1 + 1, i2).trim());
        } else {
            t.op = "=";
            t.arg1 = getArg(rhs);
            t.arg2 = "";
        }

        // Store mapping
        indexMap.put(lhs, index);
    }

    // Convert variable to (index) if exists
    static String getArg(String s) {
        if (indexMap.containsKey(s)) {
            return "(" + indexMap.get(s) + ")";
        }
        return s;
    }
}