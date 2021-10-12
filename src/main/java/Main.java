
import cubes.*;
import graphicalMenu.GraphMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

//TODO abstract class, general for cubs  or only NxNxN
//TODO loggers everywhere, tests everywhere, change graphics
public class Main {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        t();
    }

    private static void q() {
        Cube2x2 cube2x2 = new Cube2x2();
        QuickestSolve solve = new QuickestSolve(cube2x2);
        solve.findQuickestSolve();
    }

    private static void g(){
        Cube2x2 cube2x2 = new Cube2x2();
        new GraphMenu(cube2x2);
    }
    
    private static void t() throws InterruptedException, ExecutionException {
        final String[] ALL_POSSIBLE_MOVES = {"U", "U2", "U'", "R", "R2", "R'", "F", "F2", "F'"};
        String scramble = Algorithm.randomScramble(12,15);
        System.out.println(scramble);

        ExecutorService executorService = Executors.newFixedThreadPool(9);
        Map<String , Future<String>> resultList = new HashMap<>();

        for (String s : ALL_POSSIBLE_MOVES){
            Cube2x2 cube = new Cube2x2();
            cube.moveCube(scramble);
            cube.moveCube(s);
            System.out.println(s);
            Future future = executorService.submit((Callable) () -> new QuickestSolve(cube).findQuickestSolve());
            resultList.put(s, future);
        }

        executorService.awaitTermination(10, TimeUnit.SECONDS);

        for (String s : resultList.keySet()){
            Future f = resultList.get(s);
            if (f.get()!=null){
                System.out.println(s+f.get());
            }
        }
    }
}