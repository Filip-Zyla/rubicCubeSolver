package cubes;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import static cubes.Cube_2x2.getString;

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
        Cube_2x2.paint(height, width, dimension, cube);
    }

    @Override
    public String toString() {
        return getString(height, width, cube);
    }

    public void setCube(int i, int j) {
        this.cube[i][j] = cube[i][j];
    }

    public int getCube(int i, int j) {
        return cube[i][j];
    }

    public int[][] getCubeAsMatrices(){
        return cube;
    }

    public void setCubeAsMatrices(int[][] cube){
        this.cube=cube;
    }
}
