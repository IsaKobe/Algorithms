package Input.Callbacks;

import Input.Input;
import models.Point;

import javax.swing.*;
import java.awt.event.*;

public class Mouse {
    public Mouse(Input input, JPanel panel) {
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(e.getButton() != MouseEvent.BUTTON1)
                    return;
                input.press(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(e.getButton() != MouseEvent.BUTTON1)
                    return;
                input.release(e);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                input.UpdateB(e, true);
            }


            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                input.changeSpacing(e.getUnitsToScroll());
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                input.click(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                input.UpdateB(e, false);
            }
        };

        panel.addMouseListener(mouseAdapter);
        panel.addMouseMotionListener(mouseAdapter);
        panel.addMouseWheelListener(mouseAdapter);
    }
}

