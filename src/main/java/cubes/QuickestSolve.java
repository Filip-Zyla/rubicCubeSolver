package cubes;

import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuickestSolve {
    private final int GODS_NUMBER = 14;
    private final String[] POSSIBLE_MOVES = {"U", "U2", "U'", "R", "R2", "R'", "F", "F2", "F'", "D", "D2", "D'", "L", "L2", "L'", "B", "B2", "B'"};

    private ArrayList<String> solutions = new ArrayList();

    //TODO     https://www.speedsolving.com/wiki/index.php/Kociemba's_Algorithm
    public void findQuickestSolve() {
        Cube2x2 cube = new Cube2x2();
        String scramble = Algorithm.randomScramble(3, 4);
        System.out.println(scramble);
        cube.moveCube(scramble);
        String fewestAlg = solveFewestMoves(cube);
        System.out.println(fewestAlg);
    }

    private String  solveFewestMoves(Cube2x2 cube){
        StringBuilder fewestAlgBuldier = new StringBuilder();
        int entropy = calculateEntropy(cube);

        int infiniteLoopFlag = 0;

        while (!cube.isSolved()){
            String curMove = getMoveWitheBestEntropy(cube);
            cube.moveCube(curMove);

            int curEntropy = calculateEntropy(cube);

            if (curEntropy>entropy){
                fewestAlgBuldier.append(curMove);
                entropy=curEntropy;
                infiniteLoopFlag=0;
            }
            else {
                reverseMove(cube, curMove);
                infiniteLoopFlag++;
            }

            if (infiniteLoopFlag>17 && fewestAlgBuldier.length()>0){
                String s = fewestAlgBuldier.toString();
                if (s.endsWith("2") || s.endsWith("'")){
                    reverseMove(cube, s.substring(s.length()-2));
                    s=s.substring(0, s.length()-2);
                }
                else {
                    reverseMove(cube, s.substring(s.length()-1));
                    s=s.substring(0, s.length()-1);
                }
                fewestAlgBuldier=new StringBuilder();
                fewestAlgBuldier.append(s);
            }

            System.out.println(fewestAlgBuldier);
        }

        return fewestAlgBuldier.toString();
    }

    private String getMoveWitheBestEntropy(Cube2x2 cube) {
        int e = -1;
        String move = "";
        for (String m: POSSIBLE_MOVES){
            cube.moveCube(m);
            int curE = calculateEntropy(cube);
            if (curE>e){
                e=curE;
                move=m;
            }
            reverseMove(cube, m);
        }
        return move;
    }

    private void reverseMove(Cube2x2 cube, String rev) {
        if (rev.contains("2")){
        }
        else if (rev.contains("'")){
            rev=rev.substring(0,1);
        }
        else {
            rev+="'";
        }
        cube.moveCube(rev);
    }

    private int calculateEntropy(Cube2x2 cube2x2) {
        int entropy = 0;
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
                    entropy+=6;
                }
                else if (numOfStickersC0.size() == 3 || numOfStickersC1.size() == 3) {
                    entropy+=4;
                }

                if (numOfStickers.size() == 4) {
                    entropy+=4;
                }
                else if (numOfStickers.size() == 3) {
                    entropy+=3;
                }
                else if (numOfStickers.size() == 2 && Math.abs(numOfStickers.get(0) - numOfStickers.get(1)) != 2) {
                    if (wall[numOfStickers.get(0)] == c0 && wall[numOfStickers.get(1)] == c0) {
                        entropy+=2;
                    }
                    else if (wall[numOfStickers.get(0)] == c1 && wall[numOfStickers.get(1)] == c1) {
                        entropy+=2;
                    }
                    else {
                        entropy+=1;
                    }
                }
            }
        }
        return entropy;
    }
}
