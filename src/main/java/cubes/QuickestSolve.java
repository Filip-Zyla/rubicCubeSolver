package cubes;

import org.javatuples.Pair;

import java.util.*;

//TODO https://www.speedsolving.com/wiki/index.php/Kociemba's_Algorithm
public class QuickestSolve {

    //TODO test and fix
    private final int GODS_NUMBER = 11;
    private final String[] ALL_POSSIBLE_MOVES = {"U", "U2", "U'", "R", "R2", "R'", "F", "F2", "F'", "D", "D2", "D'", "L", "L2", "L'", "B", "B2", "B'"};

    private Cube2x2 cube;
    private int currentLength;
    private HashMap<Integer, HashSet<String>> movesDone;

    public QuickestSolve(Cube2x2 cube) {
        this.cube = cube;
        currentLength = 0;
        movesDone = new HashMap<>();
        for (int i = 1; i <= GODS_NUMBER; i++) {
            movesDone.put(i, new HashSet<>());
        }
    }

    public void findQuickestSolve() {
        //TODO list od solutions?, optimizeAlg?
        String scramble = Algorithm.randomScramble(5, 7);
        System.out.println(scramble);
        cube.moveCube(scramble);
        String alg = solveFewestMoves();
        System.out.println(alg);
    }

    private String solveFewestMoves() {
        StringBuilder builder = new StringBuilder();

        long counter = 0;
        long start = System.currentTimeMillis();

        while (!cube.isSolved()) {
            if (currentLength == 11) {
                /**
                 * if god's number reached and movesDone(11) is not-full just try another move
                 */
                deleteLatestMove(builder);
            } else if (builder.length() > 0 && movesDone.get(currentLength + 1).size() == ALL_POSSIBLE_MOVES.length - 6) {
                /**
                 * TODO getMoveWitheBestEntropy just return "" ???
                 * current length of alg is n, n+1 moves ware all were used
                 * clear array of current move looked for
                 * undo last move and delete from builder
                 */
                movesDone.get(currentLength + 1).clear();
                deleteLatestMove(builder);
            }
            else {
                String curMove = getMoveWitheBestEntropy(builder.toString());


                cube.moveCube(curMove);
                builder.append(curMove);
                currentLength++;
                movesDone.get(currentLength).add(curMove);
            }


            counter++;
            if (counter % 1_000_000 == 0) {
                System.out.println((System.currentTimeMillis() - start) / 1000);
                // 1mln ~16s 15,31,47 then ~8s 8,16,24
            }
        }

        return builder.toString();
    }

    private void deleteLatestMove(StringBuilder builder) {
        final int lastIndex = builder.length() - 1;
        if (!Character.isLetter(builder.charAt(lastIndex))) {
            reverseMove(builder.substring(lastIndex - 1));
            builder.delete(lastIndex - 1, lastIndex + 1);
        } else {
            reverseMove(builder.substring(lastIndex));
            builder.deleteCharAt(lastIndex);
        }
        currentLength--;
    }

    private void reverseMove(String rev) {
        /**
         * rev is moved that have been done
         */
        if (rev.contains("2")) {
        } else if (rev.contains("'")) {
            rev = rev.substring(0, 1);
        } else {
            rev += "'";
        }
        cube.moveCube(rev);
    }

    private String getMoveWitheBestEntropy(String alg) {
        int entropy = -1;
        String move = "";
        String end = getLastMove(alg);
        for (String m : ALL_POSSIBLE_MOVES) {
            if (end.contains(String.valueOf(m.charAt(0)))) {
                // if are like U - U2 etc.
            } else if (areMovesParallel(m, end)) {
                // if are like R' - L2 etc.
            } else if (movesDone.get(currentLength + 1).contains(m)) {
                // if move already done
            } else {
                cube.moveCube(m);
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
        } else if (alg.endsWith("'") || alg.endsWith("2")) {
            return alg.substring(alg.length() - 2);
        } else
            return alg.substring(alg.length() - 1);
    }

    private boolean areMovesParallel(String m, String end) {
        if (end.length() == 0 || m.length() == 0) {
            return false;
        }
        HashSet<Character> set = new HashSet<>();
        set.add(m.charAt(0));
        set.add(end.charAt(0));
        if (set.contains('R') && set.contains('L')) {
            return true;
        } else if (set.contains('U') && set.contains('D')) {
            return true;
        } else if (set.contains('F') && set.contains('B')) {
            return true;
        } else
            return false;
    }

    private int calculateEntropy(Cube2x2 cube2x2) {
        int entropy = 0;
        //TODO change points???
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
                    entropy += 6;
                } else if (numOfStickersC0.size() == 3 || numOfStickersC1.size() == 3) {
                    entropy += 3;
                }

                if (numOfStickers.size() == 4) {
                    entropy += 3;
                } else if (numOfStickers.size() == 3) {
                    entropy += 2;
                } else if (numOfStickers.size() == 2 && Math.abs(numOfStickers.get(0) - numOfStickers.get(1)) != 2) {
                    if (wall[numOfStickers.get(0)] == c0 && wall[numOfStickers.get(1)] == c0) {
                        entropy += 4;
                    } else if (wall[numOfStickers.get(0)] == c1 && wall[numOfStickers.get(1)] == c1) {
                        entropy += 4;
                    } else {
                        entropy += 1;
                    }
                }
            }
        }
        return entropy;
    }
}