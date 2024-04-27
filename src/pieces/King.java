package pieces;

import main.Board;

import java.awt.image.BufferedImage;

public class King extends Piece{
    public King(Board board, boolean colorOfTeam, int col, int row){
        super(board);

        this.pieceName = "King";

        this.colorOfTeam = colorOfTeam;

        this.col = col;
        this.row = row;
        this.xPosition = col * Board.tileSize;
        this.yPosition = row * Board.tileSize;

        this.sprite = (colorOfTeam ? sheets[5] : sheets[11]).getScaledInstance(Board.tileSize, Board.tileSize, BufferedImage.SCALE_SMOOTH);
    }
}
