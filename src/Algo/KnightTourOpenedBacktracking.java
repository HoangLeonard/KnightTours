package Algo;
import Illu.KnightToursIllustration;
import Illu.KnightToursIllustration.Board.Cell;
import java.util.concurrent.TimeUnit;

import java.io.*;
import java.util.ArrayList;

public class KnightTourOpenedBacktracking implements Solver{
    public static final int[][] MOVES = {{ 1, 2, 2, 1,-1,-2,-2,-1},
                                         {-2,-1, 1, 2, 2, 1,-1,-2}};
    public int[][] board;
    private int boardSizeX;
    private int boardSizeY;
    private long execTime = 0;
    private int moveCount = 0;
    private int trialCount = 0;
    private long memoryUsage = 0;
    private long beforMemo = 0;
    private boolean display = false;
    private static String path = "src/Algo/BacktrackStep.txt";

    public KnightTourOpenedBacktracking(int sizeX, int sizeY, boolean display) {
        this.boardSizeX = sizeX;
        this.boardSizeY = sizeY;
        this.display = display;
        execTime = 0;
        moveCount = 0;
        trialCount = 0;
        memoryUsage = 0;
        this.beforMemo = getCurrentMemoryUsage();
        board = new int [sizeX][sizeY];
    }

    @Override
    public void solve() {
        if (!display) {
            long startTime = System.nanoTime();
            if (backtrackSolver(0, 0, 1)) {
                printSolution();
            } else {
                System.out.println("No solution found.");
            }
            this.memoryUsage = getCurrentMemoryUsage() - this.beforMemo;
            long endTime = System.nanoTime();
            this.execTime = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);

        } else {
            if (backtrackSolverDisplay(0,0,1)) {

            } else {
                System.out.println("No solution found.");
            }
        }
    }

    public boolean backtrackSolver(int x, int y, int move) {
        this.trialCount++;
        if (move > boardSizeX * boardSizeY) {
            return true;
        }

        if (x < 0 || x >= boardSizeX || y < 0 || y >= boardSizeY || board[x][y] != 0) {
            return false;
        }

        board[x][y] = move;
        this.moveCount++;
        for (int i = 0; i < MOVES[0].length; i++) {
            int nextX = x + MOVES[0][i];
            int nextY = y + MOVES[1][i];
            if (backtrackSolver(nextX, nextY, move + 1))
                return true;
        }

        board[x][y] = 0;
        return false;
    }

    public boolean backtrackSolverDisplay(int x, int y, int move) {
        if (move > boardSizeX * boardSizeY) {
            return true;
        }

        System.out.println("try " + x + " " + y);
        if (x < 0 || x >= boardSizeX || y < 0 || y >= boardSizeY || board[x][y] != 0) {
            return false;
        }

        board[x][y] = move;
        System.out.println("move " + x + " " + y);
        for (int i = 0; i < MOVES[0].length; i++) {
            int nextX = x + MOVES[0][i];
            int nextY = y + MOVES[1][i];
            if (backtrackSolverDisplay(nextX, nextY, move + 1))
                return true;
        }

        board[x][y] = 0;
        System.out.println("back " + x + " " + y);
        return false;
    }

    private void solveClosedKnightTour(int x, int y, int moveCount, int startX, int startY) {
        board[x][y] = moveCount;

        if (moveCount == boardSizeX * boardSizeY) {
            // Kiểm tra nếu con mã có thể di chuyển từ ô cuối cùng đến ô ban đầu để tạo thành hành trình đóng
            for (int i = 0; i < 8; i++) {
                int nextX = x + MOVES[0][i];
                int nextY = y + MOVES[1][i];

                if (nextX == startX && nextY == startY) {
                    board[nextX][nextY] = moveCount + 1;
                    return;
                }
            }
        }

        for (int i = 0; i < 8; i++) {
            int nextX = x + MOVES[0][i];
            int nextY = y + MOVES[1][i];

            if (nextX >= 0 && nextX < boardSizeX && nextY >= 0 && nextY < boardSizeY && board[x][y] == 0) {
                solveClosedKnightTour(nextX, nextY, moveCount + 1, startX, startY);

                // Nếu đã tìm được hành trình đóng, không cần kiểm tra các ô khác
                if (board[startX][startY] != 0) {
                    return;
                }
            }
        }

        // Nếu không tìm được hành trình đóng, quay lui và đánh dấu ô hiện tại là 0
        board[x][y] = 0;
    }

    private void printSolution() {
        System.out.println("Knight's Tour Solution:");
        for (int i = 0; i < boardSizeX; i++) {
            for (int j = 0; j < boardSizeY; j++) {
                System.out.printf("%2d ", board[i][j]);
            }
            System.out.println();
        }
    }

    @Override
    public int getSize() {
        return this.boardSizeX*this.boardSizeY;
    }

    public int getCurrentMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();
        long usedMemory = runtime.totalMemory() - runtime.freeMemory();
        return (int) usedMemory;
    }

    @Override
    public long getExecutionTime() {
        return this.execTime;
    }

    @Override
    public int getMoveCount() {
        return this.moveCount;
    }

    @Override
    public int getTrialCount() {
        return this.trialCount;
    }

    @Override
    public long getMemoryUsage() {
        return this.memoryUsage;
    }

    public static void main(String[] args) {
//        String outputFile = "src/Algo/BacktrackStep.txt";
//        int numCells = 5;
//        try {
//            // Tạo một đối tượng FileOutputStream để ghi dữ liệu vào file
//            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
//            PrintStream printStream = new PrintStream(fileOutputStream);
//            PrintStream stdOutput = System.out;
//            System.setOut(printStream);
//
//            KnightTourOpenedBacktracking bt = new KnightTourOpenedBacktracking(numCells, numCells, true);
//            bt.solve();
//
//            // Đóng luồng ghi
//            printStream.close();
//            fileOutputStream.close();
//            System.setOut(stdOutput);
//            System.out.println("Thuật toán chạy thành công, thực hiện in lời giải.");
//            bt.printSolution();
//
//            KnightToursIllustration i = new KnightToursIllustration(numCells, numCells);
//            KnightToursIllustration.Board tmp = i.getBoard();
//            Cell[][] cells = tmp.getCells();
//            ArrayList<int[]> path = new ArrayList<>();
//            int move = 0;
//            FileReader fileReader = new FileReader(outputFile);
//            BufferedReader bufferedReader = new BufferedReader(fileReader);
//            String line;
//            while ((line = bufferedReader.readLine()) != null) {
//                String[] code = line.split(" ");
//                int x = Integer.parseInt(code[1]);
//                int y = Integer.parseInt(code[2]);
//                if (code[0].equals("move")) {
//                    move++;
//                    cells[x][y].setNumber(move);
//                    cells[x][y].setProperties(true, true, false);
//                    if (path.size() > 0) {
//                        int[] pre = path.get(path.size()-1);
//                        cells[pre[0]][pre[1]].displayImage(false);
//                    }
//                    path.add(new int[]{x, y});
//                    Thread.sleep(300);
//                } else if (code[0].equals("try")) {
//                    try {
//                        cells[x][y].displayOverlay(true);
//                        Thread.sleep(100);
//                        if (cells[x][y].getNumber() != 0) {
//                            cells[x][y].displayOverlay(false);
//                        }
//                    } catch (Exception ignore) {}
//                } else if (code[0].equals("back")) {
//                    move--;
//                    cells[x][y].setNumber(0);
//                    cells[x][y].setProperties(false, false, false);
//                    path.remove(path.size()-1);
//                    int[] pre = path.get(path.size()-1);
//                    cells[pre[0]][pre[1]].displayImage(true);
//                    Thread.sleep(100);
//                }
//            }
//            bufferedReader.close();
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        KnightTourOpenedBacktracking bt = new KnightTourOpenedBacktracking(7, 8,false);
        bt.solve();
        bt.printSolution();
        System.out.println(bt.getSize());
        System.out.println(bt.getExecutionTime());
        System.out.println(bt.getTrialCount());
        System.out.println(bt.getMoveCount());
    }
}
