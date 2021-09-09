package cubes;

import org.javatuples.Pair;

import java.util.*;

//TODO     https://www.speedsolving.com/wiki/index.php/Kociemba's_Algorithm
public class QuickestSolve {

    private final int GODS_NUMBER = 11;
    private final String[] POSSIBLE_MOVES = {"U", "U2", "U'", "R", "R2", "R'", "F", "F2", "F'", "D", "D2", "D'", "L", "L2", "L'", "B", "B2", "B'"};
    private Cube2x2 cube;

    private HashMap<Integer, ArrayList<String>> movesDone;

    public QuickestSolve(Cube2x2 cube) {
        this.cube=cube;
        movesDone = new HashMap<Integer, ArrayList<String>>();
        for (int i = 1; i <= GODS_NUMBER; i++) {
            movesDone.put(i, new ArrayList<String>());
        }
    }

    public void findQuickestSolve() {
        //TODO list od solutions???
        String scramble = Algorithm.randomScramble(10, 15);
        System.out.println(scramble);
        cube.moveCube(scramble);
        String alg = solveFewestMoves();
        System.out.println(alg);
    }

    private String solveFewestMoves() {
        StringBuilder builder = new StringBuilder();

        while (!cube.isSolved()) {
            if (currentLength(builder.toString()) == 11) {
                /**
                 * If movesDone(11) is not-full just try another move
                 * if it's full next 'if' block take care(hope so xd)
                 * B'R2F2
                 * F'U2FB'FB'U2FB2U2F
                 */
                deleteLatestMove(builder);
            }

            String curMove = getMoveWitheBestAvailableEntropy(builder.toString());

            if (builder.length() > 0 && movesDone.get(currentLength(builder.toString())+1).size()==POSSIBLE_MOVES.length-3) {
                /**
                 * TODO getMoveWitheBestAvailableEntropy just return "" ???
                 * current length of alg is n, n+1 moves ware all were used
                 * clear array of current move looked for
                 * undo last move and delete from builder
                 */
                movesDone.get(currentLength(builder.toString())+1).clear();
                deleteLatestMove(builder);
            }
            else {
                cube.moveCube(curMove);
                builder.append(curMove);
                movesDone.get(currentLength(builder.toString())).add(curMove);
            }
        }

        return builder.toString();
    }

    private void deleteLatestMove(StringBuilder builder) {
        final int lastIndex = builder.length() - 1;
        if (builder.charAt(lastIndex)=='\'' || builder.charAt(lastIndex)=='2'){
            reverseMove(builder.substring(lastIndex-1));
            builder.delete(lastIndex-1, lastIndex+1);

        }
        else {
            reverseMove(builder.substring(lastIndex));
            builder.deleteCharAt(lastIndex);
        }
    }

    private int currentLength(String alg) {
        final long count = alg.chars().filter(Character::isLetter).count();
        return (int) count;
    }

    private String getMoveWitheBestAvailableEntropy(String alg) {
        int e = -1;
        String move = "";
        String end = getLastMove(alg);
        for (String m : POSSIBLE_MOVES) {
            if (end.contains(String.valueOf(m.charAt(0)))){
                // if are like U - U2 etc.
            }
            else if (areMovesParallel(m, end)){
                // if are like R' - L2 etc.
            }
            else if(!movesDone.get(currentLength(alg) + 1).contains(m)){
                cube.moveCube(m);
                int curE = calculateEntropy(cube);
                if (curE > e) {
                    e = curE;
                    move = m;
                }
                reverseMove(m);
            }
        }
        return move;
    }

    private boolean areMovesParallel(String m, String end) {
        if (end.length()==0 || m.length()==0){
            return false;
        }
        HashSet<Character> set = new HashSet<>();
        set.add(m.charAt(0));
        set.add(end.charAt(0));
        if (set.contains('R') && set.contains('L')){
            return true;
        }
        else if (set.contains('U') && set.contains('D')){
            return true;
        }
        else if (set.contains('F') && set.contains('B')){
            return true;
        }
        else
            return false;
    }

    private String getLastMove(String alg) {
        if (alg.length()==0){
            return "";
        }
        else if (alg.endsWith("'") || alg.endsWith("2")){
            return alg.substring(alg.length()-2);
        }
        else return alg.substring(alg.length()-1);
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

    private int calculateEntropy(Cube2x2 cube2x2) {
        int entropy = 0;
        //TODO change points???
        /**
         * fullWallUni  = 6
         * fullWall     = 5
         * threeWallUni = 4
         * threeWall    = 3
         * twoWallUni   = 2
         * twoWall      = 1
         */
        int[][] cube = cube2x2.getCube();
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
                    entropy += 4;
                }

                if (numOfStickers.size() == 4) {
                    entropy += 4;
                } else if (numOfStickers.size() == 3) {
                    entropy += 3;
                } else if (numOfStickers.size() == 2 && Math.abs(numOfStickers.get(0) - numOfStickers.get(1)) != 2) {
                    if (wall[numOfStickers.get(0)] == c0 && wall[numOfStickers.get(1)] == c0) {
                        entropy += 2;
                    } else if (wall[numOfStickers.get(0)] == c1 && wall[numOfStickers.get(1)] == c1) {
                        entropy += 2;
                    } else {
                        entropy += 1;
                    }
                }
            }
        }
        return entropy;
    }
}