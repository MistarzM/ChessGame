package pieces;

import main.Board;

import java.awt.image.BufferedImage;

public class Pawn extends Piece{

    private boolean isFirstMove;

    public Pawn(Board board, boolean colorOfTeam, int col, int row){
        super(board);

        this.pieceName = "Pawn";

        this.colorOfTeam = colorOfTeam;

        this.col = col;
        this.row = row;
        this.xPosition = col * Board.tileSize;
        this.yPosition = row * Board.tileSize;

        this.isFirstMove = true;

        this.sprite = (colorOfTeam ? sheets[0] : sheets[6]).getScaledInstance(Board.tileSize, Board.tileSize, BufferedImage.SCALE_SMOOTH);
    }

    public void setFirstMove(boolean firstMove){
        this.isFirstMove = firstMove;
    }

    public boolean isMovementLegal(int col, int row){
        if(this.isFirstMove && this.colorOfTeam && Math.abs(this.col - col) == 0 && (this.row - row == 2 || this.row - row == 1)){       // first move, white
            return true;
        } else if (this.isFirstMove && !this.colorOfTeam && Math.abs(this.col - col) == 0 && (this.row - row == -2 || this.row - row == -1)){   // first move, black
            return true;
        } else if (!this.isFirstMove && this.colorOfTeam && Math.abs(this.col - col) == 0 &&  this.row - row == 1){     // !first move, white
            return true;
        } else if(!this.isFirstMove && !this.colorOfTeam && Math.abs(this.col - col) == 0 &&  this.row - row == -1){    // !first move, black
            return true;
        }
        return false;
    }

}
