package models;
import models.Maps.PointerPointMap;
import models.Points.Point;
import models.Points.PointPointer;
import models.Shapes.Rect;
import rasters.Raster;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class MyCanvas {
    List<Rect> rectList;
    JPanel panel;
    PointerPointMap map;
    Raster raster;


    public MyCanvas(Raster _raster, JPanel panel) {
        rectList = new ArrayList<>();
        this.panel = panel;
        raster = _raster;
        map = new PointerPointMap(raster.getWidth(), raster.getHeight());
    }

    public void draw() {
        for (Rect rect : rectList) {
            rect.Draw();
        }
        panel.repaint();
    }

    public void clear() {
        rectList.clear();
        map.clearMap();
    }

    public PointPointer GetClosestPoint(Point point) {
        return map.findPoint(point.X(), point.Y());
    }
    public void moveVertex(PointPointer point, Point newVal){
        map.movePoint(point, newVal);

    }

    public void addRect(Rect polygon) {
        rectList.add(polygon);
        draw();
    }

    public void removeRect() {
        rectList.removeLast();
        draw();
    }
    public boolean FinishRect(){
        return rectList.getLast().Finish(map);
    }

    public void PaintOutline(int x0, int y0, int color) {
        raster.setPixel(x0, y0, color);
    }

    public Rect GetRect(Point point) {
        int maxRange = 5;
        int x = point.X();
        int y = point.Y();
        Rect result;
        for (int i = 0; i < maxRange; i++) {
            for (int a = x - i; a <= x + i; a++) {
                point.X(a);
                for (int b = y - i; b <= y + i; b++) {
                    point.Y(b);
                    if((result = GetRectOnePoint(point)) != null)
                        return result;
                }
            }
        }
        return null;
    }


    Rect GetRectOnePoint(Point point){
        for (int i = rectList.size()-1; i > -1; i--) {
            Rect rect = rectList.get(i);
            if(rect.PointInBounds(point)){
                return rect;
            }
        }
        return null;
    }

    public boolean DeleteAt(Point p, int radius) {
        boolean result = false;
        for (int i = rectList.size()-1; i > -1; i--) {
            Rect rect = rectList.get(i);
            p = new Point(p.X() - radius, p.Y() - radius);

            Point tempPoint = p;
            for(int x = 0; x < radius*2+1; x++){
                tempPoint.X(p.X()+x);
                for(int y = 0; y < radius*2+1; y++){
                    tempPoint.Y(p.Y()+y);
                    if(rect.PointInBounds(tempPoint)){
                        rectList.remove(rect);
                        result = true;
                    }
                }
            }

        }
        return result;
    }
}
