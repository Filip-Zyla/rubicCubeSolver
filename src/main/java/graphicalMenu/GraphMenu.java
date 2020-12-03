package graphicalMenu;

import cubes.Cube_2x2;

import javax.swing.*;
import java.awt.*;

public class GraphMenu extends JComponent {


    private JFrame window = new JFrame();
    private Cube_2x2 gCube;

    public GraphMenu(Cube_2x2 cube){
        gCube=cube;
        paintComponent(new Graphics(), cube);
    }

    public void gMenu(Cube_2x2 cube) {
        JFrame window = new JFrame();
        window.setSize(850, 650);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.getContentPane().add();
        window.setVisible(true);
    }

    public void paintComponent(Graphics g, Cube_2x2 cube) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for (int j = 0; j < 8; j++) {
            for (int i = 0; i < 6; i++) {
                g2d.drawRect(j * 100, i * 100, 100, 100);
                switch (cube.getCube()[i][j]) {
                    case 0 -> g2d.setColor(Color.WHITE);
                    case 1 -> g2d.setColor(Color.RED);
                    case 2 -> g2d.setColor(Color.BLUE);
                    case 3 -> g2d.setColor(Color.GREEN);
                    case 4 -> g2d.setColor(Color.getHSBColor(0.10f, 1f, 1f));
                    case 5 -> g2d.setColor(Color.YELLOW);
                    default -> g2d.setColor(Color.BLACK);
                }
                g2d.fillRect(j * 100, i * 100, 100, 100);
            }
        }
    }
}