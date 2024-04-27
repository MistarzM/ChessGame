package pieces;

import main.Board;

import java.awt.image.BufferedImage;

public class Pawn extends Piece{
    public Pawn(Board board, boolean colorOfTeam, int col, int row){
        super(board);

        this.pieceName = "Pawn";

        this.colorOfTeam = colorOfTeam;

        this.col = col;
        this.row = row;
        this.xPosition = col * Board.tileSize;
        this.yPosition = row * Board.tileSize;

        this.sprite = (colorOfTeam ? sheets[0] : sheets[6]).getScaledInstance(Board.tileSize, Board.tileSize, BufferedImage.SCALE_SMOOTH);
    }
}
