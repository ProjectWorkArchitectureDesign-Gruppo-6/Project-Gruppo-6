package projectworkgroup6.Factory;

import javafx.scene.paint.Color;
import projectworkgroup6.Model.*;

public class ShapeFactory {

    public enum ShapeType {
        RECTANGLE, CIRCLE, LINE
    }

    public static Shape createShape(ShapeType type, double x, double y) {
        switch (type) {
            case RECTANGLE:
                return new Rectangle(x, y, 100, 60);
            case CIRCLE:
                return new Ellipse(x, y, 40, 50);
            case LINE:
                return new Line(x, y, x + 50, y + 50);
            default:
                throw new IllegalArgumentException();
        }
    }
}
