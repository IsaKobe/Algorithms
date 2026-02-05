package Input.Callbacks;

import Input.Input;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Keyboard extends Callbacks {

    public Keyboard(Input input) {
        super(input);
        KeyAdapter keyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                input.changeDrawType(e.getKeyCode(), true);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                input.changeDrawType(e.getKeyCode(), false);
            }

            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar() == 'c') {
                    input.clear(Color.black.getRGB(),true);
                }
                if(e.getKeyChar() == 'p') {

                }
            }
        };
        input.panel.addKeyListener(keyAdapter);
    }
}
