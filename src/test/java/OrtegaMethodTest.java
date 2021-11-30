import cubes.Algorithm;
import cubes.Cube2x2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import solving2x2.OrtegaMethod;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrtegaMethodTest {

    private Cube2x2 cube;

    @BeforeEach
    void initCube(){
        cube = new Cube2x2();
    }

    @RepeatedTest(20)
    void ortegaTest(){
        cube.move(Algorithm.randomScramble(0,20));
        OrtegaMethod ortega = new OrtegaMethod(cube);
        String solve = ortega.solve();
        cube.move(solve);
        assertTrue(cube.isSolved());
    }

    @Test
    void ortegaEmptyTest(){
        cube.move("");
        OrtegaMethod ortega = new OrtegaMethod(cube);
        String solve = ortega.solve();
        cube.move(solve);
        assertTrue(cube.isSolved());
    }

    @Test
    void ortegaEmpty2Test(){
        OrtegaMethod ortega = new OrtegaMethod(cube);
        String solve = ortega.solve();
        cube.move(solve);
        assertTrue(cube.isSolved());
    }
}