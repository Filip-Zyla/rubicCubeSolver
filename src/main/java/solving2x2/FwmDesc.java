package solving2x2;

import cubes.Cube2x2;
import org.javatuples.Pair;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

public class FwmDesc implements Callable {

    private final String[] ALL_POSSIBLE_MOVES = {"U", "U2", "U'", "R", "R2", "R'", "F", "F2", "F'"};
    private final Set finalEntropy = new HashSet<>(Arrays.asList(4, 12, 14, 18, 20, 22, 26, 30, 32, 36, 42, 44));

    private Cube2x2 cube;
    private int godsNumber = 10;
    private int currentLength;
    private HashMap<Integer, HashSet<String>> movesDone;
    private AtomicInteger expectedLength; // of whole alg, with initial move
    private StringBuilder builder = new StringBuilder();

    public FwmDesc(Cube2x2 cube, AtomicInteger expectedLength) {
        this.cube = new Cube2x2(cube);
        currentLength = 0;
        movesDone = new HashMap<>();
        for (int i = 1; i <= godsNumber; i++) {
            movesDone.put(i, new HashSet<>());
        }
        this.expectedLength = expectedLength;
    }

    @Override
    public String call() {
        String alg = null;
        godsNumber = expectedLength.get()-1;
        while (godsNumber < expectedLength.get() && godsNumber >0) {
            String tempAlg = findFewestMoves();
            if(tempAlg!=null && currentLength<expectedLength.get()){
                alg=tempAlg;
                expectedLength.set(currentLength);
                for (int i = currentLength; i <= godsNumber; i++) {
                    movesDone.remove(i);
                }
                currentLength--;
                deleteLatestMove(builder);
                godsNumber=currentLength;
            }
            else if (tempAlg==null && currentLength>0){
                movesDone.get(godsNumber).clear();
                currentLength--;
                deleteLatestMove(builder);
                godsNumber=currentLength;
            }
            else {
                break;
            }
        }
        System.out.println("DESC "+Thread.currentThread().getName()+" "+alg);
        return Objects.requireNonNullElse(alg, "Error");
    }

    private String findFewestMoves() {
        long counter = 0;
        long start = System.currentTimeMillis();

        while (!cube.isSolved()) {
            if (currentLength >= expectedLength.get() || (currentLength == 0 && movesDone.get(1).size() == 9)) {
                // faster solution already found or all combinations checked
                return null;
            }
            else if ((godsNumber-currentLength<=3 && !finalEntropy.contains(calculateEntropy())) || currentLength == godsNumber) {
                //if god's number reached and movesDone(GOD_NUM) is not-full just try another move
                deleteLatestMove(builder);
                currentLength--;
            }
            else if (builder.length() > 0 && movesDone.get(currentLength + 1).size() == ALL_POSSIBLE_MOVES.length - 3) {
                /*
                 * currentLength is n, n+1 moves were all used
                 * clear array of current move looked for, n+1
                 * undo last move and delete from builder at n
                 */
                movesDone.get(currentLength + 1).clear();
                deleteLatestMove(builder);
                currentLength--;
            }
            else {
                String curMove = getMoveWitheBestEntropy(builder.toString());
                cube.move(curMove);
                builder.append(curMove);
                if (curMove!=null)
                    currentLength++;
                movesDone.get(currentLength).add(curMove);
            }
            counter++;
            if (counter % 1_000_000 == 0) {
                // all combinations is 9 * 6^10 = 550mln combinations, period last ~2.2, maxTime = 550*2.2=1210sec
                final double curTime = (System.currentTimeMillis() - start) / 1000.0;
                System.out.println(curTime);
                if (curTime > 30) {
                    System.out.println("Took more than 30s, aborting...");
                    return null;
                }
            }
        }
        return builder.toString();
    }

    private void deleteLatestMove(StringBuilder builder) {
        final int lastIndex = builder.length() - 1;
        if (!Character.isLetter(builder.charAt(lastIndex))) {
            reverseMove(builder.substring(lastIndex - 1));
            builder.delete(lastIndex - 1, lastIndex + 1);
        }
        else {
            reverseMove(builder.substring(lastIndex));
            builder.deleteCharAt(lastIndex);
        }
    }

    private void reverseMove(String rev) {
        /**
         * rev is move that have been done
         */
        if (rev.contains("2")) {
        }
        else if (rev.contains("'")) {
            rev = rev.substring(0, 1);
        }
        else {
            rev += "'";
        }
        cube.move(rev);
    }

    private String getMoveWitheBestEntropy(String alg) {
        int entropy = -1;
        String move = "";
        String end = getLastMove(alg);
        for (String m : ALL_POSSIBLE_MOVES) {
            if (movesDone.get(currentLength + 1).contains(m)) {
                // if move already done
            }
            else if (!end.equals("") && m.charAt(0) == end.charAt(0)) {
                // if are like or R - R2 etc.
            }
            else {
                cube.move(m);
                int curE = calculateEntropy();
                if (curE > entropy) {
                    entropy = curE;
                    move = m;
                }
                reverseMove(m);
            }
        }
        return move;
    }

    private String getLastMove(String alg) {
        if (alg.length() == 0) {
            return "";
        }
        else if (alg.endsWith("'") || alg.endsWith("2")) {
            return alg.substring(alg.length() - 2);
        }
        else
            return alg.substring(alg.length() - 1);
    }

    private int calculateEntropy() {
        /**               1st     2nd
         * fullWallUni  = 6       6
         * fullWall     = 5       5
         * threeWallUni = 4       3
         * threeWall    = 3       2
         * twoWallUni   = 2       4
         * twoWall      = 1       1
         */
        int entropy = 0;
        int[][] array = cube.getArray();
        List<Pair<Integer, Integer>> walls = Arrays.asList(new Pair<>(0, 2), new Pair<>(2, 0), new Pair<>(2, 2), new Pair<>(2, 4), new Pair<>(2, 6), new Pair<>(4, 2));
        List<Pair<Integer, Integer>> colors = Arrays.asList(new Pair<>(0, 5), new Pair<>(1, 4), new Pair<>(2, 3));

        for (Pair w : walls) {
            int x = (int) w.getValue0();
            int y = (int) w.getValue1();
            int[] wall = {array[x][y], array[x][y + 1], array[x + 1][y + 1], array[x + 1][y]};

            for (Pair c : colors) {

                int c0 = (int) c.getValue0();
                int c1 = (int) c.getValue1();
                int stickersC0 = 0;
                int stickersC1 = 0;
                int stickersC = 0;
                int stickersSumC=0;

                for (int i = 0; i < wall.length; i++) {
                    if (wall[i] == c0) {
                        stickersC0++;
                        stickersC++;
                        stickersSumC+=i;
                    }
                    if (wall[i] == c1) {
                        stickersC1++;
                        stickersC++;
                        stickersSumC+=i;
                    }
                }

                if (stickersC0 == 4 || stickersC1 == 4) {
                    entropy += 6; // fullWallUni
                }
                else if (stickersC == 4) {
                    entropy += 5; // fullWall
                }
                else if (stickersC0 == 3 || stickersC1 == 3) {
                    entropy += 3; // threeWallUni
                }
                else if (stickersC == 3) {
                    entropy += 2; // threeWall
                }
                else if (stickersSumC%2 == 1) {
                    if (stickersC0 == 2 || stickersC1 == 2) {
                        entropy += 4; // twoWallUni
                    }
                    else if (stickersC == 2) {
                        entropy += 1; // twoWall
                    }
                }
            }
        }
        return entropy;
    }
}