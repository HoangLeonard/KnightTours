package Illu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.security.InvalidParameterException;
import java.util.ArrayList;

public class KnightToursPath extends JFrame {
    private int boardSizeX;
    private int boardSizeY;
    private int cellSize = 50; // Kích thước ô vuông
    JPanel boardPanel;
    public int[][] board;
    private ArrayList<Line> lines;


    public KnightToursPath(int x, int y, int[][] board) {
        this.boardSizeX = x;
        this.boardSizeY = y;
        lines = new ArrayList<>();
        initComponents();
        this.board = board;
    }

    public KnightToursPath(int x, int y) {
        this.boardSizeX = x;
        this.boardSizeY = y;
        lines = new ArrayList<>();
        initComponents();
    }
    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(boardSizeX * cellSize, boardSizeY * cellSize);
        setTitle("Chess Board");

        this.boardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            Font font = new Font("Arial", Font.BOLD, 12);
            g2d.setFont(font);

            for (int row = 0; row < boardSizeX; row++) {
                for (int col = 0; col < boardSizeY; col++) {
                    int x = col * cellSize;
                    int y = row * cellSize;

                    if ((row + col) % 2 == 0) {
                        g2d.setColor(new Color(230, 230, 250)); // Màu lavanda nhạt
                    } else {
                        g2d.setColor(new Color(135, 206, 235)); // Màu xanh dương nhạt
                    }

                    g2d.fillRect(x, y, cellSize, cellSize);

                    if (board != null) {
                        g2d.setColor(Color.black);
                        g2d.drawString(String.valueOf(board[row][col]), x + cellSize / 3, y + cellSize / 2);
                    }
                }
            }

            g2d.setColor(Color.red);
            g2d.setStroke(new BasicStroke(5));
            for (Line i : lines) {
                g2d.drawLine(
                (int) i.getY1()*cellSize + cellSize/2,
                (int) i.getX1()*cellSize + cellSize/2,
                (int) i.getY2()*cellSize + cellSize/2,
                (int) i.getX2()*cellSize + cellSize/2
                );
            }
            }
        };
        this.boardPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                windowComponentResized(e);
            }
        });

        getContentPane().add(boardPanel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void windowComponentResized(ComponentEvent e) {
        int new_size = this.getHeight()/(boardSizeX+1);
        this.cellSize = new_size;
        boardPanel.repaint();
    }

    public void addLine(int x1, int y1, int x2, int y2) {
        boolean x1y1Valid = x1 >= 0 && y1 >= 0 && x1 < boardSizeX && y1 < boardSizeY;
        boolean x2y2Valid = x2 >= 0 && y2 >= 0 && x2 < boardSizeX && y2 < boardSizeY;
        if (x1y1Valid && x2y2Valid) {
            lines.add(new Line(x1, y1, x2, y2));
            boardPanel.repaint();
        } else throw new InvalidParameterException();
    }

    public void remove(int x1, int y1, int x2, int y2) {
        Line l1 = new Line(x1, y1, x2, y2);
        lines.remove(l1);
        boardPanel.repaint();
    }

    public int getNumLines() {
        if (lines != null) {
            return lines.size();
        } else return 0;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            KnightToursPath knightToursPath = new KnightToursPath(8, 8);
//            knightToursPath.move(0, 0, 4, 4); // Ví dụ: Vẽ đường thẳng từ ô (0, 0) đến ô (4, 4)
            knightToursPath.addLine(0,0, 1,2);
            knightToursPath.addLine(1, 2, 4,5);
//            knightToursPath.addLine(4,5,7,2);
//            knightToursPath.addLine(7,2,0,0);

        });
    }


}
