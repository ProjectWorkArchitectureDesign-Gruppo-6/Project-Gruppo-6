package projectworkgroup6.Factory;

import javafx.scene.paint.Color;
import projectworkgroup6.Model.Line;
import projectworkgroup6.Model.Shape;

public class LineCreator extends ShapeCreator {
    @Override
    public Shape createShape(double x, double y, Color color) {
        return new Line(x, y, color, x + 50, y + 50);
    }
}