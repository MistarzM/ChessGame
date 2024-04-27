package pieces;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Piece {

    public int col;
    public int row;

    public int xPosition;
    public int yPosition;

    public boolean colorOfTeam;
    public String pieceName;
    public int value;

    BufferedImage[] sheets;
    {
        sheets = loadImages(new String[] {
                "resources/set1/wP.svg",        //sheets[0] -> white Pawn
                "resources/set1/wR.svg",        //sheets[1] -> white Rock
                "resources/set1/wN.svg",        //sheets[2] -> white Knight
                "resources/set1/wB.svg",        //sheets[3] -> white Bishop
                "resources/set1/wQ.svg",        //sheets[4] -> white Queen
                "resources/set1/wK.svg",        //sheets[5] -> white King
                "resources/set1/bP.svg",        //sheets[6] -> black Pawn
                "resources/set1/bR.svg",        //sheets[7] -> black Rock
                "resources/set1/bN.svg",        //sheets[8] -> black Knight
                "resources/set1/bB.svg",        //sheets[9] -> black Bishop
                "resources/set1/bQ.svg",        //sheets[10] -> black Queen
                "resources/set1/bK.svg",        //sheets[11] -> black King
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

}
