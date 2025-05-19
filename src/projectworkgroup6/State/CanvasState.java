package projectworkgroup6.State;

import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import projectworkgroup6.Model.Shape;

import java.util.List;

public interface CanvasState {
    void handleClick(double x, double y, List<Shape> shapes);

    void handleMoveClick(double x, double y);

    void handleMouseDragged(double x, double y);

    void handleMouseReleased(double x, double y);

    void recoverShapes(List<Shape> shapes);

    void handleDelete(KeyEvent event, List<Shape> shapes);
}
