package cubes;

import lombok.*;
import moveInterfaces.moveOneWallInterfaceTwoCube;
import moveInterfaces.rotateInterface;
import org.javatuples.Pair;

import java.util.*;

@Data
public class Cube2x2 implements moveOneWallInterfaceTwoCube, rotateInterface {

    private final int NUMBER_OF_WALLS = 6;
    private final int DEGREE_OF_CUBE = 2;
    private final int HEIGHT = DEGREE_OF_CUBE * 3;
    private final int WIDTH = DEGREE_OF_CUBE * 4;
    private int[][] cube;

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


    private final String SEXY_MOVE_ON_LEFT_DOUBLE = "U'R'URU'R'UR";

    private final String PLL_T = "RU2R'U'RU2L'UR'U'L";  //on right, up
    private final String PLL_T_DOWN = "x2RU2R'U'RU2L'UR'U'L";   //on left, down
    private final String PLL_Y = "RU'R'U'F2U'RUR'DR2";  // right-front -> left-back, up
    private final String PLL_Y_DOWN = "x2RU'R'U'F2U'RUR'DR2";   // left-front -> right-back, down
    private final String PLL_T_BOTH = "R2U'B2U2R2U'R2"; //both on back
    private final String PLL_Y_BOTH = "R2F2R2"; //4 blocks of two
    private final String PLL_Y_DOWN_T_UP = "RU'RF2R'UR'";    //T on back up, Y left-front to right-back down
    private final String PLL_T_DOWN_Y_UP = "x2RU'RF2R'UR'";    //T on back up, Y right-front to left-back down

    private final String OLL_ONE_UP = "R2DR2";  // front-right
    private final String OLL_Y_BOTH = "R2D2F2";
    private final String OLL_T_DOWN_Y_UP_OPP = "R2U'R2U'R2";   //are left-fronts opposite
    private final String OLL_T_DOWN_Y_UP_SAME = "R2UR2UR2"; //are left-fronts same
    private final String OLL_Y_DOWN_T_UP_OPP = "R2DR2DR2";   //are left-fronts opposite
    private final String OLL_Y_DOWN_T_UP_SAME = "R2D'R2D'R2";   //are left-fronts same

    private final String OLL_UP_H = "R2U2RU2R2";    //front and back
    private final String OLL_UP_P = "RU2R2U'R2U'R2U2R"; //pair on right
    private final String OLL_UP_L = "FRU'R'U'RUR'F'";   // front-left and right-back
    private final String OLL_UP_U = "FRUR'U'F'";    //pair on left
    private final String OLL_UP_T = "L'U'LURU'L'Ux'"; // on right front and back
    private final String OLL_UP_A_COUNTER = "RUR'URU2R'";   //up on front-left
    private final String OLL_UP_A_CLOCK = "RU2R'U'RU'R'";   //up on back-right

    public String solve() {
        final String alg = Algorithm.randomScramble(15, 20);
        moveCube(alg);

        String solveAlg = "";

        solveAlg+=ortegaFirstStep();
        solveAlg+=orientBothLayer();
        solveAlg+=permuteBothLayers();

        return solveAlg;
    }

    private String ortegaFirstStep() {
        List<Pair<Integer, Integer>> walls = Arrays.asList(new Pair<>(0, 2), new Pair<>(2, 0), new Pair<>(2, 2), new Pair<>(2, 4), new Pair<>(2, 6), new Pair<>(4, 2));
        List<Pair<Integer, Integer>> colors = Arrays.asList(new Pair<>(0, 5), new Pair<>(1, 4), new Pair<>(2, 3));
        List<Pair> fullWall = new ArrayList<>();
        List<Pair> threeWall = new ArrayList<>();
        List<Pair> twoWall = new ArrayList<>();
        for (Pair w : walls) {
            for (Pair c : colors) {
                int x = (int) w.getValue0();
                int y = (int) w.getValue1();
                int c0 = (int) c.getValue0();
                int c1 = (int) c.getValue1();
                int[] wall = {cube[x][y], cube[x][y + 1], cube[x + 1][y + 1], cube[x + 1][y]};
                List<Integer> placeThatConsist = new ArrayList<>();
                for (int i = 0; i < wall.length; i++) {
                    if (wall[i] == c0 || wall[i] == c1) {
                        placeThatConsist.add(i);
                    }
                }
                if (placeThatConsist.size() == 4) {
                    fullWall.add(new Pair<>(w, c));
                }
                if (placeThatConsist.size() == 3) {
                    threeWall.add(new Pair<>(w, c));
                }
                if (placeThatConsist.size() == 2) {
                    if (Math.abs(placeThatConsist.get(0) - placeThatConsist.get(1)) == 1 || Math.abs(placeThatConsist.get(0) - placeThatConsist.get(1)) == 3) {
                        twoWall.add(new Pair<>(w, c));
                    }
                }
            }
        }

        StringBuilder builderSolve = new StringBuilder();
        if (!fullWall.isEmpty()) {
            Pair p = fullWall.get(0);
            Pair w = (Pair) p.getValue0();
            Pair c = (Pair) p.getValue1();
            int x = (int) w.getValue0();
            int y = (int) w.getValue1();
            int c0 = (int) c.getValue0();
            int c1 = (int) c.getValue1();

            if (x == 2 && y != 6) {
                if (y == 0) {
                    rotateZ(-1);
                    builderSolve.append("z'");
                }
                else {
                    if (y == 2) {
                        builderSolve.append("z");
                        rotateZ(1);
                    }
                    builderSolve.append("z");
                    rotateZ(1);
                }
            } else if (y == 2) {
                if (x < 2) {
                    builderSolve.append("x");
                    rotateX(1);
                }
                else {
                    builderSolve.append("x'");
                    rotateX(-1);
                }
            }

            builderSolve.append(orientLastLayer(c0, c1));
        }
        else if (!threeWall.isEmpty()) {
            Pair p = threeWall.get(0);
            Pair w = (Pair) p.getValue0();
            Pair c = (Pair) p.getValue1();
            int x = (int) w.getValue0();
            int y = (int) w.getValue1();
            int c0 = (int) c.getValue0();
            int c1 = (int) c.getValue1();

            if (x == 2 && y != 6) {
                if (y == 0) {
                    builderSolve.append("z'");
                    rotateZ(-1);
                }
                else {
                    if (y == 2) {
                        builderSolve.append("z");
                        rotateZ(1);
                    }
                    builderSolve.append("z");
                    rotateZ(1);
                }
            } else if (y == 2) {
                if (x < 2) {
                    builderSolve.append("x");
                    rotateX(1);
                }
                else {
                    rotateX(-1);
                    builderSolve.append("x'");
                }
            }
            while (cube[3][6] == c0 || cube[3][6] == c1){
                moveD(1);
                builderSolve.append("D");
            }
            while (true) {
                if (cube[3][4] == c0 || cube[3][4] == c1){
                    builderSolve.append("RUR'U'");
                    moveCube("RUR'U'");
                    break;
                }
                if (cube[4][3] == c0 || cube[4][3] == c1){
                    builderSolve.append("R'FRF'");
                    moveCube("R'FRF'");
                    break;
                }
                builderSolve.append("U");
                moveU(1);
            }
            builderSolve.append(orientLastLayer(c0, c1));
        }
//        else if (!twoWall.isEmpty()) {
//        }
        else {
            builderSolve.append(orientLeftWall());

            rotateY(2);
            builderSolve.append("y2");

            builderSolve.append(orientLeftWall());
            builderSolve.append(orientLastTwoEdges());

            rotateZ(1);
            builderSolve.append("z");
        }
        return builderSolve.toString();
    }

    private String orientLeftWall() {
        // on left for yellow/white
        String solveAlg = "";
        int counter = 0;
        for (int i = 4; i > 0; i--) {
            while (cube[3][1] != 0 && cube[3][1] != 5) {
                moveCube(SEXY_MOVE_ON_LEFT_DOUBLE);
                solveAlg+=SEXY_MOVE_ON_LEFT_DOUBLE;
                counter++;
            }
            moveL(1);
            solveAlg+="L";
        }
        while (counter % 3 != 0) {
            moveCube(SEXY_MOVE_ON_LEFT_DOUBLE);
            solveAlg+=SEXY_MOVE_ON_LEFT_DOUBLE;
            counter++;
        }
        return solveAlg;
    }

    private String orientLastTwoEdges() {
        String solveAlg = "";
        if(cube[3][1] != 0 && cube[3][1] != 5 && cube[2][4] != 0 && cube[2][4] != 5){
            moveR(-1);
            solveAlg+="R'";
            rotateZ(-1);
            solveAlg+="z'";
            while (cube[3][2] != 0 && cube[3][2] != 5) {
                moveCube(SEXY_MOVE_ON_LEFT_DOUBLE);
                solveAlg+=SEXY_MOVE_ON_LEFT_DOUBLE;
            }
            moveL(-1);
            solveAlg+="L'";
            while (cube[4][2] != 0 && cube[4][2] != 5) {
                moveCube(SEXY_MOVE_ON_LEFT_DOUBLE);
                solveAlg+=SEXY_MOVE_ON_LEFT_DOUBLE;
            }
            moveL(1);
            solveAlg+="L";
        }
        return solveAlg;
    }

    private String orientLastLayer(int c0, int c1) {
        List<Integer> topColors = Arrays.asList(cube[2][2], cube[2][3], cube[3][2],cube[3][3]);

        Set s = new HashSet<Integer>();
        s.add(c0);
        s.add(c1);

        long countUp = topColors.stream().filter(integer -> s.contains(integer)).count();


        StringBuilder builder = new StringBuilder();

        if (countUp == 0) {
            while (true) {
                if (s.contains(cube[1][2])  && s.contains(cube[1][3]) && s.contains(cube[4][2]) &&s.contains(cube[4][3])) {
                    builder.append(OLL_UP_H);
                    moveCube(OLL_UP_H);
                    break;
                }
                if (s.contains(cube[2][1]) && s.contains(cube[3][1]) && s.contains(cube[1][3]) && s.contains(cube[4][3])) {
                    builder.append(OLL_UP_P);
                    moveCube(OLL_UP_P);
                    break;
                }
                moveU(1);
                builder.append("U");
            }
        }
        else if (countUp == 2) {
            while (true) {
                if (s.contains(cube[2][4]) && s.contains(cube[4][2])) {
                    builder.append(OLL_UP_L);
                    moveCube(OLL_UP_L);
                    break;
                }
                if (s.contains(cube[2][1]) && s.contains(cube[3][1])) {
                    builder.append(OLL_UP_U);
                    moveCube(OLL_UP_U);
                    break;
                }
                if (s.contains(cube[1][3]) &&s.contains(cube[4][3])) {
                    builder.append(OLL_UP_T);
                    moveCube(OLL_UP_T);
                    break;
                }
                builder.append("U");
                moveU(1);
            }
        }
        else if (countUp == 3) {
            while (true) {
                if (s.contains(cube[1][2]) && s.contains(cube[2][4]) && s.contains(cube[4][3])) {
                    builder.append(OLL_UP_A_COUNTER);
                    moveCube(OLL_UP_A_COUNTER);
                    break;
                }
                if (s.contains(cube[2][1]) && s.contains(cube[4][2]) && s.contains(cube[3][4])) {
                    builder.append(OLL_UP_A_CLOCK);
                    moveCube(OLL_UP_A_CLOCK);
                    break;
                }
                moveU(1);
                builder.append("U");
            }
        }
        return builder.toString();
    }

    private String orientBothLayer() {
        String solveAlg = "";

        int posColor = cube[2][2];
        int check=0;
        for (int i=2; i<4; i++){
            for (int j=2; j<4; j++){
                if (cube[i][j]==posColor)
                    check++;
                else check--;
            }
        }
        int upperColor = check<0 ? 5-posColor : posColor;

        if (Math.abs(check)==4) {
            return "";
        }
        else if (Math.abs(check)==2){
            while (cube[3][3]==upperColor) {
                moveU(1);
                solveAlg+="U";
            }
            while (cube[3][7]!=upperColor) {
                moveD(1);
                solveAlg+="D";
            }
            moveCube(OLL_ONE_UP);
            solveAlg+= OLL_ONE_UP;
        }
        else {
            if (isOllLayoutCrossed(2, 2) && isOllLayoutCrossed(2,6)){
                while (cube[3][3]!=cube[3][6]) {
                    moveU(1);
                    solveAlg+="U";
                }
                moveCube(OLL_Y_BOTH);
                solveAlg+= OLL_Y_BOTH;
            }
            else if (isOllLayoutCrossed(2, 2) && !isOllLayoutCrossed(2,6)){
                while (cube[2][6]!=cube[3][6]) {
                    moveD(1);
                    solveAlg+="D";
                }
                if(cube[3][7]==cube[3][2]) {
                    moveCube(OLL_Y_DOWN_T_UP_SAME);
                    solveAlg+= OLL_Y_DOWN_T_UP_SAME;
                }
                else {
                    moveCube(OLL_Y_DOWN_T_UP_OPP);
                    solveAlg+= OLL_Y_DOWN_T_UP_OPP;
                }
            }
            else if (!isOllLayoutCrossed(2, 2) && isOllLayoutCrossed(2,6)){
                while (cube[2][3]!=cube[3][3]) {
                    moveU(1);
                    solveAlg+="U";
                }
                if(cube[3][7]==cube[3][2]) {
                    moveCube(OLL_Y_DOWN_T_UP_SAME);
                    solveAlg+= OLL_Y_DOWN_T_UP_SAME;
                }
                else {
                    moveCube(OLL_Y_DOWN_T_UP_OPP);
                    solveAlg+= OLL_Y_DOWN_T_UP_OPP;
                }
            }
            else if (!isOllLayoutCrossed(2, 2) && !isOllLayoutCrossed(2,6)){
                while (cube[2][3]!=cube[3][3]) {
                    moveU(1);
                    solveAlg+="U";
                }
                int color = cube[3][3];
                while (cube[2][6]==color || cube[3][6]==color) {
                    moveD(1);
                    solveAlg+="D";
                }
                moveR(2);
                solveAlg+="R2";
            }
        }

        return solveAlg;
    }

    private boolean isOllLayoutCrossed(int x, int y){
        return cube[x][y] == cube[x + 1][y + 1];
    }

    private String permuteBothLayers(){
        String solveAlg = "";
        if (isWallPermute(2,2) && isWallPermute(2, 6)) {
            while (cube[2][1]!=cube[2][0]) {
                moveU(1);
                solveAlg += "U";
            }
            return "";
        }
        else if (isWallPermute(2, 6)){
            if (isWall_Y_Perm(2, 2)){
                moveCube(PLL_Y);
                solveAlg+=PLL_Y;
            }
            else {
                while (cube[2][1]!=cube[3][1]) {
                    moveU(1);
                    solveAlg+="U";
                }
                moveCube(PLL_T);
                solveAlg+=PLL_T;
            }
        }
        else if (isWallPermute(2, 2)){
            if (isWall_Y_Perm(2, 6)){
                moveCube(PLL_Y_DOWN);
                solveAlg+=PLL_Y_DOWN;
            }
            else {
                while (cube[2][5]!=cube[3][5]) {
                    moveU(1);
                    solveAlg+="D";
                }
                moveCube(PLL_T_DOWN);
                solveAlg+=PLL_T_DOWN;
            }
        }
        else {
            if (isWall_Y_Perm(2, 2) && !isWall_Y_Perm(2, 6)){
                while (cube[5][2]!=cube[5][3]) {
                    moveD(1);
                    solveAlg+="D";
                }
                while (cube[5][2]!=cube[4][2]) {
                    moveU(1);
                    solveAlg+="U";
                }
                moveCube(PLL_T_DOWN_Y_UP);
                solveAlg+=PLL_T_DOWN_Y_UP;
            }
            else if(!isWall_Y_Perm(2, 2) && isWall_Y_Perm(2, 6)) {
                while (cube[4][2]!=cube[4][3]) {
                    moveU(1);
                    solveAlg+="U";
                }
                while (cube[5][3]!=cube[4][3]) {
                    moveD(1);
                    solveAlg+="D";
                }
                moveCube(PLL_Y_DOWN_T_UP);
                solveAlg+=PLL_Y_DOWN_T_UP;
            }
            else if(!isWall_Y_Perm(2, 2) && !isWall_Y_Perm(2, 6)) {
                while (cube[5][2]!=cube[5][3]) {
                    moveD(1);
                    solveAlg+="D";
                }
                while (cube[4][2]!=cube[4][3]) {
                    moveU(1);
                    solveAlg+="U";
                }
                moveCube(PLL_T_BOTH);
                solveAlg+=PLL_T_BOTH;
            }
            else {
                moveCube(PLL_Y_BOTH);
                solveAlg+=PLL_Y_BOTH;
            }
        }
        while (cube[2][1]!=cube[2][0]) {
            moveU(1);
            solveAlg+="U";
        }
        return solveAlg;
    }

    private boolean isWall_Y_Perm(int x, int y){
        //actually upper and down walls only, only y matters!!!
        if (x==2 && y==2){
            return cube[2][1] + cube[3][1] == cube[2][4] + cube[3][4];
        }
        else if (x==2 && y==6){
            return cube[2][0] + cube[3][0] == cube[2][5] + cube[3][5];
        }
        return false;
    }

    private boolean isWallPermute(int x, int y){
        //actually upper and down walls only y matters!!!
        if (x==2 && y==2){
            return cube[2][1] == cube[3][1] && cube[2][4] == cube[3][4];
        }
        else if (x==2 && y==6){
            return cube[2][0] == cube[3][0] && cube[2][5] == cube[3][5];
        }
        return false;
    }

}