package cubes;

import java.util.ArrayList;
import java.util.List;

public class QuickestSolve {
    private final int GODS_NUMBER = 14;
    private final String[] possibleMoves = {"U", "U2", "U'", "R", "R2", "R'", "F", "F2", "F'",
                                            "D", "D2", "D'", "L", "L2", "L'", "B", "B2", "B'"};
    private Cube2x2 cube2x2;

    public QuickestSolve(Cube2x2 cube2x2){
        this.cube2x2=cube2x2;
    }

    public String solve(){
        List<String> listOfSolves = new ArrayList<>();
        String scramble = Algorithm.randomScramble(15,20);
        cube2x2.moveCube(scramble);
        String solvingScramble = findSolve(cube2x2,"");
        return solvingScramble;
    }

    private String findSolve(Cube2x2 cube2x2, String curSolve){
        for(int i=0; i<possibleMoves.length; i++){
            cube2x2.moveCube(possibleMoves[i]);
            curSolve+=possibleMoves[i];
            if(isSolved(cube2x2))
                return curSolve;
            curSolve+=findSolve(cube2x2, curSolve);
        }
        return curSolve;
    }

    private boolean isSolved(Cube2x2 cube2x2) {
        if(cube2x2.getCube()[2][2]!=cube2x2.getCube()[2][3] || cube2x2.getCube()[3][2]!=cube2x2.getCube()[3][3] || cube2x2.getCube()[2][2]!=cube2x2.getCube()[3][3])
            return false;
        if(cube2x2.getCube()[2][4]!=cube2x2.getCube()[2][5] || cube2x2.getCube()[3][4]!=cube2x2.getCube()[3][5] || cube2x2.getCube()[2][4]!=cube2x2.getCube()[3][5])
            return false;
        if(cube2x2.getCube()[4][2]!=cube2x2.getCube()[4][3] || cube2x2.getCube()[5][2]!=cube2x2.getCube()[5][3] || cube2x2.getCube()[4][2]!=cube2x2.getCube()[5][3])
            return false;
        return true;
    }
}
