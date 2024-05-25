package pieces;

import main.Board;
import main.Game;
import main.Move;
import utils.LoadAndSave;

import java.awt.image.BufferedImage;

public class King extends Piece{

    private boolean isFirstMove;

    public King(Game game, boolean colorOfTeam, int col, int row){
        super(game, "King", colorOfTeam, col, row, col * Board.TILE_SIZE, row * Board.TILE_SIZE);

        this.isFirstMove = true;

        this.sprite = (colorOfTeam ? LoadAndSave.GetImg(LoadAndSave.WHITE_KING) : LoadAndSave.GetImg(LoadAndSave.BLACK_KING)).getScaledInstance(Board.TILE_SIZE, Board.TILE_SIZE, BufferedImage.SCALE_SMOOTH);
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
                Piece rook = game.getPiece(7, row);
                if(rook != null && rook instanceof  Rook && ((Rook) rook).getFirstMove() && isFirstMove){
                    return game.getPiece(5, row) == null && game.getPiece(6, row) == null && !game.checkDetector.isKingCheck(new Move(game, this, 5, row));
                }
            } else if (col ==2){
                Piece rook = game.getPiece(0, row);
                if(rook != null && rook instanceof  Rook && ((Rook) rook).getFirstMove() && isFirstMove){
                    return game.getPiece(3, row) == null && game.getPiece(2, row) == null && game.getPiece(1, row) == null&& !game.checkDetector.isKingCheck(new Move(game, this, 3, row));
                }
            }
        }

        return false;
    }
}
