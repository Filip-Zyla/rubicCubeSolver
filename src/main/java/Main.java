
import cubes.*;
import graphicalMenu.GraphMenu;

public class Main {

    public static void main(String[] args) {
        a();
    }

    private static void c() {
        System.out.println(Algorithm.skipRotation("x'"));
        System.out.println(Algorithm.skipRotation("xU'"));
        System.out.println(Algorithm.skipRotation("Rxx"));
        System.out.println(Algorithm.skipRotation("UzDxF'y'B'"));
    }

    private static void b() {
        Cube2x2 cube = new Cube2x2();
        new GraphMenu(cube);
    }

    private static void a() {
        Cube2x2 cube = new Cube2x2();
        String scramble = Algorithm.randomScramble(10,15);
        cube.moveCube(scramble);
        //cube.moveCube("RDF'DL'BR'D'FD");
        //cube.moveCube("BU'BL'U'R2D'B2UB'UF'RU'B");
        System.out.println("Scramble " + scramble);
        OrtegaSolveMethod method = new OrtegaSolveMethod(cube);
        System.out.println("Solve " + method.solve());
    }
}