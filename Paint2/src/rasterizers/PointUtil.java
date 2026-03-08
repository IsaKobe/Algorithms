package rasterizers;

import models.Points.Point;
import rasters.Raster;

import java.util.ArrayList;
import java.util.List;

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

    public static void MinMaxPoint(Point[] points, Point min, Point max){
        min.X(Integer.MAX_VALUE);
        min.Y(Integer.MAX_VALUE);
        max.X(Integer.MIN_VALUE);
        max.Y(Integer.MIN_VALUE);

        for (Point point : points) {
            if(point.X() < min.X())
                min.X(point.X());
            if(point.X() > max.X())
                max.X(point.X());

            if(point.Y() < min.Y())
                min.Y(point.Y());
            if(point.Y() > max.Y())
                max.Y(point.Y());
        }

    }
}
