package Input.Callbacks;

import Input.Input;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Keyboard {

    public Keyboard(Input input, JPanel panel) {
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
                input.invokeAction(e.getKeyChar());
            }

        };
        panel.addKeyListener(keyAdapter);
    }
}
