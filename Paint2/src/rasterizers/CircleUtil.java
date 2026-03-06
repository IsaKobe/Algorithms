package rasterizers;

import models.Points.Point;
import rasters.Raster;


public class CircleUtil {
    public static Raster raster;

    public static void DrawCircle(Point a, Point b, int gap, int color){

        PointUtil.DrawPoint(a, color);
        PointUtil.DrawPoint(b, color);

        int xc = (a.X() + b.X()) / 2;
        int yc = (a.Y() + b.Y()) / 2;
        int r = Math.round(Math.abs(a.Y() - b.Y()) / 2f);  // count
        int x = 0, y = r;

        int tX = Math.round(Math.abs(a.X() - b.X()) / 2f);
        int d = 3 - 2 * r;//tX;

        drawCircle(xc, yc, x, y, color);

        while (y >= x){

            if (d > 0) {
                y--;
                d += (4 * (x - y)) + 10;
            }
            else {
                d += (4 * x) + 6;
//                x++;
            }
            x++;
            drawCircle(xc, yc, x, y,color);
        }
    }

    static void drawCircle(int xc, int yc, int x, int y, int color){
        raster.setPixel(xc+x, yc+y, color);
        raster.setPixel(xc-x, yc+y, color);
        raster.setPixel(xc+x, yc-y, color);
        raster.setPixel(xc-x, yc-y, color);
        raster.setPixel(xc+y, yc+x, color);
        raster.setPixel(xc-y, yc+x, color);
        raster.setPixel(xc+y, yc-x, color);
        raster.setPixel(xc-y, yc-x, color);
    }



}
