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
        return isKingCheckedByRookOrQueen(move, king, kingCol, kingRow, Direction.NORTH) ||
                isKingCheckedByRookOrQueen(move, king, kingCol, kingRow, Direction.EAST) ||
                isKingCheckedByRookOrQueen(move, king, kingCol, kingRow, Direction.SOUTH) ||
                isKingCheckedByRookOrQueen(move, king, kingCol, kingRow, Direction.WEST);
    }

    private boolean isKingCheckedByBishop(Move move, Piece king, int kingCol, int kingRow) {
        return isKingCheckedByBishopOrQueen(move, king, kingCol, kingRow, Direction.NORTH_EAST) ||
                isKingCheckedByBishopOrQueen(move, king, kingCol, kingRow, Direction.SOUTH_EAST) ||
                isKingCheckedByBishopOrQueen(move, king, kingCol, kingRow, Direction.SOUTH_WEST) ||
                isKingCheckedByBishopOrQueen(move, king, kingCol, kingRow, Direction.NORTH_WEST);
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
        int[][] knightMoves = {
                {-1, -2}, {1, -2}, {2, -1}, {2, 1},
                {1, 2}, {-1, 2}, {-2, 1}, {-2, -1}
        };

        for (int[] knightMove : knightMoves) {
            Piece piece = game.getPiece(kingCol + knightMove[0], kingRow + knightMove[1]);
            if (piece != null && !game.isSameTeam(piece, king) && piece instanceof Knight && !(piece.col == move.newCol && piece.row == move.newRow)) {
                return true;
            }
        }

        return false;
    }

    private boolean isKingCheckedByPawn(Move move, Piece king, int kingCol, int kingRow) {
        int colorVal = king.colorOfTeam ? -1 : 1;
        Piece leftPawn = game.getPiece(kingCol - 1, kingRow + colorVal);
        Piece rightPawn = game.getPiece(kingCol + 1, kingRow + colorVal);

        if ((leftPawn != null && !game.isSameTeam(leftPawn, king) && leftPawn instanceof Pawn && !(leftPawn.col == move.newCol && leftPawn.row == move.newRow)) ||
                (rightPawn != null && !game.isSameTeam(rightPawn, king) && rightPawn instanceof Pawn && !(rightPawn.col == move.newCol && rightPawn.row == move.newRow))) {
            return true;
        }

        return false;
    }

    private boolean isKingCheckedByKing(Piece king, int kingCol, int kingRow) {
        Direction[] directions = Direction.values();

        for (Direction direction : directions) {
            Piece piece = game.getPiece(kingCol + direction.colValue, kingRow + direction.rowValue);
            if (piece != null && !game.isSameTeam(piece, king) && piece instanceof King) {
                return true;
            }
        }

        return false;
    }

    public Piece getKing(boolean colorOfTeam) {
        for (Piece p : game.piecesList) {
            if (p instanceof King && p.colorOfTeam == colorOfTeam) {
                return p;
            }
        }
        return null;
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

