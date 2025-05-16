package projectworkgroup6.Factory;

import javafx.scene.paint.Color;
import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Model.Rectangle;
import projectworkgroup6.Model.Shape;

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
    public Shape createShape(double x, double y, Color color) {
        return new Rectangle(x, y, color, 100, 50); // dimensioni di default
    }
}
