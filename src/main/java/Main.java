
import cubes.*;
import files.HistoryFile;
import graphicalMenu.GuiMenu;
import solving.FewestMovesDesc;
import solving.FwmThreads;
import solving.FwmThreadsDesc;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

//TODO tests
//TODO Cube2x2: abstract class, general for cubs  or only NxNxN
//TODO Fwm's: delete? counters, timers and sout's
//TODO FwmThreads: nThreads
//TODO FewestMoves: solveFewestMoves and inner methods & change points for entropy or method
//TODO ThreadsDesc: if gods num bigger than atomic, return or change gods num to atmomic-1
//TODO is 9 0 0 0 all moves???
public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        tDesc();
    }

    private static void tDesc() {
        String scramble = Algorithm.randomScramble(13, 15);
        System.out.println(scramble);
        FewestMovesDesc fewestMovesDesc = new FewestMovesDesc(new Cube2x2(scramble), new AtomicInteger(11));
        System.out.println(fewestMovesDesc.call());
    }

    private static void t() throws ExecutionException, InterruptedException {
        String scramble = Algorithm.randomScramble(13, 15);
        System.out.println(scramble);
        FwmThreadsDesc quickestSolveThreads = new FwmThreadsDesc(new Cube2x2(scramble), 4);
        quickestSolveThreads.fewestMoves();
    }

    private static void g() {
        Cube2x2 cube2x2 = new Cube2x2();
        new GuiMenu(cube2x2);
    }

}