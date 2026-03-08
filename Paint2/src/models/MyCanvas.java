package models;
import models.Maps.BaseMap;
import models.Maps.OutLineMap;
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
    OutLineMap outlineMap;
    Raster raster;


    public MyCanvas(Raster _raster, JPanel panel) {
        rectList = new ArrayList<>();
        this.panel = panel;
        raster = _raster;
        map = new PointerPointMap(raster.getWidth(), raster.getHeight());
        outlineMap = new OutLineMap(raster.getWidth(), raster.getHeight());
    }

    public void draw() {
        outlineMap.clearMap();
        for (Rect rect : rectList) {
            rect.Draw();
        }
        panel.repaint();
    }

    public void clear() {
        rectList.clear();
        map.clearMap();
        outlineMap.clearMap();
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

    public void PaintOutline(int x0, int y0, int color, Rect rect) {
        raster.setPixel(x0, y0, color);
        if(rect != null && rect.finished)
            outlineMap.addPoint(x0, y0, rect);
    }

    public void GetRect(Point point) {
        for (int i = rectList.size()-1; i > -1; i--) {
            Rect rect = rectList.get(i);
            if(rect.PointInBounds(point)){
                System.out.println("clicked in:" + rect);
                return;
            }
        }
    }
}
