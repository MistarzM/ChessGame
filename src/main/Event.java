package main;

import pieces.Piece;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;


public class Event extends MouseAdapter {
    Game game;
    Board board;

    public Event(Game game, Board board){
        this.board = board;
        this.game = game;
    }

    @Override
    public void mousePressed(MouseEvent e){
        int col = e.getX() / Board.TILE_SIZE;
        int row = e.getY() / Board.TILE_SIZE;

        Piece piecePosition = game.getPiece(col, row);

        if(piecePosition != null){
            game.currentPiece = piecePosition;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e){
         if(game.currentPiece != null){
             game.currentPiece.xPosition = e.getX() - Board.TILE_SIZE / 2;
             game.currentPiece.yPosition = e.getY() - Board.TILE_SIZE/ 2;

             board.repaint();
         }
    }

    @Override
    public void mouseReleased(MouseEvent e){

        int col = e.getX() / Board.TILE_SIZE;
        int row = e.getY() / Board.TILE_SIZE;

        if(game.currentPiece != null){
            Move move = new Move(game, game.currentPiece, col, row);

            if(game.isMoveLegal(move)){
                game.performMove(move);
            } else {
                game.currentPiece.xPosition = game.currentPiece.col * Board.TILE_SIZE;
                game.currentPiece.yPosition = game.currentPiece.row * Board.TILE_SIZE;
            }
        }

        game.currentPiece = null;
        board.repaint();

    }
}
