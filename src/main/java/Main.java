import cubes.*;
import graphicalMenu.graphMenu;

public class Main {

    public static void main(String[] args) {
        //new graphMenu(new Cube2x2());
        String scr = Algorithm.randomScramble(10,15);
        System.out.println(scr);
        System.out.println(Algorithm.optimizeAlg(scr));
    }

}