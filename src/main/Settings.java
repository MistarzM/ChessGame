package main;

import design.CustomButton;

import javax.swing.*;
import java.awt.*;
import java.util.jar.JarEntry;

public class Settings extends JPanel {

    private Menu menu;
    private Dimension initialSize;
    private int initialTileSize;
    private Color initialColor1;
    private Color initialColor2;
    private CustomButton saveButton;
    private CustomButton backButton;

    public Settings(Menu menu) {
        this.menu = menu;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


        this.initialSize = menu.getSize();
        this.initialTileSize = Board.tileSize;
        this.initialColor1 = Board.color1;
        this.initialColor2 = Board.color2;

        this.setBackground(new Color(210, 180, 140));

        JRadioButton smallButton = new JRadioButton("Small");
        JRadioButton mediumButton = new JRadioButton("Medium");
        JRadioButton bigButton = new JRadioButton("Big");

        smallButton.addActionListener(e-> {
            menu.setSize(new Dimension(500, 500));
            Board.tileSize = 50;
        });

        mediumButton.addActionListener(e-> {
            menu.setSize(new Dimension(800, 800));
            Board.tileSize = 80;
        });

        bigButton.addActionListener(e-> {
            menu.setSize(new Dimension(1000, 1000));
            Board.tileSize = 100;
        });

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(smallButton);
        buttonGroup.add(mediumButton);
        buttonGroup.add(bigButton);

        mediumButton.setSelected(true);

        JButton colorButton1 = new JButton();
        colorButton1.setBackground(initialColor1);
        colorButton1.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(null, "Choose a color", initialColor1);
            if (newColor != null) {
                Board.color1 = newColor;
                colorButton1.setBackground(newColor);
            }
        });

        JButton colorButton2 = new JButton();
        colorButton2.setBackground(initialColor2);
        colorButton2.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(null, "Choose a color", initialColor2);
            if (newColor != null) {
                Board.color2 = newColor;
                colorButton2.setBackground(newColor);
            }
        });

        saveButton = new CustomButton("Save");
        saveButton.setPreferredSize(new Dimension(100, 30));
        saveButton.setFont(new Font("Arial", Font.BOLD, 20));
        saveButton.addActionListener(e->{
            menu.showMenu();
        });
        backButton = new CustomButton("Back");
        backButton.setPreferredSize(new Dimension(100, 30));
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.addActionListener(e ->{
            menu.setSize(initialSize);
            Board.tileSize = initialTileSize;
            Board.color1 = initialColor1;
            Board.color2 = initialColor2;
            menu.showMenu();
        });
        this.add(new JLabel("Size:"));
        this.add(smallButton);
        this.add(mediumButton);
        this.add(bigButton);
        this.add(new JLabel("Color 1:"));
        this.add(colorButton1);
        this.add(new JLabel("Color 2:"));
        this.add(colorButton2);
        this.add(saveButton);
        this.add(Box.createVerticalStrut(10));
        this.add(backButton);
    }
}