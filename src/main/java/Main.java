
import cubes.*;
import graphicalMenu.GraphMenu;

public class Main {

    public static void main(String[] args) {
        b();
    }

    private static void b() {
        Cube2x2 cube = new Cube2x2();
        new GraphMenu(cube);
    }

    private static void a() {
        Cube2x2 cube = new Cube2x2();
        String scramble = Algorithm.randomScramble(10,15);
        cube.moveCube(scramble);
        System.out.println("Scramble " + scramble);
        OrtegaSolveMethod method = new OrtegaSolveMethod(cube);
        System.out.println("Solve " + method.solve());
    }
}