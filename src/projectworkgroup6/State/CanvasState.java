package projectworkgroup6.State;

import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import projectworkgroup6.Model.ColorModel;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.View.ShapeView;

import java.util.List;
import java.util.Map;

public interface CanvasState {
    void handleClick(double x, double y, Map<Shape, ShapeView> map);

    void handleMoveClick(double x, double y);

    void handleMouseDragged(double x, double y);

    void handleMouseReleased(double x, double y);

    void recoverShapes(Map<Shape, ShapeView> map);

    void handleDelete(KeyEvent event, Map<Shape, ShapeView> map);

    void handleColorChanged(Color currentStroke);
}
