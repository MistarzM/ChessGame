package main;

import javax.swing.*;

public class Result extends JFrame {
    private JLabel resultLabel;

    public Result(String result) {
        super("Game over");
        this.setSize(300, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        resultLabel = new JLabel(result, SwingConstants.CENTER);
        add(resultLabel);

        setVisible(true);
    }
}
