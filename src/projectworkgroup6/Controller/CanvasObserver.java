package projectworkgroup6.Controller;

import projectworkgroup6.Model.Shape;

import java.util.List;

public interface CanvasObserver {
    void onCanvasChanged(List<Shape> shapes);

}

