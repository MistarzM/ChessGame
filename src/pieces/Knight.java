package pieces;

import main.Board;
import utils.LoadAndSave;

import java.awt.image.BufferedImage;

public class Knight extends Piece{

    public Knight(Board board, boolean colorOfTeam, int col, int row){
        super(board, "Knight", colorOfTeam, col, row, col * Board.tileSize, row * Board.tileSize);

        this.sprite = (colorOfTeam ? LoadAndSave.GetImg(LoadAndSave.WHITE_KNIGHT) : LoadAndSave.GetImg(LoadAndSave.BLACK_KNIGHT)).getScaledInstance(Board.tileSize, Board.tileSize, BufferedImage.SCALE_SMOOTH);
    }

    public boolean isMovementLegal(int col, int row){
        if(Math.abs(col - this.col) * Math.abs(row - this.row) == 2){
            return true;
        }
        return false;
    }
}
