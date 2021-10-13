
import cubes.*;
import graphicalMenu.GraphMenu;

import java.util.*;
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

    //TODO nThread?
    private static void t() throws InterruptedException, ExecutionException {
        final String[] ALL_POSSIBLE_MOVES = {"U", "U2", "U'", "R", "R2", "R'", "F", "F2", "F'"};
        String scramble = Algorithm.randomScramble(12,15);
        System.out.println(scramble);

        ExecutorService executorService = Executors.newFixedThreadPool(4);
        Map<String , Future<String>> resultList = new HashMap<>();
        Set<String> solves = new HashSet<>();

        for (String s : ALL_POSSIBLE_MOVES){
            Cube2x2 cube = new Cube2x2();
            cube.moveCube(scramble);
            cube.moveCube(s);
            Future future = executorService.submit((Callable) () -> new QuickestSolve(cube).findQuickestSolve());
            resultList.put(s, future);
        }

        executorService.awaitTermination(10, TimeUnit.SECONDS);

        for (String s : resultList.keySet()){
            Future f = resultList.get(s);
            if (f.get()!=null){
                solves.add(Algorithm.optimizeAlg(s+f.get()));
            }
        }

        for (String slv:solves) {
            System.out.println("Solve "+ Algorithm.algLength(slv) + " " + slv);
        }
        Optional optional = solves.stream().min((s1,s2) -> Integer.compare(Algorithm.algLength(s1), Algorithm.algLength(s2)));
        System.out.println(optional.get());
    }
}