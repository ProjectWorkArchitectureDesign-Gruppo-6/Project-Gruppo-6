package projectworkgroup6.Factory;

import projectworkgroup6.Model.ColorModel;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.View.ShapeView;

public interface ShapeCreator {
    public abstract Shape createShape(double x, double y, ColorModel border, ColorModel fill);
    public abstract ShapeView createShapeView(Shape shape);
}
