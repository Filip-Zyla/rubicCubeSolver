
import cubes.*;
import files.HistoryFile;
import graphicalMenu.GuiMenu;
import solving2x2.FwmAsc;
import solving2x2.FwmDesc;
import solving2x2.FwmExecutor;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        t();
    }

    private static void run() {
        HistoryFile.createFile();
        new GuiMenu(new Cube2x2());
    }

    //TODO another cubes NxN, pyraminx, square-1, using abstract classes
    //TODO tests
    //TODO nThreads && moves_poss
    //TODO getMoveWitheBestEntropy reutrn move at first  curE > entropy && entropy points
    //TODO lastMoce?

    private static void t() throws ExecutionException, InterruptedException {
        String scramble = Algorithm.randomScramble(13, 15);
        System.out.println(scramble);
        FwmExecutor quickestSolveThreads = new FwmExecutor(new Cube2x2(scramble), 3);
        System.out.println(quickestSolveThreads.fewestMoves());
    }

}