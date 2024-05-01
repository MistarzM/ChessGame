package main;

import javax.swing.*;
import java.awt.*;

public class Result extends JFrame {
    private JLabel resultLabel;

    public Result(String result) {
        super("Game over");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(Board.color1);

        resultLabel = new JLabel(result);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Custom font
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalGlue());
        panel.add(resultLabel);
        panel.add(Box.createVerticalGlue());
        add(panel);

        FontMetrics metrics = getFontMetrics(resultLabel.getFont());
        int width = metrics.stringWidth(result);
        int height = metrics.getHeight();

        this.setSize(width + 100, height + 100);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}