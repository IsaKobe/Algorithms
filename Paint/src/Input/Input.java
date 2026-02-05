package Input;

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

    private final JPanel panel;
    private final Raster raster;

    private final MyCanvas canvas;
    private final TrivRasterizer trivRasterizer;

    Point a, b;

    boolean showPreview = true;
    boolean makeDotted = false;
    boolean snapGrid = false;
    int spacing = 5;


    public Input(JPanel panel, Raster raster)
    {
        this.canvas = new MyCanvas(raster, panel);
        this.trivRasterizer = new TrivRasterizer(raster, Color.yellow);
        this.panel = panel;
        this.raster = raster;

        createMouseCallbacks();
    }

    public void clear(int color, boolean doRepaint) {
        raster.setClearColor(color);
        raster.clear();
        if(doRepaint){
            panel.repaint();
            canvas.clear();
        }
    }

    void changeInputType(int key, boolean pressed){
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

        RecreateLine();
    }

    void createMouseCallbacks() {

        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(e.getButton() != MouseEvent.BUTTON1)
                    return;
                a = new Point(e.getX(), e.getY());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(e.getButton() != MouseEvent.BUTTON1)
                    return;
                b = new Point(e.getX(), e.getY());
                canvas.addLine(CreateLine());
                a = new Point(0,0);
                b = new Point(0,0);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                b = new Point(e.getX(), e.getY());
                RecreateLine();
            }
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                spacing = Math.clamp(spacing + e.getUnitsToScroll(), 2, 15);
                RecreateLine();
            }
        };

        KeyAdapter keyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                changeInputType(e.getKeyCode(), true);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                changeInputType(e.getKeyCode(), false);
            }

            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar() == 'c') {
                    clear(Color.black.getRGB(),true);
                }
            }
        };

        panel.addMouseListener(mouseAdapter);
        panel.addMouseMotionListener(mouseAdapter);
        panel.addMouseWheelListener(mouseAdapter);
        panel.addKeyListener(keyAdapter);
    }

    void RecreateLine(){
        if(showPreview) {
            clear(Color.black.getRGB(),false);

            Line line = CreateLine();
            trivRasterizer.rasterize(line);
            canvas.draw();
        }

    }

    Line CreateLine() {
        Line line;
        Point x = new Point(b.getX(), b.getY());
        if(snapGrid) {
            x = FixPoints(x);
        }

        if(makeDotted){
            line = new DotLine(a, x, Color.red, spacing);
        }
        else{
            line = new Line(a, x, Color.red);
        }
        return line;
    }

    Point FixPoints(Point point){
        int x = Math.abs(a.getX() - b.getX());
        int y = Math.abs(a.getY() - b.getY());
        double deg = Math.toDegrees(Math.atan2(x, y));
        if(deg < 22.5)
        {
            point = new Point(a.getX(), b.getY());
        }
        else if (deg < 67.5){
            //b = new Point(a.getX() + (int)Math.round((a.getX() - b.getX()) * Math.sqrt(2)), a.getY() + (int)Math.round((a.getY() -b.getY()) * Math.sqrt(2)));


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
            point = new Point(b.getX(), a.getY());
        }
        System.out.println("deg = " + deg);
        return point;
    }
}
