package models.Shapes;

import Input.Actions;
import models.Maps.BaseMap;
import models.Maps.OutLineMap;
import models.Maps.PointerPointMap;
import models.MyCanvas;
import models.Points.Point;
import models.Points.PointPointer;
import rasterizers.ElipseUtil;
import rasterizers.PointUtil;

import java.awt.*;
import java.io.Console;

public class Elipse extends Line{

    public Elipse(Point a, int space, Color color){
        super(a, a.clone(), space, color);
        start = a.clone();
        end = a.clone();
    }

    @Override
    void Outline() {
        PointUtil.DrawPoint(start, outlineColor);
        PointUtil.DrawPoint(end,  outlineColor);
        ElipseUtil.DrawElipse(start, end, dotSpace, width, outlineColor, this);
    }

    @Override
    public boolean PointInBounds(Point point) {
        if(super.PointInBounds(point)){
            int x = point.X();
            int y = point.Y();

            return OutLineMap.IsInside(minPoint.X(), y, x, this);
        }
        return false;
    }

    @Override
    void Fill() {
        int centerX = (minPoint.X() + maxPoint.X())/2;
        int toDo = centerX - minPoint.X();
        for (int y = minPoint.Y(); y < maxPoint.Y(); y++){
            if(OutLineMap.GetNumberOfIntersectsHorizontal(minPoint.X(), y, centerX, this) != 1)
                continue;
            for (int x = 0; x < toDo; x++){
                if(OutLineMap.IsRectOnPoint(centerX+x, y, this))
                    break;
                Actions.raster.setPixel(centerX-x, y, fillColor);
                Actions.raster.setPixel(centerX+x, y, fillColor);
            }
        }
    }
}
