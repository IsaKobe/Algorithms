package Input;

import models.Points.PointPointer;
import models.Shapes.Line;
import models.Shapes.Polygon;
import models.MyCanvas;
import models.Points.Point;
import models.Shapes.Rect;
import rasterizers.*;
import rasters.Raster;

import javax.swing.*;
import java.awt.*;

public class Actions
{
    public final JPanel panel;
    public static Raster raster;

    final MyCanvas canvas;

    boolean showPreview = true;
    boolean makeDotted = false;
    boolean snapGrid = false;
    int spacing = 5;

    public int GetSpacing(){
        return makeDotted ? spacing : 0;
    }
    public Actions(JPanel panel, Raster _raster){
        raster = _raster;

        this.canvas = new MyCanvas(raster, panel);
        this.panel = panel;

        LineUtil.canvas = canvas;
        CircleUtil.raster = raster;
        PointUtil.raster = raster;
        ElipseUtil.canvas = canvas;
        PixelUtil.raster = raster;
    }

    public static void SnapPoints(Point point, Point a, Point b){
        SnapPoints(point,a,b,false);
    }
    private static void SnapPoints(Point point, Point a, Point b, boolean onlyDiagonal){
        int x = Math.abs(a.X() - b.X());
        int y = Math.abs(a.Y() - b.Y());
        double deg = Math.toDegrees(Math.atan2(x, y));
        if(deg < 22.5 && !onlyDiagonal)
        {
            point.X(a.X());
        }
        else if (deg < 67.5 || onlyDiagonal){

            double mod = Math.sqrt(2) / 2f;
            int moveBy = (int)Math.round(Math.sqrt((Math.pow(x, 2) + (Math.pow(y, 2)))) * mod);



            if(a.X() < b.X())
                point.X(a.X() + moveBy);
            else
                point.X(a.X() - moveBy);

            if(a.Y() < b.Y())
                point.Y(a.Y() + moveBy);
            else
                point.Y(a.Y() - moveBy);
        }
        else{
            point.Y(a.Y());
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

    Point drawTempLine(Point a, Point b){
        if(a.X() == -1 && a.Y() == -1)
            return b;
        clear(Color.black.getRGB(), false);

        Line line = createLine(a, b.clone());
        canvas.draw();
        line.Draw();

        return line.getMaxPoint();
    }

    public void addLine(Point a, Point b)
    {
        canvas.addRect(createLine(a, b));
        canvas.FinishRect(GetSpacing());
    }

    public void addTemp(Rect rect) {
        canvas.addRect(rect);
    }

    public void removeTemp()
    {
        canvas.removeRect();
    }

    public boolean finishRect() {
        if(canvas.FinishRect(GetSpacing())){
            repaint();
            return true;
        }
        return false;
    }

    public PointPointer tryTakeVertex(Point point) {
        PointPointer p = canvas.GetClosestPoint(point);
        if(p != null)
            System.out.println("Trying to take vertex: " + p.X() + ", " + p.Y());
        else
            System.out.println("No near vertex");
        return p;
    }

    public void moveVertex(PointPointer point, Point newVal){
        canvas.moveVertex(point, newVal);
        repaint();
    }

    Line createLine(Point a, Point b) {
        Line line;
        Point x = new Point(b.X(), b.Y());
        if(snapGrid) {
            SnapPoints(x, a, b);
        }
        line = new Line(a.clone(), x, GetSpacing(), Color.red);
        return line;
    }

    public Point snapPoint(Point a, Point b, boolean onlyDiagonal) {
        Point x = b.clone();
        if(snapGrid){
            SnapPoints(x, a, b, onlyDiagonal);
        }
        return x;
    }
}
