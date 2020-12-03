package cubes;

import lombok.*;
import moveInterfaces.moveOneWallInterfaceTwoCube;
import moveInterfaces.rotateInterface;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

@Data
public class Cube_2x2 implements moveOneWallInterfaceTwoCube, rotateInterface {
    private final int NUMBER_OF_WALLS = 6;
    /**
     * colors
     */
    private final int DEGREE_OF_CUBE = 2;
    private final int HEIGHT = DEGREE_OF_CUBE * 3;
    private final int WIDTH = DEGREE_OF_CUBE * 4;
    private int[][] cube;

    // alg scrambles
    private final String SEXY_MOVE_ON_RIGHT = "U'R'UR";
    private final String SEXY_MOVE_ON_LEFT = "ULU'L'";

    /**
     * colors id's:
     * white=0 yellow=5
     * red=1 orange=4
     * blue=2 green=3
     * BLACK=9
     * <p>
     * orientation: laying cross:
     * 99  44  99  99
     * 33  00  22  55
     * 99  11  99  99
     * nulls as 9 in code
     */

    public Cube_2x2() {
        cube = new int[HEIGHT][WIDTH];
        paintCube();
    }

    private void paintCube() {
        paint(HEIGHT, WIDTH, DEGREE_OF_CUBE, cube);
    }

    private static void paint(int height, int width, int degree_of_cube, int[][] cube) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if ((i < degree_of_cube || i >= degree_of_cube * 2) && (j < degree_of_cube || j >= degree_of_cube * 2)) {
                    cube[i][j] = 9;
                } else if (i < degree_of_cube) {
                    cube[i][j] = 4;
                } else if (i >= degree_of_cube * 2) {
                    cube[i][j] = 1;
                } else {
                    if (j < degree_of_cube) {
                        cube[i][j] = 3;
                    } else if (j < degree_of_cube * 2) {
                        cube[i][j] = 0;
                    } else if (j < degree_of_cube * 3) {
                        cube[i][j] = 2;
                    } else {
                        cube[i][j] = 5;
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return getString(HEIGHT, WIDTH, cube);
    }

    private static String getString(int height, int width, int[][] cube) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                stringBuilder.append(cube[i][j]);
                stringBuilder.append(" ");
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

    /**
     * Moving methods
     */
    @Override
    public void move_U(int rotate) {
        if (rotate < 0)
            rotate = rotate + 4;

        int temp;
        for (int i = 0; i < rotate; i++) {
            temp = cube[2][2];
            cube[2][2] = cube[3][2];
            cube[3][2] = cube[3][3];
            cube[3][3] = cube[2][3];
            cube[2][3] = temp;

            temp = cube[2][1];
            cube[2][1] = cube[4][2];
            cube[4][2] = cube[3][4];
            cube[3][4] = cube[1][3];
            cube[1][3] = temp;

            temp = cube[1][2];
            cube[1][2] = cube[3][1];
            cube[3][1] = cube[4][3];
            cube[4][3] = cube[2][4];
            cube[2][4] = temp;

        }
    }

    @Override
    public void move_D(int rotate) {
        if (rotate < 0)
            rotate = rotate + 4;

        int temp;
        for (int i = 0; i < rotate; i++) {
            temp = cube[2][6];
            cube[2][6] = cube[3][6];
            cube[3][6] = cube[3][7];
            cube[3][7] = cube[2][7];
            cube[2][7] = temp;

            temp = cube[2][5];
            cube[2][5] = cube[5][3];
            cube[5][3] = cube[3][0];
            cube[3][0] = cube[0][2];
            cube[0][2] = temp;

            temp = cube[0][3];
            cube[0][3] = cube[3][5];
            cube[3][5] = cube[5][2];
            cube[5][2] = cube[2][0];
            cube[2][0] = temp;
        }
    }

    @Override
    public void move_R(int rotate) {
        if (rotate < 0)
            rotate = rotate + 4;

        int temp;
        for (int i = 0; i < rotate; i++) {
            temp = cube[3][4];
            cube[3][4] = cube[3][5];
            cube[3][5] = cube[2][5];
            cube[2][5] = cube[2][4];
            cube[2][4] = temp;

            temp = cube[4][3];
            cube[4][3] = cube[3][6];
            cube[3][6] = cube[0][3];
            cube[0][3] = cube[2][3];
            cube[2][3] = temp;

            temp = cube[3][3];
            cube[3][3] = cube[5][3];
            cube[5][3] = cube[2][6];
            cube[2][6] = cube[1][3];
            cube[1][3] = temp;


        }
    }

    @Override
    public void move_L(int rotate) {
        if (rotate < 0)
            rotate = rotate + 4;

        int temp;
        for (int i = 0; i < rotate; i++) {
            temp = cube[2][1];
            cube[2][1] = cube[2][0];
            cube[2][0] = cube[3][0];
            cube[3][0] = cube[3][1];
            cube[3][1] = temp;

            temp = cube[1][2];
            cube[1][2] = cube[2][7];
            cube[2][7] = cube[5][2];
            cube[5][2] = cube[3][2];
            cube[3][2] = temp;

            temp = cube[2][2];
            cube[2][2] = cube[0][2];
            cube[0][2] = cube[3][7];
            cube[3][7] = cube[4][2];
            cube[4][2] = temp;


        }
    }

    @Override
    public void move_F(int rotate) {
        if (rotate < 0)
            rotate = rotate + 4;

        int temp;
        for (int i = 0; i < rotate; i++) {
            temp = cube[4][2];
            cube[4][2] = cube[5][2];
            cube[5][2] = cube[5][3];
            cube[5][3] = cube[4][3];
            cube[4][3] = temp;

            temp = cube[3][1];
            cube[3][1] = cube[3][7];
            cube[3][7] = cube[3][5];
            cube[3][5] = cube[3][3];
            cube[3][3] = temp;

            temp = cube[3][2];
            cube[3][2] = cube[3][0];
            cube[3][0] = cube[3][6];
            cube[3][6] = cube[3][4];
            cube[3][4] = temp;


        }
    }

    @Override
    public void move_B(int rotate) {
        if (rotate > 0)
            rotate = rotate + 4;

        int temp;
        for (int i = 0; i > rotate; i--) {
            temp = cube[1][3];
            cube[1][3] = cube[0][3];
            cube[0][3] = cube[0][2];
            cube[0][2] = cube[1][2];
            cube[1][2] = temp;

            temp = cube[2][4];
            cube[2][4] = cube[2][6];
            cube[2][6] = cube[2][0];
            cube[2][0] = cube[2][2];
            cube[2][2] = temp;

            temp = cube[2][3];
            cube[2][3] = cube[2][5];
            cube[2][5] = cube[2][7];
            cube[2][7] = cube[2][1];
            cube[2][1] = temp;


        }
    }

    @Override
    public void rotate_x(int rotate) {
        this.move_R(rotate);
        this.move_L(-rotate);
    }

    @Override
    public void rotate_y(int rotate) {
        this.move_U(rotate);
        this.move_D(-rotate);
    }

    @Override
    public void rotate_z(int rotate) {
        this.move_F(rotate);
        this.move_B(-rotate);
    }


    public void moveCube(String alg) {
        if (!checkIsAlgProper(alg)) {
            // explicitly fot 2x2x2
            System.out.println("Not proper alg");
            return;
        }

        char[] array = alg.toCharArray();
        int index = 0;
        while (index < array.length) {
            if (index + 1 < array.length && Character.isDigit(array[index + 1])) {
                moveOneWall(array[index], array[index + 1]);
                index += 2;
            } else { // index is letter
                if (index + 1 < array.length && array[index + 1] == 39) {
                    // counter clockwise
                    moveOneWall(array[index], -1);
                    index += 2;
                } else { // clockwise
                    moveOneWall(array[index], 1);
                    index += 1;
                }
            }
        }
    }

    private boolean checkIsAlgProper(String alg) {
        if (!alg.equals(alg.toUpperCase())) {
            System.err.println("Alg with lower case");
            return false;
        }
        String poll = "UDRLFB2'";
        char[] array = alg.toCharArray();
        int i = 0;
        while (i < array.length) {
            if (poll.indexOf(array[i]) == -1) {
                System.err.println("Char " + array[i] + " at index " + i + " cannot be in alg");
                return false;
            } else if (Character.isDigit(array[i])) {
                System.err.println("Char " + array[i] + " at index " + i + " is repeated int");
                return false;
            } else if (array[i] == 39) {
                System.err.println("Char " + array[i] + " at index " + i + " is repeated coma");
                return false;
            }
            if (i+1<array.length && (array[i+1]==39 || Character.isDigit(array[i+1])))
                i=i+2;
            else i++;
        }

        return true;
    }

    public static String randomScramble() {
        String poll = "UDRLFB";
        Random r = new Random();
        StringBuilder builder = new StringBuilder();
        int ran = (int) (Math.random() * (25 - 15 + 1) + 15); // from 15 to 25
        for (int i = 0; i < ran; i++) {
            builder.append(poll.charAt(r.nextInt(poll.length())));
            switch (r.nextInt(poll.length()) % 3) {
                case 0 -> builder.append("2");
                case 1 -> builder.append("'");
            }
        }
        return builder.toString();
    }

    private void moveOneWall(char wall, int rotate) {
        switch (wall) {
            case 'R' -> this.move_R(rotate);
            case 'L' -> this.move_L(rotate);
            case 'U' -> this.move_U(rotate);
            case 'D' -> this.move_D(rotate);
            case 'F' -> this.move_F(rotate);
            case 'B' -> this.move_B(rotate);
            case 'x' -> this.rotate_x(rotate);
            case 'y' -> this.rotate_y(rotate);
            case 'z' -> this.rotate_z(rotate);
        }
    }

    /**
     * Solving cube
     */
    public void solve() {
        orientLeftWall();
        this.rotate_y(2);
        orientLeftWall();
        // resolve two last not orient stickers
        // orientation of layers
        // permutation of layers  one or two algs
    }

    private void orientLeftWall() {
        // on left and right yellow/white stickers
        int sexyCounter = 0;
        for (int i = 4; i > 0; i--) {
            while (cube[3][1] != 0 || cube[3][1] != 5) {
                this.moveCube(SEXY_MOVE_ON_LEFT);
                this.moveCube(SEXY_MOVE_ON_LEFT);
                sexyCounter = sexyCounter + 2;
            }
            this.move_L(1);
        }
        while (sexyCounter % 6 != 0) {
            this.moveCube(SEXY_MOVE_ON_LEFT);
            this.moveCube(SEXY_MOVE_ON_LEFT);
            sexyCounter = sexyCounter + 2;
        }
    }

    public void showCube(){
        return;
    }
}

