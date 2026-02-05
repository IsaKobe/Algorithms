package models.Complex;

import models.Point;

import java.awt.*;
import java.util.ArrayList;

public class MyPolygon {
    public ArrayList<Point> points = new ArrayList<Point>();
    public int space;
    public Color color;
    public boolean finished;

    public MyPolygon(Point startPoint) {
        points.add(startPoint);
        color = Color.RED;
        finished = false;
    }
}
