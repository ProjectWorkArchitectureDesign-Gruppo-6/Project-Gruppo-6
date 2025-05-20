package projectworkgroup6.Factory;

import projectworkgroup6.Model.ColorModel;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.View.ShapeView;

public abstract class ShapeCreator {
    public abstract Shape createShape(double x, double y);
    public abstract ShapeView createShapeView(Shape shape);
}
