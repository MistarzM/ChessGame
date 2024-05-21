package pieces;

import main.Board;
import main.Move;
import utils.LoadAndSave;

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

        this.sprite = (colorOfTeam ? LoadAndSave.GetImg(LoadAndSave.WHITE_KING) : LoadAndSave.GetImg(LoadAndSave.BLACK_KING)).getScaledInstance(Board.tileSize, Board.tileSize, BufferedImage.SCALE_SMOOTH);
    }

    public void setFirstMove(boolean FirstMove){
        this.isFirstMove = FirstMove;
    }

    public boolean isMovementLegal(int col, int row){
        if((Math.abs(this.col - col) == 1 && Math.abs(this.row - row) == 0) || (Math.abs(this.col - col) == 0 && Math.abs(this.row - row) == 1) || (Math.abs(this.col - col) * Math.abs(this.row-row) == 1) || canCastle(col, row)){
            return true;
        }
        return false;
    }

    private boolean canCastle(int col, int row){

        if(this.row == row){
            if(col == 6){
                Piece rook = board.getPiece(7, row);
                if(rook != null && rook instanceof  Rook && ((Rook) rook).getFirstMove() && isFirstMove){
                    return board.getPiece(5, row) == null && board.getPiece(6, row) == null && !board.checkDetector.isKingCheck(new Move(board, this, 5, row));
                }
            } else if (col ==2){
                Piece rook = board.getPiece(0, row);
                if(rook != null && rook instanceof  Rook && ((Rook) rook).getFirstMove() && isFirstMove){
                    return board.getPiece(3, row) == null && board.getPiece(2, row) == null && board.getPiece(1, row) == null&& !board.checkDetector.isKingCheck(new Move(board, this, 3, row));
                }
            }
        }

        return false;
    }
}
