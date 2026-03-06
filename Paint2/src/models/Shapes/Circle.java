package models.Shapes;

import models.Points.Point;
import rasterizers.ElipseUtil;
import rasterizers.PointUtil;

import java.awt.*;

public class Circle extends Rect{

    public Circle(Point a, int space, Color color){
        super(a, a.clone(), space, color);
    }

    @Override
    void Outline() {
        PointUtil.DrawPoint(minPoint, outlineColor);
        PointUtil.DrawPoint(maxPoint,  outlineColor);
        ElipseUtil.DrawElipse(minPoint, maxPoint, dotSpace, outlineColor);
        //System.out.println("draw:"+minPoint.toString()+ "----------------" + maxPoint);
    }

    @Override
    void Fill() {

    }

}
