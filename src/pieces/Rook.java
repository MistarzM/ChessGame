package pieces;

import main.Board;

import java.awt.image.BufferedImage;

public class Rook extends Piece{

    private boolean isFirstMove;        // for castling
    public Rook(Board board, boolean colorOfTeam, int col, int row){
        super(board);

        this.pieceName = "Rook";

        this.colorOfTeam = colorOfTeam;

        this.col = col;
        this.row = row;
        this.xPosition = col * Board.tileSize;
        this.yPosition = row * Board.tileSize;

        this.isFirstMove = true;

        this.sprite = (colorOfTeam ? sheets[1] : sheets[7]).getScaledInstance(Board.tileSize, Board.tileSize, BufferedImage.SCALE_SMOOTH);
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
                if(board.getPiece(c, this.row) != null){
                    return true;
                }
            }
        } else if(this.col == col){
            for(int r = this.row + rowDirection; r != row; r += rowDirection){
                if(board.getPiece(this.col, r) != null){
                    return true;
                }
            }
        }
        return false;
    }
}
