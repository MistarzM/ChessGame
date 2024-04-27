package pieces;

import main.Board;

import java.awt.image.BufferedImage;

public class Knight extends Piece{

    public Knight(Board board, boolean colorOfTeam, int col, int row){
        super(board);

        this.pieceName = "Knight";

        this.colorOfTeam = colorOfTeam;

        this.col = col;
        this.row = row;
        this.xPosition = col * Board.tileSize;
        this.yPosition = row * Board.tileSize;

        this.sprite = (colorOfTeam ? sheets[2] : sheets[8]).getScaledInstance(Board.tileSize, Board.tileSize, BufferedImage.SCALE_SMOOTH);
    }
}
