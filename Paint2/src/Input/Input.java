package Input;

import Input.Callbacks.Keyboard;
import Input.Callbacks.Mouse;
import models.Points.PointPointer;
import models.Shapes.*;
import models.Points.Point;
import models.Shapes.Polygon;
import models.Shapes.Rectangle;
import rasters.Raster;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Input {
    Actions actions;

    Point a;
    Point b;

    PointPointer temp;

    Rect rect;

    InputMode mode = InputMode.Line;

    boolean dragging
            = false;
    public void release(MouseEvent e) {
        switch (mode){
            case Circle, Rectangle, Line:
                if(rect != null){
                    actions.finishRect();
                    rect = null;
                }
                break;
            default:
                return;
        }
        a.reset();
        b.reset();
        temp = null;
    }

    public void UpdateB(Point p, boolean isDrag) {
        dragging = isDrag;
        switch (mode){
            case Poly:
                b = p;
                if(rect != null)
                    b = actions.drawTempLine(a, b);
                System.out.println(b);
                break;
            case Circle, Rectangle, Line:
                b = p;
                if(rect != null){
                    (rect).UpdateSecondPoint(actions.snapPoint(a, b, mode != InputMode.Line));
                    actions.repaint();
                }
                break;
            case Vertex:
                if(!isDrag || temp == null)
                    return;
                actions.moveVertex(temp, p);
                break;
            case Rubber:
                if(dragging){
                    actions.ClearAt(p);
                }
                break;
        }
    }
    /// Mouse down
    public void press(MouseEvent e) {
        switch (mode) {
            case Line:
                a.getFromEv(e);
                b.copy(a);
                rect = new Line(a.clone(), a.clone(), actions.GetSpacing(), Color.red);
                actions.addTemp(rect);
                break;
            case Vertex:
                temp = actions.tryTakeVertex(new Point(e));
                break;
            case Circle:
                a.getFromEv(e);
                b.copy(a);
                rect = new Elipse(a.clone(), actions.GetSpacing(), Color.red);
                actions.addTemp(rect);
                break;
            case Rectangle:
                a.getFromEv(e);
                b.copy(a);
                rect = new Rectangle(a.clone(), actions.GetSpacing(), Color.red);
                actions.addTemp(rect);
                break;

        }
    }


    public void click(MouseEvent e) {
        switch (mode){
            case Poly:
                if(rect == null){
                    a.getFromEv(e);
                    rect = new Polygon(a, actions.GetSpacing(), Color.red);
                    actions.addTemp(rect);
                }
                else{
                    ((Polygon) rect).AddPoint(b);
                    a.copy(b);
                }
                break;
            case Test:
                actions.canvas.GetRect(new Point(e));
                break;
        }

    }


    public void changeSpacing(int change) {
        actions.spacing = Math.clamp(actions.spacing + change, 2, 15);
        switch (mode){
            case Line, Poly:
                actions.drawTempLine(a, b);
                break;
            case Vertex:
                break;
            case Circle, Rectangle:
                if(rect == null)
                    return;
                rect.SetDotSpace(actions.spacing);
                actions.repaint();
                break;
        }
    }


    public Input(JPanel panel, Raster raster)
    {
        a = new Point(0,0);
        b = new Point(0,0);
        new Mouse(this, panel);
        new Keyboard(this, panel);
        actions = new Actions(panel, raster);
    }

    void switchMode(InputMode mode) {
        if(this.mode == mode) {
            if(mode != InputMode.Line)
                switchMode(InputMode.Line);
            return;
        }

        switch (this.mode) {
            case Line:
                break;
            case Poly, Circle, Rectangle:
                if(rect != null){
                    rect = null;
                    actions.removeTemp();
                }
                actions.repaint();
                break;
            case Vertex:
                break;
        }

        a.reset();
        b.reset();

        this.mode = mode;
    }
    public void invokeAction(int keyChar) {
        switch (keyChar) {
            case 'c':
                actions.clear();
                rect = null;
                a.reset();
                b.reset();
                break;
            case 'p':
                switchMode(InputMode.Poly);
                break;
            case KeyEvent.VK_SPACE:
                if(rect != null && actions.finishRect()){
                    rect = null;
                    a.reset();
                    b.reset();
                }
                break;
            case 'e':
                switchMode(InputMode.Vertex);
                break;
            case 'k':
                switchMode(InputMode.Circle);
                break;
            case 'r':
                switchMode(InputMode.Rectangle);
                break;
            case 'q':
                switchMode(InputMode.Test);
                break;
            case KeyEvent.VK_DELETE:
                switchMode(InputMode.Rubber);
                break;
            case 'd':
                actions.makeDotted = !actions.makeDotted;
                if(rect != null)
                    rect.SetDotSpace(actions.GetSpacing());
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
        UpdateB(b.clone(), dragging);
    }

}
