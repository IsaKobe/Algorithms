package models.Maps;

import models.Points.Point;
import models.Points.PointPointer;


public abstract class BaseMap<T> {
    ListWrapper<T>[][] points;
    int w;
    int h;

    public BaseMap(int x, int y) {
        w = x;
        h = y;
        clearMap();
    }
    public void clearMap(){
        points = new ListWrapper[w][h];
    }

    boolean isBound(int x, int y){
        return  (x < 0 || x >= w || y < 0 || y >= h);
    }
    public void addPoint(int x, int y, T point) {
        if(isBound(x,y))
            return;

        ListWrapper<T> list = points[x][y];
        if(list == null) {
            list = new ListWrapper<T>();
            points[x][y] = list;
        }
        list.points.add(point);
    }

    void removePoint(int x, int y, T point) {
        if(isBound(x,y))
            return;
        ListWrapper<T> list = points[x][y];
        if(list == null) {
            System.err.println("There are no points");
            return;
        }
        for (int i = list.points.size()-1; i > -1; i--) {
            if(list.points.get(i) == point) {
                list.points.remove(i);
                break;
            }
        }
        if(list.points.isEmpty()) {
            points[x][y] = null;
        }
    }

    T getPoint(int x, int y){
        if(x < points.length && y < points[x].length) {
            if(points[x][y] == null)
                return null;
            return points[x][y].points.getFirst();
        }
        return null;
    }

    public T findPoint(int x, int y) {
        int maxRange = 10;
        T point = null;
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
