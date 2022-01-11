package solving2x2;

import cubes.Cube2x2;
import org.javatuples.Pair;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

public class FwmAsc implements Callable {

    private final String[] ALL_POSSIBLE_MOVES = {"U", "U2", "U'", "R", "R2", "R'", "F", "F2", "F'"};
    private final Set FINAL_ENTROPIES = new HashSet<>(Arrays.asList(4, 12, 14, 18, 20, 22, 26, 30, 32, 36, 42, 44));

    private final Cube2x2 initialCube;
    private Cube2x2 tempCube;
    private int godsNumber = 1;
    private int currentLength;
    private final HashMap<Integer, HashSet<String>> movesDone;
    private final AtomicInteger expectedLength; // of whole alg, with initial move

    FwmAsc(Cube2x2 cube, AtomicInteger expectedLength) {
        this.tempCube = new Cube2x2(cube);
        this.initialCube = cube;
        currentLength = 0;
        movesDone = new HashMap<>();
        movesDone.put(1, new HashSet<>());
        this.expectedLength = expectedLength;
    }

    @Override
    public String call() {
        String alg = null;
        while (godsNumber < expectedLength.get() && alg == null) {
            alg = findFewestMoves();
            currentLength = 0;
            godsNumber++;
            tempCube = new Cube2x2(initialCube);
            movesDone.clear();
            for (int i = 1; i <= godsNumber; i++) {
                movesDone.put(i, new HashSet<>());
            }
        }
        if (alg!=null) {
            expectedLength.set(godsNumber--);
        }
        return Objects.requireNonNullElse(alg, "Error");
    }

    private String findFewestMoves() {
        StringBuilder builder = new StringBuilder();
        long counter = 0;
        long start = System.currentTimeMillis();

        while (!tempCube.isSolved()) {
            if (currentLength >= expectedLength.get() || (currentLength == 0 && movesDone.get(1).size() == 9)) {
                // faster solution already found or all combinations checked
                return null;
            }
            else if ((currentLength>0 && godsNumber-currentLength<=3 && !FINAL_ENTROPIES.contains(calculateEntropy())) || currentLength == godsNumber) {
                // if next three moves won't end up in solved cube due to wrong entropy
                // if god's number reached and movesDone(GOD_NUM) is not-full just try another move
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
                tempCube.move(curMove);
                builder.append(curMove);
                currentLength++;
                movesDone.get(currentLength).add(curMove);
            }

            counter++;
            if (counter % 300_000 == 0) {
                final double curTime = (System.currentTimeMillis() - start) / 1000.0;
                if (curTime > 2.0) {
                    return null;
                }
            }
        }
        return builder.toString();
    }

    private void deleteLatestMove(StringBuilder builder) {
        final int lastIndex = builder.length() - 1;
        if (builder.charAt(lastIndex)==50 || builder.charAt(lastIndex)==39) {
            reverseMove(builder.substring(lastIndex - 1));
            builder.delete(lastIndex - 1, lastIndex + 1);
        }
        else {
            reverseMove(builder.substring(lastIndex));
            builder.deleteCharAt(lastIndex);
        }
    }

    private void reverseMove(String rev) {
        if (rev.contains("'")) {
            rev = rev.substring(0, 1);
        }
        else if (!rev.contains("2")){
            rev += "'";
        }
        tempCube.move(rev);
    }

    private String getMoveWitheBestEntropy(String alg) {
        /*
          if (curE > entropy) return m
          this is faster when solution is not straight forward,
          entropy is not returning best solutions
         */
        int entropy = -1;
        String move = "";
        String end = getLastMove(alg);
        for (String m : ALL_POSSIBLE_MOVES) {
            // if move already done  || are like or R - R2 etc. -> next move
            if (!movesDone.get(currentLength + 1).contains(m) && (end.equals("") || m.charAt(0) != end.charAt(0))) {
                tempCube.move(m);
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
        int entropy = 0;
        int[][] array = tempCube.getArray();
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
                /*               1st     2nd
                  fullWallUni  = 6       6
                  fullWall     = 5       5
                  threeWallUni = 4       3
                  threeWall    = 3       2
                  twoWallUni   = 2       4
                  twoWall      = 1       1
                 */
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