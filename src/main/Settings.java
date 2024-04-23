package main;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Paths;

public class Settings extends JFrame {

    private Menu menu;

    public Settings(Menu menu) {
        this.menu = menu;
        this.setLayout(new FlowLayout());

        JTextField widthField = new JTextField(5);
        JTextField heightField = new JTextField(5);
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e->{
            int width = Integer.parseInt(widthField.getText());
            int height = Integer.parseInt(heightField.getText());
            menu.setMinimumSize(new Dimension(width, height));
            this.dispose();
        });

        this.add(new JLabel("Width:"));
        this.add(widthField);
        this.add(new JLabel("Height:"));
        this.add(heightField);
        this.add(saveButton);

        this.pack();
        this.setLocationRelativeTo(null);
    }


}
