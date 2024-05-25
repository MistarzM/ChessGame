package main;

import pieces.Piece;
import java.util.ArrayList;

public class Game {
    private boolean whiteMove = true; // cause white -> start
    private boolean endGame = false;
    ArrayList<Piece> piecesList = new ArrayList<>();
    public Piece currentPiece;
    public int enPassantTile = -1;
    public CheckDetector checkDetector;
    private Board board;

    public Game(Board board) {
        this.board = board;
        this.checkDetector = new CheckDetector(this);
        addPieces();
    }

    public void addPieces() {
        // Move the addPieces method from Board to here...
    }

    public Piece getPiece(int col, int row) {
        // Move the getPiece method from Board to here...
    }

    public void capture(Piece piece) {
        // Move the capture method from Board to here...
    }

    public void performMove(Move move) {
        // Move the performMove method from Board to here...
    }

    private void performPawnMove(Move move) {
        // Move the performPawnMove method from Board to here...
    }

    private void performKingMove(Move move) {
        // Move the performKingMove method from Board to here...
    }

    public void promotePawn(Move move) {
        // Move the promotePawn method from Board to here...
    }

    public boolean isMoveLegal(Move move) {
        // Move the isMoveLegal method from Board to here...
    }

    public boolean isSameTeam(Piece piece1, Piece piece2) {
        // Move the isSameTeam method from Board to here...
    }

    private void updateGameStatus() {
        // Move the updateGameStatus method from Board to here...
    }

    private boolean notEnoughPieces(boolean colorOfTeam) {
        // Move the notEnoughPieces method from Board to here...
    }
}