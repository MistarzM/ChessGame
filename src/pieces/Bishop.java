package pieces;

import main.Board;

import java.awt.image.BufferedImage;

public class Bishop extends Piece{
    public Bishop(Board board, boolean colorOfTeam, int col, int row){
        super(board);

        this.pieceName = "Bishop";

        this.colorOfTeam = colorOfTeam;

        this.col = col;
        this.row = row;
        this.xPosition = col * Board.tileSize;
        this.yPosition = row * Board.tileSize;

        this.sprite = (colorOfTeam ? sheets[3] : sheets[9]).getScaledInstance(Board.tileSize, Board.tileSize, BufferedImage.SCALE_SMOOTH);
    }
}
