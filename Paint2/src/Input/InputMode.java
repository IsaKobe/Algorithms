package Input;


public enum InputMode{
    Test(1),
    Vertex(2),
    Line(3),
    Poly(4),
    Circle(5),
    Rectangle(6),
    Rubber(7);

    final int value;

    private InputMode(int val){
        value = val;
    }
    public int getValue(){
        return this.value;
    }
}