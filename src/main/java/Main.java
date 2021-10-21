
import cubes.*;
import solving.FewestMovesDesc;
import solving.FwmThreadsExecutor;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

//TODO another cubes nxn, pyraminx, square-1
//TODO tests
//TODO Fwm's: delete? counters, timers and sout's, nThreads
//TODO FewestMoves: methods
//TODO entropy -3moves level
//TODO "F2RFR2F'L'F'R'D'LF2U'R2D"
public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        String scramble = Algorithm.randomScramble(15,16);
        System.out.println(scramble);
        FewestMovesDesc test = new FewestMovesDesc(new Cube2x2(scramble), new AtomicInteger(11));
        System.out.println(test.call());
    }

    private static void t() throws ExecutionException, InterruptedException {
        String scramble = Algorithm.randomScramble(13, 15);
        System.out.println(scramble);
        FwmThreadsExecutor quickestSolveThreads = new FwmThreadsExecutor(new Cube2x2(scramble), 1);
        System.out.println(quickestSolveThreads.fewestMoves());
    }
}