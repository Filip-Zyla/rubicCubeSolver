
import cubes.*;
import org.javatuples.Pair;
import solving.FewestMovesAsc;
import solving.FewestMovesDesc;
import solving.FwmThreadsExecutor;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

//TODO another cubes nxn, pyraminx, square-1
//TODO tests
//TODO Fwm's: delete? counters, timers and sout's, nThreads
//TODO FewestMoves: methods
public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        t();
    }

    private static void test() {
        String scramble = Algorithm.randomScramble(15, 16);
        System.out.println(scramble);
        FewestMovesDesc test = new FewestMovesDesc(new Cube2x2(scramble), new AtomicInteger(12));
        System.out.println(test.call());
    }

    private static void t() throws ExecutionException, InterruptedException {
        String scramble = Algorithm.randomScramble(13, 15);
        System.out.println(scramble);
        FwmThreadsExecutor quickestSolveThreads = new FwmThreadsExecutor(new Cube2x2(scramble), 4);
        System.out.println(quickestSolveThreads.fewestMoves());
    }

}