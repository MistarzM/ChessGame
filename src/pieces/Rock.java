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

    public boolean isMovementLegal(int col, int row){
        if(Math.abs(col - this.col) == 0|| Math.abs(row - this.row) == 0){
            return true;
        }
        return false;
    }

    public boolean moveOverlapPiece(int col, int row){


        //top
        if(this.row>row){
            for(int r = this.row-1; r > row; --r){
                if(board.getPiece(this.col, r) != null){
                    return true;
                }
            }
        }
        //down
        if(this.row<row){
            for(int r = this.row+1; r < row; ++r){
                if(board.getPiece(this.col, r) != null){
                    return true;
                }
            }
        }
        //left
        if(this.col > col){
            for(int c = this.col-1; c > col; --c){
                if(board.getPiece(c, this.row)!= null){
                    return true;
                }
            }
        }
        //right
        if(this.col < col){
            for(int c = this.col+1; c < col; ++c){
                if(board.getPiece(c, this.row)!= null){
                    return true;
                }
            }
        }
        return false;
    }
}
