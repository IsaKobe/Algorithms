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
    void Fill() {
        for (int y = minPoint.Y(); y < maxPoint.Y(); y++){
            int x = OutLineMap.GetNextPoint(minPoint.X(), y, maxPoint.X(), this, false);

            while (x < maxPoint.X()-1){
                x = OutLineMap.GetNextPoint(x, y, maxPoint.X(), this, true);
                int max = OutLineMap.GetNextPoint(x, y, maxPoint.X(), this, false);
                for(; x <= max; x++){
                    Actions.raster.setPixel(x, y, fillColor);
                }
                x = OutLineMap.GetNextPoint(x, y, maxPoint.X(), this, true);
                x = OutLineMap.GetNextPoint(x, y, maxPoint.X(), this, false);
            }
        }
    }

}
