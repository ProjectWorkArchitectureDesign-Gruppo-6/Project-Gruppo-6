package projectworkgroup6.Factory;

import projectworkgroup6.Model.ColorModel;
import projectworkgroup6.Model.Rectangle;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.View.RectangleView;
import projectworkgroup6.View.ShapeView;

import java.util.List;

public class RectangleCreator implements ShapeCreator {

    private static RectangleCreator instance;

    private RectangleCreator() {
        // Costruttore privato per Singleton
    }

    public static RectangleCreator getInstance() {
        if (instance == null) {
            instance = new RectangleCreator();
        }
        return instance;
    }
    @Override
    public Shape createShape(double x, double y, double width, double height, ColorModel border, ColorModel fill, int layer, int group) {
        return new Rectangle(x, y, false, width, height, border, fill, layer, group);
    }

    @Override
    public ShapeView createShapeView(Shape shape) {
        return new RectangleView((Rectangle) shape);
    }

}
