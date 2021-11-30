import cubes.Algorithm;
import cubes.Cube2x2;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Timeout;
import solving2x2.FwmExecutor;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FwmTest {

    private Cube2x2 cube;

    @BeforeEach
    void initCube(){
        cube = new Cube2x2();
    }

    @SneakyThrows
    @RepeatedTest(10)
    @Timeout(5)
    void fwmTest(){
        final String alg = Algorithm.randomScramble(11, 20);
        cube.move(alg);

        FwmExecutor fwm = new FwmExecutor(cube);
        String solve = fwm.fewestMoves();
        cube.move(solve);
        int solveLength = Algorithm.algLength(solve);
        assertTrue(solveLength<12);
        assertTrue(cube.isSolved());
    }
}
