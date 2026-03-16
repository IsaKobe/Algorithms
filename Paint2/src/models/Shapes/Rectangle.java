package models.Shapes;

import models.Maps.PointerPointMap;
import models.Points.Point;
import models.Points.PointPointer;
import rasterizers.LineUtil;
import rasterizers.PixelUtil;
import rasterizers.PointUtil;

public class Rectangle extends Polygon{

    public Rectangle(Point a) {
        super(a);
        AddPoint(a.clone());
        AddPoint(a.clone());
        AddPoint(a.clone());
    }

    @Override
    public void UpdateSecondPoint(Point newPoint) {
        UpdatePoint(newPoint, 2);
    }

    @Override
    public void UpdateVertex(PointPointer point){
        UpdatePoint(point, points.indexOf(point));
    }

    @Override
    void Outline() {
        for (int i = 0; i < points.size() - 1; i++) {
            LineUtil.DrawLine(points.get(i), points.get(i + 1), dotSpace, width, outlineColor);
            PointUtil.DrawPoint(points.get(i),  outlineColor);
        }
        LineUtil.DrawLine(points.getFirst(), points.getLast(), dotSpace, width, outlineColor);
        PointUtil.DrawPoint(points.getLast(), outlineColor);
    }


    void UpdatePoint(Point point, int index){
        Point a = points.get((index + 2) % 4);
        Point b = points.get(index);

        b.copy(point);

        int n = index - 1;
        if(n < 0)
            n = 3;

        int p = (index + 1) % 4;

        int numA = a.X() < b.X() ? n : p;
        int numB = a.X() < b.X() ? p : n;

        if(points.get(numA) instanceof PointPointer){
            PointerPointMap.UpdatePoint((PointPointer) points.get(numA), new Point(b.X(), a.Y()));
            PointerPointMap.UpdatePoint((PointPointer) points.get(numB), new Point(a.X(), b.Y()));
        }
        else{
            points.get(numA).X(b.X());
            points.get(numA).Y(a.Y());

            points.get(numB).X(a.X());
            points.get(numB).Y(b.Y());
        }
        minPoint.X(Math.min(a.X(), b.X()));
        minPoint.Y(Math.min(a.Y(), b.Y()));

        maxPoint.X(Math.max(a.X(), b.X()));
        maxPoint.Y(Math.max(a.Y(), b.Y()));
    }

    @Override
    void Fill() {
        for (int x = minPoint.X(); x < maxPoint.X(); x++){
            for (int y = minPoint.Y(); y < maxPoint.Y(); y++){
                PixelUtil.DrawPixel(x, y, fillColor);
            }
        }
    }

    @Override
    public boolean PointInBounds(Point point) {
        if(point.X() >= minPoint.X() && point.X() <= maxPoint.X()){
            if(point.Y() >= minPoint.Y() && point.Y() <= maxPoint.Y()){
                return true;
            }
        }
        return false;
    }
}
