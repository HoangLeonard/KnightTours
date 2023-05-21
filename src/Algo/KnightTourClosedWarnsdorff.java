package Algo;
import java.util.concurrent.ThreadLocalRandom;

public class KnightTourClosedWarnsdorff{
    public int boardSizeX;
    public int boardSizeY;
    public int[][] board;
    private boolean display = false;
    public int cx[] = {1, 1, 2, 2, -1, -1, -2, -2};
    public int cy[] = {2, -2, 1, -1, 2, -2, 1, -1};
    public int move = 1;
    public Illu.KnightToursPath path;

    public KnightTourClosedWarnsdorff(int boardSizeX, int boardSizeY, boolean display) {
        this.boardSizeX = boardSizeX;
        this.boardSizeY = boardSizeY;
        this.display = display;
        this.board = new int[boardSizeX][boardSizeY];
    }

    boolean isValidMove(int x, int y) {
        return ((x >= 0 && y >= 0) &&
                (x < boardSizeX && y < boardSizeY));
    }

    boolean isEmpty(int x, int y) {
        return (isValidMove(x, y)) && (board[x][y] < 0);
    }

    int getDegree(int x, int y) {
        int count = 0;
        for (int i = 0; i < 8; ++i)
            if (isEmpty((x + cx[i]), (y + cy[i])))
                count++;
        return count;
    }

    void printBoard() {
        this.path = new Illu.KnightToursPath(boardSizeX, boardSizeY, board);
        int sx = -1;
        int sy = -1;
        for (int i = 0; i < boardSizeX; ++i) {
            for (int j = 0; j < boardSizeY; ++j) {
                if (board[i][j] == 1) {
                    sx = i;
                    sy = j;
                }
                System.out.printf("%d\t", board[i][j]);
            }
            System.out.printf("\n");
        }

        int length = boardSizeX * boardSizeY;
        while (path.getNumLines() - length % 2 <= length) {
            for (int i = 0; i < 8; i++) {
                int tmp_x = sx + cx[i];
                int tmp_y = sy + cy[i];
                if (isValidMove(tmp_x, tmp_y)) {
                    if (board[tmp_x][tmp_y] == board[sx][sy] + 1) {
                        path.addLine(sx, sy, tmp_x, tmp_y);
                        sx = tmp_x;
                        sy = tmp_y;
                    }

                    if (board[sx][sy] == length - length % 2 && board[tmp_x][tmp_y] == 1) {
                        path.addLine(sx, sy, tmp_x, tmp_y);
                    }
                }
            }
        }
    }

    boolean neighbour(int x, int y, int xx, int yy) {
        for (int i = 0; i < 8; ++i)
            if (((x + cx[i]) == xx) && ((y + cy[i]) == yy))
                return true;

        return false;
    }

    // Picks next point using Warnsdorff's heuristic.
    // Returns false if it is not possible to pick
    // next point.
    Cell nextMove(Cell cell) {
        int min_deg_idx = -1, c, min_deg = (8 + 1), nx, ny;

        // Try all N adjacent of (*x, *y) starting
        // from a random adjacent. Find the adjacent
        // with minimum degree.
        int start = ThreadLocalRandom.current().nextInt(1000) % 8;
        for (int count = 0; count < 8; ++count)
        {
            int i = (start + count) % 8;
            nx = cell.x + cx[i];
            ny = cell.y + cy[i];
            if (isEmpty(nx, ny) && (c = getDegree(nx, ny)) < min_deg) {
                min_deg_idx = i;
                min_deg = c;
            }
        }

        // IF we could not find a next cell
        if (min_deg_idx == -1)
            return null;

        // Store coordinates of next point
        nx = cell.x + cx[min_deg_idx];
        ny = cell.y + cy[min_deg_idx];

        // Mark next move
        board[nx][ny] = board[cell.x][cell.y] + 1;

        // Update next point
        cell.x = nx;
        cell.y = ny;

        return cell;
    }

    // Picks next point using Warnsdorff's heuristic.
    // Returns false if it is not possible to pick
    // next point.
    Cell nextMove(Cell cell, int non_x, int non_y) {
        int min_deg_idx = -1, c, min_deg = (8 + 1), nx, ny;

        // Try all N adjacent of (*x, *y) starting
        // from a random adjacent. Find the adjacent
        // with minimum degree.
        int start = ThreadLocalRandom.current().nextInt(1000) % 8;
        for (int count = 0; count < 8; ++count) {
            int i = (start + count) % 8;
            nx = cell.x + cx[i];
            ny = cell.y + cy[i];
            if (!(non_x == nx && non_y == ny)) {
                if (isEmpty(nx, ny) && (c = getDegree(nx, ny)) < min_deg) {
                    min_deg_idx = i;
                    min_deg = c;
                }
            }
        }

        // IF we could not find a next cell
        if (min_deg_idx == -1)
            return null;

        // Store coordinates of next point
        nx = cell.x + cx[min_deg_idx];
        ny = cell.y + cy[min_deg_idx];

        // Mark next move
        board[nx][ny] = board[cell.x][cell.y] + 1;

        // Update next point
        cell.x = nx;
        cell.y = ny;

        return cell;
    }

    boolean findClosedTour(int sx, int sy) {
        // Filling up the chessboard matrix with -1's
        for (int i = 0; i < boardSizeX; i++) {
            for (int j = 0; j < boardSizeY; j++) {
                board[i][j] = -1;
            }
        }

        Cell cell = new Cell(sx, sy);
        board[cell.x][cell.y] = 1;

        // Keep picking next points using
        // Warnsdorff's heuristic
        Cell ret = null;
        for (int i = 0; i < boardSizeX * boardSizeY - 1; ++i) {
            ret = nextMove(cell);
            if (ret == null)
                return false;
        }

        // Check if tour is closed (Can end
        // at starting point)
        if (!neighbour(ret.x, ret.y, sx, sy))
            return false;

        if (display) printBoard();
        return true;
    }

    boolean findClosedTour(int sx, int sy, int non_x, int non_y) {
        // Filling up the chessboard matrix with -1's
        for (int i = 0; i < boardSizeX; i++) {
            for (int j = 0; j < boardSizeY; j++) {
                board[i][j] = -1;
            }
        }

        if (non_x == sx && non_y == sy) throw new IllegalArgumentException("sx: " + sx + " non_x: " + non_x + " sy: " + sy + " non_y: " + non_y);

        Cell cell = new Cell(sx, sy);
        board[cell.x][cell.y] = 1;

        // Keep picking next points using
        // Warnsdorff's heuristic
        Cell ret = null;
        for (int i = 0; i < boardSizeX * boardSizeY - 2; ++i) {
            ret = nextMove(cell, non_x, non_y);
            if (ret == null)
                return false;
        }

        // Check if tour is closed (Can end
        // at starting point)
        if (!neighbour(ret.x, ret.y, sx, sy))
            return false;
        if (display) printBoard();
        return true;
    }

    public boolean isStructuredPath() {
        boolean b1 = Math.abs(board[0][1] - board[2][0]) == 1;
        boolean b2 = Math.abs(board[0][2] - board[1][0]) == 1;
        boolean b3 = Math.abs(board[0][boardSizeY-3] - board[1][boardSizeY-1]) == 1;
        boolean b4 = Math.abs(board[0][boardSizeY-2] - board[2][boardSizeY-1]) == 1;
        boolean b5 = Math.abs(board[boardSizeX-1][1] - board[boardSizeX-3][0]) == 1;
        boolean b6 = Math.abs(board[boardSizeX-1][2] - board[boardSizeX-2][0]) == 1;
        boolean b7 = Math.abs(board[boardSizeX-1][boardSizeY-3] - board[boardSizeX-2][boardSizeY-1]) == 1;
        boolean b8 = Math.abs(board[boardSizeX-1][boardSizeY-2] - board[boardSizeX-3][boardSizeY-1]) == 1;

        return (b1 && b2 && b3 && b4 && b5 && b6 && b7 && b8);
    }

    public void solve(int sx, int sy) {
        while (!findClosedTour(sx, sy)) {}
    }

    public void solve(int sx, int sy, int non_x, int non_y) {
        while (!findClosedTour(sx, sy, non_x, non_y)) {}
    }

    // Driver Code
    public static void main(String[] args) {

        // interesting cases (3,18), (3, 16)
        KnightTourClosedWarnsdorff tmp = new KnightTourClosedWarnsdorff(12,12,true);

        while(!tmp.isStructuredPath())
            tmp.solve(1,1);
        System.out.println(tmp.isStructuredPath());


    }
}

class Cell
{
    int x;
    int y;

    public Cell(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
}
