
import cubes.*;
import files.HistoryFile;
import graphicalMenu.GuiMenu;

//TODO another cubes NxN, square-1
//TODO tests
public class Main {
    public static void main(String[] args) {
        HistoryFile.createFile();
        new GuiMenu(new Cube2x2());
    }
}