package projectworkgroup6.Factory;

import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Model.ColorModel;
import projectworkgroup6.Model.Rectangle;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.View.RectangleView;
import projectworkgroup6.View.ShapeView;

public class RectangleCreator extends ShapeCreator {

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
    public Shape createShape(double x, double y) {
        return new Rectangle(x, y, false, 100, 50); // dimensioni di default
    }

    @Override
    public ShapeView createShapeView(Shape shape) {
        return new RectangleView((Rectangle) shape);
    }
}
