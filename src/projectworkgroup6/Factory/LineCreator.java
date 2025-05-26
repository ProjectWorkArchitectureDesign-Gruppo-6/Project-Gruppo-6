package projectworkgroup6.Factory;

import projectworkgroup6.Model.ColorModel;
import projectworkgroup6.Model.Line;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.View.LineView;
import projectworkgroup6.View.ShapeView;

import java.util.List;

public class LineCreator implements ShapeCreator {

    private static final double DEFAULT_LENGTH = 100;

    private static LineCreator instance;

    private LineCreator() {
        // Costruttore privato per Singleton
    }

    public static LineCreator getInstance() {
        if (instance == null) {
            instance = new LineCreator();
        }
        return instance;
    }
    @Override
    public Shape createShape(double x, double y, ColorModel border, ColorModel fill) {

        return new Line(x, y, false, x + DEFAULT_LENGTH, y + DEFAULT_LENGTH, border, fill);
    }

    @Override
    public ShapeView createShapeView(Shape shape) {
        return new LineView((Line) shape);
    }

    /*vengono usati solo da poligon creator*/
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