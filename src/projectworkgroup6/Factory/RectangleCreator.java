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
    public Shape createShape(double x, double y, ColorModel border, ColorModel fill) {
        return new Rectangle(x, y, false, 100, 50, border, fill); // dimensioni di default
    }

    @Override
    public ShapeView createShapeView(Shape shape) {
        return new RectangleView((Rectangle) shape);
    }

    /*vengono usati solo da polygonCreator*/
    @Override
    public List<double[]> getTempVertices() {
        return null;
    }

    @Override
    public void resetVertices() {

    }

    @Override
    public void addVertex(double x, double y) {

    }
}
