package projectworkgroup6.Factory;

import javafx.scene.paint.Color;
import projectworkgroup6.Model.Ellipse;
import projectworkgroup6.Model.Shape;

public class EllipseCreator extends ShapeCreator {
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
    public Shape createShape(double x, double y, Color color) {
        return new Ellipse(x, y, false, color, 80, 40);
    }
}
