package models.Shapes;

import models.Maps.PointerPointMap;
import models.Points.Point;
import models.Maps.BaseMap;
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

    int outlineColor;
    int fillColor;
    boolean drawFilled;
    int width;

    public Rect(Point a, Point b, int space, Color lineColor){
        minPoint = a;
        maxPoint = b;
        dotSpace = space;
        outlineColor = lineColor.getRGB();
        drawFilled = true;
        fillColor = Color.green.getRGB();
        width = 5;
    }

    public abstract void UpdateSecondPoint(Point b);

    final public void Draw(){
        Outline();
        if(drawFilled)
            Fill();
    }

    abstract void Outline();

    abstract void Fill();

    public boolean Finish(PointerPointMap map, int gap) {
        if(!AddToMap(map)){
            return false;
        }
        SetDotSpace(gap);
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
}
