package models.Lines;

import models.Point;

import java.awt.*;

public class Line {
    models.Point a,  b;
    Color color;

    public Line(models.Point a, models.Point b, Color color) {
        this.a = a;
        this.b = b;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public models.Point getB() {
        return b;
    }

    public Point getA() {
        return a;
    }
}
