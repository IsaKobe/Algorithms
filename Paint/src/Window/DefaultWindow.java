package Window;

import Input.Input;
import models.MyCanvas;
import rasterizers.TrivRasterizer;
import rasters.RasterBufferedImage;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;

public class DefaultWindow {


    public DefaultWindow(int width, int height) {
        JFrame frame = new JFrame();

        frame.setLayout(new BorderLayout());

        frame.setTitle("Delta : " + this.getClass().getName());
        frame.setResizable(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        RasterBufferedImage raster = new RasterBufferedImage(width, height);

        JPanel panel = new JPanel() {
            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                raster.repaint(g);
            }

        };
        panel.setPreferredSize(new Dimension(width, height));

        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);

        panel.requestFocus();
        panel.requestFocusInWindow();


        Input input = new Input(panel, raster);
    }
}
