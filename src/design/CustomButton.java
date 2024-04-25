package design;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class CustomButton extends JButton{

    public CustomButton(String text) {
        super(text);
        setContentAreaFilled(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if(getModel().isPressed()){
            g.setColor(new Color(100,65,30));
        } else {
            g.setColor(new Color(140, 70, 20));
        }
        g.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        g.setColor(new Color(140, 70, 20));
        g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20 ,20);
    }

    @Override
    public boolean contains(int x, int y) {
        return new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 20, 20).contains(x, y);
    }
}
