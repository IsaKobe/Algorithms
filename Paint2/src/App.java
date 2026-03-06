
import Input.Input;
import javax.swing.*;
import java.awt.*;
import Window.DefaultWindow;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App(800, 600).start());
    }



    public void start() {
    }

    public App(int width, int height) {
        DefaultWindow window = new DefaultWindow(width, height);
    }

}
