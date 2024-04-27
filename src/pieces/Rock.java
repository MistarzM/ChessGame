package pieces;

import main.Board;

import java.awt.image.BufferedImage;

public class Rock extends Piece{
    public Rock(Board board, boolean colorOfTeam, int col, int row){
        super(board);

        this.pieceName = "Rock";

        this.colorOfTeam = colorOfTeam;

        this.col = col;
        this.row = row;
        this.xPosition = col * Board.tileSize;
        this.yPosition = row * Board.tileSize;

        this.sprite = (colorOfTeam ? sheets[1] : sheets[7]).getScaledInstance(Board.tileSize, Board.tileSize, BufferedImage.SCALE_SMOOTH);
    }
}
