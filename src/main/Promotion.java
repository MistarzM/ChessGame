package main;

import pieces.*;
import utils.LoadAndSave;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Promotion {

    private Game game;

    public Promotion(Game game) {
        this.game = game;
    }

    public void promotePawn(Move move) {
        ImageIcon queenPromotion = new ImageIcon(move.piece.colorOfTeam ? LoadAndSave.GetImg(LoadAndSave.WHITE_QUEEN) : LoadAndSave.GetImg(LoadAndSave.BLACK_QUEEN));
        ImageIcon rookPromotion = new ImageIcon(move.piece.colorOfTeam ? LoadAndSave.GetImg(LoadAndSave.WHITE_ROOK) : LoadAndSave.GetImg(LoadAndSave.BLACK_ROOK));
        ImageIcon bishopPromotion = new ImageIcon(move.piece.colorOfTeam ? LoadAndSave.GetImg(LoadAndSave.WHITE_BISHOP) : LoadAndSave.GetImg(LoadAndSave.BLACK_BISHOP));
        ImageIcon knightPromotion = new ImageIcon(move.piece.colorOfTeam ? LoadAndSave.GetImg(LoadAndSave.WHITE_KNIGHT) : LoadAndSave.GetImg(LoadAndSave.BLACK_KNIGHT));
        Object[] optionToChoose = new Object[] {queenPromotion, rookPromotion, bishopPromotion, knightPromotion};
        int chosenOption = JOptionPane.showOptionDialog(null, "Choose a piece for promotion", "Pawn Promotion",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, optionToChoose, optionToChoose[0]);
        switch(chosenOption){
            case 1:
                game.piecesList.add(new Rook(game, move.piece.colorOfTeam, move.newCol, move.newRow));
                break;
            case 2:
                game.piecesList.add(new Bishop(game, move.piece.colorOfTeam, move.newCol, move.newRow));
                break;
            case 3:
                game.piecesList.add(new Knight(game, move.piece.colorOfTeam, move.newCol, move.newRow));
                break;
            case 0:
            default:
                game.piecesList.add(new Queen(game, move.piece.colorOfTeam, move.newCol, move.newRow));
                break;
        }
        game.capture(move.piece);
    }
}