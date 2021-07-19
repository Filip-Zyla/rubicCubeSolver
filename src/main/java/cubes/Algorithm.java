package cubes;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

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
        List<String> movesList = new LinkedList<>();
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
            } else if ((cur + nex).contains("'")) {
                // case 2 : R'L type to rotate
                int size = movesList.size();
                if (cur.equals("R'") && nex.equals("L") || cur.equals("L") && nex.equals("R'")) {
                    movesList.set(j, "x'");
                    movesList.remove(j + 1);
                } else if (cur.equals("R") && nex.equals("L'") || cur.equals("L'") && nex.equals("R")) {
                    movesList.set(j, "x");
                    movesList.remove(j + 1);
                } else if (cur.equals("U'") && nex.equals("D") || cur.equals("D") && nex.equals("U'")) {
                    movesList.set(j, "y'");
                    movesList.remove(j + 1);
                } else if (cur.equals("U") && nex.equals("D'") || cur.equals("D'") && nex.equals("U")) {
                    movesList.set(j, "y");
                    movesList.remove(j + 1);
                } else if (cur.equals("F'") && nex.equals("B") || cur.equals("B") && nex.equals("F'")) {
                    movesList.set(j, "z'");
                    movesList.remove(j + 1);
                } else if (cur.equals("F") && nex.equals("B'") || cur.equals("B'") && nex.equals("F")) {
                    movesList.set(j, "z");
                    movesList.remove(j + 1);
                } else j++;
                if (j > 0 && size > movesList.size()) {
                    j--;
                }
            } else j++;
        }
        return String.join("", movesList);
    }
}
