package models.Complex;

import models.Point;

public class Circle{
    Point a;
    Point b;

    public Circle(Point a, Point b){
        this.a = a;
        this.b = b;
    }

    void draw(){

    }
//    t1 = r / 16
//    x = r
//            y = 0
//    Repeat Until x < y
//    Pixel (x, y) and all symmetric pixels are colored (8 times)
//    y = y + 1
//    t1 = t1 + y
//            t2 = t1 - x
//    If t2 >= 0
//    t1 = t2
//            x = x - 1
}
