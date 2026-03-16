package models.Shapes;

import models.Points.Point;
import rasterizers.ElipseUtil;
import rasterizers.PointUtil;

import java.util.ArrayList;

public class Elipse extends Line{

    //y, x - flipped to create lines
    ArrayList<Integer>[] outlinePoints;
    public Elipse(Point a){
        super(a, a.clone());
        start = a.clone();
        end = a.clone();
    }

    @Override
    public void Draw() {
        PointUtil.DrawPoint(start, outlineColor);
        PointUtil.DrawPoint(end,  outlineColor);

        outlinePoints = new ArrayList[maxPoint.Y() - minPoint.Y()];
        ElipseUtil.DrawElipse(start, end, dotSpace, width, outlineColor, fillColor, outlinePoints);
    }

    boolean IsInside(int x, int y){
        for (int i = 0; i + 1 < outlinePoints[y].size(); i+=2)
        {
            if(x > outlinePoints[y].get(i) && x < outlinePoints[y].get(i + 1))
                return true;
        }
        return false;
    }

    @Override
    public boolean PointInBounds(Point point) {
        if(point.X() > minPoint.X() && point.X() < maxPoint.X()){
            if(point.Y() > minPoint.Y() && point.Y() < maxPoint.Y()){
                Point diff = maxPoint.minus(point);
                return IsInside(diff.X(), diff.Y());
            }
        }
        return false;
    }
}
