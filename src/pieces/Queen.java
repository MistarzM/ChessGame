package pieces;

import main.Game;
import main.Board;
import utils.LoadAndSave;

import java.awt.image.BufferedImage;

public class Queen extends Piece{
    public Queen(Game game, boolean colorOfTeam, int col, int row){
        super(game, "Queen", colorOfTeam, col, row, col * Board.TILE_SIZE, row * Board.TILE_SIZE);

        this.sprite = (colorOfTeam ? LoadAndSave.GetImg(LoadAndSave.WHITE_QUEEN) : LoadAndSave.GetImg(LoadAndSave.BLACK_QUEEN)).getScaledInstance(Board.TILE_SIZE, Board.TILE_SIZE, BufferedImage.SCALE_SMOOTH);
    }

    public boolean isMovementLegal(int col, int row){
        if(Math.abs(col - this.col) == 0 || Math.abs(row - this.row) == 0 || Math.abs(col - this.col) == Math.abs(row - this.row)){
            return true;
        }
        return false;
    }

    public boolean moveOverlapPiece(int col, int row){
        int rowDirection = (row > this.row) ? 1 : -1;
        int colDirection = (col > this.col) ? 1 : -1;

        if(this.row == row){
            for(int c = this.col + colDirection; c != col; c += colDirection){
                if(game.getPiece(c, this.row) != null){
                    return true;
                }
            }
        } else if(this.col == col){
            for(int r = this.row + rowDirection; r != row; r += rowDirection){
                if(game.getPiece(this.col, r) != null){
                    return true;
                }
            }
        } else if(Math.abs(this.row - row) == Math.abs(this.col - col)){
            for(int i = 1; i < Math.abs(this.col - col); ++i){
                if(game.getPiece(this.col + i * colDirection, this.row + i * rowDirection) != null){
                    return true;
                }
            }
        }
        return false;
    }
}
