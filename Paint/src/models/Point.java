package models;

import java.awt.event.MouseEvent;

public class Point {
    int x;
    int y;

    public Point() {
        x = 0;
        y = 0;
    }
    public Point(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }

    public void reset() {
        x = -1;
        y = -1;
    }

    public Point clone() {
        return new Point(x, y);
    }

    public void copy(Point p) {
        this.x = p.x;
        this.y = p.y;
    }
}
