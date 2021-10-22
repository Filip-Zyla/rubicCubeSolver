
import cubes.*;
import files.HistoryFile;
import graphicalMenu.GuiMenu;

//TODO another cubes NxN, pyraminx, square-1, using abstract classes
//TODO tests
public class Main {
    public static void main(String[] args) {
        HistoryFile.createFile();
        new GuiMenu(new Cube2x2());
    }
}