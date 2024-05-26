package main;

import pieces.*;
import utils.Constants;

import java.util.ArrayList;

public class Game {
    private boolean whiteMove = true;   // Boolean variable to keep track of the current player's turn(True -> white | false -> black)
    private boolean endGame = false;    // Boolean variable to check if the game has ended (initially set to false)
    ArrayList<Piece> piecesList = new ArrayList<>();    //  ArrayList to store all the pieces in the game
    public Piece currentPiece;  //  The piece that is currently being moved
    public int enPassantTile = -1;  //  The tile where en passant can occur (initially set to -1)
    public CheckDetector checkDetector;
    private Board board;
    private Promotion promotion;

    public Game(Board board) {
        this.board = board;
        this.checkDetector = new CheckDetector(this);
        this.promotion = new Promotion(this);
        addPieces(); // Adding pieces to the game
    }

    public void addPieces(){
        // Adding pawns
        for(int i = 0; i < 8; i++){
            piecesList.add(new Pawn(this, false, i, 1));
            piecesList.add(new Pawn(this, true, i, 6));
        }

        // Adding other pieces for both teams
        piecesList.add(new Rook(this, false, 0, 0));
        piecesList.add(new Knight(this, false, 1, 0));
        piecesList.add(new Bishop(this, false, 2, 0));
        piecesList.add(new Queen(this, false, 3, 0));
        piecesList.add(new King(this, false, 4, 0));
        piecesList.add(new Bishop(this, false, 5, 0));
        piecesList.add(new Knight(this, false, 6, 0));
        piecesList.add(new Rook(this, false, 7, 0));

        piecesList.add(new Rook(this, true, 0, 7));
        piecesList.add(new Knight(this, true, 1, 7));
        piecesList.add(new Bishop(this, true, 2, 7));
        piecesList.add(new Queen(this, true, 3, 7));
        piecesList.add(new King(this, true, 4, 7));
        piecesList.add(new Bishop(this, true, 5, 7));
        piecesList.add(new Knight(this, true, 6, 7));
        piecesList.add(new Rook(this, true, 7, 7));
    }

    //  Get the piece at a given position || Returns null if no piece is found
    public Piece getPiece(int col, int row) {
        for(Piece p : piecesList){
            if (p.col == col && p.row == row){
                return p;
            }
        }
        return null;
    }

    // removes a captured piece from the game.
    public void capture(Piece piece){
        piecesList.remove(piece);
    }

    // Handles special moves for pawn and king
    // Updates the piece's position and captures the piece at the new position
    public void performMove(Move move){
        if(move.piece instanceof Pawn){
            performPawnMove(move); // Perform special pawn move
        }else if(move.piece instanceof King){
            performKingMove((move)); // Perform special king move
        }
        // Update the piece's position
        move.piece.col = move.newCol;
        move.piece.row = move.newRow;
        move.piece.xPosition = move.newCol * Board.TILE_SIZE;
        move.piece.yPosition = move.newRow * Board.TILE_SIZE;

        // If the move is executed by a pawn, set its firstMove = false.
        if (move.piece instanceof Pawn) {
            ((Pawn) move.piece).setFirstMove(false);
        }

        // Capture the piece at the new position
        capture(move.capture);

        // Switch turns
        whiteMove = !whiteMove;

        // Update the game status
        updateGameStatus();
    }

    // method to get the tile number from the column and row
    public int getTileNumber(int col, int row){
        return row * Constants.ROWS + col;
    }

    // method to perform a pawn move
    // Handles en passant and pawn promotion
    private void performPawnMove(Move move){
        if(getTileNumber(move.newCol, move.newRow) == enPassantTile && move.piece.colorOfTeam){ // check if the new position of the pawn is the enPassantTile and if the pawn is of the correct team.
            move.capture = getPiece(move.newCol, move.newRow + 1);  // If the conditions are met, the opponent's pawn is captured
        }
        if(getTileNumber(move.newCol, move.newRow) == enPassantTile && !move.piece.colorOfTeam){
            move.capture = getPiece(move.newCol, move.newRow - 1);  // If the conditions are met, the opponent's pawn is captured.
        }

        // Set the enPassantTile for the next move:
        // If the pawn moves two squares from its starting position =>
        // => it allow an opponent's pawn to capture it en passant
        if(Math.abs(move.piece.row - move.newRow) == 2 && move.piece.colorOfTeam){
            enPassantTile = getTileNumber(move.newCol, move.newRow + 1);    // enPassantTile  =   square where the capture would occur.
        } else if (Math.abs(move.piece.row - move.newRow) == 2 && !move.piece.colorOfTeam){
            enPassantTile = getTileNumber(move.newCol, move.newRow - 1);    // enPassantTile  =   square where the capture would occur.
        } else {
            // If the pawn did not move two squares from its starting position
            // there is no possibility of an en passant capture in the next move
            enPassantTile = -1; // enPassantTile set to -1.
        }

        // If a pawn reaches the end of the board, it can be promoted to any other piece
        if((move.piece.colorOfTeam && move.newRow == 0) || (!move.piece.colorOfTeam && move.newRow == 7)){  // check if the pawn has reached the end of the board
            promotion.promotePawn(move);    //If the pawn has reached the end of the board => promotion
        }
    }

    // used to handle special moves (castling)
    private void performKingMove(Move move){
        if(Math.abs(move.piece.col - move.newCol) == 2){    // check if the king is attempting to castle
            Piece rook;
            if(move.piece.col < move.newCol) {  // if king's new column > original column  = the king is castling to the right
                rook = getPiece(7, move.piece.row); // get the rook involved in the castling
                rook.col = 5; // move rook to new position
            } else{                             // if king's new column < original column  = the king is castling to the left
                rook = getPiece(0, move.piece.row); // get the rook involved in the castling
                rook.col = 3; // move rook to new position
            }
            rook.xPosition = rook.col * Board.TILE_SIZE; // update => rook (xPosition)
            ((King) move.piece).setFirstMove(false); // set king firstMove = false
            ((Rook) rook).setFirstMove(false); // set rook firstMove  = false
        }
    }

    // check if move is legal
    public boolean isMoveLegal(Move move){
        // The method returns false if any of the following conditions are met:
        // 1. The game has ended
        // 2. It's not the correct player's turn
        // 3. The move is not legal for the piece
        // 4. The move overlaps another piece of the same team
        // 5. The move puts the player's own king in check.
        if(endGame ||
                move.piece.colorOfTeam != whiteMove  ||
                isSameTeam(move.piece, move.capture) ||
                !move.piece.isMovementLegal(move.newCol, move.newRow) ||
                move.piece.moveOverlapPiece(move.newCol, move.newRow) ||
                checkDetector.isKingCheck(move)){
            return false;
        }

        return true;    // If none of the above conditions are met, the move is legal, return => true.
    }

    // check if two pieces are on the same team
    public boolean isSameTeam(Piece piece1, Piece piece2){
        if(piece1 == null || piece2 == null || piece1.colorOfTeam != piece2.colorOfTeam){
            return false;
        }

        return true;
    }

    // checks the game status and updates the 'endGame' variable
    private void updateGameStatus(){
        Piece king =  checkDetector.getKing(whiteMove);     // get the king of the current player's team

        if(checkDetector.gameOver(king)){   // check if the game is over.
            if(checkDetector.isKingCheck(new Move(this, king, king.col, king.row))) {
                new Result(whiteMove ? "Black wins": "White wins"); // if game over AND king => in check, the other team wins.
            } else {
                new Result("Stalemate");        // if the king is not in check => stalemate
            }
            endGame = true;     // endGame = true as the game is over
        } else if(notEnoughPieces(true) && notEnoughPieces(false)){
            new Result("Insufficient Mating Material draw");    // ff there are not enough pieces to checkmate => draw
            endGame = true;// endGame = true as the game is over
        }
    }

    //  checks if there are not enough pieces on the board for a checkmate
    private boolean notEnoughPieces(boolean colorOfTeam){
        int count = 0; // create counter to keep track of the number of pieces
        boolean importantPieces = false; // boolean to check if there are any important pieces (Queen, Rook, or Pawn) (on team)

        for(Piece p : piecesList){  // iterate over all the pieces in the game
            if(p.colorOfTeam == colorOfTeam){   // check if the piece is on the same team
                count++; // increment counter
                if(p instanceof Queen || p instanceof  Rook || p instanceof  Pawn){
                    importantPieces = true;     // if piece is: Queen, Rook, Pawn => importantPiece = true
                }
            }
        }

        // if  (no important pieces) AND (there are less than 3 pieces on the team) => return true
        // Otherwise, return false (there are enough pieces for a checkmate)
        return !importantPieces && count < 3;
    }
}