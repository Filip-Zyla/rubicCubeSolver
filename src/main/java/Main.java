
import cubes.*;
import graphicalMenu.GraphMenu;
import solving.QuickestSolveThreads;

import java.util.concurrent.*;

//TODO skipRotations: from start, recursion
//TODO Cube2x2: abstract class, general for cubs  or only NxNxN
//TODO loggers, tests

//TODO history window in graphics
public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        final int POLL = 1_000_000;
        String scramble = Algorithm.randomScramble(30,30);

        Cube2x2 cube1 = new Cube2x2();
        long start1 = System.currentTimeMillis();
        for (int i = 0; i < POLL; i++) {
            cube1.move(scramble);
        }
        long end1 = System.currentTimeMillis();
        long time1 = (end1-start1);

        Cube2x2 cube2 = new Cube2x2();
        long start2 = System.currentTimeMillis();
        for (int i = 0; i < POLL; i++) {
            cube2.move(scramble);
        }
        long end2 = System.currentTimeMillis();
        long time2 = (end2-start2);

        System.out.println(scramble);
        System.out.println("Time old = "+ time1);
        System.out.println("Time new = "+ time2);
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