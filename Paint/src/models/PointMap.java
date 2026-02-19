package models;

import java.util.ArrayList;

class PointList{
    public ArrayList<Point> points = new ArrayList<Point>();
}

public class PointMap {
    PointList[][] points;
    int w;
    int h;

    public PointMap(int x,int y) {
        w = x;
        h = y;
        clearMap();
    }
    public void clearMap(){
        points = new PointList[w][h];
    }

    void addPoint(Point point) {
        PointList list = points[point.getX()][point.getY()];
        if(list == null) {
            list = new PointList();
            points[point.getX()][point.getY()] = list;
        }
        list.points.add(point);
    }
    void removePoint(Point point) {
        PointList list = points[point.getX()][point.getY()];
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
            points[point.getX()][point.getY()] = null;
        }
    }

    public void movePoint(Point point, Point newValue) {
        removePoint(point);
        point.copy(newValue);
        addPoint(point);
    }

    Point getPoint(int x, int y){
        if(x < points.length && y < points[x].length) {
            if(points[x][y] == null)
                return null;
            return points[x][y].points.getFirst();
        }
        return null;
    }

    public Point findPoint(int x, int y) {
        int maxRange = 10;
        Point point = null;
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
