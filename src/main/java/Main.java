
import cubes.*;
import graphicalMenu.GraphMenu;

//TODO test for everything
public class Main {

    public static void main(String[] args) {
    }

    private static void q() {
        Cube2x2 cube2x2 = new Cube2x2();
        QuickestSolve solve = new QuickestSolve(cube2x2);
        solve.findQuickestSolve();
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
        for (int i = 0; i < 100_000; i++) {
            Cube2x2 cube = new Cube2x2();
            String scramble = Algorithm.randomScramble(10, 15);
            cube.moveCube(scramble);
            System.out.print("Scramble " + scramble + "   Solve:");
            OrtegaSolveMethod method = new OrtegaSolveMethod(cube);
            System.out.print(method.solve());
            System.out.print("\n");
        }
    }
}