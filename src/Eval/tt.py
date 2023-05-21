import numpy as np
import matplotlib.pyplot as plt
from scipy.optimize import curve_fit

# Đọc dữ liệu từ file .txt
data = np.loadtxt('src/Eval/exectime.txt')

# Chuẩn bị dữ liệu cho biểu đồ
x = data[:, 0]
y = data[:, 1]/1000

# Định nghĩa mô hình hồi quy phi tuyến (ví dụ: đường cong parabol)
def nonlinear_regression(x, a, b, c):
    return a + b * np.power(c, x)

def reg(x, a, b, c):
    return a*x**2 + b*x + c

# Áp dụng hàm curve_fit để tìm mô hình hồi quy phi tuyến phù hợp
params, _ = curve_fit(nonlinear_regression, x, y)

params_2, _ = curve_fit(reg, x, y)

# Tạo dữ liệu mới cho đường hồi quy
x_regression = np.linspace(x.min(), x.max(), 100)
y_regression = nonlinear_regression(x_regression, *params)
y_regression2 = reg(x_regression, *params_2)

# Vẽ biểu đồ
plt.style.use("ggplot")
plt.plot(x, y, 'bo', label='Dữ liệu thực nghiệm')
s1 = str(round(params[0]*100)/100) + " + " + str(round(params[1]*100)/100) + "*" + str(round(params[2]*10000)/10000) + "^n"
s2 = str(round(params_2[0]*100000)/100000) + "x^2" + " + " + str(round(params_2[1]*100)/100) + "x" + " + " +str(round(params_2[2]*100)/100)
plt.plot(x_regression, y_regression, 'r-', label=s1)
plt.plot(x_regression, y_regression2, 'g-', label=s2)
plt.xlabel('size (nxn)')
plt.ylabel('time (s)')
plt.title('Mối quan hệ giữa kích thước ma trận và thời gian chạy')
plt.legend()

# Hiển thị biểu đồ
plt.show()

print(params)
print(params_2)
