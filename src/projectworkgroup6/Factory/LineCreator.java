package projectworkgroup6.Factory;

import projectworkgroup6.Model.Line;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.View.LineView;
import projectworkgroup6.View.ShapeView;

public class LineCreator extends ShapeCreator {

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
    public Shape createShape(double x, double y) {

        return new Line(x, y, false, x + DEFAULT_LENGTH, y + DEFAULT_LENGTH);
    }

    @Override
    public ShapeView createShapeView(Shape shape) {
        return new LineView((Line) shape);
    }
}