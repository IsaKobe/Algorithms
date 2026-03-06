package rasterizers;

import rasters.Raster;

public class PixelUtil {
    public static Raster raster;
    public static void DrawPixel(int x, int y, int color){
        raster.setPixel(x,y,color);
    }
}
