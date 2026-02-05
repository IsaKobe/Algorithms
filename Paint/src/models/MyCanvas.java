package models;
import models.Complex.MyPolygon;
import models.Lines.Line;
import rasterizers.Rasterizer;
import rasterizers.TrivRasterizer;
import rasters.Raster;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MyCanvas {
    public MyPolygon tempPolygon;
    public List<MyPolygon> polygons;
    List<Line> lines;
    Rasterizer rasterizer;
    JPanel panel;

    public MyCanvas(Raster raster, JPanel panel) {
        lines = new ArrayList<>();
        polygons = new ArrayList<>();

        rasterizer = new TrivRasterizer(raster, Color.red);
        this.panel = panel;
    }

    public void addLine(Line line) {
        lines.add(line);
        draw();
    }

    public void removeLine(Line line) {
        lines.remove(line);
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
    }
}
