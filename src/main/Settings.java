package main;

import javax.swing.*;
import java.awt.*;
import java.util.jar.JarEntry;

public class Settings extends JPanel {

    private Menu menu;
    private Dimension initialSize;
    private int initialTileSize;

    public Settings(Menu menu) {
        this.menu = menu;
        this.setLayout(new FlowLayout());


        this.initialSize = menu.getSize();
        this.initialTileSize = Board.tileSize;

        this.setBackground(Color.black);

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

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e->{
            menu.showMenu();
        });
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e ->{
            menu.setSize(initialSize);
            Board.tileSize = initialTileSize;
            menu.showMenu();
        });
        this.add(new JLabel("Size:"));
        this.add(smallButton);
        this.add(mediumButton);
        this.add(bigButton);
        this.add(saveButton);
        this.add(backButton);
    }
}