package projectworkgroup6.Factory;

import projectworkgroup6.Model.ColorModel;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.View.ShapeView;

import java.util.List;

public interface ShapeCreator {
    public abstract Shape createShape(double x, double y, double width, double height, ColorModel border, ColorModel fill, int layer, int group);
    public abstract ShapeView createShapeView(Shape shape);

}
