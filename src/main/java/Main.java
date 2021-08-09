import cubes.*;
import graphicalMenu.graphMenu;

public class Main {

    public static void main(String[] args) {
//        new graphMenu(new Cube2x2());
        Cube2x2 cube2x2 = new Cube2x2();
        for (int i = 0; i < 1_000_000; i++) {
            final String alg = Algorithm.randomScramble(10, 15);
            System.out.println(alg);
            cube2x2.moveCube(alg);
            cube2x2.solve();
            if (cube2x2.isSolved()) {
                System.out.println(i);
            }
        }
    }

}