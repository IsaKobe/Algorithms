package rasterizers;

import models.Points.Point;
import rasters.Raster;

public class PointUtil {
    public static Raster raster;
    static int m_radius = 2;

    public static void DrawPoint(Point point, int color){
        DrawPoint(point, m_radius, color);
    }

    public static void DrawPoint(Point point, int radius, int color){
        int maxX = point.X() + radius;
        int maxY = point.Y() + radius;
        for (int x = point.X() - radius; x < maxX; x++){
            for (int y = point.Y() - radius; y < maxY; y++){
                raster.setPixel(x, y, color);
            }
        }
    }
}
