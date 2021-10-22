package cubes;

import lombok.Getter;
import lombok.Setter;
import cubeInterfaces.MoveOneWallInterface;
import cubeInterfaces.RotateInterface;
import java.util.Arrays;

@Setter
@Getter
abstract class Cube extends Puzzle implements MoveOneWallInterface, RotateInterface {
    /**
     * white=0 yellow=5
     * red=1 orange=4
     * blue=2 green=3
     * black = 9/-
     * - -  4 4  - -  - -
     * - -  4 4  - -  - -
     * 3 3  0 0  2 2  5 5
     * 3 3  0 0  2 2  5 5
     * - -  1 1  - -  - -
     * - -  1 1  - -  - -
     */

    private final int DEGREE_OF_CUBE;
    final int HEIGHT;
    final int WIDTH;

    int[][] array;

    Cube(int degree) {
        this.DEGREE_OF_CUBE=degree;
        HEIGHT = DEGREE_OF_CUBE * 3;
        WIDTH = DEGREE_OF_CUBE * 4;
        array = new int[HEIGHT][WIDTH];
        paintCube();
    }
    Cube(int degree, String scramble) {
        this(degree);
        move(scramble);
    }
    Cube(int degree, Cube2x2 cube) {
        this(degree);
        for (int i = 0; i < cube.array.length; i++) {
            this.array[i] = Arrays.copyOf(cube.array[i], cube.array[i].length);
        }
    }

    private void paintCube() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if ((i < DEGREE_OF_CUBE || i >= DEGREE_OF_CUBE * 2) && (j < DEGREE_OF_CUBE || j >= DEGREE_OF_CUBE * 2)) {
                    array[i][j] = 9;
                }
                else if (i < DEGREE_OF_CUBE) {
                    array[i][j] = 4;
                }
                else if (i >= DEGREE_OF_CUBE * 2) {
                    array[i][j] = 1;
                }
                else if (j < DEGREE_OF_CUBE) {
                    array[i][j] = 3;
                }
                else if (j < DEGREE_OF_CUBE * 2) {
                    array[i][j] = 0;
                }
                else if (j < DEGREE_OF_CUBE * 3) {
                    array[i][j] = 2;
                }
                else {
                    array[i][j] = 5;
                }
            }
        }
    }
}