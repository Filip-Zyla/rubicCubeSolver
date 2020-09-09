package cubes;

import lombok.*;
import moveInterfaces.moveOneWallInterfaceTwoCube;
import moveInterfaces.rotateInterface;

import javax.swing.*;
import java.awt.*;


@Data
public class Cube_2x2 implements moveOneWallInterfaceTwoCube, rotateInterface  {
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
     * BLACK=9
     *
     * orientation: laying cross:
     * 99  44  99  99
     * 33  00  22  55
     * 99  11  99  99
     * nulls as 9 in code
     * */

    public Cube_2x2(){
        cube=new int[HEIGHT][WIDTH];
        paintCube();
    }

    private void paintCube() {
        paint(HEIGHT, WIDTH, DEGREE_OF_CUBE, cube);
    }

    static void paint(int height, int width, int degree_of_cube, int[][] cube) {
        for (int i = 0; i< height; i++){
            for (int j = 0; j< width; j++){
                if ((i< degree_of_cube || i>= degree_of_cube *2) && (j< degree_of_cube || j>= degree_of_cube *2)){
                    cube[i][j]=9;
                }
                else if (i< degree_of_cube){
                    cube[i][j]=4;
                }
                else if (i>= degree_of_cube *2){
                    cube[i][j]=1;
                }
                else {
                    if (j< degree_of_cube){
                        cube[i][j]=3;
                    }
                    else if(j< degree_of_cube *2){
                        cube[i][j]=0;
                    }
                    else if(j< degree_of_cube *3){
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
        return getString(HEIGHT, WIDTH, cube);
    }

    static String getString(int height, int width, int[][] cube) {
        StringBuilder stringBuilder=new StringBuilder();
        for (int i = 0; i< height; i++){
            for (int j = 0; j< width; j++){
                stringBuilder.append(cube[i][j]);
                stringBuilder.append(" ");
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }


    @Override
    public void move_U(int rotate) {
        if (rotate<0)
            move_U(4+rotate);
        else {
            int temp;
           for (int i=0; i<rotate; i++){
               temp = cube[2][2];
               cube[2][2] = cube[3][2];
               cube[3][2] = cube[3][3];
               cube[3][3] = cube[2][3];
               cube[2][3] = temp;

               temp = cube[2][1];
               cube[2][1] = cube[4][2];
               cube[4][2] = cube[3][4];
               cube[3][4] = cube[1][3];
               cube[1][3] = temp;

               temp = cube[1][2];
               cube[1][2] = cube[3][1];
               cube[3][1] = cube[4][3];
               cube[4][3] = cube[2][4];
               cube[2][4] = temp;


           }
        }
    }
    @Override
    public void move_D(int rotate) {
        if (rotate < 0)
            move_D(4 + rotate);
        else {
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
                cube[2][0] =  temp;

            }
        }
    }

    @Override
    public void move_R(int rotate) {
        if (rotate < 0)
            move_R(4 + rotate);
        else {
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
    }
    @Override
    public void move_L(int rotate) {
        if (rotate < 0)
            move_L(4 + rotate);
        else {
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
    }

    @Override
    public void move_F(int rotate) {
        if (rotate < 0)
            move_F(4 + rotate);
        else {
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
    }
    @Override
    public void move_B(int rotate) {
        // !!!!!!!!!!!!!!! clokwise switched !!!!!!!!!!!!!!!!!!!!!
        if (rotate > 0)
            move_B(-4 + rotate);
        else {
            int temp;
            for (int i = 0; i > rotate; i--) {
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
    }

    @Override
    public void rotate_x(int rotate) {
        this.move_R(rotate);
        this.move_L(-rotate);
    }
    @Override
    public void rotate_y(int rotate) {
        this.move_U(rotate);
        this.move_D(-rotate);
    }
    @Override
    public void rotate_z(int rotate) {
        this.move_F(rotate);
        this.move_B(-rotate);
    }


    public void moveCube(String alg){
        /**
        Assuming alg is correct
         */
        char[] array = alg.toCharArray();
        int index = 0;
        while (index < array.length){

            if (Character.isDigit(array[index])){
                moveGivenAlg(array[index+1], 2);
                index+=2;
            }
            else { // is letter
                if(array[index+1]==39){
                    moveGivenAlg(array[index], -1);
                    index+=2;
                }
                else{
                    moveGivenAlg(array[index], 1);
                    index+=1;
                }
            }
        }
    }

    private void moveGivenAlg(char wall, int rotate){
        switch (wall) {
            case 'R' -> this.move_R(rotate);
            case 'L' -> this.move_L(rotate);
            case 'U' -> this.move_U(rotate);
            case 'D' -> this.move_D(rotate);
            case 'F' -> this.move_F(rotate);
            case 'B' -> this.move_B(rotate);
            case 'x' -> this.rotate_x(rotate);
            case 'y' -> this.rotate_y(rotate);
            case 'z' -> this.rotate_z(rotate);
        }
    }



    public void show() {
        this.paint2D();
    }
   /*****************************************************************************
   GRAPHICS 2D ******************************************************************
    *****************************************************************************/
    class Grid extends JComponent {
       public void paintComponent(Graphics g) {
           super.paintComponent(g);
           Graphics2D g2d = (Graphics2D) g;

           for (int j = 0; j < 8; j++) {
               for (int i = 0; i < 6; i++) {
                   g2d.drawRect(j * 100, i * 100, 100, 100);

                   switch (cube[i][j]) {
                       case 0 -> g2d.setColor(Color.WHITE);
                       case 1 -> g2d.setColor(Color.RED);
                       case 2 -> g2d.setColor(Color.BLUE);
                       case 3-> g2d.setColor(Color.GREEN);
                       case 4 -> g2d.setColor(Color.ORANGE);
                       case 5 -> g2d.setColor(Color.YELLOW);
                       default -> g2d.setColor(Color.BLACK);


                   }


                   g2d.fillRect(j * 100, i * 100, 100, 100);
               }
           }
       }
   }

   public void paint2D() {
       JFrame window = new JFrame();
       window.setSize(850,650);
       window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       window.getContentPane().add(new Grid());
       window.setVisible(true);
   }


}

