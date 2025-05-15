package projectworkgroup6.Factory;

import javafx.scene.paint.Color;
import projectworkgroup6.Model.Rectangle;
import projectworkgroup6.Model.Shape;

public class RectangleCreator extends ShapeCreator {
    @Override
    public Shape createShape(double x, double y, Color color) {
        return new Rectangle(x, y, color, 100, 50); // dimensioni di default
    }
}
