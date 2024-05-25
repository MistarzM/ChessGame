package main;

import pieces.Piece;
import utils.Constants;

public class CheckDetector {
    private Game game ;

    public CheckDetector(Game game) {
        this.game = game;
    }

    public boolean isKingCheck(Move move){
        Piece king = getKing(move.piece.colorOfTeam);

        if(king == null){
            throw new IllegalStateException("King not found");
        }

        int kingCol = king.col;
        int kingRow = king.row;

        if(game.currentPiece != null && game.currentPiece.pieceName.equals("King")){
            kingCol = move.newCol;
            kingRow = move.newRow;
        }

        return  isKingCheckedByRook(move.newCol, move.newRow, king, kingCol, kingRow, 0, 1) ||    // up
                isKingCheckedByRook(move.newCol, move.newRow, king, kingCol, kingRow , 1, 0) ||   //  right
                isKingCheckedByRook(move.newCol, move.newRow, king, kingCol, kingRow,0 , -1) ||   //down
                isKingCheckedByRook(move.newCol, move.newRow, king, kingCol, kingRow, -1, 0) ||   // left

                isKingCheckedByBishop(move.newCol, move.newRow, king, kingCol, kingRow, -1, -1) ||    // up left
                isKingCheckedByBishop(move.newCol, move.newRow, king, kingCol, kingRow, 1, -1) ||     // up right
                isKingCheckedByBishop(move.newCol, move.newRow, king, kingCol, kingRow, 1, 1) ||      // down right
                isKingCheckedByBishop(move.newCol, move.newRow, king, kingCol, kingRow, -1, 1) ||     //down left

                isKingCheckedByKnight(move.newCol, move.newRow, king, kingCol, kingRow) ||
                isKingCheckedByPawn(move.newCol, move.newRow, king, kingCol, kingRow) ||
                isKngCheckedByKing(king, kingCol, kingRow)
                ;
    }

    public Piece getKing(boolean colorOfTeam){
        for(Piece p : game.piecesList){
            if(p.pieceName.equals("King") && p.colorOfTeam == colorOfTeam){
                return p;
            }
        }
        return null;
    }

    private boolean isKingCheckedByRook(int col, int row, Piece king, int kingCol, int kingRow, int colValue, int rowValue){
        for(int i = 1; i < 8; i++){
            if(kingCol + (i * colValue) == col && kingRow + (i * rowValue) == row){
                break;
            }
            Piece piece = game.getPiece(kingCol + (i* colValue), kingRow + (i * rowValue));
            if(piece  != null && piece != game.currentPiece) {
                if(!game.isSameTeam(piece, king) && (piece.pieceName.equals("Rook") || piece.pieceName.equals("Queen"))){
                    return true;
                }
                break;
            }
        }
        return false;
    }

    private boolean isKingCheckedByBishop(int col, int row, Piece king, int kingCol, int kingRow, int colValue, int rowValue){
        for(int i = 1; i < 8; i++){
            if(kingCol - (i * colValue) == col && kingRow - (i * rowValue) == row){
                break;
            }
            Piece piece = game.getPiece(kingCol - (i* colValue), kingRow - (i * rowValue));
            if(piece  != null && piece != game.currentPiece) {
                if(!game.isSameTeam(piece, king) && (piece.pieceName.equals("Bishop") || piece.pieceName.equals("Queen"))){
                    return true;
                }
                break;
            }
        }
        return false;
    }

    private boolean isKingCheckedByKnight(int col, int row, Piece king, int kingCol, int kingRow){
        return checkKnight(game.getPiece(kingCol - 1, kingRow - 2), king , col, row) ||
                checkKnight(game.getPiece(kingCol + 1, kingRow - 2), king , col, row) ||
                checkKnight(game.getPiece(kingCol + 2, kingRow - 1), king , col, row) ||
                checkKnight(game.getPiece(kingCol + 2, kingRow + 1), king , col, row) ||
                checkKnight(game.getPiece(kingCol + 1, kingRow + 2), king , col, row) ||
                checkKnight(game.getPiece(kingCol - 1, kingRow + 2), king , col, row) ||
                checkKnight(game.getPiece(kingCol - 2, kingRow + 1), king , col, row) ||
                checkKnight(game.getPiece(kingCol - 2, kingRow - 1), king , col, row);
    }

    private boolean checkKnight(Piece p, Piece k, int col, int row){
        return p != null && !game.isSameTeam(p, k) && p.pieceName.equals("Knight") && !(p.col == col && p.row == row);
    }

    private boolean isKngCheckedByKing(Piece k, int kingCol, int kingRow){
        return checkKing(game.getPiece(kingCol - 1, kingRow - 1), k) ||
                 checkKing(game.getPiece(kingCol + 1, kingRow - 1), k) ||
                 checkKing(game.getPiece(kingCol, kingRow - 1), k) ||
                 checkKing(game.getPiece(kingCol - 1, kingRow), k) ||
                 checkKing(game.getPiece(kingCol + 1, kingRow), k) ||
                 checkKing(game.getPiece(kingCol - 1, kingRow + 1), k) ||
                 checkKing(game.getPiece(kingCol + 1, kingRow + 1), k) ||
                 checkKing(game.getPiece(kingCol, kingRow + 1), k);
    }

    private boolean checkKing(Piece p, Piece k){
        return p != null && !game.isSameTeam(p, k) && p.pieceName.equals("King");
    }

    private boolean isKingCheckedByPawn(int col, int row, Piece k, int kingCol, int kingRow){
        int colorVal = k.colorOfTeam ? -1 : 1;
        return checkPawn(game.getPiece(kingCol + 1, kingRow + colorVal), k, col, row) ||
                checkPawn(game.getPiece(kingCol - 1, kingRow + colorVal), k, col, row) ;
    }

    private boolean checkPawn(Piece p, Piece k, int col ,int row){
        return p != null && !game.isSameTeam(p, k) && p.pieceName.equals("Pawn") && !(p.col == col && p.row == row);
    }

    public boolean gameOver(Piece k) {

        for(Piece p  : game.piecesList){
            if(game.isSameTeam(p, k)) {
                game.currentPiece = p == k ? k : null;
                for(int row = 0; row < Constants.ROWS; row++){
                    for(int col = 0; col < Constants.COLS; col++){
                        Move move = new Move(game, p, col, row);
                        if(game.isMoveLegal(move)){
                            return false;
                        }
                    }
                }
            }
        }


        return true;
    }
}
