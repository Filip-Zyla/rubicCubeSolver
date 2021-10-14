
import cubes.*;
import graphicalMenu.GuiMenu;
import solving.FwmThreads;

import java.util.concurrent.*;

//TODO skipRotations: from start, recursion, "xURyF2"
//TODO Cube2x2: abstract class, general for cubs  or only NxNxN
//TODO loggers, tests
//TODO history window, button, logs inside
//TODO both quickest
//TODO FwmThreads: nThreads
//TODO Fwm's: delete? counters, timers and sout's
//TODO FewestMoves: impl callable?
//TODO FewestMoves: change points for entropy or method
//TODO FewestMoves: GODS_NUMBER from 11 to 0, then start alg with previous solve minus its last move
//TODO FwmThreads: terminate after some time
//TODO FewestMoves: solveFewestMoves and inner methods
public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        t();
    }

    private static void t() throws ExecutionException, InterruptedException {
        String scramble = Algorithm.randomScramble(13, 15);
        System.out.println(scramble);
        FwmThreads quickestSolveThreads = new FwmThreads(new Cube2x2(scramble));
        quickestSolveThreads.fewestMoves();
    }

    private static void g(){
        Cube2x2 cube2x2 = new Cube2x2();
        new GuiMenu(cube2x2);
    }

}