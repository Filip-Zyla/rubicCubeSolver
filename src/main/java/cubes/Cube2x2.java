package cubes;

import lombok.*;
import moveInterfaces.moveOneWallInterfaceTwoCube;
import moveInterfaces.rotateInterface;

@Data
public class Cube2x2 implements moveOneWallInterfaceTwoCube, rotateInterface {

    private final int NUMBER_OF_WALLS = 6;
    private final int DEGREE_OF_CUBE = 2;
    private final int HEIGHT = DEGREE_OF_CUBE * 3;
    private final int WIDTH = DEGREE_OF_CUBE * 4;
    private int[][] cube;

    private final String SEXY_MOVE_ON_LEFT = "U'R'UR";
    private final String SEXY_MOVE_ON_LEFT_DOUBLE = "U'R'URU'R'UR";
    private final String T_PERM = "RU2R'U'RU2L'UR'U'L";
    private final String J_PERM = "RU'R'U'F2U'RUR'DR2";
    private final String ORIENT_X_BOTH_WALLS = "R2D2F2";
    private final String ORIENT_ONE_ON_BOTH = "R2DR2";
    private final String ORIENT_TWO_AND_X = "R2DR2DR2";
    private final String ORIENT_TWO_AND_X_REV = "R2D'R2D'R2";
    private final String ORIENT_X_AND_TWO = "R2U'R2U'R2";
    private final String ORIENT_X_AND_TWO_REV = "R2UR2UR2";

    /**
     * colors id's:
     * white=0 yellow=5
     * red=1 orange=4
     * blue=2 green=3
     *
     *      4 4
     *      4 4
     * 3 3  0 0  2 2  5 5
     * 3 3  0 0  2 2  5 5
     *      1 1
     *      1 1
     */

    public Cube2x2() {
        cube = new int[HEIGHT][WIDTH];
        paintCube();
    }

    private void paintCube() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if ((i < DEGREE_OF_CUBE || i >= DEGREE_OF_CUBE * 2) && (j < DEGREE_OF_CUBE || j >= DEGREE_OF_CUBE * 2)) {
                    cube[i][j] = 9;
                } else if (i < DEGREE_OF_CUBE) {
                    cube[i][j] = 4;
                } else if (i >= DEGREE_OF_CUBE * 2) {
                    cube[i][j] = 1;
                } else {
                    if (j < DEGREE_OF_CUBE) {
                        cube[i][j] = 3;
                    } else if (j < DEGREE_OF_CUBE * 2) {
                        cube[i][j] = 0;
                    } else if (j < DEGREE_OF_CUBE * 3) {
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
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
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
    public void moveU(int rotate) {
        if (rotate < 0)
            rotate = rotate + 4;

        int temp;
        for (int i = 0; i < rotate; i++) {
            temp       = cube[2][2];
            cube[2][2] = cube[3][2];
            cube[3][2] = cube[3][3];
            cube[3][3] = cube[2][3];
            cube[2][3] = temp;

            temp       = cube[2][1];
            cube[2][1] = cube[4][2];
            cube[4][2] = cube[3][4];
            cube[3][4] = cube[1][3];
            cube[1][3] = temp;

            temp       = cube[1][2];
            cube[1][2] = cube[3][1];
            cube[3][1] = cube[4][3];
            cube[4][3] = cube[2][4];
            cube[2][4] = temp;
        }
    }
    @Override
    public void moveD(int rotate) {
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
    public void moveR(int rotate) {
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
    public void moveL(int rotate) {
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
    public void moveF(int rotate) {
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
    public void moveB(int rotate) {
        if (rotate < 0)
            rotate = rotate + 4;

        int temp;
        for (int i = 0; i < rotate; i++) {
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
        switch (wall) {
            case 'R' -> this.moveR(rotate);
            case 'L' -> this.moveL(rotate);
            case 'U' -> this.moveU(rotate);
            case 'D' -> this.moveD(rotate);
            case 'F' -> this.moveF(rotate);
            case 'B' -> this.moveB(rotate);
            case 'x' -> this.rotateX(rotate);
            case 'y' -> this.rotateY(rotate);
            case 'z' -> this.rotateZ(rotate);
        }
    }

    public void moveCube(String alg) {
        if (!Algorithm.checkIsAlgProper(alg)) {
            System.out.println("Not proper alg");
            return;
        }

        char[] array = alg.toCharArray();
        int index = 0;
        while (index < array.length) {
            if (index + 1 < array.length && Character.isDigit(array[index + 1])) {
                // double move
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

    /**
     * Solving cube
     */

    public String solve() {
        StringBuilder builderSolve = new StringBuilder();

        builderSolve.append(orientLeftWall());

        this.rotateY(2);
        builderSolve.append("y2");

        builderSolve.append(orientLeftWall());
        builderSolve.append(orientLastTwoEdges());

        this.rotateZ(1);
        builderSolve.append("z");

        builderSolve.append(orientLastLayer());
        builderSolve.append(permuteLastLayer());

        this.rotateZ(2);
        builderSolve.append("z2");

        builderSolve.append(permuteLastLayer());

        while (cube[4][3]!=cube[5][3]) {
            this.moveU(1);
            builderSolve.append("U");
        }

        return builderSolve.toString();
    }

    private String permuteLastLayer() {
        String solveAlg = "";
        if (cube[2][1]==cube[3][1] && cube[2][4]==cube[3][4])
            return solveAlg;
        else if (cube[1][2]+cube[4][3]==5){
            this.moveCube(J_PERM);
            solveAlg+=J_PERM;
        }
        else{
            while (cube[2][1]!=cube[3][1]) {
                this.moveU(1);
                solveAlg+="U";
            }

            this.moveCube(T_PERM);
            solveAlg+= T_PERM;
        }
        return solveAlg;
    }

    private String orientLastLayer() {
        String solvAlg = "";

        int check=0;
        for (int i=2; i<4; i++){
            for (int j=2; j<4; j++){
                if (cube[i][j]==0)
                    check++;
                else check--;
            }
        }
        int upperColor = check<0 ? 5 : 0;

        if (Math.abs(check)==4)
            return solvAlg;
        else if (Math.abs(check)==2){
            while (cube[3][3]==upperColor) {
                this.moveU(1);
                solvAlg+="U";
            }
            while (cube[3][7]!=upperColor) {
                this.moveD(1);
                solvAlg+="D";
            }
            this.moveCube(ORIENT_ONE_ON_BOTH);
            solvAlg+= ORIENT_ONE_ON_BOTH;
        }
        else {
            if (isOllLayoutCrossed(2, 2) && isOllLayoutCrossed(2,6)){
                while (cube[3][3]!=cube[2][7]) {
                    this.moveU(1);
                    solvAlg+="U";
                }
                this.moveCube(ORIENT_X_BOTH_WALLS);
                solvAlg+= ORIENT_X_BOTH_WALLS;
            }
            else if (isOllLayoutCrossed(2, 2) && !isOllLayoutCrossed(2,6)){
                while (cube[2][6]!=cube[3][6]) {
                    this.moveD(1);
                    solvAlg+="D";
                }
                if(cube[3][7]==cube[3][3]) {
                    this.moveCube(ORIENT_X_AND_TWO);
                    solvAlg+= ORIENT_X_AND_TWO;
                }
                else {
                    this.moveCube(ORIENT_X_AND_TWO_REV);
                    solvAlg+= ORIENT_X_AND_TWO_REV;
                }
            }
            else if (!isOllLayoutCrossed(2, 2) && isOllLayoutCrossed(2,6)){
                while (cube[2][3]!=cube[3][3]) {
                    this.moveU(1);
                    solvAlg+="U";
                }
                if(cube[3][6]==cube[3][2]) {
                    this.moveCube(ORIENT_TWO_AND_X);
                    solvAlg+= ORIENT_TWO_AND_X;
                }
                else {
                    this.moveCube(ORIENT_TWO_AND_X_REV);
                    solvAlg+= ORIENT_TWO_AND_X_REV;
                }

            }
            else if (!isOllLayoutCrossed(2, 2) && !isOllLayoutCrossed(2,6)){
                while (cube[2][3]!=cube[3][3]) {
                    this.moveU(1);
                    solvAlg+="U";
                }
                int color = cube[3][3];
                while (cube[2][6]==color || cube[3][6]==color) {
                    this.moveD(1);
                    solvAlg+="D";
                }
                this.moveR(2);
                solvAlg+="R2";
            }
        }

        return solvAlg;
    }

    private boolean isOllLayoutCrossed(int x, int y){
        if(cube[x][y]==cube[x+1][y+1])
            return true;
        return false;
    }

    private String orientLastTwoEdges() {
        String solvAlg = "";
        if(cube[3][1] != 0 && cube[3][1] != 5 && cube[2][4] != 0 && cube[2][4] != 5){
            this.moveR(-1);
            solvAlg+="R'";
            this.rotateZ(-1);
            solvAlg+="z'";
            while (cube[3][2] != 0 && cube[3][2] != 5) {
                this.moveCube(SEXY_MOVE_ON_LEFT_DOUBLE);
                solvAlg+=SEXY_MOVE_ON_LEFT_DOUBLE;
            }
            this.moveL(-1);
            solvAlg+="L'";
            while (cube[4][2] != 0 && cube[4][2] != 5) {
                this.moveCube(SEXY_MOVE_ON_LEFT_DOUBLE);
                solvAlg+=SEXY_MOVE_ON_LEFT_DOUBLE;
            }
            this.moveL(1);
            solvAlg+="L";
            this.rotateZ(1);
            solvAlg+="z";
        }
        return solvAlg;
    }

    private String orientLeftWall() {
        // on left and right yellow/white stickers
        String solvAlg = "";
        int sexyCounter = 0;
        for (int i = 4; i > 0; i--) {
            while (cube[3][1] != 0 && cube[3][1] != 5) {
                this.moveCube(SEXY_MOVE_ON_LEFT_DOUBLE);
                solvAlg+=SEXY_MOVE_ON_LEFT_DOUBLE;
                sexyCounter += 2;
            }
            this.moveL(1);
            solvAlg+="L";
        }
        while (sexyCounter % 6 != 0) {
            this.moveCube(SEXY_MOVE_ON_LEFT_DOUBLE);
            solvAlg+=SEXY_MOVE_ON_LEFT_DOUBLE;
            sexyCounter = sexyCounter + 2;
        }
        return solvAlg;
    }

}
















