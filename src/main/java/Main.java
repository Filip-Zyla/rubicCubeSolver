import cubes.Cube_2x2;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        consoleMenu(new Cube_2x2());
    }

    public static void consoleMenu(Cube_2x2 cube){
        System.out.println("!!!!!!!!!Welcome!!!!!!!!!!!");
        System.out.println("It's your menu");
        System.out.println("Choose proper option");
        String menu = "List of options:\n" +
                "0 - menu description\n" +
                "1 - new cube, reset colors\n" +
                "2 - show cube\n" +
                "3 - scramble cube\n" +
                "4 - self move cube\n" +
                "5 - solve cube\n" +
                "9 - exit program";
        System.out.println(menu);
        while (true) {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Type your action: ");
            String input = scanner.next();

            if (input.equals("0")){
                System.out.println(menu);
            }
            else if (input.equals("1")){
                cube = new Cube_2x2();
                System.out.println("Cube reset");
            }
            else if (input.equals("2")){
                System.out.println("Showing cube...");
                cube.paint2D();
            }
            else if (input.equals("3")){

            }
            else if (input.equals("4")){

            }
            else if (input.equals("5")){

            }
            else if (input.equals("9")){
                System.out.print("Goodbye world!");
                break;
            }
            else{
                System.err.println("Incorrect input, try again");
            }
        }
    }

}
