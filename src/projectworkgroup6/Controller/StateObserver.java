package projectworkgroup6.Controller;

import javafx.scene.paint.Color;
import projectworkgroup6.Model.ColorModel;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.State.CanvasState;
import projectworkgroup6.View.ShapeView;

import java.util.List;
import java.util.Map;

public interface StateObserver {
    void onStateChanged(CanvasState newMode);
    void onCanvasChanged(Map<Shape,ShapeView> map);
    void onColorChanged(Color currentStroke);
}
