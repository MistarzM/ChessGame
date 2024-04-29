package main;

import pieces.Pawn;
import pieces.Rook;
import pieces.Knight;
import pieces.Bishop;
import pieces.Queen;
import pieces.King;
import pieces.Piece;

import design.CustomButton;

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

    private Menu menu;

    public int enPassantTile = -1;

    // timer
    private GameTimer gameTimer;
    private JPanel panel;

    public JPanel getPanel() {
        return panel;
    }

    public Board(Menu menu){
        this.menu = menu;

        panel = new JPanel(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());

        topPanel.setBackground(new Color(210, 180, 140));

        CustomButton backButton = new CustomButton("Back");
        backButton.setPreferredSize(new Dimension(Board.tileSize, Board.tileSize/2));
        backButton.setFont(new Font("Arial", Font.BOLD, Board.tileSize/5));
        backButton.addActionListener(e -> {
            menu.showMenu();
        });

        topPanel.add(backButton, BorderLayout.WEST);

        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        gameTimer = new GameTimer();
        topPanel.add(gameTimer.getTimerLabel(), BorderLayout.EAST);
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(this, BorderLayout.CENTER);
        gameTimer.start();

        this.setPreferredSize(new Dimension(cols * tileSize, rows * tileSize));

        this.addMouseListener(event);
        this.addMouseMotionListener(event);

        addPieces();
    }

    public int getTileNumber(int col, int row){
        return row * rows + col;
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

        if(move.piece.pieceName.equals("Pawn")){
            performPawnMove(move);
        }else {
            move.piece.col = move.newCol;
            move.piece.row = move.newRow;

            move.piece.xPosition = move.newCol * tileSize;
            move.piece.yPosition = move.newRow * tileSize;

            if (move.piece instanceof Pawn) {                   // if move executed by pawn, pawn.firstMove = false;
                ((Pawn) move.piece).setFirstMove(false);
            }


            capture(move.capture);
        }
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

        move.piece.col = move.newCol;
        move.piece.row = move.newRow;

        move.piece.xPosition = move.newCol * tileSize;
        move.piece.yPosition = move.newRow * tileSize;

        if (move.piece instanceof Pawn) {                   // if move executed by pawn, pawn.firstMove = false;
            ((Pawn) move.piece).setFirstMove(false);
        }


        capture(move.capture);
    }

    public void promotePawn(Move move){
        ImageIcon queenPromotion = new ImageIcon(move.piece.getSheets()[move.piece.colorOfTeam ? 4 : 10]);
        ImageIcon rookPromotion = new ImageIcon(move.piece.getSheets()[move.piece.colorOfTeam ? 1 : 7]);
        ImageIcon bishopPromotion = new ImageIcon(move.piece.getSheets()[move.piece.colorOfTeam ? 3 : 9]);
        ImageIcon knightPromotion = new ImageIcon(move.piece.getSheets()[move.piece.colorOfTeam ? 2 : 8]);
        Object[] optionToChoose = new Object[] {queenPromotion, rookPromotion, bishopPromotion, knightPromotion};
        int chosenOption = JOptionPane.showOptionDialog(null, "Choose a piece for promotion", "Pawn Promotion",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, optionToChoose, optionToChoose[0]);
        switch(chosenOption){
            case 0:
                piecesList.add(new Queen(this, move.piece.colorOfTeam, move.newCol, move.newRow));
                break;
            case 1:
                piecesList.add(new Rook(this, move.piece.colorOfTeam, move.newCol, move.newRow));
                break;
            case 2:
                piecesList.add(new Bishop(this, move.piece.colorOfTeam, move.newCol, move.newRow));
                break;
            case 3:
                piecesList.add(new Knight(this, move.piece.colorOfTeam, move.newCol, move.newRow));
                break;
            default:
                piecesList.add(new Queen(this, move.piece.colorOfTeam, move.newCol, move.newRow));
                break;
        }
        capture(move.piece);
    }

    public boolean isMoveLegal(Move move){

        if(isSameTeam(move.piece, move.capture)){
            return false;
        }
        if(!move.piece.isMovementLegal(move.newCol, move.newRow)){
            return false;
        }
        if(move.piece.moveOverlapPiece(move.newCol, move.newRow)){
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

        if(currentPiece != null) {
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    if (isMoveLegal(new Move(this, currentPiece, c, r)) && getPiece(c, r) == null) {
                        graphics.setColor(new Color(70, 180, 60, 200));
                        graphics.fillOval(c * tileSize + 3 * (tileSize/8), r * tileSize + 3 * (tileSize/8), tileSize/4, tileSize/4);  // col * tileSize + tileSize/2 - tileSize/8 (cause width = tileSize/4)
                    } else if(isMoveLegal(new Move(this, currentPiece, c, r)) && getPiece(c, r) != null){
                        graphics.setColor(new Color(70, 180, 60, 200));
                        graphics.setStroke(new BasicStroke(4));
                        graphics.drawOval(c * tileSize, r * tileSize, tileSize, tileSize);
                    }
                }
            }
        }

        for(Piece p : piecesList){
            p.paint(graphics);
        }

    }
}
