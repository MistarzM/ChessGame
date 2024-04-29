package pieces;

import main.Board;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Piece {

    public int col;
    public int row;

    public int xPosition;
    public int yPosition;

    public boolean colorOfTeam;         // true -> white : false -> black
    public String pieceName;
    public int value;

    BufferedImage[] sheets;
    {
        sheets = loadImages(new String[] {
                "set1/wP.png",        //sheets[0] -> white Pawn
                "set1/wR.png",        //sheets[1] -> white Rock
                "set1/wN.png",        //sheets[2] -> white Knight
                "set1/wB.png",        //sheets[3] -> white Bishop
                "set1/wQ.png",        //sheets[4] -> white Queen
                "set1/wK.png",        //sheets[5] -> white King
                "set1/bP.png",        //sheets[6] -> black Pawn
                "set1/bR.png",        //sheets[7] -> black Rock
                "set1/bN.png",        //sheets[8] -> black Knight
                "set1/bB.png",        //sheets[9] -> black Bishop
                "set1/bQ.png",        //sheets[10] -> black Queen
                "set1/bK.png",        //sheets[11] -> black King
        });
    }

    private BufferedImage[] loadImages(String[] resourcePaths){
        BufferedImage[] images = new BufferedImage[resourcePaths.length];
        for (int i = 0; i < resourcePaths.length; i++) {
            try {
                images[i] = ImageIO.read(ClassLoader.getSystemResourceAsStream(resourcePaths[i]));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return images;
    }

    public BufferedImage[] getSheets(){             // needed for promotion
        return this.sheets;
    }

    Image sprite;

    Board board;

    public Piece(Board board) {
        this.board = board;
    }

    public void paint(Graphics2D graphics2D) {

        graphics2D.drawImage(sprite, xPosition, yPosition, null);
    }

    //movement
    public boolean isMovementLegal(int col, int row){
        return true;
    }
    public boolean moveOverlapPiece(int col, int row){
        return false;
    }
}
