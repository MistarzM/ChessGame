package main;

import javax.swing.*;
import java.awt.*;

public class GameTimer {

    private JLabel timerLabel;
    private Timer timer;
    private int timeInSeconds;
    private int timeInMinutes;

    public GameTimer(){
        timerLabel = new JLabel();
        timerLabel.setHorizontalAlignment(JLabel.RIGHT);
        timerLabel.setVerticalAlignment(JLabel.TOP);
        timerLabel.setFont(new Font("Arial", Font.BOLD,  Board.TILE_SIZE/4));
        timerLabel.setBackground(new Color(210, 180, 140));
        timerLabel.setOpaque(true);
        timeInSeconds = 0;
        timeInMinutes = 0;
        timer = new Timer(1000, e -> {
            timeInSeconds++;
            if(timeInSeconds == 60){
                timeInSeconds = 0;
                timeInMinutes++;
            }
            timerLabel.setText(String.format("%02d", timeInMinutes) + ":" + String.format("%02d", timeInSeconds));
        });

    }

    public void start() {
        timer.start();
    }

    public JLabel getTimerLabel(){
        return timerLabel;
    }
}
