package models;

import rasterizers.Rasterizer;
import rasterizers.TrivRasterizer;
import rasters.Raster;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MyCanvas {
    List<Line> lines;
    Rasterizer rasterizer;
    JPanel panel;

    public MyCanvas(Raster raster, JPanel panel) {
        lines = new ArrayList<>();

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
        panel.repaint();
    }

    public void clear() {
        lines.clear();
    }
}
