import cubes.Algorithm;
import cubes.Cube2x2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AlgorithmTest {

    @ParameterizedTest
    @ValueSource(strings = { "RU", "R'U2", "RRRRRR", "R'DD2U'FBBULRB2"})
    void checkIfProperTest(String alg) {
        assertTrue(Algorithm.checkIfProper(alg));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { "R''U", "R'U2u", "LRt", "DD2UD'22D2U"})
    void checkIfNotProperTest(String alg) {
        assertFalse(Algorithm.checkIfProper(alg));
    }

    @RepeatedTest(10)
    void checkIfProperWithRandomScrambleTest() {
        String alg = Algorithm.randomScramble(5,20);
        assertTrue(Algorithm.checkIfProper(alg));
    }

    @Test
    void toListTest(){
        String alg = "R'UDB2L'";
        List<String> list = Arrays.asList("R'", "U", "D", "B2", "L'");
        assertEquals(Algorithm.toList(alg), list);
    }

    @Test
    void toList2Test(){
        String alg = "R2R'RR'RR2";
        List<String> list = Arrays.asList("R2", "R'", "R", "R'", "R", "R2");
        assertEquals(Algorithm.toList(alg), list);
    }

    @Test
    void toListNullTest(){
        String alg = "R'UDB2'L'";
        assertNull(Algorithm.toList(alg));
    }

    @RepeatedTest(10)
    void algLengthTest(){
        String alg = Algorithm.randomScramble(5,20);
        int l1 = Algorithm.algLength(alg);
        int l2 = Algorithm.toList(alg).size();
        assertEquals(l1, l2);
    }

    @Test
    void optimizeAlgShortingTest(){
        String alg = Algorithm.optimizeAlg("RRRU2U'");
        assertEquals(alg, "R'U");
    }

    @Test
    void optimizeAlgRotationTest(){
        String alg = Algorithm.optimizeAlg("R2L'BF'");
        assertEquals(alg, "xRz'");
    }

    @Test
    void optimizeAlgTest(){
        String alg = Algorithm.optimizeAlg("R'L2LUDU");
        assertEquals(alg, "x'L2y'U'");
    }

    @Test
    void skipRotationTest(){
        String alg = Algorithm.skipRotation("xRUFzL");
        assertEquals(alg, "RFDB");
    }
}
