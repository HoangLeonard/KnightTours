package Illu;

public class Line {
    double x1;
    double y1;
    double x2;
    double y2;

    public Line(double x1, double y1, double x2, double y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public double getX1() {
        return x1;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public double getY1() {
        return y1;
    }

    public void setY1(double y1) {
        this.y1 = y1;
    }

    public double getX2() {
        return x2;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    public double getY2() {
        return y2;
    }

    public void setY2(double y2) {
        this.y2 = y2;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Line otherLine = (Line) obj;
        boolean a = this.x1 == otherLine.x1 && this.y1 == otherLine.y1 && this.x2 == otherLine.x2 && this.y2 == otherLine.y2;
        boolean b = this.x1 == otherLine.x2 && this.y1 == otherLine.y2 && this.x2 == otherLine.x1 && this.y2 == otherLine.y1;

        return a || b;
    }

    public static void main(String[] args) {
        System.out.println(new Line(1,2,3,4).equals(new Line(1,2,3,4)));
    }
}