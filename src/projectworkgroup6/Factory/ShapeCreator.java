package projectworkgroup6.Factory;

import javafx.scene.paint.Color;
import projectworkgroup6.Model.Shape;

public abstract class ShapeCreator {
    public abstract Shape createShape(double x, double y, Color color);
}
