import cubes.Cube2x2;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

public class Cube2x2Tests {

    private Cube2x2 cube;

    @BeforeEach
    void resetCube(){
        cube = new Cube2x2();
    }

    @Test
    void paintCubeTest(){
        int[][] cubeArray = {
                {9,9,4,4,9,9,9,9},
                {9,9,4,4,9,9,9,9},
                {3,3,0,0,2,2,5,5},
                {3,3,0,0,2,2,5,5},
                {9,9,1,1,9,9,9,9},
                {9,9,1,1,9,9,9,9}
        };
        assertArrayEquals(cube.getArray(), cubeArray);
    }

    @Test
    void equalsTest1(){
        Cube2x2 cube1 = new Cube2x2("RU");
        Cube2x2 cube2 = new Cube2x2();
        cube2.move("RU");
        assertEquals(cube1, cube2);
    }

    @Test
    void equalsTest2(){
        Cube2x2 cube1 = new Cube2x2("RU");
        Cube2x2 cube2 = new Cube2x2(cube1);
        assertEquals(cube1, cube2);
    }

    @Test
    void isNotSolved(){
        int[][] cubeArray = {
                {9,9,4,4,9,9,9,9},
                {9,9,4,4,9,9,9,9},
                {3,3,0,0,2,2,5,5},
                {3,3,0,0,2,2,5,5},
                {9,9,1,1,9,9,9,9},
                {9,9,1,1,9,9,9,9}
        };
        cube.move("R");
        assertFalse(cube.isSolved());
    }

    @Test
    void isSolved(){
        int[][] cubeArray = {
                {9,9,4,4,9,9,9,9},
                {9,9,4,4,9,9,9,9},
                {3,3,0,0,2,2,5,5},
                {3,3,0,0,2,2,5,5},
                {9,9,1,1,9,9,9,9},
                {9,9,1,1,9,9,9,9}
        };
        assertTrue(cube.isSolved());
        assertArrayEquals(cube.getArray(), cubeArray);
    }

    @Test
    void moveTest(){
        cube.move("RUL'FDR'");
        int[][] cubeArray = {
                {9,9,2,2,9,9,9,9},
                {9,9,1,0,9,9,9,9},
                {0,5,2,1,2,5,4,4},
                {0,4,3,3,4,1,5,3},
                {9,9,5,0,9,9,9,9},
                {9,9,1,3,9,9,9,9}
        };
        assertArrayEquals(cube.getArray(), cubeArray);
    }

    @SneakyThrows
    @Test
    void moveOneWallTest_1(){
        Method method = cube.getClass().getDeclaredMethod("moveOneWall",
                new Class[]{char.class, int.class});
        method.setAccessible(true);
        method.invoke(cube, new Object[]{'R', 1});
        int[][] cubeArray = {
                {9,9,4,0,9,9,9,9},
                {9,9,4,0,9,9,9,9},
                {3,3,0,1,2,2,4,5},
                {3,3,0,1,2,2,4,5},
                {9,9,1,5,9,9,9,9},
                {9,9,1,5,9,9,9,9}
        };
        assertArrayEquals(cube.getArray(), cubeArray);
    }

    @SneakyThrows
    @Test
    void moveOneWallTest_2(){
        Method method = cube.getClass().getDeclaredMethod("moveOneWall",
                new Class[]{char.class, int.class});
        method.setAccessible(true);
        method.invoke(cube, new Object[]{'D', -1});
        int[][] cubeArray = {
                {9,9,3,3,9,9,9,9},
                {9,9,4,4,9,9,9,9},
                {1,3,0,0,2,4,5,5},
                {1,3,0,0,2,4,5,5},
                {9,9,1,1,9,9,9,9},
                {9,9,2,2,9,9,9,9}
        };
        assertArrayEquals(cube.getArray(), cubeArray);
    }

    @SneakyThrows
    @Test
    void moveOneWallTest_3(){
        Method method = cube.getClass().getDeclaredMethod("moveOneWall",
                new Class[]{char.class, int.class});
        method.setAccessible(true);
        method.invoke(cube, new Object[]{'z', 2});
        int[][] cubeArray = {
                {9,9,4,4,9,9,9,9},
                {9,9,4,4,9,9,9,9},
                {2,2,5,5,3,3,0,0},
                {2,2,5,5,3,3,0,0},
                {9,9,1,1,9,9,9,9},
                {9,9,1,1,9,9,9,9}
        };
        assertArrayEquals(cube.getArray(), cubeArray);
    }

    @Test
    void moveNormalU_Test(){
        cube.moveNormalU();
        int[][] cubeArray = {
                {9,9,4,4,9,9,9,9},
                {9,9,3,3,9,9,9,9},
                {3,1,0,0,4,2,5,5},
                {3,1,0,0,4,2,5,5},
                {9,9,2,2,9,9,9,9},
                {9,9,1,1,9,9,9,9}
        };
        assertArrayEquals(cube.getArray(), cubeArray);
    }

    @Test
    void moveCounterU_Test(){
        cube.moveCounterU();
        int[][] cubeArray = {
                {9,9,4,4,9,9,9,9},
                {9,9,2,2,9,9,9,9},
                {3,4,0,0,1,2,5,5},
                {3,4,0,0,1,2,5,5},
                {9,9,3,3,9,9,9,9},
                {9,9,1,1,9,9,9,9}
        };
        assertArrayEquals(cube.getArray(), cubeArray);
    }

    @Test
    void moveDoubleU_Test(){
        cube.moveDoubleU();
        int[][] cubeArray = {
                {9,9,4,4,9,9,9,9},
                {9,9,1,1,9,9,9,9},
                {3,2,0,0,3,2,5,5},
                {3,2,0,0,3,2,5,5},
                {9,9,4,4,9,9,9,9},
                {9,9,1,1,9,9,9,9}
        };
        assertArrayEquals(cube.getArray(), cubeArray);
    }

    @Test
    void moveNormalD_Test(){
        cube.moveNormalD();
        int[][] cubeArray = {
                {9,9,2,2,9,9,9,9},
                {9,9,4,4,9,9,9,9},
                {4,3,0,0,2,1,5,5},
                {4,3,0,0,2,1,5,5},
                {9,9,1,1,9,9,9,9},
                {9,9,3,3,9,9,9,9}
        };
        assertArrayEquals(cube.getArray(), cubeArray);
    }

    @Test
    void moveCounterD_Test(){
        cube.moveCounterD();
        int[][] cubeArray = {
                {9,9,3,3,9,9,9,9},
                {9,9,4,4,9,9,9,9},
                {1,3,0,0,2,4,5,5},
                {1,3,0,0,2,4,5,5},
                {9,9,1,1,9,9,9,9},
                {9,9,2,2,9,9,9,9}
        };
        assertArrayEquals(cube.getArray(), cubeArray);
    }

    @Test
    void moveDoubleD_Test(){
        cube.moveDoubleD();
        int[][] cubeArray = {
                {9,9,1,1,9,9,9,9},
                {9,9,4,4,9,9,9,9},
                {2,3,0,0,2,3,5,5},
                {2,3,0,0,2,3,5,5},
                {9,9,1,1,9,9,9,9},
                {9,9,4,4,9,9,9,9}
        };
        assertArrayEquals(cube.getArray(), cubeArray);
    }

    @Test
    void moveNormalR_Test(){
        cube.moveNormalR();
        int[][] cubeArray = {
                {9,9,4,0,9,9,9,9},
                {9,9,4,0,9,9,9,9},
                {3,3,0,1,2,2,4,5},
                {3,3,0,1,2,2,4,5},
                {9,9,1,5,9,9,9,9},
                {9,9,1,5,9,9,9,9}
        };
        assertArrayEquals(cube.getArray(), cubeArray);
    }

    @Test
    void moveCounterR_Test(){
        cube.moveCounterR();
        int[][] cubeArray = {
                {9,9,4,5,9,9,9,9},
                {9,9,4,5,9,9,9,9},
                {3,3,0,4,2,2,1,5},
                {3,3,0,4,2,2,1,5},
                {9,9,1,0,9,9,9,9},
                {9,9,1,0,9,9,9,9}
        };
        assertArrayEquals(cube.getArray(), cubeArray);
    }

    @Test
    void moveDoubleR_Test(){
        cube.moveDoubleR();
        int[][] cubeArray = {
                {9,9,4,1,9,9,9,9},
                {9,9,4,1,9,9,9,9},
                {3,3,0,5,2,2,0,5},
                {3,3,0,5,2,2,0,5},
                {9,9,1,4,9,9,9,9},
                {9,9,1,4,9,9,9,9}
        };
        assertArrayEquals(cube.getArray(), cubeArray);
    }

    @Test
    void moveNormalL_Test(){
        cube.moveNormalL();
        int[][] cubeArray = {
                {9,9,5,4,9,9,9,9},
                {9,9,5,4,9,9,9,9},
                {3,3,4,0,2,2,5,1},
                {3,3,4,0,2,2,5,1},
                {9,9,0,1,9,9,9,9},
                {9,9,0,1,9,9,9,9}
        };
        assertArrayEquals(cube.getArray(), cubeArray);
    }

    @Test
    void moveCounterL_Test(){
        cube.moveCounterL();
        int[][] cubeArray = {
                {9,9,0,4,9,9,9,9},
                {9,9,0,4,9,9,9,9},
                {3,3,1,0,2,2,5,4},
                {3,3,1,0,2,2,5,4},
                {9,9,5,1,9,9,9,9},
                {9,9,5,1,9,9,9,9}
        };
        assertArrayEquals(cube.getArray(), cubeArray);
    }

    @Test
    void moveDoubleL_Test(){
        cube.moveDoubleL();
        int[][] cubeArray = {
                {9,9,1,4,9,9,9,9},
                {9,9,1,4,9,9,9,9},
                {3,3,5,0,2,2,5,0},
                {3,3,5,0,2,2,5,0},
                {9,9,4,1,9,9,9,9},
                {9,9,4,1,9,9,9,9}
        };
        assertArrayEquals(cube.getArray(), cubeArray);
    }

    @Test
    void moveNormalF_Test(){
        cube.moveNormalF();
        int[][] cubeArray = {
                {9,9,4,4,9,9,9,9},
                {9,9,4,4,9,9,9,9},
                {3,3,0,0,2,2,5,5},
                {5,5,3,3,0,0,2,2},
                {9,9,1,1,9,9,9,9},
                {9,9,1,1,9,9,9,9}
        };
        assertArrayEquals(cube.getArray(), cubeArray);
    }

    @Test
    void moveCounterF_Test(){
        cube.moveCounterF();
        int[][] cubeArray = {
                {9,9,4,4,9,9,9,9},
                {9,9,4,4,9,9,9,9},
                {3,3,0,0,2,2,5,5},
                {0,0,2,2,5,5,3,3},
                {9,9,1,1,9,9,9,9},
                {9,9,1,1,9,9,9,9}
        };
        assertArrayEquals(cube.getArray(), cubeArray);
    }

    @Test
    void moveDoubleF_Test(){
        cube.moveDoubleF();
        int[][] cubeArray = {
                {9,9,4,4,9,9,9,9},
                {9,9,4,4,9,9,9,9},
                {3,3,0,0,2,2,5,5},
                {2,2,5,5,3,3,0,0},
                {9,9,1,1,9,9,9,9},
                {9,9,1,1,9,9,9,9}
        };
        assertArrayEquals(cube.getArray(), cubeArray);
    }

    @Test
    void moveNormalB_Test(){
        cube.moveNormalB();
        int[][] cubeArray = {
                {9,9,4,4,9,9,9,9},
                {9,9,4,4,9,9,9,9},
                {0,0,2,2,5,5,3,3},
                {3,3,0,0,2,2,5,5},
                {9,9,1,1,9,9,9,9},
                {9,9,1,1,9,9,9,9}
        };
        assertArrayEquals(cube.getArray(), cubeArray);
    }

    @Test
    void moveCounterB_Test(){
        cube.moveCounterB();
        int[][] cubeArray = {
                {9,9,4,4,9,9,9,9},
                {9,9,4,4,9,9,9,9},
                {5,5,3,3,0,0,2,2},
                {3,3,0,0,2,2,5,5},
                {9,9,1,1,9,9,9,9},
                {9,9,1,1,9,9,9,9}
        };
        assertArrayEquals(cube.getArray(), cubeArray);
    }

    @Test
    void moveDoubleB_Test(){
        cube.moveDoubleB();
        int[][] cubeArray = {
                {9,9,4,4,9,9,9,9},
                {9,9,4,4,9,9,9,9},
                {2,2,5,5,3,3,0,0},
                {3,3,0,0,2,2,5,5},
                {9,9,1,1,9,9,9,9},
                {9,9,1,1,9,9,9,9}
        };
        assertArrayEquals(cube.getArray(), cubeArray);
    }

    @Test
    void rotateY_Test(){
        cube.rotateY(1);
        int[][] cubeArray = {
                {9,9,3,3,9,9,9,9},
                {9,9,3,3,9,9,9,9},
                {1,1,0,0,4,4,5,5},
                {1,1,0,0,4,4,5,5},
                {9,9,2,2,9,9,9,9},
                {9,9,2,2,9,9,9,9}
        };
        assertArrayEquals(cube.getArray(), cubeArray);
    }

    @Test
    void rotateX_Test(){
        cube.rotateX(2);
        int[][] cubeArray = {
                {9,9,1,1,9,9,9,9},
                {9,9,1,1,9,9,9,9},
                {3,3,5,5,2,2,0,0},
                {3,3,5,5,2,2,0,0},
                {9,9,4,4,9,9,9,9},
                {9,9,4,4,9,9,9,9}
        };
        assertArrayEquals(cube.getArray(), cubeArray);
    }

    @Test
    void rotateZ_Test(){
        cube.rotateZ(-1);
        int[][] cubeArray = {
                {9,9,4,4,9,9,9,9},
                {9,9,4,4,9,9,9,9},
                {0,0,2,2,5,5,3,3},
                {0,0,2,2,5,5,3,3},
                {9,9,1,1,9,9,9,9},
                {9,9,1,1,9,9,9,9}
        };
        assertArrayEquals(cube.getArray(), cubeArray);
    }
}