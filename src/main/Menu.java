package main;

import javax.swing.*;
import java.awt.*;

public class Menu extends JFrame {

    private JButton startButton;
    private JButton settingsButton;

    public Menu(){
        this.getContentPane().setBackground(Color.black);
        this.setLayout(new GridBagLayout());
        this.setSize(new Dimension(800, 800));
        this.setLocationRelativeTo(null);

        startButton = new JButton("start");
        startButton.addActionListener(e -> {
            this.getContentPane().removeAll();
            Board board = new Board();
            this.add(board);
            this.revalidate();
            this.repaint();
        });

        settingsButton = new JButton("Settings");
        settingsButton.addActionListener(e->{
            this.getContentPane().removeAll();
            Settings settings = new Settings(this);
            this.add(settings);
            this.revalidate();
            this.repaint();
        });

        showMenu();
    }

    public void showMenu() {
        this.getContentPane().removeAll();
        this.add(startButton);
        this.add(settingsButton);
        this.revalidate();
        this.repaint();
    }
}