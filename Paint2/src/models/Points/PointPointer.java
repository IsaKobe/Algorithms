package models.Points;

import models.Shapes.Rect;

public class PointPointer extends Point{
    public Rect parent;
    public PointPointer(Point point, Rect _parent){
        super(point.x, point.y);
        parent = _parent;
    }

    public void MoveParent(){
        if(parent != null)
            parent.UpdateVertex(this);
    }
}
