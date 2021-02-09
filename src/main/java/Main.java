
import cubes.*;

import java.time.temporal.ChronoUnit;

public class Main {

    public static void main(String[] args) {
        //new graphMenu(new Cube2x2());
        //QuickestSolve quickestSolve = new QuickestSolve(new Cube2x2());
        Cube2x2 cube2x2 = new Cube2x2();
        System.out.println(cube2x2.solve());

    }

    private static void test1() {
        double poll = 100_000;

        int full = 0;
        int three = 0;
        int two = 0;
        int onlyTwo = 0;
        int zero = 0;
        int bezFull = 0;
        int bezTwo = 0;
        int coutTwos = 0;

        for(int i = 0; i< poll; i++) {
            Cube2x2 cube = new Cube2x2();
            int [] res = cube.solve();
            if(res[0]>0) {
                full++;
            }
            if(res[1]>0) {
                three++;
            }
            if(res[2]>0) {
                two++;
            }

            if(res[0]==0 && res[1]>0){
                bezFull++;
            }
            if(res[0]==0 && res[1]==0){
                onlyTwo++;
            }
            if(res[0]==0 && res[1]==0 && res[2]==0){
                zero++;
            }
            if(res[2]==0){
                bezTwo++;
            }
            if(res[2]==1 && res[0]==0 && res[1]==0){
                coutTwos++;
            }
        }
        System.out.println(
                          "Sum " + (int) poll
                        + "\nfull: " + full/poll + "   %"
                        + "\nthree: " + three/poll
                        + "\ntwo: " + two/poll
                        + "\n========================="
                        + "\nbez full: " + bezFull/poll + "   %"
                        + "\nonly two: " + onlyTwo/poll + "   %"
                        + "\nbez all: " + zero/poll
                        + "\nbez two: " + bezTwo/poll
                        + "\ntwo<2: " + coutTwos/poll
                        + "\nsum of %: " + (int)(full+bezFull+onlyTwo)/poll
        );
    }

}