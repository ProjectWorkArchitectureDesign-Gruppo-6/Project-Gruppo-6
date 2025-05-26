package projectworkgroup6.Controller;

import javafx.scene.paint.Color;
import projectworkgroup6.Decorator.SelectedDecorator;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.State.CanvasState;
import projectworkgroup6.View.ShapeView;

import java.util.Map;

public interface StateObserver {

    void onStateChanged(CanvasState newMode);
    void onCanvasChanged(Map<Shape,ShapeView> map);
    void onColorChanged(Color currentStroke, Color currentFill);

    void onChangeFontColor(Color currentFontColor);

    void onChangeFontFamily(String currentFontName);
}
