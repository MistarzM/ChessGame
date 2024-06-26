package pieces;

import main.Game;
import main.Board;
import utils.LoadAndSave;

import java.awt.image.BufferedImage;

public class Knight extends Piece{

    public Knight(Game game, boolean colorOfTeam, int col, int row){
        super(game,  colorOfTeam, col, row, col * Board.TILE_SIZE, row * Board.TILE_SIZE);

        this.sprite = (colorOfTeam ? LoadAndSave.GetImg(LoadAndSave.WHITE_KNIGHT) : LoadAndSave.GetImg(LoadAndSave.BLACK_KNIGHT)).getScaledInstance(Board.TILE_SIZE, Board.TILE_SIZE, BufferedImage.SCALE_SMOOTH);
    }

    @Override
    public boolean isMovementLegal(int col, int row){
        if(Math.abs(col - this.col) * Math.abs(row - this.row) == 2){
            return true;
        }
        return false;
    }
}
