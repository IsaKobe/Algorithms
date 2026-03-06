package rasterizers;

import models.Points.Point;
import rasters.Raster;

public class LineUtil {
    public static Raster raster;

    public static void DrawLine(Point a, Point b, int pixelGap, int color) {
        int x0 = a.X();
        int x1 = b.X();
        int y0 = a.Y();
        int y1 = b.Y();

        int dx = Math.abs(x1 - x0);
        int sx = x0 < x1 ? 1 : -1;
        int dy = -Math.abs(y1 - y0);
        int sy = y0 < y1 ? 1 : -1;
        int error = dx + dy;

        int iteration = 0;
        boolean draw = true;
        while (true) {
            if(draw)
                raster.setPixel(x0, y0, color);

            int e2 = 2 * error;
            if (e2 >= dy){
                if (x0 == x1)
                    break;
                error += dy;
                x0 += sx;
            }
            if (e2 <= dx){
                if (y0 == y1)
                    break;
                error += dx;
                y0 += sy;
            }

            if(pixelGap > 0)
            {
                iteration++;
                if(iteration % pixelGap == 0){
                    draw = !draw;
                }
            }
        }
    }
}
