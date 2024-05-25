package main;

import pieces.Piece;
import utils.Constants;

public class CheckDetector {
    private Board board;

    public CheckDetector(Board board) {
        this.board = board;
    }

    public boolean isKingCheck(Move move){
        Piece king = getKing(move.piece.colorOfTeam);

        if(king == null){
            throw new IllegalStateException("King not found");
        }

        int kingCol = king.col;
        int kingRow = king.row;

        if(board.currentPiece != null && board.currentPiece.pieceName.equals("King")){
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
        for(Piece p : board.piecesList){
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
            Piece piece = board.getPiece(kingCol + (i* colValue), kingRow + (i * rowValue));
            if(piece  != null && piece != board.currentPiece) {
                if(!board.isSameTeam(piece, king) && (piece.pieceName.equals("Rook") || piece.pieceName.equals("Queen"))){
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
            Piece piece = board.getPiece(kingCol - (i* colValue), kingRow - (i * rowValue));
            if(piece  != null && piece != board.currentPiece) {
                if(!board.isSameTeam(piece, king) && (piece.pieceName.equals("Bishop") || piece.pieceName.equals("Queen"))){
                    return true;
                }
                break;
            }
        }
        return false;
    }

    private boolean isKingCheckedByKnight(int col, int row, Piece king, int kingCol, int kingRow){
        return checkKnight(board.getPiece(kingCol - 1, kingRow - 2), king , col, row) ||
                checkKnight(board.getPiece(kingCol + 1, kingRow - 2), king , col, row) ||
                checkKnight(board.getPiece(kingCol + 2, kingRow - 1), king , col, row) ||
                checkKnight(board.getPiece(kingCol + 2, kingRow + 1), king , col, row) ||
                checkKnight(board.getPiece(kingCol + 1, kingRow + 2), king , col, row) ||
                checkKnight(board.getPiece(kingCol - 1, kingRow + 2), king , col, row) ||
                checkKnight(board.getPiece(kingCol - 2, kingRow + 1), king , col, row) ||
                checkKnight(board.getPiece(kingCol - 2, kingRow - 1), king , col, row);
    }

    private boolean checkKnight(Piece p, Piece k, int col, int row){
        return p != null && !board.isSameTeam(p, k) && p.pieceName.equals("Knight") && !(p.col == col && p.row == row);
    }

    private boolean isKngCheckedByKing(Piece k, int kingCol, int kingRow){
        return checkKing(board.getPiece(kingCol - 1, kingRow - 1), k) ||
                 checkKing(board.getPiece(kingCol + 1, kingRow - 1), k) ||
                 checkKing(board.getPiece(kingCol, kingRow - 1), k) ||
                 checkKing(board.getPiece(kingCol - 1, kingRow), k) ||
                 checkKing(board.getPiece(kingCol + 1, kingRow), k) ||
                 checkKing(board.getPiece(kingCol - 1, kingRow + 1), k) ||
                 checkKing(board.getPiece(kingCol + 1, kingRow + 1), k) ||
                 checkKing(board.getPiece(kingCol, kingRow + 1), k);
    }

    private boolean checkKing(Piece p, Piece k){
        return p != null && !board.isSameTeam(p, k) && p.pieceName.equals("King");
    }

    private boolean isKingCheckedByPawn(int col, int row, Piece k, int kingCol, int kingRow){
        int colorVal = k.colorOfTeam ? -1 : 1;
        return checkPawn(board.getPiece(kingCol + 1, kingRow + colorVal), k, col, row) ||
                checkPawn(board.getPiece(kingCol - 1, kingRow + colorVal), k, col, row) ;
    }

    private boolean checkPawn(Piece p, Piece k, int col ,int row){
        return p != null && !board.isSameTeam(p, k) && p.pieceName.equals("Pawn") && !(p.col == col && p.row == row);
    }

    public boolean gameOver(Piece k) {

        for(Piece p  : board.piecesList){
            if(board.isSameTeam(p, k)) {
                board.currentPiece = p == k ? k : null;
                for(int row = 0; row < Constants.ROWS; row++){
                    for(int col = 0; col < Constants.COLS; col++){
                        Move move = new Move(board, p, col, row);
                        if(board.isMoveLegal(move)){
                            return false;
                        }
                    }
                }
            }
        }


        return true;
    }
}
