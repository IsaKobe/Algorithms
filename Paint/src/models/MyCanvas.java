package models;
import models.Complex.MyPolygon;
import models.Lines.Line;
import rasterizers.Rasterizer;
import rasterizers.TrivRasterizer;
import rasters.Raster;

import javax.swing.*;
import java.awt.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
public class MyCanvas {
    public MyPolygon tempPolygon;
    public List<MyPolygon> polygons;
    List<Line> lines;
    Rasterizer rasterizer;
    JPanel panel;
    PointMap map;

    public MyCanvas(Raster raster, JPanel panel) {
        lines = new ArrayList<>();
        polygons = new ArrayList<>();

        rasterizer = new TrivRasterizer(raster, Color.red);
        this.panel = panel;
        map = new PointMap(raster.getWidth(), raster.getHeight());
    }

    public void addLine(Line line) {
        lines.add(line);
        map.addPoint(line.getA());
        map.addPoint(line.getB());
        draw();
    }

    public void removeLine(Line line) {
        lines.remove(line);
        map.removePoint(line.getA());
        map.removePoint(line.getB());
    }


    public void addPolygon(MyPolygon polygon, int space) {
        tempPolygon = null;

        polygons.add(polygon);
        polygon.finished = true;
        polygon.space = space;
        for (int i = 0; i < polygon.points.size(); i++) {
            map.addPoint(polygon.points.get(i));
        }
    }

    public void draw() {
        for (Line line : lines) {
            rasterizer.rasterize(line);
        }

        if(rasterizer instanceof TrivRasterizer trivRasterizer){
            for (MyPolygon polygon : polygons) {
                trivRasterizer.rasterize(polygon);
            }
            if(tempPolygon != null)
                trivRasterizer.rasterize(tempPolygon);
        }
        panel.repaint();
    }

    public void clear() {
        lines.clear();
        polygons.clear();
        tempPolygon = null;
        map.clearMap();
    }

    public Point GetClosestPoint(Point point) {
        return map.findPoint(point.getX(), point.getY());
    }
    public void moveVertex(Point point, Point newVal){
        map.movePoint(point, newVal);
    }
}
