package graphicalMenu;

import cubes.Cube_2x2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class graphMenu extends JComponent implements ActionListener {

    Cube_2x2 cube;
    JButton b1,b2,b3,b4,b5;
    JTextField tf;
    JLabel jl;
    JFrame window;
    JPanel jp;
    public graphMenu(Cube_2x2 cube) {
        this.cube=cube;
        window = new JFrame();
        window.setSize(1350, 650);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        jp = new JPanel();
        for (int j = 0; j < 8; j++) {
            for (int i = 0; i < 6; i++) {
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

        b1=new JButton("Reset cube");
        b1.setBounds(850,100,140, 40);
        window.add(b1);
        b1.addActionListener(this);

        b2=new JButton("Scramble");
        b2.setBounds(850,200,140, 40);
        window.add(b2);
        b2.addActionListener(this);

        jl = new JLabel("Scramble");
        jl.setBounds(1000,200,250, 40);
        window.add(jl);

        b3=new JButton("Move");
        b3.setBounds(850,300,140, 40);
        window.add(b3);
        b3.addActionListener(this);

        tf = new JTextField();
        tf.setBounds(1000, 300, 200, 40);
        window.add(tf);
        tf.addActionListener(this);

        b4=new JButton("Solve cube");
        b4.setBounds(850,400,140, 40);
        window.add(b4);
        b4.addActionListener(this);

        b5=new JButton("Exit");
        b5.setBounds(850,500,140, 40);
        window.add(b5);
        b5.addActionListener(this);

        window.setLayout(null);
        window.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==b1){
            cube = new Cube_2x2();
        }else if(e.getSource()==b2){
            String sc = Cube_2x2.randomScramble();
            cube.moveCube(sc);
            jl.setText(sc);

            jp.repaint();

        }else if(e.getSource()==b3){
            String sc = tf.getText();
            cube.moveCube(sc);
        }else if(e.getSource()==b4){
            cube.solve();
        }else if(e.getSource()==b5){
            System.exit(0);
        }
    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
        jp = new JPanel();
        for (int j = 0; j < 8; j++) {
            for (int i = 0; i < 6; i++) {
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
    }
}

