
import cubes.*;
import graphicalMenu.GraphMenu;

//TODO abstract class, general for cubs  or only NxNxN
//TODO loggers everywhere, tests everywhere
public class Main {

    public static void main(String[] args) {
        g();
    }

    private static void q() {
        Cube2x2 cube2x2 = new Cube2x2();
        QuickestSolve solve = new QuickestSolve(cube2x2);
        solve.findQuickestSolve();
    }

    private static void g(){
        Cube2x2 cube2x2 = new Cube2x2();
        new GraphMenu(cube2x2);
    }
}