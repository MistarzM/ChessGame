package pieces;

import main.Board;

import java.awt.image.BufferedImage;

public class King extends Piece{

    private boolean isFirstMove;

    public King(Board board, boolean colorOfTeam, int col, int row){
        super(board);

        this.pieceName = "King";

        this.colorOfTeam = colorOfTeam;

        this.col = col;
        this.row = row;
        this.xPosition = col * Board.tileSize;
        this.yPosition = row * Board.tileSize;

        this.isFirstMove = true;

        this.sprite = (colorOfTeam ? sheets[5] : sheets[11]).getScaledInstance(Board.tileSize, Board.tileSize, BufferedImage.SCALE_SMOOTH);
    }

    public boolean isMovementLegal(int col, int row){
        if((Math.abs(this.col - col) == 1 && Math.abs(this.row - row) == 0) || (Math.abs(this.col - col) == 0 && Math.abs(this.row - row) == 1) || (Math.abs(this.col - col) * Math.abs(this.row-row) == 1)){
            return true;
        }
        return false;
    }
}
