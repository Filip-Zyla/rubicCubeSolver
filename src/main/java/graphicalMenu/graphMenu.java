package graphicalMenu;

import cubes.Cube_2x2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class graphMenu extends JComponent implements ActionListener {

    Cube_2x2 cube;
    JButton b1, b2, b3, b4, b5;
    JTextField tf;
    JLabel jl;
    JFrame window;
    ArrayList<JPanel> jp = new ArrayList<>();

    public graphMenu(Cube_2x2 cube) {
        this.cube = cube;
        window = new JFrame();
        window.setSize(1400, 650);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        paintCube(cube);

        b1 = new JButton("Reset cube");
        b1.setBounds(810, 100, 110, 40);
        window.add(b1);
        b1.addActionListener(this);

        b2 = new JButton("Scramble");
        b2.setBounds(810, 200, 110, 40);
        window.add(b2);
        b2.addActionListener(this);

        jl = new JLabel("Scramble");
        jl.setBounds(950, 200, 350, 40);
        window.add(jl);

        b3 = new JButton("Move");
        b3.setBounds(810, 300, 110, 40);
        window.add(b3);
        b3.addActionListener(this);

        tf = new JTextField();
        tf.setBounds(950, 300, 200, 40);
        window.add(tf);
        tf.addActionListener(this);

        b4 = new JButton("Solve cube");
        b4.setBounds(810, 400, 110, 40);
        window.add(b4);
        b4.addActionListener(this);

        b5 = new JButton("Exit");
        b5.setBounds(810, 500, 110, 40);
        window.add(b5);
        b5.addActionListener(this);

        window.setLayout(null);
        window.setVisible(true);
    }

    private void paintCube(Cube_2x2 cube) {
        jp = new ArrayList<>();
        for (int j = 0; j < 8; j++) {
            for (int i = 0; i < 6; i++) {
                JPanel panel = new JPanel();
                panel.setBounds(j * 100, i * 100, 100, 100);
                switch (cube.getCube()[i][j]) {
                    case 0 -> panel.setBackground(Color.WHITE);
                    case 1 -> panel.setBackground(Color.RED);
                    case 2 -> panel.setBackground(Color.BLUE);
                    case 3 -> panel.setBackground(Color.GREEN);
                    case 4 -> panel.setBackground(Color.getHSBColor(0.10f, 1f, 1f));
                    case 5 -> panel.setBackground(Color.YELLOW);
                    default -> panel.setBackground(Color.BLACK);
                }
                jp.add(panel);
                window.add(panel);
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            cube = new Cube_2x2();
            repaintCube();
        } else if (e.getSource() == b2) {
            String sc = Cube_2x2.randomScramble();
            System.out.println(sc);
            cube.moveCube(sc);
            jl.setText(sc);
            repaintCube();
        } else if (e.getSource() == b3) {
            String sc = tf.getText();
            cube.moveCube(sc);
            repaintCube();
        } else if (e.getSource() == b4) {
            String solveAlg = cube.solve();
            System.out.println(solveAlg);
            repaintCube();
        } else if (e.getSource() == b5) {
            System.exit(0);
        }
    }

    private void repaintCube() {
        paintCube(cube);
        for (JPanel jPanel : jp) {
            jPanel.repaint();
        }
    }

}

