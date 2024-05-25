package main;

import pieces.Piece;

import design.CustomButton;
import utils.Constants;

import javax.swing.JPanel;
import javax.swing.BorderFactory;

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;

public class Board extends JPanel {

    public static int TILE_SIZE = 80;
    public static Color color1 = new Color(230,190,110);
    public static Color color2 = new Color(180,140,70);

    private Event event;
    private Menu menu;
    private GameTimer gameTimer;
    private JPanel panel;
    private Game game;


    public Board(Menu menu){
        this.menu = menu;
        this.game = new Game(this);

        panel = new JPanel(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());

        topPanel.setBackground(new Color(210, 180, 140));

        CustomButton backButton = new CustomButton("Back");
        backButton.setPreferredSize(new Dimension(Board.TILE_SIZE, Board.TILE_SIZE/2));
        backButton.setFont(new Font("Arial", Font.BOLD, Board.TILE_SIZE/5));
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

        this.setPreferredSize(new Dimension(Constants.COLS * TILE_SIZE, Constants.ROWS * TILE_SIZE));
        event = new Event(game, this);
        this.addMouseListener(event);
        this.addMouseMotionListener(event);
    }



    public void paintComponent(Graphics g){
        Graphics2D graphics = (Graphics2D) g;

        for(int row = 0; row < Constants.ROWS; row++){
            for(int col = 0; col < Constants.COLS; col++){
                graphics.setColor((col + row) % 2 == 0 ? color1 : color2);
                graphics.fillRect(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }

        if(game.currentPiece != null) {
            for (int r = 0; r < Constants.ROWS; r++) {
                for (int c = 0; c < Constants.COLS; c++) {
                    if (game.isMoveLegal(new Move(game , game.currentPiece, c, r)) && game.getPiece(c, r) == null) {
                        graphics.setColor(new Color(70, 180, 60, 200));
                        graphics.fillOval(c * TILE_SIZE+ 3 * (TILE_SIZE/8), r * TILE_SIZE+ 3 * (TILE_SIZE/8), TILE_SIZE/4, TILE_SIZE/4);  // col * tileSize + tileSize/2 - tileSize/8 (cause width = tileSize/4)
                    } else if(game.isMoveLegal(new Move(game, game.currentPiece, c, r)) && game.getPiece(c, r) != null){
                        graphics.setColor(new Color(70, 180, 60, 200));
                        graphics.setStroke(new BasicStroke(4));
                        graphics.drawOval(c * TILE_SIZE, r * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    }
                }
            }
        }

        for(Piece p : game.piecesList){
            p.paint(graphics);
        }

    }

    public JPanel getPanel() {
        return panel;
    }
}
