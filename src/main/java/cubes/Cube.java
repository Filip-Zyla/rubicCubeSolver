package cubes;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
public class Cube {
    private final int NUMBER_OF_COLORS=6; /** walls also */
    private int dimension;
    private int height;
    private int width;
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private int[][] cube;

    /**
     * colors id's:
     * white=0 yellow=5
     * red=1 orange=4
     * blue=2 green=3
     *
     * orientation: laying cross:
     * 9999  4444  9999  9999
     * 3333  0000  2222  5555
     * 9999  1111  9999  9999
     * nulls as 9 in code
     * */

    public Cube(int size){
        this.dimension =size;
        this.height=size*3;
        this.width=size*4;
        cube=new int[height][width];
        paintCube();
    }

    void paintCube() {
        for (int i=0; i<height; i++){
            for (int j=0; j<width; j++){
                if ((i<dimension || i>=dimension*2) && (j<dimension || j>=dimension*2)){
                    cube[i][j]=9;
                }
                else if (i<dimension){
                    cube[i][j]=4;
                }
                else if (i>=dimension*2){
                    cube[i][j]=1;
                }
                else {
                    if (j<dimension){
                        cube[i][j]=3;
                    }
                    else if(j<dimension*2){
                        cube[i][j]=0;
                    }
                    else if(j<dimension*3){
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
        for (int i = 0; i<height; i++){
            for (int j=0; j<width; j++){
                stringBuilder.append(cube[i][j]);
                stringBuilder.append(" ");
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

    public void setCube(int i, int j) {
        this.cube[i][j] = cube[i][j];
    }

    public int getCube(int i, int j) {
        return cube[i][j];
    }

    public int[][] getCube_asMatrices(){
        return cube;
    }
    public void setCube_asMatrices(int[][] cube){
        this.cube=cube;
    }
}
