package graphicalMenu;

import cubes.Algorithm;
import cubes.Cube2x2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GraphMenu extends JComponent implements ActionListener {

    Cube2x2 cube;
    JButton b1, b2, b3, b4, b5, b0;
    JTextArea jta1, jta2, jta3, jta4;
    JFrame window;
    ArrayList<JPanel> jp = new ArrayList<>();

    public GraphMenu(Cube2x2 cube) {
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

        jta1 = new JTextArea();
        jta1.setBounds(950, 150, 250, 40);
        jta1.setLineWrap(true);
        window.add(jta1);

        b3 = new JButton("Move");
        b3.setBounds(810, 250, 110, 40);
        window.add(b3);
        b3.addActionListener(this);

        jta2 = new JTextArea();
        jta2.setBounds(950, 250, 250, 40);
        jta2.setLineWrap(true);
        window.add(jta2);

        b4 = new JButton("Solve cube");
        b4.setBounds(810, 350, 110, 40);
        window.add(b4);
        b4.addActionListener(this);

        jta3 = new JTextArea(2,20);
        jta3.setBounds(950, 350, 250, 50);
        jta3.setLineWrap(true);
        window.add(jta3);

        b5 = new JButton("Delete rotations");
        b5.setBounds(810, 450, 110, 40);
        window.add(b5);
        b5.addActionListener(this);

        jta4 = new JTextArea();
        jta4.setBounds(950, 450, 250, 40);
        jta4.setLineWrap(true);
        window.add(jta4);

        b0 = new JButton("Exit");
        b0.setBounds(810, 550, 110, 40);
        window.add(b0);
        b0.addActionListener(this);

        window.setLayout(null);
        window.setVisible(true);
    }

    private void paintCube(Cube2x2 cube) {
        jp = new ArrayList<>();
        for (int j = 0; j < 8; j++) {
            for (int i = 0; i < 6; i++) {
                JPanel panel = new JPanel();
                panel.setBounds(j * 100, i * 100, 100, 100);
                if (cube.getCube()[i][j] == 0) {
                    panel.setBackground(Color.WHITE);
                } else if (cube.getCube()[i][j] == 1) {
                    panel.setBackground(Color.RED);
                } else if (cube.getCube()[i][j] == 2) {
                    panel.setBackground(Color.BLUE);
                } else if (cube.getCube()[i][j] == 3) {
                    panel.setBackground(Color.GREEN);
                } else if (cube.getCube()[i][j] == 4) {
                    panel.setBackground(Color.getHSBColor(0.10f, 1f, 1f)); // orange
                } else if (cube.getCube()[i][j] == 5) {
                    panel.setBackground(Color.YELLOW);
                } else {
                    panel.setBackground(Color.BLACK);
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
            jta1.setText(null);
            jta2.setText(null);
            jta3.setText(null);
            jta4.setText(null);
        }
        else if (e.getSource() == b2) {
            String sc = Algorithm.randomScramble(15,20);
            System.out.println(sc);
            boolean b = cube.moveCube(sc);
            if (!b){
                JOptionPane.showMessageDialog(null, "Not proper alg", "Warning: ", JOptionPane.INFORMATION_MESSAGE);
            }
            jta1.setText(sc);
            repaintCube();
        }
        else if (e.getSource() == b3) {
            String sc = jta2.getText();
            if (!sc.equals("")) {
                boolean b = cube.moveCube(sc);
                if (!b) {
                    JOptionPane.showMessageDialog(null, "Not proper alg", "Warning: ", JOptionPane.INFORMATION_MESSAGE);

                }
                repaintCube();
            }
        }
        else if (e.getSource() == b4) {
            String solveAlg = cube.solve();
            repaintCube();
            jta3.setText(solveAlg);
        }
        else if (e.getSource() == b5){
            //TODO throwing some IndexOut and when not proper alg etc.
            String alg = jta4.getText();
            if (!alg.equals("")) {
                alg = Algorithm.skipRotation(alg);
                jta4.setText(alg);
            }
        }
        else if (e.getSource() == b0) {
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

