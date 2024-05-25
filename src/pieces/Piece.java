package pieces;

import main.Game;

import java.awt.Image;
import java.awt.Graphics2D;

public class Piece {

    public int col;
    public int row;

    public int xPosition;
    public int yPosition;

    public boolean colorOfTeam;         // true -> white : false -> black
    protected Image sprite;

    public Game game;

    public Piece(Game game, boolean colorOfTeam, int col, int row, int xPosition, int yPosition) {
        this.game = game;
        this.colorOfTeam = colorOfTeam;
        this.col = col;
        this.row = row;
        this.xPosition = xPosition;
        this.yPosition = yPosition;

    }

    public void paint(Graphics2D g) {

        g.drawImage(sprite, xPosition, yPosition, null);
    }

    //movement
    public boolean isMovementLegal(int col, int row){
        return true;
    }
    public boolean moveOverlapPiece(int col, int row){
        return false;
    }

}
