package projectworkgroup6.Factory;

import projectworkgroup6.Model.ColorModel;
import projectworkgroup6.Model.Ellipse;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.View.EllipseView;
import projectworkgroup6.View.ShapeView;

public class EllipseCreator implements ShapeCreator {
    private static EllipseCreator instance;

    private EllipseCreator() {
        // Costruttore privato per Singleton
    }

    public static EllipseCreator getInstance() {
        if (instance == null) {
            instance = new EllipseCreator();
        }
        return instance;
    }
    @Override
    public Shape createShape(double x, double y, ColorModel border, ColorModel fill) {
        return new Ellipse(x, y, false, 80, 40, border, fill);
    }

    @Override
    public ShapeView createShapeView(Shape shape) {
        return new EllipseView((Ellipse) shape);
    }
}
