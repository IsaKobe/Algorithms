package Input;

import Input.Callbacks.Keyboard;
import Input.Callbacks.Mouse;
import models.DotLine;
import models.Line;
import models.MyCanvas;
import models.Point;
import rasterizers.TrivRasterizer;
import rasters.Raster;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Input {

    public final JPanel panel;
    public final Raster raster;

    public final MyCanvas canvas;
    public final TrivRasterizer trivRasterizer;

    public Point a;
    public Point b;

    boolean showPreview = true;
    boolean makeDotted = false;
    boolean snapGrid = false;
    public int spacing = 5;


    public Input(JPanel panel, Raster raster)
    {
        this.canvas = new MyCanvas(raster, panel);
        this.trivRasterizer = new TrivRasterizer(raster, Color.yellow);
        this.panel = panel;
        this.raster = raster;

        new Mouse(this);
        new Keyboard(this);
    }

    public void clear(int color, boolean doRepaint) {
        raster.setClearColor(color);
        raster.clear();
        if(doRepaint){
            panel.repaint();
            canvas.clear();
        }
    }

    public void changeDrawType(int key, boolean pressed) {
        switch (key) {
            case KeyEvent.VK_CONTROL:
                makeDotted = pressed;
                break;
            case KeyEvent.VK_SHIFT:
                snapGrid = pressed;
                break;
            default:
                return;
        }
        recreateLine();
    }

    public void recreateLine(){
        if(showPreview) {
            clear(Color.black.getRGB(),false);

            Line line = createLine();
            trivRasterizer.rasterize(line);
            canvas.draw();
        }
    }

    public Line createLine() {
        Line line;
        Point x = new Point(b.getX(), b.getY());
        if(snapGrid) {
            Helpers.SnapPoints(x, a, b);
        }

        if(makeDotted){
            line = new DotLine(a, x, Color.red, spacing);
        }
        else{
            line = new Line(a, x, Color.red);
        }
        return line;
    }
}
