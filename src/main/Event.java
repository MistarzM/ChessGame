package main;

import pieces.Piece;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;


public class Event extends MouseAdapter {
    Board board;

    public Event(Board board){
        this.board = board;
    }

    @Override
    public void mousePressed(MouseEvent e){
        int col = e.getX() / Board.TILE_SIZE;
        int row = e.getY() / Board.TILE_SIZE;

        Piece piecePosition = board.getPiece(col, row);

        if(piecePosition != null){
            board.currentPiece = piecePosition;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e){
         if(board.currentPiece != null){
             board.currentPiece.xPosition = e.getX() - Board.TILE_SIZE / 2;
             board.currentPiece.yPosition = e.getY() - Board.TILE_SIZE/ 2;

             board.repaint();
         }
    }

    @Override
    public void mouseReleased(MouseEvent e){

        int col = e.getX() / Board.TILE_SIZE;
        int row = e.getY() / Board.TILE_SIZE;

        if(board.currentPiece != null){
            Move move = new Move(board, board.currentPiece, col, row);

            if(board.isMoveLegal(move)){
                board.performMove(move);
            } else {
                board.currentPiece.xPosition = board.currentPiece.col * Board.TILE_SIZE;
                board.currentPiece.yPosition = board.currentPiece.row * Board.TILE_SIZE;
            }
        }

        board.currentPiece = null;
        board.repaint();

    }
}
