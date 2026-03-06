package models.Shapes;

import models.Points.Point;
import models.PointMap;
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

    boolean finished;
    int dotSpace;
    public void SetDotSpace(int space)
    {
        dotSpace = space;
        Draw();
    }

    int outlineColor;
    int fillColor;
    boolean drawFilled;

    public Rect(Point a, Point b, int space, Color lineColor){
        minPoint = a;
        maxPoint = b;
        dotSpace = space;
        outlineColor = lineColor.getRGB();
        drawFilled = true;
        fillColor = Color.green.getRGB();
    }

    public void UpdateSecondPoint(Point b) {
        maxPoint = b.clone();
    }

    final public void Draw(){
        if(drawFilled)
            Fill();
        Outline();
    }

    abstract void Outline();

    abstract void Fill();

    public boolean Finish(PointMap map, int gap) {
        if(!AddToMap(map)){
            return false;
        }
        SetDotSpace(gap);
        finished = true;
        return true;
    }

    protected boolean AddToMap(PointMap map){
        maxPoint = new PointPointer(maxPoint, this);
        minPoint = new PointPointer(minPoint, this);
        map.addPoint((PointPointer) minPoint);
        map.addPoint((PointPointer) maxPoint);
        return true;
    }

    public void UpdateVertex(PointPointer pointPointer) {

    }
}
