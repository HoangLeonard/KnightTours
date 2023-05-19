package Algo;

public interface Solver {
    void solve();

    // Phương thức trả về kích thước của ô bàn cờ
    int getSize();

    // Phương thức trả về thời gian chạy (đơn vị: milliseconds)
    long getExecutionTime();

    // Phương thức trả về số bước di chuyển
    int getMoveCount();

    // Phương thức trả về số lần thử nghiệm
    int getTrialCount();

    // Phương thức trả về lượng bộ nhớ đã sử dụng (đơn vị: bytes)
    long getMemoryUsage();
}
