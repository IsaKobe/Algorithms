package Input;

import models.Complex.MyPolygon;
import models.Lines.DotLine;
import models.Lines.Line;
import models.MyCanvas;
import models.Point;
import rasterizers.TrivRasterizer;
import rasters.Raster;

import javax.swing.*;
import java.awt.*;

public class LineActions
{
    public final JPanel panel;
    final Raster raster;

    final MyCanvas canvas;
    final TrivRasterizer trivRasterizer;

    boolean showPreview = true;
    boolean makeDotted = false;
    boolean snapGrid = false;
    int spacing = 5;

    public LineActions(JPanel panel, Raster raster){
        this.canvas = new MyCanvas(raster, panel);
        this.trivRasterizer = new TrivRasterizer(raster, Color.yellow);
        this.panel = panel;
        this.raster = raster;
    }


    public static void SnapPoints(Point point, Point a, Point b){
        int x = Math.abs(a.getX() - b.getX());
        int y = Math.abs(a.getY() - b.getY());
        double deg = Math.toDegrees(Math.atan2(x, y));
        if(deg < 22.5)
        {
            point.setX(a.getX());
        }
        else if (deg < 67.5){

            double mod = Math.sqrt(2) / 2f;
            int moveBy = (int)Math.round(Math.sqrt((Math.pow(x, 2) + (Math.pow(y, 2)))) * mod);



            if(a.getX() < b.getX())
                point.setX(a.getX() + moveBy);
            else
                point.setX(a.getX() - moveBy);

            if(a.getY() < b.getY())
                point.setY(a.getY() + moveBy);
            else
                point.setY(a.getY() - moveBy);
        }
        else{
            point.setY(a.getY());
        }
    }

    void clear() {
        clear(Color.black.getRGB(),true);
    }

    void clear(int color, boolean deleteObjects) {
        raster.setClearColor(color);
        raster.clear();
        if(deleteObjects){
            panel.repaint();
            canvas.clear();
        }
    }
    void repaint(){
        raster.setClearColor(Color.black.getRGB());
        raster.clear();

        canvas.draw();
    }

    void drawTempLine(Point a, Point b){
        clear(Color.black.getRGB(), false);

        Line line = createLine(a, b.clone());
        trivRasterizer.rasterize(line);
        canvas.draw();
    }


    void addPolygonPoint(MyPolygon polygon, Point a, Point b)
    {
        Line l = createLine(a, b);
        b.copy(l.getB());
        polygon.points.add(b);
        polygon.space = makeDotted ? spacing : 0;
        a.copy(b);
    }

    Line createLine(Point a, Point b) {
        Line line;
        Point x = new Point(b.getX(), b.getY());
        if(snapGrid) {
            SnapPoints(x, a, b);
        }

        if(makeDotted){
            line = new DotLine(a, x, Color.red, spacing);
        }
        else{
            line = new Line(a, x, Color.red);
        }
        return line;
    }

    public void addLine(Point a, Point b) {
        canvas.addLine(createLine(a.clone(), b.clone()));
    }

    public void tempPolygon(MyPolygon polygon) {
        canvas.tempPolygon = polygon;
    }

    public void finishPolygon(MyPolygon polygon) {
        canvas.tempPolygon = null;
        canvas.polygons.add(polygon);
        polygon.finished = true;
        polygon.space = makeDotted ? spacing : 0;
        repaint();
    }
}
