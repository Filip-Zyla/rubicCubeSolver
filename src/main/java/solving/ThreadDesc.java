package solving;

import cubes.Cube2x2;
import org.javatuples.Pair;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

//TODO is 9 0 0 0 all moves???
public class ThreadDesc   {

    private int GODS_NUMBER = 11;
    private final String[] ALL_POSSIBLE_MOVES = {"U", "U2", "U'", "R", "R2", "R'", "F", "F2", "F'"};

    private final Cube2x2 initialCube;
    private Cube2x2 cube;
    private int currentLength;
    private HashMap<Integer, HashSet<String>> movesDone;
    StringBuilder builder = new StringBuilder();

    public ThreadDesc(Cube2x2 cube) {
        this.initialCube = cube;
        this.cube = new Cube2x2(cube);
        currentLength = 0;
        movesDone = new HashMap<>();
        for (int i = 1; i <= GODS_NUMBER; i++) {
            movesDone.put(i, new HashSet<>());
        }
    }

    public String solve() {
        String alg = null;
        while (GODS_NUMBER>0) {
            String tempAlg = findFewestMoves();
            System.out.println("Ending at length " + GODS_NUMBER + ", alg is " + alg);
            for (int i=1; i<=movesDone.size(); i++){
                System.out.print(movesDone.get(i).size() + " ");
            }
            System.out.println();
            if(tempAlg!=null){
                alg=tempAlg;
            }
            if (currentLength==GODS_NUMBER){
                currentLength--;
                deleteLatestMove(builder);
            }
            movesDone.remove(GODS_NUMBER);
            GODS_NUMBER--;
        }
        return Objects.requireNonNullElse(alg, "Error");
    }

    private String findFewestMoves() {

        long counter = 0;
        long start = System.currentTimeMillis();

        while (!cube.isSolved()) {
            if (currentLength == 0 && movesDone.get(1).size() == 9) {
                //all possible combination are checked
                return null;
            }
            else if (currentLength == GODS_NUMBER) {
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
                currentLength++;
                movesDone.get(currentLength).add(curMove);
            }
            counter++;
            if (counter % 1_000_000 == 0) {
                // all combinations is 9 * 6^10 = 550mln combinations, period last ~2.2, maxTime = 550*2.2=1210sec
                final long curTime = (System.currentTimeMillis() - start) / 1000;
                //System.out.println(curTime);
                if (curTime > 300) {
                    System.out.println("Took more than 300s, aborting...");
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
                int curE = calculateEntropy(cube);
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

    private int calculateEntropy(Cube2x2 cube2x2) {
        int entropy = 0;
        /**               1st     2nd
         * fullWallUni  = 6       6
         * fullWall     = 5       5
         * threeWallUni = 4       3
         * threeWall    = 3       2
         * twoWallUni   = 2       4
         * twoWall      = 1       1
         */
        int[][] cube = cube2x2.getArray();
        List<Pair<Integer, Integer>> walls = Arrays.asList(new Pair<>(0, 2), new Pair<>(2, 0), new Pair<>(2, 2), new Pair<>(2, 4), new Pair<>(2, 6), new Pair<>(4, 2));
        List<Pair<Integer, Integer>> colors = Arrays.asList(new Pair<>(0, 5), new Pair<>(1, 4), new Pair<>(2, 3));

        for (Pair w : walls) {
            int x = (int) w.getValue0();
            int y = (int) w.getValue1();
            int[] wall = {cube[x][y], cube[x][y + 1], cube[x + 1][y + 1], cube[x + 1][y]};

            for (Pair c : colors) {

                int c0 = (int) c.getValue0();
                int c1 = (int) c.getValue1();
                List<Integer> numOfStickersC0 = new ArrayList<>();
                List<Integer> numOfStickersC1 = new ArrayList<>();
                List<Integer> numOfStickers = new ArrayList<>();

                for (int i = 0; i < wall.length; i++) {
                    if (wall[i] == c0) {
                        numOfStickersC0.add(i);
                        numOfStickers.add(i);
                    }
                    if (wall[i] == c1) {
                        numOfStickersC1.add(i);
                        numOfStickers.add(i);
                    }
                }

                if (numOfStickersC0.size() == 4 || numOfStickersC1.size() == 4) {
                    entropy += 6; // fullWallUni
                }
                else if (numOfStickersC0.size() == 3 || numOfStickersC1.size() == 3) {
                    entropy += 3; // threeWallUni
                }
                if (numOfStickers.size() == 4) {
                    entropy += 3; // fullWall
                }
                else if (numOfStickers.size() == 3) {
                    entropy += 2; // threeWall
                }
                else if (numOfStickers.size() == 2 && Math.abs(numOfStickers.get(0) - numOfStickers.get(1)) != 2) {
                    if (wall[numOfStickers.get(0)] == c0 && wall[numOfStickers.get(1)] == c0) {
                        entropy += 4; // twoWallUni
                    }
                    else if (wall[numOfStickers.get(0)] == c1 && wall[numOfStickers.get(1)] == c1) {
                        entropy += 4; // twoWallUni
                    }
                    else {
                        entropy += 1; // twoWall
                    }
                }
            }
        }
        return entropy;
    }
}