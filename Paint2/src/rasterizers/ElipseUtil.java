package rasterizers;

import models.Points.Point;
import rasters.Raster;

public class ElipseUtil {
    public static Raster raster;

    public static void DrawElipse(Point a, Point b, int gap, int color){
        int cX = (a.X()+b.X())/2;
        int cY = (a.Y()+b.Y())/2;

        int XRadius = Math.abs(a.X() - b.X()) / 2;
        int YRadius = Math.abs(a.Y() - b.Y()) / 2;
        int TwoASquare = 2*XRadius*XRadius;
        int TwoBSquare = 2*YRadius*YRadius;
        int X = XRadius;
        int Y = 0;
        int XChange = YRadius*YRadius*(1 - 2 * XRadius);
        int YChange = XRadius*XRadius;
        int EllipseError = 0;
        int StoppingX = TwoBSquare*XRadius;
        int StoppingY = 0;

        if(XRadius < 2 || YRadius < 2)
        {
            LineUtil.DrawLine(a, b, gap, color);
            return;
        }
        int iteration = 0;
        boolean active = true;
        while (StoppingX >= StoppingY)
        {
            if(active)
                Plot4EllipsePoints(X, Y, cX, cY, color);

            Y++;
            StoppingY += TwoASquare;
            EllipseError += YChange;
            YChange += TwoASquare;

            if ((2 * EllipseError + XChange) > 0) {
                X--;
                StoppingX -= TwoBSquare;
                EllipseError += XChange;
                XChange += TwoBSquare;
            }

            if(gap > 0){
                iteration++;
                if(iteration % gap == 0)
                    active = !active;
            }
        }

        X = 0;
        Y = YRadius;
        XChange = YRadius*YRadius;
        YChange = XRadius*XRadius*(1 - 2*YRadius);
        EllipseError = 0;
        StoppingX = 0;
        StoppingY = TwoASquare*YRadius;

        iteration = 0;
        active = true;

        while (StoppingX <= StoppingY)
        {
            if(active)
                Plot4EllipsePoints(X, Y, cX, cY, color);

            X++;
            StoppingX += TwoBSquare;
            EllipseError += XChange;
            XChange += TwoBSquare;

            if ((2 * EllipseError + YChange) > 0) {
                Y--;
                StoppingY -= TwoASquare;
                EllipseError += YChange;
                YChange += TwoASquare;
            }
            if(gap > 0){
                iteration++;
                if(iteration % gap == 0)
                    active = !active;
            }
        }
    }


    static void Plot4EllipsePoints(int x, int y, int cX, int cY, int color)
    {
        raster.setPixel(cX + x, cY + y, color);
        raster.setPixel(cX - x, cY + y, color);
        raster.setPixel(cX - x, cY - y, color);
        raster.setPixel(cX + x, cY - y, color);
    }
}
