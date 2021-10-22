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

        for (int i=0; i<ALL_POSSIBLE_MOVES.length; i++) {
            if (i%2==0) {
                Cube2x2 cubeAsc = new Cube2x2(cube);
                cubeAsc.move(ALL_POSSIBLE_MOVES[i]);
                Callable callAsc = new FwmAsc(cubeAsc, currentGodsNumber);
                Future futureAsc = executorService.submit(callAsc);
                resultList.put(ALL_POSSIBLE_MOVES[i], futureAsc);
            }
            else {

                Cube2x2 cubeTempDesc = new Cube2x2(cube);
                cubeTempDesc.move(ALL_POSSIBLE_MOVES[i]);
                Callable callDesc = new FwmDesc(cubeTempDesc, currentGodsNumber);
                Future futureDesc = executorService.submit(callDesc);
                resultList.put(ALL_POSSIBLE_MOVES[i], futureDesc);
            }
        }

        for (String s : resultList.keySet()){
            Future f = resultList.get(s);
            if (!f.get().equals("Error")){
                solves.add(Algorithm.optimizeAlg(s+f.get()));
            }
        }

        executorService.shutdown();
        final Optional<String> s = solves.stream().min(Comparator.comparingInt(Algorithm::algLength));
        return s.orElse("Error");
    }
}