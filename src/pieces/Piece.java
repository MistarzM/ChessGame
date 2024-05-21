package pieces;

import main.Board;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Piece {

    public int col;
    public int row;

    public int xPosition;
    public int yPosition;

    public boolean colorOfTeam;         // true -> white : false -> black
    public String pieceName;
    public int value;

    Image sprite;

    Board board;

    public Piece(Board board) {
        this.board = board;
    }

    public void paint(Graphics2D graphics2D) {

        graphics2D.drawImage(sprite, xPosition, yPosition, null);
    }

    //movement
    public boolean isMovementLegal(int col, int row){
        return true;
    }
    public boolean moveOverlapPiece(int col, int row){
        return false;
    }
}
