package rasterizers;

import models.MyCanvas;
import models.Points.Point;

public class LineUtil {
    public static MyCanvas canvas;

    public static void DrawLine(Point a, Point b, int pixelGap, int width, int color){
        Point x = a.clone();
        Point y = b.clone();

        int dY = Math.abs(y.Y() - x.Y());
        int dX = Math.abs(y.X() - x.X());
        double offset = (width-1)*Math.sqrt(Math.pow((dX),2)+Math.pow((dY),2));

        if(dY < dX)
        {
            if(dY == 0)
                offset = width - 2;
            else
                offset /= (2*dX);
            for(int i = 1; i < offset; i++)
            {
                DrawLine(x.X(),x.Y()-i, y.X(), y.Y()-i, pixelGap, color);
                DrawLine(x.X(),x.Y()+i, y.X(), y.Y()+i, pixelGap, color);
            }
        }
        else
        {
            if(dX == 0)
                offset = width - 2;
            else
                offset /= (2*dY);

            for(int i = 1; i < offset; i++)
            {
                DrawLine(x.X()-i, x.Y(), y.X()-i, y.Y(), pixelGap, color);
                DrawLine(x.X()+i, x.Y(), y.X()+i, y.Y(), pixelGap, color);
            }
        }
        DrawLine(x.X(), x.Y(), y.X(), y.Y(), pixelGap, color);
    }

    static void DrawLine(int x0, int y0, int x1, int y1, int pixelGap, int color) {

        int dx = Math.abs(x1 - x0);
        int sx = x0 < x1 ? 1 : -1;
        int dy = -Math.abs(y1 - y0);
        int sy = y0 < y1 ? 1 : -1;
        int error = dx + dy;


        int iteration = 0;
        boolean draw = true;
        while (true) {
            if(draw)
                canvas.PaintOutline(x0, y0, color);

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
