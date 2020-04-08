package cubes;

import lombok.*;
import moveInterfaces.moveOneWallInterface;


@Data
public class Cube_2x2 implements moveOneWallInterface {
    private final int NUMBER_OF_WALLS = 6; /** colors */
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
     * orientation: laying cross:
     * 99  44  99  99
     * 33  00  22  55

         * nulls as 9 in code
     * */

    public Cube_2x2(){
        cube=new int[HEIGHT][WIDTH];
        paintCube();
        System.out.println(this.toString());
    }

    private void paintCube() {
        for (int i=0; i<HEIGHT; i++){
            for (int j=0; j<WIDTH; j++){
                if ((i<DEGREE_OF_CUBE || i>=DEGREE_OF_CUBE*2) && (j<DEGREE_OF_CUBE || j>=DEGREE_OF_CUBE*2)){
                    cube[i][j]=9;
                }
                else if (i<DEGREE_OF_CUBE){
                    cube[i][j]=4;
                }
                else if (i>=DEGREE_OF_CUBE*2){
                    cube[i][j]=1;
                }
                else {
                    if (j<DEGREE_OF_CUBE){
                        cube[i][j]=3;
                    }
                    else if(j<DEGREE_OF_CUBE*2){
                        cube[i][j]=0;
                    }
                    else if(j<DEGREE_OF_CUBE*3){
                        cube[i][j]=2;
                    }
                    else {
                        cube[i][j]=5;
                    }
                }
            }
        }
    }
    @Override
    public String toString() {
        StringBuilder stringBuilder=new StringBuilder();
        for (int i = 0; i<HEIGHT; i++){
            for (int j=0; j<WIDTH; j++){
                stringBuilder.append(cube[i][j]);
                stringBuilder.append(" ");
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }


    @Override
    public void move_U(int rotate) {
        while (true){
        }
    }

    @Override
    public void move_D(int rotate) {

    }

    @Override
    public void move_E(int rotate) {

    }

    @Override
    public void move_R(int rotate) {

    }

    @Override
    public void move_L(int rotate) {

    }

    @Override
    public void move_M(int rotate) {

    }

    @Override
    public void move_F(int rotate) {

    }

    @Override
    public void move_B(int rotate) {

    }

    @Override
    public void move_S(int rotate) {

    }
}
