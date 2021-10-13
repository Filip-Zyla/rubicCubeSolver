
import cubes.*;
import graphicalMenu.GraphMenu;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

//TODO abstract class, general for cubs  or only NxNxN
//TODO loggers everywhere, tests everywhere, change graphics
//TODO history window in graphics
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
        new GraphMenu(cube2x2);
    }

}