import cubes.Cube_2x2;
import graphicalMenu.graphMenu;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        //consoleMenu(new Cube_2x2());
        new graphMenu(new Cube_2x2());
    }

    public static void consoleMenu(Cube_2x2 cube){
        System.out.println("!!!!!!!!!Welcome!!!!!!!!!!!");
        System.out.println("It's your menu");
        System.out.println("Choose proper option");
        String menu =  "0 - menu description\n" +
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

            switch (input) {
                case "0" -> System.out.println(menu);
                case "1" -> {
                    cube = new Cube_2x2();
                    System.out.println("Cube reset");
                }
                case "2" -> {
                    System.out.println("Showing cube...");
                    cube.showCube();
                }
                case "3" -> {
                    System.out.println("Scramble");
                    String scm = Cube_2x2.randomScramble();
                    cube.moveCube(scm);
                    System.out.println(scm);
                    cube.showCube();
                }
                case "4" -> {
                    System.out.print("Type scramble: ");
                    String scramble = scanner.next();
                    cube.moveCube(scramble);
                    cube.showCube();
                }
                case "5" -> {
                    System.out.println("Solve cube");
                    cube.solve();
                    cube.showCube();
                }
                case "9" -> System.out.print("Goodbye world!");
                default -> System.err.println("Incorrect input, try again");
            }
        }
    }

}
