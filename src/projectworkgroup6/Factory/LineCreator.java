package projectworkgroup6.Factory;

import projectworkgroup6.Model.ColorModel;
import projectworkgroup6.Model.Line;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.View.LineView;
import projectworkgroup6.View.ShapeView;

import java.util.List;

public class LineCreator implements ShapeCreator {


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
    public Shape createShape(double x, double y, double width, double height, ColorModel border, ColorModel fill, int layer, int group) {

        return new Line(x, y, false, width, height, border, fill, layer, group);
    }

    @Override
    public ShapeView createShapeView(Shape shape) {
        return new LineView((Line) shape);
    }

}