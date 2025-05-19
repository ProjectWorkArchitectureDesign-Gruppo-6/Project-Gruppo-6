package projectworkgroup6.Factory;

import javafx.scene.paint.Color;
import projectworkgroup6.Model.Line;
import projectworkgroup6.Model.Shape;

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
    public Shape createShape(double x, double y, Color color) {

        return new Line(x, y, false, color, x + DEFAULT_LENGTH, y + DEFAULT_LENGTH);
    }
}