package graphicalMenu;

import cubes.Algorithm;
import cubes.Cube2x2;
import cubes.OrtegaSolveMethod;
import cubes.QuickestSolve;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;

public class GraphMenu extends JComponent implements ActionListener {

    Cube2x2 cube;
    JButton b1, b2, b3, b4, b5, b6, b0;
    JTextArea jta2, jta3, jta4, jta5, jta6;
    JFrame window;
    JCheckBox cb61, cb62;
    JSpinner spinner;
    JLabel jl1;
    ArrayList<JPanel> jp = new ArrayList<>();
    private volatile boolean flag = true;

    public GraphMenu(Cube2x2 cube) {
        this.cube = cube;
        window = new JFrame();
        window.setSize(1250, 635);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        paintCube(cube);

        b1 = new JButton("Reset cube");
        b1.setBounds(810, 30, 110, 40);
        window.add(b1);
        b1.addActionListener(this);

        spinner = new JSpinner(new SpinnerNumberModel(0.1, 0, 5, 0.1));
        spinner.setBounds(995, 30, 50, 40);
        window.add(spinner);

        jl1 = new JLabel("Duration of moves");
        jl1.setBounds(1050, 30, 110, 40);
        window.add(jl1);

        b2 = new JButton("Scramble");
        b2.setBounds(810, 100, 110, 40);
        window.add(b2);
        b2.addActionListener(this);

        jta2 = new JTextArea();
        jta2.setBounds(950, 100, 250, 40);
        jta2.setLineWrap(true);
        window.add(jta2);

        b3 = new JButton("Move");
        b3.setBounds(810, 170, 110, 40);
        window.add(b3);
        b3.addActionListener(this);

        jta3 = new JTextArea();
        jta3.setBounds(950, 170, 250, 40);
        jta3.setLineWrap(true);
        window.add(jta3);

        b4 = new JButton("Solve cube");
        b4.setBounds(810, 240, 110, 40);
        window.add(b4);
        b4.addActionListener(this);

        jta4 = new JTextArea(2,20);
        jta4.setBounds(950, 240, 250, 50);
        jta4.setLineWrap(true);
        window.add(jta4);

        b5 = new JButton("FWM");
        b5.setBounds(810, 310, 110, 40);
        window.add(b5);
        b5.addActionListener(this);

        jta5 = new JTextArea(2,20);
        jta5.setBounds(950, 310, 250, 40);
        jta5.setLineWrap(true);
        window.add(jta5);

        b6 = new JButton("Shorten alg");
        b6.setBounds(810, 380, 110, 40);
        window.add(b6);
        b6.addActionListener(this);

        jta6 = new JTextArea();
        jta6.setBounds(950, 380, 250, 40);
        jta6.setLineWrap(true);
        window.add(jta6);

        cb61 = new JCheckBox("Optimize");
        cb61.setBounds(950, 420, 80, 20);
        window.add(cb61);
        cb61.addActionListener(this);

        cb62 = new JCheckBox("Rotations");
        cb62.setBounds(1040, 420, 80, 20);
        window.add(cb62);
        cb62.addActionListener(this);

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
            flag=false;
            spinner.setValue(0.1);
            cube = new Cube2x2();
            repaintCube();
            jta2.setText(null);
            jta3.setText(null);
            jta4.setText(null);
            jta5.setText(null);
            jta6.setText(null);
        }
        else if (e.getSource() == b2) {
            String sc = Algorithm.randomScramble(15,20);
            if (!sc.equals("") && Algorithm.checkIsAlgProper(sc)) {
                animation(sc);
            }else {
                JOptionPane.showMessageDialog(null, "Not proper alg", "Warning: ", JOptionPane.INFORMATION_MESSAGE);
            }
            jta2.setText(sc);
        }
        else if (e.getSource() == b3) {
            String sc = jta3.getText();
            if (!sc.equals("") && Algorithm.checkIsAlgProper(sc)) {
                animation(sc);
            }else {
                JOptionPane.showMessageDialog(null, "Not proper alg", "Warning: ", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        else if (e.getSource() == b4) {
            Cube2x2 tempCube = new Cube2x2(this.cube);

            OrtegaSolveMethod method = new OrtegaSolveMethod(tempCube);
            String solveAlg = method.solve();

            animation(solveAlg);

            jta4.setText(solveAlg);
        }
        else if (e.getSource() == b5){
            Cube2x2 tempCube = new Cube2x2(this.cube);
            QuickestSolve fwm = new QuickestSolve(tempCube);

            String solveAlg = fwm.findQuickestSolve();

            animation(solveAlg);
            jta5.setText(solveAlg);
        }
        else if (e.getSource() == b6){
            String alg = jta6.getText();
            if (!Algorithm.checkIsAlgProper(alg)){
                JOptionPane.showMessageDialog(null, "Not proper alg", "Warning: ", JOptionPane.INFORMATION_MESSAGE);
            }
            else if(!cb61.isSelected() && !cb62.isSelected()){
                JOptionPane.showMessageDialog(null, "Chose one of options", "Warning: ", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                if (cb61.isSelected()){
                    alg = Algorithm.optimizeAlg(alg);
                }
                if (cb62.isSelected()) {
                    alg = Algorithm.skipRotation(alg);
                }
                jta6.setText(alg);
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

    private void animation(String alg) {
        int T = 100;
        try {
            spinner.commitEdit();
            //to milliseconds
            T = (int)((double) spinner.getValue() *1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        final int finalT = T;
        final LinkedList<String> algList = Algorithm.algToList(alg);
        flag = true;

        Thread t = new Thread(() -> {
            for (String move : algList) {
                if (!flag){
                    Thread.currentThread().interrupt();
                    break;
                }
                cube.moveCube(move);
                this.repaintCube();
                try {
                    Thread.sleep(finalT);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }
}