package Algo;

import Illu.Line;
import java.util.Random;

public class KnightTourClosedDivideConquer {
    private int boardSizeX;
    private int boardSizeY;
    private int[][] board;
    private boolean display;
    private Random random;

    public int cx[] = {1, 1, 2, 2, -1, -1, -2, -2};
    public int cy[] = {2, -2, 1, -1, 2, -2, 1, -1};

    public Illu.KnightToursPath path;

    public KnightTourClosedDivideConquer(int boardSizeX, int boardSizeY, boolean display) {
        this.boardSizeX = boardSizeX;
        this.boardSizeY = boardSizeY;
        this.display = display;
        this.board = new int[boardSizeX][boardSizeY];
        for (int i = 0; i < boardSizeX; i++)
            for (int j = 0; j < boardSizeY; j++)
                this.board[i][j] = -1;
        this.path = new Illu.KnightToursPath(boardSizeX, boardSizeY);
        this.random = new Random();
    }

    private void heuristicSolve(int x1, int x2, int y1, int y2) {
        KnightTourClosedWarnsdorff tmp = new KnightTourClosedWarnsdorff(x2-x1, y2-y1,false);

        long t1 = System.currentTimeMillis();
        int sx = 1;
        int sy = 1;
        while (!tmp.isStructuredPath()) {
            if (System.currentTimeMillis() - t1 > 5000) {
                sx = random.nextInt(x2 - x1);
                sy = random.nextInt(y2 - y1);
            }
            tmp.solve(sx, sy);
        }

        for (int i = x1; i < x2; i++)
            for (int j = y1; j < y2; j++)
                this.board[i][j] = tmp.board[i - x1][j - y1];

        if (display) {
            sx = sx + x1;
            sy = sy + y1;

            int length = (x2-x1) * (y2-y1);
            int count = 0;
            while (count <= length) {
                for (int i = 0; i < 8; i++) {
                    int tmp_x = sx + cx[i];
                    int tmp_y = sy + cy[i];
                    if ((tmp_x >= x1 && tmp_y >= y1) && (tmp_x < x2 && tmp_y < y2)) {
                        if (board[tmp_x][tmp_y] == board[sx][sy] + 1) {
                            path.addLine(sx, sy, tmp_x, tmp_y);
                            count++;
                            sx = tmp_x;
                            sy = tmp_y;
                        }

                        if (board[sx][sy] == length && board[tmp_x][tmp_y] == 1) {
                            path.addLine(sx, sy, tmp_x, tmp_y);
                            count++;
                        }
                    }
                }
            }
        }
    }

    private void heuristicSolve(int x1, int x2, int y1, int y2, int non_x, int non_y) {
        KnightTourClosedWarnsdorff tmp = new KnightTourClosedWarnsdorff(x2-x1, y2-y1,false);

        long t1 = System.currentTimeMillis();
        int sx = 1;
        int sy = 1;
        while (!tmp.isStructuredPath()) {
            if (System.currentTimeMillis() - t1 > 3000) {
                sx = random.nextInt(x2 - x1);
                sy = random.nextInt(y2 - y1);
            }
            tmp.solve(sx, sy, non_x-x1, non_y-y1);

        }

        for (int i = x1; i < x2; i++)
            for (int j = y1; j < y2; j++)
                this.board[i][j] = tmp.board[i - x1][j - y1];

        if (display) {
            sx = sx + x1;
            sy = sy + y1;

            int length = (x2-x1) * (y2-y1);
            int count = 0;
            while (count <= length) {
                for (int i = 0; i < 8; i++) {
                    int tmp_x = sx + cx[i];
                    int tmp_y = sy + cy[i];
                    if ((tmp_x >= x1 && tmp_y >= y1) && (tmp_x < x2 && tmp_y < y2)) {
                        if (board[tmp_x][tmp_y] == board[sx][sy] + 1) {
                            path.addLine(sx, sy, tmp_x, tmp_y);
                            count++;
                            sx = tmp_x;
                            sy = tmp_y;
                        }

                        if (board[sx][sy] == length - 1 && board[tmp_x][tmp_y] == 1) {
                            path.addLine(sx, sy, tmp_x, tmp_y);
                            count++;
                        }
                    }
                }
            }
        }
    }

    public void solve(int x1, int x2, int y1, int y2) {
        int height = x2 - x1;
        int width = y2 - y1;
        int unequalHeight = height/2;
        int unequalWidth = width/2;
        // Số chẵn gần trung vị nhất
        if ((unequalHeight = height/2) % 2 == 1) unequalHeight += 1;
        if ((unequalWidth = width/2) % 2 == 1) unequalWidth += 1;

        if (!(height % 2 == 1 && width % 2 == 1)) {

            if (Math.min(height, width) < 12 && Math.min(height, width) >= 6) {
                heuristicSolve(x1,x2, y1, y2);
            } else {
                solve(x1, x1 + unequalHeight, y1, y1 + unequalWidth);
                solve(x1, x1 + unequalHeight, y1 + unequalWidth, y2);
                solve(x1 + unequalHeight, x2, y1, y1 + unequalWidth);
                solve(x1 + unequalHeight, x2, y1 + unequalWidth, y2);

                path.addLine(x1 + unequalHeight - 1, y1 + unequalWidth - 3, x1 + unequalHeight, y1 + unequalWidth - 1);
                path.remove(x1 + unequalHeight - 1, y1 + unequalWidth - 3, x1 + unequalHeight - 2, y1 + unequalWidth - 1);

                path.addLine(x1 + unequalHeight + 2, y1 + unequalWidth - 2, x1 + unequalHeight + 1, y1 + unequalWidth);
                path.remove(x1 + unequalHeight + 2, y1 + unequalWidth - 2, x1 + unequalHeight, y1 + unequalWidth - 1);

                path.addLine(x1 + unequalHeight, y1 + unequalWidth + 2, x1 + unequalHeight - 1, y1 + unequalWidth);
                path.remove(x1 + unequalHeight + 1, y1 + unequalWidth, x1 + unequalHeight, y1 + unequalWidth + 2);

                path.addLine(x1 + unequalHeight - 2, y1 + unequalWidth - 1, x1 + unequalHeight - 3, y1 + unequalWidth + 1);
                path.remove(x1 + unequalHeight - 1, y1 + unequalWidth, x1 + unequalHeight - 3, y1 + unequalWidth + 1);
            }
        } else {
            if (Math.min(height,width) < 16 && Math.min(height, width) >= 7) {
                if (x2 % 2 == 1) {
                    if (y2 % 2 == 1) heuristicSolve(x1, x2, y1, y2, x2 - 1, y2 - 1);
                    else heuristicSolve(x1, x2, y1, y2, x2 - 1, y1);
                } else {
                    if (y2 % 2 == 1) heuristicSolve(x1, x2, y1, y2, x1,y2-1);
                    else heuristicSolve(x1, x2, y1, y2,x1,y1);
                }
            }
            else {
                  solve(x1, x1 + unequalHeight, y1, y1 + unequalWidth);
                  solve(x1, x1 + unequalHeight, y1 + unequalWidth, y2);
                  solve(x1 + unequalHeight, x2, y1, y1 + unequalWidth);
                  solve(x1 + unequalHeight, x2, y1 + unequalWidth, y2);

                  path.addLine(x1 + unequalHeight - 1, y1 + unequalWidth - 3, x1 + unequalHeight, y1 + unequalWidth - 1);
                  path.remove(x1 + unequalHeight - 1, y1 + unequalWidth - 3, x1 + unequalHeight - 2, y1 + unequalWidth - 1);

                  path.addLine(x1 + unequalHeight + 2, y1 + unequalWidth - 2, x1 + unequalHeight + 1, y1 + unequalWidth);
                  path.remove(x1 + unequalHeight + 2, y1 + unequalWidth - 2, x1 + unequalHeight, y1 + unequalWidth - 1);

                  path.addLine(x1 + unequalHeight, y1 + unequalWidth + 2, x1 + unequalHeight - 1, y1 + unequalWidth);
                  path.remove(x1 + unequalHeight + 1, y1 + unequalWidth, x1 + unequalHeight, y1 + unequalWidth + 2);

                  path.addLine(x1 + unequalHeight - 2, y1 + unequalWidth - 1, x1 + unequalHeight - 3, y1 + unequalWidth + 1);
                  path.remove(x1 + unequalHeight - 1, y1 + unequalWidth, x1 + unequalHeight - 3, y1 + unequalWidth + 1);
            }
        }


    }

    public static void main(String[] args) {
//        KnightTourClosedDivideConquer divq = new KnightTourClosedDivideConquer(7*2,7*2,true);
//        divq.heuristicSolve(0,7,0,7, 6,6);
//        divq.heuristicSolve(0,7,7,14, 6,7);
//        divq.heuristicSolve(7,14,0,7, 7,6);
//        divq.heuristicSolve(7,14,7,14,7,7);
//
//        KnightTourClosedDivideConquer divq = new KnightTourClosedDivideConquer(6*2*2,6*2*2, true);
//        divq.heuristicSolve(0,6,0,6);
//        divq.heuristicSolve(0,6,6,12);
//        divq.heuristicSolve(6,12,0,6);
//        divq.heuristicSolve(6,12,6,12);
//



        for (int k = 0; k < 3; k++) {
            for (int i = 100; i < 3000; i = i + 100) {
                long startTime = System.currentTimeMillis();
                KnightTourClosedDivideConquer tmp = new KnightTourClosedDivideConquer(i, i, false);
                tmp.solve(0, i, 0, i);
                System.out.println(i + " " + String.valueOf(System.currentTimeMillis() - startTime));
            }
        }

//        int boardSizeX = 14;
//        int boardSizeY = 18;
//
//        KnightTourClosedDivideConquer divq = new KnightTourClosedDivideConquer(boardSizeX, boardSizeY, true);
//        divq.solve(0,boardSizeX, 0, boardSizeY);
        // interesting cases: (8*2*2, 8*2*2) (31, 42) (13*2, 11*2) (31, 29)








    }

}
