package Input;

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
import java.util.ArrayList;


public class Input {
    Actions actions;

    Point a;
    Point b;

    PointPointer temp;

    Rect rect;

    InputMode mode = InputMode.Test;

    ArrayList<JToggleButton> buttonList = new ArrayList<JToggleButton>();

    Point downPoint;
    boolean dragging = false;
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
        b = p;
        switch (mode){
            case Test:
                if(!dragging || rect == null)
                    return;
                Point diff = p.minus(downPoint);
                rect.Move(diff);
                downPoint = p.clone();
                actions.repaint();
                break;
            case Poly:
                if(rect != null)
                    b = actions.drawTempLine(a, b);
                System.out.println(b);
                break;
            case Circle, Rectangle, Line:
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
            case Test:
                downPoint = new Point(e);
                rect = actions.canvas.GetRect(downPoint.clone());
                System.out.println(rect);
                break;
            case Line:
                a.getFromEv(e);
                b.copy(a);
                rect = new Line(a.clone(), b.clone());
                actions.addTemp(rect);
                break;
            case Vertex:
                temp = actions.tryTakeVertex(new Point(e));
                break;
            case Circle:
                a.getFromEv(e);
                b.copy(a);
                rect = new Elipse(a.clone());
                actions.addTemp(rect);
                break;
            case Rectangle:
                a.getFromEv(e);
                b.copy(a);
                rect = new Rectangle(a.clone());
                actions.addTemp(rect);
                break;

        }
    }


    public void click(MouseEvent e) {
        switch (mode){
            case Poly:
                if(rect == null){
                    a.getFromEv(e);
                    rect = new Polygon(a);
                    actions.addTemp(rect);
                }
                else{
                    ((Polygon) rect).AddPoint(b);
                    a.copy(b);
                    actions.repaint();
                }
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


    public Input(JPanel panel, Raster raster, JMenuBar bar)
    {
        a = new Point(0,0);
        b = new Point(0,0);
        new Mouse(this, panel);

        actions = new Actions(panel, raster);

        FinishAction(panel);
        ClearPanel(panel);
        MakeDotted(panel);
        SnapToGrid(panel);

    }
    void FinishAction(JPanel panel){
        String mapKey = "finish polygon";
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), mapKey);

        panel.getActionMap().put(mapKey, new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if(rect != null && actions.finishRect()){
                    rect = null;
                    a.reset();
                    b.reset();
                }
            }
        });
    }

    void ClearPanel(JPanel panel){
        String mapKey = "clear panel";
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke('c'), mapKey);

        panel.getActionMap().put(mapKey, new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                actions.clear();
                rect = null;
                a.reset();
                b.reset();
            }
        });
    }

    void MakeDotted(JPanel panel){
//        String mapKey = "make dot";
//        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
//                .put(KeyStroke.getKeyStroke('d'), mapKey);
//
//        panel.getActionMap().put(mapKey, new AbstractAction() {
//            @Override
//            public void actionPerformed(java.awt.event.ActionEvent e) {
//                actions.makeDotted = !actions.makeDotted;
//                if(rect != null)
//                    rect.SetDotSpace(actions.GetSpacing());
//                actions.repaint();
//
//                actions.drawTempLine(a, b);
//            }
//        });
    }
    void SnapToGrid(JPanel panel){
        String mapPress = "press snap";
        String mapRelease = "release snap";

        panel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SHIFT, InputEvent.SHIFT_DOWN_MASK, false), mapPress);
        panel.getActionMap().put(mapPress, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actions.snapGrid = true;
                UpdateB(b, dragging);
            }
        });

        panel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SHIFT, 0, true), mapRelease);
        panel.getActionMap().put(mapRelease, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actions.snapGrid = false;
                UpdateB(b, dragging);
            }
        });
    }

    public void switchMode(InputMode mode) {
        if(this.mode == mode) {
            if(mode != InputMode.Test)
                switchMode(InputMode.Test);
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

        rect = null;
        this.mode = mode;

        a.reset();
        b.reset();


        buttonList.get(mode.ordinal()).setSelected(true);
    }


    public void SetButtons(ArrayList<JToggleButton> buttons) {
        buttonList = buttons;
    }

    public void SetColor(Color newColor, boolean outlineSet) {
        int col = newColor.getRGB();
        if(outlineSet)
            actions.outlineColor = col;
        else
            actions.fillColor = col;
        if(rect != null){
            if(outlineSet)
                rect.outlineColor = col;
            else
                rect.fillColor = col;
            actions.repaint();
        }
    }

    public void setWidth(int value) {
        actions.lineWidth = value;
        if(rect != null){
            rect.width = value;
            actions.repaint();
        }
    }

    public void setSpacing(int value) {
        actions.spacing = value;
        if(rect != null){
            rect.SetDotSpace(actions.GetSpacing());
            actions.repaint();
        }
    }
}
