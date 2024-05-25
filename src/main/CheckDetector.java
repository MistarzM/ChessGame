package main;

import pieces.*;
import utils.Constants;

public class CheckDetector {
    private Game game;

    public CheckDetector(Game game) {
        this.game = game;
    }

    public boolean isKingCheck(Move move) {
        Piece king = getKing(move.piece.colorOfTeam);

        if (king == null) {
            throw new IllegalStateException("King not found");
        }

        int kingCol = king.col;
        int kingRow = king.row;

        if (game.currentPiece != null && game.currentPiece instanceof King) {
            kingCol = move.newCol;
            kingRow = move.newRow;
        }

        return isKingCheckedByRook(move, king, kingCol, kingRow) ||
                isKingCheckedByBishop(move, king, kingCol, kingRow) ||
                isKingCheckedByKnight(move, king, kingCol, kingRow) ||
                isKingCheckedByPawn(move, king, kingCol, kingRow) ||
                isKingCheckedByKing(king, kingCol, kingRow);
    }

    private boolean isKingCheckedByRook(Move move, Piece king, int kingCol, int kingRow) {
        return isKingCheckedByRookOrQueen(move, king, kingCol, kingRow, Direction.NORTH) ||  // up
                isKingCheckedByRookOrQueen(move, king, kingCol, kingRow, Direction.EAST) ||  // right
                isKingCheckedByRookOrQueen(move, king, kingCol, kingRow, Direction.SOUTH) || // down
                isKingCheckedByRookOrQueen(move, king, kingCol, kingRow, Direction.WEST);   // left
    }

    private boolean isKingCheckedByBishop(Move move, Piece king, int kingCol, int kingRow) {
        return isKingCheckedByBishopOrQueen(move, king, kingCol, kingRow, Direction.NORTH_EAST) || // up right
                isKingCheckedByBishopOrQueen(move, king, kingCol, kingRow, Direction.SOUTH_EAST) ||  // down right
                isKingCheckedByBishopOrQueen(move, king, kingCol, kingRow, Direction.SOUTH_WEST) ||   // down left
                isKingCheckedByBishopOrQueen(move, king, kingCol, kingRow, Direction.NORTH_WEST);    // up left
    }

    private boolean isKingCheckedByRookOrQueen(Move move, Piece king, int kingCol, int kingRow, Direction direction) {
        for (int i = 1; i < 8; i++) {
            if (kingCol + (i * direction.colValue) == move.newCol && kingRow + (i * direction.rowValue) == move.newRow) {
                break;
            }
            Piece piece = game.getPiece(kingCol + (i * direction.colValue), kingRow + (i * direction.rowValue));
            if (piece != null && piece != game.currentPiece) {
                if (!game.isSameTeam(piece, king) && (piece instanceof Rook || piece instanceof Queen)) {
                    return true;
                }
                break;
            }
        }
        return false;
    }

    private boolean isKingCheckedByBishopOrQueen(Move move, Piece king, int kingCol, int kingRow, Direction direction) {
        for (int i = 1; i < 8; i++) {
            if (kingCol + (i * direction.colValue) == move.newCol && kingRow + (i * direction.rowValue) == move.newRow) {
                break;
            }
            Piece piece = game.getPiece(kingCol + (i * direction.colValue), kingRow + (i * direction.rowValue));
            if (piece != null && piece != game.currentPiece) {
                if (!game.isSameTeam(piece, king) && (piece instanceof Bishop || piece instanceof Queen)) {
                    return true;
                }
                break;
            }
        }
        return false;
    }

    private boolean isKingCheckedByKnight(Move move, Piece king, int kingCol, int kingRow) {
        return checkKnight(game.getPiece(kingCol - 1, kingRow - 2), king, move.newCol, move.newRow) ||
                checkKnight(game.getPiece(kingCol + 1, kingRow - 2), king, move.newCol, move.newRow) ||
                checkKnight(game.getPiece(kingCol + 2, kingRow - 1), king, move.newCol, move.newRow) ||
                checkKnight(game.getPiece(kingCol + 2, kingRow + 1), king, move.newCol, move.newRow) ||
                checkKnight(game.getPiece(kingCol + 1, kingRow + 2), king, move.newCol, move.newRow) ||
                checkKnight(game.getPiece(kingCol - 1, kingRow + 2), king, move.newCol, move.newRow) ||
                checkKnight(game.getPiece(kingCol - 2, kingRow + 1), king, move.newCol, move.newRow) ||
                checkKnight(game.getPiece(kingCol - 2, kingRow - 1), king, move.newCol, move.newRow);
    }

    private boolean isKingCheckedByPawn(Move move, Piece king, int kingCol, int kingRow) {
        int colorVal = king.colorOfTeam ? -1 : 1;
        return checkPawn(game.getPiece(kingCol + 1, kingRow + colorVal), king, move.newCol, move.newRow) ||
                checkPawn(game.getPiece(kingCol - 1, kingRow + colorVal), king, move.newCol, move.newRow);
    }

    private boolean isKingCheckedByKing(Piece king, int kingCol, int kingRow) {
        return checkKing(game.getPiece(kingCol - 1, kingRow - 1), king) ||
                checkKing(game.getPiece(kingCol + 1, kingRow - 1), king) ||
                checkKing(game.getPiece(kingCol, kingRow - 1), king) ||
                checkKing(game.getPiece(kingCol - 1, kingRow), king) ||
                checkKing(game.getPiece(kingCol + 1, kingRow), king) ||
                checkKing(game.getPiece(kingCol - 1, kingRow + 1), king) ||
                checkKing(game.getPiece(kingCol + 1, kingRow + 1), king) ||
                checkKing(game.getPiece(kingCol, kingRow + 1), king);
    }

    public Piece getKing(boolean colorOfTeam) {
        for (Piece p : game.piecesList) {
            if (p instanceof King && p.colorOfTeam == colorOfTeam) {
                return p;
            }
        }
        return null;
    }

    private boolean checkKnight(Piece p, Piece king, int col, int row) {
        return p != null && !game.isSameTeam(p, king) && p instanceof Knight && !(p.col == col && p.row == row);
    }

    private boolean checkPawn(Piece p, Piece king, int col, int row) {
        return p != null && !game.isSameTeam(p, king) && p instanceof Pawn && !(p.col == col && p.row == row);
    }

    private boolean checkKing(Piece p, Piece king) {
        return p != null && !game.isSameTeam(p, king) && p instanceof King;
    }

    public boolean gameOver(Piece king) {
        for (Piece p : game.piecesList) {
            if (game.isSameTeam(p, king)) {
                game.currentPiece = p == king ? king : null;
                for (int row = 0; row < Constants.ROWS; row++) {
                    for (int col = 0; col < Constants.COLS; col++) {
                        Move move = new Move(game, p, col, row);
                        if (game.isMoveLegal(move)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}

