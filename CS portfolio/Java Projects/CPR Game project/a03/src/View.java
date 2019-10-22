import java.awt.*;

import javax.swing.*;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.rmi.RemoteException;



/*
CSCI-C322 Team orange
10/31/2018
 */

public class View extends JComponent implements KeyListener {
    private JFrame frame;
    private int id;
    private Player[] players;
    private Cell[][] cells;
    public int dx;
    public int dy;
    private Boolean drawCells = true;

    public View(Cell[][] cells, Player[] players, int id) {
        super();
        this.cells = cells;
        this.id = id;
        this.players = players;
        frame = new JFrame("Game of Life"); //need what you put inside for displaying the view

        frame.add(this);
        frame.addKeyListener(this);

        frame.setSize(256 * 3, 256 * 3);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    public void updateView(Cell[][] field, Player[] players) {
        cells = field;
        this.players = players;
        this.repaint();
    }

    public void paintComponent(Graphics g) {
        drawCells = true;
        this.draw(g);
    }

    public void draw(Graphics g) {

        if(drawCells) {

            for (int r = 0; r < cells.length; r++) {
                for (int c = 0; c < cells[r].length; c++) {
                    if (cells[r][c].getResourceNum() > 0) {

                        Color color = Color.GREEN;
                        for (int i = 0; i < cells[r][c].getResourceNum(); i++) {
                            color = color.darker();
                        }

                        g.setColor(color);
                        g.fillRect((r * 3), (c * 3), 3, 3);
                    }
                }
            }
        }

        for (Player p : players) {
            if (p.id == id) {
                g.setColor(Color.RED);
                g.fillRect((p.getX() + dx) * 3, (p.getY() + dy) * 3, 3, 3);
            } else {
                g.setColor(Color.BLUE);
                g.fillRect(p.getX() * 3, p.getY() * 3, 3, 3);
            }
        }

        if(g instanceof Graphics2D)
        {
            Graphics2D g2 = (Graphics2D)g;
            g2.setColor(Color.RED);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2.drawString("Resources collected: " + players[id].getResourcesCollected(),10,15);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int dx = 0, dy = 0;

        if(e.getKeyCode() == KeyEvent.VK_UP){
            dy = -1;
        }else if (e.getKeyCode() == KeyEvent.VK_DOWN){
            dy = 1;
        }else if(e.getKeyCode() == KeyEvent.VK_LEFT){
            dx = -1;
        }else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            dx = 1;
        }

        for(Player p : players) {
            if (p.getX() == players[id].getX() + dx) {
                if (p.getY() == players[id].getY() + dy) {
                    dx = 0;
                    dy = 0;
                }
            }
        }

        this.dx += dx;
        this.dy += dy;

        drawCells = false;
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}

