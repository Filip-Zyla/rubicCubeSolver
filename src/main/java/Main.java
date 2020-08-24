import cubes.Cube_2x2;

public class Main {
    public static void main(String[] args) {
        Cube_2x2 cube = new Cube_2x2();
        cube.move_U(1);
        cube.move_R(1);
        cube.move_B(1);
        cube.move_U(-1);
        cube.move_L(1);
        cube.move_D(1);
        cube.move_R(1);
        cube.move_U(+1);



        cube.show();


        System.out.println(cube.toString());
    }
}
