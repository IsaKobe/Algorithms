package models.Shapes;

import models.PointMap;
import models.Points.Point;
import rasterizers.LineUtil;
import rasterizers.PointUtil;

import java.awt.*;

public class Line extends Rect {

    public Line(Point a, Point b, int space, Color lineColor) {
        super(a, b, space, lineColor);
    }

    @Override
    void Outline() {
        LineUtil.DrawLine(minPoint, maxPoint, dotSpace, outlineColor);
        PointUtil.DrawPoint(minPoint, outlineColor);
        PointUtil.DrawPoint(maxPoint, outlineColor);
    }

    @Override
    void Fill() {

    }

}
