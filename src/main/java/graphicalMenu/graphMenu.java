package graphicalMenu;

import cubes.Algorithm;
import cubes.Cube2x2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class graphMenu extends JComponent implements ActionListener {

    Cube2x2 cube;
    JButton b1, b2, b3, b4, b5;
    JTextField tf;
    JLabel jl1;
    JTextArea jta1;
    JFrame window;
    ArrayList<JPanel> jp = new ArrayList<>();

    public graphMenu(Cube2x2 cube) {
        this.cube = cube;
        window = new JFrame();
        window.setSize(1400, 650);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        paintCube(cube);

        b1 = new JButton("Reset cube");
        b1.setBounds(810, 50, 110, 40);
        window.add(b1);
        b1.addActionListener(this);

        b2 = new JButton("Scramble");
        b2.setBounds(810, 150, 110, 40);
        window.add(b2);
        b2.addActionListener(this);

        jl1 = new JLabel("Scramble");
        jl1.setBounds(950, 150, 350, 40);
        window.add(jl1);

        b3 = new JButton("Move");
        b3.setBounds(810, 250, 110, 40);
        window.add(b3);
        b3.addActionListener(this);

        tf = new JTextField();
        tf.setBounds(950, 250, 200, 40);
        window.add(tf);
        tf.addActionListener(this);

        b4 = new JButton("Solve cube");
        b4.setBounds(810, 350, 110, 40);
        window.add(b4);
        b4.addActionListener(this);

        jta1 = new JTextArea(2,20);
        jta1.setBounds(950, 350, 250, 50);
        jta1.setLineWrap(true);
        window.add(jta1);

        b5 = new JButton("Exit");
        b5.setBounds(810, 550, 110, 40);
        window.add(b5);
        b5.addActionListener(this);

        window.setLayout(null);
        window.setVisible(true);
    }

    private void paintCube(Cube2x2 cube) {
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
            cube = new Cube2x2();
            repaintCube();
        }
        else if (e.getSource() == b2) {
            String sc = Algorithm.randomScramble(15,20);
            boolean b = cube.moveCube(sc);
            if (!b){
                JOptionPane.showMessageDialog(null, "Not proper alg", "Warning: ", JOptionPane.INFORMATION_MESSAGE);
            }
            jl1.setText(sc);
            repaintCube();
        }
        else if (e.getSource() == b3) {
            String sc = tf.getText();
            boolean b = cube.moveCube(sc);
            if (!b){
                JOptionPane.showMessageDialog(null, "Not proper alg", "Warning: ", JOptionPane.INFORMATION_MESSAGE);

            }
            repaintCube();
        }
        else if (e.getSource() == b4) {
            String solveAlg = cube.solve();
            repaintCube();
            jta1.setText(solveAlg);
        }
        else if (e.getSource() == b5) {
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

