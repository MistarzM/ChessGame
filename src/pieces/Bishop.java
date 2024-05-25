package pieces;

import main.Board;
import main.Game;
import utils.LoadAndSave;

import java.awt.image.BufferedImage;

public class Bishop extends Piece{
    public Bishop(Game game, boolean colorOfTeam, int col, int row){
        super(game, "Bishop", colorOfTeam, col, row, col * Board.TILE_SIZE, row * Board.TILE_SIZE);

        this.sprite = (colorOfTeam ? LoadAndSave.GetImg(LoadAndSave.WHITE_BISHOP) : LoadAndSave.GetImg(LoadAndSave.BLACK_BISHOP)).getScaledInstance(Board.TILE_SIZE, Board.TILE_SIZE, BufferedImage.SCALE_SMOOTH);
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
                if(game.getPiece(this.col + i * colDirection, this.row + i * rowDirection) != null){
                    return true;
                }
            }
        }
        return false;
    }
}
