package models.Shapes;

import Input.Actions;
import models.Maps.OutLineMap;
import models.Maps.PointerPointMap;
import models.MyCanvas;
import models.Points.Point;
import models.Maps.BaseMap;
import models.Points.PointPointer;
import rasterizers.LineUtil;
import rasterizers.PixelUtil;
import rasterizers.PointUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class Polygon extends Rect {
    ArrayList<Point> points;

    public Polygon(Point startPoint, int space, Color color) {
        super(startPoint.clone(), startPoint.clone(), space, color);
        points = new ArrayList<>();
        AddPoint(startPoint);
    }

    public void AddPoint(Point point){
        points.add(point.clone());

        RecalculatePoint(point);
        Draw();
    }

    void RecalculatePoint(Point point){
        int x = point.X();
        int y = point.Y();

        if(x < minPoint.X())
            minPoint.X(x);
        if(x > maxPoint.X())
            maxPoint.X(x);

        if(y < minPoint.Y())
            minPoint.Y(y);
        if(y > maxPoint.Y())
            maxPoint.Y(y);
    }

    @Override
    protected boolean AddToMap(PointerPointMap map) {
        if(points.size() < 3)
            return false;
        for (int i = 0; i < points.size(); i++) {
            PointPointer pointer = new PointPointer(points.get(i), this);
            points.set(i, pointer);
            map.addPoint(pointer);
        }
        return true;
    }

    @Override
    public void UpdateSecondPoint(Point b) {
        return;
    }

    @Override
    public void UpdateVertex(PointPointer pointPointer) {
        PointUtil.MinMaxPoint(points.toArray(new Point[0]), minPoint, maxPoint);
    }

    @Override
    void Outline() {
        for (int i = 0; i < points.size() - 1; i++) {
            LineUtil.DrawLine(points.get(i), points.get(i + 1), dotSpace, width, outlineColor, this);
            PointUtil.DrawPoint(points.get(i),  outlineColor);
        }
        if(finished)
        {
            LineUtil.DrawLine(points.getFirst(), points.getLast(), dotSpace, width, outlineColor, this);
            PointUtil.DrawPoint(points.getLast(), outlineColor);
        }
    }

    @Override
    public boolean PointInBounds(Point point) {
        if(super.PointInBounds(point)){
            return OutLineMap.IsInside(minPoint.X(), point.Y(), point.X(), this);
        }
        return false;
    }

    @Override
    void Fill() {
        int minY = minPoint.Y();
        int maxY = maxPoint.Y();


        for (int y = minY; y <= maxY; y++) {
            ArrayList<Integer> intersections = new ArrayList();

            for (int i = 0; i < points.size(); i++) {
                Point p1 = points.get(i);
                Point p2 = points.get((i + 1) % points.size());

                if (p1.Y() == p2.Y()) continue;

                if ((y >= p1.Y() && y < p2.Y()) || (y >= p2.Y() && y < p1.Y())) {
                    double x = (double)p1.X() + (double)(y - p1.Y()) * (p2.X() - p1.X()) / (p2.Y() - p1.Y());
                    intersections.add((int) Math.round(x));
                }
            }

            Collections.sort(intersections);

            for (int i = 0; i + 1 < intersections.size(); i += 2) {
                int startX = intersections.get(i);
                int endX = intersections.get(i + 1);
                for (int x = startX; x <= endX; x++) {
                    Actions.raster.setPixel(x, y, fillColor);
                }
            }
        }
    }

}
