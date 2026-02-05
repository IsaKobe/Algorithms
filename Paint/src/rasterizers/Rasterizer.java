package rasterizers;

import models.Lines.Line;

import java.awt.*;

public interface Rasterizer {

    void setColor(Color color);

    void rasterize(Line line);

}
