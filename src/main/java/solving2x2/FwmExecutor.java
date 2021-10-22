package solving2x2;

import cubes.Algorithm;
import cubes.Cube2x2;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class FwmExecutor {

    private final String[] ALL_POSSIBLE_MOVES = {"U", "U2", "U'", "R", "R2", "R'", "F", "F2", "F'"};

    private Cube2x2 cube;
    private ExecutorService executorService;
    private final int nThreads;
    private AtomicInteger currentGodsNumber = new AtomicInteger(11);
    private Map<String, Future<String>> resultList;
    private Set<String> solves;

    public FwmExecutor(Cube2x2 cube2x2, int threads) {
        this.cube = cube2x2;
        this.nThreads = threads;
        executorService = Executors.newFixedThreadPool(nThreads);
        resultList = new HashMap<>();
        solves = new HashSet<>();
    }

    public String fewestMoves() throws ExecutionException, InterruptedException {
        return findBestSolutions();
    }

    private String findBestSolutions() throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();

        for (String s : ALL_POSSIBLE_MOVES) {
            Cube2x2 cubeAsc = new Cube2x2(cube);
            cubeAsc.move(s);
            Callable callAsc = new FwmAsc(cubeAsc, currentGodsNumber);
            Future futureAsc = executorService.submit(callAsc);
            resultList.put(s, futureAsc);

            Cube2x2 cubeTempDesc = new Cube2x2(cube);
            cubeTempDesc.move(s);
            Callable callDesc = new FwmDesc(cubeTempDesc, currentGodsNumber);
            Future futureDesc = executorService.submit(callDesc);
            resultList.put(s, futureDesc);
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

        long time = (System.currentTimeMillis()-start)/1000;
        System.out.println("Time is " + time);
        executorService.shutdown();
        return solves.stream().min(Comparator.comparingInt(Algorithm::algLength)).get();
    }
}