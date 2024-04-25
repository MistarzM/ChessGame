package main;

import javax.swing.*;
import java.awt.*;

import design.CustomButton;

public class Menu extends JFrame {

    private CustomButton startButton;
    private CustomButton settingsButton;

    public Menu(){
        this.getContentPane().setBackground(Color.black);
        this.setLayout(new GridBagLayout());
        this.setSize(new Dimension(800, 800));
        this.setLocationRelativeTo(null);

        startButton = new CustomButton("start");
        startButton.addActionListener(e -> {
            this.getContentPane().removeAll();
            Board board = new Board();
            this.add(board);
            this.revalidate();
            this.repaint();
        });

        settingsButton = new CustomButton("Settings");
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
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(startButton, gbc);
        this.add(settingsButton, gbc);
        this.revalidate();
        this.repaint();
    }
}