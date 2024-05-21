package utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class LoadAndSave {

    public static final String BLACK_PAWN = "/set1/bP.png";
    public static final String BLACK_ROOK = "/set1/bR.png";
    public static final String BLACK_KNIGHT = "/set1/bN.png";
    public static final String BLACK_BISHOP = "/set1/bB.png";
    public static final String BLACK_QUEEN = "/set1/bQ.png";
    public static final String BLACK_KING = "/set1/bK.png";

    public static final String WHITE_PAWN = "/set1/wP.png";
    public static final String WHITE_ROOK = "/set1/wR.png";
    public static final String WHITE_KNIGHT = "/set1/wN.png";
    public static final String WHITE_BISHOP = "/set1/wB.png";
    public static final String WHITE_QUEEN = "/set1/wQ.png";
    public static final String WHITE_KING = "/set1/wK.png";

    public static BufferedImage GetImg(String path){
        BufferedImage img = null;
        InputStream is = LoadAndSave.class.getResourceAsStream(path);

        try{
            img = ImageIO.read(is);
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        return img;
    }
}
