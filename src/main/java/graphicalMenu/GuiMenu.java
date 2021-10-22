package graphicalMenu;

import cubes.*;
import files.HistoryFile;
import solving2x2.OrtegaMethod;
import solving2x2.FwmExecutor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ExecutionException;

public class GuiMenu extends JComponent implements ActionListener {

    private Cube2x2 cube;
    private JButton b1, b2, b3, b4, b5, b6, b7, b0;
    private JTextArea jta2, jta3, jta4, jta5, jta6, historyArea;
    private JFrame window, history;
    private JCheckBox cb6;
    private JSpinner spinner;
    private JLabel jl1;
    private ArrayList<JPanel> jp = new ArrayList<>();

    private volatile boolean animationFlag = true;
    private Thread fwmThread;

    public GuiMenu(Cube2x2 cube) {
        this.cube = cube;
        window = new JFrame();
        window.setSize(1250, 635);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        paintCube(cube);

        b1 = new JButton("Reset cube");
        b1.setBounds(810, 30, 110, 40);
        window.add(b1);
        b1.addActionListener(this);

        spinner = new JSpinner(new SpinnerNumberModel(0.1, 0, 5, 0.1));
        spinner.setBounds(950, 30, 50, 40);
        window.add(spinner);

        jl1 = new JLabel("Duration of moves");
        jl1.setBounds(1005, 30, 110, 40);
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
        jta4.setBounds(950, 240, 250, 40);
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

        cb6 = new JCheckBox("Delete rotations");
        cb6.setBounds(950, 420, 120, 20);
        window.add(cb6);
        cb6.addActionListener(this);

        b7 = new JButton("History");
        b7.setBounds(810, 450, 110, 40);
        window.add(b7);
        b7.addActionListener(this);

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

    private void repaintCube() {
        paintCube(cube);
        for (JPanel jPanel : jp) {
            jPanel.repaint();
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (fwmThread!= null && fwmThread.isAlive() && e.getSource()!=b0){
            return;
        }
        if (e.getSource() == b1) {
            if (fwmThread!= null && fwmThread.isAlive()){
                return;
            }
            animationFlag =false;
            spinner.setValue(0.1);
            cube = new Cube2x2();
            repaintCube();
            jta2.setText(null);
            jta3.setText(null);
            jta4.setText(null);
            jta5.setText(null);
            jta6.setText(null);
            HistoryFile.saveToHistory("Reset program");
        }
        else if (e.getSource() == b2) {
            String sc = Algorithm.randomScramble(15,20);
            animation(sc);
            jta2.setText(sc);
            HistoryFile.saveToHistory("Scramble: "+sc);
        }
        else if (e.getSource() == b3) {
            String sc = jta3.getText();
            if (Algorithm.checkIfProper(sc)) {
                animation(sc);
                HistoryFile.saveToHistory("Move: "+sc);
            }else {
                JOptionPane.showMessageDialog(null, "Not proper alg", "Warning: ", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        else if (e.getSource() == b4) {
            OrtegaMethod method = new OrtegaMethod(cube);
            String solveAlg = method.solve();
            animation(solveAlg);
            HistoryFile.saveToHistory("Ortega solve: "+solveAlg);
            jta4.setText(solveAlg);
        }
        else if (e.getSource() == b5){
            jta5.setText("Computing...");
            fwmThread = new Thread(() -> {
                FwmExecutor threads = new FwmExecutor(cube, 4);
                String solveAlg;
                try {
                    solveAlg = threads.fewestMoves();
                    animation(solveAlg);
                    HistoryFile.saveToHistory("FWM: "+solveAlg);
                    jta5.setText(solveAlg);
                } catch (ExecutionException | InterruptedException ex) {
                    ex.printStackTrace();
                    jta5.setText("Error");
                }

            });
            fwmThread.start();
        }
        else if (e.getSource() == b6){
            String alg = jta6.getText();
            if (!Algorithm.checkIfProper(alg)){
                JOptionPane.showMessageDialog(null, "Not proper alg", "Warning: ", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                if (cb6.isSelected()) {
                    alg = Algorithm.skipRotation(alg);
                }
                else {
                    alg = Algorithm.optimizeAlg(alg);
                }
                HistoryFile.saveToHistory("Optimize: "+alg);
                jta6.setText(alg);
            }
        }
        else if (e.getSource() == b7){
            history = new JFrame();
            history.setSize(400, 500);
            history.setLocation(500,100);
            history.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            history.setVisible(true);
            historyArea = new JTextArea();
            historyArea.setLineWrap(true);
            historyArea.setVisible(true);
            history.add(historyArea);
            historyArea.setText(HistoryFile.printHistory());
        }
        else if (e.getSource() == b0) {
            System.exit(0);
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

        if (T>0){
            final int finalT = T;
            final LinkedList<String> algList = Algorithm.toList(alg);
            animationFlag = true;
            new Thread(() -> {
                for (String move : algList) {
                    if (!animationFlag){
                        Thread.currentThread().interrupt();
                        break;
                    }
                    cube.move(move);
                    this.repaintCube();
                    try {
                        Thread.sleep(finalT);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        else {
            cube.move(alg);
            repaintCube();
        }
    }
}