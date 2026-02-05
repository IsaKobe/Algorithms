package Input.Callbacks;

import Input.Input;
import models.Point;

import java.awt.event.*;

public class Mouse extends Callbacks {
    public Mouse(Input input) {
        super(input);
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(e.getButton() != MouseEvent.BUTTON1)
                    return;
                input.a = new Point(e.getX(), e.getY());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(e.getButton() != MouseEvent.BUTTON1)
                    return;
                input.b = new Point(e.getX(), e.getY());
                input.canvas.addLine(input.createLine());
                input.a = new Point(0,0);
                input.b = new Point(0,0);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                input.b = new Point(e.getX(), e.getY());
                input.recreateLine();
            }
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                input.spacing = Math.clamp(input.spacing + e.getUnitsToScroll(), 2, 15);
                input.recreateLine();
            }
        };
        input.panel.addMouseListener(mouseAdapter);
        input.panel.addMouseMotionListener(mouseAdapter);
        input.panel.addMouseWheelListener(mouseAdapter);
    }
}

