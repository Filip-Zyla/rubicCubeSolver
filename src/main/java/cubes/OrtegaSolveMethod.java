package cubes;

import org.javatuples.Pair;

import java.util.*;

public class OrtegaSolveMethod {

    private Cube2x2 cube;
    private StringBuilder stringBuilder;

    // < wallsPair, colorsPair/colorInt >
    private List<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> fullWall = new ArrayList<>();
    private List<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> threeWall = new ArrayList<>();
    private List<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> twoWall = new ArrayList<>();

    private List<Pair<Pair<Integer, Integer>, Integer>> fullWallUniColor = new ArrayList<>();
    private List<Pair<Pair<Integer, Integer>, Integer>> threeWallUniColor = new ArrayList<>();
    private List<Pair<Pair<Integer, Integer>, Integer>> twoWallUniColor = new ArrayList<>();

    public OrtegaSolveMethod(Cube2x2 cube2x2){
        this.cube=cube2x2;
        stringBuilder = new StringBuilder();
    }

    public String solve() {
        if (cube.isSolved())
            return "";

        ortegaFirstStep();
        orientBothLayers();
        permuteBothLayers();

        if (cube.isSolved())
            return Algorithm.optimizeAlg(stringBuilder.toString());
        else
            return "Error";
    }

    //TODO improve
    private void ortegaFirstStep() {
        inspectWalls();

        if (!fullWallUniColor.isEmpty()) {
            if (fullWallUniColor.size() == 1) {
                givenWallToDown(fullWallUniColor.get(0).getValue0().getValue0(), fullWallUniColor.get(0).getValue0().getValue1());
                orientLastLayer(5 - fullWallUniColor.get(0).getValue1(), 5 - fullWallUniColor.get(0).getValue1());
            }
            else if (fullWallUniColor.size() == 2) {
                if (fullWallUniColor.get(0).getValue0().getValue0() + fullWallUniColor.get(1).getValue0().getValue0() % 4 == 0
                        && fullWallUniColor.get(0).getValue0().getValue1() + fullWallUniColor.get(1).getValue0().getValue1() % 4 == 0) {
                    //on opposite walls
                    givenWallToDown(fullWallUniColor.get(0).getValue0().getValue0(), fullWallUniColor.get(0).getValue0().getValue1());
                }
                else {
                    //L-shape, just take first wal
                    givenWallToDown(fullWallUniColor.get(0).getValue0().getValue0(), fullWallUniColor.get(0).getValue0().getValue1());
                    orientLastLayer(5 - fullWallUniColor.get(0).getValue1(), 5 - fullWallUniColor.get(0).getValue1());
                }
            }
            else if (fullWallUniColor.size() == 3) {
                //U-shape, three wall
                parallelUniColorWallsToUpDown();
            }
        }
        else if (!threeWallUniColor.isEmpty()) {
            if (threeWallUniColor.size() == 1) {
                givenWallToDown(threeWallUniColor.get(0).getValue0().getValue0(), threeWallUniColor.get(0).getValue0().getValue1());
                orientLastPieceInThreeWall(threeWallUniColor.get(0).getValue1(), threeWallUniColor.get(0).getValue1());
                orientLastLayer(5 - threeWallUniColor.get(0).getValue1(), 5 - threeWallUniColor.get(0).getValue1());
            }
            else {
                givenWallToDown(threeWallUniColor.get(0).getValue0().getValue0(), threeWallUniColor.get(0).getValue0().getValue1());
                orientLastPieceInThreeWall(threeWallUniColor.get(0).getValue1(), threeWallUniColor.get(0).getValue1());
                orientLastLayer(5 - threeWallUniColor.get(0).getValue1(), 5 - threeWallUniColor.get(0).getValue1());
            }
        }
        else if (!fullWall.isEmpty() || !threeWall.isEmpty()) {
            Pair p;
            if (!fullWall.isEmpty()) {
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
            givenWallToDown(x, y);

            if (fullWall.isEmpty()) {
                orientLastPieceInThreeWall(c0, c1);
            }
            orientLastLayer(c0, c1);
        }
        else if (!twoWallUniColor.isEmpty() || !twoWall.isEmpty()){
        }
        else {
            //only for white/yellow
            orientLeftWall();
            cube.rotateZ(-1);
            stringBuilder.append("z'");
            orientLastLayer(0, 5);
        }
    }

    private void inspectWalls(){
        final List<Pair<Integer, Integer>> walls =
                Arrays.asList(new Pair<>(0, 2), new Pair<>(2, 0), new Pair<>(2, 2), new Pair<>(2, 4), new Pair<>(2, 6), new Pair<>(4, 2));
        final List<Pair<Integer, Integer>> colors = Arrays.asList(new Pair<>(0, 5), new Pair<>(1, 4), new Pair<>(2, 3));

        for (Pair w : walls) {
            int x = (int) w.getValue0();
            int y = (int) w.getValue1();
            int[] wall = {cube.getArray()[x][y], cube.getArray()[x][y + 1], cube.getArray()[x + 1][y + 1], cube.getArray()[x + 1][y]};

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
                //if doubles are next to each other
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
    }

    private void givenWallToDown(int x, int y) {
        if (x == 2 && y != 6) {
            if (y == 0) {
                cube.rotateZ(-1);
                stringBuilder.append("z'");
            }
            else {
                if (y == 2) {
                    cube.rotateZ(2);
                    stringBuilder.append("z2");
                }
                else {
                    cube.rotateZ(1);
                    stringBuilder.append("z");
                }
            }
        }
        else if (y == 2) {
            if (x == 0) {
                cube.rotateX(1);
                stringBuilder.append("x");
            }
            else {
                cube.rotateX(-1);
                stringBuilder.append("x'");
            }
        }
    }

    private void parallelUniColorWallsToUpDown() {
        if (isWallUniColor(2, 0) && isWallUniColor(2, 4)) {
            cube.rotateZ(1);
            stringBuilder.append("z");
        }
        else if (isWallUniColor(0, 2) && isWallUniColor(4, 2)) {
            cube.rotateX(1);
            stringBuilder.append("x");
        }
    }

    private boolean isWallUniColor(int x, int y) {
        return cube.getArray()[x][y] == cube.getArray()[x + 1][y + 1] && cube.getArray()[x + 1][y] == cube.getArray()[x][y + 1] && cube.getArray()[x][y] == cube.getArray()[x][y + 1];
    }

    private void orientLastPieceInThreeWall(int c0, int c1) {
        // in place, but rotate
        final String FRONT_RIGHT_DOWN = "F'U'FUR'FRF'";
        final String RIGHT_FRONT_DOWN = "RUR'U'RUR'";
        // upper layer
        final String RIGHT_FRONT_UP = "RUR'";
        final String FRONT_RIGHT_UP = "URU'R'";
        final String UP_FRONT_RIGHT = "RU2R'U'RUR'";


        while (cube.getArray()[3][6] == c0 || cube.getArray()[3][6] == c1) {
            cube.moveD(1);
            stringBuilder.append("D");
        }
        if (cube.getArray()[5][3] == c0) {
            stringBuilder.append(FRONT_RIGHT_DOWN);
            cube.moveCube(FRONT_RIGHT_DOWN);
        }
        else if (cube.getArray()[3][5] == c0) {
            stringBuilder.append(RIGHT_FRONT_DOWN);
            cube.moveCube(RIGHT_FRONT_DOWN);
        }
        while (true) {
            if (cube.getArray()[3][4] == c0 || cube.getArray()[3][4] == c1) {
                stringBuilder.append(RIGHT_FRONT_UP);
                cube.moveCube(RIGHT_FRONT_UP);
            }
            else if (cube.getArray()[4][3] == c0 || cube.getArray()[4][3] == c1) {
                stringBuilder.append(FRONT_RIGHT_UP);
                cube.moveCube(FRONT_RIGHT_UP);
            }
            else if (cube.getArray()[3][3] == c0 || cube.getArray()[3][3] == c1) {
                stringBuilder.append(UP_FRONT_RIGHT);
                cube.moveCube(UP_FRONT_RIGHT);
            }
            stringBuilder.append("U");
            cube.moveU(1);
        }
    }

    private void orientLastLayer(int c0, int c1) {
        final String OLL_UP_H = "R2U2RU2R2";    //front and back
        final String OLL_UP_P = "RU2R2U'R2U'R2U2R"; //pair on right
        final String OLL_UP_L = "FRU'R'U'RUR'F'";   // front-left and right-back
        final String OLL_UP_U = "FRUR'U'F'";    //pair on left
        final String OLL_UP_T = "L'U'LURU'L'Ux'"; // on right front and back
        final String OLL_UP_A_COUNTER = "RUR'URU2R'";   //up on front-left
        final String OLL_UP_A_CLOCK = "RU2R'U'RU'R'";   //up on back-right

        List<Integer> topStickers = Arrays.asList(cube.getArray()[2][2], cube.getArray()[2][3], cube.getArray()[3][2], cube.getArray()[3][3]);

        Set s = new HashSet<Integer>();
        s.add(c0);
        s.add(c1);

        long countUp = topStickers.stream().filter(s::contains).count();

        if (countUp == 0) {
            while (true) {
                if (s.contains(cube.getArray()[1][2]) && s.contains(cube.getArray()[1][3]) && s.contains(cube.getArray()[4][2]) && s.contains(cube.getArray()[4][3])) {
                    stringBuilder.append(OLL_UP_H);
                    cube.moveCube(OLL_UP_H);
                    break;
                }
                if (s.contains(cube.getArray()[2][1]) && s.contains(cube.getArray()[3][1]) && s.contains(cube.getArray()[1][3]) && s.contains(cube.getArray()[4][3])) {
                    stringBuilder.append(OLL_UP_P);
                    cube.moveCube(OLL_UP_P);
                    break;
                }
                cube.moveU(1);
                stringBuilder.append("U");
            }
        }
        else if (countUp == 2) {
            while (true) {
                if (s.contains(cube.getArray()[2][4]) && s.contains(cube.getArray()[4][2])) {
                    stringBuilder.append(OLL_UP_L);
                    cube.moveCube(OLL_UP_L);
                    break;
                }
                if (s.contains(cube.getArray()[2][1]) && s.contains(cube.getArray()[3][1])) {
                    stringBuilder.append(OLL_UP_U);
                    cube.moveCube(OLL_UP_U);
                    break;
                }
                if (s.contains(cube.getArray()[1][3]) && s.contains(cube.getArray()[4][3])) {
                    stringBuilder.append(OLL_UP_T);
                    cube.moveCube(OLL_UP_T);
                    break;
                }
                stringBuilder.append("U");
                cube.moveU(1);
            }
        }
        else if (countUp == 1) {
            while (true) {
                if (s.contains(cube.getArray()[1][2]) && s.contains(cube.getArray()[2][4]) && s.contains(cube.getArray()[4][3])) {
                    stringBuilder.append(OLL_UP_A_COUNTER);
                    cube.moveCube(OLL_UP_A_COUNTER);
                    break;
                }
                if (s.contains(cube.getArray()[2][1]) && s.contains(cube.getArray()[4][2]) && s.contains(cube.getArray()[3][4])) {
                    stringBuilder.append(OLL_UP_A_CLOCK);
                    cube.moveCube(OLL_UP_A_CLOCK);
                    break;
                }
                cube.moveU(1);
                stringBuilder.append("U");
            }
        }
    }

    private void orientLeftWall() {
        final String DOWN_FRONT_RIGHT = "U'RU";
        final String FRONT_UP_RIGHT = "R'U'RU";
        final String UP_BACK_RIGHT = "R'FRF'";
        final String BACK_UP_RIGHT = "FR'F'";
        final String UP_FRONT_RIGHT = "RFR'F'";
        final String UP_FRONT_LEFT = "U'R'URU'R'U";
        final String FRONT_UP_LEFT = "FRF'R2U'RU";

        for (int i = 0; i < 4; i++) {
            if (cube.getArray()[3][1] != 0 && cube.getArray()[3][1] != 5) {
                if (cube.getArray()[3][6] != 0 && cube.getArray()[3][6] != 5) {
                    cube.moveCube(DOWN_FRONT_RIGHT);
                    stringBuilder.append(DOWN_FRONT_RIGHT);
                }
                else if (cube.getArray()[4][3] != 0 && cube.getArray()[4][3] != 5) {
                    cube.moveCube(FRONT_UP_RIGHT);
                    stringBuilder.append(FRONT_UP_RIGHT);
                }
                else if (cube.getArray()[2][3] != 0 && cube.getArray()[2][3] != 5) {
                    cube.moveCube(UP_BACK_RIGHT);
                    stringBuilder.append(UP_BACK_RIGHT);
                }
                else if (cube.getArray()[1][3] != 0 && cube.getArray()[1][3] != 5) {
                    cube.moveCube(BACK_UP_RIGHT);
                    stringBuilder.append(BACK_UP_RIGHT);
                }
                else if (cube.getArray()[3][3] != 0 && cube.getArray()[3][3] != 5) {
                    cube.moveCube(UP_FRONT_RIGHT);
                    stringBuilder.append(UP_FRONT_RIGHT);
                }
                else if (cube.getArray()[3][2] != 0 && cube.getArray()[3][2] != 5) {
                    cube.moveCube(UP_FRONT_LEFT);
                    stringBuilder.append(UP_FRONT_LEFT);
                }
                else if (cube.getArray()[4][2] != 0 && cube.getArray()[4][2] != 5) {
                    cube.moveCube(FRONT_UP_LEFT);
                    stringBuilder.append(FRONT_UP_LEFT);
                }
            }
            cube.moveL(1);
            stringBuilder.append("L");
        }
    }

    private void orientBothLayers() {
        final String OBL_ONE_UP = "R2DR2";  // front-right
        final String OBL_Y_BOTH = "R2D2F2";
        final String OBL_T_DOWN_Y_UP_OPP = "R2U'R2U'R2";   //are left-fronts opposite
        final String OBL_T_DOWN_Y_UP_SAME = "R2UR2UR2"; //are left-fronts same
        final String OBL_Y_DOWN_T_UP_OPP = "R2DR2DR2";   //are left-fronts opposite
        final String OBL_Y_DOWN_T_UP_SAME = "R2D'R2D'R2";   //are left-fronts same

        int posColor = cube.getArray()[2][2];
        int check = 0;
        for (int i = 2; i < 4; i++) {
            for (int j = 2; j < 4; j++) {
                if (cube.getArray()[i][j] == posColor)
                    check++;
                else check--;
            }
        }
        int upperColor = check < 0 ? 5 - posColor : posColor;

        if (Math.abs(check) == 2) {
            while (cube.getArray()[3][3] == upperColor) {
                cube.moveU(1);
                stringBuilder.append("U");
            }
            while (cube.getArray()[3][7] != upperColor) {
                cube.moveD(1);
                stringBuilder.append("D");
            }
            cube.moveCube(OBL_ONE_UP);
            stringBuilder.append(OBL_ONE_UP);
        }
        else if (Math.abs(check) != 4) {
            if (isOblCrossed(2, 2) && isOblCrossed(2, 6)) {
                while (cube.getArray()[3][3] != cube.getArray()[3][6]) {
                    cube.moveU(1);
                    stringBuilder.append("U");
                }
                cube.moveCube(OBL_Y_BOTH);
                stringBuilder.append(OBL_Y_BOTH);
            }
            else if (isOblCrossed(2, 2) && !isOblCrossed(2, 6)) {
                while (cube.getArray()[2][6] != cube.getArray()[3][6]) {
                    cube.moveD(1);
                    stringBuilder.append("D");
                }
                if (cube.getArray()[3][7] == cube.getArray()[3][2]) {
                    cube.moveCube(OBL_T_DOWN_Y_UP_SAME);
                    stringBuilder.append(OBL_T_DOWN_Y_UP_SAME);
                }
                else {
                    cube.moveCube(OBL_T_DOWN_Y_UP_OPP);
                    stringBuilder.append(OBL_T_DOWN_Y_UP_OPP);
                }
            }
            else if (!isOblCrossed(2, 2) && isOblCrossed(2, 6)) {
                while (cube.getArray()[2][3] != cube.getArray()[3][3]) {
                    cube.moveU(1);
                    stringBuilder.append("U");
                }
                if (cube.getArray()[3][7] == cube.getArray()[3][2]) {
                    cube.moveCube(OBL_Y_DOWN_T_UP_SAME);
                    stringBuilder.append(OBL_Y_DOWN_T_UP_SAME);
                }
                else {
                    cube.moveCube(OBL_Y_DOWN_T_UP_OPP);
                    stringBuilder.append(OBL_Y_DOWN_T_UP_OPP);
                }
            }
            else if (!isOblCrossed(2, 2) && !isOblCrossed(2, 6)) {
                while (cube.getArray()[2][3] != cube.getArray()[3][3]) {
                    cube.moveU(1);
                    stringBuilder.append("U");
                }
                int color = cube.getArray()[3][3];
                while (cube.getArray()[2][6] == color || cube.getArray()[3][6] == color) {
                    cube.moveD(1);
                    stringBuilder.append("D");
                }
                cube.moveR(2);
                stringBuilder.append("R2");
            }
        }
    }

    private boolean isOblCrossed(int x, int y) {
        return cube.getArray()[x][y] == cube.getArray()[x + 1][y + 1];
    }

    private void permuteBothLayers() {
        final String PLL_T = "RU2R'U'RU2L'UR'U'L";  //on right, up
        final String PLL_T_DOWN = "z2RU2R'U'RU2L'UR'U'L";   //on left, down
        final String PLL_Y = "RU'R'U'F2U'RUR'DR2";  // right-front -> left-back, up
        final String PLL_Y_DOWN = "x2RU'R'U'F2U'RUR'DR2";   // left-front -> right-back, down
        final String PLL_T_BOTH = "R2U'B2U2R2U'R2"; //both on back
        final String PLL_Y_BOTH = "R2F2R2"; //4 blocks of two
        final String PLL_Y_DOWN_T_UP = "RU'RF2R'UR'";    //T on back up, Y left-front to right-back down
        final String PLL_T_DOWN_Y_UP = "z2RU'RF2R'UR'";    //T on back up, Y right-front to left-back down

        if (isWallPermute(2) && isWallPermute(6)) {
        }
        else if (isWallPermute(6)) {
            if (isWall_Y_Perm(2)) {
                cube.moveCube(PLL_Y);
                stringBuilder.append(PLL_Y);
            }
            else {
                while (cube.getArray()[2][1] != cube.getArray()[3][1]) {
                    cube.moveU(1);
                    stringBuilder.append("U");
                }
                cube.moveCube(PLL_T);
                stringBuilder.append(PLL_T);
            }
        }
        else if (isWallPermute(2)) {
            if (isWall_Y_Perm(6)) {
                cube.moveCube(PLL_Y_DOWN);
                stringBuilder.append(PLL_Y_DOWN);
            }
            else {
                while (cube.getArray()[2][5] != cube.getArray()[3][5]) {
                    cube.moveD(1);
                    stringBuilder.append("D");
                }
                cube.moveCube(PLL_T_DOWN);
                stringBuilder.append(PLL_T_DOWN);
            }
        }
        else {
            if (isWall_Y_Perm(2) && !isWall_Y_Perm(6)) {
                while (cube.getArray()[5][2] != cube.getArray()[5][3]) {
                    cube.moveD(1);
                    stringBuilder.append("D");
                }
                while (cube.getArray()[5][2] != cube.getArray()[4][2]) {
                    cube.moveU(1);
                    stringBuilder.append("U");
                }
                cube.moveCube(PLL_T_DOWN_Y_UP);
                stringBuilder.append(PLL_T_DOWN_Y_UP);
            }
            else if (!isWall_Y_Perm(2) && isWall_Y_Perm(6)) {
                while (cube.getArray()[4][2] != cube.getArray()[4][3]) {
                    cube.moveU(1);
                    stringBuilder.append("U");
                }
                while (cube.getArray()[5][3] != cube.getArray()[4][3]) {
                    cube.moveD(1);
                    stringBuilder.append("D");
                }
                cube.moveCube(PLL_Y_DOWN_T_UP);
                stringBuilder.append(PLL_Y_DOWN_T_UP);
            }
            else if (!isWall_Y_Perm(2) && !isWall_Y_Perm(6)) {
                while (cube.getArray()[5][2] != cube.getArray()[5][3]) {
                    cube.moveD(1);
                    stringBuilder.append("D");
                }
                while (cube.getArray()[4][2] != cube.getArray()[4][3]) {
                    cube.moveU(1);
                    stringBuilder.append("U");
                }
                cube.moveCube(PLL_T_BOTH);
                stringBuilder.append(PLL_T_BOTH);
            }
            else {
                cube.moveCube(PLL_Y_BOTH);
                stringBuilder.append(PLL_Y_BOTH);
            }
        }
        while (cube.getArray()[2][1] != cube.getArray()[2][0]) {
            cube.moveU(1);
            stringBuilder.append("U");
        }
    }

    private boolean isWall_Y_Perm(int y) {
        // up and down only, y matters!
        if (y == 2) {
            return cube.getArray()[2][1] + cube.getArray()[3][1] == cube.getArray()[2][4] + cube.getArray()[3][4];
        }
        else if (y == 6) {
            return cube.getArray()[2][0] + cube.getArray()[3][0] == cube.getArray()[2][5] + cube.getArray()[3][5];
        }
        return false;
    }

    private boolean isWallPermute(int y) {
        // up and down only, y matters!
        if (y == 2) {
            return cube.getArray()[2][1] == cube.getArray()[3][1] && cube.getArray()[2][4] == cube.getArray()[3][4];
        }
        else if (y == 6) {
            return cube.getArray()[2][0] == cube.getArray()[3][0] && cube.getArray()[2][5] == cube.getArray()[3][5];
        }
        return false;
    }
}
