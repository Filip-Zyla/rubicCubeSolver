
import cubes.*;
import files.HistoryFile;
import graphicalMenu.GuiMenu;

public class Main {
    public static void main(String[] args) {
        HistoryFile.createFile();
        new GuiMenu(new Cube2x2());
    }

    //TODO another cubes NxN, pyraminx, square-1, using abstract classes
    //TODO tests
    //TODO nThreads && moves_poss
    //TODO getMoveWitheBestEntropy reutrn move at first  curE > entropy && entropy points
}