package graphicalMenu;

import cubes.Algorithm;
import cubes.Cube2x2;
import cubes.OrtegaSolveMethod;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

//TODO cube moving move by move with delay, animation, perhaps pixel by pixel?, thread fo pixels?
public class GraphMenu extends JComponent implements ActionListener {

    Cube2x2 cube;
    JButton b1, b2, b3, b4, b5, b0;
    JTextArea jta2, jta3, jta4, jta5;
    JFrame window;
    JCheckBox cb51, cb52;
    ArrayList<JPanel> jp = new ArrayList<>();

    public GraphMenu(Cube2x2 cube) {
        this.cube = cube;
        window = new JFrame();
        window.setSize(1400, 650);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        paintCube(cube);

        b1 = new JButton("Reset cube");
        b1.setBounds(810, 40, 110, 40);
        window.add(b1);
        b1.addActionListener(this);

        b2 = new JButton("Scramble");
        b2.setBounds(810, 120, 110, 40);
        window.add(b2);
        b2.addActionListener(this);

        jta2 = new JTextArea();
        jta2.setBounds(950, 120, 250, 40);
        jta2.setLineWrap(true);
        window.add(jta2);

        b3 = new JButton("Move");
        b3.setBounds(810, 200, 110, 40);
        window.add(b3);
        b3.addActionListener(this);

        jta3 = new JTextArea();
        jta3.setBounds(950, 200, 250, 40);
        jta3.setLineWrap(true);
        window.add(jta3);

        b4 = new JButton("Solve cube");
        b4.setBounds(810, 280, 110, 40);
        window.add(b4);
        b4.addActionListener(this);

        jta4 = new JTextArea(2,20);
        jta4.setBounds(950, 280, 250, 50);
        jta4.setLineWrap(true);
        window.add(jta4);

        b5 = new JButton("Shorten");
        b5.setBounds(810, 360, 110, 40);
        window.add(b5);
        b5.addActionListener(this);

        jta5 = new JTextArea();
        jta5.setBounds(950, 360, 250, 40);
        jta5.setLineWrap(true);
        window.add(jta5);

        cb51 = new JCheckBox("Optimize");
        cb51.setBounds(950, 400, 80, 20);
        window.add(cb51);
        cb51.addActionListener(this);

        cb52 = new JCheckBox("Rotations");
        cb52.setBounds(1040, 400, 80, 20);
        window.add(cb52);
        cb52.addActionListener(this);

        b0 = new JButton("Exit");
        b0.setBounds(810, 520, 110, 40);
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
                if (cube.getArray()[i][j] == 0) {
                    panel.setBackground(Color.WHITE);
                } else if (cube.getArray()[i][j] == 1) {
                    panel.setBackground(Color.RED);
                } else if (cube.getArray()[i][j] == 2) {
                    panel.setBackground(Color.BLUE);
                } else if (cube.getArray()[i][j] == 3) {
                    panel.setBackground(Color.GREEN);
                } else if (cube.getArray()[i][j] == 4) {
                    panel.setBackground(Color.getHSBColor(0.10f, 1f, 1f)); // orange
                } else if (cube.getArray()[i][j] == 5) {
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
            jta2.setText(null);
            jta3.setText(null);
            jta4.setText(null);
            jta5.setText(null);
        }
        else if (e.getSource() == b2) {
            String sc = Algorithm.randomScramble(15,20);
            System.out.println(sc);
            boolean b = cube.moveCube(sc);
            if (!b){
                JOptionPane.showMessageDialog(null, "Not proper alg", "Warning: ", JOptionPane.INFORMATION_MESSAGE);
            }
            jta2.setText(sc);
            repaintCube();
        }
        else if (e.getSource() == b3) {
            String sc = jta3.getText();
            if (!sc.equals("")) {
                boolean b = cube.moveCube(sc);
                if (!b) {
                    JOptionPane.showMessageDialog(null, "Not proper alg", "Warning: ", JOptionPane.INFORMATION_MESSAGE);

                }
                repaintCube();
            }
        }
        else if (e.getSource() == b4) {
            OrtegaSolveMethod method = new OrtegaSolveMethod(cube);
            String solveAlg = method.solve();
            repaintCube();
            jta4.setText(solveAlg);
        }
        else if (e.getSource() == b5){
            String alg = jta5.getText();
            if (!Algorithm.checkIsAlgProper(alg)){
                JOptionPane.showMessageDialog(null, "Not proper alg", "Warning: ", JOptionPane.INFORMATION_MESSAGE);
            }
            else if(!cb51.isSelected() && !cb52.isSelected()){
                JOptionPane.showMessageDialog(null, "Chose one of options", "Warning: ", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                if (cb51.isSelected()){
                    alg = Algorithm.optimizeAlg(alg);
                }
                if (cb52.isSelected()) {
                    alg = Algorithm.skipRotation(alg);
                }
                jta5.setText(alg);
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