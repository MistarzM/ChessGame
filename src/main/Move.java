package main;

import pieces.Piece;
public class Move {

    int prevCol;
    int prevRow;
    int newCol;
    int newRow;

    Piece piece;
    Piece capture;

    public Move(Board board, Piece piece, int newCol, int newRow){

        this.piece = piece;
        this.prevCol = piece.col;
        this.prevRow = piece.row;
        this.newCol = newCol;
        this.newRow = newRow;

        this.capture = board.getPiece(newCol, newRow);
    }
}
