package main;

import javax.swing.*;
import java.awt.*;

public class Settings extends JPanel {

    private Menu menu;

    public Settings(Menu menu) {
        this.menu = menu;
        this.setLayout(new FlowLayout());

        // Set the background color to black
        this.setBackground(Color.black);

        JTextField widthField = new JTextField(5);
        JTextField heightField = new JTextField(5);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e->{
            int width = Integer.parseInt(widthField.getText());
            int height = Integer.parseInt(heightField.getText());
            menu.setMinimumSize(new Dimension(width, height));
            menu.showMenu();
        });
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e ->{
            menu.showMenu();
        });
        this.add(new JLabel("Width:"));
        this.add(widthField);
        this.add(new JLabel("Height:"));
        this.add(heightField);
        this.add(saveButton);
        this.add(backButton);
    }
}