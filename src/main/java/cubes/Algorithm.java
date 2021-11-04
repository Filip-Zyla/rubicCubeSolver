package cubes;

import com.google.common.collect.HashBasedTable;
import files.HistoryFile;

import java.util.*;

public class Algorithm {

    public static String randomScramble(int min, int max) {
        if (max<min){
            return "";
        }
        final String POLL_ALL = "URFDLB";
        final String POLL_U_D = "RFLB";
        final String POLL_R_L = "UFDB";
        final String POLL_F_B = "URDL";

        Random r = new Random();
        StringBuilder builder = new StringBuilder();
        int ran = (int) (Math.random() * (max - min + 1) + min); // from min to max

        for (int i = 0; i < ran; i++) {
            int diff;
            if (i > 1 && !Character.isLetter(builder.charAt(i - 1))) {
                // avoid repetitions lik D2D
                diff = 2;
            }
            else if (i > 0 && Character.isLetter(builder.charAt(i - 1))) {
                // avoid repetitions like DD
                diff = 1;
            }
            else diff = -1;

            char ch;
            if (diff == -1) {
                ch = POLL_ALL.charAt(r.nextInt(POLL_ALL.length()));
            }
            else if (builder.charAt(i - diff) == 'D' || builder.charAt(i - diff) == 'U') {
                ch = POLL_U_D.charAt(r.nextInt(POLL_U_D.length()));
            }
            else if (builder.charAt(i - diff) == 'B' || builder.charAt(i - diff) == 'F') {
                ch = POLL_F_B.charAt(r.nextInt(POLL_F_B.length()));
            }
            else if (builder.charAt(i - diff) == 'L' || builder.charAt(i - diff) == 'R') {
                ch = POLL_R_L.charAt(r.nextInt(POLL_R_L.length()));
            }
            else {
                ch = POLL_ALL.charAt(r.nextInt(POLL_ALL.length()));
            }

            builder.append(ch);
            int ii = r.nextInt(POLL_ALL.length()) % 7;
            if (ii == 1) {
                builder.append("2");
                ran++;
                i++;
            }
            else if (ii == 3 || ii == 5) {
                builder.append("'");
                ran++;
                i++;
            }
        }
        return builder.toString();
    }

    public static boolean checkIfProper(String alg) {
        if (alg.isEmpty()) {
            return false;
        }
        String poll = "xyzUDRLFB";
        char[] array = alg.toCharArray();
        int i = 0;
        while (i < array.length) {
            if (poll.indexOf(array[i]) == -1) {
                HistoryFile.saveToHistory("Char \"" + array[i] + "\" at index " + i + " cannot be in alg: " + alg);
                return false;
            }
            else if (i + 1 < array.length && (array[i + 1] == 39 || array[i + 1] == 50)) {
                i = i + 2;
            }
            else i++;
        }
        return true;
    }

    public static LinkedList<String> toList(String alg){
        if (!checkIfProper(alg)){
            return null;
        }
        LinkedList<String> movesList = new LinkedList<>();
        int i=0;
        final int length = alg.length();
        while (i<length){
            if (i+1 == length){
                movesList.add(String.valueOf(alg.charAt(i)));
                break;
            }
            else if (alg.charAt(i+1)=='2' || alg.charAt(i+1)=='\''){
                movesList.add(alg.substring(i,i+2));
                i=i+2;
            }
            else {
                movesList.add(String.valueOf(alg.charAt(i)));
                i++;
            }
        }
        return movesList;
    }

    public static int algLength(String alg){
        // simpler but ~4x slower: Algorithm.toList(alg).size()
        if (!checkIfProper(alg)){
            return -1;
        }
        int i=0;
        int counter = 0;
        final int length = alg.length();
        while (i<length){
            if (i+1 == length){
                counter++;
                break;
            }
            if (alg.charAt(i+1)=='2' || alg.charAt(i+1)=='\''){
                i=i+2;
            }
            else {
                i++;
            }
            counter++;
        }
        return counter;
    }

    public static String optimizeAlg(String alg) {
        if (!checkIfProper(alg)) {
            return "Alg is not proper: " + alg;
        }

        LinkedList<String> movesList = toList(alg);
        int i = 0;
        while (i < movesList.size()) {
            if (i + 1 == movesList.size()) {
                break;
            }
            String cur = movesList.get(i);
            String nex = movesList.get(i + 1);
            if (cur.charAt(0) == nex.charAt(0)) {
                // repetitions DD D'D etc.
                String combined = (cur + nex).replaceAll("[A-Za-z]", "");
                String s = String.valueOf(movesList.get(i).charAt(0));
                switch (combined) {
                    case "":
                    case "''":
                        movesList.set(i, s + "2");
                        movesList.remove(i + 1);
                        break;
                    case "'":
                    case "22":
                        movesList.remove(i + 1);
                        movesList.remove(i);
                        break;
                    case "2":
                        movesList.set(i, s + "'");
                        movesList.remove(i + 1);
                        break;
                    case "2'":
                    case "'2":
                        movesList.set(i, s);
                        movesList.remove(i + 1);
                        break;
                }
                if (i > 0) {
                    i--;
                }
            }
            else if (cur.charAt(0) != nex.charAt(0)) {
                // parallel moves changed to rotation+move
                // sum is m1+m2
                final int sum = cur.charAt(0) + nex.charAt(0);
                if (sum == 158)
                    eliminateParallelMoves(cur, nex, "R", "L", "x", movesList, i);
                else if (sum == 153)
                    eliminateParallelMoves(cur, nex, "U", "D", "y", movesList, i);
                else if (sum == 136)
                    eliminateParallelMoves(cur, nex, "F", "B", "z", movesList, i);
                else i++;
            }
            else i++;
        }
        return String.join("", movesList);
    }

    private static void eliminateParallelMoves(String cur, String nex, String m1, String m2, String r, LinkedList<String> movesList, int i) {
        // cur & nex are changed to mix of r & (m1||m2) with (2, ') as in cur & nex
        // ex. L2R' || R'L2 to x'L
        final int size = movesList.size();
        if (cur.equals(m1) && nex.equals(m2) || cur.equals(m2) && nex.equals(m1)) {
            movesList.set(i, r + "'");
            movesList.set(i + 1, m1 + "2");
        }
        else if (cur.equals(m1) && nex.equals(m2 + "'") || cur.equals(m2 + "'") && nex.equals(m1)) {
            movesList.set(i, r);
            movesList.remove(i + 1);
        }
        else if (cur.equals(m1) && nex.equals(m2 + "2") || cur.equals(m2 + "2") && nex.equals(m1)) {
            movesList.set(i, r);
            movesList.set(i + 1, m2 + "'");
        }
        else if (cur.equals(m1 + "'") && nex.equals(m2) || cur.equals(m2) && nex.equals(m1 + "'")) {
            movesList.set(i, r + "'");
            movesList.remove(i + 1);
        }
        else if (cur.equals(m1 + "'") && nex.equals(m2 + "'") || cur.equals(m2 + "'") && nex.equals(m1 + "'")) {
            movesList.set(i, r);
            movesList.set(i + 1, m1 + "2");
        }
        else if (cur.equals(m1 + "'") && nex.equals(m2 + "2") || cur.equals(m2 + "2") && nex.equals(m1 + "'")) {
            movesList.set(i, r + "'");
            movesList.set(i + 1, m2);
        }
        else if (cur.equals(m1 + "2") && nex.equals(m2) || cur.equals(m2) && nex.equals(m1 + "2")) {
            movesList.set(i, r + "'");
            movesList.set(i + 1, m1 + "'");
        }
        else if (cur.equals(m1 + "2") && nex.equals(m2 + "'") || cur.equals(m2 + "'") && nex.equals(m1 + "2")) {
            movesList.set(i, r);
            movesList.set(i + 1, m1);
        }
        else if (cur.equals(m1 + "2") && nex.equals(m2 + "2") || cur.equals(m2 + "2") && nex.equals(m1 + "2")) {
            movesList.set(i, r + "2");
            movesList.remove(i + 1);
        }
        if (i > 0 && size > movesList.size()) {
            --i;
        }
    }

    public static String skipRotation(String alg) {
        if (!checkIfProper(alg)) {
            return "Alg is not proper: " + alg;
        }
        LinkedList<String> algToHandle = toList(alg);
        return String.join("", skipRotation(algToHandle));
    }

    private static LinkedList<String> skipRotation(LinkedList<String> alg) {
        /*
               y   y'   x   x'   z   z'

          R    B   F    R   R    U   D
          L    F   B    L   L    D   U

          U    U   U    F   B    L   R
          D    D   D    B   F    R   L

          F    R   L    D   U    F   F
          B    L   R    U   D    B   B

          if ' or 2 then add it
         */
        Set rotations = new HashSet<>(Arrays.asList('x', 'y', 'z'));
        LinkedList<String> xyz = new LinkedList<>();
        for (int i=0; i<alg.size(); i++){
            if (rotations.contains(alg.get(i).charAt(0))){
                xyz.add(alg.get(i));
                alg.remove(i);
                i--;
            }
            else {
                for (int j=xyz.size()-1; j>=0; j--){
                    String toReplace = alg.get(i);
                    if (alg.get(i).length() == 2) {
                        // yR2 -> yR + 2 -> B + 2 -> B2
                        toReplace = rotationsTable.get(xyz.get(j), String.valueOf(toReplace.charAt(0))) + toReplace.charAt(1);
                    }
                    else {
                        toReplace = rotationsTable.get(xyz.get(j), toReplace);
                    }
                    alg.set(i, toReplace);
                }
            }
        }
        return alg;
    }

    private static final HashBasedTable<String, String, String> rotationsTable = createTable();

    private static HashBasedTable<String, String, String> createTable() {
        HashBasedTable<String, String, String> rotationsTable = HashBasedTable.create(6, 6);
        rotationsTable.put("y", "R", "B");
        rotationsTable.put("y", "L", "F");
        rotationsTable.put("y", "U", "U");
        rotationsTable.put("y", "D", "D");
        rotationsTable.put("y", "F", "R");
        rotationsTable.put("y", "B", "L");
        rotationsTable.put("y'", "R", "F");
        rotationsTable.put("y'", "L", "B");
        rotationsTable.put("y'", "U", "U");
        rotationsTable.put("y'", "D", "D");
        rotationsTable.put("y'", "F", "L");
        rotationsTable.put("y'", "B", "R");
        rotationsTable.put("y2", "R", "L");
        rotationsTable.put("y2", "L", "R");
        rotationsTable.put("y2", "U", "U");
        rotationsTable.put("y2", "D", "D");
        rotationsTable.put("y2", "F", "B");
        rotationsTable.put("y2", "B", "F");
        rotationsTable.put("x", "R", "R");
        rotationsTable.put("x", "L", "L");
        rotationsTable.put("x", "U", "F");
        rotationsTable.put("x", "D", "B");
        rotationsTable.put("x", "F", "D");
        rotationsTable.put("x", "B", "U");
        rotationsTable.put("x'", "R", "R");
        rotationsTable.put("x'", "L", "L");
        rotationsTable.put("x'", "U", "B");
        rotationsTable.put("x'", "D", "F");
        rotationsTable.put("x'", "F", "U");
        rotationsTable.put("x'", "B", "D");
        rotationsTable.put("x2", "R", "R");
        rotationsTable.put("x2", "L", "L");
        rotationsTable.put("x2", "U", "D");
        rotationsTable.put("x2", "D", "U");
        rotationsTable.put("x2", "F", "B");
        rotationsTable.put("x2", "B", "F");
        rotationsTable.put("z", "R", "U");
        rotationsTable.put("z", "L", "D");
        rotationsTable.put("z", "U", "L");
        rotationsTable.put("z", "D", "R");
        rotationsTable.put("z", "F", "F");
        rotationsTable.put("z", "B", "B");
        rotationsTable.put("z'", "R", "D");
        rotationsTable.put("z'", "L", "U");
        rotationsTable.put("z'", "U", "R");
        rotationsTable.put("z'", "D", "L");
        rotationsTable.put("z'", "F", "F");
        rotationsTable.put("z'", "B", "B");
        rotationsTable.put("z2", "R", "L");
        rotationsTable.put("z2", "L", "R");
        rotationsTable.put("z2", "U", "D");
        rotationsTable.put("z2", "D", "U");
        rotationsTable.put("z2", "F", "F");
        rotationsTable.put("z2", "B", "B");
        return rotationsTable;
    }
}