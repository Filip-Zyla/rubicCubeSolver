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

    /**
     * ================================================================
     * Solving part, ortega method
     * ================================================================
     **/

    //TODO move to another class
    private final List<Pair<Integer, Integer>> walls =
            Arrays.asList(new Pair<>(0, 2), new Pair<>(2, 0), new Pair<>(2, 2), new Pair<>(2, 4), new Pair<>(2, 6), new Pair<>(4, 2));
    private final List<Pair<Integer, Integer>> colors = Arrays.asList(new Pair<>(0, 5), new Pair<>(1, 4), new Pair<>(2, 3));

    private final String SEXY_MOVE_ON_LEFT_DOUBLE = "U'R'URU'R'UR";

    private final String OBL_ONE_UP = "R2DR2";  // front-right
    private final String OBL_Y_BOTH = "R2D2F2";
    private final String OBL_T_DOWN_Y_UP_OPP = "R2U'R2U'R2";   //are left-fronts opposite
    private final String OBL_T_DOWN_Y_UP_SAME = "R2UR2UR2"; //are left-fronts same
    private final String OBL_Y_DOWN_T_UP_OPP = "R2DR2DR2";   //are left-fronts opposite
    private final String OBL_Y_DOWN_T_UP_SAME = "R2D'R2D'R2";   //are left-fronts same

    private final String OLL_UP_H = "R2U2RU2R2";    //front and back
    private final String OLL_UP_P = "RU2R2U'R2U'R2U2R"; //pair on right
    private final String OLL_UP_L = "FRU'R'U'RUR'F'";   // front-left and right-back
    private final String OLL_UP_U = "FRUR'U'F'";    //pair on left
    private final String OLL_UP_T = "L'U'LURU'L'Ux'"; // on right front and back
    private final String OLL_UP_A_COUNTER = "RUR'URU2R'";   //up on front-left
    private final String OLL_UP_A_CLOCK = "RU2R'U'RU'R'";   //up on back-right

    private final String PLL_T = "RU2R'U'RU2L'UR'U'L";  //on right, up
    private final String PLL_T_DOWN = "z2RU2R'U'RU2L'UR'U'L";   //on left, down
    private final String PLL_Y = "RU'R'U'F2U'RUR'DR2";  // right-front -> left-back, up
    private final String PLL_Y_DOWN = "x2RU'R'U'F2U'RUR'DR2";   // left-front -> right-back, down
    private final String PLL_T_BOTH = "R2U'B2U2R2U'R2"; //both on back
    private final String PLL_Y_BOTH = "R2F2R2"; //4 blocks of two
    private final String PLL_Y_DOWN_T_UP = "RU'RF2R'UR'";    //T on back up, Y left-front to right-back down
    private final String PLL_T_DOWN_Y_UP = "z2RU'RF2R'UR'";    //T on back up, Y right-front to left-back down

    public String solve() {
        String solveAlg = "";

        if (this.isSolved())
            return solveAlg;

        solveAlg += ortegaFirstStep();
        solveAlg += orientBothLayers();
        solveAlg += permuteBothLayers();

        if (this.isSolved())
            return Algorithm.optimizeAlg(solveAlg);
        else
            return "Error";
    }

    //TODO improve
    private String ortegaFirstStep() {
        StringBuilder builderSolve = new StringBuilder();
        // < wallsPair, colorsPair/colorInt >
        List<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> fullWall = new ArrayList<>();
        List<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> threeWall = new ArrayList<>();
        List<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> twoWall = new ArrayList<>();

        List<Pair<Pair<Integer, Integer>, Integer>> fullWallUniColor = new ArrayList<>();
        List<Pair<Pair<Integer, Integer>, Integer>> threeWallUniColor = new ArrayList<>();
        List<Pair<Pair<Integer, Integer>, Integer>> twoWallUniColor = new ArrayList<>();

        //find colors on all walls
        for (Pair w : walls) {
            int x = (int) w.getValue0();
            int y = (int) w.getValue1();
            int[] wall = {array[x][y], array[x][y + 1], array[x + 1][y + 1], array[x + 1][y]};

            for (Pair c : colors) {
                int c0 = (int) c.getValue0();
                int c1 = (int) c.getValue1();
                List<Integer> numOfStickersC0 = new ArrayList<>();
                List<Integer> numOfStickersC1 = new ArrayList<>();
                List<Integer> numOfStickers = new ArrayList<>();

                for (int i = 0; i < wall.length; i++) {
                    if (wall[i] == c0) {
                        numOfStickersC0.add(i);
                        numOfStickers.add(i);
                    }
                    if (wall[i] == c1) {
                        numOfStickersC1.add(i);
                        numOfStickers.add(i);
                    }
                }

                if (numOfStickersC0.size() == 4) {
                    fullWallUniColor.add(new Pair<>(w, c0));
                }
                else if (numOfStickersC1.size() == 4) {
                    fullWallUniColor.add(new Pair<>(w, c1));
                }
                else if (numOfStickersC0.size() == 3) {
                    threeWallUniColor.add(new Pair<>(w, c0));
                }
                else if (numOfStickersC1.size() == 3) {
                    threeWallUniColor.add(new Pair<>(w, c1));
                }

                if (numOfStickers.size() == 4) {
                    fullWall.add(new Pair<>(w, c));
                }
                else if (numOfStickers.size() == 3) {
                    threeWall.add(new Pair<>(w, c));
                }
                //if are next to each other
                else if (numOfStickers.size() == 2 && Math.abs(numOfStickers.get(0) - numOfStickers.get(1)) != 2) {
                    twoWall.add(new Pair<>(w, c));
                    if (wall[numOfStickers.get(0)] == c0 && wall[numOfStickers.get(1)] == c0) {
                        twoWallUniColor.add(new Pair<>(w, c0));
                    }
                    else if (wall[numOfStickers.get(0)] == c1 && wall[numOfStickers.get(1)] == c1) {
                        twoWallUniColor.add(new Pair<>(w, c1));
                    }
                }
            }
        }

        if (!fullWallUniColor.isEmpty()) {
            if (fullWallUniColor.size() == 1) {
                builderSolve.append(givenWallToDown(fullWallUniColor.get(0).getValue0().getValue0(), fullWallUniColor.get(0).getValue0().getValue1()));
                builderSolve.append(orientLastLayer(5 - fullWallUniColor.get(0).getValue1(), 5 - fullWallUniColor.get(0).getValue1()));
                return builderSolve.toString();
            }
            else if (fullWallUniColor.size() == 2) {
                if (fullWallUniColor.get(0).getValue0().getValue0() + fullWallUniColor.get(1).getValue0().getValue0() % 4 == 0
                        && fullWallUniColor.get(0).getValue0().getValue1() + fullWallUniColor.get(1).getValue0().getValue1() % 4 == 0) {
                    //on opposite walls
                    builderSolve.append(givenWallToDown(fullWallUniColor.get(0).getValue0().getValue0(), fullWallUniColor.get(0).getValue0().getValue1()));
                    return builderSolve.toString();
                }
                else {
                    //L-shape, just take first wall
                    builderSolve.append(givenWallToDown(fullWallUniColor.get(0).getValue0().getValue0(), fullWallUniColor.get(0).getValue0().getValue1()));
                    builderSolve.append(orientLastLayer(5 - fullWallUniColor.get(0).getValue1(), 5 - fullWallUniColor.get(0).getValue1()));
                    return builderSolve.toString();
                }
            }
            else if (fullWallUniColor.size() == 3) {
                //U-shape, three wall
                builderSolve.append(parallelUniColorWallsToUpDown());
            }
        }
        else if (!threeWallUniColor.isEmpty()) {
            if (threeWallUniColor.size() == 1) {
                builderSolve.append(givenWallToDown(threeWallUniColor.get(0).getValue0().getValue0(), threeWallUniColor.get(0).getValue0().getValue1()));
                builderSolve.append(orientLastPieceInThreeWall(threeWallUniColor.get(0).getValue1(), threeWallUniColor.get(0).getValue1()));
                builderSolve.append(orientLastLayer(5 - threeWallUniColor.get(0).getValue1(), 5 - threeWallUniColor.get(0).getValue1()));
                return builderSolve.toString();
            }
            else {
                builderSolve.append(givenWallToDown(threeWallUniColor.get(0).getValue0().getValue0(), threeWallUniColor.get(0).getValue0().getValue1()));
                builderSolve.append(orientLastPieceInThreeWall(threeWallUniColor.get(0).getValue1(), threeWallUniColor.get(0).getValue1()));
                builderSolve.append(orientLastLayer(5 - threeWallUniColor.get(0).getValue1(), 5 - threeWallUniColor.get(0).getValue1()));
                return builderSolve.toString();
            }
        }
        else if (!fullWall.isEmpty() || !threeWall.isEmpty()) {
            Pair p;
            if (fullWall.size() > 0) {
                p = fullWall.get(0);
            }
            else {
                p = threeWall.get(0);
            }
            Pair w = (Pair) p.getValue0();
            Pair c = (Pair) p.getValue1();
            int x = (int) w.getValue0();
            int y = (int) w.getValue1();
            int c0 = (int) c.getValue0();
            int c1 = (int) c.getValue1();
            builderSolve.append(givenWallToDown(x, y));

            if (fullWall.isEmpty() && !threeWall.isEmpty()) {
                builderSolve.append(orientLastPieceInThreeWall(c0, c1));
            }
            builderSolve.append(orientLastLayer(c0, c1));
        }
        else {
            //only for white/yellow
            builderSolve.append(orientLeftWall());
            rotateZ(-1);
            builderSolve.append("z'");
            builderSolve.append(orientLastLayer(0, 5));
        }
        return builderSolve.toString();
    }

    private String givenWallToDown(int x, int y) {
        if (x == 2 && y != 6) {
            if (y == 0) {
                rotateZ(-1);
                return "z'";
            }
            else {
                if (y == 2) {
                    rotateZ(2);
                    return "z2";
                }
                else {
                    rotateZ(1);
                    return "z";
                }
            }
        }
        else if (y == 2) {
            if (x == 0) {
                rotateX(1);
                return "x";
            }
            else {
                rotateX(-1);
                return "x'";
            }
        }
        return "";
    }

    private String parallelUniColorWallsToUpDown() {
        if (isWallUniColor(2, 0) && isWallUniColor(2, 4)) {
            rotateZ(1);
            return "z";
        }
        else if (isWallUniColor(0, 2) && isWallUniColor(4, 2)) {
            rotateX(1);
            return "x";
        }
        return "";
    }

    private boolean isWallUniColor(int x, int y) {
        return array[x][y] == array[x + 1][y + 1] && array[x + 1][y] == array[x][y + 1] && array[x][y] == array[x][y + 1];
    }

    private String orientLastPieceInThreeWall(int c0, int c1) {
        String moves = "";
        while (array[3][6] == c0 || array[3][6] == c1) {
            moveD(1);
            moves += "D";
        }
        if (array[5][3] == c0) {
            moves += "F'U'FUR'FRF'";
            moveCube("F'U'FUR'FRF'");
            return moves;
        }
        else if (array[3][5] == c0) {
            moves += "RUR'U'RUR'";
            moveCube("RUR'U'RUR'");
            return moves;
        }
        while (true) {
            if (array[3][4] == c0 || array[3][4] == c1) {
                moves += "RUR'";
                moveCube("RUR'");
                return moves;
            }
            else if (array[4][3] == c0 || array[4][3] == c1) {
                moves += "R'FRF'";
                moveCube("R'FRF'");
                return moves;
            }
            else if (array[3][3] == c0 || array[3][3] == c1) {
                moves += "RU2R'U'RUR'";
                moveCube("RU2R'U'RUR'");
                return moves;
            }
            moves += "U";
            moveU(1);
        }
    }

    private String orientLastLayer(int c0, int c1) {
        List<Integer> topStickers = Arrays.asList(array[2][2], array[2][3], array[3][2], array[3][3]);

        Set s = new HashSet<Integer>();
        s.add(c0);
        s.add(c1);

        long countUp = topStickers.stream().filter(s::contains).count();

        StringBuilder builder = new StringBuilder();

        if (countUp == 0) {
            while (true) {
                if (s.contains(array[1][2]) && s.contains(array[1][3]) && s.contains(array[4][2]) && s.contains(array[4][3])) {
                    builder.append(OLL_UP_H);
                    moveCube(OLL_UP_H);
                    break;
                }
                if (s.contains(array[2][1]) && s.contains(array[3][1]) && s.contains(array[1][3]) && s.contains(array[4][3])) {
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
                if (s.contains(array[2][4]) && s.contains(array[4][2])) {
                    builder.append(OLL_UP_L);
                    moveCube(OLL_UP_L);
                    break;
                }
                if (s.contains(array[2][1]) && s.contains(array[3][1])) {
                    builder.append(OLL_UP_U);
                    moveCube(OLL_UP_U);
                    break;
                }
                if (s.contains(array[1][3]) && s.contains(array[4][3])) {
                    builder.append(OLL_UP_T);
                    moveCube(OLL_UP_T);
                    break;
                }
                builder.append("U");
                moveU(1);
            }
        }
        else if (countUp == 1) {
            while (true) {
                if (s.contains(array[1][2]) && s.contains(array[2][4]) && s.contains(array[4][3])) {
                    builder.append(OLL_UP_A_COUNTER);
                    moveCube(OLL_UP_A_COUNTER);
                    break;
                }
                if (s.contains(array[2][1]) && s.contains(array[4][2]) && s.contains(array[3][4])) {
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

    private String orientLeftWall() {
        String solveAlg = "";
        for (int i = 0; i < 4; i++) {
            while (array[3][1] != 0 && array[3][1] != 5) {
                moveCube(SEXY_MOVE_ON_LEFT_DOUBLE);
                solveAlg += SEXY_MOVE_ON_LEFT_DOUBLE;
            }
            moveL(1);
            solveAlg += "L";
        }
        return solveAlg;
    }

    private String orientBothLayers() {
        String solveAlg = "";

        int posColor = array[2][2];
        int check = 0;
        for (int i = 2; i < 4; i++) {
            for (int j = 2; j < 4; j++) {
                if (array[i][j] == posColor)
                    check++;
                else check--;
            }
        }
        int upperColor = check < 0 ? 5 - posColor : posColor;

        if (Math.abs(check) == 4) {
            return "";
        }
        else if (Math.abs(check) == 2) {
            while (array[3][3] == upperColor) {
                moveU(1);
                solveAlg += "U";
            }
            while (array[3][7] != upperColor) {
                moveD(1);
                solveAlg += "D";
            }
            moveCube(OBL_ONE_UP);
            solveAlg += OBL_ONE_UP;
        }
        else {
            if (isOblCrossed(2, 2) && isOblCrossed(2, 6)) {
                while (array[3][3] != array[3][6]) {
                    moveU(1);
                    solveAlg += "U";
                }
                moveCube(OBL_Y_BOTH);
                solveAlg += OBL_Y_BOTH;
            }
            else if (isOblCrossed(2, 2) && !isOblCrossed(2, 6)) {
                while (array[2][6] != array[3][6]) {
                    moveD(1);
                    solveAlg += "D";
                }
                if (array[3][7] == array[3][2]) {
                    moveCube(OBL_T_DOWN_Y_UP_SAME);
                    solveAlg += OBL_T_DOWN_Y_UP_SAME;
                }
                else {
                    moveCube(OBL_T_DOWN_Y_UP_OPP);
                    solveAlg += OBL_T_DOWN_Y_UP_OPP;
                }
            }
            else if (!isOblCrossed(2, 2) && isOblCrossed(2, 6)) {
                while (array[2][3] != array[3][3]) {
                    moveU(1);
                    solveAlg += "U";
                }
                if (array[3][7] == array[3][2]) {
                    moveCube(OBL_Y_DOWN_T_UP_SAME);
                    solveAlg += OBL_Y_DOWN_T_UP_SAME;
                }
                else {
                    moveCube(OBL_Y_DOWN_T_UP_OPP);
                    solveAlg += OBL_Y_DOWN_T_UP_OPP;
                }
            }
            else if (!isOblCrossed(2, 2) && !isOblCrossed(2, 6)) {
                while (array[2][3] != array[3][3]) {
                    moveU(1);
                    solveAlg += "U";
                }
                int color = array[3][3];
                while (array[2][6] == color || array[3][6] == color) {
                    moveD(1);
                    solveAlg += "D";
                }
                moveR(2);
                solveAlg += "R2";
            }
        }
        return solveAlg;
    }

    private boolean isOblCrossed(int x, int y) {
        return array[x][y] == array[x + 1][y + 1];
    }

    private String permuteBothLayers() {
        String solveAlg = "";
        if (isWallPermute(2) && isWallPermute(6)) {
        }
        else if (isWallPermute(6)) {
            if (isWall_Y_Perm( 2)) {
                moveCube(PLL_Y);
                solveAlg += PLL_Y;
            }
            else {
                while (array[2][1] != array[3][1]) {
                    moveU(1);
                    solveAlg += "U";
                }
                moveCube(PLL_T);
                solveAlg += PLL_T;
            }
        }
        else if (isWallPermute(2)) {
            if (isWall_Y_Perm( 6)) {
                moveCube(PLL_Y_DOWN);
                solveAlg += PLL_Y_DOWN;
            }
            else {
                while (array[2][5] != array[3][5]) {
                    moveD(1);
                    solveAlg += "D";
                }
                moveCube(PLL_T_DOWN);
                solveAlg += PLL_T_DOWN;
            }
        }
        else {
            if (isWall_Y_Perm( 2) && !isWall_Y_Perm( 6)) {
                while (array[5][2] != array[5][3]) {
                    moveD(1);
                    solveAlg += "D";
                }
                while (array[5][2] != array[4][2]) {
                    moveU(1);
                    solveAlg += "U";
                }
                moveCube(PLL_T_DOWN_Y_UP);
                solveAlg += PLL_T_DOWN_Y_UP;
            }
            else if (!isWall_Y_Perm( 2) && isWall_Y_Perm( 6)) {
                while (array[4][2] != array[4][3]) {
                    moveU(1);
                    solveAlg += "U";
                }
                while (array[5][3] != array[4][3]) {
                    moveD(1);
                    solveAlg += "D";
                }
                moveCube(PLL_Y_DOWN_T_UP);
                solveAlg += PLL_Y_DOWN_T_UP;
            }
            else if (!isWall_Y_Perm( 2) && !isWall_Y_Perm( 6)) {
                while (array[5][2] != array[5][3]) {
                    moveD(1);
                    solveAlg += "D";
                }
                while (array[4][2] != array[4][3]) {
                    moveU(1);
                    solveAlg += "U";
                }
                moveCube(PLL_T_BOTH);
                solveAlg += PLL_T_BOTH;
            }
            else {
                moveCube(PLL_Y_BOTH);
                solveAlg += PLL_Y_BOTH;
            }
        }
        while (array[2][1] != array[2][0]) {
            moveU(1);
            solveAlg += "U";
        }
        return solveAlg;
    }

    private boolean isWall_Y_Perm(int y) {
        // up and down only, y matters!
        if (y == 2) {
            return array[2][1] + array[3][1] == array[2][4] + array[3][4];
        }
        else if (y == 6) {
            return array[2][0] + array[3][0] == array[2][5] + array[3][5];
        }
        return false;
    }

    private boolean isWallPermute(int y) {
        // up and down only, y matters!
        if (y == 2) {
            return array[2][1] == array[3][1] && array[2][4] == array[3][4];
        }
        else if (y == 6) {
            return array[2][0] == array[3][0] && array[2][5] == array[3][5];
        }
        return false;
    }
}