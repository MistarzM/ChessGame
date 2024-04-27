package main;

import pieces.Knight;
import pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Board extends JPanel {

    public static int tileSize = 80;
    public static Color color1 = new Color(255,255,255);
    public static Color color2 = new Color(0,0,0);
    int cols = 8;
    int rows = 8;

    ArrayList<Piece> piecesList = new ArrayList<>();

    public Board(){

        this.setPreferredSize(new Dimension(cols * tileSize, rows * tileSize));
        addPieces();
    }

    public void addPieces(){
        piecesList.add(new Knight(this, false, 0, 1));
    }


    public void paintComponent(Graphics g){
        Graphics2D graphics = (Graphics2D) g;

        for(int row = 0; row < rows; row++){
            for(int col = 0; col < cols; col++){
                graphics.setColor((col + row) % 2 == 0 ? color1 : color2);
                graphics.fillRect(col * tileSize, row * tileSize, tileSize, tileSize);
            }
        }

        for(Piece p : piecesList){
            p.paint(graphics);
        }

    }
}
