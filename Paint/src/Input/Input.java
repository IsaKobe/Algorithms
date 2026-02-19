package Input;

import Input.Callbacks.Keyboard;
import Input.Callbacks.Mouse;
import models.Complex.MyPolygon;
import models.Point;
import rasters.Raster;

import javax.swing.*;
import java.awt.event.*;
enum InputMode{
    Line,
    Poly,
    Vertex
}

public class Input {
    LineActions actions;

    Point a;
    Point b;

    Point temp;

    MyPolygon polygon;

    InputMode mode = InputMode.Line;

    public void release(MouseEvent e) {
        if(mode != InputMode.Line || a.getX() == -1 && a.getY() == -1) {
           return;
        }
        b.setX(e.getX());
        b.setY(e.getY());
        actions.addLine(a,b);
        a.reset();
        b.reset();
        temp = null;
    }

    public void UpdateB(MouseEvent e, boolean isDrag) {
        if(mode == InputMode.Poly) {
            b.getFromEv(e);
            if(polygon != null && !polygon.points.isEmpty())
                actions.drawTempLine(a, b);
        }
        else if(isDrag) {
            if(mode == InputMode.Line) {
                b.getFromEv(e);
                actions.drawTempLine(a, b);
            }
            else if (temp != null) {
                actions.moveVertex(temp, new Point(e));
            }
        }
    }

    /// Mouse down
    public void press(MouseEvent e) {
        switch (mode) {
            case Poly:
                return;
            case Line:
                a.getFromEv(e);
                b.copy(a);
                break;
            case Vertex:
                temp = actions.tryTakeVertex(new Point(e));
                break;
        }
    }


    public void click(MouseEvent e) {
        if(mode == InputMode.Poly) {
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

    void switchMode(InputMode mode) {
        if(this.mode == mode) {
            if(mode != InputMode.Line)
                switchMode(InputMode.Line);
            return;
        }

        switch (this.mode) {
            case Line:
                a.reset();
                b.reset();
                break;
            case Poly:
                polygon = null;
                actions.tempPolygon(polygon);
                break;
            case Vertex:
                break;
        }

        a.reset();
        b.reset();
        actions.repaint();

        switch (mode) {
            case Line:
                break;
            case Poly:
                break;
            case Vertex:
                break;
        }
        this.mode = mode;
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
                switchMode(InputMode.Poly);
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
            case 'e':
                a.reset();
                b.reset();
                switchMode(InputMode.Vertex);
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
