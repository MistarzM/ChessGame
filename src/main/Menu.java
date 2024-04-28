package main;

import javax.swing.*;
import java.awt.*;

import design.CustomButton;

public class Menu extends JFrame {

    private CustomButton startButton;
    private CustomButton settingsButton;

    public Menu(){
        this.getContentPane().setBackground(new Color(210, 180, 140));
        this.setLayout(new GridBagLayout());
        this.setSize(new Dimension(800, 800));
        this.setLocationRelativeTo(null);

        startButton = new CustomButton("start");
        startButton.setPreferredSize(new Dimension(150, 50));
        startButton.setFont(new Font("Arial", Font.BOLD, 25));
        startButton.addActionListener(e -> {
            this.getContentPane().removeAll();
            Board board = new Board();
            this.add(board.getPanel());
            this.revalidate();
            this.repaint();
        });

        settingsButton = new CustomButton("Settings");
        settingsButton.setPreferredSize(new Dimension(150, 50));
        settingsButton.setFont(new Font("Arial", Font.BOLD, 25));
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
        gbc.insets = new Insets(10, 0, 10, 0);
        this.add(startButton, gbc);
        this.add(settingsButton, gbc);
        this.revalidate();
        this.repaint();
    }
}