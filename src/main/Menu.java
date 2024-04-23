package main;

import javax.swing.*;
import java.awt.*;  // This import is needed to use: Color, Dimension, Graphics, GridBagLayout

public class Menu extends JFrame {

    public Menu(){
        this.getContentPane().setBackground(Color.black);
        this.setLayout(new GridBagLayout());
        this.setMinimumSize(new Dimension(800, 800));
        this.setLocationRelativeTo(null);

        JButton startButton = new JButton("start");
        startButton.addActionListener(e -> {
            this.getContentPane().removeAll();
            Board board = new Board();
            this.add(board);
            this.revalidate();
            this.repaint();
        });
        this.add(startButton);
    }

}
