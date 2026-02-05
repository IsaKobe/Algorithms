package Input;

import models.Point;

public class Helpers {
    public static void SnapPoints(Point point, Point a, Point b){
        int x = Math.abs(a.getX() - b.getX());
        int y = Math.abs(a.getY() - b.getY());
        double deg = Math.toDegrees(Math.atan2(x, y));
        if(deg < 22.5)
        {
            point.setX(a.getX());
        }
        else if (deg < 67.5){

            double mod = Math.sqrt(2) / 2f;
            int moveBy = (int)Math.round(Math.sqrt((Math.pow(x, 2) + (Math.pow(y, 2)))) * mod);



            if(a.getX() < b.getX())
                point.setX(a.getX() + moveBy);
            else
                point.setX(a.getX() - moveBy);

            if(a.getY() < b.getY())
                point.setY(a.getY() + moveBy);
            else
                point.setY(a.getY() - moveBy);
        }
        else{
            point.setY(a.getY());
        }
    }
}
