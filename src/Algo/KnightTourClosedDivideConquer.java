package Algo;

public class KnightTourClosedDivideConquer {
    private int boardSizeX;
    private int boardSizeY;
    private int[][] board;

    public int cx[] = {1, 1, 2, 2, -1, -1, -2, -2};
    public int cy[] = {2, -2, 1, -1, 2, -2, 1, -1};

    public Illu.KnightToursPath path;

    public KnightTourClosedDivideConquer(int boardSizeX, int boardSizeY) {
        this.boardSizeX = boardSizeX;
        this.boardSizeY = boardSizeY;
        this.board = new int[boardSizeX][boardSizeY];
        for (int i = 0; i < boardSizeX; i++)
            for (int j = 0; j < boardSizeY; j++)
                this.board[i][j] = -1;
    }

    private void heuristicSolve(int x1, int y1, int x2, int y2) {
        KnightTourClosedWarnsdorff tmp = new KnightTourClosedWarnsdorff(x2-x1, y2-y1,false);

        tmp.solve(0,0);

        for (int i = x1; i < x2; i++)
            for (int j = y1; j < y2; j++)
                this.board[i][j] = tmp.board[i][j];

    }

}
