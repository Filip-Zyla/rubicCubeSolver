package cubes;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

//TODO nThread?
//TODO initialMove part of QuickestSolve?
//TODO atomicInt change in threads
public class QuickestSolveThreads {

    private final String[] ALL_POSSIBLE_MOVES = {"U", "U2", "U'", "R", "R2", "R'", "F", "F2", "F'"};

    private Cube2x2 cube;
    private ExecutorService executorService;
    private int nThreads = 4;
    private AtomicInteger godsNumber = new AtomicInteger(11);
    private Map<String, Future<String>> resultList;
    private Set<String> solves;

    public QuickestSolveThreads(Cube2x2 cube2x2) {
        this.cube = cube2x2;
        executorService = Executors.newFixedThreadPool(nThreads);
        resultList = new HashMap<>();
        solves = new HashSet<>();
    }

    public QuickestSolveThreads(String scramble) {
        this.cube = new Cube2x2(scramble);
        executorService = Executors.newFixedThreadPool(nThreads);
        resultList = new HashMap<>();
        solves = new HashSet<>();
    }

    public void findBestSolutions() throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();

        for (String s : ALL_POSSIBLE_MOVES) {
            Cube2x2 cubeTemp = new Cube2x2(cube);
            cubeTemp.moveCube(s);
            Future future = executorService.submit((Callable) () -> new QuickestSolve(cubeTemp).findQuickestSolve());
            resultList.put(s, future);
        }

        for (String s : resultList.keySet()){
            Future f = resultList.get(s);
            if (!f.get().equals("Error")){
                solves.add(Algorithm.optimizeAlg(s+f.get()));
            }
        }

        System.out.println("==========ENDING==========");
        for (String slv : solves) {
            System.out.println("Solve " + Algorithm.algLength(slv) + " " + slv);
        }

        Optional optional = solves.stream().min(Comparator.comparingInt(Algorithm::algLength));
        if (optional.isPresent()) {
            System.out.println("Best solution: " + optional.get());
        }
        else {
            System.out.println("No solution");
        }

        long end = System.currentTimeMillis();
        long time = (end-start)/1000;
        System.out.println("Time is " + time);
    }
}
