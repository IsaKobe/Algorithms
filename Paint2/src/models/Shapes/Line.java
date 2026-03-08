package models.Shapes;

import models.Maps.BaseMap;
import models.Maps.OutLineMap;
import models.Maps.PointerPointMap;
import models.Points.Point;
import models.Points.PointPointer;
import rasterizers.LineUtil;
import rasterizers.PointUtil;

import java.awt.*;

public class Line extends Rect {

    public Line(Point _a, Point _b, int space, Color lineColor)
    {
        super(_a, _b, space, lineColor);
        start = _a.clone();
        end = _b.clone();
    }

    Point start;
    Point end;

    @Override
    public void UpdateSecondPoint(Point b) {
        end.copy(b);
        PointUtil.MinMaxPoint(new Point[]{start, end}, minPoint, maxPoint);
    }

    public void UpdateVertex(PointPointer point){

        minPoint.X(Math.min(start.X(), end.X()));
        minPoint.Y(Math.min(start.Y(), end.Y()));

        maxPoint.X(Math.max(start.X(), end.X()));
        maxPoint.Y(Math.max(start.Y(), end.Y()));
    }

    @Override
    void Outline() {
        LineUtil.DrawLine(start, end, dotSpace, width, outlineColor, this);
        PointUtil.DrawPoint(start, outlineColor);
        PointUtil.DrawPoint(end, outlineColor);
    }

    @Override
    protected boolean AddToMap(PointerPointMap map) {
        start = new PointPointer(start, this);
        end = new PointPointer(end, this);
        map.addPoint((PointPointer) start);
        map.addPoint((PointPointer) end);
        return true;
    }

    @Override
    public boolean PointInBounds(Point point) {
        if(super.PointInBounds(point)){
            return OutLineMap.IsRectOnPoint(point.X(), point.Y(), this);
        }
        return false;
    }

    @Override
    void Fill() {}

}
