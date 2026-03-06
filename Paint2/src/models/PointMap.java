package models;

import models.Points.Point;
import models.Points.PointPointer;

import java.util.ArrayList;

class PointList{
    public ArrayList<PointPointer> points = new ArrayList<PointPointer>();
}

public class PointMap {
    PointList[][] points;
    int w;
    int h;

    static PointMap map;

    public PointMap(int x,int y) {
        w = x;
        h = y;
        clearMap();
        map = this;
    }
    public void clearMap(){
        points = new PointList[w][h];
    }

    public void addPoint(PointPointer point) {
        if(point.X() < 0 || point.X() >= w || point.Y() < 0 || point.Y() >= h)
            return;

        PointList list = points[point.X()][point.Y()];
        if(list == null) {
            list = new PointList();
            points[point.X()][point.Y()] = list;
        }
        list.points.add(point);
    }

    void removePoint(PointPointer point) {
        if(point.X() < 0 || point.X() >= w || point.Y() < 0 || point.Y() >= h)
            return;
        PointList list = points[point.X()][point.Y()];
        if(list == null) {
            System.err.println("There are no points");
            return;
        }
        for (int i = list.points.size()-1; i > -1; i--) {
            if(list.points.get(i) ==(point)) {
                list.points.remove(i);
                break;
            }
        }
        if(list.points.isEmpty()) {
            points[point.X()][point.Y()] = null;
        }
    }

    public void movePoint(PointPointer point, Point newValue) {
        removePoint(point);

        point.copy(newValue);

        addPoint(point);
        point.MoveParent();
    }

    public static void UpdatePoint(PointPointer point, Point newValue){
        map.removePoint(point);

        point.copy(newValue);

        map.addPoint(point);
    }

    PointPointer getPoint(int x, int y){
        if(x < points.length && y < points[x].length) {
            if(points[x][y] == null)
                return null;
            return points[x][y].points.getFirst();
        }
        return null;
    }

    public PointPointer findPoint(int x, int y) {
        int maxRange = 10;
        PointPointer point = null;
        for (int i = 0; i < maxRange; i++) {
            for (int a = x - i; a <= x + i; a++) {
                for (int b = y - i; b <= y + i; b++) {
                    if((point = getPoint(a,b)) != null)
                        return point;
                }
            }
        }
        return point;
    }
}
