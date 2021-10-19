package cubes;

import lombok.Getter;
import lombok.Setter;
import moveInterfaces.MoveOneWallInterface;
import moveInterfaces.RotateInterface;

import java.util.Arrays;

@Setter
@Getter
abstract class Cube extends Puzzle implements MoveOneWallInterface, RotateInterface {

    private final int DEGREE_OF_CUBE = 2;
    final int HEIGHT = DEGREE_OF_CUBE * 3;
    final int WIDTH = DEGREE_OF_CUBE * 4;

    int[][] array;

    Cube() {
        array = new int[HEIGHT][WIDTH];
        paintCube();
    }

    Cube(String scramble) {
        array = new int[HEIGHT][WIDTH];
        paintCube();
        move(scramble);
    }

    Cube(Cube2x2 cube) {
        this.array = new int[HEIGHT][WIDTH];
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
