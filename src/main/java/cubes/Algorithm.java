package cubes;

import com.google.common.collect.HashBasedTable;

import java.util.*;

public class Algorithm {

    public static String randomScramble(int min, int max) {
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
            if (diff == -1){
                ch = POLL_ALL.charAt(r.nextInt(POLL_ALL.length()));
            }
            else if (builder.charAt(i - diff) == 'D' || builder.charAt(i - diff) =='U'){
                ch = POLL_U_D.charAt(r.nextInt(POLL_U_D.length()));
            }
            else if (builder.charAt(i - diff) == 'B' || builder.charAt(i - diff) =='F') {
                ch = POLL_F_B.charAt(r.nextInt(POLL_F_B.length()));
            }
            else if (builder.charAt(i - diff) == 'L' || builder.charAt(i - diff) =='R'){
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
            } else if (ii == 3 || ii == 5) {
                builder.append("'");
                ran++;
                i++;
            }
        }
        return builder.toString();
    }

    public static boolean checkIsAlgProper(String alg) {
        if (alg.isEmpty())
            return false;
        String poll = "xyzUDRLFB2'";
        char[] array = alg.toCharArray();
        int i = 0;
        while (i < array.length) {
            if (poll.indexOf(array[i]) == -1) {
                System.err.println("Char " + array[i] + " at index " + i + " cannot be in alg");
                return false;
            } else if (Character.isDigit(array[i])) {
                System.err.println("Char " + array[i] + " at index " + i + " is repeated int");
                return false;
            } else if (array[i] == 39) {
                System.err.println("Char " + array[i] + " at index " + i + " is repeated coma");
                return false;
            }
            if (i + 1 < array.length && (array[i + 1] == 39 || Character.isDigit(array[i + 1])))
                i = i + 2;
            else i++;
        }

        return true;
    }

    public static String optimizeAlg(String alg) {
        if (!checkIsAlgProper(alg))
            return "Alg is not proper: " + alg;
        LinkedList<String> movesList = algToList(alg);

        int j = 0;
        while (j < movesList.size()) {
            if (j + 1 == movesList.size()) {
                break;
            }
            String cur = movesList.get(j);
            String nex = movesList.get(j + 1);
            if (cur.charAt(0) == nex.charAt(0)) {
                // repetitions DD D'D etc.
                String combined = (cur + nex).replaceAll("[A-Za-z]", "");
                String s = String.valueOf(movesList.get(j).charAt(0));
                switch (combined) {
                    case "", "''" -> {
                        movesList.set(j, s + "2");
                        movesList.remove(j + 1);
                    }
                    case "'", "22" -> {
                        movesList.remove(j + 1);
                        movesList.remove(j);
                    }
                    case "2" -> {
                        movesList.set(j, s + "'");
                        movesList.remove(j + 1);
                    }
                    case "2'", "'2" -> {
                        movesList.set(j, s);
                        movesList.remove(j + 1);
                    }
                }
                if (j > 0) {
                    j--;
                }
            }
            else if (cur.charAt(0) != nex.charAt(0)){
                // parallel moves changed to rotation+move
                final int sum = cur.charAt(0) + nex.charAt(0);
                if (sum ==158)
                    eliminateParallelMoves(cur, nex, "R", "L", "x", movesList, j);
                else if (sum ==153)
                    eliminateParallelMoves(cur, nex, "U", "D", "y", movesList, j);
                else if (sum==136)
                    eliminateParallelMoves(cur, nex, "F", "B", "z", movesList, j);
                else j++;
            }
            else j++;
        }
        return String.join("", movesList);
    }

    private static LinkedList<String> algToList(String alg) {
        LinkedList<String> movesList = new LinkedList<>();
        String[] array = alg.split("");
        int i = 0;
        while (i < alg.length()) {
            if (i + 1 == alg.length()) {
                movesList.add(array[i]);
                i++;
            } else if (array[i + 1].matches("[A-Za-z]")) {
                movesList.add(array[i]);
                i++;
            } else {
                movesList.add(array[i] + array[i + 1]);
                i += 2;
            }
        }
        return movesList;
    }

    private static void eliminateParallelMoves(String cur, String nex, String m1, String m2, String r, LinkedList<String> movesList, int j){
        final int size = movesList.size();
        if (cur.equals(m1) && nex.equals(m2) || cur.equals(m2) && nex.equals(m1)) {
            movesList.set(j, r+"'");
            movesList.set(j + 1, m1+"2");
        }
        else if (cur.equals(m1) && nex.equals(m2+"'") || cur.equals(m2+"'") && nex.equals(m1)) {
            movesList.set(j, r);
            movesList.remove(j + 1);
        }
        else if (cur.equals(m1) && nex.equals(m2+"2") || cur.equals(m2+"2") && nex.equals(m1)) {
            movesList.set(j, r);
            movesList.set(j + 1, m2+"'");
        }
        else if (cur.equals(m1+"'") && nex.equals(m2) || cur.equals(m2) && nex.equals(m1+"'")) {
            movesList.set(j, r+"'");
            movesList.remove(j + 1);
        }
        else if (cur.equals(m1+"'") && nex.equals(m2+"'") || cur.equals(m2+"'") && nex.equals(m1+"'")) {
            movesList.set(j, r);
            movesList.set(j + 1, m1+"2");
        }
        else if (cur.equals(m1+"'") && nex.equals(m2+"2") || cur.equals(m2+"2") && nex.equals(m1+"'")) {
            movesList.set(j, r+"'");
            movesList.set(j + 1, m2);
        }
        else if (cur.equals(m1+"2") && nex.equals(m2) || cur.equals(m2) && nex.equals(m1+"2")) {
            movesList.set(j, r+"'");
            movesList.set(j + 1, m1+"'");
        }
        else if (cur.equals(m1+"2") && nex.equals(m2+"'") || cur.equals(m2+"'") && nex.equals(m1+"2")) {
            movesList.set(j, r);
            movesList.set(j + 1, m1);
        }
        else if (cur.equals(m1+"2") && nex.equals(m2+"2") || cur.equals(m2+"2") && nex.equals(m1+"2")) {
            movesList.set(j, r+"2");
            movesList.remove(j + 1);
        }
        if (j > 0 && size > movesList.size()) {
            j--;
        }
    }

    //TODO test and fix, check if proper, length
    public static String skipRotation(String alg){
        final LinkedList<String> algToHandle = algToList(alg);
        skipRotation(algToHandle);
        return String.join("", algToHandle);
    }

    private static void skipRotation(LinkedList<String> alg){
        /**
         *      y   y'   x   x'   z   z'
         *
         * R    F   B    R   R    D   U
         * L    B   F    L   L    U   D
         *
         * U    U   U    B   F    R   L
         * D    D   D    F   B    L   R
         *
         * F    L   R    U   D    F   F
         * B    R   L    D   U    B   B
         *
         * if ' or 2 then add it
         *
         * xyzR -> xyD -> xD -> F
         */
        Set rotations = new HashSet<Character>(Arrays.asList('x', 'y', 'z'));
        if (alg.size()<2){
            if (rotations.contains(alg.get(1))) alg.remove(1);
            return;
        }
        for (int i=alg.size()-2; i>=0; i--){
            if (rotations.contains(alg.get(i).charAt(0))){
                if (alg.get(i).contains("2")){
                    alg.set(i, String.valueOf(alg.get(i).charAt(0)));
                    alg.add(i, String.valueOf(alg.get(i).charAt(0)));
                    i++;
                }
                skipRotation(alg, i);
            }
        }
    }

    private static void skipRotation(LinkedList<String> alg, int i) {
        if (i+1>=alg.size()) {
            alg.remove(i);
            return;
        }
        String rotation = alg.get(i);
        for (int j=i+1; j<alg.size(); j++){
            String toReplace;
            if (alg.get(j).length()>1) {
                // yR2 -> yR + 2 -> F + 2 -> F2
                toReplace = rotationsTable.get(rotation, String.valueOf(alg.get(j).charAt(0))) + alg.get(j).charAt(1);
            }
            else {
                toReplace = rotationsTable.get(rotation, alg.get(j));
            }
            alg.set(j, toReplace);
        }
        alg.remove(i);
    }

    private static HashBasedTable<String, String, String> rotationsTable = createTable();

    private static HashBasedTable<String, String, String> createTable() {
        HashBasedTable<String, String, String> rotationsTable = HashBasedTable.create(6,6);
        rotationsTable.put("y", "R", "F");
        rotationsTable.put("y", "L", "B");
        rotationsTable.put("y", "U", "U");
        rotationsTable.put("y", "D", "D");
        rotationsTable.put("y", "F", "L");
        rotationsTable.put("y", "B", "R");
        rotationsTable.put("y'", "R", "B");
        rotationsTable.put("y'", "L", "F");
        rotationsTable.put("y'", "U", "U");
        rotationsTable.put("y'", "D", "D");
        rotationsTable.put("y'", "F", "R");
        rotationsTable.put("y'", "B", "L");
        rotationsTable.put("x", "R", "R");
        rotationsTable.put("x", "L", "L");
        rotationsTable.put("x", "U", "B");
        rotationsTable.put("x", "D", "F");
        rotationsTable.put("x", "F", "U");
        rotationsTable.put("x", "B", "D");
        rotationsTable.put("x'", "R", "R");
        rotationsTable.put("x'", "L", "L");
        rotationsTable.put("x'", "U", "F");
        rotationsTable.put("x'", "D", "B");
        rotationsTable.put("x'", "F", "D");
        rotationsTable.put("x'", "B", "U");
        rotationsTable.put("z", "R", "D");
        rotationsTable.put("z", "L", "U");
        rotationsTable.put("z", "U", "R");
        rotationsTable.put("z", "D", "L");
        rotationsTable.put("z", "F", "F");
        rotationsTable.put("z", "B", "B");
        rotationsTable.put("z'", "R", "U");
        rotationsTable.put("z'", "L", "D");
        rotationsTable.put("z'", "U", "L");
        rotationsTable.put("z'", "D", "R");
        rotationsTable.put("z'", "F", "F");
        rotationsTable.put("z'", "B", "B");
        return rotationsTable;
    }
}