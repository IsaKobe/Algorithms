package models.Shapes;

import models.Maps.PointerPointMap;
import models.Points.Point;
import models.Points.PointPointer;
import rasterizers.LineUtil;
import rasterizers.PointUtil;

public class Line extends Rect {

    public Line(Point _a, Point _b)
    {
        super(_a, _b);
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
    public void Draw() {
        LineUtil.DrawLine(start, end, dotSpace, width, outlineColor);
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
            int diff = end.X() - start.X();
            if(diff != 0){
                float d = (float)(end.Y() - start.Y()) / diff;

                int x = point.X() - start.X();
                int y = Math.round(x * d) + start.Y();

                return Math.abs(point.Y() - y) < 2;
            }
            else{
                return true;
            }
        }
        return false;
    }


    @Override
    public void Move(Point diff) {
        super.Move(diff);
        PointerPointMap.UpdatePoint((PointPointer) start, start.plus(diff));
        PointerPointMap.UpdatePoint((PointPointer) end, end.plus(diff));
    }
}
