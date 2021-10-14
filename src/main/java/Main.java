
import cubes.*;
import graphicalMenu.GuiMenu;
import solving.QuickestSolveThreads;

import java.util.concurrent.*;

//TODO skipRotations: from start, recursion
//TODO Cube2x2: abstract class, general for cubs  or only NxNxN
//TODO loggers, tests
//TODO graphic: move & scramble as one method, empty field etc.
//TODO history window in graphics on right
//TODO change graphics?
public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        g();
    }

    private static void t() throws ExecutionException, InterruptedException {
        String scramble = Algorithm.randomScramble(13, 15);
        System.out.println(scramble);
        QuickestSolveThreads quickestSolveThreads = new QuickestSolveThreads(scramble);
        quickestSolveThreads.findQuickestSolutions();
    }

    private static void g(){
        Cube2x2 cube2x2 = new Cube2x2();
        new GuiMenu(cube2x2);
    }

}