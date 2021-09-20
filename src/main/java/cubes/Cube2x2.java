package cubes;

import lombok.*;
import moveInterfaces.moveOneWallInterfaceTwoCube;
import moveInterfaces.rotateInterface;
import org.javatuples.Pair;

import java.util.*;

@Getter
@Setter
public class Cube2x2 implements moveOneWallInterfaceTwoCube, rotateInterface {

    private final int NUMBER_OF_WALLS = 6;
    private final int DEGREE_OF_CUBE = 2;
    private final int HEIGHT = DEGREE_OF_CUBE * 3;
    private final int WIDTH = DEGREE_OF_CUBE * 4;

    private int[][] array;

    /**
     * white=0 yellow=5
     * red=1 orange=4
     * blue=2 green=3
     * 4 4
     * 4 4
     * 3 3  0 0  2 2  5 5
     * 3 3  0 0  2 2  5 5
     * 1 1
     * 1 1
     */

    //TODO abstract class, general for cubs  or olny NxNxN
    public Cube2x2() {
        array = new int[HEIGHT][WIDTH];
        paintCube();
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

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                stringBuilder.append(array[i][j]);
                stringBuilder.append(" ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Cube2x2 cube = (Cube2x2) o;
        return Arrays.equals(this.array, cube.array);
    }

    //TODO find faster method
    @Override
    public void moveU(int rotate) {
        int temp;
        for (int i = 0; i < rotate; i++) {
            temp = array[2][2];
            array[2][2] = array[3][2];
            array[3][2] = array[3][3];
            array[3][3] = array[2][3];
            array[2][3] = temp;

            temp = array[2][1];
            array[2][1] = array[4][2];
            array[4][2] = array[3][4];
            array[3][4] = array[1][3];
            array[1][3] = temp;

            temp = array[1][2];
            array[1][2] = array[3][1];
            array[3][1] = array[4][3];
            array[4][3] = array[2][4];
            array[2][4] = temp;
        }
    }

    @Override
    public void moveD(int rotate) {
        int temp;
        for (int i = 0; i < rotate; i++) {
            temp = array[2][6];
            array[2][6] = array[3][6];
            array[3][6] = array[3][7];
            array[3][7] = array[2][7];
            array[2][7] = temp;

            temp = array[2][5];
            array[2][5] = array[5][3];
            array[5][3] = array[3][0];
            array[3][0] = array[0][2];
            array[0][2] = temp;

            temp = array[0][3];
            array[0][3] = array[3][5];
            array[3][5] = array[5][2];
            array[5][2] = array[2][0];
            array[2][0] = temp;
        }
    }

    @Override
    public void moveR(int rotate) {
        int temp;
        for (int i = 0; i < rotate; i++) {
            temp = array[3][4];
            array[3][4] = array[3][5];
            array[3][5] = array[2][5];
            array[2][5] = array[2][4];
            array[2][4] = temp;

            temp = array[4][3];
            array[4][3] = array[3][6];
            array[3][6] = array[0][3];
            array[0][3] = array[2][3];
            array[2][3] = temp;

            temp = array[3][3];
            array[3][3] = array[5][3];
            array[5][3] = array[2][6];
            array[2][6] = array[1][3];
            array[1][3] = temp;
        }
    }

    @Override
    public void moveL(int rotate) {
        int temp;
        for (int i = 0; i < rotate; i++) {
            temp = array[2][1];
            array[2][1] = array[2][0];
            array[2][0] = array[3][0];
            array[3][0] = array[3][1];
            array[3][1] = temp;

            temp = array[1][2];
            array[1][2] = array[2][7];
            array[2][7] = array[5][2];
            array[5][2] = array[3][2];
            array[3][2] = temp;

            temp = array[2][2];
            array[2][2] = array[0][2];
            array[0][2] = array[3][7];
            array[3][7] = array[4][2];
            array[4][2] = temp;
        }
    }

    @Override
    public void moveF(int rotate) {
        int temp;
        for (int i = 0; i < rotate; i++) {
            temp = array[4][2];
            array[4][2] = array[5][2];
            array[5][2] = array[5][3];
            array[5][3] = array[4][3];
            array[4][3] = temp;

            temp = array[3][1];
            array[3][1] = array[3][7];
            array[3][7] = array[3][5];
            array[3][5] = array[3][3];
            array[3][3] = temp;

            temp = array[3][2];
            array[3][2] = array[3][0];
            array[3][0] = array[3][6];
            array[3][6] = array[3][4];
            array[3][4] = temp;
        }
    }

    @Override
    public void moveB(int rotate) {
        int temp;
        for (int i = 0; i < rotate; i++) {
            temp = array[1][3];
            array[1][3] = array[0][3];
            array[0][3] = array[0][2];
            array[0][2] = array[1][2];
            array[1][2] = temp;

            temp = array[2][4];
            array[2][4] = array[2][6];
            array[2][6] = array[2][0];
            array[2][0] = array[2][2];
            array[2][2] = temp;

            temp = array[2][3];
            array[2][3] = array[2][5];
            array[2][5] = array[2][7];
            array[2][7] = array[2][1];
            array[2][1] = temp;
        }
    }

    @Override
    public void rotateX(int rotate) {
        this.moveR(rotate);
        this.moveL(-rotate);
    }

    @Override
    public void rotateY(int rotate) {
        this.moveU(rotate);
        this.moveD(-rotate);
    }

    @Override
    public void rotateZ(int rotate) {
        this.moveF(rotate);
        this.moveB(-rotate);
    }

    private void moveOneWall(char wall, int rotate) {
        rotate = rotateCheck(rotate);
        if (wall == 'R') {
            this.moveR(rotate);
        }
        else if (wall == 'L') {
            this.moveL(rotate);
        }
        else if (wall == 'U') {
            this.moveU(rotate);
        }
        else if (wall == 'D') {
            this.moveD(rotate);
        }
        else if (wall == 'F') {
            this.moveF(rotate);
        }
        else if (wall == 'B') {
            this.moveB(rotate);
        }
        else if (wall == 'x') {
            this.rotateX(rotate);
        }
        else if (wall == 'y') {
            this.rotateY(rotate);
        }
        else if (wall == 'z') {
            this.rotateZ(rotate);
        }
    }

    //TODO avoid avoiding negatives rotations
    private int rotateCheck(int rotate) {
        if (rotate < 0)
            rotate = rotate + 4;
        if (rotate > 3)
            rotate = rotate - 4;
        return rotate;
    }

    public boolean moveCube(String alg) {
        if (!Algorithm.checkIsAlgProper(alg)) {
            return false;
        }

        char[] array = alg.toCharArray();
        int index = 0;
        while (index < array.length) {
            if (index + 1 < array.length && Character.isDigit(array[index + 1])) {
                // double move
                moveOneWall(array[index], Character.getNumericValue(array[index + 1]));
                index += 2;
            }
            else {
                if (index + 1 < array.length && array[index + 1] == 39) {
                    // counter clockwise
                    moveOneWall(array[index], -1);
                    index += 2;
                }
                else {
                    // clockwise
                    moveOneWall(array[index], 1);
                    index += 1;
                }
            }
        }
        return true;
    }

    boolean isSolved() {
        if (this.array[2][2] != this.array[2][3] || this.array[3][2] != this.array[3][3] || this.array[2][2] != this.array[3][3])
            return false;
        if (this.array[2][4] != this.array[2][5] || this.array[3][4] != this.array[3][5] || this.array[2][4] != this.array[3][5])
            return false;
        if (this.array[4][2] != this.array[4][3] || this.array[5][2] != this.array[5][3] || this.array[4][2] != this.array[5][3])
            return false;
        return true;
    }
}