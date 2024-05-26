package main;

import pieces.*;
import utils.Constants;

public class CheckDetector {
    private Game game;

    public CheckDetector(Game game) {
        this.game = game;
    }

    public boolean isKingCheck(Move move) {
        Piece king = getKing(move.piece.colorOfTeam);               // get king piece

        if (king == null) {                                         // if king is not found => throw exception
            throw new IllegalStateException("King not found");
        }

        int kingCol = king.col;
        int kingRow = king.row;

        // if currentPiece == king => update king's position
        if (game.currentPiece != null && game.currentPiece instanceof King) {
            kingCol = move.newCol;
            kingRow = move.newRow;
        }

        // returns a value that tells us whether the king is checked by:
        // rook, bishop, knight, pawn, king
        return isKingCheckedByRook(move, king, kingCol, kingRow) ||
                isKingCheckedByBishop(move, king, kingCol, kingRow) ||
                isKingCheckedByKnight(move, king, kingCol, kingRow) ||
                isKingCheckedByPawn(move, king, kingCol, kingRow) ||
                isKingCheckedByKing(king, kingCol, kingRow);
    }
    // checks whether the king is checked by rooks by checking in all four cardinal directions
    // NORTH, EAST, SOUTH, WEST -> using the isKingCheckedByRookOrQueen method.
    private boolean isKingCheckedByRook(Move move, Piece king, int kingCol, int kingRow) {
        return isKingCheckedByRookOrQueen(move, king, kingCol, kingRow, Direction.NORTH) ||
                isKingCheckedByRookOrQueen(move, king, kingCol, kingRow, Direction.EAST) ||
                isKingCheckedByRookOrQueen(move, king, kingCol, kingRow, Direction.SOUTH) ||
                isKingCheckedByRookOrQueen(move, king, kingCol, kingRow, Direction.WEST);
    }

    // checks whether the king is checked by bishops by checking in all four diagonal directions
    // NORTH_EAST, SOUTH_EAST, SOUTH_WEST, NORTH_WEST -> using the isKingCheckedByBishopOrQueen method.
    private boolean isKingCheckedByBishop(Move move, Piece king, int kingCol, int kingRow) {
        return isKingCheckedByBishopOrQueen(move, king, kingCol, kingRow, Direction.NORTH_EAST) ||
                isKingCheckedByBishopOrQueen(move, king, kingCol, kingRow, Direction.SOUTH_EAST) ||
                isKingCheckedByBishopOrQueen(move, king, kingCol, kingRow, Direction.SOUTH_WEST) ||
                isKingCheckedByBishopOrQueen(move, king, kingCol, kingRow, Direction.NORTH_WEST);
    }

    private boolean isKingCheckedByRookOrQueen(Move move, Piece king, int kingCol, int kingRow, Direction direction) {
        for (int i = 1; i < Constants.ROWS; i++) {       // iterate over the squares in the given direction
            if (kingCol + (i * direction.colValue) == move.newCol && kingRow + (i * direction.rowValue) == move.newRow) {
                break;                                  // if the square is the destination of the move -> break the loop
            }
            Piece piece = game.getPiece(kingCol + (i * direction.colValue), kingRow + (i * direction.rowValue));    // get piece on the square
            if (piece != null && piece != game.currentPiece) {  // if there is a piece (!null) AND this piece is not the piece that just moved
                if (!game.isSameTeam(piece, king) && (piece instanceof Rook || piece instanceof Queen)) {   // if the piece is of the opposite team (rook or queen)
                    return true;        // return true => king is in check
                }
                break;  // if the piece is of the same team => break loop
            }
        }
        return false;   // king not in check
    }

    // checks whether the king is checked by a bishop or queen in a given direction
    private boolean isKingCheckedByBishopOrQueen(Move move, Piece king, int kingCol, int kingRow, Direction direction) {
        for (int i = 1; i < Constants.ROWS; i++) {              // it iterates over the squares in the given direction
            if (kingCol + (i * direction.colValue) == move.newCol && kingRow + (i * direction.rowValue) == move.newRow) {
                break;                                          // if the square is the destination of the move -> break the loop
            }
            Piece piece = game.getPiece(kingCol + (i * direction.colValue), kingRow + (i * direction.rowValue));
            if (piece != null && piece != game.currentPiece) {      // if there is a piece (!null) AND this piece is not the piece that just moved
                if (!game.isSameTeam(piece, king) && (piece instanceof Bishop || piece instanceof Queen)) {     // if the piece is of the opposite team (bishop or queen)
                    return true; //=> return true => king is in check
                }
                break;  // if the piece is of the same team => break loop
            }
        }
        return false;   // if no piece of the opposite team (bishop or queen) is found => return false => king not in check
    }

    private boolean isKingCheckedByKnight(Move move, Piece king, int kingCol, int kingRow) {
        // knightMoves represents all possible moves a knight can make from a given position
        int[][] knightMoves = {
                {-1, -2}, {1, -2}, {2, -1}, {2, 1},
                {1, 2}, {-1, 2}, {-2, 1}, {-2, -1}
        };

        // iterate over all possible knight moves
        for (int[] knightMove : knightMoves) {
            // get the piece on the square that the knight would move to
            Piece piece = game.getPiece(kingCol + knightMove[0], kingRow + knightMove[1]);
            // if there is a knight of the opposite team on one of these squares
            if (piece != null && !game.isSameTeam(piece, king) && piece instanceof Knight && !(piece.col == move.newCol && piece.row == move.newRow)) {
                return true; // =>  king is in check
            }
        }

        return false; // no knight of the opposite team is found => king not in check
    }

    private boolean isKingCheckedByPawn(Move move, Piece king, int kingCol, int kingRow) {
        int colorVal = king.colorOfTeam ? -1 : 1; // colorVal is used to determine the direction of the pawn's movement based on its team

        // get the pieces on the squares where a pawn could check the king
        Piece leftPawn = game.getPiece(kingCol - 1, kingRow + colorVal);
        Piece rightPawn = game.getPiece(kingCol + 1, kingRow + colorVal);

        // if there is a pawn of the opposite team on one of these squares
        if ((leftPawn != null && !game.isSameTeam(leftPawn, king) && leftPawn instanceof Pawn && !(leftPawn.col == move.newCol && leftPawn.row == move.newRow)) ||
                (rightPawn != null && !game.isSameTeam(rightPawn, king) && rightPawn instanceof Pawn && !(rightPawn.col == move.newCol && rightPawn.row == move.newRow))) {
            return true; //  => king is in check
        }

        return false; // no pawn of the opposite team is found => king not in check
    }

    private boolean isKingCheckedByKing(Piece king, int kingCol, int kingRow) {
        Direction[] directions = Direction.values(); // get all possible directions

        // iterate over all possible directions from Direction.java
        for (Direction direction : directions) {
            // get the piece on the square in the given direction
            Piece piece = game.getPiece(kingCol + direction.colValue, kingRow + direction.rowValue);
            // if there is a king of the opposite team on one of these squares
            if (piece != null && !game.isSameTeam(piece, king) && piece instanceof King) {
                return true; // => king is in check
            }
        }

        return false; // no king of the opposite team is found => king not in check
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
        for (Piece p : game.piecesList) {   // iterate over all pieces in the game
            if (game.isSameTeam(p, king)) {     // if the piece is on the same team as the king
                game.currentPiece = p == king ? king : null;    // if the piece is the king, set it as the current piece
                for (int row = 0; row < Constants.ROWS; row++) {        // iterate over all rows on the board
                    for (int col = 0; col < Constants.COLS; col++) {    // iterate over all cols on the board
                        Move move = new Move(game, p, col, row);    // create a move for the current piece to the current square
                        if (game.isMoveLegal(move)) {   // if the move is legal, the game is not over
                            return false;
                        }
                    }
                }
            }
        }
        return true;    // if no legal moves are found for any piece on the king's team, the game is over
    }
}

