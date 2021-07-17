package cubes;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Algorithm {

    public static String randomScramble(int min, int max) {
        String poll = "UDRLFB";
        Random r = new Random();
        StringBuilder builder = new StringBuilder();
        int ran = (int) (Math.random() * (max - min + 1) + min); // from min to max

        for (int i = 0; i < ran; i++) {
            char ch = poll.charAt(r.nextInt(poll.length()));

            if(i>1 && !Character.isLetter(builder.charAt(i-1)) && builder.charAt(i-2)==ch){
                // avoid repetitions lik D2D
                while (builder.charAt(i-2)==ch)
                    ch=poll.charAt(r.nextInt(poll.length()));
            }
            while (i>0 && builder.charAt(i-1)==ch)
                // avoid repetitions like DD
                ch=poll.charAt(r.nextInt(poll.length()));
            builder.append(ch);
            switch (r.nextInt(poll.length()) % 8) {
                case 0 -> {
                    builder.append("2");
                    ran++; i++;
                }
                case 1,5 -> {
                    builder.append("'");
                    ran++; i++;
                }
            }
        }
        return optimizeAlg(builder.toString());
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
            if (i+1<array.length && (array[i+1]==39 || Character.isDigit(array[i+1])))
                i=i+2;
            else i++;
        }

        return true;
    }

    public static String optimizeAlg(String alg){
        /*
          case 1 : two or three repetitions etc DD DDD D2D D'DD
          case 2 : R"L type to rotate
         */
        List<String> algList = new LinkedList<>();
        String[] algArr = alg.split("");
        int i=0;
        while (i<alg.length()){
            if (i+1==alg.length()){
                algList.add(algArr[i]);
                i++;
            }
            else if (algArr[i+1].matches("[A-Za-z]")){
                algList.add(algArr[i]);
                i++;
            }
            else {
                algList.add(algArr[i]+algArr[i+1]);
                i+=2;
            }
        }

        int j=0;
        while (j<algList.size()){
            if (j+1==algList.size()){
                break;
            }
            String cur = algList.get(j);
            String nex = algList.get(j+1);
            if (cur.charAt(0)==nex.charAt(0)){
                String combined = (cur+nex).replaceAll("[A-Za-z]", "");
                String s = String.valueOf(algList.get(j).charAt(0));
                switch (combined){
                    case "", "''" -> {algList.set(j, s+"2"); algList.remove(j+1);}
                    case "'", "22" -> {algList.remove(j+1); algList.remove(j);}
                    case "2" -> {algList.set(j, s+"'"); algList.remove(j+1);}
                    case "2'", "'2" -> {algList.set(j, s); algList.remove(j+1);}
                }
                if (j>0){
                    j--;
                }
            }
            else if ((cur+nex).contains("'")) {
                int size = algList.size();
                if (cur.equals("R'") && nex.equals("L") || cur.equals("L") && nex.equals("R'")){
                    algList.set(j, "x'"); algList.remove(j+1);
                }
                else if (cur.equals("R") && nex.equals("L'") || cur.equals("L'") && nex.equals("R")){
                    algList.set(j, "x"); algList.remove(j+1);
                }
                else if (cur.equals("U'") && nex.equals("D") || cur.equals("D") && nex.equals("U'")){
                    algList.set(j, "y'"); algList.remove(j+1);
                }
                else if (cur.equals("U") && nex.equals("D'") || cur.equals("D'") && nex.equals("U")){
                    algList.set(j, "y"); algList.remove(j+1);
                }
                else if (cur.equals("F'") && nex.equals("B") || cur.equals("B") && nex.equals("F'")){
                    algList.set(j, "z'"); algList.remove(j+1);
                }
                else if (cur.equals("F") && nex.equals("B'") || cur.equals("B'") && nex.equals("F")){
                    algList.set(j, "z"); algList.remove(j+1);
                }
                else j++;
                if (j>0 && size>algList.size()){
                    j--;
                }
            }
            else j++;
        }
        return String.join("", algList);
    }
}
