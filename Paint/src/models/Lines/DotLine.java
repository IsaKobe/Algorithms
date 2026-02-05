package models.Lines;

import models.Point;

import java.awt.*;

public class DotLine extends Line {
    int pixelSpacing;

    public DotLine(models.Point a, Point b, Color color, int pixelSpacing) {
        super(a,b,color);
        this.pixelSpacing = pixelSpacing;
    }

    public int getPixelSpacing() {
        return pixelSpacing;
    }
}
