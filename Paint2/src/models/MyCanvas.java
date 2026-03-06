package models;
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
    PointMap map;

    public MyCanvas(Raster raster, JPanel panel) {
        rectList = new ArrayList<>();
        this.panel = panel;
        map = new PointMap(raster.getWidth(), raster.getHeight());
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
    public boolean FinishRect(int gap){
        return rectList.getLast().Finish(map, gap);
    }
}
