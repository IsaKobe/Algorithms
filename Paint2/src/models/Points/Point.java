package models.Points;

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

    public int X() {
        return x;
    }

    public int Y() {
        return y;
    }

    public void X(int x) {
        this.x = x;
    }
    public void Y(int y) {
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
    public void getFromEv(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }

    @Override
    public String toString() {
        return x + ", " + y;
    }

    public Point minus(Point p) {
        return new Point(x - p.x, y - p.y);
    }
    public Point plus(Point p) {
        return new Point(x + p.x, y + p.y);
    }

    public void MoveBy(Point diff) {
        x += diff.x;
        y += diff.y;
    }
}
