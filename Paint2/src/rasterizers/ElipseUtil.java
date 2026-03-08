package rasterizers;

import models.MyCanvas;
import models.Points.Point;
import models.Shapes.Rect;
import rasters.Raster;

public class ElipseUtil {
    public static MyCanvas canvas;
    public static void DrawElipse(Point a, Point b, int gap, int width, int color, Rect rect){
        int cX = (a.X()+b.X())/2;
        int cY = (a.Y()+b.Y())/2;


        int XRadius = Math.abs(a.X() - b.X()) / 2;
        int YRadius = Math.abs(a.Y() - b.Y()) / 2;

        if(XRadius - width < 2 || YRadius - width < 2)
        {
            LineUtil.DrawLine(a, b, gap, width, color, rect);
            return;
        }
        DrawElipse(cX, cY, XRadius, YRadius, gap, color, false, rect);

        for (int i = 0; i < width; i++) {
            DrawElipse(cX, cY, XRadius + i, YRadius + i, gap, color, i+1 < width, null);
            DrawElipse(cX, cY, XRadius - i, YRadius - i, gap, color, i+1 < width, null);
        }
    }

    static void DrawElipse(int cX, int cY, int XRadius, int YRadius, int gap, int color, boolean bold, Rect rect){
        int TwoASquare = 2*XRadius*XRadius;
        int TwoBSquare = 2*YRadius*YRadius;
        int X = XRadius;
        int Y = 0;
        int XChange = YRadius*YRadius*(1 - 2 * XRadius);
        int YChange = XRadius*XRadius;
        int EllipseError = 0;
        int StoppingX = TwoBSquare*XRadius;
        int StoppingY = 0;

        int iteration = 0;
        boolean active = true;
        while (StoppingX >= StoppingY)
        {
            if(active){
                Plot4EllipsePoints(X, Y, cX, cY, color, rect);
                if(bold){
                    Plot4EllipsePoints(X + 1, Y, cX, cY, color, null);
                    Plot4EllipsePoints(X - 1, Y, cX, cY, color, null);
                }
            }

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
            if(active){
                Plot4EllipsePoints(X, Y, cX, cY, color, rect);
                if(bold){
                    Plot4EllipsePoints(X, Y+1, cX, cY, color, null);
                    Plot4EllipsePoints(X, Y-1, cX, cY, color, null);
                }
            }

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


    static void Plot4EllipsePoints(int x, int y, int cX, int cY, int color, Rect rect)
    {
        canvas.PaintOutline(cX + x, cY + y, color, rect);
        canvas.PaintOutline(cX - x, cY + y, color, rect);
        canvas.PaintOutline(cX - x, cY - y, color, rect);
        canvas.PaintOutline(cX + x, cY - y, color, rect);
    }
}
