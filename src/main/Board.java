package main;

import javax.swing.*;
import java.awt.*;

public class Board extends JPanel {

    public int tileSize = 85;

    int cols = 8;
    int rows = 8;

    public Board(){
        this.setPreferredSize(new Dimension(cols * tileSize, rows * tileSize));
    }

    public void paintComponent(Graphics g){
        Graphics2D graphics = (Graphics2D) g;

        for(int row = 0; row < rows; row++){
            for(int col = 0; col < cols; col++){
                graphics.setColor((col + row) % 2 == 0 ? new Color(243, 233, 158) : new Color(150, 116, 30));
                graphics.fillRect(col * tileSize, row * tileSize, tileSize, tileSize);
            }
        }
    }
}
