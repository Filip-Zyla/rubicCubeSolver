package cubes;

import java.util.ArrayList;

public class QuickestSolve {
    private final int GODS_NUMBER = 14;
    private final String[] POSSIBLE_MOVES = {"U", "U'", "R", "R'", "F", "F'", "D", "D'", "L", "L'", "B", "B'"};

    private ArrayList<String> solutions = new ArrayList();

    public void findQuickestSolve() {
        Cube2x2 cube = new Cube2x2();

        String scramble = "RU"; //Algorithm.randomScramble(15, 20);
        cube.moveCube(scramble);

        moveCube(scramble, cube);

        System.out.println(scramble);
        System.out.println("Solutions: ");
        for (String s:solutions){
            System.out.println(s);
        }

    }

    private void moveCube(String alg, Cube2x2 cube){
        int counter = 1;
        while (counter<GODS_NUMBER){
            if (!solutions.isEmpty()){
                return;
            }
            checkAlgsOfGivenLength(counter, cube, alg);
        }
    }

    private void checkAlgsOfGivenLength(int counter, Cube2x2 cube, String alg) {
        for (String s1:POSSIBLE_MOVES){
            for (String s2:POSSIBLE_MOVES){
                if (!solutions.isEmpty()){
                    return;
                }
                cube = new Cube2x2();
                cube.moveCube(alg+s1+s2);
                if (cube.isSolved()){
                    solutions.add(s1+s2);
                }
            }
        }
    }

}
