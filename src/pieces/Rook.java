package pieces;

import main.Board;
import main.Game;
import utils.LoadAndSave;

import java.awt.image.BufferedImage;

public class Rook extends Piece{

    private boolean isFirstMove;        // for castling
    public Rook(Game game, boolean colorOfTeam, int col, int row){
        super(game, "Rook", colorOfTeam, col, row, col * Board.TILE_SIZE, row*Board.TILE_SIZE);


        this.isFirstMove = true;

        this.sprite = (colorOfTeam ? LoadAndSave.GetImg(LoadAndSave.WHITE_ROOK) : LoadAndSave.GetImg(LoadAndSave.BLACK_ROOK)).getScaledInstance(Board.TILE_SIZE, Board.TILE_SIZE, BufferedImage.SCALE_SMOOTH);
    }

    public boolean getFirstMove(){
        return isFirstMove;
    }
    public void setFirstMove(boolean FirstMove){
        this.isFirstMove = FirstMove;
    }

    public boolean isMovementLegal(int col, int row){
        if(Math.abs(col - this.col) == 0|| Math.abs(row - this.row) == 0){
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
        }
        return false;
    }
}
