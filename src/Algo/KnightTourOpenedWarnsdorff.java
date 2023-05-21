package Algo;

public class KnightTourOpenedWarnsdorff {
    private int boardSizeX;
    private int boardSizeY;
    private int[][] board;
    private static final int moveX[] = {1, 1, 2, 2, -1, -1, -2, -2};
    private static final int moveY[] = {2, -2, 1, -1, 2, -2, 1, -1};

    private int cx = 0;
    private int cy = 0;

    public KnightTourOpenedWarnsdorff(int boardSizeX, int boardSizeY) {
        this.boardSizeX = boardSizeX;
        this.boardSizeY = boardSizeY;
        board = new int[boardSizeX][boardSizeY];
    }

    boolean isValidMove(int x, int y) {
        return (x >= 0 && x < boardSizeX) && (y >= 0 && y < boardSizeY);
    }

    void printBoard() {
        for (int i = 0; i < boardSizeX; i++) {
            for (int j = 0; j < boardSizeY; j++) {
                System.out.printf("%d\t", board[i][j]);
            }
            System.out.println();
        }
    }

    int[] getMinNeighbor(int x, int y) {
        int minNei = 9;
        int x_res = -1, y_res = -1;
        for (int i=0; i<8; i++) {
            int x_new = x + moveX[i];
            int y_new = y + moveY[i];
            if (isValidMove(x_new, y_new) && board[x_new][y_new] == 0) {
                int count = 0;
                for (int j=0; j<8; j++) {
                    int x_new_new = x_new + moveX[j];
                    int y_new_new = y_new + moveY[j];
                    if (isValidMove(x_new_new, y_new_new) && board[x_new_new][y_new_new] == 0)
                        count++;
                }
                if (count < minNei) {
                    minNei = count;
                    x_res = x_new;
                    y_res = y_new;
                }
            }
        }
        if (x_res != -1) {
            return new int[]{x_res, y_res};
        } else return null;
    }

    void closedTours() {
        int startX = 0;
        int startY = 0;
        board[startX][startY] = 1;
        for (int i=2; i<=boardSizeX*boardSizeY; i++) {
            int[] tmp = getMinNeighbor(startX, startY);
            board[tmp[0]][tmp[1]] = i;
            startX = tmp[0];
            startY = tmp[1];
        }
    }


    public static void main(String[] args) {

        // interesting cases (3,7) (3,8) (3,4)
        KnightTourOpenedWarnsdorff a = new KnightTourOpenedWarnsdorff(30,30);
        a.closedTours();
        a.printBoard();
    }
}
