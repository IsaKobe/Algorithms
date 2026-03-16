package models.Shapes;

import models.Maps.PointerPointMap;
import models.Points.Point;
import models.Points.PointPointer;

import java.awt.*;

public abstract class Rect{
    Point minPoint, maxPoint;

    public Point getMaxPoint() {
        return maxPoint;
    }

    public Point getMinPoint() {
        return minPoint;
    }

    public boolean finished;
    int dotSpace;
    public void SetDotSpace(int space)
    {
        dotSpace = space;
        Draw();
    }

    public int outlineColor;
    public int fillColor;
    boolean drawFilled;
    public int width;

    public Rect(Point a, Point b){
        minPoint = a;
        maxPoint = b;
    }

    public abstract void UpdateSecondPoint(Point b);

    abstract public void Draw();

    public boolean Finish(PointerPointMap map) {
        if(!AddToMap(map)){
            return false;
        }
        finished = true;
        return true;
    }

    protected boolean AddToMap(PointerPointMap map){
        maxPoint = new PointPointer(maxPoint, this);
        minPoint = new PointPointer(minPoint, this);
        map.addPoint((PointPointer) minPoint);
        map.addPoint((PointPointer) maxPoint);
        return true;
    }

    public void UpdateVertex(PointPointer pointPointer) {}

    public boolean PointInBounds(Point point){
        if(point.X() >= minPoint.X() && point.X() <= maxPoint.X()){
            if(point.Y() >= minPoint.Y() && point.Y() <= maxPoint.Y()){
                return true;
            }
        }
        return false;
    }

    public void Move(Point diff) {
        minPoint.MoveBy(diff);
        maxPoint.MoveBy(diff);
    }
}
