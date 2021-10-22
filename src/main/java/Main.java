
import cubes.*;
import files.HistoryFile;
import graphicalMenu.GuiMenu;
import solving2x2.FwmDesc;
import solving2x2.FwmExecutor;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        run();
    }

    private static void run() {
        HistoryFile.createFile();
        new GuiMenu(new Cube2x2());
    }

    //TODO another cubes NxN, pyraminx, square-1, using abstract classes
    //TODO tests
    //TODO delete: executor ending/timer
    //TODO executor waiting for all, returning not null
    //TODO asc and desc not doing same pre-moves && nThreads
    //TODO FwmAsc/Desc: methods

    private static void test() {
        String scramble = Algorithm.randomScramble(15, 16);
        System.out.println(scramble);
        FwmDesc test = new FwmDesc(new Cube2x2(scramble), new AtomicInteger(12));
        System.out.println(test.call());
    }

    private static void t() throws ExecutionException, InterruptedException {
        String scramble = Algorithm.randomScramble(13, 15);
        System.out.println(scramble);
        FwmExecutor quickestSolveThreads = new FwmExecutor(new Cube2x2(scramble), 4);
        System.out.println(quickestSolveThreads.fewestMoves());
    }

}