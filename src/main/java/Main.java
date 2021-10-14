
import cubes.*;
import graphicalMenu.GuiMenu;
import solving.FwmThreads;

import java.util.concurrent.*;

//TODO tests
//TODO Cube2x2: abstract class, general for cubs  or only NxNxN
//TODO Fwm's: delete? counters, timers and sout's
//TODO FewestMoves: terminate after some time?
//TODO FwmThreads: nThreads
//TODO FewestMoves: solveFewestMoves and inner methods & change points for entropy or method
//TODO FewestMoves: GODS_NUMBER from 11 to 0, then start alg with previous solve minus its last move
//TODO history window, button, logs inside
public class Main {


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        final String alg = "zRUxDFz2U2R'y";
        System.out.println(alg);
        System.out.println(Algorithm.skipRotation(alg));
    }

    private static void t() throws ExecutionException, InterruptedException {
        String scramble = Algorithm.randomScramble(13, 15);
        System.out.println(scramble);
        FwmThreads quickestSolveThreads = new FwmThreads(new Cube2x2(scramble));
        quickestSolveThreads.fewestMoves();
    }

    private static void g() {
        Cube2x2 cube2x2 = new Cube2x2();
        new GuiMenu(cube2x2);
    }

}