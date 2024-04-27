package pieces;

import main.Board;

import java.awt.image.BufferedImage;

public class Queen extends Piece{
    public Queen(Board board, boolean colorOfTeam, int col, int row){
        super(board);

        this.pieceName = "Queen";

        this.colorOfTeam = colorOfTeam;

        this.col = col;
        this.row = row;
        this.xPosition = col * Board.tileSize;
        this.yPosition = row * Board.tileSize;

        this.sprite = (colorOfTeam ? sheets[4] : sheets[10]).getScaledInstance(Board.tileSize, Board.tileSize, BufferedImage.SCALE_SMOOTH);
    }
}
