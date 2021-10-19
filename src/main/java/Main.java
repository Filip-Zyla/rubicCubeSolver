
import cubes.*;
import solving.FwmThreadsExecutor;

import java.util.concurrent.*;

//TODO another cubes nxn, pyraminx, square-1
//TODO tests
//TODO Fwm's: delete? counters, timers and sout's
//TODO FwmThreads: nThreads
//TODO FewestMoves: methods
public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        t();
    }

    private static void t() throws ExecutionException, InterruptedException {
        String scramble = "F2RFR2F'L'F'R'D'LF2U'R2D";//Algorithm.randomScramble(13, 15);
        System.out.println(scramble);
        FwmThreadsExecutor quickestSolveThreads = new FwmThreadsExecutor(new Cube2x2(scramble), 6);
        System.out.println(quickestSolveThreads.fewestMoves());
    }
}