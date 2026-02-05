package Input;

import Input.Callbacks.Keyboard;
import Input.Callbacks.Mouse;
import models.Complex.MyPolygon;
import models.Point;
import rasters.Raster;

import javax.swing.*;
import java.awt.event.*;

public class Input {
    LineActions actions;

    Point a;
    Point b;

    MyPolygon polygon;

    boolean makePolygon = false;

    public void release(MouseEvent e) {
        if(makePolygon) {
           return;
        }
        b.setX(e.getX());
        b.setY(e.getY());
        actions.addLine(a,b);
        a.reset();
        b.reset();
    }

    public void UpdateB(MouseEvent e, boolean isDrag) {
        if(makePolygon) {
            b =  new Point(e);
            if(polygon != null && !polygon.points.isEmpty())
                actions.drawTempLine(a, b);
        }
        else if(isDrag) {
            b =  new Point(e);
            actions.drawTempLine(a, b);
        }
    }

    public void press(MouseEvent e) {
        if(makePolygon) {
            return;
        }
        a.setX(e.getX());
        a.setY(e.getY());
        b.copy(a);
    }


    public void click(MouseEvent e) {
        if(makePolygon) {
            if(polygon == null){
                a = new Point(e);
                polygon = new MyPolygon(a.clone());
                actions.tempPolygon(polygon);
            }
            else{
                actions.addPolygonPoint(polygon, a, new Point(e));
            }
        }
    }


    public void changeSpacing(int change) {
        int space = Math.clamp(actions.spacing + change, 2, 15);
        actions.spacing = space;
        actions.drawTempLine(a, b);
    }


    public Input(JPanel panel, Raster raster)
    {
        a = new Point(0,0);
        b = new Point(0,0);
        new Mouse(this, panel);
        new Keyboard(this, panel);
        actions = new LineActions(panel, raster);
    }

    public void invokeAction(int keyChar) {

        switch (keyChar) {
            case 'c':
                actions.clear();
                polygon = null;
                break;
            case 'p':
                a.reset();
                b.reset();
                if(makePolygon) {
                    polygon = null;
                    actions.tempPolygon(polygon);
                    actions.repaint();
                }
                makePolygon = !makePolygon;

                break;
            case KeyEvent.VK_SPACE:
                if(polygon != null && polygon.points.size() > 2)
                {
                    actions.finishPolygon(polygon);
                    polygon = null;
                    a.reset();
                    b.reset();
                }
                break;
            case 'd':
                actions.makeDotted = !actions.makeDotted;
                if(polygon != null)
                    polygon.space = actions.makeDotted ? actions.spacing : 0;
                actions.repaint();

                actions.drawTempLine(a, b);
                break;
        }
    }

    public void changeDrawType(int key, boolean pressed) {
        switch (key) {
            case KeyEvent.VK_SHIFT:
                actions.snapGrid = pressed;
                break;
            default:
                return;
        }
        actions.drawTempLine(a, b);
    }

}
