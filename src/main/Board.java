package main;

import pieces.Pawn;
import pieces.Rock;
import pieces.Knight;
import pieces.Bishop;
import pieces.Queen;
import pieces.King;
import pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Board extends JPanel {

    public static int tileSize = 80;
    public static Color color1 = new Color(230,190,110);
    public static Color color2 = new Color(180,140,70);
    int cols = 8;
    int rows = 8;

    ArrayList<Piece> piecesList = new ArrayList<>();

    public Piece currentPiece;
    Event event = new Event(this);

    public Board(){

        this.setPreferredSize(new Dimension(cols * tileSize, rows * tileSize));

        this.addMouseListener(event);
        this.addMouseMotionListener(event);

        addPieces();
    }

    public void addPieces(){
        piecesList.add(new Pawn(this, false, 0, 1));
        piecesList.add(new Pawn(this, false, 1, 1));
        piecesList.add(new Pawn(this, false, 2, 1));
        piecesList.add(new Pawn(this, false, 3, 1));
        piecesList.add(new Pawn(this, false, 4, 1));
        piecesList.add(new Pawn(this, false, 5, 1));
        piecesList.add(new Pawn(this, false, 6, 1));
        piecesList.add(new Pawn(this, false, 7, 1));

        piecesList.add(new Pawn(this, true, 0, 6));
        piecesList.add(new Pawn(this, true, 1, 6));
        piecesList.add(new Pawn(this, true, 2, 6));
        piecesList.add(new Pawn(this, true, 3, 6));
        piecesList.add(new Pawn(this, true, 4, 6));
        piecesList.add(new Pawn(this, true, 5, 6));
        piecesList.add(new Pawn(this, true, 6, 6));
        piecesList.add(new Pawn(this, true, 7, 6));

        piecesList.add(new Rock(this, false, 0, 0));
        piecesList.add(new Knight(this, false, 1, 0));
        piecesList.add(new Bishop(this, false, 2, 0));
        piecesList.add(new Queen(this, false, 3, 0));
        piecesList.add(new King(this, false, 4, 0));
        piecesList.add(new Bishop(this, false, 5, 0));
        piecesList.add(new Knight(this, false, 6, 0));
        piecesList.add(new Rock(this, false, 7, 0));

        piecesList.add(new Rock(this, true, 0, 7));
        piecesList.add(new Knight(this, true, 1, 7));
        piecesList.add(new Bishop(this, true, 2, 7));
        piecesList.add(new Queen(this, true, 3, 7));
        piecesList.add(new King(this, true, 4, 7));
        piecesList.add(new Bishop(this, true, 5, 7));
        piecesList.add(new Knight(this, true, 6, 7));
        piecesList.add(new Rock(this, true, 7, 7));
    }

    public Piece getPiece(int col, int row) {

        for(Piece p : piecesList){
            if (p.col == col && p.row == row){
                return p;
            }
        }

        return null;
    }

    public void capture(Move move){
        piecesList.remove(move.capture);
    }

    public void performMove(Move move){
        move.piece.col = move.newCol;
        move.piece.row = move.newRow;

        move.piece.xPosition = move.newCol * tileSize;
        move.piece.yPosition = move.newRow * tileSize;

        capture(move);
    }

    public boolean isMoveLegal(Move move){

        if(isSameTeam(move.piece, move.capture)){
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


    public void paintComponent(Graphics g){
        Graphics2D graphics = (Graphics2D) g;

        for(int row = 0; row < rows; row++){
            for(int col = 0; col < cols; col++){
                graphics.setColor((col + row) % 2 == 0 ? color1 : color2);
                graphics.fillRect(col * tileSize, row * tileSize, tileSize, tileSize);
            }
        }

        for(Piece p : piecesList){
            p.paint(graphics);
        }

    }
}
