package cubes;

import com.google.common.collect.HashBasedTable;

import java.util.*;

public class Algorithm {

    public static String randomScramble(int min, int max) {
        String poll = "URFDLB";
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
                ch = poll.charAt(r.nextInt(poll.length()));
            }
            else if (builder.charAt(i - diff) == 'D' || builder.charAt(i - diff) =='U'){
                do {
                    ch = poll.charAt(r.nextInt(poll.length()));
                }
                while (ch == 'D' || ch =='U');
            }
            else if (builder.charAt(i - diff) == 'B' || builder.charAt(i - diff) =='F'){
                do {
                    ch = poll.charAt(r.nextInt(poll.length()));
                }
                while (ch == 'B' || ch =='F');
            }
            else if (builder.charAt(i - diff) == 'L' || builder.charAt(i - diff) =='R'){
                do {
                    ch = poll.charAt(r.nextInt(poll.length()));
                }
                while (ch == 'L' || ch =='R');
            }
            else {
                ch = poll.charAt(r.nextInt(poll.length()));
            }

            builder.append(ch);
            switch (r.nextInt(poll.length()) % 7) {
                case 1 -> {
                    builder.append("2");
                    ran++; i++;
                }
                case 3, 5 -> {
                    builder.append("'");
                    ran++; i++;
                }
            }
        }
        return builder.toString();
    }

    public static boolean checkIsAlgProper(String alg) {
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
        boolean check = checkIsAlgProper(alg);
        if (!check)
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
                //case 1 : two or three repetitions etc DD DDD D2D D'DD
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
                int size = movesList.size();
                final int sum = cur.charAt(0) + nex.charAt(0);
                if (sum ==158) {
                    if (cur.equals("R") && nex.equals("L") || cur.equals("L") && nex.equals("R")) {
                        movesList.set(j, "x'");
                        movesList.set(j + 1, "R2");
                    }
                    else if (cur.equals("R'") && nex.equals("L'") || cur.equals("L'") && nex.equals("R'")) {
                        movesList.set(j, "x");
                        movesList.set(j + 1, "R2");
                    }
                    else if (cur.equals("R2") && nex.equals("L2") || cur.equals("L2") && nex.equals("R2")) {
                        movesList.set(j, "x2");
                        movesList.remove(j + 1);
                    }
                    else if (cur.equals("R") && nex.equals("L'") || cur.equals("L'") && nex.equals("R")) {
                        movesList.set(j, "x");
                        movesList.remove(j + 1);
                    }
                    else if (cur.equals("R'") && nex.equals("L") || cur.equals("L") && nex.equals("R'")) {
                        movesList.set(j, "x'");
                        movesList.remove(j + 1);
                    }
                    else if (cur.equals("R") && nex.equals("L2") || cur.equals("L2") && nex.equals("R")) {
                        movesList.set(j, "x");
                        movesList.set(j + 1, "L'");
                    }
                    else if (cur.equals("R2") && nex.equals("L") || cur.equals("L") && nex.equals("R2")) {
                        movesList.set(j, "x'");
                        movesList.set(j + 1, "R'");
                    }
                    else if (cur.equals("R'") && nex.equals("L2") || cur.equals("L2") && nex.equals("R'")) {
                        movesList.set(j, "x'");
                        movesList.set(j + 1, "L");
                    }
                    else if (cur.equals("R2") && nex.equals("L'") || cur.equals("L'") && nex.equals("R2")) {
                        movesList.set(j, "x");
                        movesList.set(j + 1, "R");
                    }
                    else j++;
                    if (j > 0 && size > movesList.size()) {
                        j--;
                    }
                }
                else if (sum ==153) {
                    if (cur.equals("U") && nex.equals("D") || cur.equals("D") && nex.equals("U")) {
                        movesList.set(j, "y'");
                        movesList.set(j + 1, "U2");
                    } else if (cur.equals("U'") && nex.equals("D'") || cur.equals("D'") && nex.equals("U'")) {
                        movesList.set(j, "y");
                        movesList.set(j + 1, "U2");
                    } else if (cur.equals("U2") && nex.equals("D2") || cur.equals("D2") && nex.equals("U2")) {
                        movesList.set(j, "y2");
                        movesList.remove(j + 1);
                    } else if (cur.equals("U") && nex.equals("D'") || cur.equals("D'") && nex.equals("U")) {
                        movesList.set(j, "y");
                        movesList.remove(j + 1);
                    } else if (cur.equals("U'") && nex.equals("D") || cur.equals("D") && nex.equals("U'")) {
                        movesList.set(j, "y'");
                        movesList.remove(j + 1);
                    } else if (cur.equals("U") && nex.equals("D2") || cur.equals("D2") && nex.equals("U")) {
                        movesList.set(j, "y");
                        movesList.set(j + 1, "D'");
                    } else if (cur.equals("U2") && nex.equals("D") || cur.equals("D") && nex.equals("U2")) {
                        movesList.set(j, "y'");
                        movesList.set(j + 1, "U'");
                    } else if (cur.equals("U'") && nex.equals("D2") || cur.equals("D2") && nex.equals("U'")) {
                        movesList.set(j, "y'");
                        movesList.set(j + 1, "L");
                    } else if (cur.equals("U2") && nex.equals("D'") || cur.equals("D'") && nex.equals("U2")) {
                        movesList.set(j, "y");
                        movesList.set(j + 1, "U");
                    } else j++;
                    if (j > 0 && size > movesList.size()) {
                        j--;
                    }
                }
                else if (sum==136) {
                    if (cur.equals("F") && nex.equals("B") || cur.equals("B") && nex.equals("F")) {
                        movesList.set(j, "z'");
                        movesList.set(j + 1, "F2");
                    } else if (cur.equals("F'") && nex.equals("B'") || cur.equals("B'") && nex.equals("F'")) {
                        movesList.set(j, "z");
                        movesList.set(j + 1, "F2");
                    } else if (cur.equals("F2") && nex.equals("B2") || cur.equals("B2") && nex.equals("F2")) {
                        movesList.set(j, "z2");
                        movesList.remove(j + 1);
                    } else if (cur.equals("F") && nex.equals("B'") || cur.equals("B'") && nex.equals("F")) {
                        movesList.set(j, "z");
                        movesList.remove(j + 1);
                    } else if (cur.equals("F'") && nex.equals("B") || cur.equals("B") && nex.equals("F'")) {
                        movesList.set(j, "z'");
                        movesList.remove(j + 1);
                    } else if (cur.equals("F") && nex.equals("B2") || cur.equals("B2") && nex.equals("F")) {
                        movesList.set(j, "z");
                        movesList.set(j + 1, "B'");
                    } else if (cur.equals("F2") && nex.equals("B") || cur.equals("B") && nex.equals("F2")) {
                        movesList.set(j, "z'");
                        movesList.set(j + 1, "F'");
                    } else if (cur.equals("F'") && nex.equals("B2") || cur.equals("B2") && nex.equals("F'")) {
                        movesList.set(j, "z'");
                        movesList.set(j + 1, "B");
                    } else if (cur.equals("F2") && nex.equals("B'") || cur.equals("B'") && nex.equals("F2")) {
                        movesList.set(j, "z");
                        movesList.set(j + 1, "F");
                    } else j++;
                    if (j > 0 && size > movesList.size()) {
                        j--;
                    }
                }
                else j++;
            }
            else j++;
        }

        skipRotations(movesList);
        return String.join("", movesList);
    }

    public static String skipRotation(String alg){
        final LinkedList<String> algToHandle = algToList(alg);
        skipRotations(algToHandle);
        return String.join("", algToHandle);
    }

    private static LinkedList<String> algToList(String alg) {
        LinkedList<String> movesList = new LinkedList<>();
        String[] algArr = alg.split("");
        int i = 0;
        while (i < alg.length()) {
            if (i + 1 == alg.length()) {
                movesList.add(algArr[i]);
                i++;
            } else if (algArr[i + 1].matches("[A-Za-z]")) {
                movesList.add(algArr[i]);
                i++;
            } else {
                movesList.add(algArr[i] + algArr[i + 1]);
                i += 2;
            }
        }
        return movesList;
    }

    private static void skipRotations(LinkedList<String> alg){
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
                skipRotations(alg, i);
            }
        }
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

    private static void skipRotations(LinkedList<String> alg, int i) {
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
}