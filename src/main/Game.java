package main;

import pieces.Pawn;
import pieces.Rook;
import pieces.Knight;
import pieces.Bishop;
import pieces.Queen;
import pieces.King;
import pieces.Piece;
import utils.Constants;

import utils.LoadAndSave;

import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

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

    public void addPieces(){
        for(int i = 0; i < 8; i++){
            piecesList.add(new Pawn(this, false, i, 1));
            piecesList.add(new Pawn(this, true, i, 6));
        }

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

    public Piece getPiece(int col, int row) {

        for(Piece p : piecesList){
            if (p.col == col && p.row == row){
                return p;
            }
        }

        return null;
    }

    public void capture(Piece piece){
        piecesList.remove(piece);
    }

    public void performMove(Move move){

        if(move.piece instanceof Pawn){
            performPawnMove(move);
        }else if(move.piece instanceof King){
            performKingMove((move));
        }
        move.piece.col = move.newCol;
        move.piece.row = move.newRow;

        move.piece.xPosition = move.newCol * Board.TILE_SIZE;
        move.piece.yPosition = move.newRow * Board.TILE_SIZE;

        if (move.piece instanceof Pawn) {                   // if move executed by pawn, pawn.firstMove = false;
            ((Pawn) move.piece).setFirstMove(false);
        }


        capture(move.capture);

        whiteMove = !whiteMove;

        updateGameStatus();
    }

    public int getTileNumber(int col, int row){
        return row * Constants.ROWS + col;
    }

    private void performPawnMove(Move move){

        if(getTileNumber(move.newCol, move.newRow) == enPassantTile && move.piece.colorOfTeam){
            move.capture = getPiece(move.newCol, move.newRow + 1);
        }
        if(getTileNumber(move.newCol, move.newRow) == enPassantTile && !move.piece.colorOfTeam){
            move.capture = getPiece(move.newCol, move.newRow - 1);
        }
        if(Math.abs(move.piece.row - move.newRow) == 2 && move.piece.colorOfTeam){
            enPassantTile = getTileNumber(move.newCol, move.newRow + 1);
        } else if (Math.abs(move.piece.row - move.newRow) == 2 && !move.piece.colorOfTeam){
            enPassantTile = getTileNumber(move.newCol, move.newRow - 1);
        } else {
            enPassantTile = -1;
        }

        if((move.piece.colorOfTeam && move.newRow == 0) || (!move.piece.colorOfTeam && move.newRow == 7)){
            promotePawn(move);
        }
    }

    private void performKingMove(Move move){

        if(Math.abs(move.piece.col - move.newCol) == 2){
            Piece rook;
            if(move.piece.col < move.newCol) {
                rook = getPiece(7, move.piece.row);
                rook.col = 5;
            } else{
                rook = getPiece(0, move.piece.row);
                rook.col = 3;
            }
            rook.xPosition = rook.col * Board.TILE_SIZE;
            ((King) move.piece).setFirstMove(false);
            ((Rook) rook).setFirstMove(false);
        }

    }

    public void promotePawn(Move move){
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
                piecesList.add(new Rook(this, move.piece.colorOfTeam, move.newCol, move.newRow));
                break;
            case 2:
                piecesList.add(new Bishop(this, move.piece.colorOfTeam, move.newCol, move.newRow));
                break;
            case 3:
                piecesList.add(new Knight(this, move.piece.colorOfTeam, move.newCol, move.newRow));
                break;
            case 0:
            default:
                piecesList.add(new Queen(this, move.piece.colorOfTeam, move.newCol, move.newRow));
                break;
        }
        capture(move.piece);
    }

    public boolean isMoveLegal(Move move){

        if(endGame || move.piece.colorOfTeam != whiteMove  ||
                isSameTeam(move.piece, move.capture) ||
                !move.piece.isMovementLegal(move.newCol, move.newRow) ||
                move.piece.moveOverlapPiece(move.newCol, move.newRow) ||
                checkDetector.isKingCheck(move)){
            return false;
        }

        return true;
    }

    public boolean isSameTeam(Piece piece1, Piece piece2){
        if(piece1 == null || piece2 == null || piece1.colorOfTeam != piece2.colorOfTeam){
            return false;
        }

        return true;
    }

    private void updateGameStatus(){
        Piece king =  checkDetector.getKing(whiteMove);

        if(checkDetector.gameOver(king)){
            if(checkDetector.isKingCheck(new Move(this, king, king.col, king.row))) {
                new Result(whiteMove ? "Black wins": "White wins");
            }else {
                new Result("Stalemate");
            }
            endGame = true;
        } else if(notEnoughPieces(true) && notEnoughPieces(false)){
            new Result("Insufficient Mating Material draw");
            endGame = true;
        }
    }

    private boolean notEnoughPieces(boolean colorOfTeam){
        int count = 0;
        boolean importantPieces = false;

        for(Piece p : piecesList){
            if(p.colorOfTeam == colorOfTeam){
                count++;
                if(p instanceof Queen || p instanceof  Rook || p instanceof  Pawn){
                    importantPieces = true;
                }
            }
        }

        return !importantPieces && count < 3;
    }
}