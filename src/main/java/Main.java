import cubes.Cube2x2;
import cubes.QuickestSolve;

public class Main {

    public static void main(String[] args) {
        //new graphMenu(new Cube2x2());
        QuickestSolve quickestSolve = new QuickestSolve(new Cube2x2());
        System.out.println(quickestSolve.solve());
    }

}














