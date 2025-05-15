package projectworkgroup6.Factory;

import javafx.scene.paint.Color;
import projectworkgroup6.Model.Ellipse;
import projectworkgroup6.Model.Shape;

public class EllipseCreator extends ShapeCreator {
    @Override
    public Shape createShape(double x, double y, Color color) {
        return new Ellipse(x, y, color, 80, 40);
    }
}
