package Illu;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @author minhhn
 */
public class KnightToursIllustration extends JFrame {

    private JFrame window;
    private Board board;
    private JScrollPane move;
    public class Board extends JPanel {
        static Color lightColor = new Color(240, 217, 181);  // Màu của ô sáng
        static Color darkColor = new Color(181, 136, 99);    // Màu của ô tối
        static Color overlayColor = new Color(27, 234, 27, 98); // Màu overlay
        private Cell[][] cells;
        public class Cell extends JPanel {
            private static Image knightImage = Toolkit.getDefaultToolkit().getImage("src/Illu/Icon/knight.png");
            private int number = 0;
            private boolean isDisplayNumber = false;
            private boolean isDisplayKnight = false;
            private boolean overlayEnabled = false;
            private Color overlay;

            public Cell() {
            }

            public Cell(int cellSize, int number) {
                this.number = number;
                setPreferredSize(new Dimension(cellSize, cellSize));
            }

            public void setNumber(int number) {
                this.number = number;
            }

            public void displayOverlay(boolean enabled) {
                this.overlayEnabled = enabled;
                repaint();
            }

            // phương thức đặt overlay có màu sắc tùy chỉnh cho Cells
            // Red - Green - Blue - Opacity (độ trong suốt)
            public void displayOverlay(int R, int G, int B, int O, boolean enabled){
                this.overlay = new Color(R, G, B, O);
                this.overlayEnabled = enabled;
                repaint();
            }

            public void displayImage(boolean b) {
                isDisplayKnight = b;
                repaint();
            }

            public void displayNumber(boolean b) {
                isDisplayNumber = b;
                repaint();
            }

            public void setProperties(boolean number, boolean image, boolean overlay) {
                isDisplayNumber = number;
                isDisplayKnight = image;
                overlayEnabled = overlay;
                repaint();
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                if (isDisplayNumber) {
                    g2d.setColor(Color.red);
                    g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
                    FontMetrics fm = g2d.getFontMetrics();

                    // In số
                    String numberText = String.valueOf(number);
                    int textWidth = fm.stringWidth(numberText);
                    int textHeight = fm.getHeight();
                    int x = (getWidth() - textWidth) / 2;
                    int y = (getHeight() - textHeight) / 2 + fm.getAscent();
                    g2d.drawString(numberText, x, y);
                }
                if (isDisplayKnight) {
                    g2d.drawImage(knightImage, 0, 0, getWidth(), getHeight(), null);
                }
                if (overlayEnabled) {
                    if (this.overlay == null) {
                        g.setColor(Board.overlayColor); // Màu xanh nhạt với độ trong suốt 50%
                    } else {
                        g.setColor(this.overlay);
                    }
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
                g2d.dispose();
            }

            public int getNumber() {
                return this.number;
            }
        }

        private int numCellsX;
        private int numCellsY;
        public Board(int numCellsX, int numCellsY) {
            this.numCellsX = numCellsX;
            this.numCellsY = numCellsY;
            this.setLayout(new GridLayout(numCellsX, numCellsY));
            this.setOpaque(true);
            this.initComponents();
        }

        private void initComponents() {
            this.cells = new Cell[numCellsX][numCellsY];
            int CELL_SIZE = window.getHeight() / numCellsX;
            for (int row = 0; row < numCellsX; row++) {
                for (int col = 0; col < numCellsY; col++) {
                    Cell cell = new Cell(CELL_SIZE, -1);
                    cell.setBackground((row + col) % 2 == 0 ? lightColor : darkColor);
                    this.add(cell);
                    cells[row][col] = cell;
                }
            }
        }

        public void boardComponentResized(ComponentEvent e) {
            int new_size = window.getHeight() / (numCellsX);
            for (Cell[] cs : cells)
                for (Cell c : cs)
                    if (c != null) c.setPreferredSize(new Dimension(new_size, new_size));
            board.revalidate();
            board.repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Vẽ đối tượng ban đầu các Cells
            for (int i=0; i<numCellsX; i++) {
                for (int j=0; j<numCellsY; j++) {
                    cells[i][j].repaint();
                }
            }
            g.dispose();
        }

        public Cell[][] getCells() {
            return cells;
        }

        public int getNumCellsX() {
            return numCellsX;
        }
        public int getNumCellsY() {
            return numCellsY;
        }
    }

    public KnightToursIllustration(int numCellsX, int numCellsY) {
        initComponents(numCellsX, numCellsY);
    }

    private void initComponents(int numCellsX, int numCellsY) {
        window = new JFrame();

        //______init window properties_____
        {
            window.getContentPane().setBackground(new Color(46, 204, 113));
            window.setMinimumSize(new Dimension(500, 500));
            window.setPreferredSize(new Dimension(500, 500));
            window.setMaximumSize(new Dimension(1366, 694));
            window.setTitle("Knight Tour");
            window.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    windowComponentResized(e);
                }
            });
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            var windowContentPane = window.getContentPane();
            windowContentPane.setLayout(new FlowLayout());
            //______init window components: board
            {
                board = new Board(numCellsX, numCellsY);
                windowContentPane.add(board);
            }
            //______init window components: move
            {
                move = new JScrollPane();
                windowContentPane.add(move);
            }
        }
        window.pack();
        window.setLocationRelativeTo(window.getOwner());
        window.setVisible(true);
    }

    private void windowComponentResized(ComponentEvent e) {
        board.boardComponentResized(e);
    }

    public JFrame getWindow() {
        return window;
    }

    public void setWindow(JFrame window) {
        this.window = window;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public JScrollPane getMove() {
        return move;
    }

    public void setMove(JScrollPane move) {
        this.move = move;
    }
}

