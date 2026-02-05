package rasterizers;
import models.DotLine;
import models.Line;
import rasters.Raster;

import java.awt.*;

public class TrivRasterizer implements Rasterizer {

    Raster raster;
    int color;

    public TrivRasterizer(Raster raster, Color color) {
        this.raster = raster;
        this.color = color.getRGB();
    }

    @Override
    public void setColor(Color color) {
        this.color = color.getRGB();
    }

    @Override
    public void rasterize(Line line) {
        setColor(line.getColor());
        int xA = line.getA().getX();
        int xB = line.getB().getX();

        int yA = line.getA().getY();
        int yB = line.getB().getY();

        int step = 1;
        boolean draw = false;

        if(line instanceof DotLine dot)
        {
            step = dot.getPixelSpacing();
            draw = true;
        }
        int stepI = 0;



        if(xB == xA) {
            int min = Math.min(yA, yB);
            int max = Math.max(yA, yB);
            for (int i = min; i < max; i++) {
                if(draw && stepI % step == 0) {
                    i += step - 1;
                    stepI++;
                    continue;
                }
                raster.setPixel(xA, i, color);
                stepI++;
            }
        }

        double k = (yB - yA) / (double)(xB - xA);
        double q = yA - k * xA;

        if(Math.abs(k) < 1){
            int min = Math.min(xA, xB);
            int max = Math.max(xA, xB);

            for (int x = min; x < max; x++) {
                int y = (int) Math.round(k * x + q);

                if(draw && stepI % step == 0){
                    x += step - 1;
                    stepI++;
                    continue;
                }
                raster.setPixel(x, y, color);
                stepI++;
            }
        }
        else{

            int min = Math.min(yA, yB);
            int max = Math.max(yA, yB);

            for (int y = min; y < max; y++) {
                int x = (int) Math.round((y - q)/k);

                if(draw && stepI % step == 0){
                    y += step - 1;
                    stepI++;
                    continue;
                }
                raster.setPixel(x, y, color);
                stepI++;

            }
        }
    }
}
