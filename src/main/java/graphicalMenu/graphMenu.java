package graphicalMenu;

import cubes.Cube_2x2;

import javax.swing.*;
import java.awt.*;

public class graphMenu extends JComponent {

    Cube_2x2 cube;
    public graphMenu(Cube_2x2 cube) {
        this.cube=cube;
        JFrame window = new JFrame();
        window.setSize(1500, 1000);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        for (int j = 0; j < 8; j++) {
            for (int i = 0; i < 6; i++) {
                JPanel jp = new JPanel();
                jp.setBounds(j * 100, i * 100, 100, 100);
                switch (cube.getCube()[i][j]) {
                    case 0 -> jp.setBackground(Color.WHITE);
                    case 1 -> jp.setBackground(Color.RED);
                    case 2 -> jp.setBackground(Color.BLUE);
                    case 3 -> jp.setBackground(Color.GREEN);
                    case 4 -> jp.setBackground(Color.getHSBColor(0.10f, 1f, 1f));
                    case 5 -> jp.setBackground(Color.YELLOW);
                    default -> jp.setBackground(Color.BLACK);
                }
                window.add(jp);
            }
        }

        JButton b1=new JButton("Reset cube");
        b1.setBounds(850,100,140, 40);
        window.add(b1);

        JButton b2=new JButton("Scramble");
        b2.setBounds(850,200,140, 40);
        window.add(b2);

        JButton b3=new JButton("Move");
        b3.setBounds(850,300,140, 40);
        window.add(b3);

        JTextField tf = new JTextField();
        tf.setBounds(1000, 300, 200, 40);
        window.add(tf);

        JButton b4=new JButton("Solve cube");
        b4.setBounds(850,400,140, 40);
        window.add(b4);

        JButton b5=new JButton("Exit");
        b5.setBounds(850,500,140, 40);
        window.add(b5);

        window.setLayout(null);
        window.setVisible(true);
    }
}

