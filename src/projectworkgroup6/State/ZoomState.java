package projectworkgroup6.State;

import javafx.geometry.Point2D;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import projectworkgroup6.Controller.CanvasController;
import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.View.ShapeView;

import java.util.Map;

public class ZoomState implements CanvasState{

    private double zoom;
    public ZoomState(double zoom) {
        this.zoom = zoom;
    }

    CanvasController canvasController = StateController.getInstance().getCanvasController();


    @Override
    public void handleClick(MouseEvent e, double x, double y, Map<Shape, ShapeView> map) {
        // Punto cliccato nella scena
        Point2D clickInCanvas = new Point2D(x,y);
        System.out.println(clickInCanvas);
        canvasController.setZoomClick(clickInCanvas, zoom);
    }


    @Override
    public void handlePression(double x, double y) {
        //Nulla
    }

    @Override
    public void handlePressionRotate(double x, double y) {

    }

    @Override
    public void handleMouseDragged(double x, double y) {
        // NUlla
    }

    @Override
    public void handleMouseReleased(double x, double y) {
        // Nulla
    }

    @Override
    public void recoverShapes(Map<Shape, ShapeView> map) {
        //Nulla
    }

    @Override
    public void handleDelete(KeyEvent event, Map<Shape, ShapeView> map) {
        //
    }

    @Override
    public void handleColorChanged(Color currentStroke, Color currentFill) {
        //
    }

    @Override
    public void handleKeyTyped(KeyEvent event, Map<Shape, ShapeView> map) {
        //
    }
}
