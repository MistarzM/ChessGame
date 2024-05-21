package pieces;

import main.Board;
import utils.LoadAndSave;

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

        this.sprite = (colorOfTeam ? LoadAndSave.GetImg(LoadAndSave.WHITE_BISHOP) : LoadAndSave.GetImg(LoadAndSave.BLACK_BISHOP)).getScaledInstance(Board.tileSize, Board.tileSize, BufferedImage.SCALE_SMOOTH);
    }

    public boolean isMovementLegal(int col, int row){
        if(Math.abs(col - this.col) == Math.abs(row - this.row)){
            return true;
        }
        return false;
    }
    public boolean moveOverlapPiece(int col, int row){
        int rowDirection = (row > this.row) ? 1 : -1;
        int colDirection = (col > this.col) ? 1 : -1;

        if(Math.abs(this.row - row) == Math.abs(this.col - col)){
            for(int i = 1; i < Math.abs(this.col - col); ++i){
                if(board.getPiece(this.col + i * colDirection, this.row + i * rowDirection) != null){
                    return true;
                }
            }
        }
        return false;
    }
}
