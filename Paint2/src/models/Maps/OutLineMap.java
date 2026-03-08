package models.Maps;

import models.Shapes.Rect;

public class OutLineMap extends BaseMap<Rect>{
    static OutLineMap map;
    public OutLineMap(int x, int y) {
        super(x, y);
        map = this;
    }

    public static boolean IsRectOnPoint(int x, int y, Rect rect){
        if(map.points[x][y] != null)
            return map.points[x][y].points.contains(rect);
        return false;
    }

    @Override
    public void addPoint(int x, int y, Rect point) {
        super.addPoint(x, y, point);
        //System.out.println("Rect:" + point + " x: " + x + " y: " + y);
    }

    public static int GetNumberOfIntersectsHorizontal(int sX, int sY, int eX, Rect rect){
        int number = 0;
            boolean lastWasWall = false;
        boolean res = false;
        for (;sX < eX; sX++){
            res = IsRectOnPoint(sX, sY, rect);
            if(res && !lastWasWall)
            {
                lastWasWall = true;
            }
            else if(!res && lastWasWall){
                lastWasWall = false;
                number++;
            }
        }
        return number;
    }



    public static boolean IsInside(int sX, int sY, int eX, Rect rect){
        int number = GetNumberOfIntersectsHorizontal(sX, sY, eX, rect);
        if(number % 2 == 1){
            number = GetNumberOfIntersectsHorizontal(eX, sY, rect.getMaxPoint().X(), rect);
            return number > 0;
        }
        return false;
    }
    public static int GetNextPoint(int sX, int sY, int eX, Rect rect, boolean start){
        boolean res = false;
        for (;sX < eX; sX++){
            res = IsRectOnPoint(sX, sY, rect);
            if(res && !start || !res && start)
            {
                return sX;
            }
        }
        return sX;
    }
}
