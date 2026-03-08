package models.Maps;

import models.Points.Point;
import models.Points.PointPointer;

public class PointerPointMap extends BaseMap<PointPointer>{
    static PointerPointMap map;
    public PointerPointMap(int x, int y) {
        super(x, y);
        map = this;
    }

    public void addPoint(PointPointer pointer){
        super.addPoint(pointer.X(), pointer.Y(), pointer);
    }

    public void movePoint(PointPointer point, Point newValue) {
        MovePoint(point, newValue, true);
    }
    void MovePoint(PointPointer point, Point newValue, boolean updateParent){
        removePoint(point.X(), point.Y(), point);

        point.copy(newValue);

        addPoint(point.X(), point.Y(), point);
        if(updateParent)
            point.MoveParent();
    }

    public static void UpdatePoint(PointPointer point, Point newValue){
        map.MovePoint(point, newValue, false);
    }
}
